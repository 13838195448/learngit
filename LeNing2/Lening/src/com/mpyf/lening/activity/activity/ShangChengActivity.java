package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.activity.adapter.ShangCheng_LeAdapter;
import com.mpyf.lening.activity.adapter.Vpadapter;
import com.mpyf.lening.activity.fragment.GoldShangFragment;
import com.mpyf.lening.activity.fragment.LeShangFragment;
import com.mpyf.lening.interfaces.http.HttpUse;

public class ShangChengActivity extends FragmentActivity {

	private TextView tv_shangcheng_title;
	private LinearLayout ll_shangcheng_back;
	private RelativeLayout rl_shangcheng_le, rl_shangcheng_jin, rl_search;
	private TextView tv_shangcheng_le, tv_shangcheng_jin;
	private ImageView iv_shangcheng_le, iv_shangcheng_jin, iv_search;
	private ViewPager vp_shangcheng;
	private List<Fragment> fr = new ArrayList<Fragment>();
	private EditText et_search;
	private List<Map<String, Object>> list;
	private ListView lv_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shangcheng);
		init();
		showinfo();
		addlistener();
	}

	private void init() {
		et_search = (EditText) findViewById(R.id.et_search);
		lv_list = (ListView) findViewById(R.id.lv_list);

		ll_shangcheng_back = (LinearLayout) findViewById(R.id.ll_shangcheng_back);
		tv_shangcheng_title = (TextView) findViewById(R.id.tv_shangcheng_title);
		tv_shangcheng_title.setText("�̳�");

		iv_search = (ImageView) findViewById(R.id.iv_search);
		vp_shangcheng = (ViewPager) findViewById(R.id.vp_shangcheng);

		rl_shangcheng_le = (RelativeLayout) findViewById(R.id.rl_shangcheng_le);
		rl_shangcheng_jin = (RelativeLayout) findViewById(R.id.rl_shangcheng_jin);
		rl_search = (RelativeLayout) findViewById(R.id.rl_search);

		iv_shangcheng_le = (ImageView) findViewById(R.id.iv_shangcheng_le);
		iv_shangcheng_jin = (ImageView) findViewById(R.id.iv_shangcheng_jin);

		tv_shangcheng_le = (TextView) findViewById(R.id.tv_shangcheng_le);
		tv_shangcheng_jin = (TextView) findViewById(R.id.tv_shangcheng_jin);

		fr.add(new LeShangFragment());
		fr.add(new GoldShangFragment());
		Vpadapter adapter = new Vpadapter(getSupportFragmentManager(), fr);
		vp_shangcheng.setAdapter(adapter);

	}

	private void showinfo() {

	}

	private void addlistener() {
		// ���ؼ�
		ListenerServer.setfinish(ShangChengActivity.this, ll_shangcheng_back);
		// �ֱ�
		rl_shangcheng_le.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clearall(0);
				vp_shangcheng.setCurrentItem(0);
			}
		});
		// ���
		rl_shangcheng_jin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clearall(1);
				vp_shangcheng.setCurrentItem(1);
			}
		});
		// ����
		iv_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				searchLebi();
			}

		});
		vp_shangcheng.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// �����ж� arg0 = 0�ֱң�1���
				if (arg0 == 0) {
					addListener();
				} else if (arg0 == 1) {
					search();
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				if (vp_shangcheng.getCurrentItem() == 0) {
					tv_shangcheng_le.setTextColor(getResources().getColor(
							R.color.main));
					tv_shangcheng_jin.setTextColor(getResources().getColor(
							R.color.zywz));
					iv_shangcheng_le.setBackgroundColor(getResources()
							.getColor(R.color.main));
					iv_shangcheng_jin.setBackgroundColor(getResources()
							.getColor(R.color.dise));
				} else if (vp_shangcheng.getCurrentItem() == 1) {
					tv_shangcheng_le.setTextColor(getResources().getColor(
							R.color.zywz));
					tv_shangcheng_jin.setTextColor(getResources().getColor(
							R.color.main));
					iv_shangcheng_le.setBackgroundColor(getResources()
							.getColor(R.color.dise));
					iv_shangcheng_jin.setBackgroundColor(getResources()
							.getColor(R.color.main));
				}

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	protected void search() {
		iv_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				lv_list.setVisibility(View.VISIBLE);
				vp_shangcheng.setVisibility(View.GONE);
				if (rl_search.getVisibility() == View.INVISIBLE) {
					rl_search.setVisibility(View.VISIBLE);
					tv_shangcheng_title.setVisibility(View.GONE);
				} else if (rl_search.getVisibility() == View.VISIBLE) {
					final Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							if (msg.what == 1) {
								// ��ȡhandler���������ݣ��ŵ�map��,��ӵ�list����
								try {
									list = new ArrayList<Map<String, Object>>();
									JSONArray ja = new JSONArray(msg.obj
											.toString());
									for (int i = 0; i < ja.length(); i++) {
										Map<String, Object> map = new HashMap<String, Object>();
										JSONObject jo = ja.getJSONObject(i);
										// ��Ʒ����
										map.put("pk_goods",
												jo.getString("pk_goods"));
										// ��Ʒ����
										map.put("goodsName",
												jo.getString("goodsName"));
										// �����
										map.put("inventory",
												jo.getInt("inventory"));
										// ��������
										map.put("buy_num", jo.getInt("buy_num"));
										// �¹�������
										map.put("mbuy_num",
												jo.getInt("mbuy_num"));
										// ͼƬ��ַ
										map.put("picUrl",
												jo.getString("picUrl"));
										// ���
										map.put("remark",
												jo.getString("remark"));
										// �ۼ�
										map.put("amount",
												jo.getString("amount"));
										// ����ʱ��
										map.put("onlineTime",
												jo.getString("onlineTime"));

										list.add(map);
									}
									// ��list����adapter��������Դչʾ
									ShangCheng_LeAdapter adapter = new ShangCheng_LeAdapter(
											ShangChengActivity.this, list);
									// vp_qa.setCurrentItem(0);
									// ��������ݴ���ȥ��
									lv_list.setAdapter(adapter);
									// listview ��Ŀ����¼�

									lv_list.setOnItemClickListener(new OnItemClickListener() {

										@Override
										public void onItemClick(
												AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
											// TODO Auto-generated method stub
											Intent intent = new Intent(
													ShangChengActivity.this,
													ShangQadetilActivity.class);
											intent.putExtra("id", list
													.get(arg2).get("pk_goods")
													.toString());
											startActivity(intent);
										}
									});

								} catch (JSONException e) {
									Diaoxian.showerror(ShangChengActivity.this,
											e.getMessage());
								}
							} else {
								Diaoxian.showerror(ShangChengActivity.this,
										msg.obj.toString());
							}
						}
					};

					new Thread() {
						@Override
						public void run() {

							Map<String, Object> map = new HashMap<String, Object>();
							map.put("page", 1);
							map.put("pageSize", 20);
							map.put("buyWay", 2);
							// ��Ʒ��������
							map.put("goodsName", et_search.getText().toString());
							Message msg = new Message();

							String result = HttpUse.messageget("QueAndAns",
									"listGoods", map);
							// TODO ��ӡ���󵽵�����
							System.out
									.println("=====��ӡ�������󵽵Ľ����Ʒ====" + result);

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

						}
					}.start();
				}
			}
		});
	}

	protected void addListener() {
		iv_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				searchLebi();
			}
		});
	}

	private void clearall(int index) {
		TextView[] texttop = { tv_shangcheng_le, tv_shangcheng_jin };
		ImageView[] imagetop = { iv_shangcheng_le, iv_shangcheng_jin };

		for (int i = 0; i < texttop.length; i++) {
			if (i == index) {
				texttop[i].setTextColor(getResources().getColor(R.color.main));
				imagetop[i].setBackgroundColor(getResources().getColor(
						R.color.main));
			} else {
				texttop[i].setTextColor(getResources().getColor(R.color.zywz));
				imagetop[i].setBackgroundColor(getResources().getColor(
						R.color.dise));
			}
		}
	}

	public void searchLebi() {
		lv_list.setVisibility(View.VISIBLE);
		vp_shangcheng.setVisibility(View.GONE);
		if (rl_search.getVisibility() == View.INVISIBLE) {
			rl_search.setVisibility(View.VISIBLE);
			tv_shangcheng_title.setVisibility(View.GONE);
		} else if (rl_search.getVisibility() == View.VISIBLE) {
			final Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					if (msg.what == 1) {
						// ��ȡhandler���������ݣ��ŵ�map��,��ӵ�list����
						try {
							list = new ArrayList<Map<String, Object>>();
							JSONArray ja = new JSONArray(msg.obj.toString());
							for (int i = 0; i < ja.length(); i++) {
								Map<String, Object> map = new HashMap<String, Object>();
								JSONObject jo = ja.getJSONObject(i);
								// ��Ʒ����
								map.put("pk_goods", jo.getString("pk_goods"));
								// ��Ʒ����
								map.put("goodsName", jo.getString("goodsName"));
								// �����
								map.put("inventory", jo.getInt("inventory"));
								// ��������
								map.put("buy_num", jo.getInt("buy_num"));
								// �¹�������
								map.put("mbuy_num", jo.getInt("mbuy_num"));
								// ͼƬ��ַ
								map.put("picUrl", jo.getString("picUrl"));
								// ���
								map.put("remark", jo.getString("remark"));
								// �ۼ�
								map.put("amount", jo.getString("amount"));
								// ����ʱ��
								map.put("onlineTime",
										jo.getString("onlineTime"));

								list.add(map);
							}
							// ��list����adapter��������Դչʾ
							ShangCheng_LeAdapter adapter = new ShangCheng_LeAdapter(
									ShangChengActivity.this, list);
							// vp_qa.setCurrentItem(0);
							// ��������ݴ���ȥ��
							lv_list.setAdapter(adapter);
							// listview ��Ŀ����¼�

							lv_list.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									Intent intent = new Intent(
											ShangChengActivity.this,
											ShangQadetilActivity.class);
									intent.putExtra("id",
											list.get(arg2).get("pk_goods")
													.toString());
									startActivity(intent);
								}
							});

						} catch (JSONException e) {
							Diaoxian.showerror(ShangChengActivity.this,
									e.getMessage());
						}
					} else {
						Diaoxian.showerror(ShangChengActivity.this,
								msg.obj.toString());
					}
				}
			};

			new Thread() {
				@Override
				public void run() {

					Map<String, Object> map = new HashMap<String, Object>();
					map.put("page", 1);
					map.put("pageSize", 20);
					map.put("buyWay", 1);
					// ��Ʒ��������
					map.put("goodsName", et_search.getText().toString());
					Message msg = new Message();
//					System.out.println("�����Ĺؼ���"
//							+ et_search.getText().toString());
					String result = HttpUse.messageget("QueAndAns",
							"listGoods", map);
					// TODO ��ӡ���󵽵�����
//					System.out.println("=====��ӡ�������󵽵��ֱ���Ʒ====" + result);

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

				}
			}.start();
		}
	}
}
