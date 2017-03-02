package com.mpyf.lening.activity.fragment;

import java.util.List;
import java.util.Map;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.MyListView;
import com.mpyf.lening.activity.adapter.DanxuanAdapter;
import com.mpyf.lening.interfaces.bean.Parame.ItemUser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Fragment_show_pan extends Fragment {
	private List<String> trueItem3;
	private	List<String> listItempandu;
	private List<ItemUser> itemUser;
	private Integer que_Score;
	private String que_content;
	private String a="";
	private String b="";
	
	private List<Map<String, Object>> listpanduan;
	public Fragment_show_pan(List<String> trueItem3,
			List<String> listItempandu, List<ItemUser> itemUser,
			Integer que_Score, String que_content,
			List<Map<String, Object>> listpanduan) {
		this.trueItem3 = trueItem3;
		this.listItempandu = listItempandu;
		this.itemUser = itemUser;
		this.que_Score = que_Score;
		this.que_content = que_content;
		this.listpanduan = listpanduan;
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			
		View v = inflater.inflate(R.layout.fragment_panduan, null);
		
		TextView tv_quest_title = (TextView) v
				.findViewById(R.id.tv_quest_title);
		
		TextView tv_r_ans = (TextView) v.findViewById(R.id.tv_r_ans);
		TextView tv_your_ans = (TextView) v.findViewById(R.id.tv_your_ans);

		tv_r_ans.setVisibility(View.VISIBLE);
		tv_your_ans.setVisibility(View.VISIBLE);
		
		tv_quest_title.setText(Html.fromHtml(que_content
				+ "<font color='#979797'><small><small> (判断题 分数:" + que_Score
				+ "分)</small><small></font>"));
		
		
		for (int i = 0; i <trueItem3.size(); i++) {
			
			a = trueItem3.get(i)+"";
		
	}
	for(int j =0; j<listItempandu.size();j++){
		
			b = listItempandu.get(j)+"";
		
	}
	
	if(a.equals("A")){
		
		tv_r_ans.setText("正确答案是: "+"对");
	}else{
		tv_r_ans.setText("正确答案是: "+"错");
	}
	
	if(b.equals("A")){
		tv_your_ans.setText("你的答案是: "+"对");
	}else if(b.equals("B")){
		
		tv_your_ans.setText("你的答案是: "+"错");
	}else {
		tv_your_ans.setText("你的答案是: "+"");
	}
		return v;
	}
}
