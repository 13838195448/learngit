package com.mpyf.lening.activity.adapter;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.AsyncBitmapLoader;
import com.mpyf.lening.Jutil.ImageOptions;
import com.mpyf.lening.activity.activity.Qa_photoActivity;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AgridviewAdapter extends BaseAdapter {

	private int[] arr;
	private Context context;
	
	private String a;
	private DisplayImageOptions options;
	
	public AgridviewAdapter(Context context, int[] arr, String a) {
		this.context = context;
		this.arr = arr;
		this.a =a;
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
		return arr.length;
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
	public View getView(int arg0, View converView, ViewGroup arg2) { 
		  
		ViewHolder holder ;
	//	if(converView==null){
			 holder = new ViewHolder();
			converView= View.inflate(context, R.layout.gridview_pic, null);
			holder. iv_mypic = (ImageView) converView.findViewById(R.id.iv_mypic);
			converView.setTag(holder);
//		}else{
//			holder = (ViewHolder) converView.getTag();
//		}
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(Setting.apiUrl+"new-pages/QA/"+a+"/"+arr[arg0]+".jpg", holder.iv_mypic,options);
		final String s =Setting.apiUrl+"new-pages/QA/"+a+"/"+arr[arg0]+".jpg";
		holder.iv_mypic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context,Qa_photoActivity.class);
				intent.putExtra("url", s);
				context.startActivity(intent);
			}
		});
		//AsyncBitmapLoader.setImage(holder.iv_mypic, Setting.apiUrl+"new-pages/QA/"+a+"/"+arr[arg0]+".jpg");
		return converView;
	}
	class ViewHolder{
		public ImageView iv_mypic;
	}

}
