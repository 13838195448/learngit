package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.activity.adapter.Vpadapter;
import com.mpyf.lening.activity.fragment.Fragment_qagoodpay;
import com.mpyf.lening.activity.fragment.Fragment_qahot;

public class QaActivity extends FragmentActivity {

	private LinearLayout ll_qa_back;
	private RelativeLayout rl_qa_all,rl_qa_hot,rl_qa_goodpay,rl_qa_search;
	private ViewPager vp_qa;
	private List<Fragment> listf;
	private TextView tv_qa_all,tv_qa_hot,tv_qa_goodpay,tv_qa_title;
	private ImageView iv_qa_all,iv_qa_hot,iv_qa_goodpay,iv_qa_seek,iv_qa_addqa;
	private ArrayList<Map<String, Object>> list2;
	private EditText et_qa_search;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//new Xiaoxibeijing().changetop(this, R.color.main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_qa);
		init();
		setvp();
		search();
		addlistener();
	}
	private void search() {}
	private void init(){
//		ll_qa_back=(LinearLayout) findViewById(R.id.ll_qa_back);
		rl_qa_all=(RelativeLayout) findViewById(R.id.rl_qa_all);
		rl_qa_hot=(RelativeLayout) findViewById(R.id.rl_qa_hot);
		rl_qa_goodpay=(RelativeLayout) findViewById(R.id.rl_qa_goodpay);
//		rl_qa_search=(RelativeLayout) findViewById(R.id.rl_qa_search);
		tv_qa_all=(TextView) findViewById(R.id.tv_qa_all);
		tv_qa_hot=(TextView) findViewById(R.id.tv_qa_hot);
//		tv_qa_title=(TextView) findViewById(R.id.tv_qa_title);
		tv_qa_goodpay=(TextView) findViewById(R.id.tv_qa_goodpay);
		iv_qa_all=(ImageView) findViewById(R.id.iv_qa_all);
		iv_qa_hot=(ImageView) findViewById(R.id.iv_qa_hot);
		iv_qa_goodpay=(ImageView) findViewById(R.id.iv_qa_goodpay);
//		iv_qa_seek=(ImageView) findViewById(R.id.iv_qa_seek);
		iv_qa_addqa=(ImageView) findViewById(R.id.iv_qa_addqa);
		vp_qa=(ViewPager) findViewById(R.id.vp_qa);
//		et_qa_search = (EditText)findViewById(R.id.et_qa_search);
		tv_qa_title.setText("ÎÊ´ð");
		
	}
	
	private void setvp(){
		listf=new ArrayList<Fragment>();
//		listf.add(new Fragment_qaall(vp_qa));
		listf.add(new Fragment_qahot());
		listf.add(new Fragment_qagoodpay());
		
		Vpadapter adapter=new Vpadapter(getSupportFragmentManager(), listf);
		vp_qa.setAdapter(adapter);
		vp_qa.setOffscreenPageLimit(1);
	}
	
	private void clearall(int index){
		TextView[] texttop={tv_qa_all,tv_qa_hot,tv_qa_goodpay};
		ImageView[] imagetop={iv_qa_all,iv_qa_hot,iv_qa_goodpay};
		
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
		
		ListenerServer.setfinish(this, ll_qa_back);
		
		
		rl_qa_all.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				clearall(0);
				vp_qa.setCurrentItem(0);
			}
		});
		rl_qa_hot.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				clearall(1);
				vp_qa.setCurrentItem(1);
			}
		});
		
		rl_qa_goodpay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				clearall(2);
				vp_qa.setCurrentItem(2);
			}
		});
		
		
		iv_qa_addqa.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(QaActivity.this, FatieActivity.class));
			}
		});
		
		vp_qa.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				if(arg0==2){
					clearall(vp_qa.getCurrentItem());
				}
			}
		});
			
		
	}
}
