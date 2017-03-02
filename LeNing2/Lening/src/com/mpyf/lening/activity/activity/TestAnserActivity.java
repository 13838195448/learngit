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
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.activity.adapter.Vpadapter;
import com.mpyf.lening.activity.fragment.Fragment_show_dan;
import com.mpyf.lening.activity.fragment.Fragment_show_duo;
import com.mpyf.lening.activity.fragment.Fragment_show_pan;
import com.mpyf.lening.interfaces.bean.Parame.ItemUser;
import com.mpyf.lening.interfaces.bean.Result.Item;
import com.mpyf.lening.interfaces.bean.Result.QueAndRes;
import com.mpyf.lening.interfaces.http.HttpUse;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * @author Administrator 显示答案
 *
 */
public class TestAnserActivity extends FragmentActivity {

	private String[] zimu = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N" };
	private LinearLayout mColumnContent;
	private TestAnserActivity mContext;
	private LinearLayout ll_test_back;
	private TextView tv_test_title;
	private TextView tv_test_num;
	private TextView tv_sum_unm;
	private TextView tv_time_test;
	private ViewPager mViewPager;
	private TextView tv_text_ok;
	private List<Fragment> listvp;
	private HorizontalScrollView mColumnHorizontalScrollView;
	private Handler handle;
	private Vpadapter mAdapter;
	private ArrayList<Integer> right_index;
	private int columnSelectIndex = 0;
	/** 屏幕宽度 */
	private int mScreenWidth = 0;
	/** Item宽度 */
	private int mItemWidth = 0;
	private TextView localTextView;
	private int a;
	private int single=0;
	private int more =0;
	private int judge =0;
	
	private int choiceState;
	private int secondtitle;
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			setChangelView(listvp);
		};
	};
	private Dialog dialog;
	private int count;
	private LayoutParams p;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		
		setContentView(R.layout.activity_zicetest);
		init();
		initTest();
		addListener();
	}

	private void setChangelView(List<Fragment> listvp2) {

		initTabColumn();
		selectMode(1);
	}

	private void initTabColumn() {
		// TODO Auto-generated method stub
		mScreenWidth = getWindowsWidth(this);
		mItemWidth = mScreenWidth / 5; // 一个Item宽度为屏幕的1/5
		
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
		columuParams.width = mItemWidth * count;
		//columuParams.width = (a+40+a)*(count-1);
		mColumnContent.setLayoutParams(columuParams);

		setModelClick();
	}

	private void choiceMode3(int count) {
		

		for (int i = 0; i < count; i++) {
			textSetting();
			
			//小圆的大小
			if(right_index!=null && right_index.contains(i)){
				localTextView.setBackgroundResource(R.drawable.bg_yuan2);
			}else{
				
				localTextView.setBackgroundResource(R.drawable.bg_yuan_hong);
			}
			localTextView.setText((i)+ "");
			if(i==0){
				p = new LayoutParams(dip2px(this, 14),
						dip2px(this, 30));
				localTextView.setText("单选题");
			//	localTextView.setTextColor(Color.WHITE);
				localTextView.setTextSize(2, 8);
				localTextView.setBackgroundResource(R.drawable.bg_title);
				localTextView.setTextColor(getResources().getColorStateList(
						R.color.dise));
			}else if(i==single+1){
				localTextView.setText("多选题");
				localTextView.setTextColor(Color.WHITE);
				localTextView.setTextSize(2, 8);
				localTextView.setBackgroundResource(R.drawable.bg_title);
				p = new LayoutParams(dip2px(this, 14),
						dip2px(this, 30));
				localTextView.getBackground().setAlpha(255);
			}else if(i==single+more+2){
				localTextView.setText("判断题");
				
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
			
			
			
			localTextView.getBackground().setAlpha(80);
			
			setParams(i);
			mColumnContent.addView(localTextView, i);
		}
	
	
	
	}

	private void choiceMode2(int count, String string, String string2) {



		for (int i = 0; i < count; i++) {
			textSetting();
			
			//小圆的大小
			localTextView.setText((i)+ "");
			
			if(right_index!=null && right_index.contains(i)){
				localTextView.setBackgroundResource(R.drawable.bg_yuan2);
			}else{
				
				localTextView.setBackgroundResource(R.drawable.bg_yuan_hong);
			}
			
			if(i==0){
				localTextView.setText(string);
				localTextView.setBackgroundResource(R.drawable.bg_title);
				localTextView.setTextSize(2, 8);
				p = new LayoutParams(dip2px(this, 14),
						dip2px(this, 30));
				localTextView.getBackground().setAlpha(255);
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
			
			
			
			localTextView.getBackground().setAlpha(80);
		
			setParams(i);
			mColumnContent.addView(localTextView, i);
		}
	
	
	}

	private void choiceMode1(int count) {
		for (int i = 0; i < count; i++) {
			
			textSetting();

			localTextView.setText((i)+ "");
			if(i==0){
				if(listvp.size()==single){
					localTextView.setText("单选题");
					localTextView.setBackgroundResource(R.drawable.bg_title);
					p = new LayoutParams(dip2px(this, 14),
							dip2px(this, 30));
					localTextView.setTextSize(2, 8);
				}else if(listvp.size()==more){
					localTextView.setText("多选题");
					localTextView.setBackgroundResource(R.drawable.bg_title);
					p = new LayoutParams(dip2px(this, 14),
							dip2px(this, 30));
					localTextView.setTextSize(2, 8);
				}else if(listvp.size()==judge){
					localTextView.setText("判断题");
					localTextView.setBackgroundResource(R.drawable.bg_title);
					p = new LayoutParams(dip2px(this, 14),
							dip2px(this, 30));
					localTextView.setTextSize(2, 8);
				}
				localTextView.setBackgroundResource(R.drawable.bg_title);
			}
			
			
			if(right_index!=null && right_index.contains(i)){
				localTextView.setBackgroundResource(R.drawable.bg_yuan2);
			}else{
				
				localTextView.setBackgroundResource(R.drawable.bg_yuan_hong);
			}
			
			localTextView.getBackground().setAlpha(80);
			
			
			setParams(i);
			
			mColumnContent.addView(localTextView, i);
		}
	}

	private void setParams(int i) {
		a = (mScreenWidth - dip2px(this, 150)) / 6;


		p.leftMargin = a;

		localTextView.setLayoutParams(p);

	

		if (columnSelectIndex == i) {
			localTextView.setTextColor(Color.WHITE);
			localTextView.getBackground().setAlpha(255);
			// view.getBackground().setAlpha(255);
		}
	}

	private void textSetting() {
		localTextView = new TextView(this);
		p = new LayoutParams(dip2px(this, 30),
				dip2px(this, 30));
		localTextView.setGravity(Gravity.CENTER);
		localTextView.setTextColor(Color.WHITE);
		localTextView.setTextAppearance(this,
				R.style.top_category_scroll_view_item_text);
		
	}
	private void setModelClick() {
		// TODO Auto-generated method stub
		// 获取所有model,清除之前选择的状态
		for (int i = 0; i < mColumnContent.getChildCount(); i++) {
			TextView textView = (TextView) mColumnContent.getChildAt(i);
		//	textView.setTag(i);
			
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
	
	public final static int getWindowsWidth(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	private void selectMode(int position) {

		if (listvp.isEmpty())
			return;

		TextView selTextView = null;
		// 获取所有model,清除之前选择的状态
		for (int i = 0; i < mColumnContent.getChildCount(); i++) {
			TextView textView = (TextView) mColumnContent.getChildAt(i);
			textView.setTextColor(Color.WHITE);
			textView.getBackground().setAlpha(80);
			if (i == position) {
				selTextView = textView;
				// 设置被选中项字体颜色变化为被选中颜色
				selTextView
						.setTextColor(Color.WHITE);
				selTextView.getBackground().setAlpha(255);

			}
		}

		int left = selTextView.getLeft();
		int right = selTextView.getRight();
		int sw = getResources().getDisplayMetrics().widthPixels;
		// 将控件滚动到屏幕的中间位置
		mColumnHorizontalScrollView.scrollTo(
				left - sw / 2 + (right - left) / 2, 0);
	
	}

	private void addListener() {

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
	//			selectMode(arg0);
				
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
				// TODO Auto-generated method stub
				
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
		
		
		ll_test_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
				
			}
		});
	}

	private void init() {

		mContext = TestAnserActivity.this;

		mColumnHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.mColumnHorizontalScrollView_IntegralShop);
		mColumnContent = (LinearLayout) findViewById(R.id.mRadioGroup_content_IntegralShop);

		ll_test_back = (LinearLayout) findViewById(R.id.ll_test_back);
		tv_test_title = (TextView) findViewById(R.id.tv_test_title);

		tv_test_num = (TextView) findViewById(R.id.tv_test_num);
		tv_sum_unm = (TextView) findViewById(R.id.tv_sum_unm);

		tv_time_test = (TextView) findViewById(R.id.tv_time_test);

		mViewPager = (ViewPager) findViewById(R.id.vp_test);

		tv_text_ok = (TextView) findViewById(R.id.tv_text_ok);
		ImageView iv_line = (ImageView) findViewById(R.id.iv_line);

		tv_text_ok.setVisibility(View.INVISIBLE);
		tv_time_test.setVisibility(View.GONE);
		iv_line.setVisibility(View.GONE);

	}

	private void initTest() {
		final ArrayList<QueAndRes> QaList = new ArrayList<QueAndRes>();
		dialog = MyDialog.MyDialogloading(mContext);
		dialog.show();
		
		handle = new Handler(){
			
			
			@Override
			public void handleMessage(Message msg) {
				
				if(msg.what!=1){
					Diaoxian.showerror(mContext, msg.obj.toString());
				}
				 listvp = new ArrayList<Fragment>();
				 right_index = new ArrayList<Integer>();
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(msg.obj.toString());
			
				
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
						item.setIS_Right(joo.getBoolean("IS_Right"));
						aList.add(item);
					}
					
					JSONArray ja2 = j.getJSONArray("itemUser");
					List<ItemUser> userlist = new ArrayList<ItemUser>();
					if(ja2!=null){
						
						for (int z = 0; z < ja2.length(); z++) {
							JSONObject object = ja2.getJSONObject(z);
							ItemUser user = new ItemUser();
							user.setPK_Item(object.getString("PK_Item"));
							
							userlist.add(user);
						}
					}
					
					queAndRes.setItem(aList);
					queAndRes.setItemUser(userlist);
					
					QaList.add(queAndRes);
					
				}
				
				} catch (JSONException e) {
					e.printStackTrace();
				
				}
			getQuestion(QaList);
			}
			protected void getQuestion(ArrayList<QueAndRes> QaList) {
				


				for (int i = 0; i < QaList.size(); i++) {
					QueAndRes question = QaList.get(i);
					
					switch (question.getQue_type()) {
					case 10:// 单选
						List<Map<String, Object>> listdaxuan = new ArrayList<Map<String, Object>>();
						List<String> listItemdaxuan = new ArrayList<String>();
						List<String> trueItem = new ArrayList<String>();
						
						List<Item> danxuanitem = question.getItem();
						List<ItemUser> itemUser = question.getItemUser();
						
						for ( int j = 0; j < danxuanitem.size(); j++) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("num", zimu[j]);
							map.put("option", danxuanitem.get(j)
									.getItem_CONTENT());
							map.put("id", danxuanitem.get(j).getPK_Que());
							map.put("ID", danxuanitem.get(j).getPK_Item());
							
							
							listdaxuan.add(map);
						}
						
						//正确答案
						for ( int j = 0; j < danxuanitem.size(); j++) {
							String PK_item =  danxuanitem.get(j).getPK_Item();
							
							if(danxuanitem.get(j).getIS_Right()){
								trueItem.add(zimu[j]);
							}
							//我的答案
							for (int k = 0; k < itemUser.size(); k++) {
								String pk_item = itemUser.get(k).getPK_Item();
						//		Log.i("pk_item", pk_item);
								if(pk_item.equals(PK_item)){
									
									listItemdaxuan.add(zimu[j]);
								}
							}
						}
						
						
						
						Log.i("size", listdaxuan.size()+"");
						Fragment_show_dan danxuan = new Fragment_show_dan(
								trueItem,listItemdaxuan,
								question.getItemUser(),
								question.getQue_Score(),
								question.getQue_content(), listdaxuan);
					//	记录对错
						if(listItemdaxuan.size()==trueItem.size() && trueItem.containsAll(listItemdaxuan) && listItemdaxuan!=null){
							right_index.add(listvp.size()+1);
				//			Log.i("index", listvp.size()+"");
						}
				          		listvp.add(danxuan);
				          		
				          		single++;
						break;

					case 20:// 多选
						List<Map<String, Object>> listduoxuan = new ArrayList<Map<String, Object>>();
						List<Item> duoxuanoption = question.getItem();
					
						List<String> listItemduoxuan = new ArrayList<String>();
						List<String> trueItem2 = new ArrayList<String>();
						
						List<ItemUser> itemUser2 = question.getItemUser();
						
						
						for (int j = 0; j < duoxuanoption.size(); j++) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("num", zimu[j]);
							map.put("option", duoxuanoption.get(j)
									.getItem_CONTENT());
							map.put("id", duoxuanoption.get(j).getPK_Que());
							map.put("ID", duoxuanoption.get(j).getPK_Item());

							listduoxuan.add(map);
						}
						//正确答案
						for ( int j = 0; j < duoxuanoption.size(); j++) {
							String PK_item =  duoxuanoption.get(j).getPK_Item();
							
							if(duoxuanoption.get(j).getIS_Right()){
								trueItem2.add(zimu[j]);
							}
							//我的答案
							for (int k = 0; k < itemUser2.size(); k++) {
								String pk_item = itemUser2.get(k).getPK_Item();
						//		Log.i("pk_item", pk_item);
								if(pk_item.equals(PK_item)){
									
									listItemduoxuan.add(zimu[j]);
								}
							}
						}


						Fragment_show_duo duoxuan = new Fragment_show_duo(
								trueItem2,listItemduoxuan,question.getItemUser(),
								question.getQue_Score(),
								question.getQue_content(),
								listduoxuan);
						
						if(trueItem2.size()==listItemduoxuan.size() && trueItem2.containsAll(listItemduoxuan) && listduoxuan!=null){
							right_index.add(listvp.size()+2);
						}
					
				listvp.add(duoxuan);
					more++;
						break;
					case 30:// 判断
						List<Map<String, Object>> listpanduan = new ArrayList<Map<String, Object>>();
						
						List<String> listItempandu = new ArrayList<String>();
						List<String> trueItem3 = new ArrayList<String>();
						
						List<Item> panduanoptions = question.getItem();
						List<ItemUser> itemUser3 = question.getItemUser();
					
						for (int j = 0; j < panduanoptions.size(); j++) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("num", zimu[j]);
							map.put("option", panduanoptions.get(j)
									.getItem_CONTENT());
							map.put("ID", panduanoptions.get(j).getPK_Item());
							map.put("id", panduanoptions.get(j).getPK_Que());
							listpanduan.add(map);
						}
						
						//正确答案
						for ( int j = 0; j < panduanoptions.size(); j++) {
							String PK_item =  panduanoptions.get(j).getPK_Item();
							
							if(panduanoptions.get(j).getIS_Right()){
								trueItem3.add(zimu[j]);
							}
							//我的答案
							for (int k = 0; k < itemUser3.size(); k++) {
								String pk_item = itemUser3.get(k).getPK_Item();
						//		Log.i("pk_item", pk_item);
								if(pk_item.equals(PK_item)){
									
									listItempandu.add(zimu[j]);
								}
							}
						}

						
						Fragment_show_pan panduan = new Fragment_show_pan(
								trueItem3,listItempandu,
								question.getItemUser(),
								question.getQue_Score(),
								question.getQue_content(),
								listpanduan);
						
						if(trueItem3.size()==listItempandu.size() && trueItem3.containsAll(listItempandu) && listItempandu!=null){
							right_index.add(listvp.size()+3);
						}
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
							handler.sendEmptyMessage(0);
						}
					}.start();
				

				}
				
//				mViewPager.removeAllViews();
//				mViewPager.setOffscreenPageLimit(listvp.size());
				
				mViewPager.removeAllViews();
				mViewPager.setOffscreenPageLimit(listvp.size());
				if (mAdapter != null) {
					mAdapter.clearFragment();
				}
				mAdapter = new Vpadapter(getSupportFragmentManager(),
						listvp);
				mViewPager.setAdapter(mAdapter);

				tv_test_title.setText(getIntent().getStringExtra("title"));
				dialog.dismiss();
				
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
		
		//查看结果
		new Thread() {
			String net ;
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();

				Message msg = Message.obtain();
				
				if(getIntent().getStringExtra("isTest")!=null){
					
					map.put("examId",  getIntent().getStringExtra("selfId"));
					net = "showExam";
				}else{
					
					map.put("selfId",  getIntent().getStringExtra("selfId"));
					net = "showExamSelf";
				}
				
				String result = HttpUse.messageget("Exam", net, map);
			//	LogUtil.LogShitou(result.toString());

				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = result;
					} else {
						msg.obj = jo.getString("message");
					}
				} catch (JSONException e) {
					msg.obj = e.getMessage();
	//				Diaoxian.showerror(mContext, e.getMessage());
				}

				handle.sendMessage(msg);

			};
		}.start();
		
	
	
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

}
