package com.mpyf.lening.activity.adapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView.CommaTokenizer;
import android.widget.TextView;

import com.mining.app.zxing.MipcaActivityCapture;
import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.AsyncBitmapLoader;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ImageOptions;
import com.mpyf.lening.Jutil.Quanjubianliang;
import com.mpyf.lening.activity.activity.AddPeixun_comActivity;
import com.mpyf.lening.activity.activity.AddcommentActivity;
import com.mpyf.lening.activity.activity.ExamActivity;
import com.mpyf.lening.activity.activity.MycommentActivity;
import com.mpyf.lening.activity.activity.QaActivity;
import com.mpyf.lening.interfaces.bean.Parame.TrainComments;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class MyPeixunAdapter extends BaseAdapter {
	
	private final static int SCANNIN_GREQUEST_CODE = 1;
	private Context context;
	private List<Map<String, Object>> list;
	private DisplayImageOptions options;
	public MyPeixunAdapter(Context context,List<Map<String, Object>> list) {
		this.context=context;
		this.list=list;
		
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
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		View view;

		String[] zhuangtai={"通过","拒绝","审批中"};
		
			view=LayoutInflater.from(context).inflate(R.layout.item_mypeixun, null);
			
			LinearLayout ll_mypeixun_text=(LinearLayout) view.findViewById(R.id.ll_mypeixun_text);
			LinearLayout ll_mypeixun_qiaodao=(LinearLayout) view.findViewById(R.id.ll_mypeixun_qiaodao);
			ImageView iv_mypeixun_top=(ImageView) view.findViewById(R.id.iv_mypeixun_top);
			ImageView iv_mypeixun_carmer=(ImageView) view.findViewById(R.id.iv_mypeixun_carmer);
			TextView tv_mypeixun_title=(TextView) view.findViewById(R.id.tv_mypeixun_title);
			TextView tv_mypeixun_date=(TextView) view.findViewById(R.id.tv_mypeixun_date);
			TextView tv_mypeixun_zhuangtai=(TextView) view.findViewById(R.id.tv_mypeixun_zhuangtai);
			TextView tv_mypeixun_qiandao=(TextView) view.findViewById(R.id.tv_mypeixun_qiandao);
			
		
			final String id = list.get(arg0).get("id").toString();
			
		tv_mypeixun_title.setText(list.get(arg0).get("title").toString());
		tv_mypeixun_date.setText(list.get(arg0).get("time").toString());
		tv_mypeixun_zhuangtai.setText(zhuangtai[(Integer) list.get(arg0).get("Application_Type")-1]);
		
		if((Integer) list.get(arg0).get("Application_Type")!=1){
			ll_mypeixun_text.setVisibility(View.INVISIBLE);
			ll_mypeixun_qiaodao.setVisibility(View.INVISIBLE);
		}else{
			ll_mypeixun_text.setVisibility(View.VISIBLE);
			ll_mypeixun_qiaodao.setVisibility(View.VISIBLE);
			
			ll_mypeixun_text.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
				context.startActivity(new Intent(context, ExamActivity.class));
				}
			});
			
			String endtime=list.get(arg0).get("endtime").toString();
			Date date=fromattime(endtime);
			Date today=new Date();
			
			
			if(today.getTime()>=date.getTime()){
				iv_mypeixun_carmer.setVisibility(View.GONE);
				tv_mypeixun_qiandao.setText("评价");
				
				ll_mypeixun_qiaodao.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						
						Intent intent = new Intent(context,AddPeixun_comActivity.class);
						intent.putExtra("id", id);
						context.startActivity(intent);
						
//						final Handler handler=new Handler(){
//							@Override
//							public void handleMessage(Message msg) {
//								if(msg.what==1){
//									try {
//										
//											JSONObject jo=new JSONObject(msg.obj.toString());
//											Map<String,Object> map=new HashMap<String, Object>();
//											
//											map.put("id", jo.getString("PK_Com"));
//											//map.put("PK_Course", jo.getString("PK_Course"));
//											map.put("PK_Res", jo.getString("PK_Act"));
//											map.put("name", jo.getString("nickname"));
//											
//											map.put("time", jo.getString("commTime"));
//											
//											map.put("context", jo.getString("COMMENT_CONTENT"));
//											//map.put("from", "来自课件： "+jo.getString("resName"));
//								 			map.put("srcoe", jo.getInt("COMMENT_LEVEL"));
//											//map.put("ImagePath",  Setting.apiUrl+"new-pages/PersonalPhoto/"+Setting.currentUser.getPk_user()+".jpg");
//										
//										
//										
//										Intent intent=new Intent(context, AddcommentActivity.class);
//										intent.putExtra("id", map.get("id").toString());
//										intent.putExtra("resId", map.get("PK_Res").toString());
//										intent.putExtra("srcoe",(Integer) map.get("srcoe"));
//										intent.putExtra("context", map.get("context").toString());
//										
//										context.startActivity(intent);
//										
////										MyCommentAdapter adapter=new MyCommentAdapter(MycommentActivity.this, list);
////										lv_list_info.setAdapter(adapter);
//									} catch (JSONException e) {
//										Diaoxian.showerror(context, e.getMessage());
//									}
//								}else{
//									Diaoxian.showerror(context, msg.obj.toString());
//								}
//							}
//						};
//						
//						new Thread(){
//							@Override
//							public void run() {
//								Map<String,Object> map=new HashMap<String, Object>();
//								map.put("pk_act",id);   
//								
//								Message msg=new Message();
//								String result=HttpUse.messageget("TrainACt", "getTrianComments", map);
//								try {
//									JSONObject jo=new JSONObject(result);
//									if(jo.getBoolean("result")){
//										msg.what=1;
//										msg.obj=jo.getString("data");
//									}else{
//										msg.obj=jo.getString("message");
//									}
//								} catch (JSONException e) {
//									msg.obj=e.getMessage();
//								}
//								handler.sendMessage(msg);
//							};
//						}.start();
						
					}
				});
			}else{
				
				ll_mypeixun_qiaodao.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View view) {
						// TODO Auto-generated method stub
						
						Intent intent = new Intent();
						intent.setClass(context, MipcaActivityCapture.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						((Activity) context).startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
					}
				});
			}
		}
			
		
		
		//AsyncBitmapLoader.setImage(iv_mypeixun_top, Setting.apiUrl+list.get(arg0).get("picUrl").toString());
		ImageLoader imageLoader = ImageLoader.getInstance();
imageLoader.displayImage(Setting.apiUrl+list.get(arg0).get("picUrl").toString(), iv_mypeixun_top, options);
		
		iv_mypeixun_top.setAlpha(150);
		
		
		return view;
	}
	
	private Date fromattime(String time){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
		Date date=null;
		SimpleDateFormat fromat=new SimpleDateFormat("HH:mm");
		try {
			date = df.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return date;
		
	}

}
