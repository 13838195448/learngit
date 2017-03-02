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
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.Quanjubianliang;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.interfaces.bean.Parame.Comments;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class AddcommentActivity extends Activity implements OnClickListener {

	private LinearLayout ll_addcomment_back;
	private TextView tv_addcomment_ok,tv_addcomment_title;
	private ImageView iv_addcomment_start1, iv_addcomment_start2,
			iv_addcomment_start3, iv_addcomment_start4, iv_addcomment_start5;
	private EditText et_addcomment_context;
	private ImageView[] stars = new ImageView[5];

	private int score = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	//	new Xiaoxibeijing().changetop(this, R.color.main);
		setContentView(R.layout.activity_addcomment);
		init();
		showinfo();
		addlistener();
	}

	private void init() {
		ll_addcomment_back = (LinearLayout) findViewById(R.id.ll_addcomment_back);
		tv_addcomment_ok = (TextView) findViewById(R.id.tv_addcomment_ok);
		tv_addcomment_title=(TextView) findViewById(R.id.tv_addcomment_title);
		iv_addcomment_start1 = (ImageView) findViewById(R.id.iv_addcomment_start1);
		iv_addcomment_start2 = (ImageView) findViewById(R.id.iv_addcomment_start2);
		iv_addcomment_start3 = (ImageView) findViewById(R.id.iv_addcomment_start3);
		iv_addcomment_start4 = (ImageView) findViewById(R.id.iv_addcomment_start4);
		iv_addcomment_start5 = (ImageView) findViewById(R.id.iv_addcomment_start5);
		et_addcomment_context = (EditText) findViewById(R.id.et_addcomment_context);

		stars[0] = iv_addcomment_start1;
		stars[1] = iv_addcomment_start2;
		stars[2] = iv_addcomment_start3;
		stars[3] = iv_addcomment_start4;
		stars[4] = iv_addcomment_start5;

	}

	private void showinfo(){
		
		
		if(getIntent().getStringExtra("context")==null){
			et_addcomment_context.setEnabled(true);
			tv_addcomment_ok.setText("完成");
			iv_addcomment_start1.setOnClickListener(this);
			iv_addcomment_start2.setOnClickListener(this);
			iv_addcomment_start3.setOnClickListener(this);
			iv_addcomment_start4.setOnClickListener(this);
			iv_addcomment_start5.setOnClickListener(this);
		}else{
			tv_addcomment_title.setText("评论详情");
			et_addcomment_context.setEnabled(false);
			et_addcomment_context.setText(getIntent().getStringExtra("context"));
			changestar(getIntent().getIntExtra("srcoe", 0));
		}
		
	}
	
	private void addlistener() {
		ll_addcomment_back.setOnClickListener(this);
		tv_addcomment_ok.setOnClickListener(this);
		
		et_addcomment_context.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.ll_addcomment_back:
			if(et_addcomment_context.getText().toString().trim().equals("")){
				finish();
			}else{
				issave();
			}
			break;
		case R.id.tv_addcomment_ok:
			postcomment();
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
		default:
			break;
		}
	}

	private void issave() {
		Dialog dialog = MyDialog.MyDialogShow(this, R.layout.popup_isok, 1f);
		Button bt_isok_quie = (Button) dialog.findViewById(R.id.bt_isok_quie);
		Button bt_isok_ok = (Button) dialog.findViewById(R.id.bt_isok_ok);

		ListenerServer.setfinish(AddcommentActivity.this, bt_isok_quie);

		bt_isok_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				postcomment();
			}
		});

		dialog.show();
	}

	private void postcomment() {
		
		if(tv_addcomment_ok.getText().toString().equals("编辑")){
			tv_addcomment_ok.setText("完成");
			et_addcomment_context.setEnabled(true);
			iv_addcomment_start1.setOnClickListener(this);
			iv_addcomment_start2.setOnClickListener(this);
			iv_addcomment_start3.setOnClickListener(this);
			iv_addcomment_start4.setOnClickListener(this);
			iv_addcomment_start5.setOnClickListener(this);
		}else if(tv_addcomment_ok.getText().toString().equals("完成")){
			if(et_addcomment_context.getText().toString().trim().equals("")){
				Diaoxian.showerror(AddcommentActivity.this, "请输入评论内容");
				return;
			}
			
			final Dialog dialog = MyDialog.MyDialogloading(AddcommentActivity.this);
			dialog.show();

			final Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					dialog.dismiss();

					if (msg.what == 1) {
						if (getIntent().getStringExtra("id")==null) {
							Diaoxian.showerror(AddcommentActivity.this, "评论成功");
						}else{
							Diaoxian.showerror(AddcommentActivity.this, "修改成功");
						}
						
						finish();
					} else {
						Diaoxian.showerror(AddcommentActivity.this,
								msg.obj.toString());
					}

				}
			};
			
			new Thread() {
				@Override
				public void run() {
					Message msg = new Message();

					Date date = new Date();
					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy/MM/dd HH:mm:ss");
					String datestr = format.format(date);
					Comments comments = new Comments();
					comments.setCOMMENT_CONTENT(et_addcomment_context.getText().toString());
					comments.setCOMMENT_LEVEL(score);
					comments.setCommTime(datestr);
					comments.setNickname(Setting.currentUser.getNickname());
					comments.setPK_Com(getIntent().getStringExtra("id")==null?"":getIntent().getStringExtra("id"));
					comments.setPK_Course(Quanjubianliang.courseid);
					comments.setPK_Res(getIntent().getStringExtra("resId"));
					comments.setPk_user(Setting.currentUser.getPk_user());
					comments.setUserName(Setting.currentUser.getUsername());
					comments.setResName("");
					String result = HttpUse.messagepost("CourseStudy",
							"saveResComments", comments);

					try {
						JSONObject jo = new JSONObject(result);
						if (jo.getBoolean("result")) {
							msg.what = 1;
						}
						msg.obj = jo.getString("message");

					} catch (JSONException e) {
						msg.obj = e.getMessage();
					}
					handler.sendMessage(msg);
				}
			}.start();
		}
		
	}

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
}
