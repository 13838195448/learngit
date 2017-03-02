package com.mpyf.lening.activity.adapter;

import java.util.ArrayList;
import java.util.List;

import com.mpyf.lening.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ZX_XuanAdapter extends BaseAdapter {
	private List<String> list;
	private Context context;
	private ArrayList<String> checkedItems;

	public ZX_XuanAdapter(Context context, List<String> list, ArrayList<String> checkedItems) {
		this.context = context;
		this.list = list;
		this.checkedItems = checkedItems;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(context, R.layout.zx_xuan, null);
		TextView tv_zx_xuan = (TextView) view.findViewById(R.id.tv_zx_xuan);
		if(checkedItems.size() > 0){
			System.out.println(checkedItems.get(0));
		}
		tv_zx_xuan.setText(list.get(position));
		if(checkedItems.contains(list.get(position).substring(0, 1))){
			tv_zx_xuan.setTextColor(Color.YELLOW);
		}else{
			tv_zx_xuan.setTextColor(Color.WHITE);
		}
		return view;
	}

}
