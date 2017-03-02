package com.mpyf.lening.activity.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.AsyncBitmapLoader;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ImageOptions;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MingshidetilActivity extends Activity {

	private LinearLayout ll_moneydetil_back;
	private ImageView iv_mingshidetil_photo, iv_mingshidetil_level;
	private TextView tv_mingshidetil_name, tv_mingshidetil_context,
			tv_mingshidetil_zhiwei, tv_mingshidetil_feild,
			tv_mingshidetil_hangye, tv_mingshidetil_jianjie;
	private DisplayImageOptions options;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//new Xiaoxibeijing().changetop(this, R.color.main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mingshidetill);
		init();
		showinfo();
		addlistener();
	}

	private void init() {
		ll_moneydetil_back = (LinearLayout) findViewById(R.id.ll_moneydetil_back);
		iv_mingshidetil_photo = (ImageView) findViewById(R.id.iv_mingshidetil_photo);
		tv_mingshidetil_name = (TextView) findViewById(R.id.tv_mingshidetil_name);
		iv_mingshidetil_level = (ImageView) findViewById(R.id.iv_mingshidetil_level);
		tv_mingshidetil_context = (TextView) findViewById(R.id.tv_mingshidetil_context);
		tv_mingshidetil_zhiwei = (TextView) findViewById(R.id.tv_mingshidetil_zhiwei);
		tv_mingshidetil_feild = (TextView) findViewById(R.id.tv_mingshidetil_feild);
		tv_mingshidetil_hangye = (TextView) findViewById(R.id.tv_mingshidetil_hangye);
		tv_mingshidetil_jianjie = (TextView) findViewById(R.id.tv_mingshidetil_jianjie);
	}

	private void showinfo() {
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.defaultimage) 
		.showImageOnFail(R.drawable.defaultimage)
		.showImageForEmptyUri(R.drawable.defaultimage)
		.cacheInMemory(true) 
		.cacheOnDisk(true) 
		 .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
        .bitmapConfig(Bitmap.Config.ALPHA_8)
		.build(); 
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 1) {
					try {
						int[] levels = { R.drawable.vi, R.drawable.vii,
								R.drawable.viii, R.drawable.viv, R.drawable.vv,
								R.drawable.vvi, R.drawable.vvii,
								R.drawable.vviii, R.drawable.vix, };

						JSONObject jo = new JSONObject(msg.obj.toString());
					//	AsyncBitmapLoader.sethoneImage(iv_mingshidetil_photo,Setting.apiUrl + jo.getString("picUrl"));
						ImageLoader imageLoader = ImageLoader.getInstance();
						imageLoader.displayImage(Setting.apiUrl + jo.getString("picUrl"), iv_mingshidetil_photo, options);
						tv_mingshidetil_name.setText(jo.getString("lec_Name"));
						tv_mingshidetil_context.setText(jo.getString("signature"));
						tv_mingshidetil_feild.setText(jo.getString("field"));
						tv_mingshidetil_hangye
								.setText(jo.getString("industry"));
						tv_mingshidetil_jianjie.setText(jo.getString("remark"));
						tv_mingshidetil_zhiwei.setText(jo.getString("honor"));
						iv_mingshidetil_level.setImageResource(levels[jo
								.getInt("lec_Level") - 1]);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Diaoxian.showerror(MingshidetilActivity.this,
								e.getMessage());
					}
				} else {
					Diaoxian.showerror(MingshidetilActivity.this,
							msg.obj.toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("pk_Lec", getIntent().getStringExtra("id"));
				Message msg = new Message();

				String result = HttpUse.messageget("Account", "getLecturer",
						map);
				
				Log.i("TAG", result);
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
			}
		}.start();
	}

	private void addlistener() {
		ListenerServer.setfinish(MingshidetilActivity.this, ll_moneydetil_back);
	}
}
