package com.mpyf.lening.activity.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpyf.lening.R;

public class DaohangAdapter extends BaseAdapter {

	private List<Map<String,Object>> list;
	private Context context;
	
	public DaohangAdapter(Context context,List<Map<String,Object>> list){
		this.context=context;
		this.list=list;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}
	@Override
	public Object getItem(int arg0) {
		return null;
	}
	@Override
	public long getItemId(int arg0) {
		return 0;
	}
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view=LayoutInflater.from(context).inflate(R.layout.item_daohang, null);
		ImageView iv_main_daohang=(ImageView) view.findViewById(R.id.iv_main_daohang);
		TextView tv_main_daohang=(TextView) view.findViewById(R.id.tv_main_daohang);
		
		tv_main_daohang.setText(list.get(arg0).get("name").toString());
		iv_main_daohang.setImageResource((Integer)list.get(arg0).get("image"));
		return view;
	}

}
