package com.mpyf.lening.activity.adapter;

import java.util.List;
import java.util.Map;

import com.mpyf.lening.R;
import com.mpyf.lening.activity.activity.RepayloucengActivity;
import com.mpyf.lening.interfaces.http.Setting;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RepayloucengAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, Object>> list;
	public RepayloucengAdapter(Context context,List<Map<String, Object>> list) {
		this.context=context;
		this.list=list;
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
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(context).inflate(
				R.layout.item_repaylouceng, null);
		
		TextView tv_repaylouceng_answers=(TextView) view.findViewById(R.id.tv_repaylouceng_answers);
		String name=list.get(arg0).get("trueName").toString();
		String content=list.get(arg0).get("ANS_CONTENT").toString();
		String time=list.get(arg0).get("ansTime").toString();
		String pname = list.get(arg0).get("p_TrueName").toString();
		String source="<font color='#a2d46f'>"+name+"</font>  »Ø¸´    "+pname+": "+content+"<font color='#979797'>   "+time.substring(0, time.indexOf(" "))+"</font>";
		
		tv_repaylouceng_answers.setText(Html.fromHtml(source));
		
		return view;
	}

}
