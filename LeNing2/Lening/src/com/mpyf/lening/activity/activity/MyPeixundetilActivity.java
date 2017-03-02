package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.activity.adapter.QiandaoinfoAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;

public class MyPeixundetilActivity extends Activity {

	private LinearLayout ll_list_back;
	private View view_list_top;
	private TextView tv_list_title;
	private ListView lv_list_info;
	private List<Map<String,Object>> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		new Xiaoxibeijing().changetop(this, R.color.main);
		setContentView(R.layout.activity_list);
		init();
		showinfo();
		addlistener();
	}
	
	private void init(){
		ll_list_back=(LinearLayout) findViewById(R.id.ll_list_back);
		view_list_top=findViewById(R.id.view_list_top);
		tv_list_title=(TextView) findViewById(R.id.tv_list_title);
		lv_list_info=(ListView) findViewById(R.id.lv_list_info);
	}
	
	private void showinfo(){

		tv_list_title.setText("≈‡—µ");
		view_list_top.setVisibility(View.GONE);
		
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(msg.what==1){
					try {
						list=new ArrayList<Map<String,Object>>();
						JSONArray ja=new JSONArray(msg.obj.toString());
						for (int i = 0; i < ja.length(); i++) {
							JSONObject jo=ja.getJSONObject(i);
							Map<String,Object> map=new HashMap<String, Object>();
							map.put("att_date", jo.getString("att_date"));
							map.put("att_type", jo.getInt("att_type"));
							map.put("att_ance", jo.getInt("att_ance"));
							map.put("att_datetime", jo.getString("att_datetime"));
							map.put("att_datetime_s", jo.getString("att_datetime_s"));
							map.put("att_datetime_e", jo.getString("att_datetime_e"));
							list.add(map);
						}
						
						QiandaoinfoAdapter adapter=new QiandaoinfoAdapter(getApplicationContext(), list);
						lv_list_info.setAdapter(adapter);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Diaoxian.showerror(getApplicationContext(), e.getMessage());
					}
				}else{
					Diaoxian.showerror(getApplicationContext(), msg.obj.toString());
				}
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("trainID", getIntent().getStringExtra("id"));
				Message msg=new Message();
				String result=HttpUse.messageget("PersonalCenter", "attendanceInfo", map);
				try {
					JSONObject jo=new JSONObject(result);
					if (jo.getBoolean("result")) {
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
		ListenerServer.setfinish(this, ll_list_back);
	}
}
