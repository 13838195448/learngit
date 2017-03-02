package com.mpyf.lening.activity.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.AsyncBitmapLoader;
import com.mpyf.lening.Jutil.ImageOptions;
import com.mpyf.lening.activity.activity.NLRZActivity;
import com.mpyf.lening.activity.activity.NewsinfoActivity;
import com.mpyf.lening.activity.activity.NotbuycourseActivity;
import com.mpyf.lening.activity.activity.PeixundetilActivity;
import com.mpyf.lening.activity.activity.ZhuantiActivity;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class Fragment_Lunbo extends Fragment {
	
	private int type;//1培训，2专题，3资讯，4课程，5 认证
	private String pic_url;
	private String parame;
	private ImageView iv_maintop;
	private DisplayImageOptions options;
	
	public Fragment_Lunbo(){
		
	}
	
	public Fragment_Lunbo(int type,String pic_url,String parame){
		this.type=type;
		this.pic_url=pic_url;
		this.parame=parame;
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
		showinfo();
		setchange();
		return view;
	};
	
	private void showinfo(){
		if (!TextUtils.isEmpty(pic_url)){
//			AsyncBitmapLoader.sethoneImage(iv_maintop, Setting.apiUrl+pic_url);
		ImageLoader imageLoader = ImageLoader.getInstance();
imageLoader.displayImage(Setting.apiUrl+pic_url, iv_maintop, options);
		
		}
		
	}
	
	private void setchange(){
		iv_maintop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				switch (type) {
				case 1:
					intent.setClass(getActivity(), PeixundetilActivity.class);
					intent.putExtra("trainID", parame);
					break;
				case 2:
					intent.setClass(getActivity(), ZhuantiActivity.class);
					break;
				case 3:
					intent.setClass(getActivity(), NewsinfoActivity.class);
					intent.putExtra("id", parame);
					break;
				case 4:
					intent.setClass(getActivity(), NotbuycourseActivity.class);
					intent.putExtra("id", parame);
					break;
				case 5:
					intent.setClass(getActivity(), NLRZActivity.class);
					break;
				default:
					break;
				}
				
				startActivity(intent);
			}
		});
		
	}
	
}
