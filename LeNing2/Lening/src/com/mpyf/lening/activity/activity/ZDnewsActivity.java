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
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ZDnewsActivity extends Activity {

	private LinearLayout ll_zd_news_back;
	private ListView lv_zd_news;
	private List<Map<String, Object>> list;
	private JSONObject jo;
	private  TextView tv_messageitem_time,tv_messageitem_context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_zdnews);
		init();
		getdata();
		addlistenr();
	}

	private void init() {
		ll_zd_news_back = (LinearLayout) findViewById(R.id.ll_zd_news_back);
		lv_zd_news = (ListView) findViewById(R.id.lv_zd_news);
		tv_messageitem_time=(TextView) findViewById(R.id.tv_messageitem_time);
		tv_messageitem_context=(TextView) findViewById(R.id.tv_messageitem_context);
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
							jo = ja.getJSONObject(i);
							map.put("id", jo.getString("PK_msg"));
							map.put("title", jo.getString("msg_NAME"));
							map.put("time", jo.getString("msg_NAME") + "    "
									+ jo.getString("msg_Time"));
							map.put("context", jo.getString("msg_CONTENT"));
							list.add(map);
						}
						SimpleAdapter adapter = new SimpleAdapter(ZDnewsActivity.this, list,
								R.layout.item_message, new String[] { "time",
										"context" }, new int[] {
										R.id.tv_messageitem_time,
										R.id.tv_messageitem_context });
						lv_zd_news.setAdapter(adapter);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					Diaoxian.showerror(ZDnewsActivity.this, msg.obj.toString());
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
				String result = HttpUse.messageget("QueAndAns",
						"findUserQAmessage", map);
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

	private void addlistenr() {
		ListenerServer.setfinish(this, ll_zd_news_back);
		lv_zd_news.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent=new Intent(ZDnewsActivity.this,ZDnewsInfoActivity.class);
				intent.putExtra("title", list.get(arg2).get("title").toString());
				intent.putExtra("context", list.get(arg2).get("context").toString());
				startActivity(intent);
			}
		});
	}
}
