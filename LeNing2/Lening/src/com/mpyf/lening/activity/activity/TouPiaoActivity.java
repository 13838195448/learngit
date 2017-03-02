package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.activity.adapter.Vpadapter;
import com.mpyf.lening.activity.fragment.HotTouPiaoFragment;
import com.mpyf.lening.activity.fragment.JiDuFragment;
import com.mpyf.lening.activity.fragment.MonthPhFragment;
import com.mpyf.lening.activity.fragment.NewTouPiaoFragment;
import com.mpyf.lening.interfaces.http.HttpUse;

public class TouPiaoActivity extends FragmentActivity {
	private LinearLayout ll_zd_toupiao_back, ll_starttou;
	private ViewPager vp_toupian;
	private List<Fragment> fr = new ArrayList<Fragment>();
	private RelativeLayout rl_toupiao_new, rl_toupiao_hot;
	private ImageView iv_toupiao_new, iv_toupiao_hot;
	private TextView tv_toupiao_new, tv_toupiao_hot, tv_mytoupiao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_toupiao);
		init();
		getData();
		addlistener();

	}

	private void init() {
		ll_zd_toupiao_back = (LinearLayout) findViewById(R.id.ll_zd_toupiao_back);
		vp_toupian = (ViewPager) findViewById(R.id.vp_toupian);

		rl_toupiao_new = (RelativeLayout) findViewById(R.id.rl_toupiao_new);
		rl_toupiao_hot = (RelativeLayout) findViewById(R.id.rl_toupiao_hot);

		iv_toupiao_new = (ImageView) findViewById(R.id.iv_toupiao_new);
		iv_toupiao_hot = (ImageView) findViewById(R.id.iv_toupiao_hot);

		tv_toupiao_new = (TextView) findViewById(R.id.tv_toupiao_new);
		tv_toupiao_hot = (TextView) findViewById(R.id.tv_toupiao_hot);
		tv_mytoupiao = (TextView) findViewById(R.id.tv_mytoupiao);

		// 发起投票
		ll_starttou = (LinearLayout) findViewById(R.id.ll_starttou);

		fr.add(new NewTouPiaoFragment());
		fr.add(new HotTouPiaoFragment());
		Vpadapter adapter = new Vpadapter(getSupportFragmentManager(), fr);
		vp_toupian.setAdapter(adapter);

	}

	private void getData() {

	}

	private void addlistener() {
		// 返回键
		ListenerServer.setfinish(TouPiaoActivity.this, ll_zd_toupiao_back);

		// 发起投票
		ll_starttou.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final Handler handler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						if (msg.what == 1) {
							// TODO 如果可以投票跳到投票界面
							if ((Boolean) msg.obj) {
								Intent intent = new Intent(
										TouPiaoActivity.this,
										StartTouActivity.class);
								startActivity(intent);
							} else {
								Toast.makeText(TouPiaoActivity.this,
										"今日投票次数已使用完毕,请明日再来!",
										Toast.LENGTH_SHORT).show();
							}
						} else {
							// Toast.makeText(TouPiaoActivity.this,
							// "今日投票次数已使用完毕,请明日再来!", Toast.LENGTH_SHORT)
							// .show();
							Toast.makeText(TouPiaoActivity.this,
									msg.obj.toString(), Toast.LENGTH_SHORT)
									.show();
						}
					}
				};

				new Thread() {
					public void run() {
						Map<String, Object> map = new HashMap<String, Object>();

						Message msg = new Message();
						String result = HttpUse.messageget("QueAndAns",
								"todayUserIsCanVote", map);
						// TODO 打印请求到的数据 没判断data
//						System.out.println("=====打印今日是否可发布投票====" + result);

						try {
							JSONObject jo = new JSONObject(result);
							if (jo.getBoolean("result")) {
								msg.what = 1;
								msg.obj = jo.getBoolean("data");
							} else {
								msg.obj = jo.getString("message");
							}

						} catch (JSONException e) {
							msg.obj = e.getMessage();
						}

						handler.sendMessage(msg);
					};
				}.start();
			}
		});
		// 我的投票
		tv_mytoupiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(TouPiaoActivity.this,
						MyTouPiaoActivity.class);
				startActivity(intent);
			}
		});

		// 最新
		rl_toupiao_new.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clearall(0);
				vp_toupian.setCurrentItem(0);
			}
		});
		// 高悬赏
		rl_toupiao_hot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clearall(1);
				vp_toupian.setCurrentItem(1);
			}
		});
		vp_toupian.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				if (vp_toupian.getCurrentItem() == 0) {
					tv_toupiao_new.setTextColor(getResources().getColor(
							R.color.main));
					tv_toupiao_hot.setTextColor(getResources().getColor(
							R.color.zywz));
					iv_toupiao_new.setBackgroundColor(getResources().getColor(
							R.color.main));
					iv_toupiao_hot.setBackgroundColor(getResources().getColor(
							R.color.dise));
				} else if (vp_toupian.getCurrentItem() == 1) {
					tv_toupiao_new.setTextColor(getResources().getColor(
							R.color.zywz));
					tv_toupiao_hot.setTextColor(getResources().getColor(
							R.color.main));
					iv_toupiao_new.setBackgroundColor(getResources().getColor(
							R.color.dise));
					iv_toupiao_hot.setBackgroundColor(getResources().getColor(
							R.color.main));
				}

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	private void clearall(int index) {
		TextView[] texttop = { tv_toupiao_new, tv_toupiao_hot };
		ImageView[] imagetop = { iv_toupiao_new, iv_toupiao_hot };

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
