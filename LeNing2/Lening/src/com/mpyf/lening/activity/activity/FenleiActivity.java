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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.activity.adapter.KcflAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;

public class FenleiActivity extends Activity {
	private LinearLayout ll_fenlei_back;
	private ListView lv_fenlei_info;
	private GridView gv_fenlei_info;
	private List<Map<String, Object>> listtitle;
	private List<Map<String, Object>> listdetil;
	private String parentid="";
	private String title="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new Xiaoxibeijing().changetop(FenleiActivity.this, R.color.main);
		setContentView(R.layout.activity_fenlei);
		init();
		getdate();
		addlistenr();
//		defaultshow();
	}

	private void init() {
		ll_fenlei_back = (LinearLayout) findViewById(R.id.ll_fenlei_back);
		lv_fenlei_info = (ListView) findViewById(R.id.lv_fenlei_info);
		gv_fenlei_info = (GridView) findViewById(R.id.gv_fenlei_info);

		listtitle = new ArrayList<Map<String, Object>>();

	}

	
	private void addlistenr() {
		ListenerServer.setfinish(this, ll_fenlei_back);

		lv_fenlei_info.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				for (int i = 0; i < lv_fenlei_info.getChildCount(); i++) {
					View view = lv_fenlei_info.getChildAt(i);
					ImageView iv_kcfl_make = (ImageView) view
							.findViewById(R.id.iv_kcfl_make);
					TextView tv_kcfl_title = (TextView) view
							.findViewById(R.id.tv_kcfl_title);
					iv_kcfl_make.setBackgroundColor(getResources().getColor(
							R.color.dise));
					tv_kcfl_title.setBackgroundColor(getResources().getColor(
							R.color.dise));
				}
				View view = lv_fenlei_info.getChildAt(arg2);
				ImageView iv_kcfl_make = (ImageView) view
						.findViewById(R.id.iv_kcfl_make);
				TextView tv_kcfl_title = (TextView) view
						.findViewById(R.id.tv_kcfl_title);
				iv_kcfl_make.setBackgroundColor(getResources().getColor(
						R.color.main));
				tv_kcfl_title.setBackgroundColor(getResources().getColor(
						android.R.color.white));
				title=listtitle.get(arg2).get("title").toString();
				gvgetdata(arg2);
			}
		});

		gv_fenlei_info.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent=new Intent(FenleiActivity.this,FenleidelioActivity.class);
				intent.putExtra("parentid", parentid);
				intent.putExtra("coursename", listdetil.get(arg2).get("title").toString());
				intent.putExtra("courseid", listdetil.get(arg2).get("id").toString());
				intent.putExtra("title", title);
				startActivity(intent);
			}
		});
		
	}

	private void defaultshow(){
		title=listtitle.get(0).get("title").toString();
		gvgetdata(0);
//		View view = lv_fenlei_info.getChildAt(lv_fenlei_info.getFirstVisiblePosition()-0);
//		ImageView iv_kcfl_make = (ImageView) view.findViewById(R.id.iv_kcfl_make);
//		TextView tv_kcfl_title = (TextView) view.findViewById(R.id.tv_kcfl_title);
//		iv_kcfl_make.setBackgroundColor(getResources().getColor(
//				R.color.main));
//		tv_kcfl_title.setBackgroundColor(getResources().getColor(
//				android.R.color.white));
	}
	
	private void getdate() {

		final Dialog dialog=MyDialog.MyDialogloading(this);
		dialog.show();
		final Map<String, Object> map = new HashMap<String, Object>();

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
							map.put("id", jo.getString("PK_Class"));
							map.put("title", jo.getString("Class_Name"));
							listtitle.add(map);
						}
						KcflAdapter adapter = new KcflAdapter(
								FenleiActivity.this, listtitle);
						lv_fenlei_info.setAdapter(adapter);
						
						defaultshow();
						
					} catch (JSONException e) {
						Diaoxian.showerror(FenleiActivity.this, e.getMessage());
					}
					
				}else{
					Diaoxian.showerror(FenleiActivity.this, msg.obj .toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				String result = HttpUse.messageget("CourseStudy",
						"getRootCourseClass", map);
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
				}
				handler.sendMessage(msg);
			};
		}.start();

	}

	private void gvgetdata(final int index) {
		listdetil = new ArrayList<Map<String, Object>>();
		
		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						for (int i = 0; i < ja.length(); i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							JSONObject jo = ja.getJSONObject(i);
							map.put("id",jo.getString("PK_Class"));
							map.put("title", jo.getString("Class_Name"));
							listdetil.add(map);
						}
						SimpleAdapter adapter = new SimpleAdapter(
								FenleiActivity.this, listdetil,
								R.layout.item_kcflxx, new String[] { "title" },
								new int[] { R.id.tv_kcflxx_title });
						gv_fenlei_info.setAdapter(adapter);
					} catch (JSONException e) {
						Diaoxian.showerror(FenleiActivity.this, e.getMessage());
					}
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("courseClassId", listtitle.get(index).get("id").toString());
				parentid=listtitle.get(index).get("id").toString();
				String result = HttpUse.messageget("CourseStudy","getCourseClass", map);
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
					Diaoxian.showerror(FenleiActivity.this, e.getMessage());
				}
				handler.sendMessage(msg);
			};
		}.start();

	}
}
