package com.mpyf.lening.activity.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.activity.activity.AddcommentActivity;
import com.mpyf.lening.activity.activity.ChengjiActivity;
import com.mpyf.lening.activity.activity.NLRZScanActivity;
import com.mpyf.lening.activity.adapter.MyCommentAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class Fragment_myrenzheng extends Fragment {

	private ListView lv_list;
	private int index;
	List<Map<String, Object>> list;

	public Fragment_myrenzheng(int index) {
		this.index = index;
	}

	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list, null);
		init(view);

		return view;
	};

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		show();
	}

	private void init(View view) {
		lv_list = (ListView) view.findViewById(R.id.lv_list);
	}

	private void show() {
		switch (index) {
		case 0:
			baoming();
			break;
		case 1:
			chengji();
			break;
		case 2:
			pingshen();
			break;
		case 3:
			zhengshu();
			break;

		default:
			break;
		}
	}

	private void baoming() {


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
								getActivity(), list,
								R.layout.item_baoming, new String[] { "title",
										"name", "payed", "tijiao" }, new int[] {
										R.id.tv_baoming_title,
										R.id.tv_baoming_name,
										R.id.tv_baoming_payed,
										R.id.tv_baoming_tijiao });
						lv_list.setAdapter(adapter);

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Diaoxian.showerror(getActivity(),
								e.getMessage());
					}
				} else {
					Diaoxian.showerror(getActivity(),
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

	private void chengji() {

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
								getActivity(), list,
								R.layout.item_chengji, new String[] { "title",
										"name", "result", "level" }, new int[] {
										R.id.tv_chengji_title,
										R.id.tv_chengji_name,
										R.id.tv_chengji_result,
										R.id.tv_chengji_level });
						lv_list.setAdapter(adapter);

						lv_list.setOnItemClickListener(new OnItemClickListener() {
									
									@Override
									public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
										// TODO Auto-generated method stub
										Intent intent=new Intent(getActivity(),ChengjiActivity.class);
										intent.putExtra("id", list.get(arg2).get("id").toString());
										startActivity(intent);
									}
								});
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Diaoxian.showerror(getActivity(),
								e.getMessage());
					}
				} else {
					Diaoxian.showerror(getActivity(),
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

	private void pingshen() {

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
								getActivity(), list,
								R.layout.item_pingshen, new String[] { "title",
										"name", "zhuangtai", "nianshen" },
								new int[] { R.id.tv_pingshen_title,
										R.id.tv_pingshen_name,
										R.id.tv_pingshen_result,
										R.id.tv_pingshen_nianshen });
						lv_list.setAdapter(adapter);

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Diaoxian.showerror(getActivity(),
								e.getMessage());
					}
				} else {
					Diaoxian.showerror(getActivity(),
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

	private void zhengshu() {

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
						SimpleAdapter adapter = new SimpleAdapter(getActivity(), list,
								R.layout.item_renzheng, new String[] { "id", "file","name",
										"level", "time" }, new int[] {
										R.id.tv_renzheng_zsid, R.id.tv_renzheng_file,
										R.id.tv_renzheng_name, R.id.tv_renzheng_level,R.id.tv_renzheng_time });
						lv_list.setAdapter(adapter);

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Diaoxian.showerror(getActivity(),
								e.getMessage());
					}
				} else {
					Diaoxian.showerror(getActivity(),
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
