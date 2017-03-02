package com.mpyf.lening.activity.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.activity.activity.ShangQadetilActivity;
import com.mpyf.lening.activity.adapter.ShangCheng_LeAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;

public class GoldShangFragment extends Fragment {
	private ListView lv_list;
	private List<Map<String, Object>> list;

	private ArrayList<Map<String, Object>> data;
	private List<Map<String, Object>> firstlist;
	private ShangCheng_LeAdapter adapter;
	private static int page = 1;
	private boolean is_divpage;
	private int selection = 0;

	public GoldShangFragment() {
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list, null);
		page = 1;
		init(view);
//		showInfo();
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		page = 1;
		showInfo();
	}

	@Override
	public void onPause() {
		super.onPause();

	}

	private void init(View view) {
		lv_list = (ListView) view.findViewById(R.id.lv_list);
	}

	private void showInfo() {
		data = new ArrayList<Map<String, Object>>();
		adapter = new ShangCheng_LeAdapter(getActivity(), data);
		getData();
		lv_list.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

				if (is_divpage && scrollState == 0) {
					is_divpage = true;
					selection = lv_list.getFirstVisiblePosition();
					getData();
				}
			}

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				if ((arg1 + arg2) == arg3) {
					is_divpage = true;
				} else {
					is_divpage = false;
				}
			}
		});

	}

	private void getData() {
		firstlist = new ArrayList<Map<String, Object>>();
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					// ��ȡhandler���������ݣ��ŵ�map��,���ӵ�list����
					try {
//						list = new ArrayList<Map<String, Object>>();
						JSONArray ja = new JSONArray(msg.obj.toString());
						for (int i = 0; i < ja.length(); i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							JSONObject jo = ja.getJSONObject(i);
							// ��Ʒ����
							map.put("pk_goods", jo.getString("pk_goods"));
							// ��Ʒ����
							map.put("goodsName", jo.getString("goodsName"));
							// �����
							map.put("inventory", jo.getInt("inventory"));
							// ��������
							map.put("buy_num", jo.getInt("buy_num"));
							// �¹�������
							map.put("mbuy_num", jo.getInt("mbuy_num"));
							// ͼƬ��ַ
							map.put("picUrl", jo.getString("picUrl"));
							// ���
							map.put("remark", jo.getString("remark"));
							// �ۼ�
							map.put("amount", jo.getString("amount"));
							// ����ʱ��
							map.put("onlineTime", jo.getString("onlineTime"));

							firstlist.add(map);
						}
						// ��list����adapter��������Դչʾ
						// ShangCheng_GoldAdapter adapter = new
						// ShangCheng_GoldAdapter(
						// getActivity(), list);
						// vp_qa.setCurrentItem(0);
						// ��������ݴ���ȥ��
						// lv_list.setAdapter(adapter);

						data.addAll(firstlist);
						if (page == 1) {

							lv_list.setAdapter(adapter);
						}
						adapter.notifyDataSetChanged();
						page++;

						// listview ��Ŀ����¼�

						lv_list.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(getActivity(),
										ShangQadetilActivity.class);
								intent.putExtra("id",
										data.get(arg2).get("pk_goods")
												.toString());
								startActivity(intent);
							}
						});

					} catch (JSONException e) {
						Diaoxian.showerror(getActivity(), e.getMessage());
					}
				} else {
					Diaoxian.showerror(getActivity(), msg.obj.toString());
				}
			}
		};

		new Thread() {
			public void run() {

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("page", page);
				map.put("pageSize", 20);
				map.put("buyWay", 2);
				// ��Ʒ��������
				map.put("goodsName", "");
				Message msg = new Message();

				String result = HttpUse.messageget("QueAndAns", "listGoods",
						map);
				// TODO ��ӡ���󵽵�����
//				System.out.println("=====��ӡ���󵽵Ľ����Ʒ====" + result);

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