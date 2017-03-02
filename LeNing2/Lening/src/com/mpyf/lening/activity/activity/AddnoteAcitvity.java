package com.mpyf.lening.activity.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import com.mpyf.lening.interfaces.bean.Parame.NewNote;
import com.mpyf.lening.interfaces.bean.Parame.Note;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class AddnoteAcitvity extends Activity {
	
	private LinearLayout ll_addnote_back;
	private TextView tv_addnote_ok;
	private EditText et_addnote_title,et_addnote_context;
	private String id="";
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
		tv_addnote_ok=(TextView) findViewById(R.id.tv_addnote_ok);
		et_addnote_title=(EditText) findViewById(R.id.et_addnote_title);
		et_addnote_context=(EditText) findViewById(R.id.et_addnote_context);
	}
	
	private void addlistener(){
		
		ll_addnote_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(et_addnote_title.getText().toString().trim().equals("")&&et_addnote_context.getText().toString().trim().equals("")){
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
				postresnote();
			}
		});
		
	}
	
private void showinfo(){

		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==1&&!msg.obj.equals("null")){
					try {
						JSONObject jo=new JSONObject(msg.obj.toString());
						
						id=jo.getString("PK_Note");
						et_addnote_title.setText(jo.getString("NOTE_TITLE"));
						et_addnote_context.setText(jo.getString("NOTE_CONTENT"));
						
					} catch (JSONException e) {
						Diaoxian.showerror(AddnoteAcitvity.this, e.getMessage());
					}
				}
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				Map<String,Object> map=new HashMap<String, Object>();
				
				map.put("courseId", Quanjubianliang.courseid);
				map.put("resId", getIntent().getStringExtra("resId"));
				
				Message msg=new Message();
				
				String result=HttpUse.messageget("CourseStudy", "resNote", map);
				try {
					JSONObject jo=new JSONObject(result);
					if(jo.getBoolean("result")){
						msg.what=1;
						msg.obj=jo.getString("data");
					}else{
						msg.obj=jo.getString("message");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				handler.sendMessage(msg);
			};
		}.start();
		
		
	}
	
	private void issave(){
		Dialog dialog=MyDialog.MyDialogShow(this, R.layout.popup_isok, 1f);
		Button bt_isok_quie=(Button) dialog.findViewById(R.id.bt_isok_quie);
		Button bt_isok_ok=(Button) dialog.findViewById(R.id.bt_isok_ok);
		
		ListenerServer.setfinish(AddnoteAcitvity.this, bt_isok_quie);
		
		bt_isok_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				postresnote();
			}
		});
		
		dialog.show();
	}
	
	
	private void postresnote(){
		if(et_addnote_title.getText().toString().trim().equals("")){
			Diaoxian.showerror(AddnoteAcitvity.this, "请输入标题");
			return;
		}else if(et_addnote_context.getText().toString().trim().equals("")){
			Diaoxian.showerror(AddnoteAcitvity.this, "请输入内容");
			return;
		}
		final Dialog dialog=MyDialog.MyDialogloading(AddnoteAcitvity.this);
		dialog.show();
		
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				dialog.dismiss();
				
				if(msg.what==1){
					Diaoxian.showerror(AddnoteAcitvity.this, "添加成功");
					finish();
				}else{
					Diaoxian.showerror(AddnoteAcitvity.this, msg.obj.toString());
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
				note.setNOTE_CONTENT(et_addnote_context.getText().toString());
				note.setNOTE_Time(datestr);
				note.setNOTE_TITLE(et_addnote_title.getText().toString());
				note.setPK_Course(Quanjubianliang.courseid);
				note.setPK_Res(getIntent().getStringExtra("resId"));
				note.setPk_user(Setting.currentUser.getPk_user());
				note.setPK_Note(id);
				
//				NewNote note=new NewNote();
//				note.setNOTE_CONTENT(et_addnote_context.getText().toString());
//				note.setNOTE_Time(datestr);
//				note.setNOTE_TITLE(et_addnote_title.getText().toString());
//				note.setPK_Course(Quanjubianliang.courseid);
//				note.setPK_Res(getIntent().getStringExtra("resId"));
//				note.setPk_user(Setting.currentUser.getPk_user());
//				note.setPK_Note(id);
				
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
}
