package com.mpyf.lening.activity.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mpyf.lening.R;

public class DayiAdapter extends BaseAdapter {
	

	private Context context;
	private List<Map<String, Object>> list;
	public DayiAdapter(Context context,List<Map<String, Object>> list) {
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
			view=LayoutInflater.from(context).inflate(R.layout.item_buyeddayi, null);
			
			TextView tv_buyeddayi_isanswered=(TextView) view.findViewById(R.id.tv_buyeddayi_isanswered);
			TextView tv_buyeddayi_context=(TextView) view.findViewById(R.id.tv_buyeddayi_context);
			TextView tv_buyeddayi_from=(TextView) view.findViewById(R.id.tv_buyeddayi_from);
			TextView tv_buyeddayi_answer=(TextView) view.findViewById(R.id.tv_buyeddayi_answer);
			
			LinearLayout ll_buyeddayi_answer=(LinearLayout) view.findViewById(R.id.ll_buyeddayi_answer);
			
			viewHolder = new ViewHolder(ll_buyeddayi_answer,tv_buyeddayi_isanswered,tv_buyeddayi_context,tv_buyeddayi_from,tv_buyeddayi_answer);
            view.setTag(viewHolder);
		}else{
			view = convertView;
            viewHolder = (ViewHolder) view.getTag();
		}
		
		viewHolder.tv_buyeddayi_context.setText("问题："+list.get(arg0).get("context").toString());
		if(list.get(arg0).get("from").toString().equals("")){
			viewHolder.tv_buyeddayi_from.setVisibility(View.GONE);
		}else{
			viewHolder.tv_buyeddayi_from.setText(list.get(arg0).get("from").toString());
		}
		
		viewHolder.tv_buyeddayi_answer.setText(list.get(arg0).get("answer").toString());
		
		if((Boolean) list.get(arg0).get("beAnswered")){
			viewHolder.tv_buyeddayi_isanswered.setText("已回答");
		}else {
			viewHolder.tv_buyeddayi_isanswered.setText("未回答");
			viewHolder.ll_buyeddayi_answer.setVisibility(View.GONE);
		}
		
		return view;
	}
	
	class ViewHolder {
	     
	    public ViewHolder(LinearLayout ll_buyeddayi_answer,TextView tv_buyeddayi_isanswered,TextView tv_buyeddayi_context,TextView tv_buyeddayi_from,TextView tv_buyeddayi_answer){
	    	this.ll_buyeddayi_answer=ll_buyeddayi_answer;
	    	this.tv_buyeddayi_isanswered = tv_buyeddayi_isanswered;
	        this.tv_buyeddayi_context=tv_buyeddayi_context;
	        this.tv_buyeddayi_from = tv_buyeddayi_from;
	        this.tv_buyeddayi_answer = tv_buyeddayi_answer;
	    }       
	    LinearLayout ll_buyeddayi_answer;
	    TextView tv_buyeddayi_isanswered,tv_buyeddayi_context,tv_buyeddayi_from,tv_buyeddayi_answer;
	}


}
