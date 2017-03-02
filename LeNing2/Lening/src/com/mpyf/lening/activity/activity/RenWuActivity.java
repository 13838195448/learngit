package com.mpyf.lening.activity.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.ImageOptions;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;
import com.mpyf.lening.service.ScratchView;
import com.mpyf.lening.service.ScratchView.OnShowListener;
import com.mpyf.lening.service.ScratchView.OnStartListener;

public class RenWuActivity extends Activity {
	private LinearLayout ll_zd_renwu_back, ll_zd_qian_content, ll_da_content,
			ll_cai_content, ll_lei_content, ll_denglu, ll_da, ll_cai, ll_lei;
	private TextView tv_zd_rwqian_jiang, tv_ling, tv_da_jiang, tv_da_shu,
			tv_da_ling, tv_cai_jiang, tv_cai_shu, tv_cai, tv_cai_ling,
			tv_lei_jiang, tv_lei_shu, tv_lei_ling, tv_da, tv_lei, tv_zd_rwqian,
			tv_da_taskname, tv_cai_taskname, tv_lei_taskname, tv_da_shuoming,
			tv_cai_shuoming, tv_lei_shuoming;
	private ImageView iv_zd_rwling, iv_da_ling, iv_cai_ling, iv_lei_ling;

	private int checkedItem = -1;
	private PopupWindow mPopWindow;
	private LinearLayout ll_prizepic;
	private ScratchView scr_view;
	private TextView tv_prizename;
	private TextView tv_guamore;
	private Bitmap bitmap;
	private int cardNum;
	private String pkTask1;
	private String pkTask2;
	private String pkTask3;
	private String prizePic;
	private String prizeName;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				try {
					/*
					 * taskState����״̬[Int32]1δ��ɣ� 2���δ��ȡ��3���� ��4ȫ���������;������״̬û�жϰ�
					 */
					JSONObject jo = new JSONObject(msg.obj.toString());
					tv_da_taskname.setText(jo.getString("taskName"));
					tv_da_shu.setText(jo.getString("taskInfo"));
					pkTask1 = jo.getString("pkTask");
					if (jo.getInt("taskState") == 1) {
						tv_da_shu.setVisibility(View.VISIBLE);
						iv_da_ling.setVisibility(View.GONE);

						tv_da_jiang.setText("����:" + jo.getInt("lcoin")
								+ "�ֱ�   " + jo.getInt("gcoin") + "���   "
								+ jo.getInt("hcoin") + "����ֵ");
						// ����˵��
						tv_da_shuoming.setText("1.������APP��"
								+ jo.getString("taskName")
								+ ",�����������\n2.ÿ�������������,��ɺ�ǵ���ȡ����");
					} else if (jo.getInt("taskState") == 2) {
						// TODO 2���δ��ȡ
						tv_da_shu.setVisibility(View.GONE);
						iv_da_ling.setVisibility(View.VISIBLE);
						tv_da_jiang.setText("����:" + jo.getInt("lcoin")
								+ "�ֱ�   " + jo.getInt("gcoin") + "���   "
								+ jo.getInt("hcoin") + "����ֵ");
						// ����˵��
						tv_da_shuoming.setText("1.������APP��"
								+ jo.getString("taskName")
								+ ",�����������\n2.ÿ�������������,��ɺ�ǵ���ȡ����");
					} else if (jo.getInt("taskState") == 4) {
						tv_da_jiang.setText("����:" + jo.getInt("lcoin")
								+ "�ֱ�   " + jo.getInt("gcoin") + "���   "
								+ jo.getInt("hcoin") + "����ֵ");
						// ����˵��
						tv_da_shuoming.setText("1.������APP��"
								+ jo.getString("taskName")
								+ ",�����������\n2.ÿ�������������,��ɺ�ǵ���ȡ����");

						tv_da_shu.setVisibility(View.VISIBLE);
						tv_da_shu.setText("�����");
						iv_da_ling.setVisibility(View.GONE);
						tv_da_ling.setText("����ȡ");
						tv_da_ling.setOnClickListener(null);
					} else if (jo.getInt("taskState") == 3) {
						tv_da_jiang.setText("����:" + jo.getInt("lcoin")
								+ "�ֱ�   " + jo.getInt("gcoin") + "���   "
								+ jo.getInt("hcoin") + "����ֵ");
						// ����˵��
						tv_da_shuoming.setText("1.������APP��"
								+ jo.getString("taskName")
								+ ",�����������\n2.ÿ�������������,��ɺ�ǵ���ȡ����");

						tv_da_shu.setVisibility(View.VISIBLE);
						tv_da_shu.setText("�����");
						iv_da_ling.setVisibility(View.GONE);
						tv_da_ling.setText("����ȡ");
						tv_da_ling.setOnClickListener(null);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else if (msg.what == 2) {
				try {
					JSONObject jo = new JSONObject(msg.obj.toString());
					tv_cai_taskname.setText(jo.getString("taskName"));
					tv_cai_shu.setText(jo.getString("taskInfo"));
					pkTask2 = jo.getString("pkTask");
					if (jo.getInt("taskState") == 1) {

						tv_cai_shu.setVisibility(View.VISIBLE);
						iv_cai_ling.setVisibility(View.GONE);

						tv_cai_jiang.setText("����:" + jo.getInt("lcoin")
								+ "�ֱ�   " + jo.getInt("gcoin") + "���   "
								+ jo.getInt("hcoin") + "����ֵ");
						// ����˵��
						tv_cai_shuoming.setText("1." + jo.getString("taskName")
								+ ",�����������\n2.ÿ�������������,��ɺ�ǵ���ȡ����");
					} else if (jo.getInt("taskState") == 2) {
						// TODO 2���δ��ȡ
						tv_cai_shu.setVisibility(View.GONE);
						iv_cai_ling.setVisibility(View.VISIBLE);

						tv_cai_jiang.setText("����:" + jo.getInt("lcoin")
								+ "�ֱ�   " + jo.getInt("gcoin") + "���   "
								+ jo.getInt("hcoin") + "����ֵ");
						// ����˵��
						tv_cai_shuoming.setText("1." + jo.getString("taskName")
								+ ",�����������\n2.ÿ�������������,��ɺ�ǵ���ȡ����");
					} else if (jo.getInt("taskState") == 4) {

						tv_cai_jiang.setText("����:" + jo.getInt("lcoin")
								+ "�ֱ�   " + jo.getInt("gcoin") + "���   "
								+ jo.getInt("hcoin") + "����ֵ");
						// ����˵��
						tv_cai_shuoming.setText("1." + jo.getString("taskName")
								+ ",�����������\n2.ÿ�������������,��ɺ�ǵ���ȡ����");
						tv_cai_shu.setVisibility(View.VISIBLE);
						tv_cai_shu.setText("�����");
						tv_cai_ling.setText("����ȡ");
						iv_cai_ling.setVisibility(View.GONE);
						// �����õ��ǵ�һ�������ǵڶ���
						tv_cai_ling.setOnClickListener(null);

					} else if (jo.getInt("taskState") == 3) {

						tv_cai_jiang.setText("����:" + jo.getInt("lcoin")
								+ "�ֱ�   " + jo.getInt("gcoin") + "���   "
								+ jo.getInt("hcoin") + "����ֵ");
						// ����˵��
						tv_cai_shuoming.setText("1." + jo.getString("taskName")
								+ ",�����������\n2.ÿ�������������,��ɺ�ǵ���ȡ����");
						tv_cai_shu.setVisibility(View.VISIBLE);
						tv_cai_shu.setText("�����");
						tv_cai_ling.setText("����ȡ");
						iv_cai_ling.setVisibility(View.GONE);
						// �����õ��ǵ�һ�������ǵڶ���
						tv_cai_ling.setOnClickListener(null);

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else if (msg.what == 3) {
				try {
					JSONObject jo = new JSONObject(msg.obj.toString());
					tv_lei_taskname.setText(jo.getString("taskName"));
					tv_lei_shu.setText(jo.getString("taskInfo"));
					pkTask3 = jo.getString("pkTask");
					if (jo.getInt("taskState") == 1) {
						tv_lei_shu.setVisibility(View.VISIBLE);
						iv_lei_ling.setVisibility(View.GONE);

						tv_lei_jiang.setText("����:" + jo.getInt("lcoin")
								+ "�ֱ�   " + jo.getInt("gcoin") + "���   "
								+ jo.getInt("hcoin") + "����ֵ");
						// ����˵��
						tv_lei_shuoming.setText("1.��APP��"
								+ jo.getString("taskName")
								+ ",�����������\n2.ÿ�������������,��ɺ�ǵ���ȡ����");
					} else if (jo.getInt("taskState") == 2) {
						// TODO 2���δ��ȡ
						tv_lei_shu.setVisibility(View.GONE);
						iv_lei_ling.setVisibility(View.VISIBLE);

						tv_lei_jiang.setText("����:" + jo.getInt("lcoin")
								+ "�ֱ�   " + jo.getInt("gcoin") + "���   "
								+ jo.getInt("hcoin") + "����ֵ");
						// ����˵��
						tv_lei_shuoming.setText("1.��APP��"
								+ jo.getString("taskName")
								+ ",�����������\n2.ÿ�������������,��ɺ�ǵ���ȡ����");
					} else if (jo.getInt("taskState") == 4) {

						tv_lei_jiang.setText("����:" + jo.getInt("lcoin")
								+ "�ֱ�   " + jo.getInt("gcoin") + "���   "
								+ jo.getInt("hcoin") + "����ֵ");

						// ����˵��
						tv_lei_shuoming.setText("1.��APP��"
								+ jo.getString("taskName")
								+ ",�����������\n2.ÿ�������������,��ɺ�ǵ���ȡ����");

						tv_lei_shu.setVisibility(View.VISIBLE);
						tv_lei_shu.setText("�����");
						tv_lei_ling.setText("����ȡ");
						tv_lei_ling.setOnClickListener(null);
						iv_lei_ling.setVisibility(View.GONE);

					} else if (jo.getInt("taskState") == 3) {

						tv_lei_jiang.setText("����:" + jo.getInt("lcoin")
								+ "�ֱ�   " + jo.getInt("gcoin") + "���   "
								+ jo.getInt("hcoin") + "����ֵ");

						// ����˵��
						tv_lei_shuoming.setText("1.��APP��"
								+ jo.getString("taskName")
								+ ",�����������\n2.ÿ�������������,��ɺ�ǵ���ȡ����");

						tv_lei_shu.setVisibility(View.VISIBLE);
						tv_lei_shu.setText("�����");
						tv_lei_ling.setText("����ȡ");
						tv_lei_ling.setOnClickListener(null);
						iv_lei_ling.setVisibility(View.GONE);

					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else if (msg.what == 4) {
				try {

					JSONObject jo = new JSONObject(msg.obj.toString());
					if (jo.getBoolean("data")) {
						iv_zd_rwling.setOnClickListener(null);
						iv_zd_rwling
								.setBackgroundResource(R.drawable.zd_know_btn_receive_sel);
						tv_ling.setText("����ȡ");
					} else {
						iv_zd_rwling
								.setBackgroundResource(R.drawable.zd_know_btn_receive_nor);
						tv_ling.setText("�����ȡ");

						/**
						 * �����ȡ�ĵ���¼�
						 */
						iv_zd_rwling.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								iv_zd_rwling
										.setBackgroundResource(R.drawable.zd_know_btn_receive_sel);
								tv_ling.setText("����ȡ");
								getCard();
							}
						});
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else if (msg.what == 5) {
				tv_zd_rwqian_jiang.setText("����:" + cardNum + "�Źιο�");
				// ������������ιο�
				tv_zd_rwqian_jiang.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// �����ιο�
						if (cardNum > 0) {
							showPopWindow();
						} else {
							Toast.makeText(getApplicationContext(),
									"�ܱ�Ǹ����û�п���~", Toast.LENGTH_SHORT).show();
						}

					}
				});

			} else if (msg.what == 6) {

				try {
					JSONObject jo = new JSONObject(msg.obj.toString());
					if (jo.getBoolean("result")) {
						cardNum = cardNum + 1;
						handler.sendEmptyMessage(5);
						iv_zd_rwling.setOnClickListener(null);
						iv_zd_rwling
								.setBackgroundResource(R.drawable.zd_know_btn_receive_sel);
						// �����ιο�
						showPopWindow();

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

			} else if (msg.what == 7) {

				try {
					JSONObject jo = new JSONObject(msg.obj.toString());
					if (jo.getBoolean("result")) {
						System.out.println("==��ȡpopWindow����Դ==="
								+ msg.obj.toString());
						cardNum = cardNum - 1;
						tv_guamore.setText("�ٹ�һ��" + "(��ʣ" + cardNum + "�ſ�)");
						handler.sendEmptyMessage(5);
						JSONObject ja = jo.getJSONObject("data");
						prizePic = ja.getString("prizePic");
						prizeName = ja.getString("prizeName");
						new Thread() {
							public void run() {
								bitmap = ImageOptions.getBitmap(Setting.apiUrl
										+ prizePic);
								handler.sendEmptyMessage(9);
							}
						}.start();

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

			} else if (msg.what == 8) {
				String name = prizeName;
				if ("δ��".equals(name)) {
					ll_prizepic
							.setBackgroundResource(R.drawable.zd_scratch_regreat_bg);
				} else if ("��ȡһ�Źιο�".equals(name)) {
					cardNum = cardNum + 1;
					handler.sendEmptyMessage(5);
					ll_prizepic
							.setBackgroundResource(R.drawable.zd_scratch_con_bg);
				} else {
					ll_prizepic
							.setBackgroundResource(R.drawable.zd_scratch_con_bg);
				}
				tv_prizename.setText(name);
			} else if (msg.what == 9) {
				// �ѽ�ƷͼƬ������ȥ
				scr_view.setScratchBackground(bitmap);
			} else if (msg.what == 10) {
				try {
					// ������ȡ
					iv_da_ling.setVisibility(View.GONE);
					tv_da_shu.setVisibility(View.VISIBLE);
					JSONObject jo = new JSONObject(msg.obj.toString());
					tv_da_taskname.setText(jo.getString("taskName"));
					tv_da_jiang.setText("����:" + jo.getInt("lcoin") + "�ֱ�   "
							+ jo.getInt("gcoin") + "���   " + jo.getInt("hcoin")
							+ "����ֵ");
					// ����˵��
					tv_da_shuoming.setText("1.������APP��"
							+ jo.getString("taskName")
							+ ",�����������\n2.ÿ�������������,��ɺ�ǵ���ȡ����");
					tv_da_shu.setText(jo.getString("taskInfo"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else if (msg.what == 11) {
				try {
					// ������ȡ
					System.out.println("�������ȡ");
					iv_cai_ling.setVisibility(View.GONE);
					tv_cai_shu.setVisibility(View.VISIBLE);
					JSONObject jo = new JSONObject(msg.obj.toString());
					tv_cai_taskname.setText(jo.getString("taskName"));
					tv_cai_jiang.setText("����:" + jo.getInt("lcoin") + "�ֱ�   "
							+ jo.getInt("gcoin") + "���   " + jo.getInt("hcoin")
							+ "����ֵ");
					// ����˵��
					tv_cai_shuoming.setText("1." + jo.getString("taskName")
							+ ",�����������\n2.ÿ�������������,��ɺ�ǵ���ȡ����");
					tv_cai_shu.setText(jo.getString("taskInfo"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else if (msg.what == 12) {
				try {
					// ������ȡ
					System.out.println("�������ȡ�ۼƽ���");
					iv_lei_ling.setVisibility(View.GONE);
					tv_lei_shu.setVisibility(View.VISIBLE);
					JSONObject jo = new JSONObject(msg.obj.toString());
					tv_lei_taskname.setText(jo.getString("taskName"));
					tv_lei_jiang.setText("����:" + jo.getInt("lcoin") + "�ֱ�   "
							+ jo.getInt("gcoin") + "���   " + jo.getInt("hcoin")
							+ "����ֵ");
					// ����˵��
					tv_lei_shuoming.setText("1.��APP��"
							+ jo.getString("taskName")
							+ ",�����������\n2.ÿ�������������,��ɺ�ǵ���ȡ����");

					tv_lei_shu.setText(jo.getString("taskInfo"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_myrenwu);
		init();
		showinfo();
		addlistener();
	}

	private void showinfo() {

		/**
		 * 
		 * ��ȡ����׬���������
		 */
		new Thread() {
			public void run() {

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("taskType", 1);
				Message msg = new Message();
				String result = HttpUse.messageget("QueAndAns", "getUserTask",
						map);
				// TODO
				System.out.println("====����һ++��ȡ����׬���������====" + result);
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = jo.getString("data");
					} else {
						msg.obj = jo.getString("message");
					}
				} catch (JSONException e) {
					msg.obj = e.getMessage();
				}

				handler.sendMessage(msg);

			};
		}.start();
		/**
		 * ��ȡ����׬���������
		 */
		new Thread() {
			public void run() {

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("taskType", 2);
				Message msg = new Message();
				String result = HttpUse.messageget("QueAndAns", "getUserTask",
						map);
				// TODO
				System.out.println("====���Ͷ�++��ȡ����׬���������====" + result);
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 2;
						msg.obj = jo.getString("data");
					} else {
						msg.obj = jo.getString("message");
					}
				} catch (JSONException e) {
					msg.obj = e.getMessage();
				}

				handler.sendMessage(msg);

			};
		}.start();
		/**
		 * ��ȡ�ۼƲ��ɵ�����
		 */
		new Thread() {
			public void run() {

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("taskType", 3);
				Message msg = new Message();
				String result = HttpUse.messageget("QueAndAns", "getUserTask",
						map);
				// TODO
				System.out.println("====������++ ��ȡ�ۼƲ��ɵ�����====" + result);
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 3;
						msg.obj = jo.getString("data");
					} else {
						msg.obj = jo.getString("message");
					}
				} catch (JSONException e) {
					msg.obj = e.getMessage();
				}

				handler.sendMessage(msg);

			};
		}.start();

		/***
		 * ��ѯ�Ƿ��Ѿ���ȡ���ιο�
		 */
		new Thread() {
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				Message msg = new Message();
				String result = HttpUse.messageget("QueAndAns", "isReceive",
						map);
				System.out.println("===��ѯ�Ƿ���ιο�===" + result);
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 4;
						msg.obj = result;
						// msg.obj����û��ֵ
					} else {
						msg.obj = jo.getString("message");
					}
				} catch (JSONException e) {
					msg.obj = e.getMessage();
				}

				handler.sendMessage(msg);

			};
		}.start();

		/**
		 * ��ȡ�ιο�������
		 */

		new Thread() {
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				Message msg = new Message();
				String result = HttpUse.messageget("QueAndAns", "getUserCard",
						map);
				System.out.println("===��ȡ�ιο�����===" + result + "zhang");
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 5;
						cardNum = jo.getInt("data");
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

	private void init() {
		ll_zd_renwu_back = (LinearLayout) findViewById(R.id.ll_zd_renwu_back);
		ll_zd_qian_content = (LinearLayout) findViewById(R.id.ll_zd_qian_content);
		ll_da_content = (LinearLayout) findViewById(R.id.ll_da_content);
		ll_cai_content = (LinearLayout) findViewById(R.id.ll_cai_content);
		ll_lei_content = (LinearLayout) findViewById(R.id.ll_lei_content);
		/**
		 * ���ӵ�
		 */
		ll_denglu = (LinearLayout) findViewById(R.id.ll_denglu);
		ll_da = (LinearLayout) findViewById(R.id.ll_da);
		ll_cai = (LinearLayout) findViewById(R.id.ll_cai);
		ll_lei = (LinearLayout) findViewById(R.id.ll_lei);

		tv_zd_rwqian_jiang = (TextView) findViewById(R.id.tv_zd_rwqian_jiang);
		tv_ling = (TextView) findViewById(R.id.tv_ling);
		tv_da_jiang = (TextView) findViewById(R.id.tv_da_jiang);
		tv_da_shu = (TextView) findViewById(R.id.tv_da_shu);
		tv_da_ling = (TextView) findViewById(R.id.tv_da_ling);
		tv_cai_jiang = (TextView) findViewById(R.id.tv_cai_jiang);
		tv_cai_shu = (TextView) findViewById(R.id.tv_cai_shu);
		tv_cai = (TextView) findViewById(R.id.tv_cai);
		tv_cai_ling = (TextView) findViewById(R.id.tv_cai_ling);
		tv_lei_jiang = (TextView) findViewById(R.id.tv_lei_jiang);
		tv_lei_shu = (TextView) findViewById(R.id.tv_lei_shu);
		tv_lei_ling = (TextView) findViewById(R.id.tv_lei_ling);
		tv_zd_rwqian = (TextView) findViewById(R.id.tv_zd_rwqian);
		tv_da = (TextView) findViewById(R.id.tv_da);
		tv_lei = (TextView) findViewById(R.id.tv_lei);
		// ��������
		tv_da_taskname = (TextView) findViewById(R.id.tv_da_taskname);
		tv_cai_taskname = (TextView) findViewById(R.id.tv_cai_taskname);
		tv_lei_taskname = (TextView) findViewById(R.id.tv_lei_taskname);
		// ����˵��
		tv_da_shuoming = (TextView) findViewById(R.id.tv_da_shuoming);
		tv_cai_shuoming = (TextView) findViewById(R.id.tv_cai_shuoming);
		tv_lei_shuoming = (TextView) findViewById(R.id.tv_lei_shuoming);

		iv_zd_rwling = (ImageView) findViewById(R.id.iv_zd_rwling);
		iv_da_ling = (ImageView) findViewById(R.id.iv_da_ling);
		iv_cai_ling = (ImageView) findViewById(R.id.iv_cai_ling);
		iv_lei_ling = (ImageView) findViewById(R.id.iv_lei_ling);
	}

	// ����ν���������
	private void getCardMessage() {

		new Thread() {
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				Message msg = new Message();
				String result = HttpUse.messageget("QueAndAns", "scratchCard",
						map);
				System.out.println("===�ο�===" + result);
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 7;
						msg.obj = result;
						// msg.obj����û��ֵ
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

	private void getCard() {
		// ������ȡ�Ľӿ�
		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				Message msg = new Message();
				String result = HttpUse.messageget("QueAndAns", "receiveCard",
						map);
				System.out.println("===��ιο�===" + result);
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 6;
						msg.obj = result;
					} else {
						msg.obj = jo.getString("message");
					}
				} catch (JSONException e) {
					msg.obj = e.getMessage();
				}

				handler.sendMessage(msg);

			}
		}.start();
	}

	private void showPopWindow() {
		// ����contentView
		View contentView = LayoutInflater.from(RenWuActivity.this).inflate(
				R.layout.popuplayout, null);
		ll_prizepic = (LinearLayout) contentView.findViewById(R.id.ll_prizepic);
		ll_prizepic.setBackgroundResource(R.drawable.zd_scratch_bg);
		scr_view = (ScratchView) contentView.findViewById(R.id.scr_view);
		tv_prizename = (TextView) contentView.findViewById(R.id.tv_prizename);
		tv_prizename.setText("�Ͻ��ο��ν���������������\n		�������ʲô��!");
		// ���д��ͼ����һ�£����ˣ����п���
		// scr_view.setScratchBackground((Bitmap) map.get("bitmap"));
		scr_view.addStartListener(new OnStartListener() {

			@Override
			public void startListener() {
				// ��ʼ�ο�������Ƭ����
				getCardMessage();
			}

		});
		scr_view.addShowListener(new OnShowListener() {

			@Override
			public void showListener() {
				// TODO �ѹο�
				System.out.println("����һ���Ƿ����������50%");
				handler.sendEmptyMessage(8);
			}

		});
		mPopWindow = new PopupWindow(contentView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, true);
		mPopWindow.setContentView(contentView);
		mPopWindow.setFocusable(true); // ����PopupWindow�ɻ�ý���
		mPopWindow.setTouchable(true); // ����PopupWindow�ɴ���
		// mPopWindow.setOutsideTouchable(true); // ���÷�PopupWindow����ɴ���
		mPopWindow.setBackgroundDrawable(new BitmapDrawable());
		// ��ʾPopupWindow
		View rootview = LayoutInflater.from(RenWuActivity.this).inflate(
				R.layout.activity_myrenwu, null);
		mPopWindow.showAtLocation(rootview, Gravity.CENTER, 5, 5);
		// �رհ�ť
		ImageView iv_close = (ImageView) contentView
				.findViewById(R.id.iv_close);
		/**
		 * �ٹ�һ��
		 */
		tv_guamore = (TextView) contentView.findViewById(R.id.tv_guamore);
		tv_guamore.setText("�ٹ�һ��" + "(��ʣ" + cardNum + "�ſ�)");
		tv_guamore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (cardNum > 0) {
					ll_prizepic.setBackgroundResource(R.drawable.zd_scratch_bg);
					tv_prizename.setText("�Ͻ��ο��ν���������������\n		�������ʲô��!");
					// Ӧ�õ����Ǹ�����
					getCardMessage();
				} else {
					Toast.makeText(getApplicationContext(), "�ܱ�Ǹ����û�п���~",
							Toast.LENGTH_SHORT).show();
				}

			}
		});
		/**
		 * ����ر�popWindow
		 */
		iv_close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPopWindow.dismiss();
				iv_zd_rwling
						.setBackgroundResource(R.drawable.zd_know_btn_receive_sel);
				tv_ling.setText("����ȡ");
			}
		});
	}

	private void addlistener() {
		// ���ؼ�
		ListenerServer.setfinish(RenWuActivity.this, ll_zd_renwu_back);
		/**
		 * ǩ�����������������
		 */
		ll_denglu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkedItem == 0) {
					checkedItem = -1;
				} else {
					checkedItem = 0;
				}
				hideAll();
			}
		});

		/**
		 * ����׬������������������
		 */
		ll_da.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkedItem == 1) {
					checkedItem = -1;
				} else {
					checkedItem = 1;
				}
				hideAll();

			}
		});
		tv_da_ling.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RenWuActivity.this,
						ZhidaoActivity.class);
				startActivity(intent);
			}
		});
		/**
		 * ����׬������������������
		 */
		ll_cai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkedItem == 2) {
					checkedItem = -1;
				} else {
					checkedItem = 2;
				}
				hideAll();

			}
		});
		tv_cai_ling.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RenWuActivity.this,
						ZhidaoActivity.class);
				startActivity(intent);

			}
		});
		/**
		 * �ۼƲ��ɵ��������������
		 */
		ll_lei.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkedItem == 3) {
					checkedItem = -1;
				} else {
					checkedItem = 3;
				}
				hideAll();
			}
		});
		tv_lei_ling.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RenWuActivity.this,
						ZhidaoActivity.class);
				startActivity(intent);
			}
		});

		// ������ȡ�ĵ���¼�
		iv_cai_ling.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread() {
					public void run() {
						// TODO �����������ݵ��� getUserTask//������

						Map<String, Object> map = new HashMap<String, Object>();
						// ��Ҫ��
						map.put("pkTask", pkTask2);
						System.out.println("����pkTask2" + pkTask2);
						map.put("taskType", 2);
						Message msg = new Message();
						String result = HttpUse.messageget("QueAndAns",
								"taskaward", map);
						System.out.print("��ȡ������" + result);
						try {
							JSONObject jo = new JSONObject(result);
							if (jo.getBoolean("result")) {
								msg.what = 11;
								msg.obj = jo.getString("data");
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
		});

		// ������ȡ�ĵ���¼�
		iv_da_ling.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread() {
					public void run() {
						// TODO �����������ݵ��� taskaward//������

						Map<String, Object> map = new HashMap<String, Object>();
						// ��Ҫ��
						map.put("pkTask", pkTask1);
						System.out.println("����pkTask1" + pkTask1);
						map.put("taskType", 1);
						Message msg = new Message();
						String result = HttpUse.messageget("QueAndAns",
								"taskaward", map);
						try {
							JSONObject jo = new JSONObject(result);
							if (jo.getBoolean("result")) {
								msg.what = 10;
								msg.obj = jo.getString("data");
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
		});
		// �ۼƲ�����ȡ�ĵ���¼�
		iv_lei_ling.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread() {
					public void run() {
						// TODO �����������ݵ��� getUserTask//������

						Map<String, Object> map = new HashMap<String, Object>();
						// ��Ҫ��
						map.put("pkTask", pkTask3);
						System.out.println("����pkTask3" + pkTask3);
						map.put("taskType", 3);
						Message msg = new Message();
						String result = HttpUse.messageget("QueAndAns",
								"taskaward", map);
						System.out.print("��ȡ������" + result);
						try {
							JSONObject jo = new JSONObject(result);
							if (jo.getBoolean("result")) {
								msg.what = 12;
								msg.obj = jo.getString("data");
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
		});
	}

	// �ж��Ƿ�չʾ�������
	private void hideAll() {
		tv_zd_rwqian.setBackgroundResource(R.drawable.zd_task_icon_down);
		tv_da.setBackgroundResource(R.drawable.zd_task_icon_down);
		tv_cai.setBackgroundResource(R.drawable.zd_task_icon_down);
		tv_lei.setBackgroundResource(R.drawable.zd_task_icon_down);
		ll_zd_qian_content.setVisibility(View.GONE);
		ll_da_content.setVisibility(View.GONE);
		ll_cai_content.setVisibility(View.GONE);
		ll_lei_content.setVisibility(View.GONE);
		switch (checkedItem) {
		case -1:
			break;
		case 0:
			tv_zd_rwqian.setBackgroundResource(R.drawable.zd_task_icon_up);
			ll_zd_qian_content.setVisibility(View.VISIBLE);
			break;
		case 1:
			tv_da.setBackgroundResource(R.drawable.zd_task_icon_up);
			ll_da_content.setVisibility(View.VISIBLE);
			break;
		case 2:
			tv_cai.setBackgroundResource(R.drawable.zd_task_icon_up);
			ll_cai_content.setVisibility(View.VISIBLE);
			break;
		case 3:
			tv_lei.setBackgroundResource(R.drawable.zd_task_icon_up);
			ll_lei_content.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

}