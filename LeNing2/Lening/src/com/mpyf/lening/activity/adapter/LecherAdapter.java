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

public class LecherAdapter extends BaseAdapter {
	

	private Context context;
	private List<Map<String, Object>> list;
	private DisplayImageOptions options;
	public LecherAdapter(Context context,List<Map<String, Object>> list) {
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

		int[] levels={R.drawable.vi,R.drawable.vii,R.drawable.viii,R.drawable.viv,R.drawable.vv,R.drawable.vvi,R.drawable.vvii,R.drawable.vviii,R.drawable.vix,};
		
			view=LayoutInflater.from(context).inflate(R.layout.item_lecher, null);
			
			ImageView iv_itemlecher_img=(ImageView) view.findViewById(R.id.iv_itemlecher_img);
			ImageView iv_itemlecher_level=(ImageView) view.findViewById(R.id.iv_itemlecher_level);
			TextView tv_itemlecher_name=(TextView) view.findViewById(R.id.tv_itemlecher_name);
		
			tv_itemlecher_name.setText(list.get(arg0).get("name").toString());
		//AsyncBitmapLoader.setImage(iv_itemlecher_img, Setting.apiUrl+list.get(arg0).get("picUrl").toString());
			ImageLoader imageLoader = ImageLoader.getInstance();
			imageLoader.displayImage(Setting.apiUrl+list.get(arg0).get("picUrl").toString(), iv_itemlecher_img, options);
			
		iv_itemlecher_level.setImageResource(levels[(Integer)list.get(arg0).get("Lec_Level")-1]);
		return view;
	}

}
