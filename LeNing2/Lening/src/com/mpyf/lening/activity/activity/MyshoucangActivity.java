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
import com.mpyf.lening.activity.adapter.PaihangbAdapter;
import com.mpyf.lening.activity.adapter.Wenda1;
import com.mpyf.lening.interfaces.http.HttpUse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MyshoucangActivity extends Activity {
	private ListView lv_list;
	private List<Map<String, Object>> list;
	private Wenda1 adapter;
	private ArrayList<Map<String, Object>> data;

	private LinearLayout ll_myshoucang_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_myshoucang);
		init();
		showinfo();
		// ·µ»Ø¼ü
		ListenerServer.setfinish(MyshoucangActivity.this, ll_myshoucang_back);

	}

	private void init() {
		lv_list = (ListView) findViewById(R.id.lv_myshoucang);
		ll_myshoucang_back = (LinearLayout) findViewById(R.id.ll_myshoucang_back);
	}

	private void showinfo() {

		list = new ArrayList<Map<String, Object>>();

		final Handler handler = new Handler() {
			private JSONObject jo;

			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						for (int i = 0; i < ja.length(); i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							jo = ja.getJSONObject(i);
							map.put("id", jo.getString("PK_Que"));
							map.put("userid", jo.getInt("pk_user"));
							map.put("UserName", jo.getString("userName"));
							map.put("Nickname", jo.getString("nickname"));
							map.put("QUE_CONTENT", jo.getString("QUE_CONTENT"));
							map.put("REWARD_WAY", jo.getInt("REWARD_WAY"));
							map.put("REWARD_Num", jo.getString("REWARD_Num"));
							map.put("Ans_Num", jo.getString("ans_Num"));
							map.put("QUE_STATE", jo.getInt("QUE_STATE"));
							map.put("PIC_NUM", jo.getInt("pic_num"));
							map.put("trueName", jo.getString("trueName"));
							map.put("honor_name", jo.getString("honor_name"));
							map.put("honor_pic", jo.getString("honor_pic"));
							map.put("isCollection", jo.getInt("isCollection"));
							list.add(map);
						}
						Wenda1 adapter = new Wenda1(MyshoucangActivity.this,
								list);
						lv_list.setAdapter(adapter);
						lv_list.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								Intent intent = new Intent(
										MyshoucangActivity.this,
										QadetilActivity.class);
								try {
									intent.putExtra("id",
											jo.getString("PK_Que"));
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								startActivity(intent);
							}
						});
					} catch (JSONException e) {
						Diaoxian.showerror(MyshoucangActivity.this,
								e.getMessage());
					}
				} else {
					Diaoxian.showerror(MyshoucangActivity.this,
							msg.obj.toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("page", 1);
				map.put("pageSize", 999);

				Message msg = new Message();

				String result = HttpUse.messageget("QueAndAns",
						"myCollectionQue", map);
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = jo.getString("data");
					} else {
						msg.obj = jo.getString("message");
					}
				} catch (JSONException e) {
					msg.obj = e.getMessage();
				}

				handler.sendMessage(msg);

			};
		}.start();

	}
}
