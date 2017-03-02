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
import android.widget.RatingBar;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.AsyncBitmapLoader;
import com.mpyf.lening.Jutil.ImageOptions;
import com.mpyf.lening.Jutil.Roundbitmap;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class MingshiAdapter extends BaseAdapter {

	private List<Map<String, Object>> list;
	private Context context;
	private DisplayImageOptions options;

	public MingshiAdapter(Context context, List<Map<String, Object>> list) {
		this.context = context;
		this.list = list;
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.icon_defualt) 
		.showImageOnFail(R.drawable.icon_defualt)
		.showImageForEmptyUri(R.drawable.icon_defualt)
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
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.item_mingshi, null);

		int[] levels={R.drawable.vi,R.drawable.vii,R.drawable.viii,R.drawable.viv,R.drawable.vv,R.drawable.vvi,R.drawable.vvii,R.drawable.vviii,R.drawable.vix,};
		
		
		ImageView iv_mingshi_photo=(ImageView) view.findViewById(R.id.iv_mingshi_photo);
		ImageView iv_mingshi_level=(ImageView) view.findViewById(R.id.iv_mingshi_level);
		TextView tv_mingshi_name=(TextView) view.findViewById(R.id.tv_mingshi_name);
		TextView tv_mingshi_context=(TextView) view.findViewById(R.id.tv_mingshi_context);
		
		tv_mingshi_name.setText(list.get(arg0).get("name").toString());
		tv_mingshi_context.setText(list.get(arg0).get("signature").toString());
		//AsyncBitmapLoader.setRoundImage(iv_mingshi_photo, Setting.apiUrl+list.get(arg0).get("picUrl").toString());
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(Setting.apiUrl+list.get(arg0).get("picUrl").toString(), iv_mingshi_photo, options);
		iv_mingshi_level.setImageResource(levels[(Integer)list.get(arg0).get("Lec_Level")-1]);
		
		return view;
	}

}
