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

public class KebaomingAdapter extends BaseAdapter {

	private List<Map<String, Object>> list;
	private Context context;

	public KebaomingAdapter(Context context, List<Map<String, Object>> list) {
		this.context = context;
		this.list = list;
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
				R.layout.item_kebaoming, null);


		TextView tv_kebaoming_title = (TextView) view
				.findViewById(R.id.tv_kebaoming_title);
		TextView tv_kebaoming_name = (TextView) view
				.findViewById(R.id.tv_kebaoming_name);
		TextView tv_kebaoming_type = (TextView) view
				.findViewById(R.id.tv_kebaoming_type);
		

		tv_kebaoming_title.setText(list.get(arg0).get("title").toString());
		tv_kebaoming_name.setText(list.get(arg0).get("name").toString());
		
		if((Integer)list.get(arg0).get("type")==0){
			tv_kebaoming_type.setText("优普年度认证");
		}else if((Integer)list.get(arg0).get("type")==1){
			tv_kebaoming_type.setText("授权中心认证");
		}
		

		return view;
	}

}
