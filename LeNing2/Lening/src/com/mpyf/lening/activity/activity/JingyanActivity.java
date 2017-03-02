package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.List;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.activity.adapter.Vpadapter;
import com.mpyf.lening.activity.fragment.Fragment_jingyanShou;
import com.mpyf.lening.activity.fragment.Fragment_jingyanZhi;
import com.mpyf.lening.activity.fragment.Fragment_money;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class JingyanActivity extends FragmentActivity {

	private LinearLayout ll_jingyan_back;
	private RelativeLayout rl_jingyan_get, rl_jingyan_use;
	private ImageView iv_jingyan_get, iv_jingyan_use;
	private ViewPager vp_jingyan;
	private TextView tv_jingyan_get, tv_jingyan_use;
	private List<Fragment> listfg = new ArrayList<Fragment>();

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_jingyan);
		init();
		showinfo();
		adddlistener();
	}

	private void showinfo() {

	}

	private void init() {
		// ·µ»Ø¼ü
		ll_jingyan_back = (LinearLayout) findViewById(R.id.ll_jingyan_back);

		vp_jingyan = (ViewPager) findViewById(R.id.vp_jingyan);

		rl_jingyan_get = (RelativeLayout) findViewById(R.id.rl_jingyan_get);
		rl_jingyan_use = (RelativeLayout) findViewById(R.id.rl_jingyan_use);

		tv_jingyan_get = (TextView) findViewById(R.id.tv_jingyan_get);
		tv_jingyan_use = (TextView) findViewById(R.id.tv_jingyan_use);

		iv_jingyan_get = (ImageView) findViewById(R.id.iv_jingyan_get);
		iv_jingyan_use = (ImageView) findViewById(R.id.iv_jingyan_use);

		listfg.add(new Fragment_jingyanShou());
		listfg.add(new Fragment_jingyanZhi());
		Vpadapter adapter = new Vpadapter(getSupportFragmentManager(), listfg);
		vp_jingyan.setAdapter(adapter);
		clearall(0);

	}

	private void adddlistener() {
		ListenerServer.setfinish(JingyanActivity.this, ll_jingyan_back);

		rl_jingyan_get.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				clearall(0);
				vp_jingyan.setCurrentItem(0);
			}
		});

		rl_jingyan_use.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				clearall(1);
				vp_jingyan.setCurrentItem(1);
			}
		});

		vp_jingyan.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				if (arg0 == 2) {
					if (vp_jingyan.getCurrentItem() == 0) {
						tv_jingyan_get.setTextColor(getResources().getColor(
								R.color.main));
						tv_jingyan_use.setTextColor(getResources().getColor(
								R.color.zywz));
						iv_jingyan_get.setBackgroundColor(getResources()
								.getColor(R.color.main));
						iv_jingyan_use.setBackgroundColor(getResources()
								.getColor(R.color.dise));
					} else if (vp_jingyan.getCurrentItem() == 1) {
						tv_jingyan_get.setTextColor(getResources().getColor(
								R.color.zywz));
						tv_jingyan_use.setTextColor(getResources().getColor(
								R.color.main));
						iv_jingyan_get.setBackgroundColor(getResources()
								.getColor(R.color.dise));
						iv_jingyan_use.setBackgroundColor(getResources()
								.getColor(R.color.main));
					}
				}
			}
		});
	}

	private void clearall(int index) {
		TextView[] texttop = { tv_jingyan_get, tv_jingyan_use };
		ImageView[] imagetop = { iv_jingyan_get, iv_jingyan_use };

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
