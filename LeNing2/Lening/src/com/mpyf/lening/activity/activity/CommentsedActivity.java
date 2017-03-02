package com.mpyf.lening.activity.activity;

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

public class CommentsedActivity extends Activity implements OnClickListener {
	private LinearLayout ll_commentsed_back;
	private TextView tv_commentsed_save;
	private ImageView iv_commentsed_start1, iv_commentsed_start2,
			iv_commentsed_start3, iv_commentsed_start4, iv_commentsed_start5;
	private EditText et_commentsed_context;
	private ImageView[] stars = new ImageView[5];

	private int score = 0;
	private String pk_order;
	private String pk_goods;
	private String pk_Com;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_commentsed);
		init();
		getdate();
		addlistenr();
	}

	private void init() {
		ll_commentsed_back = (LinearLayout) findViewById(R.id.ll_commentsed_back);
		iv_commentsed_start1 = (ImageView) findViewById(R.id.iv_commentsed_start1);
		iv_commentsed_start2 = (ImageView) findViewById(R.id.iv_commentsed_start2);
		iv_commentsed_start3 = (ImageView) findViewById(R.id.iv_commentsed_start3);
		iv_commentsed_start4 = (ImageView) findViewById(R.id.iv_commentsed_start4);
		iv_commentsed_start5 = (ImageView) findViewById(R.id.iv_commentsed_start5);
		et_commentsed_context = (EditText) findViewById(R.id.et_commentsed_context);
		tv_commentsed_save = (TextView) findViewById(R.id.tv_commentsed_save);

		stars[0] = iv_commentsed_start1;
		stars[1] = iv_commentsed_start2;
		stars[2] = iv_commentsed_start3;
		stars[3] = iv_commentsed_start4;
		stars[4] = iv_commentsed_start5;
	}

	private void getdate() {
		pk_Com = getIntent().getStringExtra("pk_Com");
		pk_goods = getIntent().getStringExtra("pk_goods");
		pk_order = getIntent().getStringExtra("pk_order");
		int com_Leve = getIntent().getIntExtra("com_Leve", 0);

		et_commentsed_context.setText(getIntent().getStringExtra("com_con"));

		if (com_Leve == 0) {
			iv_commentsed_start1
					.setBackgroundResource(R.drawable.course_icon_star01);
			iv_commentsed_start2
					.setBackgroundResource(R.drawable.course_icon_star01);
			iv_commentsed_start3
					.setBackgroundResource(R.drawable.course_icon_star01);
			iv_commentsed_start4
					.setBackgroundResource(R.drawable.course_icon_star01);
			iv_commentsed_start5
					.setBackgroundResource(R.drawable.course_icon_star01);
		} else if (com_Leve == 1) {
			iv_commentsed_start1
					.setBackgroundResource(R.drawable.course_icon_star02);
		} else if (com_Leve == 2) {
			iv_commentsed_start1
					.setBackgroundResource(R.drawable.course_icon_star02);
			iv_commentsed_start2
					.setBackgroundResource(R.drawable.course_icon_star02);
		} else if (com_Leve == 3) {
			iv_commentsed_start1
					.setBackgroundResource(R.drawable.course_icon_star02);
			iv_commentsed_start2
					.setBackgroundResource(R.drawable.course_icon_star02);
			iv_commentsed_start3
					.setBackgroundResource(R.drawable.course_icon_star02);
		} else if (com_Leve == 4) {
			iv_commentsed_start1
					.setBackgroundResource(R.drawable.course_icon_star02);
			iv_commentsed_start2
					.setBackgroundResource(R.drawable.course_icon_star02);
			iv_commentsed_start3
					.setBackgroundResource(R.drawable.course_icon_star02);
			iv_commentsed_start4
					.setBackgroundResource(R.drawable.course_icon_star02);
		} else if (com_Leve == 5) {
			iv_commentsed_start1
					.setBackgroundResource(R.drawable.course_icon_star02);
			iv_commentsed_start2
					.setBackgroundResource(R.drawable.course_icon_star02);
			iv_commentsed_start3
					.setBackgroundResource(R.drawable.course_icon_star02);
			iv_commentsed_start4
					.setBackgroundResource(R.drawable.course_icon_star02);
			iv_commentsed_start5
					.setBackgroundResource(R.drawable.course_icon_star02);
		}

	}

	private void addlistenr() {
		ll_commentsed_back.setOnClickListener(this);
		tv_commentsed_save.setOnClickListener(this);

		iv_commentsed_start1.setOnClickListener(this);
		iv_commentsed_start2.setOnClickListener(this);
		iv_commentsed_start3.setOnClickListener(this);
		iv_commentsed_start4.setOnClickListener(this);
		iv_commentsed_start5.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_commentsed_back:
			if (et_commentsed_context.getText().toString().trim().equals("")) {
				finish();
			} else {
				issave();
			}
			break;
		case R.id.tv_commentsed_save:
			if (et_commentsed_context.getText().toString().trim().equals("")) {
				Toast.makeText(CommentsedActivity.this, "请输入评论内容",
						Toast.LENGTH_SHORT).show();
			} else {
				issave();
			}
			break;

		case R.id.iv_commentsed_start1:
			changestar(1);
			break;
		case R.id.iv_commentsed_start2:
			changestar(2);
			break;
		case R.id.iv_commentsed_start3:
			changestar(3);
			break;
		case R.id.iv_commentsed_start4:
			changestar(4);
			break;
		case R.id.iv_commentsed_start5:
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

		ListenerServer.setfinish(CommentsedActivity.this, bt_isok_quie);

		bt_isok_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 提交到服务器
				postcomment();

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
					Toast.makeText(CommentsedActivity.this, "修改成功",
							Toast.LENGTH_SHORT).show();
					finish();
				} else {
					Diaoxian.showerror(CommentsedActivity.this,
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

				goodsComment.setPk_Com(pk_Com);
				goodsComment.setPk_goods(pk_goods);

				goodsComment.setPk_order(pk_order);

				goodsComment.setCom_Con(et_commentsed_context.getText()
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
