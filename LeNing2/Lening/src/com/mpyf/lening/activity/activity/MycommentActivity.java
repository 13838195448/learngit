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
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.Quanjubianliang;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.activity.adapter.MyCommentAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class MycommentActivity extends Activity {

	private LinearLayout ll_list_back;
	private TextView tv_list_title;
	private ListView lv_list_info;
	private List<Map<String,Object>> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//new Xiaoxibeijing().changetop(this, R.color.main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_list);
		init();
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		showinfo();
		addlistener();
	}
	
	private void init(){
		ll_list_back=(LinearLayout) findViewById(R.id.ll_list_back);
		tv_list_title=(TextView) findViewById(R.id.tv_list_title);
		lv_list_info=(ListView) findViewById(R.id.lv_list_info);
	}
	private void showinfo(){
		tv_list_title.setText("我的评论");
		
		list=new ArrayList<Map<String,Object>>();
		
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==1){
					try {
						JSONArray ja=new JSONArray(msg.obj.toString());
						for(int i=0;i<ja.length();i++){
							JSONObject jo=ja.getJSONObject(i);
							Map<String,Object> map=new HashMap<String, Object>();
							
							map.put("id", jo.getString("PK_Com"));
							map.put("PK_Course", jo.getString("PK_Course"));
							map.put("PK_Res", jo.getString("PK_Res"));
							map.put("name", jo.getString("nickname"));
							
							map.put("time", jo.getString("commTime"));
							
							map.put("context", jo.getString("COMMENT_CONTENT"));
							map.put("from", "来自课件： "+jo.getString("resName"));
							map.put("srcoe", jo.getInt("COMMENT_LEVEL"));
							map.put("ImagePath",  Setting.apiUrl+"new-pages/PersonalPhoto/"+Setting.currentUser.getPk_user()+".jpg");
							list.add(0,map);
						}
						
						MyCommentAdapter adapter=new MyCommentAdapter(MycommentActivity.this, list);
						lv_list_info.setAdapter(adapter);
					} catch (JSONException e) {
						Diaoxian.showerror(MycommentActivity.this, e.getMessage());
					}
				}else{
					Diaoxian.showerror(MycommentActivity.this, msg.obj.toString());
				}
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("page",1);
				map.put("pageSize",999);
				
				Message msg=new Message();
				String result=HttpUse.messageget("CourseStudy", "myComments", map);
				try {
					JSONObject jo=new JSONObject(result);
					if(jo.getBoolean("result")){
						msg.what=1;
						msg.obj=jo.getString("data");
					}else{
						msg.obj=jo.getString("message");
					}
				} catch (JSONException e) {
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
				Quanjubianliang.courseid=list.get(arg2).get("PK_Course").toString();
				Intent intent=new Intent(MycommentActivity.this, AddcommentActivity.class);
				intent.putExtra("id", list.get(arg2).get("id").toString());
				intent.putExtra("resId", list.get(arg2).get("PK_Res").toString());
				intent.putExtra("srcoe", (Integer)list.get(arg2).get("srcoe"));
				intent.putExtra("context", list.get(arg2).get("context").toString());
				startActivity(intent);
			}
		});
	}
}
