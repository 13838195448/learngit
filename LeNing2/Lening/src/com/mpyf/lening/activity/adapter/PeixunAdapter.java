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
import com.mpyf.lening.Jutil.AsyncBitmapLoader;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PeixunAdapter extends BaseAdapter {
	
	private DisplayImageOptions options;
	private Context context;
	private List<Map<String, Object>> list;
	public PeixunAdapter(Context context,List<Map<String, Object>> list, DisplayImageOptions options) {
		this.context=context;
		this.list=list;
		this.options=options;
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
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		View view;
		
			view=LayoutInflater.from(context).inflate(R.layout.item_peixun, null);
			
			ImageView iv_peixun_top=(ImageView) view.findViewById(R.id.iv_peixun_top);
			TextView tv_peixun_title=(TextView) view.findViewById(R.id.tv_peixun_title);
			TextView tv_peixun_date=(TextView) view.findViewById(R.id.tv_peixun_date);
			TextView tv_peixun_time=(TextView) view.findViewById(R.id.tv_peixun_time);
			TextView tv_peixun_le=(TextView) view.findViewById(R.id.tv_peixun_le);
			TextView tv_peixun_member=(TextView) view.findViewById(R.id.tv_peixun_member);
			TextView tv_peixun_place=(TextView) view.findViewById(R.id.tv_peixun_place);
			
		
		tv_peixun_title.setText(list.get(arg0).get("title").toString());
		tv_peixun_date.setText(list.get(arg0).get("date").toString());
		
		String time=list.get(arg0).get("time").toString();
		if(time.length()>=10){
			time=time.substring(0, 10);
			time=time.replace("-", "/");
		}
		tv_peixun_time.setText("报名截止日期:"+time);
		tv_peixun_le.setText(list.get(arg0).get("le").toString()+"乐币");
		tv_peixun_member.setText(list.get(arg0).get("num").toString()+"人");
		tv_peixun_place.setText(list.get(arg0).get("place").toString());
		
			//AsyncBitmapLoader.setImage(iv_peixun_top, Setting.apiUrl+list.get(arg0).get("picUrl").toString());
			
			ImageLoader imageLoader = ImageLoader.getInstance();
			imageLoader.displayImage( Setting.apiUrl+list.get(arg0).get("picUrl").toString(), iv_peixun_top, options);
			iv_peixun_top.setAlpha(180);
		
		return view;
	}

}
