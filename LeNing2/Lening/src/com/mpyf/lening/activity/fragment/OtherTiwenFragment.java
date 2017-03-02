package com.mpyf.lening.activity.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.activity.activity.QadetilActivity;
import com.mpyf.lening.activity.adapter.OtherHuiDaAdapter;
import com.mpyf.lening.activity.adapter.OtherTiwenAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class OtherTiwenFragment extends Fragment {
	private String pk_user;
	private ListView lv_list;
	private List<Map<String, Object>> list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.fragment_list, null);
		init(view);
		return view;
	}

	private void init(View view) {
		lv_list = (ListView) view.findViewById(R.id.lv_list);
	}

	public void setData(String userId) {
		pk_user = userId;
		showinfo();
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
							map.put("id", jo.getString("PK_Que"));
							map.put("QUE_STATE", jo.getInt("QUE_STATE"));
							map.put("QUE_CONTENT", jo.getString("QUE_CONTENT"));
							list.add(map);
						}

						OtherTiwenAdapter adapter = new OtherTiwenAdapter(
								getActivity(), list);
						lv_list.setAdapter(adapter);

						lv_list.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								Intent intent = new Intent(getActivity(),
										QadetilActivity.class);
								intent.putExtra("id", list.get(arg2).get("id")
										.toString());
								startActivity(intent);
							}
						});

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
				map.put("userId", pk_user);

				String result = HttpUse.messageget("QueAndAns", "userQue", map);
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
