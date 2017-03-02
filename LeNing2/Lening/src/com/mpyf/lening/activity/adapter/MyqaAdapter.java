package com.mpyf.lening.activity.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.AsyncBitmapLoader;
import com.mpyf.lening.Jutil.ImageOptions;
import com.mpyf.lening.Jutil.Roundbitmap;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class MyqaAdapter extends BaseAdapter {
	

	private Context context;
	private List<Map<String, Object>> list;
	private DisplayImageOptions options;
	public MyqaAdapter(Context context,List<Map<String, Object>> list) {
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
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		View view;
		ViewHolder viewHolder;

		if(null==convertView  ){
			view=LayoutInflater.from(context).inflate(R.layout.item_myqa, null);
			
			ImageView iv_myqa_touxiang=(ImageView) view.findViewById(R.id.iv_myqa_touxiang);
			ImageView iv_myqa_payway=(ImageView) view.findViewById(R.id.iv_myqa_payway);
			TextView tv_myqa_name=(TextView) view.findViewById(R.id.tv_myqa_name);
			TextView tv_myqa_cost=(TextView) view.findViewById(R.id.tv_myqa_cost);
			TextView tv_myqa_comments=(TextView) view.findViewById(R.id.tv_myqa_comments);
			TextView tv_myqa_title=(TextView) view.findViewById(R.id.tv_myqa_title);
			TextView tv_myqa_answer=(TextView) view.findViewById(R.id.tv_myqa_answer);
			
			viewHolder = new ViewHolder(iv_myqa_touxiang,iv_myqa_payway,tv_myqa_name,tv_myqa_cost,tv_myqa_comments,tv_myqa_title,tv_myqa_answer);
            view.setTag(viewHolder);
		}else{
			view = convertView;
            viewHolder = (ViewHolder) view.getTag();
		}
		
		viewHolder.tv_myqa_name.setMaxWidth(200);
		viewHolder.tv_myqa_name.setText(list.get(arg0).get("Nickname").toString());
		viewHolder.tv_myqa_cost.setText(list.get(arg0).get("REWARD_Num").toString());
		viewHolder.tv_myqa_comments.setText(list.get(arg0).get("Ans_Num").toString());
		
		int stae=(Integer) list.get(arg0).get("QUE_STATE");
		
		if(stae==0){
			viewHolder.tv_myqa_title.setText(Html.fromHtml("<font color='red'>[未解决]</font> "+list.get(arg0).get("QUE_CONTENT").toString()));
			viewHolder.tv_myqa_answer.setVisibility(View.GONE);
		}else if(stae==1){
			viewHolder.tv_myqa_title.setText(Html.fromHtml("<font color='#41d092'>[已解决]</font> "+list.get(arg0).get("QUE_CONTENT").toString()));
//			viewHolder.tv_myqa_answer.setText(list.get(arg0).get("Ans_Num").toString());
		}
		
		int payway=(Integer) list.get(arg0).get("REWARD_WAY");
		
		if (payway==1) {
			viewHolder.iv_myqa_payway.setImageResource(R.drawable.know_icon_le);
		}else if (payway==2) {
			viewHolder.iv_myqa_payway.setImageResource(R.drawable.know_icon_jin);
		}
		
	//	AsyncBitmapLoader.setRoundImage(viewHolder.iv_myqa_touxiang, Setting.apiUrl+"new-pages/PersonalPhoto/"+Setting.currentUser.getPk_user()+".jpg");
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(Setting.apiUrl+"new-pages/PersonalPhoto/"+Setting.currentUser.getPk_user()+".jpg", viewHolder.iv_myqa_touxiang, options);
		
		
		return view;
	}
	
	class ViewHolder {
	     
	    public ViewHolder(ImageView iv_myqa_touxiang,ImageView iv_myqa_payway,TextView tv_myqa_name,TextView tv_myqa_cost,TextView tv_myqa_comments,TextView tv_myqa_title,TextView tv_myqa_answer){
	        this.iv_myqa_touxiang = iv_myqa_touxiang;
	        this.iv_myqa_payway=iv_myqa_payway;
	        this.tv_myqa_name = tv_myqa_name;
	        this.tv_myqa_cost = tv_myqa_cost;
	        this.tv_myqa_comments = tv_myqa_comments;
	        this.tv_myqa_title = tv_myqa_title;
	        this.tv_myqa_answer=tv_myqa_answer;
	    }       
	    
	    ImageView iv_myqa_touxiang,iv_myqa_payway;
	    TextView tv_myqa_name,tv_myqa_cost,tv_myqa_comments,tv_myqa_title,tv_myqa_answer;
	}


}
