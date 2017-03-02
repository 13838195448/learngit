package com.mpyf.lening.activity.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import u.aly.aw;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.MyListView;
import com.mpyf.lening.Jutil.Writesaved;
import com.mpyf.lening.activity.activity.FatieActivity;
import com.mpyf.lening.activity.adapter.DanxuanAdapter;
import com.mpyf.lening.interfaces.bean.Parame.ItemRes;
import com.mpyf.lening.interfaces.bean.Parame.ItemUser;
import com.mpyf.lening.interfaces.http.HttpUse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Fragment_danxuan extends Fragment {

	private String pk_Que;
	private String que_content;
	private List<Map<String, Object>> listdaxuan;
	private Integer score;
	private Integer state;
	private Integer itemid;
	private ViewPager vp_test;
	private String pK_TruePaper;
	private String PK_exam;
	private String PK_paper;
	private List<ItemUser> list;
	private CheckedListener listener;
	private String aNS;

	public Fragment_danxuan(String aNS, List<ItemUser> list, String PK_exam,
			String PK_paper, String pK_TruePaper, ViewPager vp_test,
			Integer itemid, Integer state, Integer score, String pk_Que,
			String que_content, List<Map<String, Object>> listdaxuan) {
	
		this.aNS = aNS;
		this.pk_Que = pk_Que;
		this.state = state;
		this.score = score;
		this.que_content = que_content;
		this.listdaxuan = listdaxuan;
		this.itemid = itemid;
		this.vp_test = vp_test;
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

		MyListView lv_duoxuan = (MyListView) v.findViewById(R.id.lv_duoxuan);
		Button bt_ok_duoxuan = (Button) v.findViewById(R.id.bt_ok_duoxuan);
		lv_duoxuan.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		lv_duoxuan.setDivider(null);
	
		DanxuanAdapter adapter = new DanxuanAdapter(getActivity(), listdaxuan);
		lv_duoxuan.setAdapter(adapter);

		tv_quest_title.setText(Html.fromHtml(que_content
				+ "<font color='#979797'><small><small> (单选题 分数:" + score
				+ "分)</small><small></font>"));
		// "<font color='#4D4D4D' size='30'>"+content+"</font>"
		bt_ok_duoxuan.setVisibility(View.GONE);
		lv_duoxuan.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {

				listener.checked();
				vp_test.setCurrentItem(vp_test.getCurrentItem() + 1);//这两行顺序不能搞乱
				new Thread() {
					private JSONObject jo;

					@Override
					public void run() {

						ItemRes itemRes = new ItemRes();
						
						itemRes.setPK_Paper(PK_paper);
						itemRes.setPK_Exam(PK_exam);
						itemRes.setPK_TruePaper(pK_TruePaper);
						itemRes.setPK_Que(pk_Que);
						List<ItemUser> userlist = new ArrayList<ItemUser>();
						for (int i = 0; i < 1; i++) {
							ItemUser user = new ItemUser();
							user.setPK_Item(listdaxuan.get(arg2).get("ID")
									.toString());
							user.setPK_Que(listdaxuan.get(arg2).get("id")
									.toString());
							user.setItem_CONTENT(listdaxuan.get(arg2)
									.get("option").toString());
							userlist.add(user);
						}

						itemRes.setItemUser(userlist);

						String result = HttpUse.messagepost("Exam",
								aNS, itemRes);
				//		Log.i("res", result);
						try {
							jo = new JSONObject(result);
						} catch (JSONException e) {
							e.printStackTrace();
							Diaoxian.showerror(getActivity(), e.toString());
						}
					};
				}.start();

			}
		});

		return v;
	}
	
	public interface CheckedListener{
		public void checked();
	}
	
	public void setOnCheckedListener(CheckedListener listener){
		this.listener = listener;
	}
}
