package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.Quanjubianliang;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.activity.adapter.MyPeixunAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;

public class MyPeixunActivity extends Activity {
	private LinearLayout ll_list_back;
	private TextView tv_list_title;
	private ListView lv_list_info;
	private List<Map<String,Object>> list;
	private String id="",date="";
	private int time=0;
	
	private final static int SCANNIN_GREQUEST_CODE = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//new Xiaoxibeijing().changetop(this, R.color.main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_list);
		init();
		showdata();
		addlistener();
	}
	private void init(){
		ll_list_back=(LinearLayout) findViewById(R.id.ll_list_back);
		tv_list_title=(TextView) findViewById(R.id.tv_list_title);
		lv_list_info=(ListView) findViewById(R.id.lv_list_info);
	}
	
	private void showdata(){
		tv_list_title.setText("我的培训");
		
		
		gatedata();
		
		lv_list_info.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if((Integer)list.get(arg2).get("Application_Type")!=1){
					return;
				}else{
					Intent intent=new Intent(MyPeixunActivity.this, MyPeixundetilActivity.class);
					intent.putExtra("id", list.get(arg2).get("id").toString());
					startActivity(intent);
				}
			}
		});
	}
	
	private void addlistener(){
		ListenerServer.setfinish(MyPeixunActivity.this, ll_list_back);
	}
	
	private void gatedata(){
		
		list = new ArrayList<Map<String, Object>>();
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(msg.what==1){
					try {
						JSONArray ja=new JSONArray(msg.obj.toString());
						for(int i=0;i<ja.length();i++){
							Map<String,Object> map=new HashMap<String, Object>();
							JSONObject jo=ja.getJSONObject(i);
							map.put("id", jo.getString("PK_Act"));
							map.put("title", jo.getString("act_Name"));
							map.put("time", jo.getString("start_Time").replace("-", "/").trim()+"-"+jo.getString("end_Time").replace("-", "/").trim());
							map.put("endtime", jo.getString("end_Time"));
							map.put("date", jo.getString("bm_End_Time"));
							map.put("Application_Type", jo.getInt("application_Type"));
							map.put("le", jo.getString("application_Fee"));
							map.put("member", jo.getString("seats"));
							map.put("place", jo.getString("place"));
							map.put("picUrl",jo.getString("picUrl"));
							list.add(map);
						}
						MyPeixunAdapter adapter=new MyPeixunAdapter(MyPeixunActivity.this, list);
						lv_list_info.setAdapter(adapter);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Diaoxian.showerror(MyPeixunActivity.this, e.getMessage());
					}
				}else{
					Diaoxian.showerror(MyPeixunActivity.this, msg.obj.toString());
				}
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("page", 1);
				map.put("pageSize", 99);
				Message msg=new Message();
				
				String result=HttpUse.messageget("TrainACt", "myTrainActList", map);
				try {
					JSONObject jo=new JSONObject(result);
					if(jo.getBoolean("result")){
						msg.what=1;
						msg.obj=jo.getString("data");
					}else{
						msg.obj=jo.getString("message");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					msg.obj=e.getMessage();
				}
				
				handler.sendMessage(msg);
			};
		}.start();
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if(resultCode == RESULT_OK){
				Bundle bundle = data.getExtras();
				//显示扫描到的内容
				String result=bundle.getString("result");
				try {
					JSONObject jo=new JSONObject(result);
					id=jo.getString("trainID");
					date=jo.getString("att_date");
					time=jo.getInt("att_type");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//显示
//				mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
				shaoma();
			}
			break;
		}
    }	
	
	private void shaoma(){
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(msg.what==1){
					Diaoxian.showerror(MyPeixunActivity.this, "签到成功");
				}else{
					Diaoxian.showerror(MyPeixunActivity.this, msg.obj.toString());
				}
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("trainID", id);
				map.put("att_date", date);
				map.put("att_type", time);
				
				Message msg=new Message();
				
				String result=HttpUse.messageget("PersonalCenter", "sweepAttendance", map);
				
				JSONObject jo;
				try {
					jo = new JSONObject(result);
					if(jo.getBoolean("result")){
						msg.what=1;
						
					}
					msg.obj=jo.getString("message");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					msg.obj=e.getMessage();
				}
				
				
				handler.sendMessage(msg);
			}
		}.start();
	}
}

