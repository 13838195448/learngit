package com.mpyf.lening.activity.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mpyf.lening.R;

public class MoneyAdapter extends BaseAdapter {

	private List<Map<String, Object>> list;
	private Context context;

	public MoneyAdapter(Context context, List<Map<String, Object>> list) {
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
				R.layout.item_golditem, null);
		
		RelativeLayout rl_golditem_month=(RelativeLayout) view.findViewById(R.id.rl_golditem_month);
		TextView tv_golditem_month=(TextView) view.findViewById(R.id.tv_golditem_month);
		TextView tv_golditem_week=(TextView) view.findViewById(R.id.tv_golditem_week);
		TextView tv_golditem_date=(TextView) view.findViewById(R.id.tv_golditem_date);
		ImageView iv_golditem_type=(ImageView) view.findViewById(R.id.iv_golditem_type);
		TextView tv_golditem_num=(TextView) view.findViewById(R.id.tv_golditem_num);
		TextView tv_golditem_rules=(TextView) view.findViewById(R.id.tv_golditem_rules);

		tv_golditem_month.setText(getmonth(list.get(arg0).get("time").toString()));
		
		if(arg0>0){
			if(getmonth(list.get(arg0).get("time").toString()).equals(getmonth(list.get(arg0-1).get("time").toString()))){
				rl_golditem_month.setVisibility(View.GONE);
			}
		}
		
//		tv_golditem_week.setText(getFullDateWeekTime(list.get(arg0).get("time").toString()));
		tv_golditem_week.setText(getday(list.get(arg0).get("time").toString()));
		
		
		if(list.get(arg0).get("type").toString().equals("le")){
			iv_golditem_type.setImageResource(R.drawable.me_asset_icon_le);
		}else if(list.get(arg0).get("type").toString().equals("gold")){
			iv_golditem_type.setImageResource(R.drawable.me_asset_icon_jin);
		}
		
		if(tv_golditem_week.getText().toString().equals("今天")||tv_golditem_week.getText().toString().equals("昨天")){
			
			tv_golditem_date.setText(formatdaytime(list.get(arg0).get("time").toString()));
		}else{
			tv_golditem_date.setText(formattime(list.get(arg0).get("time").toString()));
		}
		
		
		if((Integer)list.get(arg0).get("shouzhi")==0||(Integer)list.get(arg0).get("shouzhi")==2){
			tv_golditem_num.setText("+"+list.get(arg0).get("Num").toString());
		}else{
			tv_golditem_num.setText("-"+list.get(arg0).get("Num").toString());
		}
		
		tv_golditem_rules.setText(list.get(arg0).get("Rules").toString());
		
		return view;
	}
	
	private String formattime(String time){
		if(time.indexOf(" ")!=-1){
			time=time.substring(time.indexOf("-")+1,time.indexOf(" "));
		}
		
		return time;
	}
	
	private String formatdaytime(String time){
		if(time.indexOf(" ")!=-1){
			time=time.substring(time.indexOf(" ")+1,time.lastIndexOf(":"));
		}
		
		return time;
	}
	
	private String getmonth(String sDate){
		try{
			String formater = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat format = new SimpleDateFormat(formater);
			Date date=format.parse(sDate);
			Date today=new Date();
			if (today.getMonth()==date.getMonth()) {
				return "本月";
			}
			return (date.getMonth()+1)+"月";
			}catch(Exception ex){
			System.out.println("TimeUtil getFullDateWeekTime"+ex.getMessage());
			return "";
			}
			
	}

	public static String getFullDateWeekTime(String sDate){
		try{
		String formater = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat format = new SimpleDateFormat(formater);
		Date date=format.parse(sDate);
		format.applyPattern("E");
		return format.format(date);
		}catch(Exception ex){
		System.out.println("TimeUtil getFullDateWeekTime"+ex.getMessage());
		return "";
		}
		}
	
	public static String getday(String sDate){
		try{
			String formater = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat format = new SimpleDateFormat(formater);
			Date date=format.parse(sDate);
			Date today=new Date();
			
			if(today.getMonth()==date.getMonth()){
				if(today.getDate()==date.getDate()){
					return "今天";
				}else if(today.getDate()-date.getDate()==1){
					return "昨天";
				}
			}
			
			format.applyPattern("E");
			return format.format(date);
			}catch(Exception ex){
			System.out.println("TimeUtil getFullDateWeekTime"+ex.getMessage());
			return "";
			}
	}
}
