package com.mpyf.lening.activity.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mpyf.lening.R;

public class KcflxxAdapter extends BaseAdapter {

	private List<Map<String, Object>> list;
	private Context context;
	private String title;
	
	
	public KcflxxAdapter(Context context, List<Map<String, Object>> list,String title) {
		this.context = context;
		this.list = list;
		this.title=title;
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
		View view = LayoutInflater.from(context).inflate(
				R.layout.item_kcflxx, null);

		TextView tv_kcflxx_title = (TextView) view
				.findViewById(R.id.tv_kcflxx_title);
		
		tv_kcflxx_title.setText(list.get(arg0).get("title").toString());

		if(tv_kcflxx_title.getText().toString().trim().equals(title)){
			tv_kcflxx_title.setTextColor(view.getResources().getColor(R.color.main));
		}
		
		return view;
	}

}
