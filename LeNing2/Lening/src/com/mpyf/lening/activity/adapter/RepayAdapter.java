package com.mpyf.lening.activity.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.AsyncBitmapLoader;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.activity.activity.RepayloucengActivity;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.sax.StartElementListener;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

public class RepayAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, Object>> list;

	public RepayAdapter(Context context, List<Map<String, Object>> list) {
		this.context = context;
		this.list = list;
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
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_qarepay,
				null);
		ImageView iv_qarepay_touxiang = (ImageView) view
				.findViewById(R.id.iv_qarepay_touxiang);
		TextView tv_qarepay_name = (TextView) view
				.findViewById(R.id.tv_qarepay_name);
		TextView tv_qarepay_time = (TextView) view
				.findViewById(R.id.tv_qarepay_time);
		TextView tv_qarepay_caina = (TextView) view
				.findViewById(R.id.tv_qarepay_caina);
		TextView tv_qarepay_context = (TextView) view
				.findViewById(R.id.tv_qarepay_context);
		ImageView iv_itemrepay_good = (ImageView) view
				.findViewById(R.id.iv_itemrepay_good);
		TextView tv_itemrepay_good = (TextView) view
				.findViewById(R.id.tv_itemrepay_good);
		ImageView iv_itemrepay_bad = (ImageView) view
				.findViewById(R.id.iv_itemrepay_bad);
		TextView tv_itemrepay_bad = (TextView) view
				.findViewById(R.id.tv_itemrepay_bad);
		LinearLayout ll_itemrepay_repay = (LinearLayout) view
				.findViewById(R.id.ll_itemrepay_repay);
		LinearLayout ll_itemrepay_answers = (LinearLayout) view
				.findViewById(R.id.ll_itemrepay_answers);
		TextView tv_itemrepay_answers1 = (TextView) view
				.findViewById(R.id.tv_itemrepay_answers1);
		TextView tv_itemrepay_answers2 = (TextView) view
				.findViewById(R.id.tv_itemrepay_answers2);
		TextView tv_itemrepay_moreanswers = (TextView) view
				.findViewById(R.id.tv_itemrepay_moreanswers);
		
//		ImageView iv_pic1 = (ImageView) view.findViewById(R.id.iv_pic1);
//		ImageView	iv_pic2 = (ImageView) view.findViewById(R.id.iv_pic2);
//		ImageView iv_pic3 = (ImageView) view.findViewById(R.id.iv_pic3);
//		ImageView	iv_pic4 = (ImageView) view.findViewById(R.id.iv_pic4);
//		ImageView	iv_pic5 = (ImageView) view.findViewById(R.id.iv_pic5);
//		ImageView	iv_pic6 = (ImageView) view.findViewById(R.id.iv_pic6);
//		ImageView iv_pic7 = (ImageView) view.findViewById(R.id.iv_pic7);
//		ImageView iv_pic8 = (ImageView) view.findViewById(R.id.iv_pic8);
//		ImageView iv_pic9 = (ImageView) view.findViewById(R.id.iv_pic9);
//		
		final String queid = list.get(arg0).get("qid").toString();
		final String aid = list.get(arg0).get("id").toString();
		final String uid = list.get(arg0).get("Pk_user").toString();
		final String nickname = list.get(arg0).get("Nickname").toString();
		final String anscontent = list.get(arg0).get("ANS_CONTENT").toString();
		final String goodnum = list.get(arg0).get("GOOD_NUM").toString();
		final String badnum = list.get(arg0).get("BAD_NUM").toString();
		final String time = list.get(arg0).get("AnsTime").toString();
		final String havechild = list.get(arg0).get("is_havechild").toString();
		
		
//		int num = (Integer) list.get(arg0).get("pic_num");
//		HashMap<Integer, ImageView> map = new HashMap<Integer, ImageView>();
//		map.put(1, iv_pic1);
//		map.put(2, iv_pic2);
//		map.put(3, iv_pic3);
//		map.put(4, iv_pic4);
//		map.put(5, iv_pic5);
//		map.put(6, iv_pic6);
//		map.put(7, iv_pic7);
//		map.put(8, iv_pic8);
//		map.put(9, iv_pic9);
//		if (num == 0) {
//			for (int z = 1; z <= 9; z++) {
//				map.get(z).setVisibility(View.GONE);
//			} 
//		}
//		
//		if(num>9){
//			num =9;
//		}
//		if (num > 0 && num <= 9) {
//			
//			for (int i = 1; i <= num; i++) {
//				
//				AsyncBitmapLoader.setImage(map.get(i), Setting.apiUrl
//						+ "new-pages/QA/" + list.get(arg0).get("pic_num") + "/" + i
//						+ ".jpg");
//			}
//			
//			for (int j = 9; j > num; j--) {
//				map.get(j).setVisibility(View.GONE);
//			}
//		} 
//		

		if (havechild.equals("0")) {
			ll_itemrepay_answers.setVisibility(View.GONE);
		}else{
			try {
				JSONArray ja=new JSONArray(list.get(arg0).get("child").toString());
				if (ja.length()==1) {
					tv_itemrepay_answers2.setVisibility(View.GONE);
					tv_itemrepay_moreanswers.setVisibility(View.GONE);
				}else if (ja.length()==2) {
					tv_itemrepay_moreanswers.setVisibility(View.GONE);
				}else{
					tv_itemrepay_moreanswers.setText("查看更多"+(ja.length()-2)+"条回复");
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Diaoxian.showerror(context, e.getMessage());
			}
		}

		if ((Integer) list.get(arg0).get("lzid") == Setting.currentUser
				.getPk_user()) {
			tv_qarepay_caina.setVisibility(View.VISIBLE);
		}
		AsyncBitmapLoader.setRoundImage(iv_qarepay_touxiang, Setting.apiUrl
				+ "new-pages/PersonalPhoto/" + uid + ".jpg");
		tv_qarepay_name.setText(nickname);
		tv_qarepay_context.setText(anscontent);
		tv_itemrepay_good.setText(goodnum);
		tv_itemrepay_bad.setText(badnum);
		tv_qarepay_time.setText(time);

		ll_itemrepay_repay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(context, RepayloucengActivity.class);
				intent.putExtra("ishavechild", havechild);
				intent.putExtra("queid", queid);
				intent.putExtra("ansid", aid);
				intent.putExtra("uid", uid);
				intent.putExtra("name", nickname);
				intent.putExtra("anscontent", anscontent);
				intent.putExtra("time", time);
				intent.putExtra("goodnum", goodnum);
				intent.putExtra("badnum", badnum);
				context.startActivity(intent);
			}
		});

		tv_itemrepay_moreanswers.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, RepayloucengActivity.class);
				intent.putExtra("ishavechild", havechild);
				intent.putExtra("queid", queid);
				intent.putExtra("ansid", aid);
				intent.putExtra("uid", uid);
				intent.putExtra("name", nickname);
				intent.putExtra("anscontent", anscontent);
				intent.putExtra("time", time);
				intent.putExtra("goodnum", goodnum);
				intent.putExtra("badnum", badnum);
				context.startActivity(intent);
			}
		});

		return view;
	}
	
}
