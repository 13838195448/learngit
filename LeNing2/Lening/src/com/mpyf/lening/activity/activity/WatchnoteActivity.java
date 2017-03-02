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
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.interfaces.bean.Parame.Note;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class WatchnoteActivity extends Activity {

	private LinearLayout ll_mynote_back,ll_mynote_top;
	private TextView tv_mynote_change,tv_mynote_from,tv_mynote_time;
	private EditText tv_mynote_name,tv_mynote_context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
		tv_mynote_name=(EditText) findViewById(R.id.tv_mynote_name);
		tv_mynote_change=(TextView) findViewById(R.id.tv_mynote_change);
		tv_mynote_context=(EditText) findViewById(R.id.tv_mynote_context);
		tv_mynote_from=(TextView) findViewById(R.id.tv_mynote_from);
		tv_mynote_time=(TextView) findViewById(R.id.tv_mynote_time);
		
		tv_mynote_name.setEnabled(false);
		tv_mynote_context.setEnabled(false);
	}
	
	private void showinfo(){
		tv_mynote_name.setText(getIntent().getStringExtra("title"));
		tv_mynote_context.setText(getIntent().getStringExtra("context"));
		tv_mynote_from.setText(getIntent().getStringExtra("from"));
		tv_mynote_time.setText(getIntent().getStringExtra("time"));
		
	}
	private void addlistener(){
		
		ll_mynote_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
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
		
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==1){
					Diaoxian.showerror(WatchnoteActivity.this, "修改成功");
					finish();
				}else{
					Diaoxian.showerror(WatchnoteActivity.this, msg.obj.toString());
				}
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				Message msg=new Message();
				
				Date date=new Date();
//				SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
				String datestr=format.format(date);
				Note note=new Note();
				note.setNOTE_CONTENT(tv_mynote_context.getText().toString());
				note.setNOTE_Time(datestr);
				note.setNOTE_TITLE(tv_mynote_name.getText().toString());
				note.setPK_Course(getIntent().getStringExtra("PK_Course"));
				note.setPK_Res(getIntent().getStringExtra("PK_Res"));
				note.setPk_user(Setting.currentUser.getPk_user());
				note.setPK_Note(getIntent().getStringExtra("id"));
				
				String result=HttpUse.messagepost("CourseStudy", "saveResNote", note);
				
				try {
					JSONObject jo=new JSONObject(result);
					if(jo.getBoolean("result")){
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
				savenote();
			}
		});
		bt_isok_quie.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		dialog.show();
	}
}
