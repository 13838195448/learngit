package com.mpyf.lening.activity.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mpyf.lening.R;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class JingyanAdapter extends BaseAdapter {
	private List<Map<String, Object>> list;
	private Context context;

	public JingyanAdapter(Context context, List<Map<String, Object>> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(context, R.layout.item_jingyanitem, null);
		RelativeLayout rl_jingyanitem_month = (RelativeLayout) view
				.findViewById(R.id.rl_jingyanitem_month);
		TextView tv_jingyanitem_month = (TextView) view
				.findViewById(R.id.tv_jingyanitem_month);
		TextView tv_jingyanitem_week = (TextView) view
				.findViewById(R.id.tv_jingyanitem_week);
		TextView tv_jingyanitem_date = (TextView) view
				.findViewById(R.id.tv_jingyanitem_date);
		ImageView iv_jingyanitem_type = (ImageView) view
				.findViewById(R.id.iv_jingyanitem_type);
		TextView tv_jingyanitem_num = (TextView) view
				.findViewById(R.id.tv_jingyanitem_num);
		TextView tv_jingyanitem_rules = (TextView) view
				.findViewById(R.id.tv_jingyanitem_rules);

		tv_jingyanitem_month.setText(getmonth(list.get(position).get("time")
				.toString()));

		if (position > 0) {
			if (getmonth(list.get(position).get("time").toString()).equals(
					getmonth(list.get(position - 1).get("time").toString()))) {
				rl_jingyanitem_month.setVisibility(View.GONE);
			}
		}

		// tv_golditem_week.setText(getFullDateWeekTime(list.get(arg0).get("time").toString()));
		tv_jingyanitem_week.setText(getday(list.get(position).get("time")
				.toString()));

		if (tv_jingyanitem_week.getText().toString().equals("今天")
				|| tv_jingyanitem_week.getText().toString().equals("昨天")) {

			tv_jingyanitem_date.setText(formatdaytime(list.get(position)
					.get("time").toString()));
		} else {
			tv_jingyanitem_date.setText(formattime(list.get(position)
					.get("time").toString()));
		}

		tv_jingyanitem_num.setText("+"
				+ list.get(position).get("Num").toString());

		tv_jingyanitem_rules
				.setText(list.get(position).get("Rules").toString());

		return view;
	}

	private String formattime(String time) {
		if (time.indexOf(" ") != -1) {
			time = time.substring(time.indexOf("-") + 1, time.indexOf(" "));
		}

		return time;
	}

	private String formatdaytime(String time) {
		if (time.indexOf(" ") != -1) {
			time = time.substring(time.indexOf(" ") + 1, time.lastIndexOf(":"));
		}

		return time;
	}

	private String getmonth(String sDate) {
		try {
			String formater = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat format = new SimpleDateFormat(formater);
			Date date = format.parse(sDate);
			Date today = new Date();
			if (today.getMonth() == date.getMonth()) {
				return "本月";
			}
			return (date.getMonth() + 1) + "月";
		} catch (Exception ex) {
			System.out
					.println("TimeUtil getFullDateWeekTime" + ex.getMessage());
			return "";
		}

	}

	public static String getFullDateWeekTime(String sDate) {
		try {
			String formater = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat format = new SimpleDateFormat(formater);
			Date date = format.parse(sDate);
			format.applyPattern("E");
			return format.format(date);
		} catch (Exception ex) {
			System.out
					.println("TimeUtil getFullDateWeekTime" + ex.getMessage());
			return "";
		}
	}

	public static String getday(String sDate) {
		try {
			String formater = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat format = new SimpleDateFormat(formater);
			Date date = format.parse(sDate);
			Date today = new Date();

			if (today.getMonth() == date.getMonth()) {
				if (today.getDate() == date.getDate()) {
					return "今天";
				} else if (today.getDate() - date.getDate() == 1) {
					return "昨天";
				}
			}

			format.applyPattern("E");
			return format.format(date);
		} catch (Exception ex) {
			System.out
					.println("TimeUtil getFullDateWeekTime" + ex.getMessage());
			return "";
		}
	}
}
