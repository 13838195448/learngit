package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.activity.adapter.PeixunAdapter;
import com.mpyf.lening.activity.adapter.RmCourseAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.graphics.Bitmap;
public class PeixunActivity extends Activity {

	private TextView tv_fenleidelio_title;
	private LinearLayout ll_fenleidelio_back,ll_fenleidelio_top,ll_fenleideilo_up;
	private RelativeLayout rl_fenleidelio_seek;
	private ListView lv_fenleidelio_top;
	private ImageView iv_fenleidelio_seek;
	private EditText et_fenleidelio_seek;
	private List<Map<String,Object>> list;
	private String result="";
	private Handler handler;
	private static Timer timer;
	private DisplayImageOptions options;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		new Xiaoxibeijing().changetop(this, R.color.main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_fenleidelio);
		init();
		showinfo();
		showdata();
		addlistener();
	}
	private void init(){
		tv_fenleidelio_title=(TextView) findViewById(R.id.tv_fenleidelio_title);
		rl_fenleidelio_seek=(RelativeLayout) findViewById(R.id.rl_fenleidelio_seek);
		iv_fenleidelio_seek=(ImageView) findViewById(R.id.iv_fenleidelio_seek);
		et_fenleidelio_seek=(EditText) findViewById(R.id.et_fenleidelio_seek);
		ll_fenleidelio_back=(LinearLayout) findViewById(R.id.ll_fenleidelio_back);
		ll_fenleidelio_top=(LinearLayout) findViewById(R.id.ll_fenleidelio_top);
		ll_fenleideilo_up=(LinearLayout) findViewById(R.id.ll_fenleideilo_up);
		lv_fenleidelio_top=(ListView) findViewById(R.id.lv_fenleidelio_top);
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.defaultimage) 
		.showImageOnFail(R.drawable.defaultimage)
		.showImageForEmptyUri(R.drawable.defaultimage)
		.cacheInMemory(true) 
		.cacheOnDisk(true) 
		 .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
        .bitmapConfig(Bitmap.Config.ALPHA_8)
		.build(); 
		
	}
	
	private void showinfo(){
		tv_fenleidelio_title.setText("≈‡—µ");
		ll_fenleidelio_top.setVisibility(View.GONE);
		ll_fenleideilo_up.setVisibility(View.GONE);
	}
	
	private void showdata(){
		
		
		list = new ArrayList<Map<String, Object>>();
		 handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(msg.what==1){
					try {
						JSONArray ja=new JSONArray(msg.obj.toString());
						for(int i=0;i<ja.length();i++){
							Map<String,Object> map=new HashMap<String, Object>();
							JSONObject jo=ja.getJSONObject(i);
							map.put("id", jo.getString("PK_MainAct"));
							map.put("title", jo.getString("act_Name"));
							map.put("date", jo.getString("start_Time").replace("-", "/")+"--"+jo.getString("end_Time").replace("-", "/"));
							map.put("time", jo.getString("bm_End_Time"));
							map.put("Application_Type", jo.getInt("application_Type"));
							map.put("le", jo.getString("application_Fee"));
							map.put("member", jo.getString("seats"));
							map.put("place", jo.getString("place"));
							map.put("picUrl",jo.getString("picUrl"));
							map.put("num",jo.getString("num"));
							list.add(map);
						}
						PeixunAdapter adapter=new PeixunAdapter(PeixunActivity.this, list,options);
						lv_fenleidelio_top.setAdapter(adapter);
					} catch (JSONException e) {
						
						Diaoxian.showerror(PeixunActivity.this, e.getMessage());
					}
				}else{
					Diaoxian.showerror(PeixunActivity.this, msg.obj.toString());
				}
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("page", 1);
				map.put("pageSize", 999);
				Message msg=new Message();
				
				result=HttpUse.messageget("TrainACt", "trainActList", map);
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
	
	private void addlistener(){
		ListenerServer.setfinish(PeixunActivity.this, ll_fenleidelio_back);
		
		iv_fenleidelio_seek.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (rl_fenleidelio_seek.getVisibility()==View.INVISIBLE) {
					rl_fenleidelio_seek.setVisibility(View.VISIBLE);
					tv_fenleidelio_title.setVisibility(View.GONE);
				}else if(rl_fenleidelio_seek.getVisibility()==View.VISIBLE){
//					et_fenleidelio_seek.setVisibility(View.INVISIBLE);
//					tv_fenleidelio_title.setVisibility(View.VISIBLE);
//					showdata();
					if (et_fenleidelio_seek.getText().toString().length() == 0) {
						list=new ArrayList<Map<String, Object>>();
						PeixunAdapter adapter=new PeixunAdapter(PeixunActivity.this, list,options);
						lv_fenleidelio_top.setAdapter(adapter);
						return;
					} else {
						dosearch();
					}
				}
			}
		});
		
		et_fenleidelio_seek.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				if (arg1==KeyEvent.KEYCODE_ENTER){
					if(et_fenleidelio_seek.getText().toString().trim().equals("")){
						RmCourseAdapter adapter=new RmCourseAdapter(PeixunActivity.this, new ArrayList<Map<String,Object>>());
						lv_fenleidelio_top.setAdapter(adapter);
					}else{
						dosearch();
					}
				} 
				return false;
			}
		});
		
		lv_fenleidelio_top.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(PeixunActivity.this, PeixundetilActivity.class);
						intent.putExtra("trainID", list.get(arg2).get("id").toString());
				startActivity(intent);
			}
		});
	}
	private void getpeixun(String result){
		try {
			list=new ArrayList<Map<String,Object>>();
			JSONArray ja=new JSONArray(result);
			for(int i=0;i<ja.length();i++){
				Map<String,Object> map=new HashMap<String, Object>();
				JSONObject jo=ja.getJSONObject(i);
				map.put("id", jo.getString("PK_MainAct"));
				map.put("title", jo.getString("act_Name"));
				map.put("date", jo.getString("start_Time").replace("-", "/")+"--"+jo.getString("end_Time").replace("-", "/"));
				map.put("time", jo.getString("bm_End_Time"));
				map.put("Application_Type", jo.getInt("application_Type"));
				map.put("le", jo.getString("application_Fee"));
				map.put("member", jo.getString("seats"));
				map.put("place", jo.getString("place"));
				map.put("picUrl",jo.getString("picUrl"));
				list.add(map);
			}
			PeixunAdapter adapter=new PeixunAdapter(PeixunActivity.this, list,options);
			lv_fenleidelio_top.setAdapter(adapter);
			
			Diaoxian.showerror(PeixunActivity.this, "π≤’“µΩ"+list.size()+"œÓ");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Diaoxian.showerror(PeixunActivity.this, e.getMessage());
		}
	}
	
		private void dosearch(){
		
			handler=new Handler(){
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					if(msg.what==1){
						getpeixun(msg.obj.toString());
					}else{
						Diaoxian.showerror(PeixunActivity.this, msg.obj.toString());
					}
					
				}
			};
			
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				Message msg=new Message();
				
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("query", et_fenleidelio_seek.getText().toString());
				map.put("queryType", 2);
				map.put("page", 1);
				map.put("pageSize", 99);
				
				result=HttpUse.messageget("CourseStudy", "userSearch", map);
				
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
			}
		}.start();
	}
}

