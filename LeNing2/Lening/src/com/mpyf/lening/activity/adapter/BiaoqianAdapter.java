package com.mpyf.lening.activity.adapter;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.activity.activity.FatieActivity;
import com.mpyf.lening.activity.activity.MyInterestActivity;

public class BiaoqianAdapter extends BaseAdapter {

	private Activity fatieActivity;
	private List<Map<String, String>> list1;
	private HashSet<Integer> hs;

	public BiaoqianAdapter(List<Map<String, String>> list1,
			FatieActivity fatieActivity, HashSet<Integer> hs) {
		this.list1 = list1;
		this.fatieActivity = fatieActivity;
		this.hs = hs;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list1.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list1.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(fatieActivity, R.layout.mark_layout,
					null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String name = list1.get(position).get("label_name");
		holder.tv.setText(name);
		if (hs.contains(position)) {
			holder.tv.setBackgroundResource(R.drawable.zd_qa_icon_s);
		} else {
			holder.tv.setBackgroundResource(R.drawable.zd_qa_icon_n);
		}
		return convertView;
	}

	private class ViewHolder {
		private TextView tv;

		public ViewHolder(View convertView) {
			tv = (TextView) convertView.findViewById(R.id.textView1);
		}
	}

}
