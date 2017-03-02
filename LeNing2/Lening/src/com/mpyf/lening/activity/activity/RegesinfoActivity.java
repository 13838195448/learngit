package com.mpyf.lening.activity.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.StringUtils;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.interfaces.bean.Parame.Register;
import com.mpyf.lening.interfaces.http.HttpUse;

public class RegesinfoActivity extends Activity {

	private LinearLayout ll_info_back;
	private EditText et_info_name,et_info_phone,et_forgetpwd_email,et_info_pwd;
	private ImageView iv_info_look;
	private Button bt_info_ok;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		new Xiaoxibeijing().changetop(RegesinfoActivity.this, R.color.main);
		setContentView(R.layout.activity_info);
		init();
		addlistener();
	}
	private void init(){
		ll_info_back=(LinearLayout) findViewById(R.id.ll_info_back);
		et_info_name=(EditText) findViewById(R.id.et_info_name);
		et_info_phone=(EditText) findViewById(R.id.et_info_phone);
		et_forgetpwd_email=(EditText) findViewById(R.id.et_forgetpwd_email);
		et_info_pwd=(EditText) findViewById(R.id.et_info_pwd);
		iv_info_look=(ImageView) findViewById(R.id.iv_info_look);
		bt_info_ok=(Button) findViewById(R.id.bt_info_ok);
		
		et_info_phone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
		
	}
	
	private void addlistener(){
		ListenerServer.setfinish(this, ll_info_back);
		iv_info_look.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (et_info_pwd.getTransformationMethod() == PasswordTransformationMethod
						.getInstance()) { 
					iv_info_look.setImageResource(R.drawable.login_btn_find_look);
					et_info_pwd.setTransformationMethod(HideReturnsTransformationMethod
							.getInstance());
				}else{
					iv_info_look
					.setImageResource(R.drawable.login_btn_find_close);
					et_info_pwd
					.setTransformationMethod(PasswordTransformationMethod
							.getInstance());
				}
				
			}
		});
		
		bt_info_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(et_info_name.getText().toString().equals("")){
					Diaoxian.showerror(RegesinfoActivity.this,"真实姓名不能为空");
				}else if(et_info_phone.getText().toString().equals("")){
					Diaoxian.showerror(RegesinfoActivity.this,"请输入手机号码");
				}else if(!StringUtils.isMobileNo(et_info_phone.getText().toString())){
					Diaoxian.showerror(RegesinfoActivity.this,"请输入正确的手机号码");
				}else if(et_forgetpwd_email.getText().toString().equals("")){
					Diaoxian.showerror(RegesinfoActivity.this,"请输入邮箱");
				}else if(yanzhengemail(et_forgetpwd_email.getText().toString())){
					Diaoxian.showerror(RegesinfoActivity.this,"请输入正确的邮箱");
				}else if(et_info_pwd.getText().toString().equals("")){
					Diaoxian.showerror(RegesinfoActivity.this,"请输入密码");
				}else{
					final Dialog dialog=MyDialog.MyDialogloading(RegesinfoActivity.this);
					dialog.show();
					final Handler handler=new Handler(){
						@Override
						public void handleMessage(Message msg) {
							dialog.dismiss();
							Diaoxian.showerror(RegesinfoActivity.this, msg.obj.toString());
							if(msg.what==1){
								startActivity(new Intent(RegesinfoActivity.this, LoginActivity.class));
								finish();
							}
						};
					};
					
					new Thread(){
						@Override
						public void run() {
							
							Message msg=new Message();
							
							Register register=new Register();
							register.setEmail(et_forgetpwd_email.getText().toString());
							register.setIdcard(getIntent().getStringExtra("idcard"));
							register.setMphone(et_info_phone.getText().toString());
							register.setPassword(et_info_pwd.getText().toString());
							register.setReg_org(getIntent().getStringExtra("org"));
							register.setTruename(et_info_name.getText().toString());
							String result=HttpUse.messagepost("Account", "saveRegister", register);
							
							try {
								JSONObject jo=new JSONObject(result);
								if(jo.getBoolean("result")){
									msg.what=1;
								}
								msg.obj=jo.getString("message");
								handler.sendMessage(msg);
							} catch (JSONException e) {
								Diaoxian.showerror(RegesinfoActivity.this, e.getMessage());
							}
						};
					}.start();
					
				}
			}
		});
		
	}
	
	private boolean yanzhengemail(String email){
		
		if(email.indexOf(".")==-1||email.indexOf("@")==-1||email.indexOf("@")>=email.indexOf(".")-1||email.startsWith("@")){
			return true;
		}else{
			return false;
		}
	}
}
