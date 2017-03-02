package com.mpyf.lening.activity.activity;

import io.vov.vitamio.Vitamio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.AsyncBitmapLoader;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ImageOptions;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.Quanjubianliang;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.activity.adapter.Vpadapter;
import com.mpyf.lening.activity.fragment.Fragment_buyeddayi;
import com.mpyf.lening.activity.fragment.Fragment_buyedkejian;
import com.mpyf.lening.activity.fragment.Fragment_buyednote;
import com.mpyf.lening.activity.fragment.Fragment_comment;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class BuyedActivity extends FragmentActivity implements OnClickListener {

	private RelativeLayout rl_ymcourse_back, rl_ymcourse_kejian,
			rl_ymcourse_note, rl_ymcourse_answer, rl_ymcourse_comment;

	private TextView tv_ymcourse_kejian, tv_ymcourse_note, tv_ymcourse_answer,
			tv_ymcourse_comment,tv_ymcourse_title,tv_ymcourse_time,tv_ymcourse_score;

	private ImageView iv_ymcourse_kejian, iv_ymcourse_note, iv_ymcourse_answer,
			iv_ymcourse_comment,iv_ymcourse_top;

	private ViewPager vp_ymcourse;
	private List<Fragment> listvp;
	private Fragment_buyedkejian fragment_kejian;
	private Fragment_buyednote fragment_buyednote;
	private Fragment_buyeddayi fragment_buyeddayi;
	private Fragment_comment fragment_comment;
	
	private String id="";
	private String studyres="";

	private DisplayImageOptions options;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//new Xiaoxibeijing().changetop(this, R.color.main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_buyedcourse);
//		Vitamio.isInitialized(this);
		init();
		showinfo();
		
		addlistener();
	}

	private void init() {
		rl_ymcourse_back = (RelativeLayout) findViewById(R.id.rl_ymcourse_back);
		rl_ymcourse_kejian = (RelativeLayout) findViewById(R.id.rl_ymcourse_kejian);
		rl_ymcourse_note = (RelativeLayout) findViewById(R.id.rl_ymcourse_note);
		rl_ymcourse_answer = (RelativeLayout) findViewById(R.id.rl_ymcourse_answer);
		rl_ymcourse_comment = (RelativeLayout) findViewById(R.id.rl_ymcourse_comment);

		tv_ymcourse_kejian = (TextView) findViewById(R.id.tv_ymcourse_kejian);
		tv_ymcourse_note = (TextView) findViewById(R.id.tv_ymcourse_note);
		tv_ymcourse_answer = (TextView) findViewById(R.id.tv_ymcourse_answer);
		tv_ymcourse_comment = (TextView) findViewById(R.id.tv_ymcourse_comment);
		tv_ymcourse_title=(TextView) findViewById(R.id.tv_ymcourse_title);
		tv_ymcourse_time=(TextView) findViewById(R.id.tv_ymcourse_time);
		tv_ymcourse_score=(TextView) findViewById(R.id.tv_ymcourse_score);
		
		iv_ymcourse_kejian = (ImageView) findViewById(R.id.iv_ymcourse_kejian);
		iv_ymcourse_note = (ImageView) findViewById(R.id.iv_ymcourse_note);
		iv_ymcourse_answer = (ImageView) findViewById(R.id.iv_ymcourse_answer);
		iv_ymcourse_comment = (ImageView) findViewById(R.id.iv_ymcourse_comment);
		iv_ymcourse_top=(ImageView) findViewById(R.id.iv_ymcourse_top);
		
		
		vp_ymcourse = (ViewPager) findViewById(R.id.vp_ymcourse);
		
		id=getIntent().getStringExtra("id");
		Quanjubianliang.courseid=id;
	}

	private void showinfo(){
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.defaultimage)
		.showImageOnFail(R.drawable.defaultimage)
		.showImageForEmptyUri(R.drawable.defaultimage)
		.cacheInMemory(true) 
		.cacheOnDisk(true) 
		 .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
        .bitmapConfig(Bitmap.Config.ALPHA_8)
		.build();
		
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(msg.what==1){
					JSONObject jo;
					try {
						jo = new JSONObject(msg.obj.toString());
						tv_ymcourse_title.setText(jo.getString("courseName"));
						tv_ymcourse_time.setText("距离有效时间:"+jo.getString("remainIng"));
						tv_ymcourse_score.setText(jo.getString("score"));
						//AsyncBitmapLoader.sethoneImage(iv_ymcourse_top, Setting.apiUrl+jo.getString("picUrl"));
					
						ImageLoader imageLoader = ImageLoader.getInstance();
						imageLoader.displayImage(Setting.apiUrl+jo.getString("picUrl"), iv_ymcourse_top, options);
						iv_ymcourse_top.setAlpha(180);
						studyres=jo.getString("studyRes");
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Diaoxian.showerror(BuyedActivity.this, e.getMessage());
						
					}
					setvp();
				}else{
					Diaoxian.showerror(BuyedActivity.this,msg.obj.toString());
				}
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("courseId", id);
				Message msg=new Message();
				String result=HttpUse.messageget("CourseStudy", "studyCourseInf", map);
				try {
					JSONObject jo=new JSONObject(result);
					if(jo.getBoolean("result")){
						msg.what=1;
						msg.obj=jo.getString("data");
					}else{
						msg.obj=jo.getString("message");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				handler.sendMessage(msg);
			};
		}.start();
		
		
	
	
	}
	
	private void setvp() {

		fragment_kejian = new Fragment_buyedkejian(id,studyres);
		fragment_buyednote = new Fragment_buyednote(id);
		fragment_buyeddayi = new Fragment_buyeddayi();
		fragment_comment=new Fragment_comment(id);
		
		listvp = new ArrayList<Fragment>();
		listvp.add(fragment_kejian);
		listvp.add(fragment_buyednote);
		listvp.add(fragment_buyeddayi);
		listvp.add(fragment_comment);
		
		Vpadapter adapter = new Vpadapter(getSupportFragmentManager(), listvp);
		vp_ymcourse.setAdapter(adapter);
	}

	private void addlistener() {
		ListenerServer.setfinish(this, rl_ymcourse_back);
		rl_ymcourse_kejian.setOnClickListener(this);
		rl_ymcourse_note.setOnClickListener(this);
		rl_ymcourse_answer.setOnClickListener(this);
		rl_ymcourse_comment.setOnClickListener(this);
		
		vp_ymcourse.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				if(arg0==2){
					clearvp(vp_ymcourse.getCurrentItem());
				}
			}
		});
		
	}

	private void clearvp(int index){
		TextView[] texttop={tv_ymcourse_kejian, tv_ymcourse_note, tv_ymcourse_answer,tv_ymcourse_comment};
		 ImageView[] imagetop={iv_ymcourse_kejian, iv_ymcourse_note, iv_ymcourse_answer,iv_ymcourse_comment};
		for(int i=0;i<texttop.length;i++){
			if(index==i){
				texttop[i].setTextColor(getResources().getColor(R.color.main));
				imagetop[i].setBackgroundColor(getResources().getColor(R.color.main));
			}else{
				texttop[i].setTextColor(getResources().getColor(R.color.zywz));
				imagetop[i].setBackgroundColor(getResources().getColor(R.color.dise));
			}
		}
		
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.rl_ymcourse_kejian:
			clearvp(0);
			vp_ymcourse.setCurrentItem(0);
			break;
		case R.id.rl_ymcourse_note:
			clearvp(1);
			vp_ymcourse.setCurrentItem(1);
			break;
		case R.id.rl_ymcourse_answer:
			clearvp(2);
			vp_ymcourse.setCurrentItem(2);
			break;
		case R.id.rl_ymcourse_comment:
			clearvp(3);
			vp_ymcourse.setCurrentItem(3);
			break;
		default:
			break;
		}

	}
}
