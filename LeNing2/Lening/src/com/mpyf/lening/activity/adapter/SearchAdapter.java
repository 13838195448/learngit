package com.mpyf.lening.activity.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Roundbitmap;
import com.mpyf.lening.interfaces.http.Setting;

public class SearchAdapter extends BaseAdapter {

	private List<Map<String, Object>> list;
	private Context context;

	public SearchAdapter(Context context, List<Map<String, Object>> list) {
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
				R.layout.item_search, null);


		ImageView iv_item_search = (ImageView) view.findViewById(R.id.iv_item_search);
		TextView tv_item_search = (TextView) view
				.findViewById(R.id.tv_item_search);
		
		if(list.get(arg0).get("type").toString().equals("1")){
			iv_item_search.setImageResource(R.drawable.seek_icon_course);
		}else if (list.get(arg0).get("type").toString().equals("2")) {
			iv_item_search.setImageResource(R.drawable.seek_btn_train);
		}else if (list.get(arg0).get("type").toString().equals("3")) {
			iv_item_search.setImageResource(R.drawable.seek_btn_qa);
		}

		tv_item_search.setText(list.get(arg0).get("context").toString());
		
		return view;
	}

}
