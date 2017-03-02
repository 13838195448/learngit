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
import com.mpyf.lening.activity.activity.ZicetestActivity;
import com.mpyf.lening.activity.adapter.ExamselfAdapter;
import com.mpyf.lening.interfaces.bean.Result.ExamSelf;
import com.mpyf.lening.interfaces.http.HttpUse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class Fragment_myzice extends Fragment {

	private ListView lv_list;
	private Handler handler;
	private ExamselfAdapter adapter;
	private List<Map<String, Object>> list;
	private List<ExamSelf> examList;
	
	private boolean is_divpage;
	 private List<Map<String, Object>> data;
	 private static int page=1;
	private String string;
/*Exam_Stateøº ‘◊¥Ã¨[Byte] 0øº ‘Œ¥π∫¬Ú£¨1“—π∫¬ÚŒ¥øº ‘£¨2øº ‘Ω¯––÷–£¨3øº ‘“—Ã·Ωªµ»¥˝≈˙‘ƒ£¨4øº ‘ÕÍ≥…*/
	

	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list, null);
		init(view);
		

		return view;
	};

	private void init(View view) {
		lv_list = (ListView) view.findViewById(R.id.lv_list);

	}

	@Override
	public void onResume() {
		super.onResume();
		page=1;
		showinfo();
	}
	void showinfo() {

		data = new ArrayList<Map<String, Object>>();
		adapter = new ExamselfAdapter(getActivity(), data);
		loadData();
		
		lv_list.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
				if(is_divpage&&scrollState==0){
					is_divpage=true;
					loadData();
				}
			}
			
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				if((arg1+arg2)==arg3){
					is_divpage=true;
				}else{
					is_divpage=false;
				}
			}
		});

	}

	private void loadData() {
		list = new ArrayList<Map<String, Object>>();

		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						for (int i = 0; i < ja.length(); i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							JSONObject jo = ja.getJSONObject(i);

							map.put("PK_ExamSelf", jo.getString("PK_ExamSelf"));
							map.put("Exam_Name", jo.getString("exam_Name"));
							map.put("Sta_Time", jo.getString("sta_Time"));
							map.put("End_Time", jo.getString("end_Time"));
							map.put("Exam_Long", jo.getInt("exam_Long"));
							map.put("Exam_State", jo.getInt("exam_State"));
							map.put("Score", jo.getInt("score"));
							

							list.add(map);
						}
						
						data.addAll(list);
						
						if(page ==1){
							
							lv_list.setAdapter(adapter);
						}
							adapter.notifyDataSetChanged();
						page++;
				//		adLinsener(data);
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
				map.put("pageSize", 15);

				Message msg = new Message();

				String result = HttpUse.messageget("Exam", "getMyExamSelf", map);
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
	}

	

}
