package com.mpyf.lening.activity.adapter;

import java.util.List;
import java.util.Map;

import com.mpyf.lening.R;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchedAdapter extends BaseAdapter {

	private List<Map<String, Object>> list;
	private Context context;

	private String lvse;
	
	public SearchedAdapter(Context context, String lvse,List<Map<String, Object>> list) {
		this.context = context;
		this.lvse=lvse;
		this.list = list;
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
		View view = LayoutInflater.from(context).inflate(
				R.layout.item_searched, null);

		String newlvse="<font color='#41d092'>"+lvse+"</font>";
		
		String title=list.get(arg0).get("title").toString();
		
		TextView tv_searched_title = (TextView) view
				.findViewById(R.id.tv_searched_title);
		
		if(title.indexOf(lvse)!=-1){
			title=title.replace(lvse, newlvse);
			tv_searched_title.setText(Html.fromHtml(title));
			return view;
		}else
		
		if (title.indexOf(lvse.toLowerCase())!=-1) {
			title=title.replace(lvse.toLowerCase(), "<font color='#41d092'>"+lvse.toLowerCase()+"</font>");
			tv_searched_title.setText(Html.fromHtml(title));
			return view;
		}else
		
		if (title.indexOf(lvse.toUpperCase())!=-1) {
			title=title.replace(lvse.toUpperCase(), "<font color='#41d092'>"+lvse.toUpperCase()+"</font>");
			tv_searched_title.setText(Html.fromHtml(title));
			return view;
		}else{
			tv_searched_title.setText(Html.fromHtml(title));
		}
		
//		tv_searched_title.loadDataWithBaseURL(null, title, "text/html", "utf-8",null);
		
		return view;
	}

}
