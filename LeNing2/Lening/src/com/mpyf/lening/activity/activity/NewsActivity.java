package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.activity.adapter.NewsAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;

public class NewsActivity extends Activity {

	private LinearLayout ll_fenlei_back;
	private TextView tv_fenlei_title;
	private ListView lv_fenlei_info;
	private GridView gv_fenlei_info;
	private List<Map<String,Object>> list;
	private static int page = 1;
	private boolean is_divpage;
	private ArrayList<Map<String, Object>> data;
	private NewsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//new Xiaoxibeijing().changetop(NewsActivity.this, R.color.main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_fenlei);
		
		init();
		showdata();
		addlistenr();

	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		page=1;
	}

	private void init() {
		ll_fenlei_back = (LinearLayout) findViewById(R.id.ll_fenlei_back);
		tv_fenlei_title = (TextView) findViewById(R.id.tv_fenlei_title);
		lv_fenlei_info = (ListView) findViewById(R.id.lv_fenlei_info);
		gv_fenlei_info=(GridView) findViewById(R.id.gv_fenlei_info);
		
		gv_fenlei_info.setVisibility(View.GONE);
		
		tv_fenlei_title.setText("×ÊÑ¶");

	}

	private void addlistenr() {
		ListenerServer.setfinish(this, ll_fenlei_back);
		lv_fenlei_info.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(NewsActivity.this, NewsinfoActivity.class);
				intent.putExtra("id", data.get(arg2).get("id").toString());
				startActivity(intent);
			}
		});
	}
	private void showdata(){
		

		data = new ArrayList<Map<String, Object>>();
		adapter = new NewsAdapter(NewsActivity.this, data);
		
		getData();

		lv_fenlei_info.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

				if (is_divpage && scrollState == 0) {
					is_divpage = true;
					getData();
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

	private void getData() {
		final Dialog dialog=MyDialog.MyDialogloading(this);
		dialog.show();
		
		list=new ArrayList<Map<String,Object>>();

		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				dialog.dismiss();
				if (msg.what == 1) {
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						for (int i = 0; i < ja.length(); i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							JSONObject jo = ja.getJSONObject(i);
								map.put("id", jo.getString("PK_Ann"));
								map.put("title", jo.getString("INM_TITLE_NAME"));
								map.put("time", jo.getString("INM_Time"));
								map.put("context",jo.getString("INM_CONTENT"));
								list.add(map);
						}
						data.addAll(list);
						if (page == 1) {

							lv_fenlei_info.setAdapter(adapter);
						}
						adapter.notifyDataSetChanged();
						page++;
						
					} catch (JSONException e) {
						Diaoxian.showerror(NewsActivity.this, e.getMessage());
					}
					
				}else{
					Diaoxian.showerror(NewsActivity.this, msg.obj .toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				final Map<String, Object> map = new HashMap<String, Object>();
				map.put("page", page);
				map.put("pageSize", 10);
				String result = HttpUse.messageget("AbilityCertification",
						"getInmList", map);
				Message msg = new Message();
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = jo.getString("data");
					} else {
						msg.obj = jo.getString("message");
					}
				} catch (JSONException e) {
//					
					msg.obj=e.getMessage();
				}
				handler.sendMessage(msg);
			};
		}.start();
	}
}
