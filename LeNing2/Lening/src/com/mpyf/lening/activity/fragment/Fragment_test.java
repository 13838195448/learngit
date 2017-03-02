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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.activity.activity.ZicetestActivity;
import com.mpyf.lening.activity.adapter.ExamsAdapter;
import com.mpyf.lening.activity.adapter.ExamselfAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;

public class Fragment_test extends Fragment {

	private String string;
	private ArrayList<Map<String, Object>> list;
	private ExamsAdapter adapter;
	private PullToRefreshListView lv_list;
	private int a=0;

	private ArrayList<Map<String, Object>> data;
	private int page = 1;

	public Fragment_test(String string) {
		this.string = string;
	}

	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.a_fragment_list, null);

		init(view);

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();

			
			a = data.size();
			if(a!=0){
			data.clear();
			getData(1, a);
			adapter = new ExamsAdapter(getActivity(), data);
			lv_list.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}
			
	}
	
	
	private void init(View view) {

		lv_list = (PullToRefreshListView) view
				.findViewById(R.id.lv_pullToRefresh);
		lv_list.setMode(Mode.BOTH);
		
		
		
		data = new ArrayList<Map<String, Object>>();
		lv_list.setSelection(3);
		getData(1, 5);

		adapter = new ExamsAdapter(getActivity(), data);
		lv_list.setAdapter(adapter);
		

	

		lv_list.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				a = data.size();
				data.clear();
				getData(1, a);
				adapter.notifyDataSetChanged();

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {

				page++;
				getData(page, 5);
				
				adapter.notifyDataSetChanged();
			}
		});

	}

	private void getData(final int page, final int size) {
		final Dialog dialog = MyDialog.MyDialogloading(getActivity());
		dialog.show();
		list = new ArrayList<Map<String, Object>>();
		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						dialog.dismiss();
						JSONArray ja = new JSONArray(msg.obj.toString());
						for (int i = 0; i < ja.length(); i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							JSONObject jo = ja.getJSONObject(i);

							map.put("PK_Exam", jo.getString("PK_Exam"));
							map.put("Exam_Name", jo.getString("exam_Name"));
							map.put("Sta_Time", jo.getString("sta_Time"));
							map.put("End_Time", jo.getString("end_Time"));
							map.put("Exam_Long", jo.getInt("exam_Long"));
							map.put("Exam_State", jo.getInt("exam_State"));
							map.put("Score", jo.getInt("score"));
							map.put("BuyWay", jo.getInt("buyWay"));
							map.put("Amount", jo.getInt("amount"));

							list.add(map);
						}
						if (list.size() == 0) {
							Diaoxian.showerror(getActivity(), "没有更多了");
						}

						data.addAll(list);

						adapter.notifyDataSetChanged();

						lv_list.onRefreshComplete();

					} catch (JSONException e) {
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

				map.put("pageSize", size);

				Message msg = new Message();

				String result = HttpUse.messageget("Exam", string, map);
				// android.util.Log.i("JSON", result.toString());

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
	};
}
