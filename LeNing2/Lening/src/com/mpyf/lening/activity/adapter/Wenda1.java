package com.mpyf.lening.activity.adapter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.AsyncBitmapLoader;
import com.mpyf.lening.Jutil.ImageOptions;
import com.mpyf.lening.Jutil.MyGridview;
import com.mpyf.lening.Jutil.Roundbitmap;
import com.mpyf.lening.Jutil.XCRoundImageView;
import com.mpyf.lening.activity.activity.OthersInfoActivity;
import com.mpyf.lening.activity.activity.Qa_photoActivity;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.sax.StartElementListener;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Administrator 问答的gridView用法
 * 
 */
public class Wenda1 extends BaseAdapter {

	private Context context;
	private List<Map<String, Object>> list;

	private HashMap<Integer, ImageView> map;

	// private ViewHolder holder;
	private DisplayImageOptions options;
	private DisplayImageOptions option;
	private String queId;
	private int[] arrs;

	public Wenda1(Context context, List<Map<String, Object>> list) {
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
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int arg0, View convertView, ViewGroup arg2) {

		// if (null == convertView) {
		// holder = new ViewHolder();
		View view = View.inflate(context, R.layout.wenda1, null);
		ImageView iv_qaitem_touxiang = (ImageView) view
				.findViewById(R.id.iv_qaitem_touxiang);
		// 等级的图片
		ImageView iv_qaitem_dengji = (ImageView) view
				.findViewById(R.id.iv_qaitem_dengji);
		ImageView iv_qa_payway = (ImageView) view
				.findViewById(R.id.iv_qa_payway);
		final ImageView iv_wenda_shoucang = (ImageView) view
				.findViewById(R.id.iv_wenda_shoucang);
		TextView tv_qaitem_name = (TextView) view
				.findViewById(R.id.tv_qaitem_name);
		final TextView tv_wenda_shoucang = (TextView) view
				.findViewById(R.id.tv_wenda_shoucang);
		// 收藏的LinearLayout
		final LinearLayout ll_qaitem_shoucang = (LinearLayout) view
				.findViewById(R.id.ll_qaitem_shoucang);
		// 等级的昵称
		TextView tv_qaitem_chenghao = (TextView) view
				.findViewById(R.id.tv_qaitem_chenghao);
		TextView tv_qaitem_cost = (TextView) view
				.findViewById(R.id.tv_qaitem_cost);
		TextView tv_qaitem_comments = (TextView) view
				.findViewById(R.id.tv_qaitem_comments);
		TextView tv_qaitem_title = (TextView) view
				.findViewById(R.id.tv_qaitem_title);
		TextView tv_NUM = (TextView) view.findViewById(R.id.tv_NUM);
		MyGridview gv_pic = (MyGridview) view.findViewById(R.id.gv_pic);
		tv_qaitem_name.setMaxWidth(200);
		tv_qaitem_name.setText(list.get(arg0).get("trueName").toString());
		tv_qaitem_cost.setText(list.get(arg0).get("REWARD_Num").toString());
		// TODO

		tv_qaitem_chenghao.setText((String) list.get(arg0).get("honor_name"));

		// 这里isCollection==0表示未被收藏，才可以执行下面点击添加收藏
		if ((Integer) list.get(arg0).get("isCollection") == 0) {
			tv_wenda_shoucang.setText("添加收藏");
			iv_wenda_shoucang
					.setBackgroundResource(R.drawable.know_collect_nor);
			// 点击收藏
			ll_qaitem_shoucang.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					final Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							switch (msg.what) {

							case 1:
								Toast.makeText(context, "收藏成功",
										Toast.LENGTH_SHORT).show();
								tv_wenda_shoucang.setText("已收藏");
								iv_wenda_shoucang
										.setBackgroundResource(R.drawable.know_collect_selected);

								ll_qaitem_shoucang.setOnClickListener(null);
								break;
							default:
								Toast.makeText(context, msg.obj.toString(),
										Toast.LENGTH_SHORT).show();
								break;

							}
						}
					};

					// 有两种方法，一种是在调用添加收藏接口前，先调用判断是否已收藏的接口，另一种我现在写，你看你用哪个，前面那种代码稍微简单点
					new Thread() {

						public void run() {
							Message msg = new Message();
							Map<String, Object> map = new HashMap<String, Object>();

							map.put("queId", list.get(arg0).get("id"));
							String result = HttpUse.messageget("QueAndAns",
									"collectionQue", map);
							try {
								JSONObject jo = new JSONObject(result);
								// 添加收藏返回， true,表示收藏成功；false,表示已经收藏过
								if (jo.getBoolean("result")) {
									msg.what = 1;
								}
								msg.obj = jo.get("message");
							} catch (JSONException e) {
								msg.obj = e.getMessage();
							}

							handler.sendMessage(msg);

						};
					}.start();
				}
			});
		} else {
			tv_wenda_shoucang.setText("已收藏");
			iv_wenda_shoucang
					.setBackgroundResource(R.drawable.know_collect_selected);
			ll_qaitem_shoucang.setOnClickListener(null);
		}

		tv_qaitem_comments.setText(list.get(arg0).get("Ans_Num").toString());

		int num = (Integer) list.get(arg0).get("PIC_NUM");
		arrs = new int[num];
		for (int i = 0; i < arrs.length; i++) {
			arrs[i] = i + 1;
		}
		queId = list.get(arg0).get("id").toString();

		AgridviewAdapter newGrideView = new AgridviewAdapter(context, arrs,
				queId);
		gv_pic.setAdapter(newGrideView);

		int stae = (Integer) list.get(arg0).get("QUE_STATE");
		if (stae == 0) {
			tv_qaitem_title.setText(Html
					.fromHtml("<font color='red'>[未解决]</font> "
							+ list.get(arg0).get("QUE_CONTENT").toString()));
		} else if (stae == 1) {
			tv_qaitem_title.setText(Html
					.fromHtml("<font color='#a2d46f'>[已解决]</font> "
							+ list.get(arg0).get("QUE_CONTENT").toString()));
		}

		int payway = (Integer) list.get(arg0).get("REWARD_WAY");

		if (payway == 1) {
			iv_qa_payway.setImageResource(R.drawable.know_icon_le);
		} else if (payway == 2) {
			iv_qa_payway.setImageResource(R.drawable.know_icon_jin);
		}
		// AsyncBitmapLoader.setRoundImage(holder.iv_qaitem_touxiang,Setting.apiUrl
		// + "new-pages/PersonalPhoto/"+ list.get(arg0).get("userid") + ".jpg");
		// holder.iv_qaitem_touxiang.setImageBitmap(Roundbitmap
		// .toRoundBitmap(holder.iv_qaitem_touxiang));
		// iv_qaitem_dengji.setBackgroundDrawable(list.get(arg0).get("honor_pic").toString());
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(Setting.apiUrl + "new-pages/PersonalPhoto/"
				+ list.get(arg0).get("userid") + ".jpg", iv_qaitem_touxiang,
				options);
		// 等级图片 new-pages\QA\
		ImageLoader imageLoader2 = ImageLoader.getInstance();
		imageLoader2.displayImage(
				Setting.apiUrl + list.get(arg0).get("honor_pic"),
				iv_qaitem_dengji, option);

		iv_qaitem_touxiang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, OthersInfoActivity.class);
				intent.putExtra("userId", list.get(arg0).get("userid")
						.toString());
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 不是在Activity中进行跳转，需要添加这个方法
				context.startActivity(intent);
			}
		});

		return view;
	}

	// class ViewHolder {
	//
	// public ImageView iv_qaitem_touxiang, iv_qa_payway, iv_pic1, iv_pic2,
	// iv_pic3, iv_pic4, iv_pic5, iv_pic6, iv_pic8, iv_pic7, iv_pic9;
	// public TextView tv_qaitem_name, tv_qaitem_cost, tv_qaitem_comments,
	// tv_qaitem_title;
	// public MyGridview gv_pic;
	// }

}
