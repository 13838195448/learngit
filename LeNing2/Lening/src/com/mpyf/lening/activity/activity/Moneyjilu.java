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
import com.mpyf.lening.activity.fragment.Fragment_money;

public class Moneyjilu extends FragmentActivity {

	private LinearLayout ll_moneyjilu_back;
	private RelativeLayout rl_money_get,rl_money_use;
	private TextView tv_moneyjilu_title,tv_moneyjilu_search,tv_money_get,tv_money_use;
	private ImageView iv_money_get,iv_money_use;
	private ViewPager vp_money;
	private List<Fragment> listfg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//new Xiaoxibeijing().changetop(this, R.color.main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_moneyjilu);
		init();
		showinfo();
		addlistener();
	}
	private void init(){
		ll_moneyjilu_back=(LinearLayout) findViewById(R.id.ll_moneyjilu_back);
		tv_moneyjilu_title=(TextView) findViewById(R.id.tv_moneyjilu_title);
		tv_moneyjilu_search=(TextView) findViewById(R.id.tv_moneyjilu_search);
		vp_money=(ViewPager) findViewById(R.id.vp_money);
		rl_money_get=(RelativeLayout) findViewById(R.id.rl_money_get);
		rl_money_use=(RelativeLayout) findViewById(R.id.rl_money_use);
		tv_money_get=(TextView) findViewById(R.id.tv_money_get);
		tv_money_use=(TextView) findViewById(R.id.tv_money_use);
		iv_money_get=(ImageView) findViewById(R.id.iv_money_get);
		iv_money_use=(ImageView) findViewById(R.id.iv_money_use);
		tv_moneyjilu_search.setVisibility(View.GONE);
	}
	private void showinfo(){
		listfg=new ArrayList<Fragment>();
		
		if (getIntent().getStringExtra("money").equals("le")) {
			tv_moneyjilu_title.setText("乐币详情");
			listfg.add(new Fragment_money("le", 0));
			listfg.add(new Fragment_money("le", 1));
		}else if (getIntent().getStringExtra("money").equals("gold")) {
			tv_moneyjilu_title.setText("金币详情");
			listfg.add(new Fragment_money("gold", 2));
			listfg.add(new Fragment_money("gold", 3));
		}else if(getIntent().getStringExtra("money").equals("honor")) {
			tv_moneyjilu_title.setText("经验详情");
			listfg.add(new Fragment_money("honor", 4));
			listfg.add(new Fragment_money("honor", 5));
		}
		
		Vpadapter adapter=new Vpadapter(getSupportFragmentManager(), listfg);
		vp_money.setAdapter(adapter);
		
	}
	private void addlistener(){
		ListenerServer.setfinish(Moneyjilu.this, ll_moneyjilu_back);
		
		rl_money_get.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				vp_money.setCurrentItem(0);
			}
		});
		
		rl_money_use.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				vp_money.setCurrentItem(1);
			}
		});
		
		vp_money.setOnPageChangeListener(new OnPageChangeListener() {
			
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
					if(vp_money.getCurrentItem()==0){
						tv_money_get.setTextColor(getResources().getColor(R.color.main));
						tv_money_use.setTextColor(getResources().getColor(R.color.zywz));
						iv_money_get.setBackgroundColor(getResources().getColor(R.color.main));
						iv_money_use.setBackgroundColor(getResources().getColor(R.color.dise));
					}else if(vp_money.getCurrentItem()==1){
						tv_money_get.setTextColor(getResources().getColor(R.color.zywz));
						tv_money_use.setTextColor(getResources().getColor(R.color.main));
						iv_money_get.setBackgroundColor(getResources().getColor(R.color.dise));
						iv_money_use.setBackgroundColor(getResources().getColor(R.color.main));
					}
				}
			}
		});
		
	}
	
	
}
