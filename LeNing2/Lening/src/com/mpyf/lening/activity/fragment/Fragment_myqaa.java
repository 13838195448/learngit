package com.mpyf.lening.activity.fragment;

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
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.activity.adapter.QaaAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;

public class Fragment_myqaa extends Fragment {

	private ListView lv_list;
	private List<Map<String, Object>> list;
	private static int page = 1;
	private ArrayList<Map<String, Object>> data;
	private boolean is_divpage;
	private QaaAdapter adapter;

	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list, null);
		init(view);
		showinfo();

		return view;
	};

	private void init(View view) {
		lv_list = (ListView) view.findViewById(R.id.lv_list);
	}

	private void showinfo() {

		data = new ArrayList<Map<String, Object>>();
		adapter = new QaaAdapter(getActivity(), data);
		getData();

		lv_list.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

				if (is_divpage && scrollState == 0) {
					is_divpage = true;

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
							map.put("ANS_CONTENT", jo.getString("ANS_CONTENT"));
							map.put("ANS_STATE", jo.getInt("ANS_STATE"));
							map.put("BAD_NUM", jo.getInt("BAD_NUM"));
							map.put("GOOD_NUM", jo.getInt("GOOD_NUM"));
							map.put("PK_Ans", jo.getString("PK_Ans"));
							map.put("PK_Que", jo.getString("PK_Que"));
							map.put("ansTime", jo.getString("ansTime"));
							map.put("is_child", jo.getInt("is_child"));
							map.put("is_havechild", jo.getInt("is_havechild"));
							map.put("nickname", jo.getString("nickname"));
							map.put("p_Nickname", jo.getString("p_Nickname"));
							map.put("p_Pk_user", jo.getInt("p_Pk_user"));
							map.put("p_UserName", jo.getString("p_UserName"));
							map.put("p_pk_Ans", jo.getString("p_pk_Ans"));
							map.put("pk_user", jo.getInt("pk_user"));
							map.put("trueName", jo.getString("trueName"));
							map.put("userName", jo.getString("userName"));
							map.put("PIC_NUM", jo.getInt("pic_num"));
							//  «∑Ò ’≤ÿπ˝
							// map.put("isCollection",
							// jo.getInt("isCollection"));
							// »Ÿ”˛≥∆∫≈Õº∆¨µÿ÷∑
							map.put("honor_pic", jo.getString("honor_pic"));
							// »Ÿ”˛≥∆∫≈
							map.put("honor_name", jo.getString("honor_name"));

							list.add(map);
						}
						data.addAll(list);
						if (page == 1) {

							lv_list.setAdapter(adapter);
						}
						adapter.notifyDataSetChanged();
						page++;

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Diaoxian.showerror(getActivity(), e.getMessage());
					}
				} else {
					Diaoxian.showerror(getActivity(), msg.obj.toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("page", page);
				map.put("pageSize", 4);

				Message msg = new Message();

				String result = HttpUse
						.messageget("QueAndAns", "myAnswer", map);
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

			};
		}.start();
	}

}
