package com.mpyf.lening.activity.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mpyf.lening.R;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Toupiao_XuanxiangAdapter extends BaseAdapter {
	private List<Map<String, Object>> list;
	private ArrayList<String> list1;
	private ArrayList<String> list2;
	private Context context;
	private HashMap<String, ArrayList<String>> map;
	private int p;

	public Toupiao_XuanxiangAdapter(Context context,
			List<Map<String, Object>> list,
			HashMap<String, ArrayList<String>> map, int position) {
		this.context = context;
		this.list = list;
		this.map = map;
		p = position;

		initData();

	}

	private void initData() {
		list1 = new ArrayList<String>();
		list2 = new ArrayList<String>();
		list2.add(list.get(p).get("option_a").toString());
		list2.add(list.get(p).get("option_b").toString());
		list2.add(list.get(p).get("option_c").toString());
		list2.add(list.get(p).get("option_d").toString());
		list2.add(list.get(p).get("option_e").toString());
		list2.add(list.get(p).get("option_f").toString());
		list2.add(list.get(p).get("option_g").toString());
		list2.add(list.get(p).get("option_h").toString());
		list2.add(list.get(p).get("option_i").toString());
		list2.add(list.get(p).get("option_j").toString());

		for (int i = 0; i < list2.size(); i++) {
			String it = list2.get(i);
			if (!TextUtils.isEmpty(it)) {
				list1.add(it);
			}
		}
		
//		if("啦啦啦".equals(list.get(p).get("option_a"))){
//			System.out.println("啦啦啦====p:"+p+" list:"+list.get(p));
//		}

	}

	@Override
	public int getCount() {
		return list1.size();
	}

	@Override
	public Object getItem(int position) {
		return list1.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.toupiao_xuanxiang,
					null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

//		System.out.println("==投票选项==" + getCount() + "\nposition:" + position+"\n"+list1);
		
//		if(position >= getCount()){
//			return convertView;
//		}
		holder.tv_xuan.setText(list1.get(position));
		String userOption = (String) list.get(p).get("user_option");

		if ((Integer) list.get(p).get("isVote") == 1) {
			// 在这改颜色

			holder.tv_ans.setVisibility(View.VISIBLE);
			switch (position) {
			case 0:
				holder.tv_ans.setText(list.get(p).get("votes_a") + "");

				String percent0 = (String) list.get(p).get("votes_a");
				if (!TextUtils.isEmpty(percent0) && percent0.contains("%")) {
					String pt0 = percent0.replace("%", "");
					int p0 = (int) (Double.parseDouble(pt0) * 100);
					holder.pb.setProgress(p0);
				}

				if (!TextUtils.isEmpty(userOption) && userOption.contains("a")) {
					holder.tv_xuan.setTextColor(context.getResources()
							.getColor(R.color.ciyao));

				} else {
					holder.tv_xuan.setTextColor(Color.BLACK);
				}
				break;
			case 1:
				holder.tv_ans.setText(list.get(p).get("votes_b") + "");
				String percent1 = (String) list.get(p).get("votes_b");
				if (!TextUtils.isEmpty(percent1) && percent1.contains("%")) {
					String pt1 = percent1.replace("%", "");

					int p1 = (int) (Double.parseDouble(pt1) * 100);
					holder.pb.setProgress(p1);
				}
				if (!TextUtils.isEmpty(userOption) && userOption.contains("b")) {
					holder.tv_xuan.setTextColor(context.getResources()
							.getColor(R.color.ciyao));

				} else {
					holder.tv_xuan.setTextColor(Color.BLACK);
				}
				break;
			case 2:
				holder.tv_ans.setText(list.get(p).get("votes_c") + "");
				String percent2 = (String) list.get(p).get("votes_c");
				if (!TextUtils.isEmpty(percent2) && percent2.contains("%")) {
					String pt2 = percent2.replace("%", "");
					int p2 = (int) (Double.parseDouble(pt2) * 100);
					holder.pb.setProgress(p2);
				}
				if (!TextUtils.isEmpty(userOption) && userOption.contains("c")) {
					holder.tv_xuan.setTextColor(context.getResources()
							.getColor(R.color.ciyao));

				} else {
					holder.tv_xuan.setTextColor(Color.BLACK);
				}
				break;
			case 3:
				holder.tv_ans.setText(list.get(p).get("votes_d") + "");

				String percent3 = (String) list.get(p).get("votes_d");
				if (!TextUtils.isEmpty(percent3) && percent3.contains("%")) {
					String pt3 = percent3.replace("%", "");
					int p3 = (int) (Double.parseDouble(pt3) * 100);
					holder.pb.setProgress(p3);
				}
				if (!TextUtils.isEmpty(userOption) && userOption.contains("d")) {
					holder.tv_xuan.setTextColor(context.getResources()
							.getColor(R.color.ciyao));

				} else {
					holder.tv_xuan.setTextColor(Color.BLACK);
				}
				break;
			case 4:
				holder.tv_ans.setText(list.get(p).get("votes_e") + "");
				String percent4 = (String) list.get(p).get("votes_e");
				if (!TextUtils.isEmpty(percent4) && percent4.contains("%")) {
					String pt4 = percent4.replace("%", "");
					int p4 = (int) (Double.parseDouble(pt4) * 100);
					holder.pb.setProgress(p4);
				}
				if (!TextUtils.isEmpty(userOption) && userOption.contains("e")) {
					holder.tv_xuan.setTextColor(context.getResources()
							.getColor(R.color.ciyao));

				} else {
					holder.tv_xuan.setTextColor(Color.BLACK);
				}
				break;
			case 5:
				holder.tv_ans.setText(list.get(p).get("votes_f") + "");

				String percent5 = (String) list.get(p).get("votes_f");
				if (!TextUtils.isEmpty(percent5) && percent5.contains("%")) {
					String pt5 = percent5.replace("%", "");
					int p5 = (int) (Double.parseDouble(pt5) * 100);
					holder.pb.setProgress(p5);
				}
				if (!TextUtils.isEmpty(userOption) && userOption.contains("f")) {
					holder.tv_xuan.setTextColor(context.getResources()
							.getColor(R.color.ciyao));

				} else {
					holder.tv_xuan.setTextColor(Color.BLACK);
				}
				break;
			case 6:
				holder.tv_ans.setText(list.get(p).get("votes_g") + "");
				String percent6 = (String) list.get(p).get("votes_g");
				if (!TextUtils.isEmpty(percent6) && percent6.contains("%")) {
					String pt6 = percent6.replace("%", "");
					int p6 = (int) (Double.parseDouble(pt6) * 100);
					holder.pb.setProgress(p6);
				}
				if (!TextUtils.isEmpty(userOption) && userOption.contains("g")) {
					holder.tv_xuan.setTextColor(context.getResources()
							.getColor(R.color.ciyao));

				} else {
					holder.tv_xuan.setTextColor(Color.BLACK);
				}
				break;
			case 7:
				holder.tv_ans.setText(list.get(p).get("votes_h") + "");
				String percent7 = (String) list.get(p).get("votes_h");
				if (!TextUtils.isEmpty(percent7) && percent7.contains("%")) {
					String pt7 = percent7.replace("%", "");
					int p7 = (int) (Double.parseDouble(pt7) * 100);
					holder.pb.setProgress(p7);
				}
				if (!TextUtils.isEmpty(userOption) && userOption.contains("h")) {
					holder.tv_xuan.setTextColor(context.getResources()
							.getColor(R.color.ciyao));

				} else {
					holder.tv_xuan.setTextColor(Color.BLACK);
				}
				break;
			case 8:
				holder.tv_ans.setText(list.get(p).get("votes_i") + "");
				String percent8 = (String) list.get(p).get("votes_i");
				if (!TextUtils.isEmpty(percent8) && percent8.contains("%")) {
					String pt8 = percent8.replace("%", "");
					int p8 = (int) (Double.parseDouble(pt8) * 100);
					holder.pb.setProgress(p8);
				}
				if (!TextUtils.isEmpty(userOption) && userOption.contains("i")) {
					holder.tv_xuan.setTextColor(context.getResources()
							.getColor(R.color.ciyao));

				} else {
					holder.tv_xuan.setTextColor(Color.BLACK);
				}
				break;
			case 9:
				holder.tv_ans.setText(list.get(p).get("votes_j") + "");
				String percent9 = (String) list.get(p).get("votes_j");
				if (!TextUtils.isEmpty(percent9) && percent9.contains("%")) {
					String pt9 = percent9.replace("%", "");
					int p9 = (int) (Double.parseDouble(pt9) * 100);
					holder.pb.setProgress(p9);
				}
				if (!TextUtils.isEmpty(userOption) && userOption.contains("j")) {
					holder.tv_xuan.setTextColor(context.getResources()
							.getColor(R.color.ciyao));

				} else {
					holder.tv_xuan.setTextColor(Color.BLACK);
				}
				break;
			}
		} else {
			holder.tv_ans.setVisibility(View.GONE);
			if (map.containsKey(p + "")) {
				if (map.get(p + "").contains(position + "")) {
					holder.tv_xuan.setTextColor(Color.BLACK);
				} else {
					holder.tv_xuan.setTextColor(context.getResources()
							.getColor(R.color.main));
				}
			} else {
				holder.tv_xuan.setTextColor(context.getResources().getColor(
						R.color.main));
			}
		}
		return convertView;
	}

	class ViewHolder {
		private TextView tv_xuan;
		private TextView tv_ans;
		private ProgressBar pb;

		public ViewHolder(View convertView) {
			tv_xuan = (TextView) convertView.findViewById(R.id.tv_xuan);
			tv_ans = (TextView) convertView.findViewById(R.id.tv_ans);
			pb = (ProgressBar) convertView.findViewById(R.id.pb);
		}
	}

}
