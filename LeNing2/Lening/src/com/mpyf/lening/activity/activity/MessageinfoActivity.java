package com.mpyf.lening.activity.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.interfaces.http.HttpUse;

public class MessageinfoActivity extends Activity {

	private LinearLayout ll_messageinfo_back;
	private TextView tv_messageinfo_type, tv_messageinfo_context,
			tv_messageinfo_time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		new Xiaoxibeijing().changetop(MessageinfoActivity.this, R.color.main);
		setContentView(R.layout.activity_messageinfo);
		init();
		getdata();
		addlistener();
	}

	private void init() {
		ll_messageinfo_back = (LinearLayout) findViewById(R.id.ll_messageinfo_back);
		tv_messageinfo_type = (TextView) findViewById(R.id.tv_messageinfo_type);
		tv_messageinfo_context = (TextView) findViewById(R.id.tv_messageinfo_context);
		tv_messageinfo_time = (TextView) findViewById(R.id.tv_messageinfo_time);
	}

	private void getdata() {

		final Dialog dialog = MyDialog.MyDialogloading(this);
		dialog.show();

		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				dialog.dismiss();
				if (msg.what == 1) {
					try {
						JSONObject jo = new JSONObject(msg.obj.toString());

						tv_messageinfo_type.setText(jo.getString("msg_NAME"));
						tv_messageinfo_time.setText(jo.getString("msg_Time"));
						tv_messageinfo_context.setText(Html.fromHtml(jo
								.getString("msg_CONTENT")));

					} catch (JSONException e) {
						Diaoxian.showerror(MessageinfoActivity.this,
								e.getMessage());
					}

				} else {
					Diaoxian.showerror(MessageinfoActivity.this,
							msg.obj.toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				final Map<String, Object> map = new HashMap<String, Object>();
				map.put("msgId", getIntent().getStringExtra("id"));
				String result = HttpUse.messageget("PersonalCenter",
						"getMsgBymsgId", map);
				Message msg = new Message();
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = jo.getString("data");
					} else {
						msg.obj = jo.getString("message");
					}
				} catch (JSONException e) {
					//
					msg.obj = e.getMessage();
				}
				handler.sendMessage(msg);
			};
		}.start();

	}

	private void addlistener() {
		ListenerServer.setfinish(MessageinfoActivity.this, ll_messageinfo_back);
	}
}
