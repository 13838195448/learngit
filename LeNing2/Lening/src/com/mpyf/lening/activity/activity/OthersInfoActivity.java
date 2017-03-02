package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.XCRoundImageView;
import com.mpyf.lening.activity.adapter.Vpadapter;
import com.mpyf.lening.activity.fragment.OtherHuidaFragment;
import com.mpyf.lening.activity.fragment.OtherTiwenFragment;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
 * 查看别人的信息
 * 
 * @author s
 * 
 */
public class OthersInfoActivity extends FragmentActivity {
	private DisplayImageOptions options;
	private XCRoundImageView cm_other_touxiang;
	private LinearLayout ll_other_back;
	private TextView tv_other_name, tv_other_honor_name, tv_other_honor_value,
			tv_other_sex, tv_other_good, tv_other_jianjie, tv_other_huida,
			tv_other_tiwen;
	private RelativeLayout rl_other_huida, rl_other_tiwen;
	private ImageView iv_other_huida, iv_other_tiwen;
	private ViewPager vp_other;
	private List<Fragment> fr = new ArrayList<Fragment>();
	private String pk_user;
	private FragmentManager manager;
	private FragmentTransaction transaction;
	private OtherHuidaFragment huidaFragment;
	private OtherTiwenFragment tiwenFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_otherinfo);

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.icon_defualt)
				.showImageOnFail(R.drawable.icon_defualt)
				.showImageForEmptyUri(R.drawable.icon_defualt)
				.cacheInMemory(true).cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.ALPHA_8).build();

		init();
		getData();
		addlistener();
	}

	private void init() {
		// 返回键
		ll_other_back = (LinearLayout) findViewById(R.id.ll_other_back);
		// 姓名
		tv_other_name = (TextView) findViewById(R.id.tv_other_name);
		// 头像
		cm_other_touxiang = (XCRoundImageView) findViewById(R.id.cm_other_touxiang);
		// 称号
		tv_other_honor_name = (TextView) findViewById(R.id.tv_other_honor_name);
		// 经验值
		tv_other_honor_value = (TextView) findViewById(R.id.tv_other_honor_value);
		// 性别
		tv_other_sex = (TextView) findViewById(R.id.tv_other_sex);
		// 擅长
		tv_other_good = (TextView) findViewById(R.id.tv_other_good);
		// 简介
		tv_other_jianjie = (TextView) findViewById(R.id.tv_other_jianjie);
		// 回答
		rl_other_huida = (RelativeLayout) findViewById(R.id.rl_other_huida);
		tv_other_huida = (TextView) findViewById(R.id.tv_other_huida);
		iv_other_huida = (ImageView) findViewById(R.id.iv_other_huida);
		// 提问
		rl_other_tiwen = (RelativeLayout) findViewById(R.id.rl_other_tiwen);
		tv_other_tiwen = (TextView) findViewById(R.id.tv_other_tiwen);
		iv_other_tiwen = (ImageView) findViewById(R.id.iv_other_tiwen);
		// viewpager
		vp_other = (ViewPager) findViewById(R.id.vp_other);
		
		huidaFragment = new OtherHuidaFragment();
		tiwenFragment = new OtherTiwenFragment();
		fr.add(huidaFragment);
		fr.add(tiwenFragment);
		
		Vpadapter adapter = new Vpadapter(getSupportFragmentManager(), fr);
		vp_other.setAdapter(adapter);
	}

	private void getData() {

		final Handler handler = new Handler() {

			private Bundle bundle;

			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						JSONObject ja = new JSONObject(msg.obj.toString());
						tv_other_name.setText(ja.getString("truename"));
						tv_other_honor_name.setText(ja.getString("honor_name"));
						tv_other_honor_value.setText(ja
								.getString("honor_value"));
						pk_user = ja.getString("pk_user");
						
						huidaFragment.setData(pk_user);
						tiwenFragment.setData(pk_user);
						
						if (ja.getInt("sex") == 2) {
							tv_other_sex.setText("男");
						} else if (ja.getInt("sex") == 1) {
							tv_other_sex.setText("女");
						} else {
							tv_other_sex.setText("");
						}
						tv_other_good.setText(ja.getString("field"));
						tv_other_jianjie.setText("简介: "+ja.getString("introduce"));
						// 设置头像
						ImageLoader imageLoader = ImageLoader.getInstance();
						imageLoader.displayImage(
								Setting.apiUrl + "new-pages/PersonalPhoto/"
										+ ja.getString("pk_user") + ".jpg",
								cm_other_touxiang, options);
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
				map.put("userId", getIntent().getStringExtra("userId"));
				Message msg = new Message();

				String result = HttpUse.messageget("QueAndAns", "getUser", map);
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
		ListenerServer.setfinish(OthersInfoActivity.this, ll_other_back);

		// 月度排行
		rl_other_huida.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clearall(0);
				vp_other.setCurrentItem(0);
			}
		});
		// 季度排行
		rl_other_tiwen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clearall(1);
				vp_other.setCurrentItem(1);
			}
		});

		vp_other.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				if (arg0 == 2) {
					if (vp_other.getCurrentItem() == 0) {
						tv_other_huida.setTextColor(getResources().getColor(
								R.color.main));
						tv_other_tiwen.setTextColor(getResources().getColor(
								R.color.zywz));
						iv_other_huida.setBackgroundColor(getResources()
								.getColor(R.color.main));
						iv_other_tiwen.setBackgroundColor(getResources()
								.getColor(R.color.dise));
					} else if (vp_other.getCurrentItem() == 1) {
						tv_other_huida.setTextColor(getResources().getColor(
								R.color.zywz));
						tv_other_tiwen.setTextColor(getResources().getColor(
								R.color.main));
						iv_other_huida.setBackgroundColor(getResources()
								.getColor(R.color.dise));
						iv_other_tiwen.setBackgroundColor(getResources()
								.getColor(R.color.main));
					}
				}
			}
		});
	}

	private void clearall(int index) {
		TextView[] texttop = { tv_other_huida, tv_other_tiwen };
		ImageView[] imagetop = { iv_other_huida, iv_other_tiwen };

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
