package com.mpyf.lening.activity.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import u.aly.bt;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.MyListView;
import com.mpyf.lening.activity.adapter.DanxuanAdapter;
import com.mpyf.lening.activity.adapter.DuoxuanAdapter;
import com.mpyf.lening.interfaces.bean.Parame.ItemRes;
import com.mpyf.lening.interfaces.bean.Parame.ItemUser;
import com.mpyf.lening.interfaces.http.HttpUse;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Fragment_duoxuan extends Fragment {
	private String pk_Que;
	private String que_content;
	private List<Map<String, Object>> listduoxuan;
	private Integer que_Score;
	private Integer state;
	private Integer itemid;
	private ViewPager vp_test;
	private String pK_TruePaper;
	private MyListView lv_duoxuan;
	private String PK_exam;
	private String PK_paper;
	private List<ItemUser> list;
	private String aNS;
	private Checked2Listener listener;
	public Fragment_duoxuan(String aNS, List<ItemUser> list, String pK_TruePaper, String string, String pK_TruePaper2, ViewPager vp_test, int currentItem, Integer que_type,
			Integer que_Score, String pk_Que, String que_content,
			List<Map<String, Object>> listduoxuan) {
		this.aNS = aNS;
		this.pk_Que = pk_Que;
		this.state = state;
		this.que_Score = que_Score;
		this.que_content = que_content;
		this.listduoxuan = listduoxuan;
		this.itemid = itemid;
		this.vp_test =vp_test;
		this.pK_TruePaper = pK_TruePaper;
		this.pK_TruePaper = pK_TruePaper;
		this.PK_paper = PK_paper;
		this.PK_exam = PK_exam;
		this.list = list;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_danxuan, null);
		
		TextView tv_quest_title = (TextView) v
				.findViewById(R.id.tv_quest_title);

		 lv_duoxuan = (MyListView) v.findViewById(R.id.lv_duoxuan);
		lv_duoxuan.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		lv_duoxuan.setDivider(getResources().getDrawable(R.color.dise)); //设置分割线 
		lv_duoxuan.setDividerHeight(18);
		Button bt_ok_duoxuan = (Button) v.findViewById(R.id.bt_ok_duoxuan);

		DuoxuanAdapter adapter = new DuoxuanAdapter(getActivity(), listduoxuan);
		lv_duoxuan.setAdapter(adapter);
		

			tv_quest_title.setText(Html.fromHtml(que_content
					+ "<font color='#979797'><small><small> (多选题 分数:" + que_Score
					+ "分)</small><small></font>"));
			
			bt_ok_duoxuan.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					listener.checked2();
					vp_test.setCurrentItem(vp_test.getCurrentItem()+1);
					
					
					long[] ids = lv_duoxuan.getCheckedItemIds();
					
//					for (int i = 0; i < ids.length; i++) {
//						Log.i("ids", ids[i]+"");
//					}
					
					getForNet(ids);
				}
			});
				return v;
		
		
	}

	protected void getForNet(final long[] ids) {

		new Thread(){
			@Override
			public void run() {
				ItemRes itemRes = new ItemRes();
				
				itemRes.setPK_Paper(PK_paper);
				itemRes.setPK_Exam(PK_exam);
				itemRes.setPK_TruePaper(pK_TruePaper);
				itemRes.setPK_Que(pk_Que);
				List<ItemUser> userlist = new ArrayList<ItemUser>();
				for (int i = 0; i < ids.length; i++) {
					ItemUser user = new ItemUser();
					user.setPK_Item(listduoxuan.get(i).get("ID")
							.toString());
					user.setPK_Que(listduoxuan.get(i).get("id")
							.toString());
					user.setItem_CONTENT(listduoxuan.get(i)
							.get("option").toString());
					userlist.add(user);
				}
				
				itemRes.setItemUser(userlist);
				
				String result = HttpUse.messagepost("Exam",aNS, itemRes);
				
				try {
					JSONObject jo = new JSONObject(result);
				} catch (JSONException e) {
					e.printStackTrace();
					Diaoxian.showerror(getActivity(), e.toString());
				}
			};
		}.start();
	}
	
	
	public interface Checked2Listener{
		public void checked2();
	}
	
	public void setChecked2Listener(Checked2Listener listener){
		this.listener = listener;
	}
}

















