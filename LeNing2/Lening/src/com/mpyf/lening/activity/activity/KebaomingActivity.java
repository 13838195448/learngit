package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.activity.adapter.KebaomingAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;

public class KebaomingActivity extends Activity {

	private TextView tv_list_title;
	private LinearLayout ll_list_back;
	private ListView lv_list_info;
	private List<Map<String,Object>> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	//	new Xiaoxibeijing().changetop(this,R.color.main);
		setContentView(R.layout.activity_list);
		
		init();
		showinfo();
		addlistener();
	}
	
	private void init(){
		tv_list_title=(TextView) findViewById(R.id.tv_list_title);
		ll_list_back=(LinearLayout) findViewById(R.id.ll_list_back);
		lv_list_info=(ListView) findViewById(R.id.lv_list_info);
	}
	
	private void showinfo(){
		tv_list_title.setText("选择认证报名");
		
		final Dialog dialog=MyDialog.MyDialogloading(KebaomingActivity.this);
		dialog.show();
		
		list=new ArrayList<Map<String,Object>>();
		
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				dialog.dismiss();
				if (msg.what==1) {
					try {
						JSONArray ja=new JSONArray(msg.obj.toString());
						for(int i=0;i<ja.length();i++){
							Map<String,Object> map=new HashMap<String, Object>();
							JSONObject jo=ja.getJSONObject(i);
							map.put("id", jo.getString("pk_cer"));
							map.put("type", jo.getInt("cer_type"));
							map.put("title", jo.getString("cerName"));
							map.put("name", jo.getString("remark"));
							list.add(map);
						}
						KebaomingAdapter adapter=new KebaomingAdapter(KebaomingActivity.this, list);
						lv_list_info.setAdapter(adapter);
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Diaoxian.showerror(KebaomingActivity.this, e.getMessage());
					}
					
				}else{
					Diaoxian.showerror(KebaomingActivity.this, msg.obj.toString());
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
				String result=HttpUse.messageget("AbilityCertification","getCertification", map);
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
	
	private void addlistener(){
		ListenerServer.setfinish(KebaomingActivity.this, ll_list_back);
		
		lv_list_info.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(KebaomingActivity.this, BaomingActivity.class);
				intent.putExtra("id", list.get(arg2).get("id").toString());
				intent.putExtra("name", list.get(arg2).get("title").toString());
				startActivity(intent);
			}
		});
	}
}
