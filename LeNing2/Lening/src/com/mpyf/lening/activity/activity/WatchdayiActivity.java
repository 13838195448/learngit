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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.Quanjubianliang;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.interfaces.bean.Parame.ResFaq;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class WatchdayiActivity extends Activity {

	private LinearLayout ll_mynote_back,ll_mynote_top;
	private RelativeLayout ll_mynote_head;
	private TextView tv_mynote_change,tv_mynote_from,tv_mynote_time,tv_mynote_title;
	private EditText tv_mynote_name,tv_mynote_context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		new Xiaoxibeijing().changetop(this, R.color.main);
		setContentView(R.layout.activity_mynote);
		init();
		showinfo();
		addlistener();
	}
	
	private void init(){
		ll_mynote_back=(LinearLayout) findViewById(R.id.ll_mynote_back);
		ll_mynote_top=(LinearLayout) findViewById(R.id.ll_mynote_top);
		ll_mynote_head=(RelativeLayout) findViewById(R.id.ll_mynote_head);
		tv_mynote_name=(EditText) findViewById(R.id.tv_mynote_name);
		tv_mynote_change=(TextView) findViewById(R.id.tv_mynote_change);
		tv_mynote_context=(EditText) findViewById(R.id.tv_mynote_context);
		tv_mynote_from=(TextView) findViewById(R.id.tv_mynote_from);
		tv_mynote_time=(TextView) findViewById(R.id.tv_mynote_time);
		tv_mynote_title=(TextView) findViewById(R.id.tv_mynote_title);
		ll_mynote_head.setVisibility(View.GONE);
		tv_mynote_title.setText("问题详情");
		tv_mynote_name.setEnabled(false);
		tv_mynote_context.setEnabled(false);
	}
	
	private void showinfo(){
		tv_mynote_context.setText(getIntent().getStringExtra("context"));
		tv_mynote_from.setText(getIntent().getStringExtra("from"));
		tv_mynote_time.setText(getIntent().getStringExtra("time"));
		
	}
	private void addlistener(){
		
		ll_mynote_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(tv_mynote_change.getText().toString().equals("完成")){
					isfinish();
				}else{
					finish();
				}
			}
		});
		
		tv_mynote_change.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(tv_mynote_change.getText().toString().equals("编辑")){
					tochange();
				}else if(tv_mynote_change.getText().toString().equals("完成")){
					savenote();
				}
			}
		});
	}
	
	private void tochange(){
		tv_mynote_change.setText("完成");
		ll_mynote_top.setVisibility(View.GONE);
		tv_mynote_name.setEnabled(true);
		tv_mynote_context.setEnabled(true);
	}
	
	private void savenote(){
		final Dialog dialog = MyDialog.MyDialogloading(WatchdayiActivity.this);
		dialog.show();

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				
				if (msg.what == 1) {
					Diaoxian.showerror(WatchdayiActivity.this,"修改成功");
					finish();
				}else{
					Diaoxian.showerror(WatchdayiActivity.this, msg.obj.toString());
				}

			}
		};

		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
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
				resfaq.setFAQ_CONTENT(tv_mynote_context.getText().toString());
				resfaq.setNickname(Setting.currentUser.getNickname());
				resfaq.setFAQ_Time(datestr);
				resfaq.setPK_Course(Quanjubianliang.courseid);
				resfaq.setPK_Faq(getIntent().getStringExtra("id"));
				resfaq.setPK_Res(getIntent().getStringExtra("PK_Res"));
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
	
	
	private void isfinish(){
		Dialog dialog=MyDialog.MyDialogShow(this, R.layout.popup_isok, 1.0f);
		TextView tv_isok_title=(TextView) dialog.findViewById(R.id.tv_isok_title);
		tv_isok_title.setText("是否放弃编辑？");
		
		Button bt_isok_ok=(Button) dialog.findViewById(R.id.bt_isok_ok);
		Button bt_isok_quie=(Button) dialog.findViewById(R.id.bt_isok_quie);
		
		bt_isok_quie.setText("放弃");
		bt_isok_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				savenote();
			}
		});
		bt_isok_quie.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		dialog.show();
	}
}
