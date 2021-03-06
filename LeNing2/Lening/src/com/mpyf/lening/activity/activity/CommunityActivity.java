package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.List;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.activity.adapter.Vpadapter;
import com.mpyf.lening.activity.fragment.Fragment_comm_resource;
import com.mpyf.lening.activity.fragment.Fragment_community;
import com.mpyf.lening.activity.fragment.Fragment_paihangb;

import android.app.Activity;
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

/**
 * @author Administrator
 * 没用的
 *	社区
 */
public class CommunityActivity extends FragmentActivity {

	private LinearLayout ll_mycourse_back;
	private RelativeLayout rl_mycourse_zixiu,rl_mycourse_banji,rl_mycourse_gangwei;
	private TextView tv_mycourse_title,tv_mycourse_zixiu,tv_mycourse_banji,tv_mycourse_gangwei;
	
	private ImageView iv_mycourse_zixiu,iv_mycourse_banji,iv_mycourse_gangwei;
	
	private ViewPager vp_mycourse_top;
	private List<Fragment> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//new Xiaoxibeijing().changetop(this,R.color.main);
		setContentView(R.layout.activity_mycourse);
		init();
		showinfo();
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
		
		
		list.add(new Fragment_community());
		list.add(new Fragment_comm_resource());
		list.add(new Fragment_community());
		
		Vpadapter adapter=new Vpadapter(getSupportFragmentManager(), list);
		vp_mycourse_top.setAdapter(adapter);
		vp_mycourse_top.setCurrentItem(1);
		clearall(1); 
	}
	
	private void addlistener(){

		ListenerServer.setfinish(CommunityActivity.this, ll_mycourse_back);
		
		rl_mycourse_zixiu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				clearall(0);
				vp_mycourse_top.setCurrentItem(0);
			}
		});
		rl_mycourse_banji.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				clearall(1);
				vp_mycourse_top.setCurrentItem(1);
			}
		});
		
		rl_mycourse_gangwei.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				clearall(2);
				vp_mycourse_top.setCurrentItem(2);
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
	
	private void clearall(int index){
		TextView[] texttop={tv_mycourse_zixiu,tv_mycourse_banji,tv_mycourse_gangwei};
		ImageView[] imagetop={iv_mycourse_zixiu,iv_mycourse_banji,iv_mycourse_gangwei};
		
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
	
	
}
