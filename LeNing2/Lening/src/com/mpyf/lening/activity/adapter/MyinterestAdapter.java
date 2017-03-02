package com.mpyf.lening.activity.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mpyf.lening.R;

public class MyinterestAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, String>> list;

	public MyinterestAdapter(List<Map<String, String>> list, Context context) {
		this.list = list;
		this.context = context;
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
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.mark_layout, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String name = list.get(position).get("label_name");
		holder.tv.setText(name);
		// if (hs.contains(position)) {
		// holder.tv.setBackgroundResource(R.drawable.zd_qa_icon_s);
		// } else {
		// holder.tv.setBackgroundResource(R.drawable.zd_qa_icon_n);
		// }
		return convertView;
	}

	private class ViewHolder {
		private TextView tv;

		public ViewHolder(View convertView) {
			tv = (TextView) convertView.findViewById(R.id.textView1);
		}
	}

}
