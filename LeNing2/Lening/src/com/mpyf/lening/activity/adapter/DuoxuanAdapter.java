package com.mpyf.lening.activity.adapter;

import java.util.List;
import java.util.Map;

import com.mpyf.lening.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DuoxuanAdapter extends BaseAdapter {

	private List<Map<String, Object>> list;
	private Context context;
	public DuoxuanAdapter(Context context ,List<Map<String, Object>> list) {
		this.list = list;
		this.context = context ;
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
		
		return arg0;
	}
	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		View view = View.inflate(context, R.layout.item_duoxuan, null);
		TextView tv_duoxuan_option = (TextView) view.findViewById(R.id.tv_duoxuan_option);
		
		tv_duoxuan_option.setText(list.get(arg0).get("num").toString()+"¡¢ "+
				list.get(arg0).get("option").toString());
		return view;
	}

}
