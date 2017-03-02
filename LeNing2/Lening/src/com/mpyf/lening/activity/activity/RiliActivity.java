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

					// �������ؼ���������
					setDate();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (msg.what == 2) {
				try {
					JSONObject jo = new JSONObject(msg.obj.toString());
					if (jo.getBoolean("result")) {
						Toast.makeText(RiliActivity.this, "��ǩ�ɹ�",
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
						Toast.makeText(RiliActivity.this, "ǩ���ɹ�",
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
		// ���ؼ�
		ll_zd_rili_back = (LinearLayout) findViewById(R.id.ll_zd_rili_back);
		// ���ü���
		setOnlistener();
		// ��ʼ��������
		initSpinner();
		// �����������ݻ�õ���ǩ�����飬Ȼ���list�б����ø�list
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
		// ѡ��ǩ���·�
		spinner = (Spinner) findViewById(R.id.sp_spinner);
		// ��������Դ
		String[] mItems = { month + "��", month2 + "��" };
		// ����Adapter���Ұ�����Դ
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, mItems);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// �� Adapter���ؼ�
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
				// lastMonthSignforUser �ϸ��µ�ǩ������
				// TODO ����ǩ����������
				System.out.println("=====��ȡ������ǩ������====" + result
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

		tv_date.setText("�������Ѿ�����ǩ��" + lists.size() + "��");
		monthDateView.setTextView(tv_date);
		monthDateView.setDaysHasThingList(lists);
		monthDateView.setDateClick(new DateClick() {

			@Override
			public void onClickOnDate() {
				// ���ж��·ݣ����ж����������ж��Ƿ�ɲ�ǩ��������ɲ�ǩ��return;
				// ͬһ�£������ǽ���֮��
				if (monthDateView.getmSelMonth() == mCurrMonth - 1
						&& monthDateView.getmSelDay() > Calendar.getInstance()
								.get(Calendar.DAY_OF_MONTH)) {
					return;
				}

				// ͬһ�£������ǽ���
				if (monthDateView.getmSelMonth() == mCurrMonth - 1
						&& monthDateView.getmSelDay() == Calendar.getInstance()
								.get(Calendar.DAY_OF_MONTH)) {
					// ����Ѿ�ǩ����
					if ((Integer) list.get(monthDateView.getmSelDay() - 1).get(
							"isSign") == 1) {
					} else {
						// ����ǩ���ӿ�

						new Thread() {
							public void run() {
								Map<String, Object> map = new HashMap<String, Object>();
								String result = HttpUse.messageget("Account",
										"signIn", map);
								// TODO
//								System.out.println(result + "=====ǩ��====");
								Message msg = new Message();
								msg.what = 3;
								msg.obj = result;
								handler.sendMessage(msg);
							};
						}.start();
					}

					return;
				}

				// ����ǽ���֮ǰ
				// ����Ѿ�ǩ����
				if ((Integer) list.get(monthDateView.getmSelDay() - 1).get(
						"isSign") == 1) {
					return;
				}

				// ���ӿڲ�ѯ��ǩ�������������Ի����ڶԻ����е��ȷ�ϲ��Ҳ�ǩ������>0ʱ�ŵ��÷���ChangeColor��û�и�����ʾ;

				new Thread() {
					public void run() {
						Map<String, Object> map = new HashMap<String, Object>();
						String result = HttpUse.messageget("QueAndAns",
								"getUserSignCard", map);
						// TODO
//						System.out.println(result + "=====��ȡǩ��������====");
						Message msg = new Message();
						msg.what = 4;
						msg.obj = result;
						handler.sendMessage(msg);
					};
				}.start();

				// �������ǩ��������ڵ��ȷ��ʱ�ŵ���
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
				System.out.println("==ƴ�ӵĽ��=="
						+ getTime(monthDateView.getmSelYear(),
								monthDateView.getmSelMonth() + 1,
								monthDateView.getmSelDay()));
				String result = HttpUse.messageget("QueAndAns",
						"supplementSign", map);
				// TODO
//				System.out.println("==��ǩ�Ľ��==" + result);
				Message msg = new Message();
				msg.what = 2;
				msg.obj = result;
				handler.sendMessage(msg);
			};
		}.start();

	}

	private void setOnlistener() {
		/**
		 * ���ؼ��ļ���
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

	// �����Ի���
	private void showDialog() {
		Builder builder = new AlertDialog.Builder(RiliActivity.this);
		builder.setTitle("��ܰ��ʾ");// ���öԻ������
		builder.setMessage("����" + cardNum + "�Ų�ǩ��,�Ƿ�ʹ�ò�ǩ��");// ������ʾ������
		builder.setPositiveButton("��ǩ", new DialogInterface.OnClickListener() {
			// ��Ӳ�ǩ��ť����Ӧ�¼�
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// ����ǩ��
				if (cardNum > 0) {
					ChangeColor();
				} else {
					Toast.makeText(RiliActivity.this, "�ܱ�Ǹ,��û�в�ǩ����~", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			// ���ȡ����ť
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		builder.show();
	}

}
