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
import com.mpyf.lening.activity.activity.MoneydetilActivity;
import com.mpyf.lening.activity.adapter.MoneyAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;

public class Fragment_money extends Fragment {

	private ListView lv_list;
	private List<Map<String, Object>> list;
	
	private String type="";
	private int shouzhi=0;
	
	private String[] jiekou={"UserClassHinf","userClassBinf","UserGoldHinf","userGoldBinf"};
	
	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list, null);
		init(view);
		showinfo();
		return view;
	};

	public Fragment_money(String type,int shouzhi){
		this.type=type;
		this.shouzhi=shouzhi;
	}
	
	private void init(View view) {
		lv_list = (ListView) view.findViewById(R.id.lv_list);
		
	}

	private void showinfo() {

		
		final Dialog dialog=MyDialog.MyDialogloading(getActivity());
		dialog.show();
		
		list=new ArrayList<Map<String,Object>>();
		
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				dialog.dismiss();
				if (msg.what==1) {
					try {
						JSONArray ja=new JSONArray(msg.obj.toString());
						
						for(int i=0;i<ja.length();i++){
							Map<String,Object> map=new HashMap<String, Object>();
							JSONObject jo=ja.getJSONObject(i);
							map.put("type", type);
							map.put("time", jo.getString("infoTime"));
							map.put("Rules", jo.getString("rules"));
							map.put("Num", jo.getString("num"));
							map.put("shouzhi", shouzhi);
							list.add(map);
						}
						
						MoneyAdapter adapter=new MoneyAdapter(getActivity(), list);
						lv_list.setAdapter(adapter);
						
						lv_list.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								// TODO Auto-generated method stub
								Intent intent=new Intent(getActivity(), MoneydetilActivity.class);
								intent.putExtra("type", list.get(arg2).get("type").toString());
								intent.putExtra("time", list.get(arg2).get("time").toString());
								intent.putExtra("Rules", list.get(arg2).get("Rules").toString());
								intent.putExtra("Num", list.get(arg2).get("Num").toString());
								intent.putExtra("shouzhi", (Integer)list.get(arg2).get("shouzhi"));
								
								startActivity(intent);
							}
							
						});
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
				map.put("pageSize", 999);
				
				Message msg=new Message();
				String result=HttpUse.messageget("Account",jiekou[shouzhi], map);
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
			}
		}.start();
		
	
	}
}
