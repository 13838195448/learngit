package com.mpyf.lening.activity.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class ChengjiActivity extends Activity {

	private LinearLayout ll_chengjidelio_back;
	private TextView tv_chengjideilo_cername, tv_chengjideilo_name,
			tv_chengjidelio_result, tv_chengjidelio_level,
			tv_chengjidelio_phone, tv_chengjidelio_email,
			tv_chengjidelio_zhuanye, tv_chengjidelio_cpjb,
			tv_chengjidelio_zyly, tv_chengjidelio_bkjb, tv_chengjidelio_xsxx,
			tv_chengjidelio_chengji, tv_chengjidelio_zsjn,
			tv_chengjidelio_ksjg, tv_chengjidelio_hqzg, tv_chengjidelio_psdf,
			tv_chengjidelio_zhdf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		new Xiaoxibeijing().changetop(this, R.color.main);
		setContentView(R.layout.activity_chengjidelio);
		init();
		showinfo();
		addlistener();
	}

	private void init() {
		ll_chengjidelio_back = (LinearLayout) findViewById(R.id.ll_chengjidelio_back);

		tv_chengjideilo_cername = (TextView) findViewById(R.id.tv_chengjideilo_cername);
		tv_chengjideilo_name = (TextView) findViewById(R.id.tv_chengjideilo_name);
		tv_chengjidelio_result = (TextView) findViewById(R.id.tv_chengjidelio_result);
		tv_chengjidelio_level = (TextView) findViewById(R.id.tv_chengjidelio_level);
		tv_chengjidelio_phone = (TextView) findViewById(R.id.tv_chengjidelio_phone);
		tv_chengjidelio_email = (TextView) findViewById(R.id.tv_chengjidelio_email);
		tv_chengjidelio_zhuanye = (TextView) findViewById(R.id.tv_chengjidelio_zhuanye);
		tv_chengjidelio_cpjb = (TextView) findViewById(R.id.tv_chengjidelio_cpjb);
		tv_chengjidelio_zyly = (TextView) findViewById(R.id.tv_chengjidelio_zyly);
		tv_chengjidelio_bkjb = (TextView) findViewById(R.id.tv_chengjidelio_bkjb);
		tv_chengjidelio_xsxx = (TextView) findViewById(R.id.tv_chengjidelio_xsxx);
		tv_chengjidelio_chengji = (TextView) findViewById(R.id.tv_chengjidelio_chengji);
		tv_chengjidelio_zsjn = (TextView) findViewById(R.id.tv_chengjidelio_zsjn);
		tv_chengjidelio_ksjg = (TextView) findViewById(R.id.tv_chengjidelio_ksjg);
		tv_chengjidelio_hqzg = (TextView) findViewById(R.id.tv_chengjidelio_hqzg);
		tv_chengjidelio_psdf = (TextView) findViewById(R.id.tv_chengjidelio_psdf);
		tv_chengjidelio_zhdf = (TextView) findViewById(R.id.tv_chengjidelio_zhdf);

	}

	private void showinfo() {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 1) {
					try {
						JSONObject jo = new JSONObject(msg.obj.toString());
						tv_chengjideilo_cername.setText(jo
								.getString("cer_Name"));
						tv_chengjideilo_name.setText(jo.getString("trueName"));
						tv_chengjidelio_result.setText(jo
								.getString("skillsResults"));
						tv_chengjidelio_level.setText(jo.getString("RZJB"));
						tv_chengjidelio_phone.setText(jo.getString("mphone"));
						tv_chengjidelio_email.setText(jo.getString("email"));
						tv_chengjidelio_zhuanye.setText(jo.getString("YWFX"));
						tv_chengjidelio_cpjb.setText(jo.getString("CPYY"));
						tv_chengjidelio_zyly.setText(jo.getString("ZYLY"));
						tv_chengjidelio_bkjb.setText(jo.getString("BKJB"));
						tv_chengjidelio_xsxx.setText(jo
								.getString("lineResults"));
						tv_chengjidelio_chengji.setText(jo
								.getString("examResults"));
						tv_chengjidelio_zsjn.setText(jo
								.getString("totalResults"));
						tv_chengjidelio_ksjg.setText(jo
								.getString("skillsResults"));
						tv_chengjidelio_hqzg.setText(jo
								.getString("qualifications"));
						tv_chengjidelio_psdf.setText(jo
								.getString("reviewResult"));
						tv_chengjidelio_zhdf
								.setText(jo.getString("cerResults"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Diaoxian.showerror(ChengjiActivity.this, e.getMessage());
					}

				} else {
					Diaoxian.showerror(ChengjiActivity.this, msg.obj.toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("cerID", getIntent().getStringExtra("id"));
				Message msg = new Message();
				String result = HttpUse.messageget("AbilityCertification",
						"queryResultsInfo", map);
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = jo.getString("data");
					} else {
						msg.obj = jo.getString("message");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					msg.obj = e.getMessage();
				}

				handler.sendMessage(msg);

			}
		}.start();
	}

	private void addlistener() {
		ListenerServer.setfinish(ChengjiActivity.this, ll_chengjidelio_back);
	}
}
