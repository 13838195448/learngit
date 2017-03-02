package com.mpyf.lening.activity.adapter;

import java.util.List;

import com.mpyf.lening.R;
import com.mpyf.lening.activity.activity.NotbuycourseActivity;
import com.mpyf.lening.interfaces.bean.Result.Course;
import com.mpyf.lening.interfaces.bean.Result.ProColumn;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ZhuantiDtatilAdapter extends BaseAdapter {

	private List<ProColumn> list;
	private Context context;
	private String id;
	private int j;
	public ZhuantiDtatilAdapter(Context context,List<ProColumn> columnList) {
		list = columnList;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		View view = View.inflate(context, R.layout.item_activity_zhuntidetail, null);
		TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
		TextView tv_detail_remark = (TextView) view.findViewById(R.id.tv_detail_remark);
		LinearLayout ll_add = (LinearLayout) view.findViewById(R.id.ll_add);
		
		tv_title.setText(list.get(arg0).col_Name);
		tv_detail_remark.setText(list.get(arg0).remark);
		final List<Course> courselist = list.get(arg0).col_course;
		for (int i = 0; i < courselist.size(); i++) {
			
			View v = View.inflate(context, R.layout.item_linearlayout, null);
			
			TextView tv_detail_title = (TextView) v.findViewById(R.id.tv_detail_title);
			TextView tv_keyword = (TextView) v.findViewById(R.id.tv_keyword);
			
			tv_detail_title.setText(courselist.get(i).courseName);
			tv_keyword.setText("¹Ø¼ü´Ê: "+courselist.get(i).remark);
			ll_add.addView(v);
			id = courselist.get(i).PK_Course;
			v.setTag(id);
			
			v.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(context,NotbuycourseActivity.class);
					intent.putExtra("id", arg0.getTag().toString());
					context.startActivity(intent);
				}
			});
			
			
		}
			
		
		
		return view;
	}

}
