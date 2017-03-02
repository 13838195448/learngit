package com.mpyf.lening.activity.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.interfaces.bean.Parame.ShippingAddress;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EditAddressActivity extends Activity {
	private LinearLayout ll_editadd_back;
	private EditText et_name, et_phone, et_address;
	private TextView tv_save;
	private RelativeLayout rl_delete;
	private String pk_adr;
	private int is_default;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_editaddress);
		init();
		showinfo();
		addlistener();
	}

	private void init() {
		ll_editadd_back = (LinearLayout) findViewById(R.id.ll_editadd_back);
		rl_delete = (RelativeLayout) findViewById(R.id.rl_delete);
		et_name = (EditText) findViewById(R.id.et_name);
		et_phone = (EditText) findViewById(R.id.et_phone);
		et_address = (EditText) findViewById(R.id.et_address);
		tv_save = (TextView) findViewById(R.id.tv_save);
	}

	private void showinfo() {
		et_name.setText(getIntent().getStringExtra("name"));
		et_phone.setText(getIntent().getStringExtra("phone"));
		et_address.setText(getIntent().getStringExtra("address"));
		pk_adr = getIntent().getStringExtra("pk_adr");
		// is_default是否默认地址[Int32]0否，1是
	}

	private void addlistener() {
		// 返回键
		ListenerServer.setfinish(EditAddressActivity.this, ll_editadd_back);

		// 保存按钮
		tv_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!(et_phone.getText().length() == 11)) {
					Toast.makeText(EditAddressActivity.this, "电话号码长度为11位",
							Toast.LENGTH_SHORT).show();
				} else {
					// TODO 保存 地址

					final Handler handler = new Handler() {

						@Override
						public void handleMessage(Message msg) {
							if (msg.what == 1) {

								Toast.makeText(EditAddressActivity.this,
										"保存成功", Toast.LENGTH_SHORT).show();

								// setResult(RESULT_ADD_CODE);
								finish();

							} else {
								Diaoxian.showerror(EditAddressActivity.this,
										msg.obj.toString());
							}
						}
					};

					new Thread() {
						public void run() {
							Message msg = new Message();
							ShippingAddress sa = new ShippingAddress();
							sa.setAddress(et_address.getText().toString());
							sa.setConsignee(et_name.getText().toString());
							sa.setMphone(et_phone.getText().toString());
							sa.setPk_adr(pk_adr);
							sa.setIs_default(getIntent().getIntExtra(
									"is_default", -1));
							String result = HttpUse.messagepost("QueAndAns",
									"saveShippingAddress", sa);

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
						};
					}.start();
				}

			}
		});

		// TODO 删除按钮
		rl_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 保存 地址

				final Handler handler = new Handler() {

					@Override
					public void handleMessage(Message msg) {
						if (msg.what == 1) {

							Toast.makeText(EditAddressActivity.this, "删除成功",
									Toast.LENGTH_SHORT).show();

							// setResult(RESULT_ADD_CODE);
							finish();

						} else {
							Diaoxian.showerror(EditAddressActivity.this,
									msg.obj.toString());
						}
					}
				};

				new Thread() {
					public void run() {
						Message msg = new Message();
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("pk_adr", pk_adr);
						String result = HttpUse.messageget("QueAndAns",
								"deleteShippingAddress", map);

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
					};
				}.start();
			}
		});
	}
}
