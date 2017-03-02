package com.mpyf.lening.activity.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.interfaces.bean.Parame.TrainComments;
import com.mpyf.lening.interfaces.http.HttpUse;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddPeixun_comActivity extends Activity implements OnClickListener{
	private ImageView[] stars2 = new ImageView[5];
	private ImageView[] stars = new ImageView[5];
	private LinearLayout ll_addcomment_back;
	private TextView tv_addcomment_ok;
	private ImageView iv_addcomment_start1;
	private ImageView iv_addcomment_start2;
	private ImageView iv_addcomment_start3;
	private ImageView iv_addcomment_start4;
	private ImageView iv_addcomment_start5;
	private ImageView iv_start1;
	private ImageView iv_start2;
	private ImageView iv_start3;
	private ImageView iv_start4;
	private ImageView iv_start5;
	private EditText et_addcomment_context1;
	private EditText et_addcomment_context2;
	private String id;
	private int score=0;
	private int score2=0;
	
	private String pK_Act;
	private String pK_Com;
	private String s;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_addpx_comment);

		initUI();
		initData();
		
		addListener();
	}

	private void addListener() {

		ll_addcomment_back.setOnClickListener(this);
		tv_addcomment_ok.setOnClickListener(this);
	}

	private void initUI() {

		ll_addcomment_back = (LinearLayout) findViewById(R.id.ll_addcomment_back);
		tv_addcomment_ok = (TextView) findViewById(R.id.tv_addcomment_ok);

		iv_addcomment_start1 = (ImageView) findViewById(R.id.iv_addcomment_start1);
		iv_addcomment_start2 = (ImageView) findViewById(R.id.iv_addcomment_start2);
		iv_addcomment_start3 = (ImageView) findViewById(R.id.iv_addcomment_start3);
		iv_addcomment_start4 = (ImageView) findViewById(R.id.iv_addcomment_start4);
		iv_addcomment_start5 = (ImageView) findViewById(R.id.iv_addcomment_start5);

		et_addcomment_context1 = (EditText) findViewById(R.id.et_addcomment_context1);

		stars[0] = iv_addcomment_start1;
		stars[1] = iv_addcomment_start2;
		stars[2] = iv_addcomment_start3;
		stars[3] = iv_addcomment_start4;
		stars[4] = iv_addcomment_start5;

		iv_start1 = (ImageView) findViewById(R.id.iv_start1);
		iv_start2 = (ImageView) findViewById(R.id.iv_start2);
		iv_start3 = (ImageView) findViewById(R.id.iv_start3);
		iv_start4 = (ImageView) findViewById(R.id.iv_start4);
		iv_start5 = (ImageView) findViewById(R.id.iv_start5);

		et_addcomment_context2 = (EditText) findViewById(R.id.et_addcomment_context2);

		stars2[0] = iv_start1;
		stars2[1] = iv_start2;
		stars2[2] = iv_start3;
		stars2[3] = iv_start4;
		stars2[4] = iv_start5;
		
		id = getIntent().getStringExtra("id");
		
	}

	private void initData() {

		final Handler handler = new Handler(){
		
			

			public void handleMessage(Message msg) {
				
				if(msg.what==1){
					s = msg.obj.toString();
					if(TextUtils.isEmpty(s)){
						noComment();
					}else{
						try {
							JSONObject jo=new JSONObject(msg.obj.toString());
							
								TrainComments comments = new TrainComments();
								pK_Act = jo.getString("PK_Act");
								pK_Com = jo.getString("PK_Com");
							comments.setCOMMENT_CONTENT(jo.getString("COMMENT_CONTENT"));
							comments.setCOMMENT_LEVEL(jo.getInt("COMMENT_LEVEL"));
						//	comments.setPK_Act(jo.getString("PK_Act"));
						//	comments.setPK_Com(jo.getString("PK_Com"));
							comments.setPk_user(jo.getInt("pk_user"));
							comments.setTeacher_CONTENT(jo.getString("teacher_CONTENT"));
							comments.setTeacher_LEVEL(jo.getInt("teacher_LEVEL"));
							
							
							
							if(TextUtils.isEmpty(comments.getCOMMENT_CONTENT())){
								noComment();
							}else{
								showinfo(comments);
							}
							
						} catch (JSONException e) {
							e.printStackTrace();
						} 
					}
				}
			};
		} ;
		new Thread(){
			@Override
			public void run() {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("pk_act",id);   
				
				Message msg=new Message();
				String result=HttpUse.messageget("TrainACt", "getTrianComments", map);
				try {
					JSONObject jo=new JSONObject(result);
					if(jo.getBoolean("result")){
						msg.what=1;
						msg.obj=jo.getString("data");
					}else{
						msg.obj=jo.getString("message");
					}
				} catch (JSONException e) {
					msg.obj=e.getMessage();
				}
				handler.sendMessage(msg);
			};
		}.start();
	}
	
	protected void noComment() {

		et_addcomment_context1.setEnabled(true);
		et_addcomment_context2.setEnabled(true);
		tv_addcomment_ok.setText("完成");
		iv_addcomment_start1.setOnClickListener(this);
		iv_addcomment_start2.setOnClickListener(this);
		iv_addcomment_start3.setOnClickListener(this);
		iv_addcomment_start4.setOnClickListener(this);
		iv_addcomment_start5.setOnClickListener(this);
		
		iv_start1.setOnClickListener(this);
		iv_start2.setOnClickListener(this);
		iv_start3.setOnClickListener(this);
		iv_start4.setOnClickListener(this);
		iv_start5.setOnClickListener(this);
	}

	protected void showinfo(TrainComments com) {
	
		et_addcomment_context1.setEnabled(false);
		et_addcomment_context2.setEnabled(false);
		et_addcomment_context1.setText(com.getCOMMENT_CONTENT());
		changestar(com.getCOMMENT_LEVEL());
		
		et_addcomment_context2.setText(com.getTeacher_CONTENT());
		changestar2(com.getTeacher_LEVEL());
	}

	//星评选择器1
	private void changestar(int index) {
		for (int i = 0; i < stars.length; i++) {
			if (i < index) {
				stars[i].setImageResource(R.drawable.course_icon_star02);
			} else {
				stars[i].setImageResource(R.drawable.course_icon_star01);
			}
		}
		score = index;
	}
	//星评选择器2 
	private void changestar2(int index) {
		for (int i = 0; i < stars.length; i++) {
			if (i < index) {
				stars2[i].setImageResource(R.drawable.course_icon_star02);
			} else {
				stars2[i].setImageResource(R.drawable.course_icon_star01);
			}
		}
		score2 = index;
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.ll_addcomment_back:
			if(et_addcomment_context1.getText().toString().trim().equals("")||
					et_addcomment_context2.getText().toString().trim().equals("")||
					TextUtils.isEmpty(s) ){
				finish();
			}else{
				issave();
			}
			break;

		case R.id.tv_addcomment_ok:
			postComment();
			break;
		case R.id.iv_addcomment_start1:
			changestar(1);
			break;
		case R.id.iv_addcomment_start2:
			changestar(2);
			break;
		case R.id.iv_addcomment_start3:
			changestar(3);
			break;
		case R.id.iv_addcomment_start4:
			changestar(4);
			break;
		case R.id.iv_addcomment_start5:
			changestar(5);
			break;
		
		case R.id.iv_start1:
			changestar2(1);
			break;
		case R.id.iv_start2:
			changestar2(2);
			break;
		case R.id.iv_start3:
			changestar2(3);
			break;
		case R.id.iv_start4:
			changestar2(4);
			break;
		case R.id.iv_start5:
			changestar2(5);
			break;
		default:
			break;
		}
	}

	
	private void issave() {

		Dialog dialog = MyDialog.MyDialogShow(this, R.layout.popup_isok, 1f);
		Button bt_isok_quie = (Button) dialog.findViewById(R.id.bt_isok_quie);
		Button bt_isok_ok = (Button) dialog.findViewById(R.id.bt_isok_ok);

		ListenerServer.setfinish(AddPeixun_comActivity.this, bt_isok_quie);

		bt_isok_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				postComment();
			}
		});

		dialog.show();
	}

	private void postComment() {

		if(tv_addcomment_ok.getText().toString().equals("编辑")){
			noComment();
		}else if(tv_addcomment_ok.getText().toString().equals("完成")){
			if(et_addcomment_context1.getText().toString().trim().equals("")||
				et_addcomment_context2.getText().toString().trim().equals("")){
				Diaoxian.showerror(AddPeixun_comActivity.this, "请填写完整评论内容");
				return;
			}
			
			     final Handler handle = new Handler(){
			    	 @Override
			    	public void handleMessage(Message msg) {
			    		super.handleMessage(msg);
			    		
			    		if(msg.what == 1){
			    			if(TextUtils.isEmpty(s)||pK_Com==null){
			    				Diaoxian.showerror(AddPeixun_comActivity.this, "评论成功");
			    			}else{
			    				Diaoxian.showerror(AddPeixun_comActivity.this, "修改成功");
			    			}
			    			
			    			finish();
			    		}else{
			    			
			    			Diaoxian.showerror(AddPeixun_comActivity.this, msg.obj.toString());
			    		}
			    	}
			     };
			
			new Thread(){ 
				
				public void run() {
					Message msg = Message.obtain(); 
					TrainComments comments = new TrainComments();
					Date date = new Date();
					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy/MM/dd HH:mm:ss");
					String datestr = format.format(date);
					
					comments.setCOMMENT_CONTENT(et_addcomment_context1.getText().toString());
					comments.setCOMMENT_LEVEL(score);
					if(TextUtils.isEmpty(s)||pK_Com==null){
						comments.setPK_Com("");
					}else{
						comments.setPK_Com(pK_Com);
					}
					comments.setPK_Act(pK_Act);
					comments.setCommTime(datestr);
					comments.setTeacher_CONTENT(et_addcomment_context2.getText().toString());
					comments.setTeacher_LEVEL(score2);
					
					String result = HttpUse.messagepost("TrainACt", "saveTrianComments", comments);
				
					try {
						JSONObject jo = new JSONObject(result);
						if(jo.getBoolean("result")){
							msg.what=1;
						}
						msg.obj = jo.getString("message");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				handle.sendMessage(msg);
					
				};
			}.start();
			
		}
		
	}
}
