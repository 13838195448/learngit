package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.LogUtil;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.Readsaved;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.activity.adapter.Vpadapter;
import com.mpyf.lening.activity.fragment.FgDuoxuan;
import com.mpyf.lening.activity.fragment.Fragment_danxuan;
import com.mpyf.lening.activity.fragment.Fragment_danxuan.CheckedListener;
import com.mpyf.lening.activity.fragment.Fragment_duoxuan;
import com.mpyf.lening.activity.fragment.Fragment_duoxuan.Checked2Listener;
import com.mpyf.lening.activity.fragment.Fragment_panduan;
import com.mpyf.lening.activity.fragment.Fragment_panduan.Checked3Listener;
import com.mpyf.lening.interfaces.bean.Parame.ItemUser;
import com.mpyf.lening.interfaces.bean.Result.Item;
import com.mpyf.lening.interfaces.bean.Result.QueAndRes;
import com.mpyf.lening.interfaces.http.HttpUse;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class ZicetestActivity extends FragmentActivity {

	private LinearLayout ll_test_back;
	private TextView tv_test_title;
	private ViewPager mViewPager;
	private Handler handler;
	private String[] zimu = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N" };
	
	private TextView tv_test_num;
	private TextView tv_sum_unm;
	private TextView tv_yuan1;
	private TextView tv_yuan2;
	private TextView tv_yuan3;
	private String pK_TruePaper;
	private String pK_Paper;
	private TextView tv_text_ok;
	private ZicetestActivity mContext;
	private HorizontalScrollView mColumnHorizontalScrollView;
	private LinearLayout mColumnContent;
	private TextView localTextView;
	private int a;
	private List<Fragment> listvp;
	private ArrayList<Integer> listdex;
	private String title;
	
	private int columnSelectIndex = 0;
	/** 屏幕宽度 */
	private int mScreenWidth = 0;
	/** Item宽度 */
	private int mItemWidth = 0;
	
	private String fornet;
	private Dialog dialog;
	
	private int single = 0;
	private int more =0;
	private int judge =0;
	
	
	private TextView tv_time_test;
	private String aNS;
	private int choiceState;
	private int secondtitle;
	private int count;
	private LayoutParams p;
	private boolean isSendans =false;
	
	private Handler handle = new Handler(){
		public void handleMessage(Message msg) {
			setChangelView(listvp);
			
		};
	};
	private Thread thread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	
	//	new Xiaoxibeijing().changetop(ZicetestActivity.this, R.color.main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_zicetest);
		init();
		initTest();
		
		timer();
		addlistener();
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		
	}
	
	private void timer() {
		String examTime = getIntent().getStringExtra("Exam_Long");
		
	final	int time =
			Integer.valueOf(examTime)*60;
	
//		tv_time_test
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				tv_time_test.setText(msg.obj.toString());
				if(tv_time_test.getText().equals("00:00")){
					if(!isSendans){
						Diaoxian.showerror(mContext, "考试时间到");
						sendAns();
						Intent intent = new Intent(mContext,TestAnserActivity.class);
						intent.putExtra("selfId",  getIntent().getStringExtra("examId"));
						intent.putExtra("title", title);
						if(fornet.equals("participateExam")){
							intent.putExtra("isTest","true");
						}
						startActivity(intent);
					}
				}
			};
		};
		thread = new Thread(){
			@Override
			public void run() {
				
				for(int i=0;i<=time;i++){
					
					int fen=(time-i)/60;
					int miao=(time-i)%60;
					
					String fens=fen<10?"0"+fen:fen+"";
					String miaos=miao<10?"0"+miao:miao+"";
					
					Message msg=new Message();
					
					msg.obj=fens+":"+miaos;
					handler.sendMessage(msg);
					try {
						sleep(1000);
					} catch (InterruptedException e) {
//						Toast.makeText(getApplicationContext(), e.getMessage(),
//								Toast.LENGTH_SHORT).show();
						
					}
				}
			};
		};
		
		thread.start();
	
	}


	private void setChangelView(List<Fragment> list) {

		initTabColumn();
		selectMode(1);
	}



	private void selectMode(int position) {
		
		if (listvp.isEmpty())
			return;

		TextView selTextView = null;
		for (int i = 0; i < mColumnContent.getChildCount(); i++) {
			TextView textView = (TextView) mColumnContent.getChildAt(i);
			textView.setTextColor(getResources().getColor(R.color.main));
			textView.getBackground().setAlpha(80);
			if (i == position) {
				selTextView = textView;
				// 设置被选中项字体颜色变化为被选中颜色
				selTextView
						.setTextColor(getResources().getColor(R.color.main));
				selTextView.getBackground().setAlpha(255);
				
				if(listdex!=null&&listdex.contains(i)){
					selTextView
					.setTextColor(0x90ffffff);
					
				}

			}
		}

		int left = selTextView.getLeft();
		int right = selTextView.getRight();
	
		int sw = getResources().getDisplayMetrics().widthPixels;
		// 将控件滚动到屏幕的中间位置
		mColumnHorizontalScrollView.scrollTo(
				left - sw / 2 + (right - left) / 2 +2*(right - left) , 0);
//		left - sw / 2 + (right - left) / 2 , 0);
	
	}


	// TODO Auto-generated catch block
	
	private void initTabColumn() {

 
		mScreenWidth = getWindowsWidth(this);

		mColumnContent.removeAllViews();
		count = listvp.size();
		if(listvp.size()==single || listvp.size()==more || listvp.size()==judge){//一种题
			choiceState=1;
			count = listvp.size()+1;
			
		}else if(listvp.size()==single+more&&judge==0){//两种题
			choiceState=2;
			count =listvp.size()+2;
			secondtitle = single;
		}
		else if(listvp.size()==single+judge&&more==0){
			choiceState=3;
			count =listvp.size()+2;
			secondtitle = single;
		}else if(listvp.size()==more+judge&&single==0){
			choiceState=4;
			count =listvp.size()+2;
			secondtitle = more;
		}else if(single!=0 && more!=0&&judge!=0){//三种题都有
			choiceState=5;
			
			count =listvp.size()+3;
			
		}
		
		p = new LayoutParams(dip2px(this, 30),
				dip2px(this, 30));
		switch (choiceState) {
		case 1:
			choiceMode1(count);
			break;
		case 2:
		choiceMode2(count,"单选题","多选题");
		break;
		case 3:
		choiceMode2(count,"单选题","判断题");
		break;
		case 4:
			choiceMode2(count,"多选题","判断题");
			break;
		case 5:
			choiceMode3(count);
			break;
		default:
			break;
		}
		

		LinearLayout.LayoutParams columuParams = (android.widget.LinearLayout.LayoutParams) mColumnContent
				.getLayoutParams();
	//	columuParams.width =   * count;
		//columuParams.width = (a+65)*count+a*2;
		columuParams.width = (a+dip2px(this,33))*count+a*2;
		mColumnContent.setLayoutParams(columuParams);

		setModelClick();
		
	
	}

	private void choiceMode3(int count) {



		for (int i = 0; i < count; i++) {
			textSetting();
			
			//小圆的大小
			
			localTextView.setText((i)+ "");
			if(i==0){
				p = new LayoutParams(dip2px(this, 14),
						dip2px(this, 30));
				localTextView.setText("单选题");
			//	localTextView.setTextColor(Color.WHITE);
				//localTextView.setTextSize(dip2px(this, (float) 3.1));
				localTextView.setTextSize(2, 8);
				localTextView.setBackgroundResource(R.drawable.bg_title);
				localTextView.setTextColor(getResources().getColorStateList(
						R.color.dise));
			}else if(i==single+1){
				localTextView.setText("多选题");
				localTextView.setTextColor(Color.WHITE);
			//	localTextView.setTextSize(dip2px(this, (float) 3.5));
				localTextView.setTextSize(2, 8);
				localTextView.setBackgroundResource(R.drawable.bg_title);
				p = new LayoutParams(dip2px(this, 14),
						dip2px(this, 30));
				localTextView.getBackground().setAlpha(255);
			}else if(i==single+more+2){
				localTextView.setText("判断题");
				
			//	localTextView.setTextSize(dip2px(this, 4));
				localTextView.setTextSize(2, 8);
				localTextView.setBackgroundResource(R.drawable.bg_title);
				p = new LayoutParams(dip2px(this, 14),
						dip2px(this, 30));
				localTextView.getBackground().setAlpha(255);
			}
			
			if(i>single+1&&i<single+more+2){
				localTextView.setText((i-single-1)+ "");
			}else if(i>single+more+2){
				localTextView.setText((i-single-2-more)+ "");
			}
			
			setParams(i);
			mColumnContent.addView(localTextView, i);
		}
	
	
	}


	private void choiceMode2(int count, String string, String string2) {

		for (int i = 0; i < count; i++) {
			textSetting();
			
			//小圆的大小
			
			localTextView.setText((i)+ "");
			if(i==0){
				localTextView.setText(string);
				localTextView.setBackgroundResource(R.drawable.bg_title);
				localTextView.setTextSize(2, 8);
				p = new LayoutParams(dip2px(this, 14),
						dip2px(this, 30));
			}else if(i==single+1){
				localTextView.setText(string2);
				localTextView.setBackgroundResource(R.drawable.bg_title);
				localTextView.setTextSize(2, 8);
				p = new LayoutParams(dip2px(this, 14),
						dip2px(this, 30));
			}
			
			if(i>secondtitle+1){
				localTextView.setText((i-secondtitle-1)+ "");
			}
			
			setParams(i);
			mColumnContent.addView(localTextView, i);
		}
	
	}


	//添加titl分类方法
	private void choiceMode1(int count) {
		for (int i = 0; i < count; i++) {
			textSetting();
			
			//小圆的大小
			
			localTextView.setText((i)+ "");
			if(i==0){
				if(listvp.size()==single){
					localTextView.setText("单选题");
					p = new LayoutParams(dip2px(this, 14),
							dip2px(this, 30));
					localTextView.setTextSize(2, 8);
				}else if(listvp.size()==more){
					localTextView.setText("多选题");
					p = new LayoutParams(dip2px(this, 14),
							dip2px(this, 30));
					localTextView.setTextSize(2, 8);
				}else if(listvp.size()==judge){
					localTextView.setText("判断题");
					p = new LayoutParams(dip2px(this, 14),
							dip2px(this, 30));
					localTextView.setTextSize(2, 8);
				}
				localTextView.setBackgroundResource(R.drawable.bg_title);
			}
			
			setParams(i);
			mColumnContent.addView(localTextView, i);
		}
	}

//设置小圆点位置
	private void setParams(int i) {
		
		
		a = (mScreenWidth - dip2px(this, 90)-150) / 5;

			p.leftMargin = a;//小圆的左margin
		
		localTextView.setLayoutParams(p);


		localTextView.setGravity(Gravity.CENTER);
		
		

		if (columnSelectIndex == i) {
			localTextView.setTextColor(getResources().getColorStateList(
					R.color.main));
			localTextView.getBackground().setAlpha(255);
			// view.getBackground().setAlpha(255);
		}
	}


	private void textSetting() {//textview的设置项
		localTextView = new TextView(this);
		localTextView.setBackgroundResource(R.drawable.bg_yuan1);
		p = new LayoutParams(dip2px(this, 30),
				dip2px(this, 30));
		localTextView.setTextAppearance(this,
				R.style.top_category_scroll_view_item_text);
		localTextView.setTextColor(getResources().getColorStateList(
				R.color.main));
		localTextView.getBackground().setAlpha(80);
	}



	private void setModelClick() {
		// TODO Auto-generated method stub
		// 获取所有model,清除之前选择的状态
		for (int i = 0; i < mColumnContent.getChildCount(); i++) {
			TextView textView = (TextView) mColumnContent.getChildAt(i);
	//		textView.setTag(i);
		switch (choiceState) {
		case 1:
			textView.setTag(i-1);
			break;

		case 2:
		case 3:
		case 4:
			if(i<=secondtitle){
				textView.setTag(i-1);
			}else if(i>secondtitle) {
				textView.setTag(i-2);
			}
			
			break;
		case 5:
			if(i>0&&i<=single){
				textView.setTag(i-1);
			}else if(i>single&&i<=single+more+1){
				textView.setTag(i-2);
			}else if(i>single+more+1){
				textView.setTag(i-3);
			}else if(i==0){
				textView.setTag(i);
			}
		default:
			break;
		}	
			
			
			
			textView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int index = (Integer) v.getTag();
					mViewPager.setCurrentItem(index);
				}
			});
		}

	}



	private int getWindowsWidth(ZicetestActivity zicetestActivity) {
	

		DisplayMetrics dm = new DisplayMetrics();
		zicetestActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	
	}



	private void addlistener() {

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			// TODO Auto-generated catch block
			@Override
			public void onPageSelected(int arg0) {

			//	selectMode(arg0);
				
				switch (choiceState) {
				case 1:
					selectMode(arg0+1);
					break;
				case 2:
				case 3:
					 
				case 4:
					if(arg0<secondtitle){
						selectMode(arg0+1);
					}else{
						selectMode(arg0+2);
					}
					break;
				case 5:
					if(arg0<single){
						selectMode(arg0+1);
					}else if(arg0>=single && arg0<single+more){
						selectMode(arg0+2);
					}else {
						selectMode(arg0+3);
					}
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}
		
			@Override
			public void onPageScrollStateChanged(int arg0) {
	//			tv_test_num.setText((mViewPager.getCurrentItem() + 1) + "");
				
				int i =mViewPager.getCurrentItem()+1;
				
				if(i<=single){

					tv_test_num.setText(i + " ");
					tv_sum_unm.setText("/" + (single));
					
				}else if(i>single&& i<= (single+more)){
					
					tv_test_num.setText((i-single) + " ");
					tv_sum_unm.setText("/" + (listvp.size()-single-judge));
					
				}else if(i>(single+more)){
					
					tv_test_num.setText((i-single-more) + " ");
					tv_sum_unm.setText("/" + (listvp.size()-single-more));
				}
			}
		});
		
		
		
		tv_text_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
					 
			final	Dialog dialog = MyDialog.MyDialogShow(mContext, R.layout.popup_isok, 1f);
				
				Button bt_isok_quie = (Button) dialog.findViewById(R.id.bt_isok_quie);
				Button bt_isok_ok = (Button) dialog.findViewById(R.id.bt_isok_ok);
				TextView tv_isok_title = (TextView) dialog.findViewById(R.id.tv_isok_title);
				tv_isok_title.setText("是否提交");
				bt_isok_ok.setText("提交");
				
				bt_isok_quie.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {

						dialog.dismiss();
					}
				});
				
				bt_isok_ok.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						
						sendAns();
						isSendans=true;
						Intent intent = new Intent(mContext,TestAnserActivity.class);
						intent.putExtra("selfId",  getIntent().getStringExtra("examId"));
						intent.putExtra("title", title);
				 		if(fornet.equals("participateExam")){
							intent.putExtra("isTest","true");
						}
						startActivity(intent);
						dialog.dismiss();
						finish();
					}
				});
				dialog.show();
			}
		});
		
		ll_test_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
			//	sendAns();
				
				showDialog();
				
				
				
			}
		});
		
	}
	protected void showDialog() {

		final	Dialog dialog = MyDialog.MyDialogShow(mContext, R.layout.popup_isok, 1f);
		
		Button bt_isok_quie = (Button) dialog.findViewById(R.id.bt_isok_quie);
		Button bt_isok_ok = (Button) dialog.findViewById(R.id.bt_isok_ok);
		TextView tv_isok_title = (TextView) dialog.findViewById(R.id.tv_isok_title);
		tv_isok_title.setText("返回试题将自动提交");
		bt_isok_ok.setText("确定");
		bt_isok_quie.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				dialog.dismiss();
				
			}
		});
		
		
		bt_isok_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

			sendAns();
			
			isSendans=true;
			
			Intent intent = new Intent(mContext,TestAnserActivity.class);
			intent.putExtra("selfId",  getIntent().getStringExtra("examId"));
			intent.putExtra("title", title);
			if(fornet.equals("participateExam")){
				intent.putExtra("isTest","true");
			}
			startActivity(intent);
			dialog.dismiss();
			finish();
			}
		});
		
			dialog.show();
	}


	//返回
	@Override  
	public void onBackPressed() {  
	//	sendAns();
		showDialog();
		
	//	super.onBackPressed();  
		
	}  
	
	//发送答案

	protected void sendAns() {

		final Handler h = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what != 1){
					Diaoxian.showerror(mContext, msg.obj.toString());
				//	Toast.makeText(mContext, "提交问题", 0).show();
				}else{
					finish();
				}
			}
		};
		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("examId",  getIntent().getStringExtra("examId"));
				map.put("paperId", pK_Paper);
				map.put("truepaperId", pK_TruePaper);

				Message msg = new Message();

				String result = HttpUse.messageget("Exam", getIntent().getStringExtra("tijiao"), map);
				// android.util.Log.i("JSON", result.toString());

				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = jo.getString("data");
					} else {
						msg.obj = jo.getString("message");
					}
				} catch (JSONException e) {
					msg.obj = e.getMessage();
		//			Diaoxian.showerror(mContext, e.getMessage());
				}

				h.sendMessage(msg);

			};
		}.start();
		
	}

	
	private void init() {

		mContext = ZicetestActivity.this;
		
		mColumnHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.mColumnHorizontalScrollView_IntegralShop);
		mColumnContent = (LinearLayout) findViewById(R.id.mRadioGroup_content_IntegralShop);
		
		
		
		ll_test_back = (LinearLayout) findViewById(R.id.ll_test_back);
		tv_test_title = (TextView) findViewById(R.id.tv_test_title);
		
		
		tv_test_num = (TextView) findViewById(R.id.tv_test_num);
		tv_sum_unm = (TextView) findViewById(R.id.tv_sum_unm);
		
		
		tv_time_test = (TextView) findViewById(R.id.tv_time_test);
		
		
		
//		tv_yuan1.setVisibility(View.INVISIBLE);
//		tv_yuan1.getBackground().setAlpha(100);
//		tv_yuan3.getBackground().setAlpha(100);
//		tv_yuan3.setTextColor(0x6441d092);
		
		mViewPager = (ViewPager) findViewById(R.id.vp_test);
		
		tv_text_ok = (TextView) findViewById(R.id.tv_text_ok);


	}
//初始化数据
	private void initTest() {
		aNS = getIntent().getStringExtra("ANS");
		dialog = MyDialog.MyDialogloading(mContext);
		dialog.show();

		final ArrayList<QueAndRes> QaList = new ArrayList<QueAndRes>();
	
		handler = new Handler() {

			private Vpadapter mAdapter;
			
		
			@Override
			public void handleMessage(Message msg) {

				if(msg.what!=1){
					Diaoxian.showerror(mContext, msg.obj.toString());
				}
				 listvp = new ArrayList<Fragment>();
				 listdex = new ArrayList<Integer>();
				try {
					JSONObject jsonObject = new JSONObject(msg.obj.toString());

					JSONArray jo = jsonObject.getJSONArray("data");

					for (int i = 0; i < jo.length(); i++) {
						JSONObject j = jo.getJSONObject(i);
						QueAndRes queAndRes = new QueAndRes();
						// queAndRes.setIS_Right(j.optBoolean("IS_Right"));
						queAndRes.setIS_Right(j.getBoolean("IS_Right"));
						queAndRes.setPK_Exam(j.getString("PK_Exam"));
						queAndRes.setPK_Paper(j.getString("PK_Paper"));
						queAndRes.setPK_Que(j.getString("PK_Que"));
						queAndRes.setQue_content(j.getString("que_CONTEN"));
						queAndRes.setQue_Score(j.getInt("que_Score"));
						queAndRes.setQue_type(j.getInt("que_type"));

						JSONArray ja = j.getJSONArray("item");
						List<Item> aList = new ArrayList<Item>();
						for (int k = 0; k < ja.length(); k++) {
							JSONObject joo = ja.getJSONObject(k);
							Item item = new Item();
							// item.setIS_Right(joo.getString("IS_Right"));

							item.setPK_Item(joo.getString("PK_Item"));
							item.setItem_CONTENT(joo.getString("item_CONTENT"));
							item.setPK_Que(joo.getString("PK_Que"));
							aList.add(item);
						}

						JSONArray ja2 = j.getJSONArray("itemUser");
						List<ItemUser> userlist = new ArrayList<ItemUser>();
						for (int z = 0; z < ja2.length(); z++) {
							ItemUser user = new ItemUser();

							userlist.add(user);
						}

						queAndRes.setItem(aList);
						queAndRes.setItemUser(userlist);

						QaList.add(queAndRes);
					}

					JSONObject jso = jsonObject.getJSONObject("data2");
					pK_TruePaper = jso.getString("PK_TruePaper").toString();
					pK_Paper = jso.getString("PK_Paper").toString();
					title = jso.getString("exam_Name").toString();
					
					
				} catch (JSONException e) {
					e.printStackTrace();
				}

				getQuestion(QaList, pK_TruePaper);
			}

			private void getQuestion(ArrayList<QueAndRes> QaList,
					String pK_TruePaper) {

				for (int i = 0; i < QaList.size(); i++) {
					QueAndRes question = QaList.get(i);
					switch (question.getQue_type()) {
					case 10:// 单选
						List<Map<String, Object>> listdaxuan = new ArrayList<Map<String, Object>>();
						List<Item> danxuanitem = question.getItem();
						for ( int j = 0; j < danxuanitem.size(); j++) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("num", zimu[j]);
							map.put("option", danxuanitem.get(j)
									.getItem_CONTENT());
							map.put("id", danxuanitem.get(j).getPK_Que());
							map.put("ID", danxuanitem.get(j).getPK_Item());

							listdaxuan.add(map);
						}

						Fragment_danxuan danxuan = new Fragment_danxuan(
								aNS,
								question.getItemUser(), question.getPK_Exam(),
								question.getPK_Paper(), pK_TruePaper, mViewPager,
								mViewPager.getCurrentItem(),
								question.getQue_type(),
								question.getQue_Score(), question.getPK_Que(),
								question.getQue_content(), listdaxuan);
						
						danxuan.setOnCheckedListener(new CheckedListener() {
							
							@Override
							public void checked() {
								mColumnContent.getChildAt(mViewPager.getCurrentItem()+1).setBackgroundResource(
										R.drawable.bg_yuan2);
								TextView textview = (TextView) mColumnContent.getChildAt(mViewPager.getCurrentItem()+1);
								textview.setTextColor(Color.WHITE);
								if(mViewPager.getCurrentItem()!=listvp.size()-1){
									textview.getBackground().setAlpha(80);
									
								}
								
								listdex.add(mViewPager.getCurrentItem()+1);
							}
						});
				          		listvp.add(danxuan);
				          
				          		single++;
						break;

					case 20:// 多选
						List<Map<String, Object>> listduoxuan = new ArrayList<Map<String, Object>>();
						List<Item> duoxuanoption = question.getItem();
						for (int j = 0; j < duoxuanoption.size(); j++) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("num", zimu[j]);
							map.put("option", duoxuanoption.get(j)
									.getItem_CONTENT());
							map.put("id", duoxuanoption.get(j).getPK_Que());
							map.put("ID", duoxuanoption.get(j).getPK_Item());

							listduoxuan.add(map);
						}

						Fragment_duoxuan duoxuan = new Fragment_duoxuan(
								aNS,
								question.getItemUser(), question.getPK_Exam(),
								question.getPK_Paper(),
								pK_TruePaper, mViewPager,
								mViewPager.getCurrentItem(),
								question.getQue_type(),
								question.getQue_Score(), question.getPK_Que(),
								question.getQue_content(), listduoxuan);
						
						
						duoxuan.setChecked2Listener(new Checked2Listener() {
							
							@Override
							public void checked2() {
								mColumnContent.getChildAt(mViewPager.getCurrentItem()+2).setBackgroundResource(
										R.drawable.bg_yuan2);
								TextView textview = (TextView) mColumnContent.getChildAt(mViewPager.getCurrentItem()+2);
								textview.setTextColor(Color.WHITE);
								if(mViewPager.getCurrentItem()!=listvp.size()-1){
									textview.getBackground().setAlpha(80);
									
								}
								listdex.add(mViewPager.getCurrentItem()+2);
								
							}
						});
						
						
					listvp.add(duoxuan);
					
					more++;
					
						break;
					case 30:// 判断
						List<Map<String, Object>> listpanduan = new ArrayList<Map<String, Object>>();
						List<Item> panduanoptions = question.getItem();
						for (int j = 0; j < panduanoptions.size(); j++) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("num", zimu[j]);
							map.put("option", panduanoptions.get(j)
									.getItem_CONTENT());
							map.put("ID", panduanoptions.get(j).getPK_Item());
							map.put("id", panduanoptions.get(j).getPK_Que());
							listpanduan.add(map);
						}
						

						Fragment_panduan panduan = new Fragment_panduan(
								aNS,
								question.getItemUser(), question.getPK_Exam(),
								question.getPK_Paper(), pK_TruePaper, mViewPager,
								mViewPager.getCurrentItem(),
								question.getQue_type(),
								question.getQue_Score(), question.getPK_Que(),
								question.getQue_content(), listpanduan);
						
						
						panduan.setChecked3Listener(new Checked3Listener() {
							
							@Override
							public void checked3() {
								
//								if(mViewPager.getCurrentItem()==listvp.size()-1){
//									TextView t = (TextView) mColumnContent.getChildAt(mViewPager.getCurrentItem()+3);
//									t.setTextColor(Color.WHITE);
//									t.setBackgroundResource(R.drawable.bg_yuan2);
//								}else{
									
									mColumnContent.getChildAt(mViewPager.getCurrentItem()+3).setBackgroundResource(
											R.drawable.bg_yuan2);
									TextView textview = (TextView) mColumnContent.getChildAt(mViewPager.getCurrentItem()+3);
									textview.setTextColor(Color.WHITE);
									
									if(mViewPager.getCurrentItem()!=listvp.size()-1){
										textview.getBackground().setAlpha(80);
										
									}
									
		//						}
								listdex.add(mViewPager.getCurrentItem()+3);
								
								
								
							}
						});
						
						listvp.add(panduan);
						judge++;
						break;
					default:
						break;
					}
					new Thread(){
						@Override
						public void run() {
							
							//	Message message = Message.obtain();
							handle.sendEmptyMessage(0);
						}
					}.start();

				}
				
				mViewPager.removeAllViews();
				mViewPager.setOffscreenPageLimit(listvp.size());
				
				if(mAdapter != null){
					mAdapter.clearFragment();
				}
				
				
				mAdapter = new Vpadapter(getSupportFragmentManager(),
						listvp);
				mViewPager.setAdapter(mAdapter);
				dialog.dismiss();
				tv_test_title.setText(title);
				
//				tv_test_num.setText((mViewPager.getCurrentItem() + 1) + "");
//				tv_sum_unm.setText("/" + listvp.size());
				int i =mViewPager.getCurrentItem()+1;
				
				if(i<=single){

					tv_test_num.setText(i + " ");
					tv_sum_unm.setText("/" + (single));
					
				}else if(i>single&& i<= (single+more)){
					
					tv_test_num.setText((i-single) + " ");
					tv_sum_unm.setText("/" + (listvp.size()-single-judge));
					
				}else if(i>(single+more)){
					
					tv_test_num.setText((i-single-more) + " ");
					tv_sum_unm.setText("/" + (listvp.size()-single-more));
				}
			}
		};

		new Thread() {
			
			

			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				
			

				fornet = getIntent().getStringExtra("forNet");
				if(fornet.equals("participateExam")){
					map.put("examId",getIntent().getStringExtra("examId"));
				}else{
					
					map.put("selfId", getIntent().getStringExtra("examId"));
				}
				String result = HttpUse.messageget("Exam",
						fornet, map);

			//	 LogUtil.LogShitou(result.toString());
				Message msg = Message.obtain();

				JSONObject jo;
				try {
					jo = new JSONObject(result);
					if (jo.getBoolean("result")) {

						msg.what = 1;
						msg.obj = result;

					} else {
						msg.obj = jo.getString("message");
					}

				} catch (JSONException e) {
					msg.obj = e.getMessage();
				}
				handler.sendMessage(msg);
			};
		}.start();

	}
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}
