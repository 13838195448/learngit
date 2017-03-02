package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.activity.adapter.LecherAdapter;
import com.mpyf.lening.activity.adapter.MingshiAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;

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

public class MingshiActivity extends Activity {

	private LinearLayout ll_list_back;
	private TextView tv_list_title;
	private ListView lv_list_info;
	private List<Map<String,Object>> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	//	new Xiaoxibeijing().changetop(this, R.color.main);
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
		tv_list_title.setText("名师风采");
		
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub

				if (msg.what == 1) {
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						list = new ArrayList<Map<String, Object>>();
						for (int i = 0; i < ja.length(); i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							JSONObject jo = ja.getJSONObject(i);
							map.put("id", jo.getString("PK_Lec"));
							map.put("name", jo.getString("lec_Name"));
							map.put("picUrl", jo.getString("picUrl"));
							map.put("Lec_Level", jo.getInt("lec_Level"));
							map.put("context", jo.getString("remark"));
							map.put("signature", jo.getString("signature"));
							
							list.add(map);
						}

					} catch (JSONException e) {
						Diaoxian.showerror(MingshiActivity.this, e.getMessage());
					}

					MingshiAdapter adapterremen = new MingshiAdapter(
							MingshiActivity.this, list);
					lv_list_info.setAdapter(adapterremen);
				}else{
					Diaoxian.showerror(MingshiActivity.this, msg.obj.toString());
				}
			
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("page", 1);
				map.put("pageSize", 999);
				Message msg = new Message();
				String result = HttpUse.messageget("Account",
						"listLecturer", map);
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = jo.getString("data");
					} else {
						msg.obj = jo.getString("message");
					}
				} catch (JSONException e) {
					msg.obj=e.getMessage();
				}
				handler.sendMessage(msg);
			
			}
		}.start();
		
	}
	
	private void addlistener(){
		ListenerServer.setfinish(MingshiActivity.this, ll_list_back);
		
		lv_list_info.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MingshiActivity.this,MingshidetilActivity.class);
				intent.putExtra("id", list.get(arg2).get("id").toString());
				startActivity(intent);
			}
		});
	}
	
}
