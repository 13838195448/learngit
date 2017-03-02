package com.mpyf.lening.activity.adapter;

import java.util.List;
import java.util.Map;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.AsyncBitmapLoader;
import com.mpyf.lening.Jutil.ImageOptions;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PhcrouseAdapter extends BaseAdapter {

	private List<Map<String, Object>> list;
	private Context context;
	private DisplayImageOptions options;

	public PhcrouseAdapter(Context context, List<Map<String, Object>> list) {
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
		.build(); 
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
	public View getView(int position, View converView, ViewGroup arg2) {

		View view = View.inflate(context, R.layout.item_phcrouse, null);

		ImageView iv_pic = (ImageView) view.findViewById(R.id.iv_pic);

		TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
		TextView tv_teacher = (TextView) view
				.findViewById(R.id.tv_teacher);
		TextView tv_buy_num = (TextView) view
				.findViewById(R.id.tv_buy_num);
		TextView tv_num = (TextView) view.findViewById(R.id.tv_num);

//		AsyncBitmapLoader.setImage(iv_pic, Setting.apiUrl
//				+ list.get(position).get("PicUrl").toString());
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(Setting.apiUrl	+ list.get(position).get("PicUrl").toString(), iv_pic, options);
		tv_title.setText(list.get(position).get("title").toString());
		tv_teacher.setText("½²Ê¦£º "
				+ list.get(position).get("teacher").toString());
		tv_buy_num.setText("¹ºÂòÈËÊý £º "
				+ list.get(position).get("scan").toString());
		tv_num.setText("" + (position + 1));

		return view;
	}

}
