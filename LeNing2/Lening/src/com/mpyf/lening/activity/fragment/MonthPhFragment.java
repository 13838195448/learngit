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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.activity.adapter.ZDPaihangAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;

/**
 * 知道 --->活动--->排行榜--->月度排行
 * 
 * @author s
 * 
 */
public class MonthPhFragment extends Fragment {
	private ListView lv_list;
	private List<Map<String, Object>> list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.fragment_list, null);
		init(view);
		showinfo();
		return view;
	}

	private void init(View view) {
		lv_list = (ListView) view.findViewById(R.id.lv_list);
	}

	private void showinfo() {

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						list = new ArrayList<Map<String, Object>>();
						JSONArray ja = new JSONArray(msg.obj.toString());
						for (int i = 0; i < ja.length(); i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							JSONObject jo = ja.getJSONObject(i);
							map.put("trueName", jo.getString("trueName"));
							map.put("honor_name", jo.getString("honor_name"));
							map.put("honor_pic", jo.getString("honor_pic"));
							map.put("adoptnum", jo.getInt("adoptnum"));
							map.put("userId", jo.getInt("userId"));
							list.add(map);
						}

						ZDPaihangAdapter adapter = new ZDPaihangAdapter(
								getActivity(), list);
						lv_list.setAdapter(adapter);
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
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				Message msg = new Message();
				map.put("page", 1);
				map.put("pageSize", 999);
				String result = HttpUse.messageget("QueAndAns",
						"adoptRankingMoth", map);
				JSONObject jo;
				try {
					jo = new JSONObject(result);
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
