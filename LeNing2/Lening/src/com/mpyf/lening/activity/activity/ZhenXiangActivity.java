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
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.activity.adapter.KcflAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ZhenXiangActivity extends Activity {

	private LinearLayout ll_zd_zhenxiang_back;
	private ListView lv_zhenxiang_info;
	private GridView gv_zhenxiang_info;

	private List<Map<String, Object>> listtitle;
	private List<Map<String, Object>> listdetil;
	private String parentid = "";
	private String title = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_zhenxiang);
		init();
		getdate();
		addlistenr();

	}

	private void init() {
		ll_zd_zhenxiang_back = (LinearLayout) findViewById(R.id.ll_zd_zhenxiang_back);
		lv_zhenxiang_info = (ListView) findViewById(R.id.lv_zhenxiang_info);
		gv_zhenxiang_info = (GridView) findViewById(R.id.gv_zhenxiang_info);

		listtitle = new ArrayList<Map<String, Object>>();
	}

	private void getdate() {

		final Dialog dialog = MyDialog.MyDialogloading(this);
		dialog.show();
		final Map<String, Object> map = new HashMap<String, Object>();

		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				dialog.dismiss();
				if (msg.what == 1) {
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						for (int i = 0; i < ja.length(); i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							JSONObject jo = ja.getJSONObject(i);
							map.put("id", jo.getString("label_id"));
							map.put("title", jo.getString("label_name"));
							listtitle.add(map);
						}
						KcflAdapter adapter = new KcflAdapter(
								ZhenXiangActivity.this, listtitle);
						lv_zhenxiang_info.setAdapter(adapter);

						if(listtitle.size() > 0){
						defaultshow();}

					} catch (JSONException e) {
						Diaoxian.showerror(ZhenXiangActivity.this,
								e.getMessage());
					}

				} else {
					Diaoxian.showerror(ZhenXiangActivity.this,
							msg.obj.toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				String result = HttpUse.messageget("QueAndAns",
						"getRootTruthLabel", map);
//				System.out.println("===获取真相问答一级分类标签===" + result);
				Message msg = new Message();
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = jo.getString("data");
					} else {
						msg.obj = jo.getString("message");
					}
				} catch (JSONException e) {
					//这是子线程，不能弹toast
					msg.obj = e.getMessage();
				}
				handler.sendMessage(msg);
			};
		}.start();

	}

	private void defaultshow() {
		title = listtitle.get(0).get("title").toString();
		gvgetdata(0);
	}

	private void addlistenr() {
		// 返回键
		ListenerServer.setfinish(ZhenXiangActivity.this, ll_zd_zhenxiang_back);

		lv_zhenxiang_info.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				for (int i = 0; i < lv_zhenxiang_info.getChildCount(); i++) {
					View view = lv_zhenxiang_info.getChildAt(i);
					ImageView iv_kcfl_make = (ImageView) view
							.findViewById(R.id.iv_kcfl_make);
					TextView tv_kcfl_title = (TextView) view
							.findViewById(R.id.tv_kcfl_title);
					iv_kcfl_make.setBackgroundColor(getResources().getColor(
							R.color.dise));
					tv_kcfl_title.setBackgroundColor(getResources().getColor(
							R.color.dise));
				}
				View view = lv_zhenxiang_info.getChildAt(arg2);
				ImageView iv_kcfl_make = (ImageView) view
						.findViewById(R.id.iv_kcfl_make);
				TextView tv_kcfl_title = (TextView) view
						.findViewById(R.id.tv_kcfl_title);
				iv_kcfl_make.setBackgroundColor(getResources().getColor(
						R.color.main));
				tv_kcfl_title.setBackgroundColor(getResources().getColor(
						android.R.color.white));
				title = listtitle.get(arg2).get("title").toString();
				gvgetdata(arg2);
			}
		});

		gv_zhenxiang_info.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// 请求网络，是否可以答题

				final Handler handler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						if (msg.what == 2) {
							boolean data = (Boolean) msg.obj;
							// 这里if(data)相当于if(data == true)；else{}相当于else
							// if(data == false)
//							if (data) {
//								/**
//								 * 可以答题，跳到答题界面
//								 */
								Intent intent = new Intent(
										ZhenXiangActivity.this,
										StartZxActivity.class);
								intent.putExtra("sn_aux", listdetil.get(arg2)
										.get("sn_aux").toString());
								intent.putExtra("labelId", listdetil.get(arg2)
										.get("id").toString());
								startActivity(intent);
//							} else {
//								Toast.makeText(ZhenXiangActivity.this,
//										"你今天已经错误三次了，请稍后再来答题!", Toast.LENGTH_SHORT)
//										.show();
//							}
						} else {
							Toast.makeText(ZhenXiangActivity.this,
									msg.obj.toString(), Toast.LENGTH_SHORT)
									.show();
						}
					}
				};

				new Thread() {
					public void run() {
						Map<String, Object> map = new HashMap<String, Object>();

						Message msg = new Message();
						String result = HttpUse.messageget("QueAndAns",
								"iscCan", map);
//						System.out.println("=====查询是否可以答题====" + result);

						try {
							JSONObject jo = new JSONObject(result);
							if (jo.getBoolean("result")) {
								msg.what = 2;
								msg.obj = jo.getBoolean("data");
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
		});

	}

	private void gvgetdata(final int index) {
		listdetil = new ArrayList<Map<String, Object>>();

		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						for (int i = 0; i < ja.length(); i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							JSONObject jo = ja.getJSONObject(i);
							map.put("id", jo.getString("label_id"));
							map.put("title", jo.getString("label_name"));
							map.put("sn_aux", jo.getString("sn_aux"));
							listdetil.add(map);
						}
						SimpleAdapter adapter = new SimpleAdapter(
								ZhenXiangActivity.this, listdetil,
								R.layout.item_kcflxx, new String[] { "title" },
								new int[] { R.id.tv_kcflxx_title });
						gv_zhenxiang_info.setAdapter(adapter);
					} catch (JSONException e) {
						Diaoxian.showerror(ZhenXiangActivity.this,
								e.getMessage());
					}

				}
			
			}
		};

		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("labelId", listtitle.get(index).get("id").toString());
				parentid = listtitle.get(index).get("id").toString();

				String result = HttpUse.messageget("QueAndAns",
						"getTruthLabel", map);
//				System.out.println("eeee获取真相问答子标签ccccc" + result);
				Message msg = new Message();
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = jo.getString("data");
					} else {
						msg.obj = jo.getString("message");
					}
				} catch (JSONException e) {
					Diaoxian.showerror(ZhenXiangActivity.this, e.getMessage());
				}
				handler.sendMessage(msg);
			};
		}.start();

	}

}
