package com.mpyf.lening.activity.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.interfaces.http.HttpUse;

public class ChangepwdActivity extends Activity {

	private LinearLayout ll_changepwd_back,ll_changepwd_oldpwd,ll_changepwd_newpwd;
	private TextView tv_changepwd_top;
	private EditText et_changepwd_oldpwd,et_changepwd_newpwd;
	private Button bt_changepwd_ok;
	private ImageView iv_changepwd_look;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	//	new Xiaoxibeijing().changetop(this, R.color.main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_changepwd);
		init();
		addlistener();
	}
	private void init(){
		ll_changepwd_back=(LinearLayout) findViewById(R.id.ll_changepwd_back);
		ll_changepwd_oldpwd=(LinearLayout) findViewById(R.id.ll_changepwd_oldpwd);
		ll_changepwd_newpwd=(LinearLayout) findViewById(R.id.ll_changepwd_newpwd);
		tv_changepwd_top=(TextView) findViewById(R.id.tv_changepwd_top);
		et_changepwd_oldpwd=(EditText) findViewById(R.id.et_changepwd_oldpwd);
		et_changepwd_newpwd=(EditText) findViewById(R.id.et_changepwd_newpwd);
		bt_changepwd_ok=(Button) findViewById(R.id.bt_changepwd_ok);
		iv_changepwd_look=(ImageView) findViewById(R.id.iv_changepwd_look);
		
		ll_changepwd_newpwd.setVisibility(View.GONE);
	}
	
	private void addlistener(){
		ll_changepwd_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(ll_changepwd_oldpwd.getVisibility()==View.VISIBLE){
					finish();
				}else{
					ll_changepwd_oldpwd.setVisibility(View.VISIBLE);
					tv_changepwd_top.setVisibility(View.VISIBLE);
					ll_changepwd_newpwd.setVisibility(View.GONE);
				}
			}
		});
		
		bt_changepwd_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				clickok();
			}
		});
		
		iv_changepwd_look.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (et_changepwd_newpwd.getTransformationMethod() == PasswordTransformationMethod
						.getInstance()) {
					iv_changepwd_look.setImageResource(R.drawable.login_btn_find_look);
					et_changepwd_newpwd.setTransformationMethod(HideReturnsTransformationMethod
							.getInstance());
				}else{
					iv_changepwd_look
					.setImageResource(R.drawable.login_btn_find_close);
					et_changepwd_newpwd
					.setTransformationMethod(PasswordTransformationMethod
							.getInstance());
				}
			}
		});
	}
	
	private void clickok(){
		if(ll_changepwd_oldpwd.getVisibility()==View.VISIBLE){
			if(et_changepwd_oldpwd.getText().toString().equals("")){
				Diaoxian.showerror(ChangepwdActivity.this, "«Î ‰»Îæ…√‹¬Î");
				return;
			}else{
				checkoldpwd();
			}
			
		}else if(ll_changepwd_oldpwd.getVisibility()==View.GONE){
			if(et_changepwd_newpwd.getText().toString().equals("")){
				Diaoxian.showerror(ChangepwdActivity.this, "«Î ‰»Î–¬√‹¬Î");
				return;
			}else{
				changepwd();
				finish();
			}
		}
	}
	
	private void checkoldpwd(){
		final Dialog dialog=MyDialog.MyDialogloading(this);
		dialog.show();
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				dialog.dismiss();
				
				if(msg.what==1){
					ll_changepwd_oldpwd.setVisibility(View.GONE);
					tv_changepwd_top.setVisibility(View.INVISIBLE);
					ll_changepwd_newpwd.setVisibility(View.VISIBLE);
				}else{
					Diaoxian.showerror(ChangepwdActivity.this, msg.obj.toString());
				}
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("password", et_changepwd_oldpwd.getText().toString());
				Message msg=new Message();
				String result=HttpUse.messageget("Account", "verifyPw", map);
				
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
	
	private void changepwd(){
		final Dialog dialog=MyDialog.MyDialogloading(this);
		dialog.show();
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				dialog.dismiss();
				
				if(msg.what==1){
					Diaoxian.showerror(ChangepwdActivity.this, "–ﬁ∏ƒ≥…π¶");
					finish();
				}else{
					Diaoxian.showerror(ChangepwdActivity.this, msg.obj.toString());
				}
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("npw", et_changepwd_newpwd.getText().toString());
				Message msg=new Message();
				String result=HttpUse.messageget("Account", "saveNewPw", map);
				
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
