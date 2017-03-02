package com.mpyf.lening.activity.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.Xiaoxibeijing;

public class JingyandetilZhiActivity extends Activity {

	private LinearLayout ll_jingyandetil_back;
	private TextView tv_jingyandetil_jine, tv_jingyandetil_xiangqing,
			tv_jingyandetil_time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		new Xiaoxibeijing().changetop(this, R.color.main);
		setContentView(R.layout.activity_jingyandetil);
		init();
		showinfo();
		ListenerServer.setfinish(JingyandetilZhiActivity.this,
				ll_jingyandetil_back);
	}

	private void init() {
		ll_jingyandetil_back = (LinearLayout) findViewById(R.id.ll_jingyandetil_back);
		tv_jingyandetil_jine = (TextView) findViewById(R.id.tv_jingyandetil_jine);
		tv_jingyandetil_xiangqing = (TextView) findViewById(R.id.tv_jingyandetil_xiangqing);
		tv_jingyandetil_time = (TextView) findViewById(R.id.tv_jingyandetil_time);
	}

	private void showinfo() {
		Intent intent = getIntent();
		tv_jingyandetil_xiangqing.setText(intent.getStringExtra("Rules"));
		tv_jingyandetil_jine.setText("-" + intent.getStringExtra("Num"));
		String time = intent.getStringExtra("time");
		tv_jingyandetil_time
				.setText(time.subSequence(0, time.lastIndexOf(":")));
	}
}
