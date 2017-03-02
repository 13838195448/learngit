package com.mpyf.lening.activity.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.AsyncBitmapLoader;
import com.mpyf.lening.Jutil.ImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class MyCourseAdapter extends BaseAdapter {
	

	private Context context;
	private List<Map<String, Object>> list;
	private DisplayImageOptions options;
	public MyCourseAdapter(Context context,List<Map<String, Object>> list) {
		this.context=context;
		this.list=list;
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.defaultimage) 
		.showImageOnFail(R.drawable.defaultimage)
		.showImageForEmptyUri(R.drawable.defaultimage)
		.cacheInMemory(true) 
		.cacheOnDisk(true) 
		 .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
        .bitmapConfig(Bitmap.Config.ALPHA_8)
		.build(); 
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
			view=LayoutInflater.from(context).inflate(R.layout.item_mycourse, null);
			
			ImageView iv_itemmycourse_photo=(ImageView) view.findViewById(R.id.iv_itemmycourse_photo);
			TextView iv_itemmycourse_title=(TextView) view.findViewById(R.id.iv_itemmycourse_title);
			TextView iv_itemmycourse_yixue=(TextView) view.findViewById(R.id.iv_itemmycourse_yixue);
			TextView iv_itemmycourse_youxiao=(TextView) view.findViewById(R.id.iv_itemmycourse_youxiao);
			
			viewHolder = new ViewHolder(iv_itemmycourse_photo, iv_itemmycourse_title,iv_itemmycourse_yixue,iv_itemmycourse_youxiao);
            view.setTag(viewHolder);
		}else{
			view = convertView;
            viewHolder = (ViewHolder) view.getTag();
		}
	//	AsyncBitmapLoader.setImage(viewHolder.iv_itemmycourse_photo, list.get(arg0).get("picUrl").toString());
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(list.get(arg0).get("picUrl").toString(), viewHolder.iv_itemmycourse_photo, options);
		
		viewHolder.iv_itemmycourse_title.setText(list.get(arg0).get("title").toString());
		viewHolder.iv_itemmycourse_yixue.setText(list.get(arg0).get("time").toString());
		if((Integer)list.get(arg0).get("date")<=0){
			viewHolder.tv_listkecheng_cost.setText("已过期");
		}else{
			viewHolder.tv_listkecheng_cost.setText("距离有效天数   "+list.get(arg0).get("date").toString());
		}
		return view;
	}
	
	class ViewHolder {
	     
	    public ViewHolder(ImageView iv_itemmycourse_photo, TextView iv_itemmycourse_title, TextView iv_itemmycourse_yixue, TextView tv_listkecheng_cost){
	        this.iv_itemmycourse_photo = iv_itemmycourse_photo;
	        this.iv_itemmycourse_title = iv_itemmycourse_title;
	        this.iv_itemmycourse_yixue = iv_itemmycourse_yixue;
	        this.tv_listkecheng_cost = tv_listkecheng_cost;
	    }       
	    
	    ImageView iv_itemmycourse_photo;
	    TextView iv_itemmycourse_title,iv_itemmycourse_yixue,tv_listkecheng_cost;
	}


}
