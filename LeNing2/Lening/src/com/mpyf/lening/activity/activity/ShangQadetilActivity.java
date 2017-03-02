package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.activity.adapter.Vpadapter;
import com.mpyf.lening.activity.fragment.Fragment_comment;
import com.mpyf.lening.activity.fragment.Fragment_courseinfo;
import com.mpyf.lening.activity.fragment.Fragment_goodscomment;
import com.mpyf.lening.activity.fragment.Fragment_goodsinfo;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class ShangQadetilActivity extends FragmentActivity {

	private RelativeLayout rl_sc_gold_back;
	private ImageView iv_sc_gold_top, iv_sc_gold_le, iv_sc_gold_info,
			iv_sc_gold_comment;
	private TextView tv_sc_gold_title, tv_sc_gold_time, tv_sc_gold_le,
			tv_sc_gold_month, tv_sc_gold_score, tv_sc_gold_buynum,
			tv_sc_gold_resnum, tv_sc_gold_buy, tv_sc_gold_info,
			tv_sc_gold_comment;
	private ViewPager vp_sc_gold;
	private RelativeLayout rl_sc_gold_info, rl_sc_gold_comment;
	private DisplayImageOptions options;

	private List<Fragment> listvp;
	private Fragment_goodsinfo fragment_goodsinfo;
	private Fragment_goodscomment fragment_goodscomment;
	public static String id = "";
	public static String remark = "";

	private String name;
	private String time;
	private String pk_goods;
	public String imgurl = "";
	private int buyWay;
	private int amount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shang_gold);
		init();
		showinfo();
		addlistener();
	}

	private void init() {
		rl_sc_gold_back = (RelativeLayout) findViewById(R.id.rl_sc_gold_back);
		// 背景图片
		iv_sc_gold_top = (ImageView) findViewById(R.id.iv_sc_gold_top);
		// 商品名称
		tv_sc_gold_title = (TextView) findViewById(R.id.tv_sc_gold_title);
		// 时间
		tv_sc_gold_time = (TextView) findViewById(R.id.tv_sc_gold_time);
		// 乐币金币图标
		iv_sc_gold_le = (ImageView) findViewById(R.id.iv_sc_gold_le);
		// 乐币金币数量
		tv_sc_gold_le = (TextView) findViewById(R.id.tv_sc_gold_le);
		// 平均评价
		tv_sc_gold_score = (TextView) findViewById(R.id.tv_sc_gold_score);
		// 月销量
		tv_sc_gold_month = (TextView) findViewById(R.id.tv_sc_gold_teacher);
		// 购买人数
		tv_sc_gold_buynum = (TextView) findViewById(R.id.tv_sc_gold_buynum);
		// 库存
		tv_sc_gold_resnum = (TextView) findViewById(R.id.tv_sc_gold_resnum);
		// 购买
		tv_sc_gold_buy = (TextView) findViewById(R.id.tv_sc_gold_buy);
		// viewPager
		vp_sc_gold = (ViewPager) findViewById(R.id.vp_sc_gold);
		// 介绍的布局控件
		rl_sc_gold_info = (RelativeLayout) findViewById(R.id.rl_sc_gold_info);
		tv_sc_gold_info = (TextView) findViewById(R.id.tv_sc_gold_info);
		iv_sc_gold_info = (ImageView) findViewById(R.id.iv_sc_gold_info);
		// 评论的布局控件
		rl_sc_gold_comment = (RelativeLayout) findViewById(R.id.rl_sc_gold_comment);
		tv_sc_gold_comment = (TextView) findViewById(R.id.tv_sc_gold_comment);
		iv_sc_gold_comment = (ImageView) findViewById(R.id.iv_sc_gold_comment);

		id = getIntent().getStringExtra("id");
		iv_sc_gold_top.setAlpha(180);
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.defaultimage)
				.showImageOnFail(R.drawable.defaultimage)
				.showImageForEmptyUri(R.drawable.defaultimage)
				.cacheInMemory(true).cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.ALPHA_8).build();

	}

	private void showinfo() {
		final Dialog dialog = MyDialog.MyDialogloading(this);
		dialog.show();
		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				dialog.dismiss();

				if (msg.what == 1) {
					try {
						JSONObject jo = (JSONObject) msg.obj;

						name = jo.getString("goodsName");
						time = jo.getString("onlineTime");

						imgurl = Setting.apiUrl + jo.getString("picUrl");
						amount = jo.getInt("amount");
						buyWay = jo.getInt("buyWay");
						pk_goods = jo.getString("pk_goods");

						// AsyncBitmapLoader.sethoneImage(iv_wmcourse_top,imgurl
						// );
						ImageLoader imageLoader = ImageLoader.getInstance();
						imageLoader.displayImage(imgurl, iv_sc_gold_top,
								options);
						tv_sc_gold_title.setText("商品名称/" + name);
						tv_sc_gold_time.setText("上线时间:" + time);
						tv_sc_gold_le.setText(String.valueOf(amount));
						tv_sc_gold_score.setText(jo.getString("eval"));
						// 购买人数
						tv_sc_gold_buynum.setText(String.valueOf(jo
								.getInt("buy_num")));

						// 月购买
						tv_sc_gold_month.setText(String.valueOf(jo
								.getInt("mbuy_num")));
						// 库存
						tv_sc_gold_resnum.setText(String.valueOf(jo
								.getInt("inventory")));
						// 简介
						remark = jo.getString("remark").trim();

						if (buyWay == 1) {
							iv_sc_gold_le
									.setImageResource(R.drawable.me_icon_le);
						} else if (buyWay == 2) {
							iv_sc_gold_le
									.setImageResource(R.drawable.me_icon_jin);
						}
						setvp();
					} catch (JSONException e) {
						Diaoxian.showerror(ShangQadetilActivity.this,
								e.getMessage());
						return;
					}

				} else {
					Diaoxian.showerror(ShangQadetilActivity.this,
							msg.obj.toString());
					return;
				}
			}
		};

		new Thread() {
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("pk_goods", id);

				Message msg = new Message();

				String result = HttpUse.messageget("QueAndAns", "showGoods",
						map);
//				System.out.println("商品的详情" + result);
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = jo.getJSONObject("data");
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
		ListenerServer.setfinish(ShangQadetilActivity.this, rl_sc_gold_back);

		// 立即购买的点击事件
		tv_sc_gold_buy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO
				Intent intent = new Intent(ShangQadetilActivity.this,
						OrderActivity.class);
				intent.putExtra("name", name);
				intent.putExtra("time", time);
				intent.putExtra("imgurl", imgurl);
				intent.putExtra("amount", amount);
				intent.putExtra("buyWay", buyWay);
				intent.putExtra("pk_goods", pk_goods);
				startActivity(intent);
			}
		});

		rl_sc_gold_info.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				tv_sc_gold_info.setTextColor(getResources().getColor(
						R.color.main));
				iv_sc_gold_info.setBackgroundColor(getResources().getColor(
						R.color.main));
				tv_sc_gold_comment.setTextColor(getResources().getColor(
						R.color.zywz));
				iv_sc_gold_comment.setBackgroundColor(getResources().getColor(
						R.color.dise));
				vp_sc_gold.setCurrentItem(0);
			}
		});

		rl_sc_gold_comment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				tv_sc_gold_info.setTextColor(getResources().getColor(
						R.color.zywz));
				iv_sc_gold_info.setBackgroundColor(getResources().getColor(
						R.color.dise));
				tv_sc_gold_comment.setTextColor(getResources().getColor(
						R.color.main));
				iv_sc_gold_comment.setBackgroundColor(getResources().getColor(
						R.color.main));
				vp_sc_gold.setCurrentItem(1);
			}
		});

		vp_sc_gold.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				if (arg0 == 2) {
					switch (vp_sc_gold.getCurrentItem()) {
					case 0:
						tv_sc_gold_info.setTextColor(getResources().getColor(
								R.color.main));
						iv_sc_gold_info.setBackgroundColor(getResources()
								.getColor(R.color.main));
						tv_sc_gold_comment.setTextColor(getResources()
								.getColor(R.color.zywz));
						iv_sc_gold_comment.setBackgroundColor(getResources()
								.getColor(R.color.dise));
						break;
					case 1:
						tv_sc_gold_info.setTextColor(getResources().getColor(
								R.color.zywz));
						iv_sc_gold_info.setBackgroundColor(getResources()
								.getColor(R.color.dise));
						tv_sc_gold_comment.setTextColor(getResources()
								.getColor(R.color.main));
						iv_sc_gold_comment.setBackgroundColor(getResources()
								.getColor(R.color.main));
						break;
					default:
						break;
					}
				}
			}
		});

	}

	private void setvp() {
		fragment_goodsinfo = new Fragment_goodsinfo(id, remark);
		fragment_goodscomment = new Fragment_goodscomment(id);

		listvp = new ArrayList<Fragment>();

		listvp.add(fragment_goodsinfo);
		listvp.add(fragment_goodscomment);

		Vpadapter adapter = new Vpadapter(getSupportFragmentManager(), listvp);
		vp_sc_gold.setAdapter(adapter);
	}
}
