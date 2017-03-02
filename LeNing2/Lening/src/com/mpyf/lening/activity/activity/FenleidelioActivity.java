package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.activity.adapter.FenleidetilAdapter;
import com.mpyf.lening.activity.adapter.KcflxxAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class FenleidelioActivity extends Activity {

	private LinearLayout ll_fenleidelio_back,ll_fenleidelio_showleibie,ll_fenleideilo_up;
	private RelativeLayout rl_fenleidelio_seek;
	private TextView tv_fenleidelio_title,tv_fenleidelio_zuixin,tv_fenleidelio_zuire,tv_fenleidelio_coursename;
	private EditText et_fenleidelio_seek;
	private ImageView iv_fenleidelio_seek;
	private ListView lv_fenleidelio_top;
	private List<Map<String,Object>> list;
	private List<Map<String, Object>> listdetil;
	private GridView gv_fenleidelio_fenlei;
	private String parentid="";
	private View v_up;
	private List<Map<String,Object>> searchlist;
	private String courseid="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new Xiaoxibeijing().changetop(this, R.color.main);
		setContentView(R.layout.activity_fenleidelio);
		init();
		showdata(courseid);
		addlistener();
	}
	private void init(){
		ll_fenleidelio_back=(LinearLayout) findViewById(R.id.ll_fenleidelio_back);
		ll_fenleidelio_showleibie=(LinearLayout) findViewById(R.id.ll_fenleidelio_showleibie);
		rl_fenleidelio_seek=(RelativeLayout) findViewById(R.id.rl_fenleidelio_seek);
		ll_fenleideilo_up=(LinearLayout) findViewById(R.id.ll_fenleideilo_up);
		gv_fenleidelio_fenlei=(GridView) findViewById(R.id.gv_fenleidelio_fenlei);
		tv_fenleidelio_title=(TextView) findViewById(R.id.tv_fenleidelio_title);
		tv_fenleidelio_zuixin=(TextView) findViewById(R.id.tv_fenleidelio_zuixin);
		tv_fenleidelio_zuire=(TextView) findViewById(R.id.tv_fenleidelio_zuire);
		tv_fenleidelio_coursename=(TextView) findViewById(R.id.tv_fenleidelio_coursename);
		lv_fenleidelio_top=(ListView) findViewById(R.id.lv_fenleidelio_top);
		
		et_fenleidelio_seek=(EditText) findViewById(R.id.et_fenleidelio_seek);
		iv_fenleidelio_seek=(ImageView) findViewById(R.id.iv_fenleidelio_seek);
		
		v_up=findViewById(R.id.v_up);
		parentid=getIntent().getStringExtra("parentid");
		tv_fenleidelio_coursename.setText(getIntent().getStringExtra("coursename"));
		tv_fenleidelio_title.setText(getIntent().getStringExtra("title"));
		ll_fenleideilo_up.setVisibility(View.GONE);
		
		courseid=getIntent().getStringExtra("courseid");
	}
	
	private void addlistener(){
		ListenerServer.setfinish(FenleidelioActivity.this, ll_fenleidelio_back);
		
		iv_fenleidelio_seek.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (rl_fenleidelio_seek.getVisibility()==View.INVISIBLE) {
					rl_fenleidelio_seek.setVisibility(View.VISIBLE);
					tv_fenleidelio_title.setVisibility(View.GONE);
				}else{
//					if (et_fenleidelio_seek.getText().toString().equals("")) {
//						FenleidetilAdapter adapter=new FenleidetilAdapter(FenleidelioActivity.this, null);
//						lv_fenleidelio_top.setAdapter(adapter);
//						return;
//					}
//					et_fenleidelio_seek.setVisibility(View.INVISIBLE);
//					tv_fenleidelio_title.setVisibility(View.VISIBLE);
//					tv_fenleidelio_zuixin.setTextColor(getResources().getColor(R.color.ptdl));
//					tv_fenleidelio_zuire.setTextColor(getResources().getColor(R.color.main));
//					showdata(getIntent().getStringExtra("courseid"));
					if (et_fenleidelio_seek.getText().toString().equals("")) {
						searchlist=new ArrayList<Map<String,Object>>();
						FenleidetilAdapter adapter=new FenleidetilAdapter(FenleidelioActivity.this, searchlist);
						lv_fenleidelio_top.setAdapter(adapter);
						Diaoxian.showerror(FenleidelioActivity.this, "请输入搜索内容");
					}else{
						showsearch();
					}
				}
				
				
			}
		});
		
		
		v_up.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ll_fenleideilo_up.setVisibility(View.GONE);
			}
		});
		
		ll_fenleidelio_showleibie.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				ll_fenleideilo_up.setVisibility(View.VISIBLE);
				v_up.getBackground().setAlpha(100);
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
								KcflxxAdapter adapter = new KcflxxAdapter(
										FenleidelioActivity.this, listdetil,tv_fenleidelio_coursename.getText().toString());
								gv_fenleidelio_fenlei.setAdapter(adapter);
							} catch (JSONException e) {
								
								Diaoxian.showerror(FenleidelioActivity.this, e.getMessage());
							}
						}
						
						gv_fenleidelio_fenlei.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								tv_fenleidelio_coursename.setText(listdetil.get(arg2).get("title").toString());
								ll_fenleideilo_up.setVisibility(View.GONE);
								rl_fenleidelio_seek.setVisibility(View.INVISIBLE);
								tv_fenleidelio_title.setVisibility(View.VISIBLE);
								showdata(listdetil.get(arg2).get("id").toString());
								courseid=listdetil.get(arg2).get("id").toString();
							}
						});
					}
				};

				new Thread() {
					@Override
					public void run() {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("courseClassId", parentid);
						
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
//							Diaoxian.showerror(FenleidelioActivity.this, e.getMessage());
						}
						handler.sendMessage(msg);
					};
				}.start();

			
				
			}
		});
		
		tv_fenleidelio_zuixin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				tv_fenleidelio_zuixin.setTextColor(getResources().getColor(R.color.main));
				tv_fenleidelio_zuire.setTextColor(getResources().getColor(R.color.ptdl));
				getzuire(courseid);
			}
		});
		
		tv_fenleidelio_zuire.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				tv_fenleidelio_zuixin.setTextColor(getResources().getColor(R.color.ptdl));
				tv_fenleidelio_zuire.setTextColor(getResources().getColor(R.color.main));
				showdata(courseid);
			}
		});
		
	}
	
	private void showsearch(){
		searchlist=new ArrayList<Map<String,Object>>();
		
		for(int i=0;i<list.size();i++){
			boolean youdaxie=list.get(i).get("title").toString().indexOf(et_fenleidelio_seek.getText().toString().toUpperCase())!=-1;
			boolean youxiaoxie=list.get(i).get("title").toString().indexOf(et_fenleidelio_seek.getText().toString().toLowerCase())!=-1;
			if(youdaxie||youxiaoxie){
				searchlist.add(list.get(i));
			}
		}
		FenleidetilAdapter adapter=new FenleidetilAdapter(FenleidelioActivity.this, searchlist);
		lv_fenleidelio_top.setAdapter(adapter);
		
		Diaoxian.showerror(FenleidelioActivity.this, "共找到"+searchlist.size()+"项");
		
		lv_fenleidelio_top.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent=new Intent(FenleidelioActivity.this,NotbuycourseActivity.class);
				intent.putExtra("id", searchlist.get(arg2).get("id").toString());
				startActivity(intent);
			}
		});
	}
	
	private void getzuire(final String courseid){

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
							map.put("id", jo.getString("PK_Course"));
							map.put("title", jo.getString("CourseName"));
							map.put("scan", jo.getString("BuyNum"));
							map.put("cost", jo.getString("Amount"));
							map.put("image",Setting.apiUrl+ jo.getString("PicUrl"));
							map.put("buyWay", jo.getInt("BuyWay"));
							list.add(map);
						}
						
					} catch (JSONException e) {
						Diaoxian.showerror(FenleidelioActivity.this, e.getMessage());
					}
					
					FenleidetilAdapter adapter=new FenleidetilAdapter(FenleidelioActivity.this, list);
					lv_fenleidelio_top.setAdapter(adapter);
					
					lv_fenleidelio_top.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
								long arg3) {
							Intent intent=new Intent(FenleidelioActivity.this, NotbuycourseActivity.class);
							intent.putExtra("id", list.get(arg2).get("id").toString());
							startActivity(intent);
						}
					});
				}else{
					
				}
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("courseClassId", courseid);
				map.put("page", 1);
				map.put("pageSize", 254);
				
				Message msg=new Message();
				
				String result=HttpUse.messageget("CourseStudy", "queryCourseNewByClassId", map);
				try {
					JSONObject jo=new JSONObject(result);
					if(jo.getBoolean("result")){
						msg.what=1;
						msg.obj=jo.getString("data");
					}else{
						msg.obj=jo.getString("message");
					}
				} catch (JSONException e) {
					return;
				}
				handler.sendMessage(msg);
			};
		}.start();
		
		
	
	}
	
	private void showdata(final String courseid){
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
								map.put("id", jo.getString("PK_Course"));
								map.put("title", jo.getString("CourseName"));
								map.put("scan", jo.getString("BuyNum"));
								map.put("cost", jo.getString("Amount"));
								map.put("image",Setting.apiUrl+ jo.getString("PicUrl"));
								map.put("buyWay", jo.getInt("BuyWay"));
								list.add(map);
							}
							
						} catch (JSONException e) {
							Diaoxian.showerror(FenleidelioActivity.this, e.getMessage());
						}
						
						FenleidetilAdapter adapter=new FenleidetilAdapter(FenleidelioActivity.this, list);
						lv_fenleidelio_top.setAdapter(adapter);
						
						lv_fenleidelio_top.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
								Intent intent=new Intent(FenleidelioActivity.this, NotbuycourseActivity.class);
								intent.putExtra("id", list.get(arg2).get("id").toString());
								startActivity(intent);
							}
						});
					}else{
						
					}
				}
			};
			
			new Thread(){
				@Override
				public void run() {
					
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("courseClassId", courseid);
					map.put("page", 1);
					map.put("pageSize", 254);
					
					Message msg=new Message();
					
					String result=HttpUse.messageget("CourseStudy", "queryCourseByClassId", map);
					try {
						JSONObject jo=new JSONObject(result);
						if(jo.getBoolean("result")){
							msg.what=1;
							msg.obj=jo.getString("data");
						}else{
							msg.obj=jo.getString("message");
						}
					} catch (JSONException e) {
						return;
					}
					handler.sendMessage(msg);
				};
			}.start();
			
			
		}
}
