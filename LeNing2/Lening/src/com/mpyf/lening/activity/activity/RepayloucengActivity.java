package com.mpyf.lening.activity.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.AsyncBitmapLoader;
import com.mpyf.lening.Jutil.CommonUtils;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ImageOptions;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.MyListView;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.activity.adapter.RepayloucengAdapter;
import com.mpyf.lening.interfaces.bean.Parame.Answer;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * @author Administrator
 *回复楼层
 */
public class RepayloucengActivity extends Activity {

	private LinearLayout ll_repaylouceng_back,ll_repaylouceng_repay;
	private TextView tv_repaylouceng_title,tv_repaylouceng_name,tv_repaylouceng_time,tv_repaylouceng_context,tv_repaylouceng_good,tv_repaylouceng_bad;
	private ImageView iv_repaylouceng_touxiang,iv_repaylouceng_good,iv_repaylouceng_bad;
	private MyListView lv_repaylouceng;
	private EditText et_repaylouceng_title;
	private Button bt_repaylouceng_ok;
	private List<Map<String,Object>> list;
	private TextView tv_repaylouceng_caina;
	private DisplayImageOptions options;
	private Dialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//new Xiaoxibeijing().changetop(this, R.color.main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_repaylouceng);
		init();
		showinfo();
		addlistener();
	}
	
	private void init(){
		ll_repaylouceng_back=(LinearLayout) findViewById(R.id.ll_repaylouceng_back);
		tv_repaylouceng_title=(TextView) findViewById(R.id.tv_repaylouceng_title);
		iv_repaylouceng_touxiang=(ImageView) findViewById(R.id.iv_repaylouceng_touxiang);
		tv_repaylouceng_name=(TextView) findViewById(R.id.tv_repaylouceng_name);
		tv_repaylouceng_time=(TextView) findViewById(R.id.tv_repaylouceng_time);
		tv_repaylouceng_context=(TextView) findViewById(R.id.tv_repaylouceng_context);
		iv_repaylouceng_good=(ImageView) findViewById(R.id.iv_repaylouceng_good);
		tv_repaylouceng_good=(TextView) findViewById(R.id.tv_repaylouceng_good);
		iv_repaylouceng_bad=(ImageView) findViewById(R.id.iv_repaylouceng_bad);
		tv_repaylouceng_bad=(TextView) findViewById(R.id.tv_repaylouceng_bad);
		ll_repaylouceng_repay=(LinearLayout) findViewById(R.id.ll_repaylouceng_repay);
		lv_repaylouceng=(MyListView) findViewById(R.id.lv_repaylouceng);
		et_repaylouceng_title=(EditText) findViewById(R.id.et_repaylouceng_title);
		bt_repaylouceng_ok=(Button) findViewById(R.id.bt_repaylouceng_ok);
		tv_repaylouceng_caina = (TextView) findViewById(R.id.tv_repaylouceng_caina);
		tv_repaylouceng_caina.setVisibility(View.VISIBLE);
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.icon_defualt) 
		.showImageOnFail(R.drawable.icon_defualt)
		.showImageForEmptyUri(R.drawable.icon_defualt)
		.cacheInMemory(true) 
		.cacheOnDisk(true) 
		 .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
        .bitmapConfig(Bitmap.Config.ALPHA_8)
		.build(); 
	}
	
	private void showinfo(){
		Intent intent=getIntent();
		tv_repaylouceng_title.setText("回复"+intent.getStringExtra("name"));
		
		
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(Setting.apiUrl+"new-pages/PersonalPhoto/"+getIntent().getStringExtra("uid")+".jpg", iv_repaylouceng_touxiang,options);
		//AsyncBitmapLoader.setRoundImage(iv_repaylouceng_touxiang, Setting.apiUrl+"new-pages/PersonalPhoto/"+getIntent().getStringExtra("uid")+".jpg");
		tv_repaylouceng_name.setText(intent.getStringExtra("name"));
		tv_repaylouceng_time.setText(intent.getStringExtra("time"));
		tv_repaylouceng_context.setText(intent.getStringExtra("anscontent"));
		
		final int goodnum = intent.getIntExtra("goodnum", 0);
		final int badnum = intent.getIntExtra("badnum", 0);
		tv_repaylouceng_good.setText(goodnum+"");
		tv_repaylouceng_bad.setText(badnum+"");
		final String aid = intent.getStringExtra("ansid");
		iv_repaylouceng_good.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				final	Handler handler = new Handler(){
					@Override
					public void handleMessage(Message msg) {
						if(msg.what ==1){
							tv_repaylouceng_good.setText((goodnum+1)+"");
						}
					}
				};
				new Thread(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Map<String,Object> map=new HashMap<String, Object>();
						
						map.put("AnsID",aid);
						map.put("praiseType", 1);
						
						Message msg=new Message();
						String result=HttpUse.messageget("QueAndAns", "savePraise", map);
						JSONObject jo;
						try {
							jo = new JSONObject(result);
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
		});
		
		iv_repaylouceng_bad.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {


				final	Handler handler = new Handler(){
					@Override
					public void handleMessage(Message msg) {
						if(msg.what ==1){
							tv_repaylouceng_bad.setText((badnum+1)+"");
						}
					}
				};
				new Thread(){
					@Override
					public void run() {
						Map<String,Object> map=new HashMap<String, Object>();
						
						map.put("AnsID",aid);
						map.put("praiseType", 2);
						
						Message msg=new Message();
						String result=HttpUse.messageget("QueAndAns", "savePraise", map);
						JSONObject jo;
						try {
							jo = new JSONObject(result);
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
		});
		
		int state = intent.getIntExtra("ANS_STATE", 0);
		int qstate = intent.getIntExtra("qstate", 0);
		final String qid = intent.getStringExtra("queid");
		
		String qtruename =getIntent().getStringExtra("qtruename");
		if(qstate==0){
			
			tv_repaylouceng_caina.setVisibility(View.VISIBLE);
		}else if(qstate==1&&state==1){
			tv_repaylouceng_caina.setVisibility(View.VISIBLE);
			tv_repaylouceng_caina.setBackgroundResource(R.drawable.know_icon_good);
		}
		if((!Setting.currentUser.getTruename().equals(qtruename))&&state==0){
			tv_repaylouceng_caina.setVisibility(View.GONE);
		}
			switch (state) {
			case 0:
				tv_repaylouceng_caina.setText("采纳此回答");
				if(Setting.currentUser.getTruename().equals(qtruename)){
					
					tv_repaylouceng_caina.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							
							final	Handler handler = new Handler(){
								@Override
								public void handleMessage(Message msg) {
									if(msg.what ==1){
										tv_repaylouceng_caina.setText("");
										tv_repaylouceng_caina.setBackgroundResource(R.drawable.know_icon_good);
									}
								}
							};
							new Thread(){
								@Override
								public void run() {
									// TODO Auto-generated method stub
									Map<String,Object> map=new HashMap<String, Object>();
									map.put("QueID", qid);
									map.put("AnsID",aid);
									
									
									Message msg=new Message();
									String result=HttpUse.messageget("QueAndAns", "adoptAns", map);
									JSONObject jo;
									try {
										jo = new JSONObject(result);
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
					});
				}else{
					tv_repaylouceng_caina.setVisibility(View.GONE);
				}
				break;
			case 1:
				tv_repaylouceng_caina.setText("");
				tv_repaylouceng_caina.setBackgroundResource(R.drawable.know_icon_good);
				break;
			default:
				break;
			}
		
		
		if (intent.getStringExtra("ishavechild").equals("1")) {
			getchildrepay(intent.getStringExtra("ansid"));
		}
		
	}
	
	private void getchildrepay(final String aid){
		
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what==1) {
					try {
						list=new ArrayList<Map<String,Object>>();
						JSONArray ja=new JSONArray(msg.obj.toString());
						for(int i=0;i<ja.length();i++){
							JSONObject jo=ja.getJSONObject(i);
							Map<String,Object> map=new HashMap<String, Object>();
							map.put("trueName", jo.getString("trueName"));
							map.put("ANS_CONTENT", jo.getString("ANS_CONTENT"));
							map.put("ansTime", jo.getString("ansTime"));
							map.put("p_TrueName",jo.getString("p_TrueName"));
							list.add(map);
						}
						
						RepayloucengAdapter adapter=new RepayloucengAdapter(RepayloucengActivity.this, list);
						lv_repaylouceng.setAdapter(adapter);
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Diaoxian.showerror(RepayloucengActivity.this,e.getMessage());
					}
				}else{
					Diaoxian.showerror(RepayloucengActivity.this, msg.obj.toString());
				}
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("ansID",aid);
				map.put("page", 1);
				map.put("pageSize", 99);
				
				Message msg=new Message();
				String result=HttpUse.messageget("QueAndAns", "getAnswerChild", map);
				JSONObject jo;
				try {
					jo = new JSONObject(result);
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
	
	private void addlistener(){
		ListenerServer.setfinish(this, ll_repaylouceng_back);
		
		et_repaylouceng_title.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				if (et_repaylouceng_title.getText().toString().equals("")) {
					bt_repaylouceng_ok.setBackgroundResource(R.drawable.course_btn_send_d);
					bt_repaylouceng_ok.setClickable(false);
				}else{
					bt_repaylouceng_ok.setBackgroundResource(R.drawable.course_btn_send);
					bt_repaylouceng_ok.setClickable(true);
				}
			}
		});
		
		bt_repaylouceng_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				if(TextUtils.isEmpty(et_repaylouceng_title.getText().toString())){
					Diaoxian.showerror(RepayloucengActivity.this, "回复内容不能为空!");
				}else{
					
					if(!CommonUtils.isFastDoubleClick()){
						
						final Handler handler=new Handler(){
							@Override
							public void handleMessage(Message msg) {
								if (msg.what==1) {
									Diaoxian.showerror(RepayloucengActivity.this, "回帖成功");
									tv_repaylouceng_name.setText("");
									finish();
									getchildrepay(getIntent().getStringExtra("ansid"));
								}else{
									Diaoxian.showerror(RepayloucengActivity.this, msg.obj.toString());
								}
							}
						};
						
						new Thread(){
							@Override
							public void run() {
								Date date = new Date();
								SimpleDateFormat fromat = new SimpleDateFormat(
										"yyyy-MM-dd HH:mm:ss");
								String fdate = fromat.format(date);
								
								Answer answer=new Answer();
								answer.setANS_CONTENT(et_repaylouceng_title.getText().toString());
								answer.setANS_STATE(0);
								answer.setAnsTime(fdate);
								answer.setBAD_NUM(0);
								answer.setGOOD_NUM(0);
								answer.setIs_child(1);
								answer.setIs_havechild(0);
								answer.setNickname(Setting.currentUser.getNickname());
								answer.setP_Nickname(tv_repaylouceng_name.getText().toString());
								answer.setP_pk_Ans(getIntent().getStringExtra("ansid"));
								answer.setP_Pk_user(getIntent().getIntExtra("uid",0));
								answer.setP_TrueName(getIntent().getStringExtra("name"));
								answer.setP_UserName("");
								answer.setPK_Ans("");
								answer.setPK_Que(getIntent().getStringExtra("queid"));
								answer.setPk_user(Setting.currentUser.getPk_user());
								answer.setTrueName(Setting.currentUser.getTruename());
								answer.setUserName(Setting.currentUser.getUsername());
								
								Message msg=new Message();
								
								String result=HttpUse.messagepost("QueAndAns", "saveAnswer", answer);
								
								JSONObject jo;
								try {
									jo = new JSONObject(result);
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
					}else{
						Diaoxian.showerror(RepayloucengActivity.this, "请不要重复提交");
						
					}
				}
				
			
			}
		});
	}
}
