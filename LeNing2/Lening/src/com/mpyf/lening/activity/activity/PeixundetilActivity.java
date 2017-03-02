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
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.AsyncBitmapLoader;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ImageOptions;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.MyListView;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class PeixundetilActivity extends Activity {

	private RelativeLayout rl_peixundetil_back;
	private LinearLayout ll_peixundetil_passtest;
	private MyListView lv_peixundetil_passcourse,
			lv_peixundetil_info;
	private List<Map<String, Object>> listcourse, listinfo;
	private ScrollView sv_mypeixundetil;

	private ImageView iv_mypeixundetil_top;
	
	private TextView tv_mypeixundetil_title, tv_mypeixundetil_date,
			tv_mypeixundetil_time, tv_peixundetil_place, tv_peixundetil_seats,
			tv_mypeixundetil_teacher, tv_mypeixundetil_le,
			tv_mypeixundetil_xuefei,tv_peixundetil_join,tv_peixundetil_passcourse,tv_peixundetil_passtext,tv_peixundetil_needtest,tv_peixundetil_testpassed;

	private String id="";
	private DisplayImageOptions options;
	private WebView wv_html;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	//	new Xiaoxibeijing().changetop(this, R.color.main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_peixundetil);
		init();
		showinfo();
		addlistener();
	}

	private void init(){
		rl_peixundetil_back=(RelativeLayout) findViewById(R.id.rl_peixundetil_back);
		lv_peixundetil_passcourse=(MyListView) findViewById(R.id.lv_peixundetil_passcourse);
		ll_peixundetil_passtest=(LinearLayout) findViewById(R.id.ll_peixundetil_passtest);
		lv_peixundetil_info=(MyListView) findViewById(R.id.lv_peixundetil_info);
		sv_mypeixundetil=(ScrollView) findViewById(R.id.sv_mypeixundetil);
		
		iv_mypeixundetil_top=(ImageView) findViewById(R.id.iv_mypeixundetil_top);
		
		tv_mypeixundetil_title=(TextView) findViewById(R.id.tv_mypeixundetil_title);
		tv_mypeixundetil_date=(TextView) findViewById(R.id.tv_mypeixundetil_date);
		tv_mypeixundetil_time=(TextView) findViewById(R.id.tv_mypeixundetil_time);
		tv_peixundetil_place=(TextView) findViewById(R.id.tv_peixundetil_place);
		tv_peixundetil_seats=(TextView) findViewById(R.id.tv_peixundetil_seats);
		tv_mypeixundetil_teacher=(TextView) findViewById(R.id.tv_mypeixundetil_teacher);
		tv_mypeixundetil_le=(TextView) findViewById(R.id.tv_mypeixundetil_le);
		tv_mypeixundetil_xuefei=(TextView) findViewById(R.id.tv_mypeixundetil_xuefei);
		tv_peixundetil_join=(TextView) findViewById(R.id.tv_peixundetil_join);
		
		tv_peixundetil_passcourse=(TextView) findViewById(R.id.tv_peixundetil_passcourse);
		tv_peixundetil_passtext=(TextView) findViewById(R.id.tv_peixundetil_passtext);
		tv_peixundetil_needtest=(TextView) findViewById(R.id.tv_peixundetil_needtest);
		tv_peixundetil_testpassed=(TextView) findViewById(R.id.tv_peixundetil_testpassed);
		
		wv_html = (WebView) findViewById(R.id.wv_html);
		
		sv_mypeixundetil.smoothScrollTo(0, 0);
	}

	private void showinfo() {

		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.defaultimage) 
		.showImageOnFail(R.drawable.defaultimage)
		.showImageForEmptyUri(R.drawable.defaultimage)
		.cacheInMemory(true) 
		.cacheOnDisk(true) 
		 .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
        .bitmapConfig(Bitmap.Config.ALPHA_8)
		.build(); 
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						JSONObject jo = new JSONObject(msg.obj.toString());

						id=jo.getString("PK_Act");
						
						tv_mypeixundetil_title.setText(jo.getString("act_Name"));
						tv_mypeixundetil_date.setText("培训日期:"+jo.getString("start_Time")+"至"+jo.getString("end_Time"));
						
						String time=jo.getString("bm_End_Time");
						if(time.length()>=10){
							time=time.substring(0, 10);
							time=time.replace("-", "/");
						}
						
						tv_mypeixundetil_time.setText("报名截止日期:"+time);
						tv_peixundetil_place.setText(jo.getString("place"));
						tv_peixundetil_seats.setText(jo.getString("seats"));
						tv_mypeixundetil_teacher.setText(jo.getString("lecturer"));
						tv_mypeixundetil_le.setText(jo.getString("application_Fee")+"乐币");
						tv_mypeixundetil_xuefei.setText(jo.getString("fees"));
						
						//AsyncBitmapLoader.sethoneImage(iv_mypeixundetil_top, Setting.apiUrl+jo.getString("picUrl"));
						ImageLoader imageLoader = ImageLoader.getInstance();
						imageLoader.displayImage( Setting.apiUrl+jo.getString("picUrl"), iv_mypeixundetil_top, options);
						
						iv_mypeixundetil_top.setAlpha(180);
						String jieshao=jo.getString("remark");
						String dizhi=jo.getString("address");
						String xiangqing=jo.getString("details");
						
						// TODO Auto-generated catch block
						String[] infos={jieshao,dizhi,xiangqing};
						
						wv_html.loadDataWithBaseURL(null, xiangqing, "text/html", "utf-8", null);
						wv_html.getSettings().setJavaScriptEnabled(true);  
						wv_html.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);  
						wv_html.setHorizontalScrollBarEnabled(false);  
						wv_html.getSettings().setSupportZoom(true);  
						wv_html.getSettings().setBuiltInZoomControls(true);  
						wv_html.setInitialScale(70);  
						wv_html.setHorizontalScrollbarOverlay(true);  
						
						peixuninfo(infos);
						
						if(jo.getBoolean("isCourse")){
							needcourse();
						}else{
							tv_peixundetil_passcourse.setVisibility(View.GONE);
							lv_peixundetil_passcourse.setVisibility(View.GONE);
						}
						
						if(jo.getBoolean("isExam")){
							needtest();
						}else{
							tv_peixundetil_passtext.setVisibility(View.GONE);
							ll_peixundetil_passtest.setVisibility(View.GONE);
						}
						
						
						
					} catch (JSONException e) {
						Diaoxian.showerror(PeixundetilActivity.this,
								e.getMessage());
					}
				} else {
					Diaoxian.showerror(PeixundetilActivity.this,
							msg.obj.toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();

				map.put("trainID", getIntent().getStringExtra("trainID"));

				Message msg = new Message();

				String result = HttpUse.messageget("TrainACt", "getTrainAct",
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
//培训详情
	// TODO Auto-generated catch block
	private void peixuninfo(String[] info){
		listinfo=new ArrayList<Map<String,Object>>();
		 String[] infotitle={"培训介绍","培训地址"};
		 for(int i=0;i<2;i++){
		 Map<String,Object> map=new HashMap<String, Object>();
		 map.put("title", infotitle[i]);
		 map.put("context", Html.fromHtml(info[i]));
		 listinfo.add(map);
		 }
		 SimpleAdapter adapter2=new SimpleAdapter(this, listinfo,
		 R.layout.item_peixuninfo, new String[]{"title","context"}, new
		 int[]{R.id.tv_itempeixuninfo_title,R.id.tv_itempeixuninfo_context});
		 lv_peixundetil_info.setAdapter(adapter2);
	}
	
	private void addlistener() {
		ListenerServer.setfinish(this, rl_peixundetil_back);
		
		tv_peixundetil_join.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				baoming();
			}
		});
		
	}
	
	private void baoming(){
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==1){
					finish();
					Diaoxian.showerror(PeixundetilActivity.this, "报名成功请等待审核，扣除"+tv_mypeixundetil_le.getText().toString()+"乐币");
				}else{
					Diaoxian.showerror(PeixundetilActivity.this, msg.obj.toString());
				}
			}
			
		};
		
		new Thread(){
			@Override
			public void run() {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("pk_act", id);
				
				Message msg=new Message();
				
				String result=HttpUse.messageget("TrainACt", "participateTrain", map);
				
				try {
					JSONObject jo=new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what=1;
						
					}
					msg.obj=jo.getString("message");
					
				} catch (JSONException e) {
					msg.obj=e.getMessage();
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	private void needtest(){

		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==1){
					try {
						JSONObject jo=new JSONObject(msg.obj.toString());
						tv_peixundetil_needtest.setText(jo.getString("exam_Name"));
						tv_peixundetil_testpassed.setText(jo.getInt("results")==1?"通过":"未通过");
					} catch (Exception e) {
						Diaoxian.showerror(PeixundetilActivity.this, e.getMessage());
					}
				}else{
					Diaoxian.showerror(PeixundetilActivity.this, msg.obj.toString());
				}
			}
			
		};
		
		new Thread(){
			@Override
			public void run() {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("trainID",id);
				
				Message msg=new Message();
				
				String result=HttpUse.messageget("TrainACt", "needExam", map);
				
				try {
					JSONObject jo=new JSONObject(result);
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
	
	private void needcourse(){

		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==1){
					try {
						JSONArray ja=new JSONArray(msg.obj.toString());
						 listcourse=new ArrayList<Map<String,Object>>();
							 for(int i=0;i<ja.length();i++){
							 Map<String,Object> map=new HashMap<String, Object>();
							 JSONObject jo=ja.getJSONObject(i);
							 map.put("id", jo.getString("PK_Course"));
							 map.put("course",jo.getString("courseName"));
							 map.put("listened", "已学习 "+jo.getString("speed")+"%");
							 listcourse.add(map);
							 }
							 SimpleAdapter adapter=new SimpleAdapter(PeixundetilActivity.this, listcourse, R.layout.item_peixuncourse, new String[]{"course","listened"}, 
									 new  int[]{R.id.tv_peixuncourse_course,R.id.tv_peixuncourse_listened});
							 lv_peixundetil_passcourse.setAdapter(adapter);
							 
							 lv_peixundetil_passcourse.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									Intent intent=new Intent(PeixundetilActivity.this, NotbuycourseActivity.class);
									intent.putExtra("id", listcourse.get(arg2).get("id").toString());
									startActivity(intent);
								}
							});
					} catch (JSONException e) {
						Diaoxian.showerror(PeixundetilActivity.this, e.getMessage());
					}
				}else{
					Diaoxian.showerror(PeixundetilActivity.this, msg.obj.toString());
				}
			}
			
		};
		
		new Thread(){
			@Override
			public void run() {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("trainID",id);
				
				Message msg=new Message();
				
				String result=HttpUse.messageget("TrainACt", "needCourse", map);
				
				try {
					JSONObject jo=new JSONObject(result);
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
	
}
