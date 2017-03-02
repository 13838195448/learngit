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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.Quanjubianliang;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.interfaces.http.HttpUse;

public class MynoteActivity extends Activity {

	private LinearLayout ll_list_back;
	private TextView tv_list_title;
	private ListView lv_list_info;
	private List<Map<String,Object>> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//new Xiaoxibeijing().changetop(this, R.color.main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_list);
		init();
		showinfo();
		addlistener();
	}
	private void init(){
		ll_list_back=(LinearLayout) findViewById(R.id.ll_list_back);
		tv_list_title=(TextView) findViewById(R.id.tv_list_title);
		lv_list_info=(ListView) findViewById(R.id.lv_list_info);
	}
	private void showinfo(){
		tv_list_title.setText("ÎÒµÄ±Ê¼Ç");
		
		list=new ArrayList<Map<String,Object>>();
		
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(msg.what==1){
					try {
						JSONArray ja=new JSONArray(msg.obj.toString());
						for(int i=0;i<ja.length();i++){
							JSONObject jo=ja.getJSONObject(i);
							Map<String,Object> map=new HashMap<String, Object>();
							map.put("PK_Course", jo.get("PK_Course"));
							map.put("title", jo.getString("course_Name"));
							map.put("num", jo.getString("note_Num"));
							list.add(map);
						}
						SimpleAdapter adapter=new SimpleAdapter(MynoteActivity.this, list, R.layout.item_note,new String[]{"title","num"},new int[]{R.id.tv_itemnote_title,R.id.tv_itemnote_num});
						lv_list_info.setAdapter(adapter);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Diaoxian.showerror(MynoteActivity.this, e.getMessage());
					}
				}else{
					Diaoxian.showerror(MynoteActivity.this, msg.obj.toString());
				}
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("page", 1);
				map.put("pageSize", 9999);
				
				Message msg=new Message();
				
				String result=HttpUse.messageget("CourseStudy", "myCourseNote", map);
				
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
		ListenerServer.setfinish(this, ll_list_back);
		
		lv_list_info.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MynoteActivity.this,MynotedetilActivity.class);
				intent.putExtra("title", list.get(arg2).get("title").toString());
				Quanjubianliang.courseid=list.get(arg2).get("PK_Course").toString();
				startActivity(intent);
			}
		});
	}
}
