package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.activity.adapter.ZhuantiAdapter;
import com.mpyf.lening.interfaces.bean.Result.Project;
import com.mpyf.lening.interfaces.http.HttpUse;

public class ZhuantiActivity extends Activity {
	
	private TextView tv_list_title;
	private LinearLayout ll_list_back;
	private ListView lv_list_info;
	private ArrayList<Project> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//new Xiaoxibeijing().changetop(this, R.color.main);
		setContentView(R.layout.activity_list);
		init();
		showinfo();
		addlistener();
	}
	
	private void init(){
		tv_list_title=(TextView) findViewById(R.id.tv_list_title);
		ll_list_back=(LinearLayout) findViewById(R.id.ll_list_back);
		lv_list_info= (ListView) findViewById(R.id.lv_list_info);
	}
	
	private void showinfo(){
		tv_list_title.setText("×¨Ìâ");
	//	ZhuantiAdapter adapter = new ZhuantiAdapter(ZhuantiActivity.this, list);
	//	lv_list_info.setAdapter(adapter);
	
		final Handler handler =	new Handler(){
			

			@Override
			public void handleMessage(Message msg) {

				if(msg.what==1){
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						list = new ArrayList<Project>();
						for (int i = 0; i < ja.length(); i++) {
							JSONObject jo = ja.getJSONObject(i);
							Project project = new Project();
							project.PK_Pro = jo.getString("PK_Pro");
							project.isExam = jo.getBoolean("isExam");
							project.pro_pic_url=jo.getString("pro_pic_url");
							project.proInfo_pic_url = jo.getString("proInfo_pic_url");
							project.remark = jo.getString("remark");
							project.pro_Name = jo.getString("pro_Name");
							list.add(project);
						}
						
						ZhuantiAdapter adapter = new ZhuantiAdapter(ZhuantiActivity.this, list);
						lv_list_info.setAdapter(adapter);
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				HashMap<String,Object> map = new HashMap<String, Object>();
				map.put("page", 1);
				map.put("pageSize", 999);
				String result = HttpUse.messageget("CourseStudy", "getProjectList", map);
				
			//	Log.i("TAG", result);
				Message msg = Message.obtain();
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = jo.getString("data");
					} else { 
						msg.obj = jo.getString("message");
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				handler.sendMessage(msg);
			};
		}.start();
	}
	
	

	private void addlistener(){
		ListenerServer.setfinish(ZhuantiActivity.this, ll_list_back);
		lv_list_info.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Intent intent = new Intent(ZhuantiActivity.this,ZhuantiDetailActivity.class);
				intent.putExtra("PK_Pro", list.get(arg2).PK_Pro );
				startActivity(intent);
			}
			
		});
	}
}
