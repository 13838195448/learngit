package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.AsyncBitmapLoader;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.Roundbitmap;
import com.mpyf.lening.activity.adapter.Vpadapter;
import com.mpyf.lening.activity.fragment.JiDuFragment;
import com.mpyf.lening.activity.fragment.MonthPhFragment;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class PaihangActivity extends FragmentActivity {
	private LinearLayout ll_zd_paihang_back;
	private RelativeLayout rl_zd_paihang_month, rl_zd_paihang_top;
	private ImageView iv_zd_mymonth, iv_zd_mytop, iv_touxiang;
	private ViewPager vp_zd_paihang;
	private TextView tv_mymonth, tv_mytop, tv_zd_mymonth, tv_zd_mytop,
			tv_username;
	private List<Fragment> fr = new ArrayList<Fragment>();
	private DisplayImageOptions options;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_paihang);

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.icon_defualt)
				.showImageOnFail(R.drawable.icon_defualt)
				.showImageForEmptyUri(R.drawable.icon_defualt)
				.cacheInMemory(true).cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.ALPHA_8).build();
		init();
		showinfo();
		addlistener();
	}

	private void init() {
		// 返回键
		ll_zd_paihang_back = (LinearLayout) findViewById(R.id.ll_zd_paihang_back);
		vp_zd_paihang = (ViewPager) findViewById(R.id.vp_zd_paihang);

		rl_zd_paihang_month = (RelativeLayout) findViewById(R.id.rl_zd_paihang_month);
		rl_zd_paihang_top = (RelativeLayout) findViewById(R.id.rl_zd_paihang_top);

		iv_zd_mymonth = (ImageView) findViewById(R.id.iv_zd_mymonth);
		iv_zd_mytop = (ImageView) findViewById(R.id.iv_zd_mytop);
		iv_touxiang = (ImageView) findViewById(R.id.iv_touxiang);

		tv_zd_mymonth = (TextView) findViewById(R.id.tv_zd_mymonth);
		tv_zd_mytop = (TextView) findViewById(R.id.tv_zd_mytop);
		// 第几名
		tv_mymonth = (TextView) findViewById(R.id.tv_mymonth);
		tv_mytop = (TextView) findViewById(R.id.tv_mytop);
		tv_username = (TextView) findViewById(R.id.tv_username);

		fr.add(new MonthPhFragment());
		fr.add(new JiDuFragment());
		Vpadapter adapter = new Vpadapter(getSupportFragmentManager(), fr);
		vp_zd_paihang.setAdapter(adapter);
		clearall(1);
		vp_zd_paihang.setCurrentItem(1);

		// 设置头像
		iv_touxiang.setImageResource(R.drawable.icon_defualt);
		iv_touxiang.setImageBitmap(Roundbitmap.toRoundBitmap(iv_touxiang));
		tv_username.setText(Setting.currentUser.getTruename());
		AsyncBitmapLoader.setRoundImage(iv_touxiang, Setting.apiUrl
				+ "new-pages/PersonalPhoto/" + Setting.currentUser.getPk_user()
				+ ".jpg");
	}

	private void showinfo() {

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						JSONObject jo = new JSONObject(msg.obj.toString());
						if (jo.getInt("monthRanking") == 0) {
							tv_mymonth.setText(jo.getString("unRanking"));

						} else {
							tv_mymonth.setText(jo.getInt("monthRanking") + "");

						}

						if (jo.getInt("cumulative") == 0) {
							tv_mytop.setText(jo.getString("unRanking"));
						} else {
							tv_mytop.setText(jo.getInt("cumulative") + "");
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};

		new Thread() {
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				Message msg = new Message();
				String result = HttpUse.messageget("QueAndAns",
						"myAdoptRanking", map);
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = jo.getString("data");
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

	private void addlistener() {
		// 返回键
		ListenerServer.setfinish(PaihangActivity.this, ll_zd_paihang_back);

		// 月度排行
		rl_zd_paihang_month.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clearall(0);
				vp_zd_paihang.setCurrentItem(0);
			}
		});
		// 季度排行
		rl_zd_paihang_top.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clearall(1);
				vp_zd_paihang.setCurrentItem(1);
			}
		});

		vp_zd_paihang.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				if (arg0 == 2) {
					if (vp_zd_paihang.getCurrentItem() == 0) {
						tv_zd_mymonth.setTextColor(getResources().getColor(
								R.color.main));
						tv_zd_mytop.setTextColor(getResources().getColor(
								R.color.zywz));
						iv_zd_mymonth.setBackgroundColor(getResources()
								.getColor(R.color.main));
						iv_zd_mytop.setBackgroundColor(getResources().getColor(
								R.color.dise));
					} else if (vp_zd_paihang.getCurrentItem() == 1) {
						tv_zd_mymonth.setTextColor(getResources().getColor(
								R.color.zywz));
						tv_zd_mytop.setTextColor(getResources().getColor(
								R.color.main));
						iv_zd_mymonth.setBackgroundColor(getResources()
								.getColor(R.color.dise));
						iv_zd_mytop.setBackgroundColor(getResources().getColor(
								R.color.main));
					}
				}
			}
		});
	}

	private void clearall(int index) {
		TextView[] texttop = { tv_zd_mymonth, tv_zd_mytop };
		ImageView[] imagetop = { iv_zd_mymonth, iv_zd_mytop };

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
