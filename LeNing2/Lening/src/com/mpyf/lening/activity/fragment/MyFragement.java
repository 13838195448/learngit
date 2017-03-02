package com.mpyf.lening.activity.fragment;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.AsyncBitmapLoader;
import com.mpyf.lening.Jutil.Roundbitmap;
import com.mpyf.lening.Jutil.XCRoundImageView;
import com.mpyf.lening.activity.activity.JingyanActivity;
import com.mpyf.lening.activity.activity.MyInterestActivity;
import com.mpyf.lening.activity.activity.MyqaActivity;
import com.mpyf.lening.activity.activity.MyshoucangActivity;
import com.mpyf.lening.activity.activity.RiliActivity;
import com.mpyf.lening.activity.activity.SettingActivity;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class MyFragement extends Fragment implements OnClickListener {

	private ImageView iv_fr_my_rili;
	private View view;
	private XCRoundImageView im_fr_my_photo;
	private RelativeLayout rl_fr_my_info;
	private LinearLayout ll_fr_my_ask, ll_fr_my_answer, ll_fr_my_cang,
			ll_fr_my_biaoqian, ll_fr_my_caifu;
	private TextView tv_fr_my_user, tv_fr_my_zan, tv_fr_my_lv, tv_fr_my_caifu,
			tv_fr_my_info;
	private String adopt;
	private int goodsNum;
	private int honor;
	private DisplayImageOptions options;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_my, null);

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.icon_defualt)
				.showImageOnFail(R.drawable.icon_defualt)
				.showImageForEmptyUri(R.drawable.icon_defualt)
				.cacheInMemory(true).cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.ALPHA_8).build();
		init();
		showinfo();

		return view;

	}

	private void showinfo() {

		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {

				if (msg.what == 1) {
					try {
						JSONObject ja = new JSONObject(msg.obj.toString());
						adopt = ja.getString("adopt");
						goodsNum = ja.getInt("goodsnum");
						honor = ja.getInt("honor");
						tv_fr_my_zan.setText(goodsNum + "");
						tv_fr_my_lv.setText(adopt);
						tv_fr_my_caifu.setText(honor + "");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		};

		new Thread() {
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();

				map.put("userId", Setting.currentUser.getPk_user());
				Message msg = new Message();
				String result = HttpUse.messageget("QueAndAns", "getQADate",
						map);
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = jo.getString("data");
					} else {
						msg.obj = jo.getString("message");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					msg.obj = e.getMessage();
				}
				handler.sendMessage(msg);
			};
		}.start();
	}

	private void init() {
		iv_fr_my_rili = (ImageView) view.findViewById(R.id.iv_fr_my_rili);
		im_fr_my_photo = (XCRoundImageView) view
				.findViewById(R.id.im_fr_my_photo);
		rl_fr_my_info = (RelativeLayout) view.findViewById(R.id.rl_fr_my_info);
		tv_fr_my_user = (TextView) view.findViewById(R.id.tv_fr_my_user);
		tv_fr_my_info = (TextView) view.findViewById(R.id.tv_fr_my_info);
		tv_fr_my_zan = (TextView) view.findViewById(R.id.tv_fr_my_zan);
		tv_fr_my_lv = (TextView) view.findViewById(R.id.tv_fr_my_lv);
		tv_fr_my_caifu = (TextView) view.findViewById(R.id.tv_fr_my_caifu);
		ll_fr_my_ask = (LinearLayout) view.findViewById(R.id.ll_fr_my_ask);
		ll_fr_my_answer = (LinearLayout) view
				.findViewById(R.id.ll_fr_my_answer);
		ll_fr_my_cang = (LinearLayout) view.findViewById(R.id.ll_fr_my_cang);
		ll_fr_my_caifu = (LinearLayout) view.findViewById(R.id.ll_fr_my_caifu);
		ll_fr_my_biaoqian = (LinearLayout) view
				.findViewById(R.id.ll_fr_my_biaoqian);

		tv_fr_my_user.setText(Setting.currentUser.getTruename());
		tv_fr_my_info.setText(Setting.currentUser.getHonor_name());

		// 设置头像
		// ImageLoader imageLoader = ImageLoader.getInstance();
		// imageLoader.displayImage(Setting.apiUrl + "new-pages/PersonalPhoto/"
		// + Setting.currentUser.getPk_user() + ".jpg", im_fr_my_photo,
		// options);
		im_fr_my_photo.setImageResource(R.drawable.icon_defualt);
		im_fr_my_photo
				.setImageBitmap(Roundbitmap.toRoundBitmap(im_fr_my_photo));
		AsyncBitmapLoader.setRoundImage(im_fr_my_photo, Setting.apiUrl
				+ "new-pages/PersonalPhoto/" + Setting.currentUser.getPk_user()
				+ ".jpg");

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		iv_fr_my_rili.setOnClickListener(this);
		rl_fr_my_info.setOnClickListener(this);
		ll_fr_my_caifu.setOnClickListener(this);
		ll_fr_my_ask.setOnClickListener(this);
		ll_fr_my_answer.setOnClickListener(this);
		ll_fr_my_cang.setOnClickListener(this);
		ll_fr_my_biaoqian.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_fr_my_rili:
			startActivity(new Intent(getActivity(), RiliActivity.class));
			break;
		case R.id.rl_fr_my_info:
			startActivity(new Intent(getActivity(), SettingActivity.class));
			break;
		case R.id.ll_fr_my_caifu:
			startActivity(new Intent(getActivity(), JingyanActivity.class));
			break;
		case R.id.ll_fr_my_ask:
			startActivity(new Intent(getActivity(), MyqaActivity.class));
			break;
		case R.id.ll_fr_my_answer:
			Intent intent = new Intent(getActivity(), MyqaActivity.class);
			intent.putExtra("fragid", 1);
			startActivity(intent);
			break;
		case R.id.ll_fr_my_cang:
			startActivity(new Intent(getActivity(), MyshoucangActivity.class));
			break;
		case R.id.ll_fr_my_biaoqian:
			// 跳到我感兴趣的标签页
			startActivity(new Intent(getActivity(), MyInterestActivity.class));
			break;
		}
	}
}
