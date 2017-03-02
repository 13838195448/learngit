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
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class CourseAdapter extends BaseAdapter {
	

	private Context context;
	private List<Map<String, Object>> list;
	private DisplayImageOptions options;
	public CourseAdapter(Context context,List<Map<String, Object>> list) {
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
			view=LayoutInflater.from(context).inflate(R.layout.item_kecheng, null);
			
			ImageView iv_listkecheng_img=(ImageView) view.findViewById(R.id.iv_listkecheng_img);
			ImageView iv_kecheng_buyway=(ImageView) view.findViewById(R.id.iv_kecheng_buyway);
			TextView tv_listkecheng_context=(TextView) view.findViewById(R.id.tv_listkecheng_context);
			TextView tv_listkecheng_scan=(TextView) view.findViewById(R.id.tv_listkecheng_scan);
			TextView tv_listkecheng_cost=(TextView) view.findViewById(R.id.tv_listkecheng_cost);
			
			viewHolder = new ViewHolder(iv_listkecheng_img,iv_kecheng_buyway, tv_listkecheng_context,tv_listkecheng_scan,tv_listkecheng_cost);
            view.setTag(viewHolder);
		}else{
			view = convertView;
            viewHolder = (ViewHolder) view.getTag();
		}
		
		viewHolder.tv_listkecheng_context.setText(list.get(arg0).get("context").toString());
		viewHolder.tv_listkecheng_scan.setText(list.get(arg0).get("scan").toString());
		viewHolder.tv_listkecheng_cost.setText(list.get(arg0).get("cost").toString());
		
		if(list.get(arg0).get("BuyWay").toString().equals("1")){
			viewHolder.iv_kecheng_buyway.setImageResource(R.drawable.me_icon_le);
		}else if(list.get(arg0).get("BuyWay").toString().equals("2")){
			viewHolder.iv_kecheng_buyway.setImageResource(R.drawable.me_icon_jin);
		}
		if (list.get(arg0).get("PicUrl").toString().equals("")||list.get(arg0).get("PicUrl")==null) {
			viewHolder.iv_listkecheng_img.setImageResource(R.drawable.defaultimage);
		}else{
			//AsyncBitmapLoader.setImage(viewHolder.iv_listkecheng_img, Setting.apiUrl+list.get(arg0).get("PicUrl").toString());
			ImageLoader imageLoader = ImageLoader.getInstance();
imageLoader.displayImage( Setting.apiUrl+list.get(arg0).get("PicUrl").toString(), viewHolder.iv_listkecheng_img, options);
		
		}
		
		return view;
	}
	
	class ViewHolder {
	     
	    public ViewHolder(ImageView iv_listkecheng_img, ImageView iv_kecheng_buyway,TextView tv_listkecheng_context, TextView tv_listkecheng_scan, TextView tv_listkecheng_cost){
	        this.iv_listkecheng_img = iv_listkecheng_img;
	        this.iv_kecheng_buyway=iv_kecheng_buyway;
	        this.tv_listkecheng_context = tv_listkecheng_context;
	        this.tv_listkecheng_scan = tv_listkecheng_scan;
	        this.tv_listkecheng_cost = tv_listkecheng_cost;
	    }       
	    
	    ImageView iv_listkecheng_img,iv_kecheng_buyway;
	    TextView tv_listkecheng_context,tv_listkecheng_scan,tv_listkecheng_cost;
	}


}
