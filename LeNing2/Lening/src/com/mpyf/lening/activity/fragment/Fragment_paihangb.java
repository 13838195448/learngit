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
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.activity.activity.NotbuycourseActivity;
import com.mpyf.lening.activity.activity.RmkcActivity;
import com.mpyf.lening.activity.adapter.PaihangbAdapter;
import com.mpyf.lening.activity.adapter.PhcrouseAdapter;
import com.mpyf.lening.activity.adapter.RmCourseAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;

/**
 * @author ≈≈––3“≥√Ê
 *
 */
public class Fragment_paihangb extends Fragment {

	private int type=0;
	private ListView lv_list;
	private List<Map<String,Object>> list;
	private Dialog dialog;
	
	public Fragment_paihangb(int type){
		this.type=type;
	}
	
	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list, null);
		init(view);
		showinfo();
		addlistener();
		return view;
	};
	
	private void init(View view){
		lv_list=(ListView) view.findViewById(R.id.lv_list);
	}
	
	private void showinfo(){
		switch (type) {
		case 1:
			showxuefen();
			break;
		case 2:
			showzice();
			break;
		case 3:
			showkecheng();
			break;

		default:
			break;
		}
	}
	
	private void addlistener(){
		
	}
	
	
	private void showxuefen(){
		dialog = MyDialog.MyDialogloading(getActivity());
		dialog.show();
		final Handler handler =new Handler(){
			@Override
			public void handleMessage(Message msg) {
				dialog.dismiss();
				if (msg.what==1) {
					try {
						list=new ArrayList<Map<String,Object>>();
						JSONArray ja=new JSONArray(msg.obj.toString());
						for (int i = 0; i < ja.length(); i++) {
							Map<String,Object> map=new HashMap<String, Object>();
							JSONObject jo=ja.getJSONObject(i);
							map.put("name", jo.getString("trueName"));
							map.put("image", jo.getString("PK_user"));
							map.put("scroe", jo.getInt("score"));
							list.add(map);
						}
						
						PaihangbAdapter adapter=new PaihangbAdapter(getActivity(), list);
						lv_list.setAdapter(adapter);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Diaoxian.showerror(getActivity(), e.getMessage());
					}
					
				}else{
					Diaoxian.showerror(getActivity(), msg.obj.toString());
				}
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Map<String,Object> map=new HashMap<String, Object>();
				Message msg=new Message();
				String result=HttpUse.messageget("Account", "userScoreRanking", map);
				JSONObject jo;
				try {
					jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what=1;
						msg.obj=jo.getString("data");
					}else{
						msg.obj=jo.getString("message");
					}
				} catch (JSONException e) {
					msg.obj=e.getMessage();
				}
				
				handler.sendMessage(msg);
				
			}
		}.start();
	}
	
	private void showzice(){
		
	}

	private void showkecheng(){
		dialog = MyDialog.MyDialogloading(getActivity());
		dialog.show();
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				dialog.dismiss();
				if (msg.what == 1) {
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						list = new ArrayList<Map<String, Object>>();
						for (int i = 0; i < ja.length(); i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							JSONObject jo = ja.getJSONObject(i);
							map.put("id", jo.getString("PK_Course"));
							map.put("title", jo.getString("CourseName"));
							map.put("scan", jo.getString("BuyNum"));
							map.put("teacher", jo.getString("Lecturer"));
							map.put("cost", jo.getString("Amount"));
							map.put("PicUrl", jo.getString("PicUrl"));
							map.put("BuyWay", jo.getString("BuyWay"));
							list.add(map);
						}

						PhcrouseAdapter adapter=new PhcrouseAdapter(getActivity(), list);
						lv_list.setAdapter(adapter);
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
				map.put("page", 1);
				map.put("pageSize", 999);
				Message msg = Message.obtain();
				String result = HttpUse.messageget("CourseStudy", "hotCourse",
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
					msg.obj = e.getMessage();
				}
			handler.sendMessage(msg);
			}
		}.start();

	}
}
