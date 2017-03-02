package com.mpyf.lening.activity.fragment;

import com.mpyf.lening.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FgDuoxuan extends Fragment {

//	public android.view.View onCreateView(android.view.LayoutInflater inflater,
//			android.view.ViewGroup container,
//			android.os.Bundle savedInstanceState) {
//
//		View v = inflater.inflate(R.layout.fragment_danxuan, null);
//		return container;
//
//	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_danxuan, null);
		
		TextView tv_test_num = (TextView) v.findViewById(R.id.tv_test_num);
		TextView tv_sum_unm = (TextView) v.findViewById(R.id.tv_sum_unm);
		TextView tv_quest_title = (TextView) v.findViewById(R.id.tv_quest_title);
		
		return v;
	}
}
