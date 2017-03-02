package com.mpyf.lening.activity.adapter;

import java.util.List;
import java.util.Map;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.FlexibleRoundedBitmapDisplayer;
import com.mpyf.lening.interfaces.bean.Result.Project;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ZhuantiAdapter extends BaseAdapter {

	private Context context;
	private List<Project>  list;
	private DisplayImageOptions options;
	public ZhuantiAdapter(Context context,List<Project> list){
		this.context = context;
		this.list = list;
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.defaultimage) 
		.showImageOnFail(R.drawable.defaultimage)
		.showImageForEmptyUri(R.drawable.defaultimage)
		.cacheInMemory(true) 
		.cacheOnDisk(true) 
		 .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
        .bitmapConfig(Bitmap.Config.ALPHA_8)
        .displayer(new FlexibleRoundedBitmapDisplayer(20, 1|2))
		.build(); 
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View converView, ViewGroup arg2) {
		
		
			View view = View.inflate(context, R.layout.item_zhuti, null);
			
		ImageView iv_peixun_top = (ImageView) view.findViewById(R.id.iv_peixun_top);
		TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
		TextView tv_remark = (TextView) view.findViewById(R.id.tv_remark);
		
		tv_title.setText(list.get(position).pro_Name);
		tv_remark.setText(list.get(position).remark);
		
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(Setting.apiUrl+list.get(position).pro_pic_url, iv_peixun_top, options);
		
		return view;
	}

}
