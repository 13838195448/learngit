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
import com.mpyf.lening.activity.adapter.MyCommentAdapter.ViewHolder;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class CommentAdapter extends BaseAdapter {

	private List<Map<String, Object>> list;
	private Context context;
	private DisplayImageOptions options;

	public CommentAdapter(Context context, List<Map<String, Object>> list) {
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
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		View view;
		ViewHolder viewHolder;

		if(null==convertView  ){
			view = LayoutInflater.from(context).inflate(
					R.layout.item_comments, null);

			RatingBar rb_comments = (RatingBar) view.findViewById(R.id.rb_comments);

			ImageView im_comments = (ImageView) view.findViewById(R.id.im_comments);
			TextView tv_comments_name = (TextView) view
					.findViewById(R.id.tv_comments_name);
			TextView tv_comments_time = (TextView) view
					.findViewById(R.id.tv_comments_time);
			TextView tv_comments_context = (TextView) view
					.findViewById(R.id.tv_comments_context);
			
			
			
			viewHolder = new ViewHolder(rb_comments, im_comments,tv_comments_name,tv_comments_time,tv_comments_context);
            view.setTag(viewHolder);
		}else{
			view = convertView;
            viewHolder = (ViewHolder) view.getTag();
		}
		
		viewHolder.tv_comments_name.setMaxWidth(200);
		viewHolder.tv_comments_name.setSingleLine();
		
		if (list.get(arg0).get("ImagePath").toString() == null
				|| list.get(arg0).get("ImagePath").toString().equals("")) {
			viewHolder.im_comments.setImageBitmap(Roundbitmap.toRoundBitmap(viewHolder.im_comments));
			
		}else{
			//AsyncBitmapLoader.setRoundImage(viewHolder.im_comments,
					//Setting.apiUrl+"new-pages/PersonalPhoto/"+list.get(arg0).get("ImagePath").toString()+".jpg");
			ImageLoader imageLoader = ImageLoader.getInstance();
imageLoader.displayImage(Setting.apiUrl+"new-pages/PersonalPhoto/"+list.get(arg0).get("ImagePath").toString()+".jpg", viewHolder.im_comments, options);
		
		}
//		im_comments.setImageBitmap(Roundbitmap.toRoundBitmap(im_comments));

		viewHolder.tv_comments_name.setText(list.get(arg0).get("name").toString());
		viewHolder.tv_comments_time.setText(list.get(arg0).get("time").toString());
		viewHolder.tv_comments_context.setText(list.get(arg0).get("context").toString());
		int Score = (Integer) list.get(arg0).get("srcoe");

		viewHolder.rb_comments.setRating(Score);

		return view;
	}

	class ViewHolder {
	     
	    public ViewHolder(RatingBar rb_comments,ImageView im_comments,TextView tv_comments_name,TextView tv_comments_time,TextView tv_comments_context){
	    	this.rb_comments=rb_comments;
	    	this.im_comments=im_comments;
	    	this.tv_comments_name=tv_comments_name;
	    	this.tv_comments_time=tv_comments_time;
	    	this.tv_comments_context=tv_comments_context;
	    }       
	    
	    RatingBar rb_comments;
	    ImageView im_comments;
	    TextView tv_comments_name,tv_comments_time,tv_comments_context;
	}
}
