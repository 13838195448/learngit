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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.Quanjubianliang;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.interfaces.http.HttpUse;

public class MynotedetilActivity extends Activity {

	private TextView tv_list_title;
	private ListView lv_list_info;
	private LinearLayout ll_list_back;
	private List<Map<String,Object>> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		new Xiaoxibeijing().changetop(this, R.color.main);
		setContentView(R.layout.activity_list);
		init();
		
		
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
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
		tv_list_title.setText(getIntent().getStringExtra("title"));
		
		final String[] from={"title","context","from","time"};
		final int[] to={R.id.tv_buyednote_title,R.id.tv_buyednote_context,R.id.tv_buyednote_from,R.id.tv_buyednote_time};

		list = new ArrayList<Map<String, Object>>();
		
		final Dialog dialog=MyDialog.MyDialogloading(this);
		dialog.show();
		
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(msg.what==1){
					dialog.dismiss();
					try {
						JSONArray ja=new JSONArray(msg.obj.toString());
						for(int i=0;i<ja.length();i++){
							Map<String,Object> map=new HashMap<String, Object>();
							JSONObject jo=ja.getJSONObject(i);
							map.put("id", jo.getString("PK_Note"));
							map.put("PK_Course", jo.getString("PK_Course"));
							map.put("PK_Res", jo.getString("PK_Res"));
							map.put("title", jo.getString("NOTE_TITLE"));
							map.put("context", jo.getString("NOTE_CONTENT"));
							map.put("from", "À´×Ô¿Î¼þ£º  "+jo.getString("res_Name"));
							map.put("time", jo.getString("NOTE_Time"));
							list.add(map);
						}
						SimpleAdapter adapter=new SimpleAdapter(MynotedetilActivity.this,list,R.layout.item_buyednote,from,to);
						lv_list_info.setAdapter(adapter);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Diaoxian.showerror(MynotedetilActivity.this, e.getMessage());
					}
				}else{
					Diaoxian.showerror(MynotedetilActivity.this, msg.obj.toString());
				}
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("courseId", Quanjubianliang.courseid);
				
				Message msg=new Message();
				
				String result=HttpUse.messageget("CourseStudy", "courseNote", map);
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
				Intent intent=new Intent(MynotedetilActivity.this, WatchnoteActivity.class);
				intent.putExtra("id", list.get(arg2).get("id").toString());
				intent.putExtra("title", list.get(arg2).get("title").toString());
				intent.putExtra("context", list.get(arg2).get("context").toString());
				intent.putExtra("from", list.get(arg2).get("from").toString());
				intent.putExtra("time", list.get(arg2).get("time").toString());
				intent.putExtra("PK_Course", list.get(arg2).get("PK_Course").toString());
				intent.putExtra("PK_Res", list.get(arg2).get("PK_Res").toString());
				startActivity(intent);
			}
		});
	}
}
