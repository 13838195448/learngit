package com.mpyf.lening.activity.fragment;

import com.mpyf.lening.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class Fragment_goodsinfo extends Fragment {
	private String id;
	private String remark;
	private WebView wv_goods_html;

	public Fragment_goodsinfo(String id, String remark) {
		this.id = id;
		this.remark = remark;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_goodsinfo, null);
		init(view);
		showinfo();
		return view;
	}

	private void init(View view) {
		wv_goods_html = (WebView) view.findViewById(R.id.wv_goods_html);
	}

	private void showinfo() {

		// if (remark != null) {

		wv_goods_html.loadDataWithBaseURL(null, remark, "text/html", "utf-8",
				null);
		// }
		wv_goods_html.getSettings().setJavaScriptEnabled(true);
		wv_goods_html.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		wv_goods_html.setHorizontalScrollBarEnabled(false);
		wv_goods_html.getSettings().setSupportZoom(true);
		wv_goods_html.getSettings().setBuiltInZoomControls(true);
		wv_goods_html.setInitialScale(70);
		wv_goods_html.setHorizontalScrollbarOverlay(true);
	}
}
