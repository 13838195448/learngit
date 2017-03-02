package com.mpyf.lening.activity.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.Quanjubianliang;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.interfaces.bean.Parame.ResFaq;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class AddqaAcitvity extends Activity {
	
	private LinearLayout ll_addnote_back,ll_addnote_top;
	private TextView tv_addnote_ok,tv_addnote_title;
	private EditText et_addnote_context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		new Xiaoxibeijing().changetop(this,R.color.main);
		setContentView(R.layout.activity_addnote);
		init();
		showinfo();
		addlistener();
	}
	
	private void init(){
		ll_addnote_back=(LinearLayout) findViewById(R.id.ll_addnote_back);
		
		tv_addnote_title=(TextView) findViewById(R.id.tv_addnote_title);
		
		ll_addnote_top=(LinearLayout) findViewById(R.id.ll_addnote_top);
		tv_addnote_ok=(TextView) findViewById(R.id.tv_addnote_ok);
		et_addnote_context=(EditText) findViewById(R.id.et_addnote_context);
		
		et_addnote_context.setHint("写问题");
	}
	
	private void showinfo(){
		tv_addnote_title.setText("撰写问题");
		if(getIntent().getStringExtra("context")==null){
			tv_addnote_ok.setText("完成");
			tv_addnote_title.setText("撰写问题");
			et_addnote_context.setEnabled(true);
		}else{
			tv_addnote_ok.setText("编辑");
			tv_addnote_title.setText("问题详情");
			et_addnote_context.setEnabled(false);
		}
		
		ll_addnote_top.setVisibility(View.GONE);
		et_addnote_context.setText(getIntent().getStringExtra("context")==null?"":getIntent().getStringExtra("context"));
	}
	
	private void addlistener(){
		
		ll_addnote_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(et_addnote_context.getText().toString().trim().equals("")){
					finish();
				}else{
					issave();
				}
			}
		});
		
		
		tv_addnote_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				postresqa();
			}
		});
		
	}
	
	private void issave(){
		Dialog dialog=MyDialog.MyDialogShow(this, R.layout.popup_isok, 1f);
		Button bt_isok_quie=(Button) dialog.findViewById(R.id.bt_isok_quie);
		Button bt_isok_ok=(Button) dialog.findViewById(R.id.bt_isok_ok);
		
		ListenerServer.setfinish(AddqaAcitvity.this, bt_isok_quie);
		
		bt_isok_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				postresqa();
			}
		});
		
		dialog.show();
	}
	
	
	private void postresqa(){
		
		if(tv_addnote_ok.getText().toString().equals("编辑")){
			et_addnote_context.setEnabled(true);
			tv_addnote_ok.setText("完成");
		}else{
			if(et_addnote_context.getText().toString().trim().equals("")){
				Diaoxian.showerror(AddqaAcitvity.this, "请输入问题内容");
				return;
			}
			
			final Dialog dialog=MyDialog.MyDialogloading(AddqaAcitvity.this);
			dialog.show();
			
			final Handler handler=new Handler(){
				@Override
				public void handleMessage(Message msg) {
					dialog.dismiss();
					
					if(msg.what==1){
						Diaoxian.showerror(AddqaAcitvity.this, getIntent().getStringExtra("id")==null?"保存成功":"修改成功");
						finish();
					}else{
						Diaoxian.showerror(AddqaAcitvity.this, msg.obj.toString());
					}
					
				}
			};
			
			new Thread(){
				@Override
				public void run() {
					Message msg = new Message();

					Date date = new Date();
					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy-MM-dd");
					String datestr = format.format(date);

					ResFaq resfaq=new ResFaq();
					
					resfaq.setAns_CONTENT("");
					resfaq.setAns_nickname("");
					resfaq.setAns_Time("");
					resfaq.setAns_username("");
					resfaq.setBeAnswered(false);
					resfaq.setFAQ_CONTENT(et_addnote_context.getText().toString());
					resfaq.setNickname(Setting.currentUser.getNickname());
					resfaq.setFAQ_Time(datestr);
					resfaq.setPK_Course(Quanjubianliang.courseid);
					resfaq.setPK_Faq(getIntent().getStringExtra("id")==null?"":getIntent().getStringExtra("id"));
					resfaq.setPK_Res(getIntent().getStringExtra("resId"));
					resfaq.setPk_user(Setting.currentUser.getPk_user());
					resfaq.setUserName(Setting.currentUser.getUsername());
					
					String result = HttpUse.messagepost("CourseStudy","saveResFaq", resfaq);

					try {
						JSONObject jo = new JSONObject(result);
						if (jo.getBoolean("result")) {
							msg.what = 1;
						}
						msg.obj = jo.getString("message");

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						msg.obj = e.getMessage();
					}
					handler.sendMessage(msg);
				}
			}.start();
		}
		
	}
}
