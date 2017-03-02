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



public class Fragment_show_dan extends Fragment{
	
	private String que_content;
	private List<Map<String, Object>> listdaxuan;
	private Integer score;
	private List<ItemUser> list;
	private List<String> trueItem;
	private List<String> listItemdaxuan;
	private String a="";
	private String b="";

	public Fragment_show_dan(List<String> trueItem, List<String> listItemdaxuan, List<ItemUser> list, Integer score, String que_content,
			List<Map<String, Object>> listdaxuan) {
		
		this.que_content = que_content;
		this.listdaxuan = listdaxuan;
		this.score = score;
		this.list = list;
		this.trueItem = trueItem;
		this.listItemdaxuan = listItemdaxuan;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			
		View v = inflater.inflate(R.layout.fragment_danxuan, null);
		
		TextView tv_quest_title = (TextView) v
				.findViewById(R.id.tv_quest_title);

		MyListView lv_duoxuan = (MyListView) v.findViewById(R.id.lv_duoxuan);
		Button bt_ok_duoxuan = (Button) v.findViewById(R.id.bt_ok_duoxuan);
		TextView tv_r_ans = (TextView) v.findViewById(R.id.tv_r_ans);
		TextView tv_your_ans = (TextView) v.findViewById(R.id.tv_your_ans);
		
		bt_ok_duoxuan.setVisibility(View.GONE);
		tv_r_ans.setVisibility(View.VISIBLE);
		tv_your_ans.setVisibility(View.VISIBLE);
		
		tv_quest_title.setText(Html.fromHtml(que_content
				+ "<font color='#979797'><small><small> (单选题 分数:" + score
				+ "分)</small><small></font>"));
		
		DanxuanAdapter adapter = new DanxuanAdapter(getActivity(), listdaxuan);
		lv_duoxuan.setAdapter(adapter);
		
		for (int i = 0; i <trueItem.size(); i++) {
			
				a = trueItem.get(i)+"";
			
		}
		for(int j =0; j<listItemdaxuan.size();j++){
			
				b = listItemdaxuan.get(j)+"";
			
		}
		tv_r_ans.setText("正确答案是: "+a);
		tv_your_ans.setText("你的答案是: "+b);
		return v;
		
		
	}
}
