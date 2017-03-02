package com.mpyf.lening.activity.adapter;

import java.util.List;
import java.util.Map;

import com.mpyf.lening.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OtherTiwenAdapter extends BaseAdapter {
	private List<Map<String, Object>> list;
	private Context context;
	private TextView tv_quecontent, tv_questate;

	public OtherTiwenAdapter(Context context, List<Map<String, Object>> list) {
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
		View view = View.inflate(context, R.layout.item_othertiwen, null);
		tv_quecontent = (TextView) view.findViewById(R.id.tv_quecontent);
		tv_questate = (TextView) view.findViewById(R.id.tv_questate);
		tv_quecontent.setText(list.get(position).get("QUE_CONTENT").toString());
		if ((Integer) list.get(position).get("QUE_STATE") == 0) {
			tv_questate.setText("未解决");
		} else if ((Integer) list.get(position).get("QUE_STATE") == 1) {
			tv_questate.setText("已解决");
		}
		return view;
	}

}
