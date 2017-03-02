package com.mpyf.lening.activity.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.interfaces.bean.Parame.Note;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class Fragment_donote extends Fragment {

	
	private EditText et_shownote_title,et_shownote_comment;
	private String courseid,resid;
	private Button bt_shownote_ok;
	public Fragment_donote(String courseid, String resid) {
		this.courseid=courseid;
		this.resid=resid;
	}

	private String id="";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_donote, null);
		init(view);
		addlistener();
		return view;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		showinfo();
	}
	
	private void init(View view){
		
		et_shownote_title=(EditText) view.findViewById(R.id.et_shownote_title);
		et_shownote_comment=(EditText) view.findViewById(R.id.et_shownote_comment);
		
		bt_shownote_ok=(Button) view.findViewById(R.id.bt_shownote_ok);
		
		
	}
	
	
	
	private void showinfo(){
		
		

		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==1&&!msg.obj.equals("null")){
					try {
						JSONObject jo=new JSONObject(msg.obj.toString());
						
						id=jo.getString("PK_Note");
						et_shownote_title.setText(jo.getString("NOTE_TITLE"));
						et_shownote_comment.setText(jo.getString("NOTE_CONTENT"));
						
						bt_shownote_ok.setBackgroundResource(R.drawable.course_btn_send_d);
						bt_shownote_ok.setClickable(false);
						
					} catch (JSONException e) {
						Diaoxian.showerror(getActivity(), e.getMessage());
					}
				}
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				Map<String,Object> map=new HashMap<String, Object>();
				
				map.put("courseId", courseid);
				map.put("resId", resid);
				
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
	private void addlistener(){
		et_shownote_title.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				if(et_shownote_title.getText().toString().length()>0){
					bt_shownote_ok.setBackgroundResource(R.drawable.course_btn_send);
					bt_shownote_ok.setClickable(true);
				}else{
					bt_shownote_ok.setBackgroundResource(R.drawable.course_btn_send_d);
					bt_shownote_ok.setClickable(false);
				}
			}
		});
		
		et_shownote_comment.addTextChangedListener(new TextWatcher() {
			
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
				if(et_shownote_title.getText().toString().equals("")){
					return;
				}else{
					bt_shownote_ok.setBackgroundResource(R.drawable.course_btn_send);
					bt_shownote_ok.setClickable(true);
				}
			}
		});
		
		bt_shownote_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				postresnote();
			}
		});
	}
	
	private void postresnote(){
		final Dialog dialog=MyDialog.MyDialogloading(getActivity());
		dialog.show();
		
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				dialog.dismiss();
				
				if(msg.what==1){
					Diaoxian.showerror(getActivity(), id.equals("")?"添加成功":"修改成功");
					showinfo();
				}else{
					Diaoxian.showerror(getActivity(), msg.obj.toString());
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
				note.setNOTE_CONTENT(et_shownote_comment.getText().toString());
				note.setNOTE_Time(datestr);
				note.setNOTE_TITLE(et_shownote_title.getText().toString());
				note.setPK_Course(courseid);
				note.setPK_Res(resid);
				note.setPk_user(Setting.currentUser.getPk_user());
				note.setPK_Note(id);
				
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
