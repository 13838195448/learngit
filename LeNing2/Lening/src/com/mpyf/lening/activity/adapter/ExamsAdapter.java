package com.mpyf.lening.activity.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.activity.activity.ZicetestActivity;
import com.mpyf.lening.activity.adapter.ExamselfAdapter.ViewHolder;
import com.mpyf.lening.interfaces.http.HttpUse;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExamsAdapter extends BaseAdapter {

	private List<Map<String, Object>> list;
	private Context context;
	
	private int state;
	private boolean start;
	private Date cur;
	private Date end;
	private String id;
	private Handler handler;
	private List<Integer> changeList;
	
	public ExamsAdapter(Context context, List<Map<String, Object>> list) {
		this.context = context;
		this.list = list;
		changeList = new ArrayList<Integer>();
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
	public View getView(final int position, View converView, ViewGroup arg2) {

		start = false;

		final ViewHolder holder = new ViewHolder();
		// if (converView == null) {
		View v = View.inflate(context, R.layout.item_zice, null);
		holder.v_xiantiao = v.findViewById(R.id.v_xiantiao);
		holder.tv_zice_title = (TextView) v.findViewById(R.id.tv_zice_title);
		holder.tv_zice_long = (TextView) v.findViewById(R.id.tv_zice_long);
		holder.tv_zice_start = (TextView) v.findViewById(R.id.tv_zice_start);
		holder.tv_zice_end = (TextView) v.findViewById(R.id.tv_zice_end);
		holder.tv_zice_cost = (TextView) v.findViewById(R.id.tv_zice_cost);
		holder.tv_zice_state = (TextView) v.findViewById(R.id.tv_zice_state);
		holder.iv_timeover = (ImageView) v.findViewById(R.id.iv_timeover);
		holder.ll_click_test = (LinearLayout) v
				.findViewById(R.id.ll_click_test);

		holder.tv_zice_title.setText(list.get(position).get("Exam_Name")
				.toString());
		holder.tv_zice_long.setText(list.get(position).get("Exam_Long")
				.toString()
				+ "分钟");

		// id = list.get(position).get("PK_Exam").toString();
		String stime = list.get(position).get("Sta_Time").toString();
		String etime = list.get(position).get("End_Time").toString();

		Date curDate = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String curtime = format.format(curDate);

		java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			cur = df.parse(curtime);
			end = df.parse(etime);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		holder.tv_zice_start.setText(forTime(stime));
		holder.tv_zice_end.setText(forTime(etime));

		state = (Integer) list.get(position).get("Exam_State");

		if (changeList.contains(position)) {
			state = 1;
		}

		switch (state) {

		case 0:
			if (list.get(position).get("BuyWay") != null) {
				if (list.get(position).get("BuyWay").equals(1)) {

					holder.tv_zice_cost.setText(list.get(position)
							.get("Amount").toString()
							+ "乐币");
				} else {
					holder.tv_zice_cost.setText(list.get(position)
							.get("Amount").toString()
							+ "金币");
				}
			}
			holder.tv_zice_state.setText("购买");
			break;
		case 1:

			holder.v_xiantiao.setVisibility(View.GONE);
			holder.tv_zice_cost.setVisibility(View.GONE);
			holder.tv_zice_state.setText("开始考试");
			break;

		case 2:
			holder.v_xiantiao.setVisibility(View.GONE);
			holder.tv_zice_cost.setVisibility(View.GONE);
			holder.tv_zice_state.setText("考试进行中");
			break;

		case 3:
			holder.v_xiantiao.setVisibility(View.GONE);
			holder.tv_zice_cost.setVisibility(View.GONE);
			holder.tv_zice_state.setText("等待批阅");
			break;
		case 4:

			holder.tv_zice_state.setText("考试完成");

			holder.tv_zice_cost.setText(list.get(position).get("Score")
					.toString()
					+ "分");
			break;

		default:
			break;
		}

		if (cur.getTime() - end.getTime() > 0) {
			holder.iv_timeover.setVisibility(View.VISIBLE);

			holder.tv_zice_state.setText("已经截止");
		}

		// converView.setTag(holder);
		// } else {
		// holder = (ViewHolder) converView.getTag();
		// }
		holder.tv_zice_state.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View arg0) {

				state = (Integer) list.get(position).get("Exam_State");
				id = list.get(position).get("PK_Exam").toString();

				if (changeList.contains(position)) {
					state = 1;
				}
				// holder.iv_timeover.getVisibility()==View.VISIBLE
				if (holder.iv_timeover.getVisibility() == View.VISIBLE) {

				} else {
					if (state == 0) {

						if (arg0.getTag() == null) {
							showDialog();
						}

						if (arg0.getTag() == (Integer) position) {
							openTest(position);
							return;
						}
						handler = new Handler() {

							public void handleMessage(Message msg) {
								if (msg.what == 1) {
									Diaoxian.showerror(context,
											msg.obj.toString());
									holder.v_xiantiao.setVisibility(View.GONE);
									holder.tv_zice_cost
											.setVisibility(View.GONE);
									holder.tv_zice_state.setText("开始考试");
									// start=true;
									arg0.setTag(position);
									changeList.add(position);
								}
							};
						};

					}
					/*
					 * Exam_State考试状态[Byte]
					 * 0考试未购买，1已购买未考试，2考试进行中，3考试已提交等待批阅，4考试完成
					 */

					else if (state == 1) {

						openTest(position);
					}// 1已购买未考试

					else if (state == 2)// 2考试进行中
					{

						openTest(position);
					}

					else if (state == 3) {

					}// 3考试已提交等待批阅
					else if (state == 4) {

					}// 4考试完成

				}

			}

		});

		return v;
	}

	protected void showDialog() {

		final Dialog dialog = MyDialog.MyDialogShow(context,
				R.layout.popup_isok, 1f);

		Button bt_isok_quie = (Button) dialog.findViewById(R.id.bt_isok_quie);
		Button bt_isok_ok = (Button) dialog.findViewById(R.id.bt_isok_ok);
		TextView tv_isok_title = (TextView) dialog
				.findViewById(R.id.tv_isok_title);
		tv_isok_title.setText("您确定要购买吗?");
		bt_isok_ok.setText("确定");
		bt_isok_quie.setText("再看看");

		bt_isok_quie.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				dialog.dismiss();
				return;
			}
		});

		bt_isok_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				new Thread() {
					@Override
					public void run() {
						Map<String, Object> map = new HashMap<String, Object>();

						Message msg = new Message();

						map.put("examId", id);

						String result = HttpUse.messageget("Exam", "buyExam",
								map);

						try {
							JSONObject jo = new JSONObject(result);
							if (jo.getBoolean("result")) {
								msg.what = 1;
								msg.obj = jo.getString("message");
							}
						} catch (JSONException e) {
							msg.obj = e.getMessage();
						}

						handler.sendMessage(msg);
					}
				}.start();

				dialog.dismiss();
			}
		});
		dialog.show();
	}

	// 打开考试题
	private void openTest(final int position) {
		Intent intnt = new Intent(context, ZicetestActivity.class);

		intnt.putExtra("examId", list.get(position).get("PK_Exam").toString());
		intnt.putExtra("Exam_Long", list.get(position).get("Exam_Long")
				.toString());
		intnt.putExtra("forNet", "participateExam");
		intnt.putExtra("tijiao", "subExam");
		intnt.putExtra("ANS", "saveItemRes");
		context.startActivity(intnt);
	}

	public String forTime(String time) {
		if (time.length() >= 19) {
			time = time.substring(0, 4) + "/" + time.substring(5, 7) + "/"
					+ time.substring(8, 10) + " " + time.substring(11, 13)
					+ time.substring(13, 16);
		}
		return time;
	}

	class ViewHolder {
		TextView tv_zice_long, tv_zice_start, tv_zice_end, tv_zice_title,
				tv_zice_cost, tv_zice_state;
		View v_xiantiao;
		ImageView iv_timeover;
		LinearLayout ll_click_test;
	}

}
