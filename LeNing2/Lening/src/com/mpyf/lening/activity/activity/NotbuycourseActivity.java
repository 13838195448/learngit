package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.Quanjubianliang;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.activity.adapter.Vpadapter;
import com.mpyf.lening.activity.fragment.Fragment_comment;
import com.mpyf.lening.activity.fragment.Fragment_courseinfo;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class NotbuycourseActivity extends FragmentActivity {

	private RelativeLayout rl_wmcourse_back, rl_wmcourse_info,
			rl_wmcourse_comment;
	private TextView tv_wmcourse_score,tv_wmcourse_info, tv_wmcourse_comment,tv_wmcourse_title,tv_wmcourse_time,tv_wmcourse_le,tv_wmcourse_buy;
	private ImageView iv_wmcourse_info, iv_wmcourse_comment,iv_wmcourse_top,iv_wmcourse_le;

	private ViewPager vp_wmcourse;
	private Fragment_courseinfo fragment_courseinfo;
	private Fragment_comment fragment_comment;
	private List<Fragment> listvp;

	public static String id="";
	public static String buyNum="";
	public static String buyWay="";
	public static String lecturer="";
	public static String remark="";
	public static String stuScore="";
	public static String stuTime="";
	
	public String imgurl="";
	private DisplayImageOptions options;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new Xiaoxibeijing().changetop(this, R.color.main);
		setContentView(R.layout.activity_notbuycourse);
		init();
		showinfo();
		
		addlistener();
	}

	private void init() {
		vp_wmcourse = (ViewPager) findViewById(R.id.vp_wmcourse);
		rl_wmcourse_back = (RelativeLayout) findViewById(R.id.rl_wmcourse_back);
		rl_wmcourse_info = (RelativeLayout) findViewById(R.id.rl_wmcourse_info);
		rl_wmcourse_comment = (RelativeLayout) findViewById(R.id.rl_wmcourse_comment);
		tv_wmcourse_score=(TextView) findViewById(R.id.tv_wmcourse_score);
		tv_wmcourse_info = (TextView) findViewById(R.id.tv_wmcourse_info);
		tv_wmcourse_comment = (TextView) findViewById(R.id.tv_wmcourse_comment);
		tv_wmcourse_title=(TextView) findViewById(R.id.tv_wmcourse_title);
		tv_wmcourse_time=(TextView) findViewById(R.id.tv_wmcourse_time);
		tv_wmcourse_le=(TextView) findViewById(R.id.tv_wmcourse_le);
		tv_wmcourse_buy=(TextView) findViewById(R.id.tv_wmcourse_buy);
		iv_wmcourse_info = (ImageView) findViewById(R.id.iv_wmcourse_info);
		iv_wmcourse_comment = (ImageView) findViewById(R.id.iv_wmcourse_comment);
		iv_wmcourse_top=(ImageView) findViewById(R.id.iv_wmcourse_top);
		iv_wmcourse_le=(ImageView) findViewById(R.id.iv_wmcourse_le);
		
		id=getIntent().getStringExtra("id");
		Quanjubianliang.courseid=id;
		iv_wmcourse_top.setAlpha(180);
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.defaultimage) 
		.showImageOnFail(R.drawable.defaultimage)
		.showImageForEmptyUri(R.drawable.defaultimage)
		.cacheInMemory(true) 
		.cacheOnDisk(true) 
		 .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
        .bitmapConfig(Bitmap.Config.ALPHA_8)
		.build(); 
			
	}

	private void showinfo(){
		
		
		final Dialog dialog=MyDialog.MyDialogloading(this);
		dialog.show();
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				dialog.dismiss();
				if(msg.what==1){
					try {
						JSONObject jo=new JSONObject(msg.obj.toString());
						tv_wmcourse_title.setText(jo.getString("courseName"));
						tv_wmcourse_time.setText("上线时间:"+jo.getString("onlineTime"));
						tv_wmcourse_le.setText(jo.getString("amount"));
						tv_wmcourse_score.setText(jo.getString("stuScore"));
						imgurl=Setting.apiUrl+jo.getString("picUrl");
					//	AsyncBitmapLoader.sethoneImage(iv_wmcourse_top,imgurl );
						ImageLoader imageLoader = ImageLoader.getInstance();
						imageLoader.displayImage(imgurl, iv_wmcourse_top, options);
						
						buyNum=jo.getString("buyNum");
						buyWay=jo.getString("buyWay");
						lecturer=jo.getString("lecturer").trim();
						remark=jo.getString("remark").trim();
						stuScore=jo.getString("stuScore");
						stuTime=jo.getString("stuTime").trim();
						
						if(buyWay.equals("1")){
							iv_wmcourse_le.setImageResource(R.drawable.me_icon_le);
						}else if(buyWay.equals("2")){
							iv_wmcourse_le.setImageResource(R.drawable.me_icon_jin);
						}
						
						if(jo.getBoolean("isBuy")){
							tv_wmcourse_buy.setText("学习");
						}else{
							tv_wmcourse_buy.setText("立即购买");
						}
						
						setvp();
						
					} catch (JSONException e) {
//						Diaoxian.showerror(NotbuycourseActivity.this, e.getMessage());
						return;
					}
				}else{
					return;
				}
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("courseId", id);
				Message msg=new Message();
				String result=HttpUse.messageget("CourseStudy", "getCourseByCourseId", map);
				try {
					JSONObject jo=new JSONObject(result);
					if(jo.getBoolean("result")){
						msg.what=1;
						msg.obj=jo.getString("data");
					}else{
						msg.obj=jo.getString("message");
					}
				} catch (JSONException e) {
					return;
				}
				handler.sendMessage(msg);
			};
		}.start();
	}
	
	private void setvp() {
		fragment_courseinfo = new Fragment_courseinfo(id,remark,lecturer,buyNum);
		fragment_comment = new Fragment_comment(id);

		listvp = new ArrayList<Fragment>();

		listvp.add(fragment_courseinfo);
		listvp.add(fragment_comment);

		Vpadapter adapter = new Vpadapter(getSupportFragmentManager(), listvp);
		vp_wmcourse.setAdapter(adapter);

	}

	private void addlistener() {
		ListenerServer.setfinish(this, rl_wmcourse_back);

		rl_wmcourse_info.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				tv_wmcourse_info.setTextColor(getResources().getColor(
						R.color.main));
				iv_wmcourse_info.setBackgroundColor(getResources().getColor(
						R.color.main));
				tv_wmcourse_comment.setTextColor(getResources().getColor(
						R.color.zywz));
				iv_wmcourse_comment.setBackgroundColor(getResources().getColor(
						R.color.dise));
				vp_wmcourse.setCurrentItem(0);
			}
		});

		rl_wmcourse_comment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				tv_wmcourse_info.setTextColor(getResources().getColor(
						R.color.zywz));
				iv_wmcourse_info.setBackgroundColor(getResources().getColor(
						R.color.dise));
				tv_wmcourse_comment.setTextColor(getResources().getColor(
						R.color.main));
				iv_wmcourse_comment.setBackgroundColor(getResources().getColor(
						R.color.main));
				vp_wmcourse.setCurrentItem(1);
			}
		});

		vp_wmcourse.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				if (arg0 == 2) {
					switch (vp_wmcourse.getCurrentItem()) {
					case 0:
						tv_wmcourse_info.setTextColor(getResources().getColor(
								R.color.main));
						iv_wmcourse_info.setBackgroundColor(getResources().getColor(
								R.color.main));
						tv_wmcourse_comment.setTextColor(getResources().getColor(
								R.color.zywz));
						iv_wmcourse_comment.setBackgroundColor(getResources().getColor(
								R.color.dise));
						break;
					case 1:
						tv_wmcourse_info.setTextColor(getResources().getColor(
								R.color.zywz));
						iv_wmcourse_info.setBackgroundColor(getResources().getColor(
								R.color.dise));
						tv_wmcourse_comment.setTextColor(getResources().getColor(
								R.color.main));
						iv_wmcourse_comment.setBackgroundColor(getResources().getColor(
								R.color.main));
						break;
					default:
						break;
					}
				}
			}
		});
		
		tv_wmcourse_buy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(tv_wmcourse_buy.getText().toString().equals("学习")){
					Intent intent=new Intent(NotbuycourseActivity.this, BuyedActivity.class);
					intent.putExtra("id", id);
					startActivity(intent);
				}else{
					showbuypopu();
				}
			}
		});
	}
	
	private void showbuypopu(){
		final Dialog dialog=new Dialog(this);
		
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.popupwindow_buy);
		dialog.setCanceledOnTouchOutside(true);
		Window window = dialog.getWindow(); 
		window.setGravity(Gravity.CENTER);
		WindowManager.LayoutParams lp = window.getAttributes(); 
//		lp.alpha = 0.8f; 
//		lp.width=;
	    window.setAttributes(lp); 
	    window.setBackgroundDrawableResource(R.drawable.yuanjiao);
	    
		ImageView iv_buy_course=(ImageView) dialog.findViewById(R.id.iv_buy_course);
		TextView tv_buy_title=(TextView) dialog.findViewById(R.id.tv_buy_title);
		TextView tv_buy_cost=(TextView) dialog.findViewById(R.id.tv_buy_cost);
		Button bt_buy_ok=(Button) dialog.findViewById(R.id.bt_buy_ok);
		Button bt_buy_quit=(Button) dialog.findViewById(R.id.bt_buy_quit);
		
	//	AsyncBitmapLoader.sethoneImage(iv_buy_course, imgurl);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(imgurl, iv_buy_course, options);
		
		tv_buy_title.setText(tv_wmcourse_title.getText().toString());
		
		if(buyWay.equals("1")){
			tv_buy_cost.setText("需支付："+tv_wmcourse_le.getText().toString()+"乐币");
			iv_wmcourse_le.setImageResource(R.drawable.me_icon_le);
		}else if(buyWay.equals("2")){
			tv_buy_cost.setText("需支付："+tv_wmcourse_le.getText().toString()+"金币");
			iv_wmcourse_le.setImageResource(R.drawable.me_icon_jin);
		}
		
		bt_buy_quit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		
		bt_buy_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				buy();
			}
		});
		
		dialog.show();
	}
	
	private void buy(){

		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				
				if(msg.what==1){
					Diaoxian.showerror(NotbuycourseActivity.this, "购买成功");
					finish();
					Intent intent=new Intent(NotbuycourseActivity.this,BuyedActivity.class);
					intent.putExtra("id", id);
					startActivity(intent);
				}else{
					Diaoxian.showerror(NotbuycourseActivity.this, "购买失败");
				}
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("courseId", id);
				Message msg=new Message();
				String result=HttpUse.messageget("CourseStudy", "buyCourse", map);
				
				try {
					JSONObject jo=new JSONObject(result);
					if(jo.getBoolean("result")){
						msg.what=1;
					}
					msg.obj=jo.getString("message");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				handler.sendMessage(msg);
				
				
			};
		}.start();
	
	}
}
