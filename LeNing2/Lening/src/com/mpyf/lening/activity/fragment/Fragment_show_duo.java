package com.mpyf.lening.activity.fragment;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.MyListView;
import com.mpyf.lening.activity.adapter.DanxuanAdapter;
import com.mpyf.lening.activity.adapter.DuoxuanAdapter;
import com.mpyf.lening.interfaces.bean.Parame.ItemUser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Fragment_show_duo extends Fragment {

	private MyListView lv_duoxuan;
	private List<String> trueItem2;
	private List<String> listItemduoxuan;
	private List<ItemUser> itemUser;
	private List<Map<String, Object>> listduoxuan;
	private Integer que_Score;
	private String que_content;
	private String a="";
	private String b="";
	private String c="";
	private String d="";

	public Fragment_show_duo(List<String> trueItem2,
			List<String> listItemduoxuan, List<ItemUser> itemUser,
			Integer que_Score, String que_content,
			List<Map<String, Object>> listduoxuan) {
		this.trueItem2 = trueItem2;
		this.listduoxuan = listduoxuan;
		this.listItemduoxuan = listItemduoxuan;
		this.que_content = que_content;
		this.que_Score = que_Score;
		this.itemUser = itemUser;
	}
@Override
public void onResume() {
	super.onResume();
	c="";
	d="";
}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_danxuan, null);
		TextView tv_quest_title = (TextView) v
				.findViewById(R.id.tv_quest_title);

		lv_duoxuan = (MyListView) v.findViewById(R.id.lv_duoxuan);

		Button bt_ok_duoxuan = (Button) v.findViewById(R.id.bt_ok_duoxuan);
		TextView tv_r_ans = (TextView) v.findViewById(R.id.tv_r_ans);
		TextView tv_your_ans = (TextView) v.findViewById(R.id.tv_your_ans);

		bt_ok_duoxuan.setVisibility(View.GONE);
		tv_r_ans.setVisibility(View.VISIBLE);
		tv_your_ans.setVisibility(View.VISIBLE);
		tv_quest_title.setText(Html.fromHtml(que_content
				+ "<font color='#979797'><small><small> (多选题 分数:" + que_Score
				+ "分)</small><small></font>"));
		
		
		DanxuanAdapter adapter = new DanxuanAdapter(getActivity(), listduoxuan);
		lv_duoxuan.setAdapter(adapter);
		
		for (int i = 0; i <trueItem2.size(); i++) {
			
				if(i==trueItem2.size()-1){
					a=trueItem2.get(i);
				}else{
					
					a = trueItem2.get(i)+"、";
				}
			
			c+=a;
		}

		for(int j =0; j<listItemduoxuan.size();j++){
			
			if(j==listItemduoxuan.size()-1){
				b=listItemduoxuan.get(j);
			}else{
				
				b = listItemduoxuan.get(j)+"、";
			}
		
		d+=b;
	}
		tv_r_ans.setText("正确答案是: "+c);
		tv_your_ans.setText("你的答案是: "+d);
		return v;

	}
}
