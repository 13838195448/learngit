package com.mpyf.lening.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.mpyf.lening.R;

public class Fragment_gate extends Fragment {

	private ImageView  iv_get_main;
	private int drawable;
	public Fragment_gate(int drawable){
		this.drawable=drawable;
	}
	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_gate, null);
		
		showinfo(view);
		return view;
	};
	
	private void showinfo(View view){
		iv_get_main=(ImageView) view.findViewById(R.id.iv_get_main);
		iv_get_main.setImageResource(drawable);
	}
}
