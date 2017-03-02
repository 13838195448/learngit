package com.mpyf.lening.activity.adapter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.AsyncBitmapLoader;
import com.mpyf.lening.Jutil.ImageOptions;
import com.mpyf.lening.Jutil.Roundbitmap;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.ImageLoader;

public class QaAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, Object>> list;
	private ImageView iv_pic1;
	private ImageView iv_pic2;
	private ImageView iv_pic3;
	private ImageView iv_pic4;
	private ImageView iv_pic5;
	private ImageView iv_pic6;
	private ImageView iv_pic7;
	private ImageView iv_pic8;
	private ImageView iv_pic9;
	private HashMap<Integer, ImageView> map;
	private int position;
	private ViewHolder viewHolder;

	public QaAdapter(Context context, List<Map<String, Object>> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		View view;
		
		ViewHolder viewHolder;
//		if (null == convertView) {
			view = LayoutInflater.from(context).inflate(R.layout.item_qa, null);

			ImageView iv_qaitem_touxiang = (ImageView) view
					.findViewById(R.id.iv_qaitem_touxiang);
			ImageView iv_qa_payway = (ImageView) view
					.findViewById(R.id.iv_qa_payway);
			TextView tv_qaitem_name = (TextView) view
					.findViewById(R.id.tv_qaitem_name);
			TextView tv_qaitem_cost = (TextView) view
					.findViewById(R.id.tv_qaitem_cost);
			TextView tv_qaitem_comments = (TextView) view
					.findViewById(R.id.tv_qaitem_comments);
			TextView tv_qaitem_title = (TextView) view
					.findViewById(R.id.tv_qaitem_title);
			iv_pic1 = (ImageView) view.findViewById(R.id.iv_pic1);
			iv_pic2 = (ImageView) view.findViewById(R.id.iv_pic2);
			iv_pic3 = (ImageView) view.findViewById(R.id.iv_pic3);
			iv_pic4 = (ImageView) view.findViewById(R.id.iv_pic4);
			iv_pic5 = (ImageView) view.findViewById(R.id.iv_pic5);
			iv_pic6 = (ImageView) view.findViewById(R.id.iv_pic6);
			iv_pic7 = (ImageView) view.findViewById(R.id.iv_pic7);
			iv_pic8 = (ImageView) view.findViewById(R.id.iv_pic8);
			iv_pic9 = (ImageView) view.findViewById(R.id.iv_pic9);

			viewHolder = new ViewHolder(iv_qaitem_touxiang, iv_qa_payway,
					iv_pic1, iv_pic2, iv_pic3, iv_pic4, iv_pic5, iv_pic6,
					iv_pic7, iv_pic8, iv_pic9, tv_qaitem_name, tv_qaitem_cost,
					tv_qaitem_comments, tv_qaitem_title);
			view.setTag(viewHolder);
//		} else {
//			view = convertView;
//			viewHolder = (ViewHolder) view.getTag();
//		}
		
		view.setTag(arg2);
		viewHolder.tv_qaitem_name.setMaxWidth(200);
		viewHolder.tv_qaitem_name.setText(list.get(arg0).get("Nickname")
				.toString());
		viewHolder.tv_qaitem_cost.setText(list.get(arg0).get("REWARD_Num")
				.toString());
		viewHolder.tv_qaitem_comments.setText(list.get(arg0).get("Ans_Num")
				.toString());

		int stae = (Integer) list.get(arg0).get("QUE_STATE");
		
		
		if (stae == 0) {
			viewHolder.tv_qaitem_title.setText(Html
					.fromHtml("<font color='red'>[未解决]</font> "
							+ list.get(arg0).get("QUE_CONTENT").toString()));
		} else if (stae == 1) {
			viewHolder.tv_qaitem_title.setText(Html
					.fromHtml("<font color='#a2d46f'>[已解决]</font> "
							+ list.get(arg0).get("QUE_CONTENT").toString()));
		}

		int payway = (Integer) list.get(arg0).get("REWARD_WAY");

		if (payway == 1) {
			viewHolder.iv_qa_payway.setImageResource(R.drawable.know_icon_le);
		} else if (payway == 2) {
			viewHolder.iv_qa_payway.setImageResource(R.drawable.know_icon_jin);
		}

	//	AsyncBitmapLoader.setRoundImage(viewHolder.iv_qaitem_touxiang,Setting.apiUrl + "new-pages/PersonalPhoto/"+ list.get(arg0).get("userid") + ".jpg");
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(Setting.apiUrl + "new-pages/PersonalPhoto/"+ list.get(arg0).get("userid") + ".jpg", viewHolder.iv_qaitem_touxiang, ImageOptions.options);
		
		viewHolder.iv_qaitem_touxiang.setImageBitmap(Roundbitmap
				.toRoundBitmap(viewHolder.iv_qaitem_touxiang));

		int num = (Integer) list.get(arg0).get("PIC_NUM");
	
//		viewHolder.iv_pic2.setTag(arg0);
//		if(num>0){
//			
//			viewHolder.iv_pic2.setTag(Setting.apiUrl + "new-pages/QA/"+list.get(arg0).get("id")+"/1.jpg");
//		}

		// AsyncBitmapLoader.setImage(viewHolder.iv_pic1,
		// Setting.apiUrl+"new-pages/QA/"+list.get(arg0).get("id")+"/1.jpg");
		map = new HashMap<Integer, ImageView>();
		map.put(1, viewHolder.iv_pic1);
		map.put(2, viewHolder.iv_pic2);
		map.put(3, viewHolder.iv_pic3);
		map.put(4, viewHolder.iv_pic4);
		map.put(5, viewHolder.iv_pic5);
		map.put(6, viewHolder.iv_pic6);
		map.put(7, viewHolder.iv_pic7);
		map.put(8, viewHolder.iv_pic8);
		map.put(9, viewHolder.iv_pic9);
		
		// if(!(num>0&&viewHolder.iv_pic1.getTag().equals("new-pages/QA/"+list.get(arg0).get("id")+"/1.jpg"))){
		
	
		 if (num == 0) {
			for (int z = 1; z <= 9; z++) {
				map.get(z).setVisibility(View.GONE);
			} 
		 }
			 
			 if(num>9){
				 num =9;
			 }
			 if (num > 0 && num <= 9) {
				 
				 for (int i = 1; i <= num; i++) {
					 
					 AsyncBitmapLoader.setImage(map.get(i), Setting.apiUrl
							 + "new-pages/QA/" + list.get(arg0).get("id") + "/" + i
							 + ".jpg");
					 
				 }
				 
				 for (int j = 9; j > num; j--) {
					 map.get(j).setVisibility(View.GONE);
				 }
			 } 
	
		return view;
	}

	class ViewHolder {

		
		public ViewHolder(ImageView iv_qaitem_touxiang, ImageView iv_qa_payway,
				ImageView iv_pic1, ImageView iv_pic2, ImageView iv_pic3,
				ImageView iv_pic4, ImageView iv_pic5, ImageView iv_pic6,
				ImageView iv_pic7, ImageView iv_pic8, ImageView iv_pic9,
				TextView tv_qaitem_name, TextView tv_qaitem_cost,
				TextView tv_qaitem_comments, TextView tv_qaitem_title) {
			this.iv_qaitem_touxiang = iv_qaitem_touxiang;
			this.iv_qa_payway = iv_qa_payway;
			this.tv_qaitem_name = tv_qaitem_name;
			this.tv_qaitem_cost = tv_qaitem_cost;
			this.tv_qaitem_comments = tv_qaitem_comments;
			this.tv_qaitem_title = tv_qaitem_title;
			this.iv_pic1 = iv_pic1;
			this.iv_pic2 = iv_pic2;
			this.iv_pic3 = iv_pic3;
			this.iv_pic4 = iv_pic4;
			this.iv_pic5 = iv_pic5;
			this.iv_pic6 = iv_pic6;
			this.iv_pic7 = iv_pic7;
			this.iv_pic8 = iv_pic8;
			this.iv_pic9 = iv_pic9;

		}

		ImageView iv_qaitem_touxiang, iv_qa_payway, iv_pic1, iv_pic2, iv_pic3,
				iv_pic4, iv_pic5, iv_pic6, iv_pic8, iv_pic7, iv_pic9;
		TextView tv_qaitem_name, tv_qaitem_cost, tv_qaitem_comments,
				tv_qaitem_title;
	}

}
