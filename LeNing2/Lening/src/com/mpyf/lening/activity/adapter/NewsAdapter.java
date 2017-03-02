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

public class NewsAdapter extends BaseAdapter {
	

	private Context context;
	private List<Map<String, Object>> list;
	public NewsAdapter(Context context,List<Map<String, Object>> list) {
		this.context=context;
		this.list=list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		View view;
		ViewHolder viewHolder;

		if(null==convertView  ){
			view=LayoutInflater.from(context).inflate(R.layout.item_news1, null);
			
			TextView tv_newsitem_title=(TextView) view.findViewById(R.id.tv_newsitem_title);
			TextView tv_newsitem_time=(TextView) view.findViewById(R.id.tv_newsitem_time);
			
			viewHolder = new ViewHolder(tv_newsitem_title, tv_newsitem_time);
            view.setTag(viewHolder);
		}else{
			view = convertView;
            viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.tv_newsitem_title.setText(list.get(arg0).get("title").toString());
		viewHolder.tv_newsitem_time.setText(list.get(arg0).get("time").toString());
		return view;
	}
	
	class ViewHolder {
		
		TextView tv_newsitem_title,tv_newsitem_time;
		
	    public ViewHolder(TextView tv_newsitem_title, TextView tv_newsitem_time){
	        this.tv_newsitem_title = tv_newsitem_title;
	        this.tv_newsitem_time = tv_newsitem_time;
	    }       
	    
	    
	}


}
