package com.mpyf.lening.activity.activity;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
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
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.Readsaved;
import com.mpyf.lening.Jutil.Writesaved;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.interfaces.bean.Parame.Login;
import com.mpyf.lening.interfaces.bean.Parame.User;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class LoginActivity extends Activity {

	private LinearLayout ll_login_input;
	private EditText et_logoin_id, et_logoin_pwd;
	private ImageView iv_login_delete;
	private Button bt_login_login;
	private TextView tv_login_forgetpwd, tv_login_reges;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		new Xiaoxibeijing().changetop(LoginActivity.this, R.color.touming);
		setContentView(R.layout.activity_login);
		init();
		addlistenr();

	}

	private void init() {
		ll_login_input = (LinearLayout) findViewById(R.id.ll_login_input);
		et_logoin_id = (EditText) findViewById(R.id.et_logoin_id);
		et_logoin_pwd = (EditText) findViewById(R.id.et_logoin_pwd);
		iv_login_delete = (ImageView) findViewById(R.id.iv_login_delete);
		bt_login_login = (Button) findViewById(R.id.bt_login_login);
		tv_login_forgetpwd = (TextView) findViewById(R.id.tv_login_forgetpwd);
		tv_login_reges = (TextView) findViewById(R.id.tv_login_reges);

		ll_login_input.getBackground().setAlpha(200);
		et_logoin_id.getBackground().setAlpha(0);
		et_logoin_pwd.getBackground().setAlpha(0);
		Setting.apiKey = "6C9FBC3C-03BF-4329-BBE7-EFAC4C404253";

		if (Readsaved.read(LoginActivity.this, "username") != null) {
			et_logoin_id
					.setText(Readsaved.read(LoginActivity.this, "username"));
		}

		if (Readsaved.read(LoginActivity.this, "password") != null) {
			et_logoin_pwd.setText(Readsaved
					.read(LoginActivity.this, "password"));
		}
	}

	private void addlistenr() {
		bt_login_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (et_logoin_id.getText().toString().equals("")) {
					Diaoxian.showerror(LoginActivity.this, "请输入账号");
				} else if (et_logoin_pwd.getText().toString().equals("")) {
					Diaoxian.showerror(LoginActivity.this, "请输入密码");
				} else {
					final Dialog dialog = MyDialog
							.MyDialogloading(LoginActivity.this);
					dialog.show();
					final Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							/**
							 * [Result] {"data":{"deptname":"用友优普",
							 * "email":"zoujzh@yonyou.com", "field":"人力资源",
							 * "key":"97d5a32a139e4fb6916ee5bd6de05001",
							 * "mphone":"13520342536", "nickname":"zoujzh",
							 * "pk_user":2133, "professional":"计算机科学与技术",
							 * "sex":2,"sourcefrom":"", "time":"","token":"",
							 * "truename":"邹建洲",
							 * "username":"420923198610010117"}
							 * ,"message":"","result":true}
							 */
							if (msg.what == 1) {

								try {
									JSONObject jo = new JSONObject(msg.obj
											.toString());
									Setting.apiKey = jo.getString("key");
									Setting.currentUser = new User();
									Setting.currentUser.setDeptname(jo
											.getString("deptname"));
									Setting.currentUser.setEmail(jo
											.getString("email"));
									Setting.currentUser.setField(jo
											.getString("field"));

									Setting.currentUser.setMphone(jo
											.getString("mphone"));
									Setting.currentUser.setIntroduce(jo
											.getString("introduce"));
									Setting.currentUser.setNickname(jo
											.getString("nickname"));
									Setting.currentUser.setPk_user(jo
											.getInt("pk_user"));
									Setting.currentUser.setProfessional(jo
											.getString("professional"));
									Setting.currentUser.setSex(jo.getInt("sex"));
									Setting.currentUser.setTruename(jo
											.getString("truename"));
									Setting.currentUser.setUsername(jo
											.getString("username"));
									Setting.currentUser.setEducation(jo
											.getInt("education"));
									Setting.currentUser.setSoftTime(jo
											.getString("softTime"));
									Setting.currentUser.setHonor_name(jo
											.getString("honor_name"));
									Writesaved.write(LoginActivity.this,
											"username", et_logoin_id.getText()
													.toString());
									Writesaved.write(LoginActivity.this,
											"password", et_logoin_pwd.getText()
													.toString());
									Writesaved.write(LoginActivity.this, "off",
											"on");

								} catch (JSONException e) {
									Diaoxian.showerror(LoginActivity.this,
											e.getMessage());
								}

								Intent intent = new Intent(LoginActivity.this,
										MainActivity.class);
								startActivity(intent);
								finish();
							} else {
								Diaoxian.showerror(LoginActivity.this,
										msg.obj.toString());
							}
						}
					};

					new Thread() {
						@Override
						public void run() {

							Login login = new Login();
							login.setUsername(et_logoin_id.getText().toString());
							login.setPassword(et_logoin_pwd.getText()
									.toString());

							Message msg = new Message();

							try {
								String result = HttpUse.messagepost("Account",
										"login", login);
								JSONObject jo = new JSONObject(result);
								if (jo.getBoolean("result")) {
									msg.what = 1;
									msg.obj = jo.getString("data");
								} else {
									msg.obj = jo.getString("message");
								}

							} catch (JSONException e) {
								msg.obj = e.getMessage();
							}

							handler.sendMessage(msg);

						};
					}.start();

				}
			}
		});

		iv_login_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				et_logoin_id.setText("");
			}
		});

		tv_login_forgetpwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(LoginActivity.this,
						ForgetpwdActivity.class));
			}
		});

		tv_login_reges.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(LoginActivity.this,
						RegesActivity.class));

			}
		});
	}

	/**
	 * 菜单、返回键响应
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click(); // 调用双击退出函数
		}
		return false;
	}

	/**
	 * 双击退出函数
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出乐柠", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		} else {
			finish();
			System.exit(0);
		}
	}

}
