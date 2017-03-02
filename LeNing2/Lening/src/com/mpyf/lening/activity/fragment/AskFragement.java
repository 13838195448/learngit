package com.mpyf.lening.activity.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.activity.activity.FatieActivity;
import com.mpyf.lening.activity.activity.ZDnewsActivity;
import com.mpyf.lening.activity.adapter.Vpadapter;

public class AskFragement extends Fragment implements OnClickListener {
	private RelativeLayout rl_qa_search;
	private LinearLayout ll_qa_news;
	private ImageView iv_qa_seek, iv_fr_ask_addqa, iv_qa_news;
	private ViewPager mViewPager;
	private TextView mOne, mTwo, mThree;
	private EditText et_fr_as_search;
	private List<Fragment> listf;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_ask, null);
		mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
		mOne = (TextView) view.findViewById(R.id.tv_one);
		mTwo = (TextView) view.findViewById(R.id.tv_two);
		mThree = (TextView) view.findViewById(R.id.tv_three);
		et_fr_as_search = (EditText) view.findViewById(R.id.et_qa_search);
		rl_qa_search = (RelativeLayout) view.findViewById(R.id.rl_qa_search);
		iv_qa_seek = (ImageView) view.findViewById(R.id.iv_qa_seek);
		iv_fr_ask_addqa = (ImageView) view.findViewById(R.id.iv_fr_ask_addqa);
		iv_qa_news = (ImageView) view.findViewById(R.id.iv_qa_news);

		setvp();
		return view;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		mOne.setOnClickListener(this);
		mTwo.setOnClickListener(this);
		mThree.setOnClickListener(this);
		addlistener();
	}

	private void setvp() {
		listf = new ArrayList<Fragment>();
		listf.add(new Fragment_qaall());
		listf.add(new Fragment_qahot());
		listf.add(new Fragment_qagoodpay());

		Vpadapter adapter = new Vpadapter(getChildFragmentManager(), listf);
		mViewPager.setAdapter(adapter);
		clearall(1);
		mViewPager.setCurrentItem(1);
	}

	private void addlistener() {

		mOne.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				clearall(0);
				mViewPager.setCurrentItem(0);
			}
		});
		mTwo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				clearall(1);
				mViewPager.setCurrentItem(1);
			}
		});

		mThree.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				clearall(2);
				mViewPager.setCurrentItem(2);
			}
		});

		iv_fr_ask_addqa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				startActivity(new Intent(getActivity(), FatieActivity.class));
			}
		});

		// TODO 跳到消息中心页面
		iv_qa_news.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), ZDnewsActivity.class);
				startActivity(intent);
			}
		});
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

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
				if (arg0 == 2) {
					clearall(mViewPager.getCurrentItem());
				}
			}
		});

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.tv_one:
			mViewPager.setCurrentItem(0);
			break;
		case R.id.tv_two:
			mViewPager.setCurrentItem(1);
			break;
		case R.id.tv_three:
			mViewPager.setCurrentItem(2);
			break;

		}
	}

	private void clearall(int index) {
		TextView[] texttop = { mOne, mTwo, mThree };
		// ImageView[] imagetop={iv_qa_all,iv_qa_hot,iv_qa_goodpay};

		for (int i = 0; i < texttop.length; i++) {
			if (i == index) {
				texttop[i].setTextColor(getResources().getColor(R.color.my));
				// imagetop[i].setBackgroundColor(getResources().getColor(R.color.my));
			} else {
				texttop[i].setTextColor(getResources().getColor(R.color.zywz));
				// imagetop[i].setBackgroundColor(getResources().getColor(R.color.dise));
			}
		}
	}
}
