package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.List;

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

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.activity.adapter.Vpadapter;
import com.mpyf.lening.activity.fragment.Fragment_Mytest;
import com.mpyf.lening.activity.fragment.Fragment_myzice;
import com.mpyf.lening.activity.fragment.Fragment_test;
import com.mpyf.lening.activity.fragment.Fragment_zice;

public class MyExamActivity extends FragmentActivity {

	private LinearLayout ll_mycourse_back;
	private RelativeLayout rl_mycourse_zixiu,rl_mycourse_banji,rl_mycourse_gangwei;
	private TextView tv_mycourse_title,tv_mycourse_zixiu,tv_mycourse_banji;
	
	private ImageView iv_mycourse_zixiu,iv_mycourse_banji;
	
	private ViewPager vp_mycourse_top;
	private List<Fragment> list;
	
	private Fragment_zice fragment_zice;
	private Fragment_test fragment_test;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//new Xiaoxibeijing().changetop(this, R.color.main);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mycourse);
		init();
		showdata();
		addlistener();
	}
	private void init(){
		ll_mycourse_back=(LinearLayout) findViewById(R.id.ll_mycourse_back);
		rl_mycourse_zixiu=(RelativeLayout) findViewById(R.id.rl_mycourse_zixiu);
		rl_mycourse_banji=(RelativeLayout) findViewById(R.id.rl_mycourse_banji);
		rl_mycourse_gangwei=(RelativeLayout) findViewById(R.id.rl_mycourse_gangwei);
		tv_mycourse_title=(TextView) findViewById(R.id.tv_mycourse_title);
		tv_mycourse_zixiu=(TextView) findViewById(R.id.tv_mycourse_zixiu);
		tv_mycourse_banji=(TextView) findViewById(R.id.tv_mycourse_banji);
		iv_mycourse_zixiu=(ImageView) findViewById(R.id.iv_mycourse_zixiu);
		iv_mycourse_banji=(ImageView) findViewById(R.id.iv_mycourse_banji);
		vp_mycourse_top=(ViewPager) findViewById(R.id.vp_mycourse_top);
		
		rl_mycourse_gangwei.setVisibility(View.GONE);
	}
	
	private void showdata(){
		
		tv_mycourse_title.setText("�ҵĲ���");
		tv_mycourse_zixiu.setText("�Բ�");
		tv_mycourse_banji.setText("����");
		
		list=new ArrayList<Fragment>();
		
		
		Fragment_myzice fragment_myzice = new Fragment_myzice();
		Fragment_Mytest fragment_Mytest = new Fragment_Mytest();
		list.add(fragment_myzice);
		list.add(fragment_Mytest);
		
		Vpadapter adapter=new Vpadapter(getSupportFragmentManager(), list);
		vp_mycourse_top.setAdapter(adapter);
		
	}
	
	private void clearall(int index){
		TextView[] texttop={tv_mycourse_zixiu,tv_mycourse_banji};
		ImageView[] imagetop={iv_mycourse_zixiu,iv_mycourse_banji};
		
		for(int i=0;i<texttop.length;i++){
			if(i==index){
				texttop[i].setTextColor(getResources().getColor(R.color.my));
				imagetop[i].setBackgroundColor(getResources().getColor(R.color.my));
			}else{
				texttop[i].setTextColor(getResources().getColor(R.color.zywz));
				imagetop[i].setBackgroundColor(getResources().getColor(R.color.dise));
			}
		}
	}
	
	private void addlistener(){
		ListenerServer.setfinish(MyExamActivity.this, ll_mycourse_back);
		
		rl_mycourse_zixiu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				clearall(0);
				vp_mycourse_top.setCurrentItem(0);
			}
		});
		rl_mycourse_banji.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				clearall(1);
				vp_mycourse_top.setCurrentItem(1);
			}
		});
		
		
		vp_mycourse_top.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				if(arg0==2){
					clearall(vp_mycourse_top.getCurrentItem());
				}
			}
		});
	}
		
}
