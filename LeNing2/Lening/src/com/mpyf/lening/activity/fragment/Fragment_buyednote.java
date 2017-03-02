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
import android.widget.SimpleAdapter;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.activity.activity.WatchnoteActivity;
import com.mpyf.lening.interfaces.http.HttpUse;

public class Fragment_buyednote extends Fragment {

	private ListView lv_list;
	private String[] from={"title","context","from","time"};
	private int[] to={R.id.tv_buyednote_title,R.id.tv_buyednote_context,R.id.tv_buyednote_from,R.id.tv_buyednote_time};
	private String id="";
	List<Map<String, Object>> list;
	public Fragment_buyednote(String id){
		this.id=id;
	}
	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list, null);
		init(view);
		
		return view;
	};
	public Fragment_buyednote() {

	}
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
				if(msg.what==1){
					try {
						JSONArray ja=new JSONArray(msg.obj.toString());
						for(int i=0;i<ja.length();i++){
							Map<String,Object> map=new HashMap<String, Object>();
							JSONObject jo=ja.getJSONObject(i);
//							map.put("title", jo.getString("NOTE_TITLE"));
//							map.put("context", jo.getString("NOTE_CONTENT"));
//							map.put("from", "来自课件：  "+jo.getString("res_Name"));
//							map.put("time", jo.getString("NOTE_Time"));
							
							map.put("id", jo.getString("PK_Note"));
							map.put("PK_Course", jo.getString("PK_Course"));
							map.put("PK_Res", jo.getString("PK_Res"));
							map.put("title", jo.getString("NOTE_TITLE"));
							map.put("context", jo.getString("NOTE_CONTENT"));
							map.put("from", "来自课件：  "+jo.getString("res_Name"));
							map.put("time", jo.getString("NOTE_Time"));
							list.add(map);
						}
						SimpleAdapter adapter=new SimpleAdapter(getActivity(), list,R.layout.item_buyednote,from,to);
						lv_list.setAdapter(adapter);
						
						lv_list.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								// TODO Auto-generated method stub
								Intent intent=new Intent(getActivity(), WatchnoteActivity.class);
								intent.putExtra("id", list.get(arg2).get("id").toString());
								intent.putExtra("title", list.get(arg2).get("title").toString());
								intent.putExtra("context", list.get(arg2).get("context").toString());
								intent.putExtra("from", list.get(arg2).get("from").toString());
								intent.putExtra("time", list.get(arg2).get("time").toString());
								intent.putExtra("PK_Course", list.get(arg2).get("PK_Course").toString());
								intent.putExtra("PK_Res", list.get(arg2).get("PK_Res").toString());
								startActivity(intent);
							}
						});
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Diaoxian.showerror(getActivity(), e.getMessage());
					}
				}
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("courseId", id);
				
				Message msg=new Message();
				
				String result=HttpUse.messageget("CourseStudy", "courseNote", map);
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
					msg.obj=e.getMessage();
				}
				
				handler.sendMessage(msg);
			};
		}.start();
		
		
	}
}
