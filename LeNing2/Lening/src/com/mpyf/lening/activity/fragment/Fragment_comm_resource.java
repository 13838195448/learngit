package com.mpyf.lening.activity.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mpyf.lening.R;
import com.mpyf.lening.activity.activity.CommDetailActivity;
import com.mpyf.lening.activity.activity.NewsActivity;
import com.mpyf.lening.activity.adapter.NewsAdapter;
import com.mpyf.lening.activity.adapter.ShareResAdapter;
import com.mpyf.lening.interfaces.bean.Result.ShareRes;
import com.mpyf.lening.interfaces.http.HttpUse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Fragment_comm_resource extends Fragment {

	private View view;
	private ListView lv_list;
	private RadioGroup rg_btn;
	private RadioButton bt_upload;
	private RadioButton bt_download;
	private List<ShareRes> data;

	private ShareResAdapter adapter;
	private static int page = 1;
	private boolean is_divpage;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.fragment_comm_resource, null);
		page=1;
		init();
		addLinsener();
		initData("listShareRes");
		return view;
		
	}

	private void initData(final String s) {

		
		data = new ArrayList<ShareRes>();
		adapter = new ShareResAdapter(getActivity(),data);
		
		getData(s);

		lv_list.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

				if (is_divpage && scrollState == 0) {
					is_divpage = true;
					getData(s);
				}
			}

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				if ((arg1 + arg2) == arg3) {
					is_divpage = true;
				} else {
					is_divpage = false;
				}
			}
		});

		
	}

	private void addLinsener() {

		rg_btn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				
				if(arg1==R.id.bt_upload){
					initData("listShareRes");
					page=1;
				}else{
					initData("hotShareRes");
					page=1;
				}
			}
		});
		
		
		lv_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Intent intent = new Intent(getActivity(),CommDetailActivity.class);

				
				intent.putExtra("shareName", data.get(arg2).shareName);
				intent.putExtra("buyNum", data.get(arg2).buyNum);
				intent.putExtra("onlineTime", data.get(arg2).onlineTime);
				intent.putExtra("buyWay", data.get(arg2).buyWay);
				intent.putExtra("amount", data.get(arg2).amount);
				intent.putExtra("shareSize", data.get(arg2).shareSize);
				intent.putExtra("remark", data.get(arg2).remark);
				intent.putExtra("trueName", data.get(arg2).trueName);
				intent.putExtra("PK_Share", data.get(arg2).PK_Share);
				
				startActivity(intent);
			}
		});
	}

	private void init() {
		
		lv_list = (ListView) view.findViewById(R.id.lv_list);
		
		rg_btn = (RadioGroup) view.findViewById(R.id.rg_btn);
		bt_upload = (RadioButton) view.findViewById(R.id.bt_upload);
		bt_download = (RadioButton) view.findViewById(R.id.bt_download);
		
		lv_list.setDivider(null);
		rg_btn.check(R.id.bt_upload);//设置默认选项
	}
	
	private void getData(final String s) {
	
		
		
		final Handler handler =new  Handler(){
			@Override
			public void handleMessage(Message msg) {

			if(msg.what ==1 ){
				
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						List<ShareRes> list = new ArrayList<ShareRes>();
						for (int i = 0; i < ja.length(); i++) {
							ShareRes shareRes = new ShareRes();
							JSONObject jo = ja.getJSONObject(i);
							
							shareRes.shareName = jo.getString("shareName");
							shareRes.buyNum = jo.getInt("buyNum");
							shareRes.onlineTime = jo.getString("onlineTime");
							shareRes.buyWay  = jo.getInt("buyWay");
							shareRes.amount = jo.getInt("amount");
							shareRes.shareSize = jo.getString("shareSize");
							shareRes.remark = jo.getString("remark");
							shareRes.trueName = jo.getString("trueName");
							shareRes.PK_Share = jo.getString("PK_Share");
							
							list.add(shareRes);
						}
						
						data.addAll(list);
						if (page == 1) {

							lv_list.setAdapter(adapter);
						}
						adapter.notifyDataSetChanged();
						page++;
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		new Thread(){
			
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("page", page);
				map.put("pageSize", 20); 
				
				String result = HttpUse.messageget("PersonalCenter", s, map);
				Message msg = Message.obtain();
				try {
					JSONObject jo = new JSONObject(result);
					if(jo.getBoolean("result")){
						msg.what = 1;
						msg.obj = jo.getString("data");
					}else {
						msg.obj = jo.getString("message");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				handler.sendMessage(msg);
				
			};
		}.start();
		
	}

}
