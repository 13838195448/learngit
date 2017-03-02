package com.mpyf.lening.activity.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpyf.lening.R;

public class KcflAdapter extends BaseAdapter {
	

	private Context context;
	private List<Map<String, Object>> list;
	public KcflAdapter(Context context,List<Map<String, Object>> list) {
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
			view=LayoutInflater.from(context).inflate(R.layout.item_kcfl, null);
			
			TextView tv_data_title=(TextView) view.findViewById(R.id.tv_kcfl_title);
			ImageView iv_kcfl_make=(ImageView) view.findViewById(R.id.iv_kcfl_make);
			
			
			viewHolder = new ViewHolder(iv_kcfl_make, tv_data_title);
            view.setTag(viewHolder);
		}else{
			view = convertView;
            viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.tv_kcfl_title.setText(list.get(arg0).get("title").toString());
		
		if(arg0==0){
			viewHolder.iv_kcfl_make.setBackgroundColor(view.getResources().getColor(
					R.color.main));
			viewHolder.tv_kcfl_title.setBackgroundColor(view.getResources().getColor(
					android.R.color.white));
		}
		
		return view;
	}
	
	class ViewHolder {
	     
	    public ViewHolder(ImageView iv_kcfl_make, TextView tv_kcfl_title){
	        this.iv_kcfl_make = iv_kcfl_make;
	        this.tv_kcfl_title = tv_kcfl_title;
	    }       
	    
	    ImageView iv_kcfl_make;
	    TextView tv_kcfl_title;
	}


}
