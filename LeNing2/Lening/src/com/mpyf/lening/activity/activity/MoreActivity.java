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
		tv_huodong_title.setText("���ܵ���");

		gv_more_daohang = (MyGridview) findViewById(R.id.gv_more_daohang);
	}

	/**
	 * ���ص�������
	 */
	private void showinfo() {
		String[] name = { "֪��", "��ѵ", "��һ��", "ר��", "��Դ", "��Ѷ", "��֤", "ǩ��", "�̳�" };
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
		// ���ؼ�
		ListenerServer.setfinish(MoreActivity.this, ll_huodong_back);

		gv_more_daohang.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case 0:
					// ��ת�� ֪��Activity
					startActivity(new Intent(MoreActivity.this,
							ZhidaoActivity.class));
					break;
				case 1:
					// ��ת�� ��ѵActivity
					startActivity(new Intent(MoreActivity.this,
							PeixunActivity.class));
					break;
				case 2:
					// ��ת�� ��һ��Activity
					startActivity(new Intent(MoreActivity.this,
							ExamActivity.class));
					break;
				case 3:
					// ��ת�� ר��Activity
					startActivity(new Intent(MoreActivity.this,
							ZhuantiActivity.class));
					break;
				case 4:
					// ��ת�� ��ԴActivity
					startActivity(new Intent(MoreActivity.this,
							ZiYuanActivity.class));

					break;
				case 5:
					// ��ת�� ��ѶActivity
					startActivity(new Intent(MoreActivity.this,
							NewsActivity.class));

					break;
				// ��ת�� ����Activity
				// case 5:
				// startActivity(new Intent(getActivity(),
				// CommunityActivity.class));
				// break;
				case 6:
					// ��ת�� ��֤Activity
					startActivity(new Intent(MoreActivity.this,
							NLRZActivity.class));
					break;
				case 7:

					// ǩ����ز���
					dialog = MyDialog.MyDialogloading(MoreActivity.this);
					dialog.show();
					final Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							dialog.dismiss();
							if (msg.what == 1) {
								qiandao();
							} else {
								Diaoxian.showerror(MoreActivity.this, "������ǩ��");
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
					// ��ת�� �̳�Activity
					startActivity(new Intent(MoreActivity.this,
							ShangChengActivity.class));
					break;
				case 9:
					// ��ת�� ����Activity
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
	 * ǩ������
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
						tv_qiandao_days.setText("����ǩ��" + jo.getInt("days")
								+ "��");

						Setting.les = jo.getInt("class_gold");
						bt_qiandao_ok.setText("��ȡ�ֱҡ�" + jo.getInt("num"));
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
