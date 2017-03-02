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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.activity.adapter.MyinterestAdapter;
import com.mpyf.lening.interfaces.bean.Parame.UserLabel;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

/**
 * 我感兴趣的标签
 * 
 * @author s
 * 
 */
public class MyInterestActivity extends Activity {

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				// 所有标签接口返回数据
				try {
					JSONArray ja = new JSONArray(msg.obj.toString());
					list.clear();
					for (int i = 0; i < ja.length(); i++) {
						Map<String, String> map = new HashMap<String, String>();
						JSONObject jo = ja.getJSONObject(i);
						map.put("label_id", jo.getString("label_id"));
						map.put("label_name", jo.getString("label_name"));
						list.add(map);
					}
					adapter.notifyDataSetChanged();

					requestUserLabel();

				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else if (msg.what == 2) {
				// 用户标签接口返回数据
				try {
					JSONObject ja = new JSONObject(msg.obj.toString());
					// 没有已选标签
					if (ja.getInt("label_num") == 0) {
						return;
					}
					list1.clear();
					ArrayList<Map<String, String>> tempList = new ArrayList<Map<String, String>>();
					tempList.addAll(list);

					for (int i = 0; i < ja.getInt("label_num"); i++) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("label_id",
								ja.getString("label" + (i + 1) + "_id"));
						map.put("label_name",
								ja.getString("label" + (i + 1) + "_name"));
						list1.add(map);

						for (int j = 0; j < list.size(); j++) {
							Map<String, String> m = list.get(j);
							if (ja.getString("label" + (i + 1) + "_id").equals(
									m.get("label_id"))) {
								tempList.remove(j);
							}
						}

					}
					list.clear();
					list.addAll(tempList);

					adapter.notifyDataSetChanged();
					adapter1.notifyDataSetChanged();

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	};

	private LinearLayout ll_zd_myinterest_back;
	private GridView gv_yixuan_biaoqian, gv_suoyou_biaoqian;
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> list1 = new ArrayList<Map<String, String>>();
	private MyinterestAdapter adapter = new MyinterestAdapter(list,
			MyInterestActivity.this);
	private MyinterestAdapter adapter1 = new MyinterestAdapter(list1,
			MyInterestActivity.this);;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_myinterest);
		init();
		showinfo();
		addlistener();
	}

	private void init() {
		/**
		 * 返回键
		 */
		ll_zd_myinterest_back = (LinearLayout) findViewById(R.id.ll_zd_myinterest_back);
		gv_suoyou_biaoqian = (GridView) findViewById(R.id.gv_suoyou_biaoqian);
		gv_yixuan_biaoqian = (GridView) findViewById(R.id.gv_yixuan_biaoqian);

		gv_yixuan_biaoqian.setAdapter(adapter1);
	}

	private void showinfo() {

		requestAllLabel();

	}

	private void requestAllLabel() {

		new Thread() {
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				Message msg = new Message();
				String result = HttpUse.messageget("QueAndAns", "findLabel",
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
			};
		}.start();
	}

	private void requestUserLabel() {
		new Thread() {
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", Setting.currentUser.getPk_user());// 传参userId
				String result = HttpUse.messageget("QueAndAns", "getUserLabel",
						map);
				Message msg = new Message();
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 2;
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

	/**
	 * 设置监听方法
	 */
	private void addlistener() {
		/**
		 * 返回键的监听
		 */
		ListenerServer
				.setfinish(MyInterestActivity.this, ll_zd_myinterest_back);
		gv_suoyou_biaoqian.setAdapter(adapter);
		gv_yixuan_biaoqian.setAdapter(adapter1);
		gv_suoyou_biaoqian.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (list1.size() >= 3) {
					Toast.makeText(MyInterestActivity.this, "最多只能选择三条",
							Toast.LENGTH_SHORT).show();
					return;
				}
				list1.add(list.get(arg2));
				list.remove(arg2);
				adapter1.notifyDataSetChanged();
				adapter.notifyDataSetChanged();
			}

		});
		gv_yixuan_biaoqian.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				list.add(list1.get(arg2));
				list1.remove(arg2);
				adapter1.notifyDataSetChanged();
				adapter.notifyDataSetChanged();
			}
		});

	}

	private void saveUserLabel() {
		// 退出时保存标签
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					Diaoxian.showerror(MyInterestActivity.this, getIntent()
							.getStringExtra("id") == null ? "保存成功" : "修改成功");
					finish();
				} else {
					Diaoxian.showerror(MyInterestActivity.this,
							msg.obj.toString());
				}

			}
		};

		new Thread() {

			public void run() {
				Message msg = new Message();
				UserLabel userLabel = new UserLabel();
				userLabel.setUserId(Setting.currentUser.getPk_user());
				userLabel.setLabel_num(list1.size());
				userLabel.setLabel1_id(list1.size() > 0 ? list1.get(0).get(
						"label_id") : "");
				userLabel.setLabel2_id(list1.size() > 1 ? list1.get(1).get(
						"label_id") : "");
				userLabel.setLabel3_id(list1.size() > 2 ? list1.get(2).get(
						"label_id") : "");
				userLabel.setLabel1_name(list1.size() > 0 ? list1.get(0).get(
						"label_name") : "");
				userLabel.setLabel2_name(list1.size() > 1 ? list1.get(1).get(
						"label_name") : "");
				userLabel.setLabel3_name(list1.size() > 2 ? list1.get(2).get(
						"label_name") : "");
				String result = HttpUse.messagepost("QueAndAns",
						"saveUserLabel", userLabel);
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
					}
					msg.obj = jo.getString("message");

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					msg.obj = e.getMessage();
				}
				handler.sendMessage(msg);
			};
		}.start();
	}

	@Override
	protected void onStop() {
		super.onStop();
		saveUserLabel();
	}

}
