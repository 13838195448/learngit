package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.activity.adapter.DaohangAdapter;
import com.mpyf.lening.activity.adapter.NewsAdapter;
import com.mpyf.lening.activity.adapter.Vpadapter;
import com.mpyf.lening.activity.fragment.Fragment_Lunbo;
import com.mpyf.lening.activity.fragment.Fragment_rzlb;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class NLRZActivity extends FragmentActivity {

	private GridView gv_nlrz_daohang;
	private LinearLayout ll_nlrz_back,ll_nlrz_xuexi,ll_nlrz_test;
	private ViewPager vp_nlrz_lunbo;
	private RelativeLayout rl_nlrz_baoming;
	private ListView lv_nlrz_neirong;
	private ImageView iv_nlrz_lunbo1, iv_nlrz_lunbo2, iv_nlrz_lunbo3;

	private Fragment_Lunbo fragmentOne;
	private static Handler handler;
	private Runnable viewpagerRunnable;
	private List<Map<String, Object>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	//	new Xiaoxibeijing().changetop(NLRZActivity.this, R.color.main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_nlrz);
		init();
		setlunbo();
		showdata();
		addlistener();

	}

	private void init() {
		gv_nlrz_daohang = (GridView) findViewById(R.id.gv_nlrz_daohang);
		rl_nlrz_baoming = (RelativeLayout) findViewById(R.id.rl_nlrz_baoming);
		ll_nlrz_back = (LinearLayout) findViewById(R.id.ll_nlrz_back);
		ll_nlrz_xuexi=(LinearLayout) findViewById(R.id.ll_nlrz_xuexi);
		ll_nlrz_test=(LinearLayout) findViewById(R.id.ll_nlrz_test);
		vp_nlrz_lunbo = (ViewPager) findViewById(R.id.vp_nlrz_lunbo);
		lv_nlrz_neirong = (ListView) findViewById(R.id.lv_nlrz_neirong);
		iv_nlrz_lunbo1 = (ImageView) findViewById(R.id.iv_nlrz_lunbo1);
		iv_nlrz_lunbo2 = (ImageView) findViewById(R.id.iv_nlrz_lunbo2);
		iv_nlrz_lunbo3 = (ImageView) findViewById(R.id.iv_nlrz_lunbo3);
		// setlunbo();
	}

	private void showdata() {

		String[] name = { "报名查询", "成绩查询", "评审查询", "证书查询" };
		int[] image = { R.drawable.authe_btn_apply, R.drawable.authe_btn_grade,
				R.drawable.authe_btn_review, R.drawable.authe_btn_ccie, };
		List<Map<String, Object>> listdaohang = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < name.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", name[i]);
			map.put("image", image[i]);
			listdaohang.add(map);
		}
		DaohangAdapter adapter = new DaohangAdapter(NLRZActivity.this,
				listdaohang);
		gv_nlrz_daohang.setAdapter(adapter);

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 1) {
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						list = new ArrayList<Map<String, Object>>();

						for (int i = 0; i < ja.length(); i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							JSONObject jo = ja.getJSONObject(i);
							map.put("id", jo.getString("PK_Ann"));
							map.put("title", jo.getString("INM_TITLE_NAME"));
							map.put("time", jo.getString("INM_Time"));
							map.put("context", jo.getString("INM_CONTENT"));
							list.add(map);
						}
						NewsAdapter adapterlv = new NewsAdapter(
								NLRZActivity.this, list);
						lv_nlrz_neirong.setAdapter(adapterlv);

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Diaoxian.showerror(NLRZActivity.this, e.getMessage());
					}
				}else{
					Diaoxian.showerror(NLRZActivity.this, msg.obj.toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				String result = HttpUse.messageget("AbilityCertification",
						"announcementCertificationList", map);
				Message msg = new Message();
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = jo.getString("data");
					} else {
						msg.obj = jo.getString("message");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					msg.obj = e.getMessage();
				}
				handler.sendMessage(msg);
			}
		}.start();

	}

	private void addlistener() {
		ListenerServer.setfinish(this, ll_nlrz_back);

		ll_nlrz_xuexi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(NLRZActivity.this, MycourseActivity.class);
				intent.putExtra("item", 1);
				startActivity(intent);
			}
		});
		
		ll_nlrz_test.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(NLRZActivity.this, MyExamActivity.class));
			}
		});
		
		rl_nlrz_baoming.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				startActivity(new Intent(NLRZActivity.this,
//						BaomingActivity.class));
				startActivity(new Intent(NLRZActivity.this,
						KebaomingActivity.class));
			}
		});

		gv_nlrz_daohang.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(NLRZActivity.this,
						NLRZScanActivity.class);
				intent.putExtra("Scan", arg2);
				startActivity(intent);
			}
		});

		lv_nlrz_neirong.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(NLRZActivity.this,
						NewsinfoActivity.class);
				intent.putExtra("id", list.get(arg2).get("id").toString());
				startActivity(intent);
			}
		});
	}

	/**
	 * 设置轮播广告
	 */
	private void setlunbo() {

		List<Fragment> listf = new ArrayList<Fragment>();

		listf.add(new Fragment_rzlb(Setting.apiUrl+"new-pages/images/app/cer/TURZ1.png"));
		listf.add(new Fragment_rzlb(Setting.apiUrl+"new-pages/images/app/cer/TURZ2.png"));
		listf.add(new Fragment_rzlb(Setting.apiUrl+"new-pages/images/app/cer/TURZ3.png"));
		
		Vpadapter adapter = new Vpadapter(getSupportFragmentManager(), listf);
		vp_nlrz_lunbo.setAdapter(adapter);

		handler = new Handler();
		viewpagerRunnable = new Runnable() {

			@Override
			public void run() {
				int nowIndex = vp_nlrz_lunbo.getCurrentItem();
				int count = vp_nlrz_lunbo.getAdapter().getCount();
				// 如果下一张的索引大于最后一张，则切换到第一张
				if (nowIndex + 1 >= count) {
					vp_nlrz_lunbo.setCurrentItem(0);
				} else {
					vp_nlrz_lunbo.setCurrentItem(nowIndex + 1);
				}
				handler.postDelayed(viewpagerRunnable, 2500);
			}
		};
		handler.postDelayed(viewpagerRunnable, 2500);

		vp_nlrz_lunbo.setOnPageChangeListener(new OnPageChangeListener() {

			boolean isScrolled = false;

			@Override
			public void onPageSelected(int arg0) {

			}

			@Override
			public void onPageScrolled(int position, float offset, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				if (2 == arg0) {
					int i = vp_nlrz_lunbo.getCurrentItem();
					cleanbanner();
					bannerChange(i);
				}

				switch (arg0) {
				case 1:// 手势滑动
					isScrolled = false;
					break;
				case 2:// 界面切换
					isScrolled = true;
					break;
				case 0:// 滑动结束

					// 当前为最后一张，此时从右向左滑，则切换到第一张
					if (vp_nlrz_lunbo.getCurrentItem() == vp_nlrz_lunbo
							.getAdapter().getCount() - 1 && !isScrolled) {
						vp_nlrz_lunbo.setCurrentItem(0);
					}
					// 当前为第一张，此时从左向右滑，则切换到最后一张
					else if (vp_nlrz_lunbo.getCurrentItem() == 0 && !isScrolled) {
						vp_nlrz_lunbo.setCurrentItem(vp_nlrz_lunbo.getAdapter()
								.getCount() - 1);
					}
					break;
				}
			}
		});
	}

	// 将首页广告底部点全部还原为黑色
	private void cleanbanner() {
		iv_nlrz_lunbo1.setImageResource(R.drawable.home_icon_circle_d);
		iv_nlrz_lunbo2.setImageResource(R.drawable.home_icon_circle_d);
		iv_nlrz_lunbo3.setImageResource(R.drawable.home_icon_circle_d);
	}

	// 滑动页面是底部点变化
	private void bannerChange(int num) {
		switch (num) {
		case 0:
			iv_nlrz_lunbo1.setImageResource(R.drawable.home_icon_circle_s);
			break;
		case 1:
			iv_nlrz_lunbo2.setImageResource(R.drawable.home_icon_circle_s);
			break;
		case 2:
			iv_nlrz_lunbo3.setImageResource(R.drawable.home_icon_circle_s);
			break;
		}

	}
}
