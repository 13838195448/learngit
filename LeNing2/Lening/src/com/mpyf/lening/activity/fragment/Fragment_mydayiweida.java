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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.Quanjubianliang;
import com.mpyf.lening.activity.activity.AddqaAcitvity;
import com.mpyf.lening.activity.adapter.DayiAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;

public class Fragment_mydayiweida extends Fragment {

	private ListView lv_list;
	private List<Map<String, Object>> list ;
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
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		showinfo();
		addlistener();
	}

	private void showinfo() {
		list = new ArrayList<Map<String, Object>>();
		
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(msg.what==1){
					try {
						JSONArray ja=new JSONArray(msg.obj.toString());
						for(int i=0;i<ja.length();i++){
							Map<String,Object> map=new HashMap<String, Object>();
							JSONObject jo=ja.getJSONObject(i);
							if (!jo.getBoolean("beAnswered")) {
								map.put("id", jo.getString("PK_Faq"));
								map.put("beAnswered", jo.getBoolean("beAnswered"));
								map.put("PK_Course", jo.getString("PK_Course"));
								map.put("PK_Res", jo.getString("PK_Res"));
								map.put("id", jo.getString("PK_Faq"));
								map.put("context", jo.getString("FAQ_CONTENT"));
								map.put("from", "ю╢вт©н╪Ч: "+jo.getString("res_Name"));
								map.put("answer", "");
								list.add(map);
							}
							
						}
						DayiAdapter adapter=new DayiAdapter(getActivity(), list);
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
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("page", 1);
				map.put("pageSize", 9999);
				
				Message msg=new Message();
				
				String result=HttpUse.messageget("CourseStudy", "myResFaq", map);
				try {
					JSONObject jo=new JSONObject(result);
					if(jo.getBoolean("result")){
						msg.what=1;
						msg.obj=jo.getString("data");
					}else{
						msg.obj=jo.getString("message");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				handler.sendMessage(msg);
				
			};
		}.start();
		
		
	}
	
	private void addlistener(){
		lv_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(), AddqaAcitvity.class);
				Quanjubianliang.courseid=list.get(arg2).get("PK_Course").toString();
				intent.putExtra("id", list.get(arg2).get("id").toString());
				intent.putExtra("resId", list.get(arg2).get("PK_Res").toString());
				intent.putExtra("context", list.get(arg2).get("context").toString());
				startActivity(intent);
			}
		});
	}
}
