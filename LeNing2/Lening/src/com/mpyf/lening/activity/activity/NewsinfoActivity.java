package com.mpyf.lening.activity.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.interfaces.http.HttpUse;

public class NewsinfoActivity extends Activity {

	private LinearLayout ll_newsinfo_back;
	private TextView tv_newsinfo_title,tv_newsinfo_time;
	private String context="";
	private WebView wv_newsinfo_context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//new Xiaoxibeijing().changetop(NewsinfoActivity.this, R.color.main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_newsinfo);
		init();
		getdata();
		addlistener();

	}

	private void init() {
		ll_newsinfo_back = (LinearLayout) findViewById(R.id.ll_newsinfo_back);
		tv_newsinfo_title=(TextView) findViewById(R.id.tv_newsinfo_title);
		tv_newsinfo_time=(TextView) findViewById(R.id.tv_newsinfo_time);
		wv_newsinfo_context=(WebView) findViewById(R.id.wv_newsinfo_context);
	}

	private void addlistener() {
		ListenerServer.setfinish(NewsinfoActivity.this, ll_newsinfo_back);
	}
	
	private void getdata(){


		final Dialog dialog=MyDialog.MyDialogloading(this);
		dialog.show();
		

		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				dialog.dismiss();
				if (msg.what == 1) {
					try {
						JSONObject jo=new JSONObject(msg.obj.toString());

							tv_newsinfo_title.setText(jo.getString("INM_TITLE_NAME"));
							tv_newsinfo_time.setText(jo.getString("INM_Time"));
							WebSettings webSettings= wv_newsinfo_context.getSettings(); // webView: 类WebView的实例 
							webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);  //就是这句
							wv_newsinfo_context.loadDataWithBaseURL(null,jo.getString("INM_CONTENT"), "text/html", "utf-8",null);
							
							
					} catch (JSONException e) {
						Diaoxian.showerror(NewsinfoActivity.this, e.getMessage());
					}
					
				}else{
					Diaoxian.showerror(NewsinfoActivity.this, msg.obj .toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				final Map<String, Object> map = new HashMap<String, Object>();
				map.put("inmId", getIntent().getStringExtra("id"));
				String result = HttpUse.messageget("AbilityCertification",
						"getInmByInmId", map);
				Message msg = new Message();
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = jo.getString("data");
						
				
					} else {
						msg.obj = jo.getString("message");
					}
				} catch (JSONException e) {
//					
					msg.obj=e.getMessage();
				}
				handler.sendMessage(msg);
			};
		}.start();

	
	}
	
}
