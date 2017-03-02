package com.mpyf.lening.activity.adapter;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import com.mpyf.lening.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QiandaoinfoAdapter extends BaseAdapter {

	private List<Map<String,Object>> list;
	private Context context;
	
	public QiandaoinfoAdapter(Context context,List<Map<String,Object>> list){
		this.context=context;
		this.list=list;
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
		View view = LayoutInflater.from(context).inflate(
				R.layout.item_mypeixundetil, null);
		String[] qujian={"上午","下午","晚上"};
		String[] zhuangtai={"正常出勤","缺勤","迟到"};
		
		LinearLayout ll_mypeixundetil_top=(LinearLayout) view.findViewById(R.id.ll_mypeixundetil_top);
		TextView tv_mypeixuninfo_date=(TextView) view.findViewById(R.id.tv_mypeixuninfo_date);
		TextView tv_mypeixundetil_qujian=(TextView) view.findViewById(R.id.tv_mypeixundetil_qujian);
		TextView tv_mypeixundetil_qujiantime=(TextView) view.findViewById(R.id.tv_mypeixundetil_qujiantime);
		TextView tv_mypeixundetil_isqiandao=(TextView) view.findViewById(R.id.tv_mypeixundetil_isqiandao);
		TextView tv_mypeixundetil_qiandaotime=(TextView) view.findViewById(R.id.tv_mypeixundetil_qiandaotime);
		
		tv_mypeixuninfo_date.setText(list.get(arg0).get("att_date").toString());
		tv_mypeixundetil_qujian.setText(qujian[(Integer)list.get(arg0).get("att_type")]);
		tv_mypeixundetil_qujiantime.setText(list.get(arg0).get("att_datetime_s").toString()+"-"+list.get(arg0).get("att_datetime_e").toString());
		tv_mypeixundetil_isqiandao.setText(zhuangtai[(Integer)list.get(arg0).get("att_ance")]);
		tv_mypeixundetil_qiandaotime.setText("签到时间："+list.get(arg0).get("att_datetime").toString());
		
		if(arg0!=0&&list.get(arg0).get("att_date").toString().equals(list.get(arg0-1).get("att_date").toString())){
			ll_mypeixundetil_top.setVisibility(View.GONE);
		}
		
		return view;
	}

	private String fromattime(String time){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
		Date date=null;
		SimpleDateFormat fromat=new SimpleDateFormat("HH:mm");
		try {
			date = df.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return fromat.format(date);
		
	}
}
