package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Writesaved;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.activity.adapter.Vpadapter;
import com.mpyf.lening.activity.fragment.Fragment_gate;

public class GateAcitvity extends FragmentActivity {

	private ViewPager vp_gate;
	private ImageView iv_gate_1,iv_gate_2,iv_gate_3;
	private TextView tv_gate_goin;
	
	private List<Fragment> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		new Xiaoxibeijing().changetop(this, R.color.touming);
		setContentView(R.layout.activity_gate);
		init();
		setvp();
		addlistener();
	}
	private void init(){
		vp_gate=(ViewPager) findViewById(R.id.vp_gate);
		iv_gate_1=(ImageView) findViewById(R.id.iv_gate_1);
		iv_gate_2=(ImageView) findViewById(R.id.iv_gate_2);
		iv_gate_3=(ImageView) findViewById(R.id.iv_gate_3);
		tv_gate_goin=(TextView) findViewById(R.id.tv_gate_goin);
		
	}
	
	private void setvp(){
		list=new ArrayList<Fragment>();
		list.add(new Fragment_gate(R.drawable.welcome1));
		list.add(new Fragment_gate(R.drawable.welcome2));
		list.add(new Fragment_gate(R.drawable.welcome3));
		
		Vpadapter adapter=new Vpadapter(getSupportFragmentManager(), list);
		vp_gate.setAdapter(adapter);
	}
	
	private void docchange(int index){
		ImageView[] ds={iv_gate_1,iv_gate_2,iv_gate_3};
		for(int i=0;i<ds.length;i++){
			if(i==index){
				ds[i].setImageResource(R.drawable.home_icon_circle_s);
			}else{
				ds[i].setImageResource(R.drawable.home_icon_circle_d);
			}
		}
		if(index==2){
			tv_gate_goin.setText("进入");
		}else{
			tv_gate_goin.setText("跳过，直接进入");
		}
	}
	
	
	private void addlistener(){
		tv_gate_goin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(GateAcitvity.this, LoginActivity.class));
				Writesaved.write(GateAcitvity.this,"logined" ,"1");
				finish();
			}
		});
		
		vp_gate.setOnPageChangeListener(new OnPageChangeListener() {
			
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
					docchange(vp_gate.getCurrentItem());
				}
				
			}
		});
	}
}
