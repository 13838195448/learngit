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
import com.mpyf.lening.activity.adapter.KcflAdapter.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class FenleidetilAdapter extends BaseAdapter {
	

	private Context context;
	private List<Map<String, Object>> list;
	private DisplayImageOptions options;
	public FenleidetilAdapter(Context context,List<Map<String, Object>> list) {
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

			view=LayoutInflater.from(context).inflate(R.layout.item_kcflxq, null);
			
			ImageView iv_kcflxq_photo,im_kcflxq_cost;
			TextView tv_kcflxq_title,tv_kcflxq_scan,tv_kcflxq_cost;
			
			iv_kcflxq_photo=(ImageView) view.findViewById(R.id.iv_kcflxq_photo);
			im_kcflxq_cost=(ImageView) view.findViewById(R.id.im_kcflxq_cost);
			tv_kcflxq_title=(TextView) view.findViewById(R.id.tv_kcflxq_title);
			tv_kcflxq_scan=(TextView) view.findViewById(R.id.tv_kcflxq_scan);
			tv_kcflxq_cost=(TextView) view.findViewById(R.id.tv_kcflxq_cost);
			
//			viewHolder = new ViewHolder(iv_kcflxq_photo, im_kcflxq_cost,tv_kcflxq_title,tv_kcflxq_scan,tv_kcflxq_cost);
//            view.setTag(viewHolder);
//		}else{
//			view = convertView;
//            viewHolder = (ViewHolder) view.getTag();
//		}
	//	AsyncBitmapLoader.setImage(iv_kcflxq_photo, list.get(arg0).get("image").toString());
			ImageLoader imageLoader = ImageLoader.getInstance();
imageLoader.displayImage(list.get(arg0).get("image").toString(), iv_kcflxq_photo, options);
			
			
			tv_kcflxq_title.setText(list.get(arg0).get("title").toString());
		tv_kcflxq_scan.setText(list.get(arg0).get("scan").toString());
		tv_kcflxq_cost.setText(list.get(arg0).get("cost").toString());
		
		if((Integer)list.get(arg0).get("buyWay")==1){
			im_kcflxq_cost.setImageResource(R.drawable.me_icon_le);
		}else if((Integer)list.get(arg0).get("buyWay")==2){
			im_kcflxq_cost.setImageResource(R.drawable.me_icon_jin);
		}
		
		return view;
	}
		
//	
//	class ViewHolder {
//	     
//	    public ViewHolder(ImageView iv_kcflxq_photo, ImageView im_kcflxq_cost,TextView tv_kcflxq_title, TextView tv_kcflxq_scan, TextView tv_kcflxq_cost){
//	        this.iv_kcflxq_photo = iv_kcflxq_photo;
//	        this.im_kcflxq_cost=im_kcflxq_cost;
//	        this.tv_kcflxq_title = tv_kcflxq_title;
//	        this.tv_kcflxq_scan = tv_kcflxq_scan;
//	        this.tv_kcflxq_cost = tv_kcflxq_cost;
//	    }       
//	    
//	    ImageView iv_kcflxq_photo,im_kcflxq_cost;
//	    TextView tv_kcflxq_title,tv_kcflxq_scan,tv_kcflxq_cost;
//	}

}
