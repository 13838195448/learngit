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
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Readsaved;
import com.mpyf.lening.Jutil.UpdateManager;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.interfaces.bean.Result.VersionInfo;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class BeginActivity extends Activity {

	private ImageView iv_begin;
	private LinearLayout rl_root;
	private UpdateManager manager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new Xiaoxibeijing().changetop(this, R.color.touming);
		setContentView(R.layout.activity_begin);
		init();
		UpdateVerson();
//		initAnimation();
//		login();
	}
	private void UpdateVerson() {
		
		   
	final	Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				
				if(msg.what==1){
					try {
						JSONObject jo = (JSONObject)msg.obj;
						VersionInfo versionInfo = new VersionInfo();
						versionInfo.downurl = jo.getString("downurl");
						versionInfo.versionName = jo.getString("versionName");
						versionInfo.versioncode = jo.getString("versioncode");
						
						manager = new UpdateManager(BeginActivity.this,versionInfo);
						
						manager.checkUpdate();
						
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				}else{
					login();
				}
			}
		};
		
		new Thread(){
			public void run() {
				
				Setting.apiKey="6C9FBC3C-03BF-4329-BBE7-EFAC4C404253";
				Map<String,Object> map=new HashMap<String, Object>();
				
				String result=HttpUse.messageget("Account", "getAPPVersion", map);
//				System.out.println("请求服务器的版本 "+result);
				Message msg = Message.obtain();

				try {
					JSONObject jo = new JSONObject(result);
					if(jo.getBoolean("result")){
						msg.what=1;
						msg.obj = jo.getJSONObject("data");
					}else{
						msg.obj = jo.getString("message");
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
					msg.obj = e.getMessage();
				}
				
				handler.sendMessage(msg);
				
			};
		}.start();
	}
	
	private void initAnimation() {
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(1000);
		rl_root.startAnimation(alphaAnimation);
	}
	
	private void init(){
//		NewNote newnote=new NewNote();
//		String aa;
//		try {
//			String test=JsonUtils.serialize(newnote);
//			aa=test;
//			String json="{\"sourcefrom\":2,\"time\":null,\"token\":null,\"note_content\":\"1222\",\"nOTE_TITLE\":null,\"nOTE_Time\":null,\"pK_Course\":null,\"pK_Note\":null,\"pK_Res\":null,\"pk_user\":\"4444\"}";
//			NewNote newnote2=JsonUtils.deserialize(json,NewNote.class );
//			
//			Integer id2= newnote2.getPk_user();
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		iv_begin=(ImageView) findViewById(R.id.iv_begin);
		
		rl_root = (LinearLayout) findViewById(R.id.rl_root);
	}
	
	private void login(){
		if(!Readsaved.read(this, "logined").equals("1")){
			startActivity(new Intent(this, GateAcitvity.class));
			finish();
		}else {
					/**
					 * [Result] {"data":{"deptname":"用友优普",
					 * "email":"zoujzh@yonyou.com",
					 * "field":"人力资源",
					 * "key":"97d5a32a139e4fb6916ee5bd6de05001",
					 * "mphone":"13520342536",
					 * "nickname":"zoujzh",
					 * "pk_user":2133,
					 * "professional":"计算机科学与技术",
					 * "sex":2,"sourcefrom":"",
					 * "time":"","token":"",
					 * "truename":"邹建洲",
					 * "username":"420923198610010117"},"message":"","result":true}
					 */
						Intent intent=new Intent(BeginActivity.this,LoginActivity.class);
						
						startActivity(intent);
						finish();
	}
	
}
	@Override
	protected void onDestroy() {
		if(manager != null){
			manager.dismissDialog();
		}
		super.onDestroy();
	}
}