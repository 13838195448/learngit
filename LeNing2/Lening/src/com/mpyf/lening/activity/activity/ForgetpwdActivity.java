package com.mpyf.lening.activity.activity;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.interfaces.http.HttpUse;

/**
 * @author Administrator
 *忘记密码进入  :找回密码
 */
public class ForgetpwdActivity extends Activity {

	private LinearLayout ll_forgetpwd_back,ll_forgetpwd_yzm;
	private EditText et_forgetpwd_id,et_forgetpwd_phone,et_forgetpwd_yzm;
	private Button bt_forgetpwd_ok,bt_forgetpwd_getyzm;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        new Xiaoxibeijing().changetop(ForgetpwdActivity.this, R.color.main);
	        setContentView(R.layout.activity_forgetpwd);
	        init();
	        addlistenr();
	        
	    }
	
	private void init(){
		ll_forgetpwd_back=(LinearLayout) findViewById(R.id.ll_forgetpwd_back);
		ll_forgetpwd_yzm=(LinearLayout) findViewById(R.id.ll_forgetpwd_yzm);
		et_forgetpwd_id=(EditText) findViewById(R.id.et_forgetpwd_id);
		et_forgetpwd_phone=(EditText) findViewById(R.id.et_forgetpwd_phone);
		et_forgetpwd_yzm=(EditText) findViewById(R.id.et_forgetpwd_yzm);
		bt_forgetpwd_getyzm=(Button) findViewById(R.id.bt_forgetpwd_getyzm);
		bt_forgetpwd_ok=(Button) findViewById(R.id.bt_forgetpwd_ok);
		
		
		ll_forgetpwd_yzm.setVisibility(View.INVISIBLE);
	}
	
	private void addlistenr(){
		ListenerServer.setfinish(this, ll_forgetpwd_back);
		
		et_forgetpwd_phone.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {
			}
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				String text = et_forgetpwd_phone.getText().toString();  
				  
				   Pattern p = Pattern.compile("[0-9]*");   
				     Matcher m = p.matcher(text);   
				     if(et_forgetpwd_phone.getText().toString().length()==11&&m.matches() ){  
				    	 ll_forgetpwd_yzm.setVisibility(View.VISIBLE); 
				      }else{
				    	  ll_forgetpwd_yzm.setVisibility(View.INVISIBLE);
				      }
			}
		});
		
		bt_forgetpwd_getyzm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getyzm();	
			}
		});
		
		bt_forgetpwd_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				String text = et_forgetpwd_phone.getText().toString();  
				  
				   Pattern p = Pattern.compile("[0-9]*");   
				     Matcher m = p.matcher(text); 
				
				if(et_forgetpwd_id.getText().toString().equals("")){
					Diaoxian.showerror(ForgetpwdActivity.this,"请输入账号");
					return;
				}
				
				if(ll_forgetpwd_yzm.getVisibility()==View.INVISIBLE&&et_forgetpwd_phone.getText().toString().equals("")){
					Diaoxian.showerror(ForgetpwdActivity.this,"请输入邮箱");
					return;
				}
				
				if(text.indexOf("@")!=-1){
					getbackpwdemail();
				}else if(text.length()==11&&m.matches()){
					if(et_forgetpwd_yzm.getText().toString().equals("")){
						Diaoxian.showerror(ForgetpwdActivity.this,"请输入验证码");
						return;
					}else{
						getbackpwdphone();
					}
				}
				
			}
		});
	}
	
	private void getyzm(){
		final Dialog dialog=MyDialog.MyDialogloading(this);
		dialog.show();
		final Handler handler =new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				if(msg.what==1){
					Diaoxian.showerror(ForgetpwdActivity.this,"验证码已发送");
				}else{
					Diaoxian.showerror(ForgetpwdActivity.this, msg.obj.toString());
				}
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("username", et_forgetpwd_id.getText().toString());
				map.put("phone", et_forgetpwd_phone.getText().toString());
				Message msg=new Message(); 
				String result=HttpUse.messageget("Account", "retrieveCode", map);
				try {
					JSONObject jo=new JSONObject(result);
					if(jo.getBoolean("result")){
						msg.what=1;
					}
					msg.obj=jo.getString("message");
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handler.sendMessage(msg);
			};
		}.start();
	
	
	}
	
	private void getbackpwdphone(){

		final Dialog dialog=MyDialog.MyDialogloading(this);
		dialog.show();
		final Handler handler =new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				
				if (msg.what==1) {
					Intent intent=new Intent(ForgetpwdActivity.this, NewpwsActivity.class);
					intent.putExtra("username", et_forgetpwd_id.getText().toString());
					startActivity(intent);
					finish();
					Diaoxian.showerror(ForgetpwdActivity.this, "验证成功");
				}else{
					Diaoxian.showerror(ForgetpwdActivity.this, msg.obj.toString());
				}
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("username", et_forgetpwd_id.getText().toString());
				map.put("code", et_forgetpwd_yzm.getText().toString());
				Message msg=new Message(); 
				String result=HttpUse.messageget("Account", "verifyCode", map);
				try {
					JSONObject jo=new JSONObject(result);
					if(jo.getBoolean("result")){
						msg.what=1;
					}
					msg.obj=jo.getString("message");
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handler.sendMessage(msg);
			};
		}.start();
	
	}
	
	private void getbackpwdemail(){
		final Dialog dialog=MyDialog.MyDialogloading(this);
		dialog.show();
		final Handler handler =new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				dialog.dismiss();
//				
				if (msg.what==1) {
					finish();
				}
				Diaoxian.showerror(ForgetpwdActivity.this, "重置密码已发送");
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("username", et_forgetpwd_id.getText().toString());
				map.put("mail", et_forgetpwd_phone.getText().toString());
				Message msg=new Message();
				String result=HttpUse.messageget("Account", "retrievePdMail", map);
				try {
					JSONObject jo=new JSONObject(result);
					if(jo.getBoolean("result")){
						msg.what=1;
					}
					msg.obj=jo.getString("message");
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					msg.obj=e.getMessage();
				}
				handler.sendMessage(msg);
			};
		}.start();
	}
}
