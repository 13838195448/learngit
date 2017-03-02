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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.activity.adapter.RmCourseAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;

public class RmkcActivity extends Activity {

	private LinearLayout ll_fenleidelio_back, ll_fenleidelio_top,
			ll_fenleideilo_up;
	private RelativeLayout rl_fenleidelio_seek;
	private TextView tv_fenleidelio_title;
	private EditText et_fenleidelio_seek;
	private ImageView iv_fenleidelio_seek;
	private ListView lv_fenleidelio_top;

	private List<Map<String, Object>> list;
	private List<Map<String, Object>> searchlist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new Xiaoxibeijing().changetop(RmkcActivity.this, R.color.main);
		setContentView(R.layout.activity_fenleidelio);
		init();
		getdata();
		addlistenr();
	}

	private void init() {
		ll_fenleidelio_back = (LinearLayout) findViewById(R.id.ll_fenleidelio_back);
		rl_fenleidelio_seek = (RelativeLayout) findViewById(R.id.rl_fenleidelio_seek);
		tv_fenleidelio_title = (TextView) findViewById(R.id.tv_fenleidelio_title);
		et_fenleidelio_seek = (EditText) findViewById(R.id.et_fenleidelio_seek);
		iv_fenleidelio_seek = (ImageView) findViewById(R.id.iv_fenleidelio_seek);
		ll_fenleidelio_top = (LinearLayout) findViewById(R.id.ll_fenleidelio_top);
		lv_fenleidelio_top = (ListView) findViewById(R.id.lv_fenleidelio_top);
		ll_fenleideilo_up = (LinearLayout) findViewById(R.id.ll_fenleideilo_up);

		ll_fenleidelio_top.setVisibility(View.GONE);
		ll_fenleideilo_up.setVisibility(View.GONE);
		tv_fenleidelio_title.setText("热门课程");

	}

	private void addlistenr() {
		ListenerServer.setfinish(this, ll_fenleidelio_back);

		iv_fenleidelio_seek.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (rl_fenleidelio_seek.getVisibility() == View.INVISIBLE) {
					rl_fenleidelio_seek.setVisibility(View.VISIBLE);
					tv_fenleidelio_title.setVisibility(View.GONE);
				} else if (rl_fenleidelio_seek.getVisibility() == View.VISIBLE) {
					if (et_fenleidelio_seek.getText().toString().trim()
							.equals("")) {
						RmCourseAdapter adapter = new RmCourseAdapter(
								RmkcActivity.this,
								new ArrayList<Map<String, Object>>());
						lv_fenleidelio_top.setAdapter(adapter);
					} else {
						dosearch();
					}

					// RmCourseAdapter adapter=new
					// RmCourseAdapter(RmkcActivity.this, searchlist);
					// lv_fenleidelio_top.setAdapter(adapter);
					// lv_fenleidelio_top.setOnItemClickListener(new
					// OnItemClickListener() {
					//
					// @Override
					// public void onItemClick(AdapterView<?> arg0, View arg1,
					// int arg2,
					// long arg3) {
					// // TODO Auto-generated method stub
					// Intent intent=new
					// Intent(RmkcActivity.this,NotbuycourseActivity.class);
					// intent.putExtra("id",
					// list.get(arg2).get("id").toString());
					// startActivity(intent);
					// }
					// });
				}

			}
		});

		et_fenleidelio_seek.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				// TODO Auto-generated method stub
				if (arg1 == KeyEvent.KEYCODE_ENTER) {
					if (et_fenleidelio_seek.getText().toString().trim()
							.equals("")) {
						RmCourseAdapter adapter = new RmCourseAdapter(
								RmkcActivity.this,
								new ArrayList<Map<String, Object>>());
						lv_fenleidelio_top.setAdapter(adapter);
					} else {
						dosearch();
					}
				}
				return false;
			}
		});

	}

	private void dosearch() {
		searchlist = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < list.size(); i++) {
			boolean youdaxie = list
					.get(i)
					.get("title")
					.toString()
					.indexOf(
							et_fenleidelio_seek.getText().toString()
									.toUpperCase()) != -1;
			boolean youxiaoxie = list
					.get(i)
					.get("title")
					.toString()
					.indexOf(
							et_fenleidelio_seek.getText().toString()
									.toLowerCase()) != -1;
			if (youdaxie || youxiaoxie) {
				searchlist.add(list.get(i));
			}
		}
		RmCourseAdapter adapter = new RmCourseAdapter(RmkcActivity.this,
				searchlist);
		lv_fenleidelio_top.setAdapter(adapter);
		// Diaoxian.showerror(RmkcActivity.this,"共找到"+searchlist.size()+"项");
		lv_fenleidelio_top.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RmkcActivity.this,
						NotbuycourseActivity.class);
				intent.putExtra("id", searchlist.get(arg2).get("id").toString());
				startActivity(intent);
			}
		});
	}

	private void getdata() {
		list = new ArrayList<Map<String, Object>>();

		final Dialog dialog = MyDialog.MyDialogloading(this);
		dialog.show();

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				if (msg.what == 1) {
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						list = new ArrayList<Map<String, Object>>();
						for (int i = 0; i < ja.length(); i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							JSONObject jo = ja.getJSONObject(i);
							map.put("id", jo.getString("PK_Course"));
							map.put("title", jo.getString("CourseName"));
							map.put("scan", jo.getString("BuyNum"));
							map.put("cost", jo.getString("Amount"));
							map.put("PicUrl", jo.getString("PicUrl"));
							map.put("BuyWay", jo.getString("BuyWay"));
							list.add(map);
						}

						RmCourseAdapter adapter = new RmCourseAdapter(
								RmkcActivity.this, list);

						lv_fenleidelio_top.setAdapter(adapter);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Diaoxian.showerror(RmkcActivity.this, e.getMessage());
					}
					lv_fenleidelio_top
							.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									// TODO Auto-generated method stub
									Intent intent = new Intent(
											RmkcActivity.this,
											NotbuycourseActivity.class);
									intent.putExtra("id",
											list.get(arg2).get("id").toString());
									startActivity(intent);
								}
							});
				} else {
					Diaoxian.showerror(RmkcActivity.this, msg.obj.toString());
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
				String result = HttpUse.messageget("CourseStudy", "hotCourse",
						map);
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
			}
		}.start();

	}
}
