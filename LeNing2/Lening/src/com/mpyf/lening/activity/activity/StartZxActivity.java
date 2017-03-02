package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.activity.adapter.ZX_XuanAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;

/**
 * ���࿪ʼҳ��
 * 
 * @author s
 * 
 */
public class StartZxActivity extends Activity {

	private ImageView iv_start_zhenxiang_back, iv_zx_ans;
	private String sn_aux = "";
	private String labelId = "";
	private TextView tv_zx_wenti, tv_zx_looktruth, tv_zx_next, tv_trueans;
	private ListView lv_zx_xuan;
	private JSONArray ja;
	private ArrayList<String> list1 = new ArrayList<String>();
	private ArrayList<String> list2 = new ArrayList<String>();
	private int qNo;
	private ZX_XuanAdapter adapter;
	private ArrayList<String> checkedItems = new ArrayList<String>();
	private boolean isShow;
	private int eNo;
	private String trueOption;
	private int chooseType;
	String doChoose = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_startzx);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		sn_aux = bundle.getString("sn_aux");
		labelId = bundle.getString("labelId");

		// System.out.println("�����===========" + sn_aux);
		// System.out.println("�����===========" + labelId);

		init();
		showinfo();
		addlistener();
	}

	private void init() {
		iv_start_zhenxiang_back = (ImageView) findViewById(R.id.iv_start_zhenxiang_back);
		// ����
		tv_zx_wenti = (TextView) findViewById(R.id.tv_zx_wenti);
		// ѡ��
		lv_zx_xuan = (ListView) findViewById(R.id.lv_zx_xuan);
		// �鿴����
		tv_zx_looktruth = (TextView) findViewById(R.id.tv_zx_looktruth);
		// �Դ���ͼ��
		iv_zx_ans = (ImageView) findViewById(R.id.iv_zx_ans);
		// ��һ��
		tv_zx_next = (TextView) findViewById(R.id.tv_zx_next);
		// ��ȷ��
		tv_trueans = (TextView) findViewById(R.id.tv_trueans);

		adapter = new ZX_XuanAdapter(StartZxActivity.this, list1, checkedItems);
		lv_zx_xuan.setAdapter(adapter);
	}

	private void saveUser() {

		// TODO ����𰸱�ǩ�����ýӿ�
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					// ��ʾ��һ��,�ŵ������ǩ�Ľӿ�����ɹ�֮��������÷���false�Ļ�����ִ�У�������ʾ
					boolean data = (Boolean) msg.obj;
					if (data) {
						// TODO �жϴ������С��3,���ýӿڣ���Ϊ��ȷ

						if (eNo < 3) {
							editTruth();
						} else {
							Toast.makeText(StartZxActivity.this,
									"���Ѿ��������Σ����Ժ�������", Toast.LENGTH_SHORT)
									.show();
							tv_zx_next.setVisibility(View.GONE);
							finish();
						}

					}
				} else {
					// ������˾msg.obj����ʾû�б���ɹ�
					Toast.makeText(StartZxActivity.this, msg.obj.toString(),
							Toast.LENGTH_SHORT).show();
				}
			}

		};

		new Thread() {
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("labelId", labelId);
				// Message msg = new Message();
				// String result =
				HttpUse.messageget("QueAndAns", "saveUserTruthQALabel", map);
				// // TODO ��ӡ���󵽵����ݣ��������Ҫ��ʲô��ʾ�������ӿ��У�������Щ���������Բ�Ҫ
				// System.out.println("=====��������ǩ====" + result);
				//
				// try {
				// JSONObject jo = new JSONObject(result);
				// if (jo.getBoolean("result")) {
				// msg.what = 1;

				// msg.obj = jo.getBoolean("data");
				// } else {
				// msg.obj = jo.getString("message");
				// }
				// } catch (JSONException e) {
				// msg.obj = e.getMessage();
				// }
				//
				// handler.sendMessage(msg);
			};
		}.start();

	}

	private void editTruth() {

		// final Handler handler = new Handler() {
		// @Override
		// public void handleMessage(Message msg) {
		// // TODO �޸ĳɹ�
		// }
		// };

		new Thread() {
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("labelId", labelId);
				// Message msg = new Message();
				// String result =
				HttpUse.messageget("QueAndAns", "editTruthQALabelTrue", map);
				// TODO ��ӡ���󵽵�����
				// System.out.println("=====��������ǩ====" + result);
				//
				// try {
				// JSONObject jo = new JSONObject(result);
				// if (jo.getBoolean("result")) {
				// msg.what = 1;
				// msg.obj = jo.getBoolean("data");
				// } else {
				// msg.obj = jo.getString("message");
				// }
				// } catch (JSONException e) {
				// msg.obj = e.getMessage();
				// }
				//
				// handler.sendMessage(msg);
			};
		}.start();
	}

	private void showinfo() {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					setQue(qNo);
					// ���������saveUserTruthQALabel
					saveUser();
				}
			}

		};

		new Thread() {
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("sn_aux", sn_aux);

				Message msg = new Message();
				String result = HttpUse.messageget("QueAndAns", "listTruthQA",
						map);
				// TODO ��ӡ���󵽵�����
				// System.out.println("=====��ȡ�����ʴ�====" + result);

				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						qNo = 0;
						ja = jo.getJSONArray("data");
					} else {
						msg.obj = jo.getString("message");
					}
				} catch (JSONException e) {
					msg.obj = e.getMessage();
				}

				handler.sendMessage(msg);
			};
		}.start();
	}

	private void addlistener() {
		// ���ؼ�
		ListenerServer.setfinish(StartZxActivity.this, iv_start_zhenxiang_back);
		// ��һ��ĵ���¼�
		tv_zx_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �����һ��ʱ����ѯ�Ƿ����������������������ϲ����ٴ��⣬���������������Ϊture

				isShow = false;
				checkedItems.clear();
				hideNext();
				qNo++;
				setQue(qNo);
				tv_zx_looktruth.setVisibility(View.VISIBLE);
			}

		});

		// ListView����Ŀ����¼�
		lv_zx_xuan.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// ��¼���λ��

				System.out.println("===��ӡ�����λ��===" + arg2);
				if (arg2 == 0) {
					doChoose = "A";
				} else if (arg2 == 1) {
					doChoose = "B";
				} else if (arg2 == 2) {
					doChoose = "C";
				} else if (arg2 == 3) {
					doChoose = "D";
				} else if (arg2 == 4) {
					doChoose = "E";
				} else if (arg2 == 5) {
					doChoose = "F";
				}

				if (chooseType == 0) {
					// ��ѡ
					if (isShow) {
						// ����Ѿ�ѡ�����������ѡ
						return;
					}

					if (checkedItems.isEmpty()) {
						checkedItems.add(doChoose);
						// tv_zx_looktruth.setVisibility(View.VISIBLE);
					} else if (checkedItems.contains(doChoose)) {
						checkedItems.remove(doChoose);
						// tv_zx_looktruth.setVisibility(View.GONE);
					} 
//					else {
//						// tv_zx_looktruth.setVisibility(View.VISIBLE);
//					}

					adapter.notifyDataSetChanged();
				} else {
					// ��ѡ,�鿴����
					if (isShow) {
						// �Ѿ������𰸣����ɸ���
						return;
					}
					if (checkedItems.contains(doChoose)) {
						checkedItems.remove(doChoose);
					} else {
						checkedItems.add(doChoose);
					}
					adapter.notifyDataSetChanged();
				}

			}

		});

		tv_zx_looktruth.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("====����鿴����====");
				tv_zx_looktruth.setVisibility(View.GONE);
				isShow = true;
				showNext();

				if (chooseType == 0) {
					showTrueAns(doChoose, trueOption);
				} else {
					Collections.sort(checkedItems);
					String multiChoose = "";
					for (int i = 0; i <= checkedItems.size() - 1; i++) {
						multiChoose += checkedItems.get(i);
						if (i != checkedItems.size() - 1) {
							multiChoose += ",";
						}
					}
					// ���п���
					// System.out.println("===��ӡѡ��Ĵ�==="
					// + multiChoose);
					showTrueAns(multiChoose, trueOption);
				}

				if (qNo == ja.length() - 1) {
					if (eNo < 3) {
						editTruth();
					}
					// ��������һ�⣬����һ�����ص�
					tv_zx_next.setVisibility(View.GONE);
					finish();
				} else {
					if (eNo == 3) {
						// �������Σ��Զ��˳�
						Toast.makeText(StartZxActivity.this, "���Ѿ��������Σ����Ժ�������",
								Toast.LENGTH_SHORT).show();
						finish();
					}
					tv_zx_next.setVisibility(View.VISIBLE);
				}

			}
		});

		// try {
		// JSONObject ji = new JSONObject(ja.get(qNo).toString());
		// final int chooseType = ji.getInt("option_type");
		// final String trueOption = ji.getString("true_option");
		// if (chooseType == 0) {
		// ��ѡ
		// if (isShow) {
		// // ����Ѿ�ѡ�����������ѡ
		// return;
		// }
		//
		// if (checkedItems.isEmpty()) {
		// checkedItems.add(doChoose);
		// // tv_zx_looktruth.setVisibility(View.VISIBLE);
		// } else if (checkedItems.contains(doChoose)) {
		// checkedItems.remove(doChoose);
		// // tv_zx_looktruth.setVisibility(View.GONE);
		// } else {
		// // tv_zx_looktruth.setVisibility(View.VISIBLE);
		// }
		//
		// adapter.notifyDataSetChanged();

		// } else {
		// // ��ѡ,�鿴����
		// if (isShow) {
		// // �Ѿ������𰸣����ɸ���
		// return;
		// }
		// if (checkedItems.contains(doChoose)) {
		// checkedItems.remove(doChoose);
		// } else {
		// checkedItems.add(doChoose);
		// }
		// adapter.notifyDataSetChanged();
		// if (checkedItems.isEmpty()) {
		// tv_zx_looktruth.setVisibility(View.GONE);
		// } else {
		// tv_zx_looktruth.setVisibility(View.VISIBLE);
		// }
		// tv_zx_looktruth
		// .setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// System.out.println("====����鿴����2====");
		// tv_zx_looktruth
		// .setVisibility(View.GONE);
		// isShow = true;
		// showNext();
		// Collections.sort(checkedItems);
		// String multiChoose = "";
		// for (int i = 0; i <= checkedItems
		// .size() - 1; i++) {
		// multiChoose += checkedItems.get(i);
		// if (i != checkedItems.size() - 1) {
		// multiChoose += ",";
		// }
		// }
		// // ���п���
		// // System.out.println("===��ӡѡ��Ĵ�==="
		// // + multiChoose);
		// showTrueAns(multiChoose, trueOption);
		//
		// if (qNo == ja.length() - 1) {
		// if (eNo < 3) {
		// editTruth();
		// }
		// // ��������һ�⣬����һ�����ص�
		// tv_zx_next.setVisibility(View.GONE);
		// finish();
		// } else {
		// tv_zx_next
		// .setVisibility(View.VISIBLE);
		// }
		//
		// }
		//
		// });
		// }

		// } catch (JSONException e) {
		// e.printStackTrace();
		// }

	}

	// ��ʾ��һ��
	public void showNext() {
		iv_zx_ans.setVisibility(View.VISIBLE);
		tv_zx_next.setVisibility(View.VISIBLE);
		tv_trueans.setVisibility(View.VISIBLE);
	}

	// ������һ��
	public void hideNext() {
		iv_zx_ans.setVisibility(View.GONE);
		tv_zx_next.setVisibility(View.GONE);
		tv_trueans.setVisibility(View.GONE);
	}

	// ��ʾ���
	public void showTrueAns(String doChoose, String trueOption) {
		if (doChoose.equalsIgnoreCase(trueOption)) {
			// �����
			iv_zx_ans.setBackgroundResource(R.drawable.zd_truth_icon_dui);
			tv_trueans.setVisibility(View.GONE);

		} else {
			// �����
			eNo++;
			iv_zx_ans.setBackgroundResource(R.drawable.zd_truth_icon_cuo);
			tv_trueans.setText("��ȷ�𰸣�" + trueOption);
		}
	}

	// ��ʾ���⼰ѡ��
	private void setQue(int queNo) {
		try {
			JSONObject ji = new JSONObject(ja.get(queNo).toString());
			tv_zx_wenti.setText(ji.getString("qa_title"));

			trueOption = ji.getString("true_option");
			chooseType = ji.getInt("option_type");
			if (chooseType == 0) {
				// ��ѡ
				// tv_zx_looktruth.setVisibility(View.GONE);
				tv_zx_wenti.append("(��ѡ)");
			} else {
				// ��ѡ
				// tv_zx_looktruth.setVisibility(View.VISIBLE);
				tv_zx_wenti.append("(��ѡ)");
			}
			list1.clear();
			checkedItems.clear();
			if (!TextUtils.isEmpty(ji.getString("option_a")))
				list1.add(0, "A��" + ji.getString("option_a"));
			if (!TextUtils.isEmpty(ji.getString("option_b")))
				list1.add(1, "B��" + ji.getString("option_b"));
			if (!TextUtils.isEmpty(ji.getString("option_c")))
				list1.add(2, "C��" + ji.getString("option_c"));
			if (!TextUtils.isEmpty(ji.getString("option_d")))
				list1.add(3, "D��" + ji.getString("option_d"));
			if (!TextUtils.isEmpty(ji.getString("option_e")))
				list1.add(4, "E��" + ji.getString("option_e"));
			if (!TextUtils.isEmpty(ji.getString("option_f")))
				list1.add(5, "F��" + ji.getString("option_f"));

			adapter.notifyDataSetChanged();

		} catch (JSONException e) {
			Diaoxian.showerror(StartZxActivity.this, e.getMessage());
		}
	}

}