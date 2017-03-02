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

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.activity.activity.BuyedActivity;
import com.mpyf.lening.activity.adapter.MyCourseAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class Fragment_mycourse extends Fragment {

	private ListView lv_list;
	private List<Map<String, Object>> list;
	private int type=0;
	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list, null);
		init(view);
		showinfo();
		addlistener();
		return view;
	}

	public Fragment_mycourse(int type){
		this.type=type;
	}
	
	private void init(View view) {
		lv_list = (ListView) view.findViewById(R.id.lv_list);
	}

	private void showinfo() {
		list = new ArrayList<Map<String, Object>>();
		final Dialog dialog=MyDialog.MyDialogloading(getActivity());
		dialog.show();
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				dialog.dismiss();
				if (msg.what == 1) {
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						for (int i = 0; i < ja.length(); i++) {
							JSONObject jo = ja.getJSONObject(i);
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("id", jo.getString("PK_Course"));
							map.put("title", jo.getString("courseName"));
							map.put("time", "ÒÑÑ§Ï°"+jo.getString("speed")+"%");
							map.put("date", jo.getInt("remainIng"));
							map.put("picUrl", Setting.apiUrl+jo.getString("picUrl"));
							list.add(map);
						}

					} catch (JSONException e) {
						Diaoxian.showerror(getActivity(), e.getMessage());
					}

					MyCourseAdapter adapter=new MyCourseAdapter(getActivity(), list);
					lv_list.setAdapter(adapter);
				} else {
//					Diaoxian.showerror(getActivity(), msg.obj.toString());
				}

			}
		};

		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("courseType", type);
				map.put("page", 1);
				map.put("pageSize", 999);

				Message msg = new Message();

				String result = HttpUse.messageget("CourseStudy", "myCourse",
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				handler.sendMessage(msg);

			};
		}.start();

	}

	private void addlistener() {
		lv_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(), BuyedActivity.class);
				intent.putExtra("id", list.get(arg2).get("id").toString());
				startActivity(intent);
			}
		});
	}
}
