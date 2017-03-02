package com.mpyf.lening.activity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.Xiaoxibeijing;

public class MoneydetilActivity extends Activity {

	private LinearLayout ll_moneydetil_back;
	private TextView tv_moneydetil_jine, tv_moneydetil_type,
			tv_moneydetil_xiangqing, tv_moneydetil_time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		new Xiaoxibeijing().changetop(this, R.color.main);
		setContentView(R.layout.activity_moneydetil);
		init();
		showinfo();
		addlistener();
	}

	private void init() {
		ll_moneydetil_back = (LinearLayout) findViewById(R.id.ll_moneydetil_back);
		tv_moneydetil_jine = (TextView) findViewById(R.id.tv_moneydetil_jine);
		tv_moneydetil_type = (TextView) findViewById(R.id.tv_moneydetil_type);
		tv_moneydetil_xiangqing = (TextView) findViewById(R.id.tv_moneydetil_xiangqing);
		tv_moneydetil_time = (TextView) findViewById(R.id.tv_moneydetil_time);
	}

	private void showinfo() {
		Intent intent = getIntent();

		tv_moneydetil_type
				.setText(intent.getStringExtra("type").equals("le") ? "ÀÖ±Ò"
						: "½ð±Ò");
		tv_moneydetil_xiangqing.setText(intent.getStringExtra("Rules"));

		if (intent.getIntExtra("shouzhi", 0) == 0
				|| intent.getIntExtra("shouzhi", 0) == 2) {

			tv_moneydetil_jine.setText("+" + intent.getStringExtra("Num"));
		} else {
			tv_moneydetil_jine.setText("-" + intent.getStringExtra("Num"));
		}
		String time = intent.getStringExtra("time");
		tv_moneydetil_time.setText(time.subSequence(0, time.lastIndexOf(":")));
	}

	private void addlistener() {
		ListenerServer.setfinish(MoneydetilActivity.this, ll_moneydetil_back);
	}
}
