package com.mpyf.lening.activity.adapter;

import java.util.List;
import java.util.Map;

import com.mpyf.lening.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class GoodsCommentAdapter extends BaseAdapter {

	private List<Map<String, Object>> list;
	private Context context;
	private DisplayImageOptions options;
	private ImageView iv_photo, iv_1, iv_2, iv_3, iv_4, iv_5;
	private TextView tv_name, tv_time, tv_content;
	private RatingBar rb_goodscomments;

	public GoodsCommentAdapter(Context context, List<Map<String, Object>> list) {
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
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(context, R.layout.fragment_goodscomment, null);

		iv_photo = (ImageView) view.findViewById(R.id.iv_photo);
		tv_name = (TextView) view.findViewById(R.id.tv_name);
		rb_goodscomments = (RatingBar) view.findViewById(R.id.rb_goodscomments);
		tv_time = (TextView) view.findViewById(R.id.tv_time);
		tv_content = (TextView) view.findViewById(R.id.tv_content);
		// 设置头像
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(
				list.get(position).get("ImagePath").toString(), iv_photo,
				options);
		// 设置名字
		tv_name.setText(list.get(position).get("name").toString());
		// 设置评价
		int Score = (Integer) list.get(position).get("srcoe");
		rb_goodscomments.setRating(Score);
		// 设置时间
		tv_time.setText(list.get(position).get("time").toString());
		// 设置内容
		tv_content.setText(list.get(position).get("context").toString());

		return view;
	}

}
