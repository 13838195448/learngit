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
import com.mpyf.lening.activity.activity.WatchdayiActivity;
import com.mpyf.lening.activity.adapter.DayiAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;

public class Fragment_buyeddayi extends Fragment {

	private ListView lv_list;
	private List<Map<String, Object>> list ;
	private String[] from={"isanswered","context","from","answer"};
	private int[] to={R.id.tv_buyeddayi_isanswered,R.id.tv_buyeddayi_context,R.id.tv_buyeddayi_from,R.id.tv_buyeddayi_answer};
	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list, null);
		init(view);
		
		return view;
	};

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		showinfo();
	}
	
	private void init(View view) {
		lv_list = (ListView) view.findViewById(R.id.lv_list);
	}

	private void showinfo() {
		list = new ArrayList<Map<String, Object>>();
		
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				JSONArray ja;
				try {
					ja = new JSONArray(msg.obj.toString());
					for(int i=0;i<ja.length();i++){
						JSONObject jo=ja.getJSONObject(i);
						
							Map<String,Object> map=new HashMap<String, Object>();
							
							map.put("beAnswered", jo.getBoolean("beAnswered"));
							
							map.put("id", jo.getString("PK_Faq"));
							map.put("context", jo.getString("FAQ_CONTENT"));
							map.put("from", "来自课件：  "+jo.getString("res_Name"));
							
							map.put("answer", "答案： "+jo.getString("ans_CONTENT"));
							map.put("time", jo.getString("ans_Time"));
							map.put("PK_Res", jo.getString("PK_Res"));
							list.add(map);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Diaoxian.showerror(getActivity(), e.getMessage());;
				}
				DayiAdapter adapter=new DayiAdapter(getActivity(), list);
				lv_list.setAdapter(adapter);
				
				lv_list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						if ((Boolean) list.get(arg2).get("beAnswered")) {
							return;
						}else{
							Intent intent=new Intent(getActivity(), WatchdayiActivity.class);
							intent.putExtra("id", list.get(arg2).get("id").toString());
							intent.putExtra("context", list.get(arg2).get("context").toString());
							intent.putExtra("from", list.get(arg2).get("from").toString());
							intent.putExtra("time", list.get(arg2).get("time").toString());
							intent.putExtra("PK_Res", list.get(arg2).get("PK_Res").toString());
							startActivity(intent);
						}
					}
				});
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("courseId", Quanjubianliang.courseid);
				map.put("page", 1);
				map.put("pageSize", 9999);
				Message msg=new Message();
				String result=HttpUse.messageget("CourseStudy", "myCourseResFaq", map);
				try {
					JSONObject jo=new JSONObject(result);
					if(jo.getBoolean("result")){
						msg.what=1;
						msg.obj=jo.getString("data");
					}else{
						msg.obj=jo.getString("message");
					}
				} catch (JSONException e) {
					msg.obj=e.getMessage();
				}
				handler.sendMessage(msg);
			
			};
		}.start();
		
		
	}
}
