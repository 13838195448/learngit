package com.mpyf.lening.activity.adapter;

import java.util.List;

import com.mpyf.lening.R;
import com.mpyf.lening.interfaces.bean.Result.ShareRes;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ShareResAdapter extends BaseAdapter {
	private Context context;
	private List<ShareRes> list;

	public ShareResAdapter(Context context, List<ShareRes> list) {

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
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {

		View view = View.inflate(context, R.layout.item_sharer_es, null);
		
		TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
		TextView tv_num = (TextView) view.findViewById(R.id.tv_num);
		TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
		TextView tv_cost = (TextView) view.findViewById(R.id.tv_cost);
		
		tv_name.setText(list.get(arg0).shareName);
		tv_num.setText(list.get(arg0).buyNum+"");
		tv_time.setText(list.get(arg0).onlineTime);
		
		int state = list.get(arg0).buyWay;
		
		if(state==1){
			tv_cost.setText(list.get(arg0).amount+"ÀÖ±Ò");
		}else if(state==2){
			tv_cost.setText(list.get(arg0).amount+"½ð±Ò");
		}
	
		
		
		
		return view;
	}

}
