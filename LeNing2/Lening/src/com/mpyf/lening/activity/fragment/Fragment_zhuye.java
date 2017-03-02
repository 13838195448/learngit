package com.mpyf.lening.activity.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.HorizontalListView;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.MyGridview;
import com.mpyf.lening.Jutil.Readsaved;
import com.mpyf.lening.activity.activity.CommunityActivity;
import com.mpyf.lening.activity.activity.ExamActivity;
import com.mpyf.lening.activity.activity.FenleiActivity;
import com.mpyf.lening.activity.activity.MoreActivity;
import com.mpyf.lening.activity.activity.JPkcActivity;
import com.mpyf.lening.activity.activity.MainActivity;
import com.mpyf.lening.activity.activity.MessageActivity;
import com.mpyf.lening.activity.activity.MingshiActivity;
import com.mpyf.lening.activity.activity.MingshidetilActivity;
import com.mpyf.lening.activity.activity.NLRZActivity;
import com.mpyf.lening.activity.activity.NewsActivity;
import com.mpyf.lening.activity.activity.NotbuycourseActivity;
import com.mpyf.lening.activity.activity.PaihangbangActivity;
import com.mpyf.lening.activity.activity.PeixunActivity;
import com.mpyf.lening.activity.activity.QaActivity;
import com.mpyf.lening.activity.activity.RmkcActivity;
import com.mpyf.lening.activity.activity.SerchActivity;
import com.mpyf.lening.activity.activity.ShangChengActivity;
import com.mpyf.lening.activity.activity.ZhidaoActivity;
import com.mpyf.lening.activity.activity.ZhuantiActivity;
import com.mpyf.lening.activity.activity.ZiYuanActivity;
import com.mpyf.lening.activity.adapter.CourseAdapter;
import com.mpyf.lening.activity.adapter.DaohangAdapter;
import com.mpyf.lening.activity.adapter.LecherAdapter;
import com.mpyf.lening.activity.adapter.Vpadapter;
import com.mpyf.lening.interfaces.bean.Parame.Login;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class Fragment_zhuye extends Fragment {

	private LinearLayout ll_main_fenlei, ll_main_remen, ll_main_jingpin,
			ll_main_mingshi;
	private ImageView iv_main_seek, iv_main_news, iv_main_lunbo1,
			iv_main_lunbo2, iv_main_lunbo3, iv_main_lunbo4, iv_zhuye_paihangb;

	private ViewPager vp_main_lunbo;

	private MyGridview gv_main_daohang, gv_main_remen, gv_main_jingpin;
	private HorizontalListView gv_main_mingshi;

	private static Handler handler;
	private Runnable viewpagerRunnable;

	private List<Map<String, Object>> listremen, listjingpin, listlecher;
	private Dialog dialog;
	private List<Fragment> listf;

	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_zhuye, null);
		init(view);
		addlistener();
		return view;
	};

	private void init(View view) {
		ll_main_fenlei = (LinearLayout) view.findViewById(R.id.ll_main_fenlei);
		iv_main_seek = (ImageView) view.findViewById(R.id.iv_main_seek);
		iv_main_news = (ImageView) view.findViewById(R.id.iv_main_news);
		vp_main_lunbo = (ViewPager) view.findViewById(R.id.vp_main_lunbo);

		iv_main_lunbo1 = (ImageView) view.findViewById(R.id.iv_main_lunbo1);
		iv_main_lunbo2 = (ImageView) view.findViewById(R.id.iv_main_lunbo2);
		iv_main_lunbo3 = (ImageView) view.findViewById(R.id.iv_main_lunbo3);
		iv_main_lunbo4 = (ImageView) view.findViewById(R.id.iv_main_lunbo4);
		iv_zhuye_paihangb = (ImageView) view
				.findViewById(R.id.iv_zhuye_paihangb);

		gv_main_daohang = (MyGridview) view.findViewById(R.id.gv_main_daohang);
		gv_main_remen = (MyGridview) view.findViewById(R.id.gv_main_remen);
		gv_main_jingpin = (MyGridview) view.findViewById(R.id.gv_main_jingpin);

		ll_main_remen = (LinearLayout) view.findViewById(R.id.ll_main_remen);
		ll_main_jingpin = (LinearLayout) view
				.findViewById(R.id.ll_main_jingpin);

		ll_main_mingshi = (LinearLayout) view
				.findViewById(R.id.ll_main_mingshi);
		gv_main_mingshi = (HorizontalListView) view
				.findViewById(R.id.gv_main_mingshi);

		showdata();
		getlunbotu();
	}

	private void addlistener() {
		gv_main_daohang.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case 0:
					// 跳转到 知道Activity
					startActivity(new Intent(getActivity(),
							ZhidaoActivity.class));
					break;
				case 1:
					// 跳转到 培训Activity
					startActivity(new Intent(getActivity(),
							PeixunActivity.class));
					break;
				case 2:
					// 跳转到 测一测Activity
					startActivity(new Intent(getActivity(), ExamActivity.class));
					break;
				case 3:
					// 跳转到 专题Activity
					startActivity(new Intent(getActivity(),
							ZhuantiActivity.class));
					break;
				case 4:
					// 跳转到 资源Activity
					startActivity(new Intent(getActivity(),
							ZiYuanActivity.class));

					break;
				case 5:
					// 跳转到 资讯Activity
					startActivity(new Intent(getActivity(), NewsActivity.class));

					break;
				// 跳转到 社区Activity
				// case 5:
				// startActivity(new Intent(getActivity(),
				// CommunityActivity.class));
				// break;
				case 6:
					// 跳转到 认证Activity
					startActivity(new Intent(getActivity(), NLRZActivity.class));
					break;
				case 7:

					// 签到相关操作
					dialog = MyDialog.MyDialogloading(getActivity());
					dialog.show();
					final Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							dialog.dismiss();
							if (msg.what == 1) {
								qiandao();
							} else {
								Diaoxian.showerror(getActivity(), "今天已签到");
							}
						}
					};

					new Thread() {
						@Override
						public void run() {
							Map<String, Object> map = new HashMap<String, Object>();
							String result = HttpUse.messageget("Account",
									"isSignIn", map);
							Message msg = new Message();
							try {
								JSONObject jo = new JSONObject(result);
								if (jo.getBoolean("result")) {
									msg.what = 1;
								}
								handler.sendMessage(msg);
							} catch (JSONException e) {
								e.getMessage();
							}
						};
					}.start();

					break;
				case 8:
					// 跳转到 商城Activity
					startActivity(new Intent(getActivity(),
							ShangChengActivity.class));
					break;
				case 9:
					// 跳转到 更多Activity
					startActivity(new Intent(getActivity(), MoreActivity.class));
					break;

				default:
					break;
				}
			}
		});

		gv_main_remen.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getActivity(),
						NotbuycourseActivity.class);
				intent.putExtra("id", listremen.get(arg2).get("id").toString());
				startActivity(intent);
			}
		});

		gv_main_jingpin.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getActivity(),
						NotbuycourseActivity.class);
				intent.putExtra("id", listjingpin.get(arg2).get("id")
						.toString());
				startActivity(intent);
			}
		});

		gv_main_mingshi.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						MingshidetilActivity.class);
				intent.putExtra("id", listlecher.get(arg2).get("id").toString());
				startActivity(intent);
			}
		});

		iv_main_seek.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getActivity(), SerchActivity.class));
			}
		});

		iv_main_news.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getActivity(), MessageActivity.class));
			}
		});

		ll_main_fenlei.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getActivity(), FenleiActivity.class));
			}
		});

		ll_main_remen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getActivity(), RmkcActivity.class));
			}
		});

		ll_main_jingpin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getActivity(), JPkcActivity.class));
			}
		});

		ll_main_mingshi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(), MingshiActivity.class));
			}
		});
		iv_zhuye_paihangb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(),
						PaihangbangActivity.class));
			}
		});
	}

	/**
	 * 签到动画
	 */
	private void qiandao() {

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(final Message msg) {
				if (msg.what == 1) {

					try {
						final JSONObject jo = new JSONObject(msg.obj.toString());

						final Dialog dialog = MyDialog.MyDialogShow(
								getActivity(), R.layout.pop_qiandao, 1.0f);

						Button bt_qiandao_ok = (Button) dialog
								.findViewById(R.id.bt_qiandao_ok);
						final ImageView iv_qiandao_donghua = (ImageView) dialog
								.findViewById(R.id.iv_qiandao_donghua);
						final TextView tv_qiandao_days = (TextView) dialog
								.findViewById(R.id.tv_qiandao_days);

						TextView tv_qiandao_rule = (TextView) dialog
								.findViewById(R.id.tv_qiandao_rule);

						iv_qiandao_donghua
								.setBackgroundResource(R.anim.qiandao);
						final AnimationDrawable animation = (AnimationDrawable) iv_qiandao_donghua
								.getBackground();
						tv_qiandao_days.setText("连续签到" + jo.getInt("days")
								+ "天");

						Setting.les = jo.getInt("class_gold");
						bt_qiandao_ok.setText("领取乐币×" + jo.getInt("num"));
						tv_qiandao_rule.setText(jo.getString("rules"));

						bt_qiandao_ok.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {

								animation.start();
								Timer timer = new Timer();
								TimerTask task = new TimerTask() {

									@Override
									public void run() {
										dialog.dismiss();

										startActivity(new Intent(getActivity(),
												MainActivity.class));
										getActivity().finish();
									}
								};
								timer.schedule(task, 3000);
							}
						});
						dialog.show();
					} catch (JSONException e) {
						Diaoxian.showerror(getActivity(), e.getMessage());
					}

				} else {
					Diaoxian.showerror(getActivity(), msg.obj.toString());
				}
			}
		};
		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				Message msg = new Message();
				try {

					String result = HttpUse
							.messageget("Account", "signIn", map);

					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = jo.getString("data");
					} else {
						msg.obj = jo.getString("message");
					}
				} catch (Exception e) {
					msg.obj = e.getMessage();
				}
				handler.sendMessage(msg);
			};
		}.start();

	}

	private void getlunbotu() {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						listf = new ArrayList<Fragment>();
						JSONArray ja = new JSONArray(msg.obj.toString());
						for (int i = 0; i < ja.length(); i++) {
							JSONObject jo = ja.getJSONObject(i);
							listf.add(new Fragment_Lunbo(jo.getInt("adv_type"),
									jo.getString("pic_url"), jo
											.getString("parame")));
						}
						setlunbo();
					} catch (JSONException e) {
						Diaoxian.showerror(getActivity(), e.getMessage());
					}
				} else {
					Diaoxian.showerror(getActivity(), msg.obj.toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				String result = HttpUse.messageget("Account", "getAdvertising",
						map);
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
					msg.obj = e.getMessage();
				}

				handler.sendMessage(msg);

			}
		}.start();
	}

	/**
	 * 设置轮播广告
	 */
	private void setlunbo() {

		Vpadapter adapter = new Vpadapter(getActivity()
				.getSupportFragmentManager(), listf);
		vp_main_lunbo.setAdapter(adapter);

		handler = new Handler();
		viewpagerRunnable = new Runnable() {

			@Override
			public void run() {
				int nowIndex = vp_main_lunbo.getCurrentItem();
				int count = vp_main_lunbo.getAdapter().getCount();
				// 如果下一张的索引大于最后一张，则切换到第一张
				if (nowIndex + 1 >= count) {
					vp_main_lunbo.setCurrentItem(0);
				} else {
					vp_main_lunbo.setCurrentItem(nowIndex + 1);
				}
				handler.postDelayed(viewpagerRunnable, 2500);
			}
		};
		handler.postDelayed(viewpagerRunnable, 2500);

		vp_main_lunbo.setOnPageChangeListener(new OnPageChangeListener() {
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
					int i = vp_main_lunbo.getCurrentItem();
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
					if (vp_main_lunbo.getCurrentItem() == vp_main_lunbo
							.getAdapter().getCount() - 1 && !isScrolled) {
						vp_main_lunbo.setCurrentItem(0);
					}
					// 当前为第一张，此时从左向右滑，则切换到最后一张
					else if (vp_main_lunbo.getCurrentItem() == 0 && !isScrolled) {
						vp_main_lunbo.setCurrentItem(vp_main_lunbo.getAdapter()
								.getCount() - 1);
					}
					break;
				}
			}
		});
	}

	// 将首页广告底部点全部还原为黑色
	private void cleanbanner() {
		iv_main_lunbo1.setImageResource(R.drawable.home_icon_circle_d);
		iv_main_lunbo2.setImageResource(R.drawable.home_icon_circle_d);
		iv_main_lunbo3.setImageResource(R.drawable.home_icon_circle_d);
		iv_main_lunbo4.setImageResource(R.drawable.home_icon_circle_d);
	}

	// 滑动页面是底部点变化
	private void bannerChange(int num) {
		switch (num) {
		case 0:
			iv_main_lunbo1.setImageResource(R.drawable.home_icon_circle_s);
			break;
		case 1:
			iv_main_lunbo2.setImageResource(R.drawable.home_icon_circle_s);
			break;
		case 2:
			iv_main_lunbo3.setImageResource(R.drawable.home_icon_circle_s);
			break;
		case 3:
			iv_main_lunbo4.setImageResource(R.drawable.home_icon_circle_s);
			break;
		}

	}

	/**
	 * 加载导航布局
	 */
	private void showdata() {
		String[] name = { "知道", "培训", "测一测", "专题", "资源", "资讯", "认证", "签到",
				"商城", "更多" };
		int[] image = { R.drawable.home_btn_qa, R.drawable.home_btn_train,
				R.drawable.home_btn_test, R.drawable.home_btn_subject,
				R.drawable.home_btn_resource, R.drawable.home_btn_inform,
				R.drawable.home_btn_authe, R.drawable.home_btn_check,
				R.drawable.home_btn_shop, R.drawable.home_btn_more };
		List<Map<String, Object>> listdaohang = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < name.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", name[i]);
			map.put("image", image[i]);
			listdaohang.add(map);
		}
		DaohangAdapter adapter = new DaohangAdapter(getActivity(), listdaohang);
		gv_main_daohang.setAdapter(adapter);

		// 热门课程
		getremen();

		getjingpin();
		getleacher();
	}

	private void getremen() {
		// dialog = MyDialog.MyDialogloading(getActivity());
		// dialog.show();
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				// dialog.dismiss();
				if (msg.what == 1) {
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						listremen = new ArrayList<Map<String, Object>>();
						for (int i = 0; i < ja.length(); i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							JSONObject jo = ja.getJSONObject(i);
							map.put("id", jo.getString("PK_Course"));
							map.put("context", jo.getString("CourseName"));
							map.put("scan", jo.getString("BuyNum"));
							map.put("cost", jo.getString("Amount"));
							map.put("PicUrl", jo.getString("PicUrl"));
							map.put("BuyWay", jo.getString("BuyWay"));
							listremen.add(map);
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
					CourseAdapter adapterremen = new CourseAdapter(
							getActivity(), listremen);
					gv_main_remen.setAdapter(adapterremen);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("page", 1);
				map.put("pageSize", 6);
				Message msg = new Message();
				String result = HttpUse.messageget("CourseStudy", "hotCourse",
						map);
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
			}
		}.start();
	}

	private void getjingpin() {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						listjingpin = new ArrayList<Map<String, Object>>();
						for (int i = 0; i < ja.length(); i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							JSONObject jo = ja.getJSONObject(i);
							map.put("id", jo.getString("PK_Course"));
							map.put("context", jo.getString("CourseName"));
							map.put("scan", jo.getString("BuyNum"));
							map.put("cost", jo.getString("Amount"));
							map.put("PicUrl", jo.getString("PicUrl"));
							map.put("BuyWay", jo.getString("BuyWay"));
							listjingpin.add(map);
						}

					} catch (JSONException e) {
						Diaoxian.showerror(getActivity(), e.getMessage());
					}

					CourseAdapter adapterremen = new CourseAdapter(
							getActivity(), listjingpin);
					gv_main_jingpin.setAdapter(adapterremen);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("page", 1);
				map.put("pageSize", 6);
				Message msg = new Message();
				String result = HttpUse.messageget("CourseStudy",
						"goodsCourse", map);
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
			}
		}.start();
	}

	private void getleacher() {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						listlecher = new ArrayList<Map<String, Object>>();
						for (int i = 0; i < ja.length(); i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							JSONObject jo = ja.getJSONObject(i);
							map.put("id", jo.getString("PK_Lec"));
							map.put("name", jo.getString("lec_Name"));
							map.put("picUrl", jo.getString("picUrl"));
							map.put("Lec_Level", jo.getInt("lec_Level"));

							listlecher.add(map);
						}

					} catch (JSONException e) {
						Diaoxian.showerror(getActivity(), e.getMessage());
					}

					LecherAdapter adapterremen = new LecherAdapter(
							getActivity(), listlecher);
					gv_main_mingshi.setAdapter(adapterremen);
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("page", 1);
				map.put("pageSize", 100);
				Message msg = new Message();
				String result = HttpUse.messageget("Account", "listLecturer",
						map);
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
			}
		}.start();
	}
}
