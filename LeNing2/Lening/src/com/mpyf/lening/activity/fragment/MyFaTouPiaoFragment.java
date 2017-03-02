package com.mpyf.lening.activity.fragment;

/**
 * �ҷ����ͶƱ
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.activity.adapter.TouPiaoAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;

public class MyFaTouPiaoFragment extends Fragment {

	private ListView lv_list;
	private List<Map<String, Object>> list;
	private ArrayList<Map<String, Object>> data;
	private List<Map<String, Object>> firstlist;
	private TouPiaoAdapter adapter;
	private static int page = 1;
	private boolean is_divpage;
	private int selection = 0;

	public MyFaTouPiaoFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list, null);
		page = 1;
		lv_list = (ListView) view.findViewById(R.id.lv_list);
		// showInfo();
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

	private void showInfo() {

		data = new ArrayList<Map<String, Object>>();
		adapter = new TouPiaoAdapter(getActivity(), data);
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

		// TODO ������������

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					// ��ȡhandler���������ݣ��ŵ�map��,���ӵ�list����
					try {
						// list = new ArrayList<Map<String, Object>>();
						JSONArray ja = new JSONArray(msg.obj.toString());
						for (int i = 0; i < ja.length(); i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							JSONObject jo = ja.getJSONObject(i);
							// ͶƱ����
							map.put("vote_title", jo.getString("vote_title"));
							// ͼƬ��ַ
							map.put("vote_pic", jo.getString("vote_pic"));
							// ͶƱʱ��
							map.put("vote_time", jo.getString("vote_time"));
							// ͶƱ����
							map.put("vote_num", jo.getInt("vote_num"));
							// ͶƱ����
							map.put("pk_vote", jo.getString("pk_vote"));
							// ������
							map.put("reward_num", jo.getInt("reward_num"));
							// ����ʣ��
							map.put("reward_residue",
									jo.getInt("reward_residue"));
							// ÿ������
							map.put("reward_every", jo.getInt("reward_every"));
							// �û��Ĳ���
							map.put("user_option", jo.getString("user_option"));
							// ѡ�����
							map.put("option_num", jo.getInt("option_num"));
							// ������
							map.put("pk_user", jo.getInt("pk_user"));
							// ��ʵ����
							map.put("trueName", jo.getString("trueName"));
							// �����ƺ�
							map.put("honor_name", jo.getString("honor_name"));
							// �Ƿ�ͶƱ[Int32] 0�Ƿ� 1����
							map.put("isVote", jo.getInt("isVote"));
							// ���ͷ�ʽ0������1�ֱ�2���
							map.put("reward_type", jo.getInt("reward_type"));
							// ѡ������[Int32] 0��ѡ��1��ѡ
							map.put("option_type", jo.getInt("option_type"));

							// û��ͶƱ��ѡ��
							map.put("option_a", jo.getString("option_a"));
							map.put("option_b", jo.getString("option_b"));
							map.put("option_c", jo.getString("option_c"));
							map.put("option_d", jo.getString("option_d"));
							map.put("option_e", jo.getString("option_e"));
							map.put("option_f", jo.getString("option_f"));
							map.put("option_g", jo.getString("option_g"));
							map.put("option_h", jo.getString("option_h"));
							map.put("option_i", jo.getString("option_i"));
							map.put("option_j", jo.getString("option_j"));
							// Ͷ��Ʊ��ѡ��
							map.put("votes_a", jo.getString("votes_a"));
							map.put("votes_b", jo.getString("votes_b"));
							map.put("votes_c", jo.getString("votes_c"));
							map.put("votes_d", jo.getString("votes_d"));
							map.put("votes_e", jo.getString("votes_e"));
							map.put("votes_f", jo.getString("votes_f"));
							map.put("votes_g", jo.getString("votes_g"));
							map.put("votes_h", jo.getString("votes_h"));
							map.put("votes_i", jo.getString("votes_i"));
							map.put("votes_j", jo.getString("votes_j"));

							firstlist.add(map);
						}
						// // ��list����adapter��������Դչʾ
						// TouPiaoAdapter adapter = new TouPiaoAdapter(
						// getActivity(), list);
						// // vp_qa.setCurrentItem(0);
						// // ��������ݴ���ȥ��
						// lv_list.setAdapter(adapter);
						data.addAll(firstlist);
						if (page == 1) {

							lv_list.setAdapter(adapter);
						}
						adapter.notifyDataSetChanged();
						page++;

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
				map.put("voteType", 1);
				Message msg = new Message();

				String result = HttpUse.messageget("QueAndAns", "listMyVote",
						map);
				// TODO ��ӡ���󵽵�����
				// System.out.println("=====ssss��ӡ�ҷ����ͶƱ����ssss====" + result);

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