package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.service.MonthDateView;
import com.mpyf.lening.service.MonthDateView.DateClick;

public class RiliActivity extends Activity {
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				try {
					JSONArray ja = new JSONArray(msg.obj.toString());
					list.clear();
					lists.clear();
					for (int i = 0; i < ja.length(); i++) {
						Map<String, Object> map = new HashMap<String, Object>();
						JSONObject jo = ja.getJSONObject(i);
						map.put("day", jo.getInt("day"));
						map.put("isSign", jo.getInt("isSign"));
						map.put("iscansign", jo.getInt("iscansign"));
						map.put("month", jo.getInt("month"));
						list.add(map);
						if (jo.getInt("isSign") == 1) {
							lists.add(jo.getInt("day"));
						}
					}

					// 给日历控件设置数据
					setDate();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (msg.what == 2) {
				try {
					JSONObject jo = new JSONObject(msg.obj.toString());
					if (jo.getBoolean("result")) {
						Toast.makeText(RiliActivity.this, "补签成功",
								Toast.LENGTH_SHORT).show();
						lists.add(monthDateView.getmSelDay());
						setDate();
					}
				} catch (JSONException e) {
					msg.obj = e.getMessage();
				}
			} else if (msg.what == 3) {
				try {
					JSONObject jo = new JSONObject(msg.obj.toString());
					if (jo.getBoolean("result")) {
						Toast.makeText(RiliActivity.this, "签到成功",
								Toast.LENGTH_SHORT).show();
						lists.add(monthDateView.getmSelDay());
						setDate();
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (msg.what == 4) {
				try {
					JSONObject jo = new JSONObject(msg.obj.toString());
					cardNum = jo.getInt("data");
					showDialog();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};

	private ImageView iv_left;
	private ImageView iv_right;
	private TextView tv_date;
	private TextView tv_week;
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private LinearLayout ll_zd_rili_back;
	private int mSelectBGColor = Color.parseColor("#BDE199");
	// private TextView tv_today;
	private MonthDateView monthDateView;
	private Spinner spinner;
	private int mCurrMonth;
	private int position;
	private int cardNum;
	List<Integer> lists = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_rili);
		monthDateView = (MonthDateView) findViewById(R.id.monthDateView);
		tv_date = (TextView) findViewById(R.id.date_text);
		// 返回键
		ll_zd_rili_back = (LinearLayout) findViewById(R.id.ll_zd_rili_back);
		// 设置监听
		setOnlistener();
		// 初始化下拉框
		initSpinner();
		// 联网请求数据获得当月签到详情，然后把list列表设置给list
		requestData("monthSignforUser");

	}

	private void initSpinner() {
		Calendar calendar = Calendar.getInstance();
		mCurrMonth = calendar.get(Calendar.MONTH) + 1;
		if (calendar.get(Calendar.MONTH) == 12) {
			mCurrMonth = 1;
		}
		String month = String.valueOf(mCurrMonth);
		String month2 = String.valueOf(mCurrMonth - 1);
		if (mCurrMonth == 1) {
			month2 = String.valueOf(12);
		}
		// 选择签到月份
		spinner = (Spinner) findViewById(R.id.sp_spinner);
		// 建立数据源
		String[] mItems = { month + "月", month2 + "月" };
		// 建立Adapter并且绑定数据源
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mItems);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 绑定 Adapter到控件
		spinner.setAdapter(adapter);
		// spinner.setSelection(0);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				if (position == pos) {
					return;
				}
				if (pos == 0) {
					monthDateView.onRightClick();
					requestData("monthSignforUser");
				} else {
					monthDateView.onLeftClick();
					requestData("lastMonthSignforUser");
				}
				position = pos;

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// Another interface callback
			}
		});
	}

	private void requestData(final String monthSigned) {
		new Thread() {
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				Message msg = new Message();
				String result = HttpUse.messageget("QueAndAns", monthSigned,
						map);
				// lastMonthSignforUser 上个月的签到详情
				// TODO 当月签到所有数据
				System.out.println("=====获取到当月签到详情====" + result
						+ "RiliActivity");

				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = jo.getString("data");
					} else {
						msg.obj = jo.getString("message");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					msg.obj = e.getMessage();
				}

				handler.sendMessage(msg);
			};
		}.start();
	}

	private void setDate() {

		tv_date.setText("您该月已经连续签到" + lists.size() + "天");
		monthDateView.setTextView(tv_date);
		monthDateView.setDaysHasThingList(lists);
		monthDateView.setDateClick(new DateClick() {

			@Override
			public void onClickOnDate() {
				// 先判断月份，再判断天数，再判断是否可补签，如果不可补签，return;
				// 同一月，日期是今天之后
				if (monthDateView.getmSelMonth() == mCurrMonth - 1
						&& monthDateView.getmSelDay() > Calendar.getInstance()
								.get(Calendar.DAY_OF_MONTH)) {
					return;
				}

				// 同一月，日期是今天
				if (monthDateView.getmSelMonth() == mCurrMonth - 1
						&& monthDateView.getmSelDay() == Calendar.getInstance()
								.get(Calendar.DAY_OF_MONTH)) {
					// 如果已经签到过
					if ((Integer) list.get(monthDateView.getmSelDay() - 1).get(
							"isSign") == 1) {
					} else {
						// 调用签到接口

						new Thread() {
							public void run() {
								Map<String, Object> map = new HashMap<String, Object>();
								String result = HttpUse.messageget("Account",
										"signIn", map);
								// TODO
//								System.out.println(result + "=====签到====");
								Message msg = new Message();
								msg.what = 3;
								msg.obj = result;
								handler.sendMessage(msg);
							};
						}.start();
					}

					return;
				}

				// 如果是今天之前
				// 如果已经签到过
				if ((Integer) list.get(monthDateView.getmSelDay() - 1).get(
						"isSign") == 1) {
					return;
				}

				// 调接口查询补签卡数量，弹出对话框，在对话框中点击确认并且补签卡数量>0时才调用方法ChangeColor，没有给出提示;

				new Thread() {
					public void run() {
						Map<String, Object> map = new HashMap<String, Object>();
						String result = HttpUse.messageget("QueAndAns",
								"getUserSignCard", map);
						// TODO
//						System.out.println(result + "=====获取签到卡张数====");
						Message msg = new Message();
						msg.what = 4;
						msg.obj = result;
						handler.sendMessage(msg);
					};
				}.start();

				// 如果可以签到，这个在点击确认时才调用
				// ChangeColor();
			}
		});

	}

	protected void ChangeColor() {

		new Thread() {
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("signDate",
						getTime(monthDateView.getmSelYear(),
								monthDateView.getmSelMonth() + 1,
								monthDateView.getmSelDay()));
				System.out.println("==拼接的结果=="
						+ getTime(monthDateView.getmSelYear(),
								monthDateView.getmSelMonth() + 1,
								monthDateView.getmSelDay()));
				String result = HttpUse.messageget("QueAndAns",
						"supplementSign", map);
				// TODO
//				System.out.println("==补签的结果==" + result);
				Message msg = new Message();
				msg.what = 2;
				msg.obj = result;
				handler.sendMessage(msg);
			};
		}.start();

	}

	private void setOnlistener() {
		/**
		 * 返回键的监听
		 */
		ListenerServer.setfinish(RiliActivity.this, ll_zd_rili_back);
	}

	private String getTime(int year, int month, int day) {
		StringBuilder str = new StringBuilder();
		str.append(year);
		str.append("-");
		if (month < 10) {
			str.append("0");
		}
		str.append(month);
		str.append("-");
		if (day < 10) {
			str.append("0");
		}
		str.append(day);
		return str.toString();
	}

	// 弹出对话框
	private void showDialog() {
		Builder builder = new AlertDialog.Builder(RiliActivity.this);
		builder.setTitle("温馨提示");// 设置对话框标题
		builder.setMessage("您有" + cardNum + "张补签卡,是否使用补签卡");// 设置显示的内容
		builder.setPositiveButton("补签", new DialogInterface.OnClickListener() {
			// 添加补签按钮的响应事件
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 可以签到
				if (cardNum > 0) {
					ChangeColor();
				} else {
					Toast.makeText(RiliActivity.this, "很抱歉,您没有补签卡了~", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			// 添加取消按钮
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		builder.show();
	}

}
