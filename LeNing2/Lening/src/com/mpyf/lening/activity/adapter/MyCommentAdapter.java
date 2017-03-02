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
import com.mpyf.lening.activity.adapter.MyCourseAdapter.ViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class MyCommentAdapter extends BaseAdapter {

	private List<Map<String, Object>> list;
	private Context context;
	private DisplayImageOptions options;

	public MyCommentAdapter(Context context, List<Map<String, Object>> list) {
		this.context = context;
		this.list = list;

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.icon_defualt)
				.showImageOnFail(R.drawable.icon_defualt)
				.showImageForEmptyUri(R.drawable.icon_defualt)
				.cacheInMemory(true).cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.ALPHA_8).build();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
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

		if (null == convertView) {
			view = LayoutInflater.from(context).inflate(
					R.layout.item_mycomments, null);

			RatingBar rb_mycomments = (RatingBar) view
					.findViewById(R.id.rb_mycomments);

			ImageView im_mycomments = (ImageView) view
					.findViewById(R.id.im_mycomments);
			TextView tv_mycomments_name = (TextView) view
					.findViewById(R.id.tv_mycomments_name);
			TextView tv_mycomments_time = (TextView) view
					.findViewById(R.id.tv_mycomments_time);
			TextView tv_mycomments_context = (TextView) view
					.findViewById(R.id.tv_mycomments_context);
			TextView tv_mycomments_from = (TextView) view
					.findViewById(R.id.tv_mycomments_from);

			viewHolder = new ViewHolder(rb_mycomments, im_mycomments,
					tv_mycomments_name, tv_mycomments_time,
					tv_mycomments_context, tv_mycomments_from);
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}

		// AsyncBitmapLoader.setRoundImage(viewHolder.im_mycomments,
		// list.get(arg0).get("ImagePath").toString());
		// im_mycomments.setImageBitmap(Roundbitmap.toRoundBitmap(im_mycomments));
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(list.get(arg0).get("ImagePath").toString(),
				viewHolder.im_mycomments, options);

		viewHolder.tv_mycomments_name.setText(list.get(arg0).get("name")
				.toString());
		viewHolder.tv_mycomments_time.setText(list.get(arg0).get("time")
				.toString());
		viewHolder.tv_mycomments_context.setText(list.get(arg0).get("context")
				.toString());
		viewHolder.tv_mycomments_from.setText(list.get(arg0).get("from")
				.toString());
		int Score = (Integer) list.get(arg0).get("srcoe");

		viewHolder.rb_mycomments.setRating(Score);

		return view;
	}

	class ViewHolder {

		public ViewHolder(RatingBar rb_mycomments, ImageView im_mycomments,
				TextView tv_mycomments_name, TextView tv_mycomments_time,
				TextView tv_mycomments_context, TextView tv_mycomments_from) {
			this.rb_mycomments = rb_mycomments;
			this.im_mycomments = im_mycomments;
			this.tv_mycomments_name = tv_mycomments_name;
			this.tv_mycomments_time = tv_mycomments_time;
			this.tv_mycomments_context = tv_mycomments_context;
			this.tv_mycomments_from = tv_mycomments_from;
		}

		RatingBar rb_mycomments;
		ImageView im_mycomments;
		TextView tv_mycomments_name, tv_mycomments_time, tv_mycomments_context,
				tv_mycomments_from;
	}

}
