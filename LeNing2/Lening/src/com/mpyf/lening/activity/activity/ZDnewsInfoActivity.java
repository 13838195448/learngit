package com.mpyf.lening.activity.activity;

import io.vov.vitamio.activity.InitActivity;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.ListenerServer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ZDnewsInfoActivity extends Activity {

	private TextView tv_title, tv_context;
	private LinearLayout ll_zd_newsinfo_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_zdnewsinfo);
		
		String title = getIntent().getStringExtra("title");
		String context = getIntent().getStringExtra("context");
		init();
		
		tv_title.setText(title);
		tv_context.setText(context);
		
		ListenerServer.setfinish(this, ll_zd_newsinfo_back);
	}

	private void init() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_context = (TextView) findViewById(R.id.tv_context);
		ll_zd_newsinfo_back=(LinearLayout) findViewById(R.id.ll_zd_newsinfo_back);
	}
}
