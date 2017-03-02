package com.mpyf.lening.activity.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.interfaces.bean.Parame.ItemRes;
import com.mpyf.lening.interfaces.bean.Parame.ItemUser;
import com.mpyf.lening.interfaces.http.HttpUse;

public class Fragment_panduan extends Fragment {

	private String pk_Que;
	private String que_content;
	private List<Map<String, Object>> listpanduan;
	private Integer score;
	private Integer state;
	private Integer itemid;
	private ViewPager vp_test;
	private String pK_TruePaper;
	private String PK_exam;
	private String PK_paper;
	private List<ItemUser> list;
	private String aNS;
	
	private Checked3Listener listener;

	public Fragment_panduan(String aNS, List<ItemUser> list, String PK_exam,
			String PK_paper, String pK_TruePaper, ViewPager vp_test,
			int itemid, Integer state, Integer score,
			String pk_Que, String que_content,
			List<Map<String, Object>> listpanduan) {
		this.aNS = aNS;
		this.pk_Que = pk_Que;
		this.state = state;
		this.score = score;
		this.que_content = que_content;
		this.listpanduan = listpanduan;
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

		View v = inflater.inflate(R.layout.fragment_panduan, null);

		TextView tv_quest_title = (TextView) v
				.findViewById(R.id.tv_quest_title);


		tv_quest_title.setText(Html.fromHtml(que_content
				+ "<font color='#979797'><small><small> (ÅÐ¶Ï ·ÖÊý:" + score
				+ "·Ö)</small><small></font>"));
		
		RadioGroup rg_btn = (RadioGroup) v.findViewById(R.id.rg_btn);
//		RadioButton bt_right = (RadioButton) v.findViewById(R.id.bt_right);
		
		rg_btn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			private int index;

			@Override
			public void onCheckedChanged(RadioGroup arg0, final int arg1) {
			
				if(arg1==R.id.bt_right){
					index = 0;
				}else{
					index =1 ;
				}
					listener.checked3();
					vp_test.setCurrentItem(vp_test.getCurrentItem() + 1);
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
								user.setPK_Item(listpanduan.get(index).get("ID")
										.toString());
								user.setPK_Que(listpanduan.get(index).get("id")
										.toString());
								user.setItem_CONTENT(listpanduan.get(index)
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
	
	public interface Checked3Listener{
		public void checked3();
	}
	
	public void setChecked3Listener(Checked3Listener listener){
		this.listener = listener;
	}
}
