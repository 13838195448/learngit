package com.mpyf.lening.activity.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.interfaces.http.HttpUse;

public class NewpwsActivity extends Activity {

	private LinearLayout ll_info_back, ll_info_top;
	private TextView tv_info_title;
	private EditText et_info_pwd;
	private ImageView iv_info_look;
	private Button bt_info_ok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		new Xiaoxibeijing().changetop(NewpwsActivity.this, R.color.main);
		setContentView(R.layout.activity_info);
		init();
		addlistener();
	}

	private void init() {
		ll_info_back = (LinearLayout) findViewById(R.id.ll_info_back);
		ll_info_top = (LinearLayout) findViewById(R.id.ll_info_top);
		tv_info_title = (TextView) findViewById(R.id.tv_info_title);
		et_info_pwd = (EditText) findViewById(R.id.et_info_pwd);
		iv_info_look = (ImageView) findViewById(R.id.iv_info_look);
		bt_info_ok = (Button) findViewById(R.id.bt_info_ok);

		tv_info_title.setText("填写新密码");
		et_info_pwd.setHint("请输入8-16位新密码");
		ll_info_top.setVisibility(View.GONE);

	}

	private void addlistener() {
		ListenerServer.setfinish(this, ll_info_back);
		bt_info_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(et_info_pwd.getText().toString().length()<8){
					Diaoxian.showerror(NewpwsActivity.this, "密码长度至少为8位");
				}else{
					changepwd();
				}
			}
		});
		iv_info_look.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (et_info_pwd.getTransformationMethod() == PasswordTransformationMethod
						.getInstance()) {
					iv_info_look
							.setImageResource(R.drawable.login_btn_find_look);
					et_info_pwd
							.setTransformationMethod(HideReturnsTransformationMethod
									.getInstance());
				} else {
					iv_info_look
							.setImageResource(R.drawable.login_btn_find_close);
					et_info_pwd
							.setTransformationMethod(PasswordTransformationMethod
									.getInstance());
				}

			}
		});
	}
	
	private void changepwd(){

		// TODO Auto-generated method stub
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				
				if(msg.what==1){
					Diaoxian.showerror(NewpwsActivity.this, "修改成功");
					startActivity(new Intent(NewpwsActivity.this, LoginActivity.class));
					finish();
				}else{
					Diaoxian.showerror(NewpwsActivity.this, msg.obj.toString());
				}
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("username", getIntent().getStringExtra("username"));
				map.put("npw", et_info_pwd.getText().toString());
				Message msg=new Message();
				String result=HttpUse.messageget("Account", "savePassSMS", map);
				
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
}
