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
import android.widget.Toast;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.interfaces.bean.Parame.GoodsComment;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class CommentsActivity extends Activity implements OnClickListener {
	private LinearLayout ll_comments_back;
	private TextView tv_save;
	private ImageView iv_comments_start1, iv_comments_start2,
			iv_comments_start3, iv_comments_start4, iv_comments_start5;
	private EditText et_comments_context;
	private ImageView[] stars = new ImageView[5];

	private int score = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_comments);
		init();
		getdate();
		addlistenr();
	}

	private void init() {
		ll_comments_back = (LinearLayout) findViewById(R.id.ll_comments_back);
		tv_save = (TextView) findViewById(R.id.tv_save);
		iv_comments_start1 = (ImageView) findViewById(R.id.iv_comments_start1);
		iv_comments_start2 = (ImageView) findViewById(R.id.iv_comments_start2);
		iv_comments_start3 = (ImageView) findViewById(R.id.iv_comments_start3);
		iv_comments_start4 = (ImageView) findViewById(R.id.iv_comments_start4);
		iv_comments_start5 = (ImageView) findViewById(R.id.iv_comments_start5);
		et_comments_context = (EditText) findViewById(R.id.et_comments_context);

		stars[0] = iv_comments_start1;
		stars[1] = iv_comments_start2;
		stars[2] = iv_comments_start3;
		stars[3] = iv_comments_start4;
		stars[4] = iv_comments_start5;
	}

	private void getdate() {
		iv_comments_start1.setOnClickListener(this);
		iv_comments_start2.setOnClickListener(this);
		iv_comments_start3.setOnClickListener(this);
		iv_comments_start4.setOnClickListener(this);
		iv_comments_start5.setOnClickListener(this);
	}

	private void addlistenr() {
		// 返回键
		// ListenerServer.setfinish(this, ll_comments_back);
		ll_comments_back.setOnClickListener(this);
		tv_save.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_comments_back:
			// if (et_comments_context.getText().toString().trim().equals("")) {
			finish();
			// } else {
			// issave();
			// }
			break;
		case R.id.tv_save:
			if (et_comments_context.getText().toString().trim().equals("")) {
				Toast.makeText(CommentsActivity.this, "请输入评论内容",
						Toast.LENGTH_SHORT).show();
			} else if (score == 0) {
				Toast.makeText(CommentsActivity.this, "请选择评论级别",
						Toast.LENGTH_SHORT).show();
			} else {
				issave();
			}

			break;

		case R.id.iv_comments_start1:
			changestar(1);
			break;
		case R.id.iv_comments_start2:
			changestar(2);
			break;
		case R.id.iv_comments_start3:
			changestar(3);
			break;
		case R.id.iv_comments_start4:
			changestar(4);
			break;
		case R.id.iv_comments_start5:
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

		ListenerServer.setfinish(CommentsActivity.this, bt_isok_quie);

		bt_isok_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 提交到服务器
				if (score == 0) {
					Toast.makeText(CommentsActivity.this, "请选择评论级别",
							Toast.LENGTH_SHORT).show();
				} else {
					postcomment();
				}
			}
		});

		dialog.show();
	}

	protected void postcomment() {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					// TODO
					Toast.makeText(CommentsActivity.this, "评论成功",
							Toast.LENGTH_SHORT).show();
					finish();
				} else {
					Diaoxian.showerror(CommentsActivity.this,
							msg.obj.toString());
				}

			}
		};

		new Thread() {
			@Override
			public void run() {
				Message msg = new Message();
				// TODO 要提交的数据
				GoodsComment goodsComment = new GoodsComment();

				goodsComment
						.setPk_goods(getIntent().getStringExtra("pk_goods"));

				goodsComment
						.setPk_order(getIntent().getStringExtra("pk_order"));

				goodsComment.setCom_Con(et_comments_context.getText()
						.toString());
				goodsComment.setCom_Level(score);

				String result = HttpUse.messagepost("QueAndAns",
						"saveGoodsCom", goodsComment);

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
