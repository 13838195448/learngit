package com.mpyf.lening.activity.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Quanjubianliang;
import com.mpyf.lening.activity.activity.AddcommentActivity;
import com.mpyf.lening.activity.activity.AddnoteAcitvity;
import com.mpyf.lening.activity.activity.AddqaAcitvity;

public class KejianAdapter extends BaseAdapter {
	

	private Context context;
	private List<Map<String, Object>> list;
	private ListView lv_list;
	
	public KejianAdapter(Context context,ListView lv_list,List<Map<String, Object>> list) {
		this.context=context;
		this.list=list;
		this.lv_list=lv_list;
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
	public View getView(final int arg0, View convertView, ViewGroup arg2) {
		View view;

			view=LayoutInflater.from(context).inflate(R.layout.item_buyedkejian, null);
			
			final LinearLayout rl_buyedkejian_info=(LinearLayout) view.findViewById(R.id.rl_buyedkejian_info);
			final LinearLayout ll_buyedkejian_ctrl=(LinearLayout) view.findViewById(R.id.ll_buyedkejian_ctrl);
			
			TextView tv_buyedkejian_title=(TextView) view.findViewById(R.id.tv_buyedkejian_title);
			LinearLayout ll_buyedkejian_dialog=(LinearLayout) view.findViewById(R.id.ll_buyedkejian_dialog);
			TextView tv_buyedkejian_time=(TextView) view.findViewById(R.id.tv_buyedkejian_time);
			
			TextView tv_kcflxx_addnot=(TextView) view.findViewById(R.id.tv_kcflxx_addnot);
			TextView tv_kcflxx_addqa=(TextView) view.findViewById(R.id.tv_kcflxx_addqa);
			TextView tv_kcflxx_addcomment=(TextView) view.findViewById(R.id.tv_kcflxx_addcomment);
			
		tv_buyedkejian_title.setText(list.get(arg0).get("title").toString());
		tv_buyedkejian_time.setText(list.get(arg0).get("time").toString());
		
		ll_buyedkejian_dialog.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
//				if(Quanjubianliang.kejiancontrl){
//					int fc=lv_list.getFirstVisiblePosition();
					
//					for(int i=0;i<lv_list.getCount();i++){
				for(int i=lv_list.getFirstVisiblePosition();i<lv_list.getLastVisiblePosition();i++){
						View lvview=lv_list.getChildAt(i);
						LinearLayout rl_buyedkejian_info=(LinearLayout) lvview.findViewById(R.id.rl_buyedkejian_info);
						LinearLayout ll_buyedkejian_ctrl=(LinearLayout) lvview.findViewById(R.id.ll_buyedkejian_ctrl);
						ll_buyedkejian_ctrl.setVisibility(View.GONE);
						rl_buyedkejian_info.setVisibility(View.VISIBLE);
					}
					
//					Quanjubianliang.kejiancontrl=false;
//					lv_list.setSelection(fc);
//				}else{
					ll_buyedkejian_ctrl.setVisibility(View.VISIBLE);
					rl_buyedkejian_info.setVisibility(View.GONE);
					Quanjubianliang.kejiancontrl=true;
//				}
				
			}
		});
		
		tv_kcflxx_addnot.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(context, AddnoteAcitvity.class);
				intent.putExtra("resId", list.get(arg0).get("id").toString());
				
				context.startActivity(intent);
			}
		});
		
		tv_kcflxx_addqa.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(context, AddqaAcitvity.class);
				intent.putExtra("resId", list.get(arg0).get("id").toString());
				context.startActivity(intent);
			}
		});
		
		tv_kcflxx_addcomment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(context, AddcommentActivity.class);
				intent.putExtra("resId", list.get(arg0).get("id").toString());
				context.startActivity(intent);
			}
		});
		
//		rl_buyedkejian_info.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View view) {
//				// TODO Auto-generated method stub
//				Intent intent=new Intent(context, StudyActivity.class);
//				intent.putExtra("resId", list.get(arg0).get("id").toString());
//				context.startActivity(intent);
//			}
//		});
		
		
		return view;
	}

}
