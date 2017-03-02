package com.mpyf.lening.activity.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.interfaces.http.HttpUse;

public class RegesActivity extends Activity {

	private LinearLayout ll_reges_back;
	private EditText et_reges_complan, et_reges_id;
	private Button bt_reges_ok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		new Xiaoxibeijing().changetop(RegesActivity.this, R.color.main);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_reges);
		init();
		addlistenr();

	}

	private void init() {
		ll_reges_back = (LinearLayout) findViewById(R.id.ll_reges_back);
		et_reges_complan = (EditText) findViewById(R.id.et_reges_complan);
		et_reges_id = (EditText) findViewById(R.id.et_reges_id);
		bt_reges_ok = (Button) findViewById(R.id.bt_reges_ok);
	}

	private void addlistenr() {
		ListenerServer.setfinish(this, ll_reges_back);
		bt_reges_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				final Handler handler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						// TODO Auto-generated method stub
						if (msg.what == 1) {
							Intent intent = new Intent(RegesActivity.this,
									RegesinfoActivity.class);
							intent.putExtra("idcard", et_reges_id.getText()
									.toString());
							intent.putExtra("org", et_reges_complan.getText()
									.toString());
							startActivity(intent);
						} else {
							Diaoxian.showerror(RegesActivity.this,
									msg.obj.toString());
						}
					}
				};

				new Thread() {
					@Override
					public void run() {
						JSONObject jo, jo1;
						Message msg = new Message();
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("dptname", et_reges_complan.getText()
								.toString());
						String result = HttpUse.messageget("Account",
								"verifyDeptName", map);

						try {
							jo = new JSONObject(result);
							if (jo.getBoolean("result")) {
								String isid;
								try {
									isid = Diaoxian.IDCardValidate(et_reges_id
											.getText().toString());
									
									if(et_reges_id.getText().toString().equals("")){
										msg.obj="身份证号不能为空";
									}else if (!isid.equals("")) {
										msg.obj=isid;
										
									} else {
										Map<String, Object> map1 = new HashMap<String, Object>();
										map1.put("username", et_reges_id
												.getText().toString());
										String result1 = HttpUse.messageget(
												"Account", "verifyUserName",
												map1);
										jo1 = new JSONObject(result1);
										if (!jo1.getBoolean("result")) {
											msg.what = 1;
											msg.obj = jo1.getString("message");
										} else {
											msg.obj = jo1.getString("message");
										}
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									msg.obj = e.getMessage();
								}

							} else {
								msg.obj = jo.getString("message");
							}

						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							msg.obj = e1.getMessage();
						}

						handler.sendMessage(msg);

					};
				}.start();

			}

		});
	}

}
