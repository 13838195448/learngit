package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.List;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.activity.adapter.Vpadapter;
import com.mpyf.lening.activity.fragment.Fragment_mycourse;
import com.mpyf.lening.activity.fragment.Fragment_shequ;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SheqActivity extends FragmentActivity {

	private LinearLayout ll_mycourse_back;
	private TextView tv_mycourse_title,tv_mycourse_zixiu,tv_mycourse_banji,tv_mycourse_gangwei;
	
	private RelativeLayout rl_mycourse_zixiu,
	rl_mycourse_banji,
	rl_mycourse_gangwei;
	
	private ImageView iv_mycourse_zixiu,
	iv_mycourse_banji,
	iv_mycourse_gangwei;
	
	private ViewPager vp_mycourse_top;
	private List<Fragment> list;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		new Xiaoxibeijing().changetop(this, R.color.main);
		setContentView(R.layout.activity_mycourse);
		init();
		showinfo();
	}
	
	private void init(){
		ll_mycourse_back=(LinearLayout) findViewById(R.id.ll_mycourse_back);
		tv_mycourse_title=(TextView) findViewById(R.id.tv_mycourse_title);
		
		rl_mycourse_zixiu=(RelativeLayout) findViewById(R.id.rl_mycourse_zixiu);
		rl_mycourse_banji=(RelativeLayout) findViewById(R.id.rl_mycourse_banji);
		rl_mycourse_gangwei=(RelativeLayout) findViewById(R.id.rl_mycourse_gangwei);
		
		tv_mycourse_zixiu=(TextView) findViewById(R.id.tv_mycourse_zixiu);
		tv_mycourse_banji=(TextView) findViewById(R.id.tv_mycourse_banji);
		tv_mycourse_gangwei=(TextView) findViewById(R.id.tv_mycourse_gangwei);
		
		iv_mycourse_zixiu=(ImageView) findViewById(R.id.iv_mycourse_zixiu);
		iv_mycourse_banji=(ImageView) findViewById(R.id.iv_mycourse_banji);
		iv_mycourse_gangwei=(ImageView) findViewById(R.id.iv_mycourse_gangwei);
		
		vp_mycourse_top=(ViewPager) findViewById(R.id.vp_mycourse_top);
	}
	
	private void showinfo(){
		tv_mycourse_title.setText("社区");
		tv_mycourse_zixiu.setText("商城");
		tv_mycourse_banji.setText("资源");
		tv_mycourse_gangwei.setText("活动");
			list=new ArrayList<Fragment>();
			
			list.add(new Fragment_shequ(1));
			list.add(new Fragment_shequ(2));
			list.add(new Fragment_shequ(3));
			
			Vpadapter adapter=new Vpadapter(getSupportFragmentManager(), list);
			vp_mycourse_top.setAdapter(adapter);
			
	}
}
