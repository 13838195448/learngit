package com.mpyf.lening.activity.fragment;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.AsyncBitmapLoader;
import com.mpyf.lening.Jutil.ImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

public class Fragment_rzlb extends Fragment {

	private int type;//1培训，2专题，3资讯，4课程，5 认证
	private String pic_url;
	private ImageView iv_maintop;
	private DisplayImageOptions options;
	
	public Fragment_rzlb(String pic_url){
		this.pic_url=pic_url;
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
	
	public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, Bundle savedInstanceState) {
		View  view=inflater.inflate(R.layout.fragment_main, null);
		iv_maintop=(ImageView) view.findViewById(R.id.iv_maintop);
		//AsyncBitmapLoader.sethoneImage(iv_maintop, pic_url);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(pic_url, iv_maintop, options);
		return view;
	};
}
