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
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.interfaces.http.HttpUse;

public class MessageActivity extends Activity {

	private LinearLayout ll_fenlei_back;
	private TextView tv_fenlei_title;
	private ListView lv_fenlei_info;
	private View view_fenlei_top;
	private GridView gv_fenlei_info;
	private List<Map<String, Object>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// new Xiaoxibeijing().changetop(MessageActivity.this, R.color.main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_fenlei);
		init();
		getdata();
		addlistenr();
	}

	private void init() {
		ll_fenlei_back = (LinearLayout) findViewById(R.id.ll_fenlei_back);
		tv_fenlei_title = (TextView) findViewById(R.id.tv_fenlei_title);
		lv_fenlei_info = (ListView) findViewById(R.id.lv_fenlei_info);
		view_fenlei_top = findViewById(R.id.view_fenlei_top);
		gv_fenlei_info = (GridView) findViewById(R.id.gv_fenlei_info);

		gv_fenlei_info.setVisibility(View.GONE);

		tv_fenlei_title.setText("ÏûÏ¢");

	}

	private void addlistenr() {
		ListenerServer.setfinish(this, ll_fenlei_back);

		lv_fenlei_info.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MessageActivity.this,
						MessageinfoActivity.class);
				intent.putExtra("id", list.get(arg2).get("id").toString());
				startActivity(intent);
			}
		});

	}

	private void getdata() {
		list = new ArrayList<Map<String, Object>>();

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 1) {
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						for (int i = 0; i < ja.length(); i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							JSONObject jo = ja.getJSONObject(i);
							map.put("id", jo.getString("PK_msg"));
							map.put("title", jo.getString("msg_NAME"));
							map.put("time", jo.getString("msg_NAME") + "    "
									+ jo.getString("msg_Time"));
							map.put("context", jo.getString("msg_CONTENT"));
							list.add(map);
						}
						SimpleAdapter adapter = new SimpleAdapter(
								MessageActivity.this, list,
								R.layout.item_message, new String[] { "time",
										"context" }, new int[] {
										R.id.tv_messageitem_time,
										R.id.tv_messageitem_context });
						lv_fenlei_info.setAdapter(adapter);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					Diaoxian.showerror(MessageActivity.this, msg.obj.toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("page", 1);
				map.put("pageSize", 999);
				Message msg = new Message();
				String result = HttpUse.messageget("PersonalCenter",
						"myMessages", map);

				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = jo.getString("data");
					} else {
						msg.obj = jo.getString("message");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					msg.obj = e.getMessage();
				}

				handler.sendMessage(msg);
			}

		}.start();
	}
}
