package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.activity.adapter.Vpadapter;
import com.mpyf.lening.activity.fragment.AskFragement;
import com.mpyf.lening.activity.fragment.EventFragement;
import com.mpyf.lening.activity.fragment.MyFragement;

public class ZhidaoActivity extends FragmentActivity implements OnClickListener {

	private List<Fragment> mfs = new ArrayList<Fragment>();
	private LinearLayout ll_zhidao_shouye;
	private ImageView iv_zhidao_shouye, iv_zhidao_wenda, iv_zhidao_huodong,
			iv_zhidao_wo;
	private ViewPager vp;
	private TextView tv_zhidao_shouye, tv_zhidao_wenda, tv_zhidao_wo,
			tv_zhidao_huodong;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_zhidao);
		initUi();
		initTabFragment();

	}

	// 添加3个Fragment
	private void initTabFragment() {
		mfs.add(new AskFragement());
		mfs.add(new EventFragement());
		mfs.add(new MyFragement());
		Vpadapter adapter = new Vpadapter(getSupportFragmentManager(), mfs);
		vp.setAdapter(adapter);
		vp.setCurrentItem(0);
	}

	// 初始化控件
	private void initUi() {
		ll_zhidao_shouye = (LinearLayout) findViewById(R.id.ll_zhidao_shouye);
		ll_zhidao_shouye.setOnClickListener(this);
		vp = (ViewPager) findViewById(R.id.vp);
		iv_zhidao_shouye = (ImageView) findViewById(R.id.iv_zhidao_shouye);
		iv_zhidao_wenda = (ImageView) findViewById(R.id.iv_zhidao_wenda);
		iv_zhidao_huodong = (ImageView) findViewById(R.id.iv_zhidao_huodong);
		iv_zhidao_wo = (ImageView) findViewById(R.id.iv_zhidao_wo);

		tv_zhidao_shouye = (TextView) findViewById(R.id.tv_zhidao_shouye);
		tv_zhidao_wenda = (TextView) findViewById(R.id.tv_zhidao_wenda);
		tv_zhidao_huodong = (TextView) findViewById(R.id.tv_zhidao_huodong);
		tv_zhidao_wo = (TextView) findViewById(R.id.tv_zhidao_wo);

		findViewById(R.id.home_ask).setOnClickListener(this);
		findViewById(R.id.home_event).setOnClickListener(this);
		findViewById(R.id.home_my).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_zhidao_shouye:
			Intent intent = new Intent(ZhidaoActivity.this, MainActivity.class);
			startActivity(intent);
			break;
		case R.id.home_ask:
			vp.setCurrentItem(0);
			// 问答 选中
			iv_zhidao_wenda.setImageResource(R.drawable.zd_know_answer_sel);
			tv_zhidao_wenda.setTextColor(getResources().getColor(R.color.main));
			// 活动 不选中
			iv_zhidao_huodong.setImageResource(R.drawable.zd_know_active_nor);
			tv_zhidao_huodong.setTextColor(getResources().getColor(
					R.color.ciyao));
			// 我 不选中
			iv_zhidao_wo.setImageResource(R.drawable.zd_btn_me_n);
			tv_zhidao_wo.setTextColor(getResources().getColor(R.color.ciyao));
			break;
		case R.id.home_event:
			vp.setCurrentItem(1);
			// 活动 选中
			iv_zhidao_huodong.setImageResource(R.drawable.zd_know_active_sel);
			tv_zhidao_huodong.setTextColor(getResources()
					.getColor(R.color.main));
			// 问答 不选中
			iv_zhidao_wenda.setImageResource(R.drawable.zd_know_answer_nor);
			tv_zhidao_wenda
					.setTextColor(getResources().getColor(R.color.ciyao));
			// 我 不选中
			iv_zhidao_wo.setImageResource(R.drawable.zd_btn_me_n);
			tv_zhidao_wo.setTextColor(getResources().getColor(R.color.ciyao));

			break;
		case R.id.home_my:
			vp.setCurrentItem(2);
			// 问答 不选中
			iv_zhidao_wenda.setImageResource(R.drawable.zd_know_answer_nor);
			tv_zhidao_wenda
					.setTextColor(getResources().getColor(R.color.ciyao));
			// 活动 不选中
			iv_zhidao_huodong.setImageResource(R.drawable.zd_know_active_nor);
			tv_zhidao_huodong.setTextColor(getResources().getColor(
					R.color.ciyao));
			// 我 选中
			iv_zhidao_wo.setImageResource(R.drawable.zd_btn_me_s);
			tv_zhidao_wo.setTextColor(getResources().getColor(R.color.main));
			break;
		}
	}
}
