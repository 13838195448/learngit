package com.mpyf.lening.activity.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mpyf.lening.R;
import com.mpyf.lening.activity.activity.NewsActivity;
import com.mpyf.lening.activity.activity.PaihangActivity;
import com.mpyf.lening.activity.activity.RenWuActivity;
import com.mpyf.lening.activity.activity.TouPiaoActivity;
import com.mpyf.lening.activity.activity.ZhenXiangActivity;
import com.mpyf.lening.activity.adapter.Vpadapter;
import com.mpyf.lening.interfaces.bean.Parame.Question;
import com.mpyf.lening.interfaces.http.Setting;

public class EventFragement extends Fragment {
	private ViewPager vp_fr_event_lunbo;
	private static Handler handler;
	private Runnable viewpagerRunnable;
	private ImageView iv_fr_event_lunbo1, iv_fr_event_lunbo2,
			iv_fr_event_lunbo3, iv_fr_event_lunbo4;
	private LinearLayout ll_fr_event_zhima, ll_fr_event_wenda,
			ll_fr_event_toupiao, ll_fr_event_paihang, ll_fr_event_renwu;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_event, null);
		// 初始化控件
		init(view);
		setlunbo();
		addlistener();
		return view;

	}

	private void init(View view) {
		vp_fr_event_lunbo = (ViewPager) view
				.findViewById(R.id.vp_fr_event_lunbo);
		iv_fr_event_lunbo1 = (ImageView) view
				.findViewById(R.id.iv_fr_event_lunbo1);
		iv_fr_event_lunbo2 = (ImageView) view
				.findViewById(R.id.iv_fr_event_lunbo2);
		// iv_fr_event_lunbo3 = (ImageView) view
		// .findViewById(R.id.iv_fr_event_lunbo3);
		// iv_fr_event_lunbo4 = (ImageView) view
		// .findViewById(R.id.iv_fr_event_lunbo4);
		ll_fr_event_zhima = (LinearLayout) view
				.findViewById(R.id.ll_fr_event_zhima);
		ll_fr_event_wenda = (LinearLayout) view
				.findViewById(R.id.ll_fr_event_wenda);
		ll_fr_event_toupiao = (LinearLayout) view
				.findViewById(R.id.ll_fr_event_toupiao);
		ll_fr_event_paihang = (LinearLayout) view
				.findViewById(R.id.ll_fr_event_paihang);
		ll_fr_event_renwu = (LinearLayout) view
				.findViewById(R.id.ll_fr_event_renwu);

	}

	/**
	 * 设置轮播广告
	 */
	private void setlunbo() {

		List<Fragment> listf = new ArrayList<Fragment>();
		listf.add(new Fragment_lunbo1());
		listf.add(new Fragment_lunbo2());

		// listf.add(new Fragment_rzlb(Setting.apiUrl
		// + "new-pages/images/app/cer/TURZ1.png"));
		// listf.add(new Fragment_rzlb(Setting.apiUrl
		// + "new-pages/images/app/cer/TURZ2.png"));
		// listf.add(new Fragment_rzlb(Setting.apiUrl
		// + "new-pages/images/app/cer/TURZ3.png"));

		Vpadapter adapter = new Vpadapter(getFragmentManager(), listf);
		vp_fr_event_lunbo.setAdapter(adapter);

		handler = new Handler();
		viewpagerRunnable = new Runnable() {

			@Override
			public void run() {
				int nowIndex = vp_fr_event_lunbo.getCurrentItem();
				int count = vp_fr_event_lunbo.getAdapter().getCount();
				// 如果下一张的索引大于最后一张，则切换到第一张
				if (nowIndex + 1 >= count) {
					vp_fr_event_lunbo.setCurrentItem(0);
				} else {
					vp_fr_event_lunbo.setCurrentItem(nowIndex + 1);
				}
				handler.postDelayed(viewpagerRunnable, 2500);
			}
		};
		handler.postDelayed(viewpagerRunnable, 2500);

		vp_fr_event_lunbo.setOnPageChangeListener(new OnPageChangeListener() {

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
					int i = vp_fr_event_lunbo.getCurrentItem();
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
					if (vp_fr_event_lunbo.getCurrentItem() == vp_fr_event_lunbo
							.getAdapter().getCount() - 1 && !isScrolled) {
						vp_fr_event_lunbo.setCurrentItem(0);
					}
					// 当前为第一张，此时从左向右滑，则切换到最后一张
					else if (vp_fr_event_lunbo.getCurrentItem() == 0
							&& !isScrolled) {
						vp_fr_event_lunbo.setCurrentItem(vp_fr_event_lunbo
								.getAdapter().getCount() - 1);
					}
					break;
				}
			}
		});
	}

	// 将首页广告底部点全部还原为黑色
	private void cleanbanner() {
		iv_fr_event_lunbo1.setImageResource(R.drawable.home_icon_circle_d);
		iv_fr_event_lunbo2.setImageResource(R.drawable.home_icon_circle_d);
		// iv_fr_event_lunbo3.setImageResource(R.drawable.home_icon_circle_d);
	}

	// 滑动页面是底部点变化
	private void bannerChange(int num) {
		switch (num) {
		case 0:
			iv_fr_event_lunbo1.setImageResource(R.drawable.home_icon_circle_s);
			break;
		case 1:
			iv_fr_event_lunbo2.setImageResource(R.drawable.home_icon_circle_s);
			break;
		// case 2:
		// iv_fr_event_lunbo3.setImageResource(R.drawable.home_icon_circle_s);
		// break;
		}
	}

	private void addlistener() {
		// 跳转到真相问答机页面
		ll_fr_event_wenda.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						ZhenXiangActivity.class);
				startActivity(intent);
			}
		});
		// 跳转到投票不能停界面
		ll_fr_event_toupiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getActivity(), TouPiaoActivity.class);
				startActivity(intent);
			}
		});
		// 跳转到我得任务页面
		ll_fr_event_renwu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), RenWuActivity.class);
				startActivity(intent);
			}
		});
		// 跳转到排行榜界面
		ll_fr_event_paihang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), PaihangActivity.class);
				// intent.putExtra("userId",);
				startActivity(intent);
			}
		});
		// 跳转到芝麻公告页面
		ll_fr_event_zhima.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), NewsActivity.class);
				startActivity(intent);
			}
		});
	}

}
