package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.MyGridview;
import com.mpyf.lening.activity.adapter.DaohangAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class MoreActivity extends Activity {

	private LinearLayout ll_huodong_back;
	private TextView tv_huodong_title;
	private MyGridview gv_more_daohang;
	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_more);
		init();
		showinfo();
		addlistener();
	}

	private void init() {
		ll_huodong_back = (LinearLayout) findViewById(R.id.ll_huodong_back);
		tv_huodong_title = (TextView) findViewById(R.id.tv_huodong_title);
		tv_huodong_title.setText("功能导航");

		gv_more_daohang = (MyGridview) findViewById(R.id.gv_more_daohang);
	}

	/**
	 * 加载导航布局
	 */
	private void showinfo() {
		String[] name = { "知道", "培训", "测一测", "专题", "资源", "资讯", "认证", "签到", "商城" };
		int[] image = { R.drawable.home_btn_qa, R.drawable.home_btn_train,
				R.drawable.home_btn_test, R.drawable.home_btn_subject,
				R.drawable.home_btn_resource, R.drawable.home_btn_inform,
				R.drawable.home_btn_authe, R.drawable.home_btn_check,
				R.drawable.home_btn_shop };
		List<Map<String, Object>> listdaohang = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < name.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", name[i]);
			map.put("image", image[i]);
			listdaohang.add(map);
		}
		DaohangAdapter adapter = new DaohangAdapter(MoreActivity.this,
				listdaohang);
		gv_more_daohang.setAdapter(adapter);
	}

	private void addlistener() {
		// 返回键
		ListenerServer.setfinish(MoreActivity.this, ll_huodong_back);

		gv_more_daohang.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case 0:
					// 跳转到 知道Activity
					startActivity(new Intent(MoreActivity.this,
							ZhidaoActivity.class));
					break;
				case 1:
					// 跳转到 培训Activity
					startActivity(new Intent(MoreActivity.this,
							PeixunActivity.class));
					break;
				case 2:
					// 跳转到 测一测Activity
					startActivity(new Intent(MoreActivity.this,
							ExamActivity.class));
					break;
				case 3:
					// 跳转到 专题Activity
					startActivity(new Intent(MoreActivity.this,
							ZhuantiActivity.class));
					break;
				case 4:
					// 跳转到 资源Activity
					startActivity(new Intent(MoreActivity.this,
							ZiYuanActivity.class));

					break;
				case 5:
					// 跳转到 资讯Activity
					startActivity(new Intent(MoreActivity.this,
							NewsActivity.class));

					break;
				// 跳转到 社区Activity
				// case 5:
				// startActivity(new Intent(getActivity(),
				// CommunityActivity.class));
				// break;
				case 6:
					// 跳转到 认证Activity
					startActivity(new Intent(MoreActivity.this,
							NLRZActivity.class));
					break;
				case 7:

					// 签到相关操作
					dialog = MyDialog.MyDialogloading(MoreActivity.this);
					dialog.show();
					final Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							dialog.dismiss();
							if (msg.what == 1) {
								qiandao();
							} else {
								Diaoxian.showerror(MoreActivity.this, "今天已签到");
							}
						}
					};

					new Thread() {
						@Override
						public void run() {
							Map<String, Object> map = new HashMap<String, Object>();
							String result = HttpUse.messageget("Account",
									"isSignIn", map);
							Message msg = new Message();
							try {
								JSONObject jo = new JSONObject(result);
								if (jo.getBoolean("result")) {
									msg.what = 1;
								}
								handler.sendMessage(msg);
							} catch (JSONException e) {
								e.getMessage();
							}
						};
					}.start();

					break;
				case 8:
					// 跳转到 商城Activity
					startActivity(new Intent(MoreActivity.this,
							ShangChengActivity.class));
					break;
				case 9:
					// 跳转到 更多Activity
					startActivity(new Intent(MoreActivity.this,
							MoreActivity.class));
					break;

				default:
					break;
				}
			}
		});

	}

	/**
	 * 签到动画
	 */
	private void qiandao() {

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(final Message msg) {
				if (msg.what == 1) {

					try {
						final JSONObject jo = new JSONObject(msg.obj.toString());

						final Dialog dialog = MyDialog.MyDialogShow(
								MoreActivity.this, R.layout.pop_qiandao, 1.0f);

						Button bt_qiandao_ok = (Button) dialog
								.findViewById(R.id.bt_qiandao_ok);
						final ImageView iv_qiandao_donghua = (ImageView) dialog
								.findViewById(R.id.iv_qiandao_donghua);
						final TextView tv_qiandao_days = (TextView) dialog
								.findViewById(R.id.tv_qiandao_days);

						TextView tv_qiandao_rule = (TextView) dialog
								.findViewById(R.id.tv_qiandao_rule);

						iv_qiandao_donghua
								.setBackgroundResource(R.anim.qiandao);
						final AnimationDrawable animation = (AnimationDrawable) iv_qiandao_donghua
								.getBackground();
						tv_qiandao_days.setText("连续签到" + jo.getInt("days")
								+ "天");

						Setting.les = jo.getInt("class_gold");
						bt_qiandao_ok.setText("领取乐币×" + jo.getInt("num"));
						tv_qiandao_rule.setText(jo.getString("rules"));

						bt_qiandao_ok.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {

								animation.start();
								Timer timer = new Timer();
								TimerTask task = new TimerTask() {

									@Override
									public void run() {
										dialog.dismiss();

										startActivity(new Intent(
												MoreActivity.this,
												MainActivity.class));
										MoreActivity.this.finish();
									}
								};
								timer.schedule(task, 3000);
							}
						});
						dialog.show();
					} catch (JSONException e) {
						Diaoxian.showerror(MoreActivity.this, e.getMessage());
					}

				} else {
					Diaoxian.showerror(MoreActivity.this, msg.obj.toString());
				}
			}
		};
		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				Message msg = new Message();
				try {

					String result = HttpUse
							.messageget("Account", "signIn", map);

					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = jo.getString("data");
					} else {
						msg.obj = jo.getString("message");
					}
				} catch (Exception e) {
					msg.obj = e.getMessage();
				}
				handler.sendMessage(msg);
			};
		}.start();

	}
}
