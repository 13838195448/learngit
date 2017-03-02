package com.mpyf.lening.activity.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.Quanjubianliang;
import com.mpyf.lening.interfaces.bean.Parame.Comments;
import com.mpyf.lening.interfaces.bean.Parame.GoodsComment;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

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

public class EditCommentsedActivity extends Activity implements OnClickListener {
	private LinearLayout ll_editcomments_back;
	private TextView tv_editcomments_ok, tv_editcomments_title;
	private ImageView iv_editcomments_start1, iv_editcomments_start2,
			iv_editcomments_start3, iv_editcomments_start4,
			iv_editcomments_start5;
	private EditText et_editcomments_context;
	private ImageView[] stars = new ImageView[5];
	private int score = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_editcomments);
		init();
		getdate();
		addlistenr();
	}

	private void init() {
		ll_editcomments_back = (LinearLayout) findViewById(R.id.ll_editcomments_back);
		tv_editcomments_ok = (TextView) findViewById(R.id.tv_editcomments_ok);
		tv_editcomments_title = (TextView) findViewById(R.id.tv_editcomments_title);
		iv_editcomments_start1 = (ImageView) findViewById(R.id.iv_editcomments_start1);
		iv_editcomments_start2 = (ImageView) findViewById(R.id.iv_editcomments_start2);
		iv_editcomments_start3 = (ImageView) findViewById(R.id.iv_editcomments_start3);
		iv_editcomments_start4 = (ImageView) findViewById(R.id.iv_editcomments_start4);
		iv_editcomments_start5 = (ImageView) findViewById(R.id.iv_editcomments_start5);
		et_editcomments_context = (EditText) findViewById(R.id.et_editcomments_context);

		stars[0] = iv_editcomments_start1;
		stars[1] = iv_editcomments_start2;
		stars[2] = iv_editcomments_start3;
		stars[3] = iv_editcomments_start4;
		stars[4] = iv_editcomments_start5;

	}

	private void getdate() {
		if (getIntent().getStringExtra("com_con") == null) {
			et_editcomments_context.setEnabled(true);
			tv_editcomments_ok.setText("完成");
			iv_editcomments_start1.setOnClickListener(this);
			iv_editcomments_start2.setOnClickListener(this);
			iv_editcomments_start3.setOnClickListener(this);
			iv_editcomments_start4.setOnClickListener(this);
			iv_editcomments_start5.setOnClickListener(this);
		} else {
			tv_editcomments_title.setText("评论详情");
			et_editcomments_context.setEnabled(false);

			et_editcomments_context.setText(getIntent().getStringExtra(
					"com_con"));
			changestar(getIntent().getIntExtra("com_Level", 0));
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

	private void addlistenr() {
		ll_editcomments_back.setOnClickListener(this);
		tv_editcomments_ok.setOnClickListener(this);
		et_editcomments_context.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_editcomments_back:
			// if
			// (et_editcomments_context.getText().toString().trim().equals(""))
			// {
			finish();
			// } else {
			// issave();
			// }
			break;
		case R.id.tv_editcomments_ok:
			postcomment();
			break;
		case R.id.iv_editcomments_start1:
			changestar(1);
			break;
		case R.id.iv_editcomments_start2:
			changestar(2);
			break;
		case R.id.iv_editcomments_start3:
			changestar(3);
			break;
		case R.id.iv_editcomments_start4:
			changestar(4);
			break;
		case R.id.iv_editcomments_start5:
			changestar(5);
			break;
		default:
			break;
		}
	}

	private void postcomment() {

		if (tv_editcomments_ok.getText().toString().equals("编辑")) {
			tv_editcomments_ok.setText("完成");
			et_editcomments_context.setEnabled(true);
			iv_editcomments_start1.setOnClickListener(this);
			iv_editcomments_start2.setOnClickListener(this);
			iv_editcomments_start3.setOnClickListener(this);
			iv_editcomments_start4.setOnClickListener(this);
			iv_editcomments_start5.setOnClickListener(this);
		} else if (tv_editcomments_ok.getText().toString().equals("完成")) {
			if (et_editcomments_context.getText().toString().trim().equals("")) {
				Diaoxian.showerror(EditCommentsedActivity.this, "请输入评论内容");
				return;
			}

			final Dialog dialog = MyDialog
					.MyDialogloading(EditCommentsedActivity.this);
			dialog.show();

			final Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					dialog.dismiss();

					if (msg.what == 1) {

						Diaoxian.showerror(EditCommentsedActivity.this, "修改成功");

						finish();
					} else {
						Diaoxian.showerror(EditCommentsedActivity.this,
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
							.setPk_Com(getIntent().getStringExtra("pk_Com"));
					goodsComment.setPk_goods(getIntent().getStringExtra(
							"pk_goods"));

					goodsComment.setPk_order(getIntent().getStringExtra(
							"pk_order"));

					goodsComment.setCom_Con(et_editcomments_context.getText()
							.toString());
					goodsComment.setCom_Level(score);
					String result = HttpUse.messagepost("QueAndAns",
							"saveGoodsCom", goodsComment);
//					System.out.println("==修改结果==" + result);
					try {
						JSONObject jo = new JSONObject(result);
						if (jo.getBoolean("result")) {
							msg.what = 1;
						} else {
							msg.obj = jo.getString("message");
						}
					} catch (JSONException e) {
						msg.obj = e.getMessage();
					}
					handler.sendMessage(msg);
				}
			}.start();
		}
	}

	private void issave() {
		Dialog dialog = MyDialog.MyDialogShow(this, R.layout.popup_isok, 1f);
		Button bt_isok_quie = (Button) dialog.findViewById(R.id.bt_isok_quie);
		Button bt_isok_ok = (Button) dialog.findViewById(R.id.bt_isok_ok);

		ListenerServer.setfinish(EditCommentsedActivity.this, bt_isok_quie);

		bt_isok_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				postcomment();
			}
		});

		dialog.show();
	}
}
