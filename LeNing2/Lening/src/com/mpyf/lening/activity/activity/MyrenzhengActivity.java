package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.List;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.activity.adapter.Vpadapter;
import com.mpyf.lening.activity.fragment.Fragment_myrenzheng;

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
import android.widget.TextView;

public class MyrenzhengActivity extends FragmentActivity implements
		OnClickListener {

	private LinearLayout ll_myrz_back;
	private TextView tv_myrz_baoming;
	private TextView tv_myrz_chengji;
	private TextView tv_myrz_pingshen;
	private TextView tv_myrz_zhengshu;
	private ViewPager vp_myrz;
	private List<Fragment> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//new Xiaoxibeijing().changetop(this, R.color.main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_myrenzheng);
		init();
		showinfo();
		addlistener();
	}

	private void init() {
		ll_myrz_back = (LinearLayout) findViewById(R.id.ll_myrz_back);
		tv_myrz_baoming = (TextView) findViewById(R.id.tv_myrz_baoming);
		tv_myrz_chengji = (TextView) findViewById(R.id.tv_myrz_chengji);
		tv_myrz_pingshen = (TextView) findViewById(R.id.tv_myrz_pingshen);
		tv_myrz_zhengshu = (TextView) findViewById(R.id.tv_myrz_zhengshu);
		vp_myrz = (ViewPager) findViewById(R.id.vp_myrz);
	}

	private void showinfo() {
		list = new ArrayList<Fragment>();

		list.add(new Fragment_myrenzheng(0));
		list.add(new Fragment_myrenzheng(1));
		list.add(new Fragment_myrenzheng(2));
		list.add(new Fragment_myrenzheng(3));

		Vpadapter adapter = new Vpadapter(getSupportFragmentManager(), list);
		vp_myrz.setAdapter(adapter);
	}

	private void addlistener() {
		ll_myrz_back.setOnClickListener(this);
		tv_myrz_baoming.setOnClickListener(this);
		tv_myrz_chengji.setOnClickListener(this);
		tv_myrz_pingshen.setOnClickListener(this);
		tv_myrz_zhengshu.setOnClickListener(this);
		
vp_myrz.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				if(arg0==2){
					clearall(vp_myrz.getCurrentItem());
					
				}
			}
		});
	}

	
	private void clearall(int index){
		TextView[] texttop={tv_myrz_baoming,tv_myrz_chengji,tv_myrz_pingshen,tv_myrz_zhengshu};
		vp_myrz.setCurrentItem(index);
		for(int i=0;i<texttop.length;i++){
			if(i==index){
				texttop[i].setTextColor(getResources().getColor(R.color.main));
			}else{
				texttop[i].setTextColor(getResources().getColor(R.color.zywz));
			}
		}
	}
	
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.ll_myrz_back:
			finish();
			break;
		case R.id.tv_myrz_baoming:
			clearall(0);
			break;
		case R.id.tv_myrz_chengji:
			clearall(1);
			break;
		case R.id.tv_myrz_pingshen:
			clearall(2);
			break;
		case R.id.tv_myrz_zhengshu:
			clearall(3);
			break;
		default:
			break;
		}

	}
}
