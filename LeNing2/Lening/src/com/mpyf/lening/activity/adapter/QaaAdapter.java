package com.mpyf.lening.activity.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.AsyncBitmapLoader;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ImageOptions;
import com.mpyf.lening.Jutil.MyGridview;
import com.mpyf.lening.Jutil.Roundbitmap;
import com.mpyf.lening.activity.activity.QadetilActivity;
import com.mpyf.lening.activity.activity.RepayQueActivity;
import com.mpyf.lening.activity.activity.RepayloucengActivity;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class QaaAdapter extends BaseAdapter {
	

	private Context context;
	private List<Map<String, Object>> list;
	private ImageLoader imageLoader;
	private String qid;
	private Map<String, Object> map;
	private DisplayImageOptions options;
	public QaaAdapter(Context context,List<Map<String, Object>> list) {
		this.context=context;
		this.list=list;
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.icon_defualt) 
		.showImageOnFail(R.drawable.icon_defualt)
		.showImageForEmptyUri(R.drawable.icon_defualt)
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
		final ViewHolder viewHolder;

		if(null==convertView  ){
			view=LayoutInflater.from(context).inflate(R.layout.item_qaa, null);
			 viewHolder= new ViewHolder();
			 viewHolder. iv_qaa_touxiang=(ImageView) view.findViewById(R.id.iv_qaa_touxiang);
			 viewHolder. tv_qaa_name=(TextView) view.findViewById(R.id.tv_qaa_name);
			 viewHolder. tv_qaa_time=(TextView) view.findViewById(R.id.tv_qaa_time);
			 viewHolder. tv_qaa_context=(TextView) view.findViewById(R.id.tv_qaa_context);
			 viewHolder. iv_qaa_lztouxiang=(ImageView) view.findViewById(R.id.iv_qaa_lztouxiang);
			 viewHolder. tv_qaa_lzname=(TextView) view.findViewById(R.id.tv_qaa_lzname);
			 viewHolder. iv_qa_payway=(ImageView) view.findViewById(R.id.iv_qa_payway);
			 viewHolder. tv_qaa_cost=(TextView) view.findViewById(R.id.tv_qaa_cost);
			 viewHolder. tv_qaa_lzcomments=(TextView) view.findViewById(R.id.tv_qaa_lzcomments);
			 viewHolder. tv_qaa_lztitle=(TextView) view.findViewById(R.id.tv_qaa_lztitle);
			viewHolder. gv_pic = (MyGridview) view.findViewById(R.id.gv_pic);
			viewHolder. gv_pic2 = (MyGridview) view.findViewById(R.id.gv_pic2);
			viewHolder.iv_reply = (ImageView) view .findViewById(R.id.iv_reply);
		
            view.setTag(viewHolder);
		}else{
			view = convertView;
            viewHolder = (ViewHolder) view.getTag();
		}
		
		viewHolder.tv_qaa_name.setMaxWidth(200);

		final int is_havechild = (Integer) list.get(arg0).get("is_havechild");
		final int goodnum = (Integer) list.get(arg0).get("GOOD_NUM");
		final int badnum = (Integer) list.get(arg0).get("BAD_NUM");
		final int state = (Integer) list.get(arg0).get("ANS_STATE");
		final int uid = (Integer) list.get(arg0).get("pk_user");
		qid = list.get(arg0).get("PK_Que").toString();
		final String aid = list.get(arg0).get("PK_Ans").toString();
		final String trueName = list.get(arg0).get("trueName").toString();
		final String anscontent = list.get(arg0).get("ANS_CONTENT").toString();
		final String time = list.get(arg0).get("ansTime").toString();
		
		
		viewHolder.tv_qaa_name.setText(list.get(arg0).get("nickname").toString());
		viewHolder.tv_qaa_time.setText(list.get(arg0).get("ansTime").toString());
		viewHolder.tv_qaa_context.setText(list.get(arg0).get("ANS_CONTENT").toString());
		viewHolder.tv_qaa_lzname.setText(list.get(arg0).get("p_Nickname").toString());
		int num = (Integer) list.get(arg0).get("PIC_NUM");
		int[] arr = new int[num];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = i + 1;
		}
		String a = list.get(arg0).get("PK_Ans").toString();
		AgridviewAdapter newGrideView = new AgridviewAdapter(context, arr, a);
		viewHolder.gv_pic.setAdapter(newGrideView);
		

		
		
		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						
							map = new HashMap<String, Object>();
							JSONObject jo = new JSONObject(msg.obj.toString());
//							map.put("id", jo.getString("PK_Que"));
//							map.put("userid", jo.getInt("pk_user"));
//							map.put("UserName", jo.getString("userName"));
//							map.put("Nickname", jo.getString("nickname"));
//							map.put("QUE_CONTENT", jo.getString("QUE_CONTENT"));
//							map.put("REWARD_WAY", jo.getInt("REWARD_WAY"));
//							map.put("REWARD_Num", jo.getString("REWARD_Num"));
//							map.put("Ans_Num", jo.getString("ans_Num"));
//							map.put("QUE_STATE", jo.getInt("QUE_STATE"));
//							map.put("PIC_NUM", jo.getInt("pic_num"));
//							map.put("trueName", jo.getString("trueName"));
					
					viewHolder.	tv_qaa_lzname.setText( jo.getString("trueName"));	
					viewHolder.tv_qaa_cost.setText(jo.getString("REWARD_Num"));
					viewHolder.tv_qaa_lzcomments.setText(jo.getString("ans_Num"));	
					
					int num = jo.getInt("pic_num");
					int[] arr = new int[num];
					for (int i = 0; i < arr.length; i++) {
						arr[i] = i + 1;
					}
					String a = qid;

					AgridviewAdapter newGrideView = new AgridviewAdapter(context, arr, a);
					viewHolder.gv_pic.setAdapter(newGrideView);
					
					
					int state = jo.getInt("QUE_STATE");
					
					if (state == 0) {
						viewHolder.tv_qaa_lztitle.setText(Html
								.fromHtml("<font color='red'>[未解决]</font> "
										+ jo.getString("QUE_CONTENT")));
					} else if (state == 1) {
						viewHolder.tv_qaa_lztitle.setText(Html
								.fromHtml("<font color='#a2d46f'>[已解决]</font> "
										+jo.getString("QUE_CONTENT")));
					}
					ImageLoader imageLoader = ImageLoader.getInstance();
					imageLoader.displayImage(Setting.apiUrl + "new-pages/PersonalPhoto/"+ jo.getInt("pk_user") + ".jpg", viewHolder.iv_qaa_lztouxiang,ImageOptions.options);
					} catch (JSONException e) {
						Diaoxian.showerror(context, e.getMessage());
					}
				} else {
					Diaoxian.showerror(context, msg.obj.toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("queID",qid);
				

				Message msg = new Message();

				String result = HttpUse.messageget("QueAndAns", "getQuestion", map);
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
	

		
	//	AsyncBitmapLoader.setRoundImage(viewHolder.iv_qaa_touxiang, Setting.apiUrl+"new-pages/PersonalPhoto/"+list.get(arg0).get("pk_user")+".jpg");
		//AsyncBitmapLoader.setRoundImage(viewHolder.iv_qaa_lztouxiang, Setting.apiUrl+"new-pages/PersonalPhoto/"+list.get(arg0).get("p_Pk_user")+".jpg");
		imageLoader = ImageLoader.getInstance();
		
		imageLoader.displayImage( Setting.apiUrl+"new-pages/PersonalPhoto/"+list.get(arg0).get("pk_user")+".jpg" ,viewHolder.iv_qaa_touxiang,options);
		imageLoader.displayImage( Setting.apiUrl+"new-pages/PersonalPhoto/"+qid+".jpg" ,viewHolder.iv_qaa_lztouxiang,options);
	
		viewHolder.iv_reply.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
//				Intent intent = new Intent(context, RepayloucengActivity.class);
//				intent.putExtra("ishavechild", is_havechild+"");
//				intent.putExtra("queid", qid);
//				intent.putExtra("ansid", aid);
//				intent.putExtra("uid", uid);
//				intent.putExtra("name", trueName);
//				intent.putExtra("anscontent", anscontent);
//				intent.putExtra("time", time);
//				intent.putExtra("goodnum", goodnum);
//				intent.putExtra("badnum", badnum);
//				intent.putExtra("ANS_STATE", state);
//				context.startActivity(intent);
			}
		});
		return view;
	}
	
	class ViewHolder {
	     
	    public ImageView iv_qaa_touxiang, iv_qaa_lztouxiang, iv_qa_payway,iv_reply;
	    	public TextView tv_qaa_name,
	    		 tv_qaa_time,
	    		tv_qaa_context, tv_qaa_lzname, tv_qaa_cost, tv_qaa_lzcomments, tv_qaa_lztitle;
	    public MyGridview gv_pic,gv_pic2;
	}

}
