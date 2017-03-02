package com.mpyf.lening.activity.adapter;

import java.util.List;
import java.util.Map;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.AsyncBitmapLoader;
import com.mpyf.lening.activity.activity.FatieActivity.GridAdapter.ViewHolder;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShangCheng_LeAdapter extends BaseAdapter {

	private Activity activity;
	private List<Map<String, Object>> list;
	private DisplayImageOptions options;

	public ShangCheng_LeAdapter(Activity activity,
			List<Map<String, Object>> list) {
		this.activity = activity;
		this.list = list;

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.defaultimage)
				.showImageOnFail(R.drawable.defaultimage)
				.showImageForEmptyUri(R.drawable.defaultimage)
				.cacheInMemory(true).cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.ALPHA_8).build();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// View view=View.inflate(activity, R.layout.item_shangcheng, null);

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(activity).inflate(
					R.layout.item_shangcheng_le, null);

			holder.iv_goods = (ImageView) convertView
					.findViewById(R.id.iv_goods);
			holder.tv_title = (TextView) convertView
					.findViewById(R.id.tv_title);
			holder.tv_buynum = (TextView) convertView
					.findViewById(R.id.tv_buynum);
			holder.tv_amount = (TextView) convertView
					.findViewById(R.id.tv_amount);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// TODO 设置数据
		// 设置图片
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(
				Setting.apiUrl + list.get(position).get("picUrl"), holder.iv_goods, options);

		// TODO
		// http://125.35.5.187/goodPic/020up1128.jpg
		// AsyncBitmapLoader.sethoneImage(holder.iv_goods, Setting.apiUrl
		// + "goodPic/" + list.get(position).get("pk_goods") + ".jpg");

		holder.tv_title.setText(list.get(position).get("goodsName").toString());
		holder.tv_buynum.setText(list.get(position).get("buy_num").toString());
		holder.tv_amount.setText(list.get(position).get("amount").toString());

		return convertView;
	}

	// ViewHolder静态类
	static class ViewHolder {
		public ImageView iv_goods;
		public TextView tv_title, tv_buynum, tv_amount;
	}
}
