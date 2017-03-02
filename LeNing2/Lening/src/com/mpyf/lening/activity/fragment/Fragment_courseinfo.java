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
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.MyListView;
import com.mpyf.lening.interfaces.http.HttpUse;

public class Fragment_courseinfo extends Fragment {

	private MyListView lv_courseinfo_kejian;
	private ScrollView sv_courseinfo;
	private TextView tv_courseinfo_remark,tv_courseinfo_teacher,tv_courseinfo_buynum,tv_courseinfo_resnum;
	private List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
	private String id;
	private String remark;
	private String teacher;
	private String buynum;
	
	public Fragment_courseinfo(){
		
	}
	
	public  Fragment_courseinfo(String id,String remark,String teacher,String buynum){
		this.id=id;
		this.remark=remark;
		this.teacher=teacher;
		this.buynum=buynum;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.fragment_courseinfo, null);
		init(view);
		
		return view;
		
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		showinfo();
	}
	
	private void init(View view){
		lv_courseinfo_kejian=(MyListView) view.findViewById(R.id.lv_courseinfo_kejian);
		sv_courseinfo=(ScrollView) view.findViewById(R.id.sv_courseinfo);
		tv_courseinfo_remark=(TextView) view.findViewById(R.id.tv_courseinfo_remark);
		tv_courseinfo_teacher=(TextView) view.findViewById(R.id.tv_courseinfo_teacher);
		tv_courseinfo_buynum=(TextView) view.findViewById(R.id.tv_courseinfo_buynum);
		tv_courseinfo_resnum=(TextView) view.findViewById(R.id.tv_courseinfo_resnum);
	}
	
	private void showinfo(){
		tv_courseinfo_remark.setText(remark);
		tv_courseinfo_teacher.setText(teacher);
		tv_courseinfo_buynum.setText(buynum);
		
		list=new ArrayList<Map<String,Object>>();
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==1){
					try {
						JSONArray ja=new JSONArray(msg.obj.toString());
						for(int i=0;i<ja.length();i++){
							JSONObject jo=ja.getJSONObject(i);
							Map<String,Object> map=new HashMap<String, Object>();
							map.put("title", jo.getString("resName"));
							map.put("teacher", jo.getString("lecturer"));
							list.add(map);
						}
						
						SimpleAdapter adapter=new SimpleAdapter(getActivity(), list, R.layout.item_kejian,new String[]{"title","teacher"}, new int[]{R.id.tv_kejian_title,R.id.tv_kejian_teacher});
						lv_courseinfo_kejian.setAdapter(adapter);
						sv_courseinfo.smoothScrollTo(0, 0);
						tv_courseinfo_resnum.setText(list.size()==0?"0":list.size()+"");
					} catch (JSONException e) {
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
				map.put("courseId", id);
				Message msg=new Message();
				String result=HttpUse.messageget("CourseStudy", "getCourseResByCourseId", map);
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
