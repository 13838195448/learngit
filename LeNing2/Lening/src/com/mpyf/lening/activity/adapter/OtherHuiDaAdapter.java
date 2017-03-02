package com.mpyf.lening.activity.adapter;

import java.util.List;
import java.util.Map;

import com.mpyf.lening.R;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OtherHuiDaAdapter extends BaseAdapter {
	private List<Map<String, Object>> list;
	private Context context;
	private TextView tv_quename, tv_queans;

	public OtherHuiDaAdapter(Context context, List<Map<String, Object>> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(context, R.layout.item_otherhuida, null);
		tv_quename = (TextView) view.findViewById(R.id.tv_quename);
		tv_queans = (TextView) view.findViewById(R.id.tv_queans);
		tv_quename.setText(list.get(position).get("queName").toString());
		tv_queans.setText(list.get(position).get("ANS_CONTENT").toString());

		return view;
	}

}
