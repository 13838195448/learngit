package com.mpyf.lening.activity.adapter;

import java.util.List;
import java.util.Map;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.XCRoundImageView;
import com.mpyf.lening.activity.activity.OthersInfoActivity;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.sax.StartElementListener;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ZDPaihangAdapter extends BaseAdapter {
	private List<Map<String, Object>> list;
	private Context context;
	private LinearLayout zd_info;
	private XCRoundImageView iv_qaitem_touxiang;
	private TextView tv_qaitem_truename, tv_qaitem_honor_name, tv_zd_adoptnum,
			tv_zd_paiming;
	private ImageView iv_qaitem_honor_pic, iv_zd_paiming;
	private DisplayImageOptions option;
	private DisplayImageOptions options;

	public ZDPaihangAdapter(Context context, List<Map<String, Object>> list) {
		this.context = context;
		this.list = list;

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.icon_defualt)
				.showImageOnFail(R.drawable.icon_defualt)
				.showImageForEmptyUri(R.drawable.icon_defualt)
				.cacheInMemory(true).cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.ALPHA_8).build();

		option = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.v1)
				.showImageOnFail(R.drawable.v1)
				.showImageForEmptyUri(R.drawable.v1).cacheInMemory(true)
				.cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.ALPHA_8).build();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		view = View.inflate(context, R.layout.item_zdpaihang, null);

		zd_info = (LinearLayout) view.findViewById(R.id.zd_info);
		iv_qaitem_touxiang = (XCRoundImageView) view
				.findViewById(R.id.iv_qaitem_touxiang);
		tv_qaitem_truename = (TextView) view
				.findViewById(R.id.tv_qaitem_truename);
		tv_qaitem_honor_name = (TextView) view
				.findViewById(R.id.tv_qaitem_honor_name);
		iv_qaitem_honor_pic = (ImageView) view
				.findViewById(R.id.iv_qaitem_honor_pic);
		iv_zd_paiming = (ImageView) view.findViewById(R.id.iv_zd_paiming);
		tv_zd_adoptnum = (TextView) view.findViewById(R.id.tv_zd_adoptnum);
		tv_zd_paiming = (TextView) view.findViewById(R.id.tv_zd_paiming);
		// 设置信息
		// tv_zd_paiming.setText();
		if (position == 0) {
			Resources resources = context.getResources();
			Drawable btnDrawable = resources
					.getDrawable(R.drawable.zd_leaderboard_icon_trophy1);
			iv_zd_paiming.setBackgroundDrawable(btnDrawable);
		} else if (position == 1) {
			Resources resources1 = context.getResources();
			Drawable btnDrawable1 = resources1
					.getDrawable(R.drawable.zd_leaderboard_icon_trophy2);
			iv_zd_paiming.setBackgroundDrawable(btnDrawable1);
		} else if (position == 2) {
			Resources resources2 = context.getResources();
			Drawable btnDrawable2 = resources2
					.getDrawable(R.drawable.zd_leaderboard_icon_trophy3);
			iv_zd_paiming.setBackgroundDrawable(btnDrawable2);
		} else {
			iv_zd_paiming.setVisibility(View.GONE);
			tv_zd_paiming.setVisibility(View.VISIBLE);
			tv_zd_paiming.setText((position + 1) + "");
		}
		tv_qaitem_truename.setText(list.get(position).get("trueName")
				.toString());
		tv_qaitem_honor_name.setText(list.get(position).get("honor_name")
				.toString());
		tv_zd_adoptnum.setText(list.get(position).get("adoptnum").toString());
		// 设置头像
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(Setting.apiUrl + "new-pages/PersonalPhoto/"
				+ list.get(position).get("userId") + ".jpg",
				iv_qaitem_touxiang, options);
		// 设置称号
		ImageLoader imageLoader2 = ImageLoader.getInstance();
		imageLoader2.displayImage(
				Setting.apiUrl + list.get(position).get("honor_pic"),
				iv_qaitem_honor_pic, option);
		addlistener(list.get(position).get("userId").toString());
		return view;
	}

	private void addlistener(final String userId) {
		zd_info.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, OthersInfoActivity.class);
				intent.putExtra("userId", userId);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 不是在Activity中进行跳转，需要添加这个方法
				context.startActivity(intent);
			}
		});
	}

}
