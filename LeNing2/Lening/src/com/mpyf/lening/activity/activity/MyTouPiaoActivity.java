package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.activity.adapter.Vpadapter;
import com.mpyf.lening.activity.fragment.MyCanTouPiaoFragment;
import com.mpyf.lening.activity.fragment.MyFaTouPiaoFragment;

public class MyTouPiaoActivity extends FragmentActivity {

	private LinearLayout ll_mytoupiao_back;
	private RelativeLayout rl_mytoupiao_fa, rl_mytoupiao_can;
	private ImageView iv_mytoupiao_fa, iv_mytoupiao_can;
	private TextView tv_mytoupiao_fa, tv_mytoupiao_can;
	private ViewPager vp_mytoupian;
	private List<Fragment> fr = new ArrayList<Fragment>();

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mytoupiao);
		init();
		getData();
		addlistener();
	}

	private void init() {
		ll_mytoupiao_back = (LinearLayout) findViewById(R.id.ll_mytoupiao_back);
		vp_mytoupian = (ViewPager) findViewById(R.id.vp_mytoupian);

		rl_mytoupiao_fa = (RelativeLayout) findViewById(R.id.rl_mytoupiao_fa);
		rl_mytoupiao_can = (RelativeLayout) findViewById(R.id.rl_mytoupiao_can);

		iv_mytoupiao_fa = (ImageView) findViewById(R.id.iv_mytoupiao_fa);
		iv_mytoupiao_can = (ImageView) findViewById(R.id.iv_mytoupiao_can);

		tv_mytoupiao_fa = (TextView) findViewById(R.id.tv_mytoupiao_fa);
		tv_mytoupiao_can = (TextView) findViewById(R.id.tv_mytoupiao_can);

		fr.add(new MyFaTouPiaoFragment());
		fr.add(new MyCanTouPiaoFragment());
		Vpadapter adapter = new Vpadapter(getSupportFragmentManager(), fr);
		vp_mytoupian.setAdapter(adapter);
	}

	private void getData() {

	}

	private void addlistener() {
		// 返回键
		ListenerServer.setfinish(MyTouPiaoActivity.this, ll_mytoupiao_back);

		// 我发起的
		rl_mytoupiao_fa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clearall(0);
				vp_mytoupian.setCurrentItem(0);
			}
		});
		// 我参与的
		rl_mytoupiao_can.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clearall(1);
				vp_mytoupian.setCurrentItem(1);
			}
		});

		vp_mytoupian.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				if (vp_mytoupian.getCurrentItem() == 0) {
					tv_mytoupiao_fa.setTextColor(getResources().getColor(
							R.color.main));
					tv_mytoupiao_can.setTextColor(getResources().getColor(
							R.color.zywz));
					iv_mytoupiao_fa.setBackgroundColor(getResources().getColor(
							R.color.main));
					iv_mytoupiao_can.setBackgroundColor(getResources()
							.getColor(R.color.dise));
				} else if (vp_mytoupian.getCurrentItem() == 1) {
					tv_mytoupiao_fa.setTextColor(getResources().getColor(
							R.color.zywz));
					tv_mytoupiao_can.setTextColor(getResources().getColor(
							R.color.main));
					iv_mytoupiao_fa.setBackgroundColor(getResources().getColor(
							R.color.dise));
					iv_mytoupiao_can.setBackgroundColor(getResources()
							.getColor(R.color.main));
				}

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	private void clearall(int index) {
		TextView[] texttop = { tv_mytoupiao_fa, tv_mytoupiao_can };
		ImageView[] imagetop = { iv_mytoupiao_fa, iv_mytoupiao_can };

		for (int i = 0; i < texttop.length; i++) {
			if (i == index) {
				texttop[i].setTextColor(getResources().getColor(R.color.main));
				imagetop[i].setBackgroundColor(getResources().getColor(
						R.color.main));
			} else {
				texttop[i].setTextColor(getResources().getColor(R.color.zywz));
				imagetop[i].setBackgroundColor(getResources().getColor(
						R.color.dise));
			}
		}
	}

}
