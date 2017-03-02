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
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.interfaces.http.HttpUse;

public class NLRZScanActivity extends Activity {
	private LinearLayout ll_list_back;
	private ListView lv_list_info;
	private TextView tv_list_title;
	private String[] titles = { "报名查询", "成绩查询", "评审查询", "证书查询" };
	private int search = 0;
	private List<Map<String, Object>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//new Xiaoxibeijing().changetop(NLRZScanActivity.this, R.color.main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_list);
		init();
		showinfo();
		addlistenr();

	}

	private void init() {

		search = getIntent().getIntExtra("Scan", 0);

		ll_list_back = (LinearLayout) findViewById(R.id.ll_list_back);
		lv_list_info = (ListView) findViewById(R.id.lv_list_info);
		tv_list_title = (TextView) findViewById(R.id.tv_list_title);
		tv_list_title.setText(titles[search]);

	}

	private void addlistenr() {
		ListenerServer.setfinish(this, ll_list_back);
	}

	private void showinfo() {
		switch (search) {
		case 0:
			scanbaoming();
			break;
		case 1:
			scanchengji();
			break;
		case 2:
			scanpingshen();
			break;
		case 3:
			scanzhengshu();
			break;

		default:
			break;
		}
	}

	/**
	 * 报名查询
	 */
	private void scanbaoming() {

		final String[] zhuangtai = { "未提交", "审批中", "拒绝", "通过" };

		final Handler handler = new Handler() {
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
							map.put("id", jo.getString("PK_Cer"));
							map.put("title", jo.getString("cer_Name"));
							map.put("name", jo.getString("pk_user"));
							map.put("payed", jo.getBoolean("payInf") ? "已支付"
									: "未支付");
							map.put("tijiao", zhuangtai[jo.getInt("bmzt")]);
							list.add(map);
						}
						SimpleAdapter adapter = new SimpleAdapter(
								NLRZScanActivity.this, list,
								R.layout.item_baoming, new String[] { "title",
										"name", "payed", "tijiao" }, new int[] {
										R.id.tv_baoming_title,
										R.id.tv_baoming_name,
										R.id.tv_baoming_payed,
										R.id.tv_baoming_tijiao });
						lv_list_info.setAdapter(adapter);

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Diaoxian.showerror(NLRZScanActivity.this,
								e.getMessage());
					}
				} else {
					Diaoxian.showerror(NLRZScanActivity.this,
							msg.obj.toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("page", 1);
				map.put("pageSize", 99);
				String result = HttpUse.messageget("AbilityCertification",
						"myCertificationList", map);
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
					// TODO Auto-generated catch block
					msg.obj = e.getMessage();
				}
				handler.sendMessage(msg);
			}
		}.start();

	}

	/**
	 * 成绩查询
	 */
	private void scanchengji() {
		list = new ArrayList<Map<String, Object>>();

		final Handler handler = new Handler() {
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
							map.put("id", jo.getString("PK_Cer"));
							map.put("title", jo.getString("cer_Name"));
							map.put("name", jo.getString("trueName"));
							map.put("result", jo.getString("skillsResults"));
							map.put("level", jo.getString("RZJB"));
							list.add(map);
						}
						SimpleAdapter adapter = new SimpleAdapter(
								NLRZScanActivity.this, list,
								R.layout.item_chengji, new String[] { "title",
										"name", "result", "level" }, new int[] {
										R.id.tv_chengji_title,
										R.id.tv_chengji_name,
										R.id.tv_chengji_result,
										R.id.tv_chengji_level });
						lv_list_info.setAdapter(adapter);

						lv_list_info.setOnItemClickListener(new OnItemClickListener() {
									
									@Override
									public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
										// TODO Auto-generated method stub
										Intent intent=new Intent(NLRZScanActivity.this,ChengjiActivity.class);
										intent.putExtra("id", list.get(arg2).get("id").toString());
										startActivity(intent);
									}
								});
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Diaoxian.showerror(NLRZScanActivity.this,
								e.getMessage());
					}
				} else {
					Diaoxian.showerror(NLRZScanActivity.this,
							msg.obj.toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("page", 1);
				map.put("pageSize", 99);
				String result = HttpUse.messageget("AbilityCertification",
						"queryResults", map);
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
					// TODO Auto-generated catch block
					msg.obj = e.getMessage();
				}
				handler.sendMessage(msg);
			}
		}.start();

	}

	/**
	 * 评审查询
	 */
	private void scanpingshen() {
		list = new ArrayList<Map<String, Object>>();

		final String[] zhuangtai={"未审核","通过","未通过"};
		
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						list = new ArrayList<Map<String, Object>>();

						for (int i = 0; i < ja.length(); i++) {

							Map<String, Object> map = new HashMap<String, Object>();
							JSONObject jo = ja.getJSONObject(i);
							map.put("id", jo.getString("PK_Cer"));
							map.put("title",jo.getString("cer_Name"));
							map.put("name", jo.getString("trueName"));
							map.put("zhuangtai", zhuangtai[jo.getInt("reviewResults")]);
							map.put("nianshen", "暂无");
							list.add(map);
						}
						SimpleAdapter adapter = new SimpleAdapter(
								NLRZScanActivity.this, list,
								R.layout.item_pingshen, new String[] { "title",
										"name", "zhuangtai", "nianshen" },
								new int[] { R.id.tv_pingshen_title,
										R.id.tv_pingshen_name,
										R.id.tv_pingshen_result,
										R.id.tv_pingshen_nianshen });
						lv_list_info.setAdapter(adapter);

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Diaoxian.showerror(NLRZScanActivity.this,
								e.getMessage());
					}
				} else {
					Diaoxian.showerror(NLRZScanActivity.this,
							msg.obj.toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("page", 1);
				map.put("pageSize", 99);
				String result = HttpUse.messageget("AbilityCertification",
						"queryReview", map);
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
					// TODO Auto-generated catch block
					msg.obj = e.getMessage();
				}
				handler.sendMessage(msg);
			}
		}.start();

	}

	/**
	 * 证书查询
	 */
	private void scanzhengshu() {
		list = new ArrayList<Map<String, Object>>();
		
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						list = new ArrayList<Map<String, Object>>();

						for (int i = 0; i < ja.length(); i++) {

							Map<String, Object> map = new HashMap<String, Object>();
							JSONObject jo = ja.getJSONObject(i);
							map.put("id", jo.getString("cerCode"));
							map.put("title", jo.getString("cer_Name"));
							map.put("file", jo.getString("YWFX"));
							map.put("name", jo.getString("trueName"));
							map.put("level", jo.getString("RZJB"));
							map.put("time", jo.getString("perioddate"));
							list.add(map);
						}
						SimpleAdapter adapter = new SimpleAdapter(NLRZScanActivity.this, list,
								R.layout.item_renzheng, new String[] { "id", "file","name",
										"level", "time" }, new int[] {
										R.id.tv_renzheng_zsid, R.id.tv_renzheng_file,
										R.id.tv_renzheng_name, R.id.tv_renzheng_level,R.id.tv_renzheng_time });
						lv_list_info.setAdapter(adapter);

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Diaoxian.showerror(NLRZScanActivity.this,
								e.getMessage());
					}
				} else {
					Diaoxian.showerror(NLRZScanActivity.this,
							msg.obj.toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("page", 1);
				map.put("pageSize", 99);
				String result = HttpUse.messageget("AbilityCertification",
						"queryCertificate", map);
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
					msg.obj = e.getMessage();
				}
				handler.sendMessage(msg);
			}
		}.start();
	}
}
