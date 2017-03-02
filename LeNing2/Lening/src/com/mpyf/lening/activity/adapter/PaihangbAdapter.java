package com.mpyf.lening.activity.adapter;

import java.util.List;
import java.util.Map;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.AsyncBitmapLoader;
import com.mpyf.lening.Jutil.ImageOptions;
import com.mpyf.lening.Jutil.Roundbitmap;
import com.mpyf.lening.activity.adapter.CommentAdapter.ViewHolder;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PaihangbAdapter extends BaseAdapter {

	private List<Map<String, Object>> list;
	private Context context;
	private DisplayImageOptions options;

	public PaihangbAdapter(Context context, List<Map<String, Object>> list) {
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
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View view;

			view = LayoutInflater.from(context).inflate(
					R.layout.item_paihangbang, null);
			RelativeLayout rl_itemphb_bg=(RelativeLayout) view.findViewById(R.id.rl_itemphb_bg);
			ImageView iv_itemphb_user=(ImageView) view.findViewById(R.id.iv_itemphb_user);
			TextView tv_itemphb_name=(TextView) view.findViewById(R.id.tv_itemphb_name);
			TextView tv_itemphb_srcoe=(TextView) view.findViewById(R.id.tv_itemphb_srcoe);
			TextView tv_itemphb_paiming=(TextView) view.findViewById(R.id.tv_itemphb_paiming);
			
		
		rl_itemphb_bg.setBackgroundColor(view.getResources().getColor(android.R.color.white));
		tv_itemphb_paiming.getPaint().setFakeBoldText(true);
		if(arg0<3){
			tv_itemphb_paiming.setText("NO."+(arg0+1));
			if(arg0==0){
				rl_itemphb_bg.setBackgroundResource(R.drawable.text_bg);
				tv_itemphb_name.setTextColor(view.getResources().getColor(android.R.color.white));
				tv_itemphb_srcoe.setTextColor(view.getResources().getColor(android.R.color.white));
				tv_itemphb_paiming.setTextColor(view.getResources().getColor(android.R.color.white));
			}else if (arg0==1) {
				tv_itemphb_paiming.setTextColor(view.getResources().getColor(android.R.color.holo_blue_bright));
			}else if (arg0==2) {
				tv_itemphb_paiming.setTextColor(view.getResources().getColor(android.R.color.holo_orange_light));
			}
		}else{
			tv_itemphb_paiming.setText((arg0+1)+"");
		}
		
		//	AsyncBitmapLoader.setRoundImage(iv_itemphb_user, Setting.apiUrl+"new-pages/PersonalPhoto/"+list.get(arg0).get("image").toString()+".jpg");
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(Setting.apiUrl+"new-pages/PersonalPhoto/"+list.get(arg0).get("image").toString()+".jpg", iv_itemphb_user,options);
		
		
		tv_itemphb_name.setText(list.get(arg0).get("name").toString());
		tv_itemphb_srcoe.setText("ังทึ: "+list.get(arg0).get("scroe").toString());
		return view;
	}

}
