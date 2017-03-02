package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.MyGridview;
import com.mpyf.lening.Jutil.Roundbitmap;
import com.mpyf.lening.activity.adapter.AgridviewAdapter;
import com.mpyf.lening.activity.adapter.RepayAdapter2;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * @author Administrator 点击进入的二级子页面
 * 
 */
public class QadetilActivity extends Activity {

	private LinearLayout ll_qadetil_back;
	private ImageView iv_qadetil_touxiang, iv_qadetil_payway,
			iv_qadetil_shoucang;
	private TextView tv_qadetil_name, tv_qadetil_time, tv_qadetil_cost,
			tv_qadetil_comments, tv_qadetil_context, tv_tou_chenghao,
			tv_qadetil_shoucang;

	private int lzid = 0;
	private TextView tv_qadetil_repay;
	private ListView lv_qadetil;
	private List<Map<String, Object>> list;
	String result = "";
	private MyGridview gv_pic;

	private static int page = 1;
	private boolean is_divpage;
	private ArrayList<Map<String, Object>> data;
	private RepayAdapter2 adapter;
	private String ans_num;
	private int stae;
	private String qtruename;
	private String a;
	private DisplayImageOptions options;
	private String reward;

	private String content;
	private LinearLayout ll_qadetil_shoucang;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// new Xiaoxibeijing().changetop(this, R.color.main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wendadtile);

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.icon_defualt)
				.showImageOnFail(R.drawable.icon_defualt)
				.showImageForEmptyUri(R.drawable.icon_defualt)
				.cacheInMemory(true).cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.ALPHA_8).build();
		page = 1;
		init();
		showinfo();
		addlistener();

	}

	private void init() {

		lv_qadetil = (ListView) findViewById(R.id.lv_qadetil);
		View headerView = getLayoutInflater().inflate(
				R.layout.activity_wenda_tou, null);

		ll_qadetil_back = (LinearLayout) findViewById(R.id.ll_qadetil_back);
		iv_qadetil_touxiang = (ImageView) headerView
				.findViewById(R.id.iv_qadetil_touxiang);
		iv_qadetil_payway = (ImageView) headerView
				.findViewById(R.id.iv_qadetil_payway);
		// iv_qadetil_shoucang = (ImageView) headerView
		// .findViewById(R.id.iv_qadetil_shoucang);
		tv_qadetil_name = (TextView) headerView
				.findViewById(R.id.tv_qadetil_name);
		// 称号
		tv_tou_chenghao = (TextView) headerView
				.findViewById(R.id.tv_tou_chenghao);
		// 收藏
		ll_qadetil_shoucang = (LinearLayout) headerView
				.findViewById(R.id.ll_qadetil_shoucang);
		iv_qadetil_shoucang = (ImageView) headerView
				.findViewById(R.id.iv_qadetil_shoucang);
		tv_qadetil_shoucang = (TextView) headerView
				.findViewById(R.id.tv_qadetil_shoucang);
		tv_qadetil_time = (TextView) headerView
				.findViewById(R.id.tv_qadetil_time);
		tv_qadetil_cost = (TextView) headerView
				.findViewById(R.id.tv_qadetil_cost);
		tv_qadetil_comments = (TextView) headerView
				.findViewById(R.id.tv_qadetil_comments);
		tv_qadetil_context = (TextView) headerView
				.findViewById(R.id.tv_qadetil_context);
		tv_qadetil_repay = (TextView) findViewById(R.id.tv_qadetil_repay);

		gv_pic = (MyGridview) headerView.findViewById(R.id.gv_pic);
		lv_qadetil.addHeaderView(headerView);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		showinfo();

		adapter.notifyDataSetChanged();
	}

	private void showinfo() {
		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						JSONObject jo = new JSONObject(msg.obj.toString());

						int num = (Integer) jo.getInt("pic_num");
						int[] arr = new int[num];
						for (int i = 0; i < arr.length; i++) {
							arr[i] = i + 1;
						}
						a = jo.getString("PK_Que");
						AgridviewAdapter newGrideView = new AgridviewAdapter(
								QadetilActivity.this, arr, a);
						gv_pic.setAdapter(newGrideView);

						ImageLoader imageLoader = ImageLoader.getInstance();
						imageLoader.displayImage(
								Setting.apiUrl + "new-pages/PersonalPhoto/"
										+ jo.getInt("pk_user") + ".jpg",
								iv_qadetil_touxiang, options);
						// AsyncBitmapLoader.setRoundImage(iv_qadetil_touxiang,
						// Setting.apiUrl+"new-pages/PersonalPhoto/"+jo.getInt("pk_user")+".jpg");
						lzid = jo.getInt("pk_user");
						iv_qadetil_touxiang.setImageBitmap(Roundbitmap
								.toRoundBitmap(iv_qadetil_touxiang));
						qtruename = jo.getString("trueName").toString();

						tv_qadetil_name.setText(jo.getString("trueName"));
						// 称号
						tv_tou_chenghao.setText("("
								+ jo.getString("honor_name") + ")");
						tv_qadetil_time.setText(jo.getString("queTime")
								.substring(0,
										jo.getString("queTime").indexOf(" ")));
						tv_qadetil_cost.setText(jo.getString("REWARD_Num"));
						tv_qadetil_comments.setText(jo.getString("ans_Num"));
						tv_qadetil_context.setText(jo.getString("QUE_CONTENT"));
						reward = jo.getString("REWARD_Num");
						ans_num = jo.getString("ans_Num");

						stae = (Integer) jo.getInt("QUE_STATE");
						content = jo.getString("QUE_CONTENT");

						// 判断是否收藏
						if ((Integer) jo.get("isCollection") == 0) {
							tv_qadetil_shoucang.setText("添加收藏");
							iv_qadetil_shoucang
									.setBackgroundResource(R.drawable.know_collect_nor);
							ll_qadetil_shoucang
									.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											// 调用收藏的接口

											final Handler handler = new Handler() {
												@Override
												public void handleMessage(
														Message msg) {
													System.out
															.println("===1，收藏成功"
																	+ msg.what);
													switch (msg.what) {

													case 1:
														Toast.makeText(
																QadetilActivity.this,
																"收藏成功",
																Toast.LENGTH_SHORT)
																.show();
														tv_qadetil_shoucang
																.setText("已收藏");
														iv_qadetil_shoucang
																.setBackgroundResource(R.drawable.know_collect_selected);

														ll_qadetil_shoucang
																.setOnClickListener(null);
														break;
													default:
														Toast.makeText(
																QadetilActivity.this,
																msg.obj.toString(),
																Toast.LENGTH_SHORT)
																.show();
														break;

													}
												}
											};

											// 有两种方法，一种是在调用添加收藏接口前，先调用判断是否已收藏的接口，另一种我现在写，你看你用哪个，前面那种代码稍微简单点
											new Thread() {

												public void run() {
													Message msg = new Message();
													Map<String, Object> map = new HashMap<String, Object>();

													map.put("queId", a);
													System.out
															.println("==测试问题id是否为点击的=="
																	+ a);

													String result = HttpUse
															.messageget(
																	"QueAndAns",
																	"collectionQue",
																	map);
													System.out
															.println("===打印添加收藏结果==="
																	+ result);
													try {
														JSONObject jo = new JSONObject(
																result);
														// 添加收藏返回，
														// true,表示收藏成功；false,表示已经收藏过
														if (jo.getBoolean("result")) {
															msg.what = 1;
														}
														msg.obj = jo
																.get("message");
													} catch (JSONException e) {
														msg.obj = e
																.getMessage();
													}

													handler.sendMessage(msg);

												};
											}.start();

										}
									});

						} else {
							tv_qadetil_shoucang.setText("已收藏");
							iv_qadetil_shoucang
									.setBackgroundResource(R.drawable.know_collect_selected);
							ll_qadetil_shoucang.setOnClickListener(null);
						}

						if (stae == 0) {
							tv_qadetil_context.setText(Html
									.fromHtml("<font color='red'>[未解决]</font> "
											+ jo.getString("QUE_CONTENT")));
						} else if (stae == 1) {
							tv_qadetil_context
									.setText(Html.fromHtml("<font color='#a2d46f'>[已解决]</font> "
											+ jo.getString("QUE_CONTENT")));
						}

						int payway = (Integer) jo.getInt("REWARD_WAY");

						if (payway == 1) {
							iv_qadetil_payway
									.setImageResource(R.drawable.know_icon_le);
						} else if (payway == 2) {
							iv_qadetil_payway
									.setImageResource(R.drawable.know_icon_jin);
						}

						if (lzid == Setting.currentUser.getPk_user()) {
							tv_qadetil_repay.setText("编辑问题");
						} else {
							tv_qadetil_repay.setText("回答问题");
						}

						getrepay();

					} catch (JSONException e) {
						Diaoxian.showerror(QadetilActivity.this, e.getMessage());
					}

				} else {
					Diaoxian.showerror(QadetilActivity.this, msg.obj.toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("queID", getIntent().getStringExtra("id"));
				Message msg = new Message();

				String result = HttpUse.messageget("QueAndAns", "getQuestion",
						map);
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

	private void getrepay() {

		// data = new ArrayList<Map<String, Object>>();
		// adapter = new RepayAdapter2(QadetilActivity.this, data);
		getData();

	}

	private void getData() {
		list = new ArrayList<Map<String, Object>>();
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						for (int i = 0; i < ja.length(); i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							JSONObject jo = ja.getJSONObject(i);
							map.put("lzid", lzid);
							map.put("id", jo.getString("PK_Ans"));
							map.put("qid", jo.getString("PK_Que"));
							map.put("Pk_user", jo.getString("pk_user"));
							map.put("trueName", jo.getString("trueName"));
							map.put("ANS_CONTENT", jo.getString("ANS_CONTENT"));
							map.put("GOOD_NUM", jo.getInt("GOOD_NUM"));
							map.put("BAD_NUM", jo.getInt("BAD_NUM"));
							map.put("AnsTime",
									jo.getString("ansTime").substring(
											0,
											jo.getString("ansTime")
													.indexOf(" ")));
							map.put("ANS_STATE", jo.getInt("ANS_STATE"));
							map.put("is_havechild",
									jo.getString("is_havechild"));
							map.put("is_child", jo.getInt("is_child"));
							map.put("p_pk_Ans", jo.getString("p_pk_Ans"));
							map.put("p_Pk_user", jo.getInt("p_Pk_user"));
							map.put("p_TrueName", jo.getString("p_TrueName"));
							map.put("pic_num", jo.getInt("pic_num"));
							map.put("honor_name", jo.getString("honor_name"));

							// Log.i("child", map.get("is_havechild") + "");
							// if (jo.getString("is_havechild").equals("1")) {
							// listyijianswer.add(map);
							// }else{
							list.add(map);
							// }

						}
						// RepayAdapter adapter=new
						// RepayAdapter(QadetilActivity.this, list);
						// if(list.size()!=0){

						adapter = new RepayAdapter2(QadetilActivity.this, list,
								stae, qtruename, tv_qadetil_context, content);
						// }
						lv_qadetil.setAdapter(adapter);
					} catch (JSONException e) {
						Diaoxian.showerror(QadetilActivity.this, e.getMessage());
					}

				} else {
					Diaoxian.showerror(QadetilActivity.this, msg.obj.toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("queID", getIntent().getStringExtra("id"));
				map.put("page", page);
				map.put("pageSize", 999);

				Message msg = new Message();

				String result = HttpUse.messageget("QueAndAns", "getAnswer",
						map);
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

	private void addlistener() {
		ListenerServer.setfinish(QadetilActivity.this, ll_qadetil_back);

		tv_qadetil_repay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (lzid == Setting.currentUser.getPk_user() && stae == 0) {
					Intent intent = new Intent(QadetilActivity.this,
							FatieActivity.class);
					intent.putExtra("titlename", "编辑问题");
					intent.putExtra("pK_id", a);
					intent.putExtra("reward", reward);
					intent.putExtra("content", content);
					startActivity(intent);
				} else if (stae == 1
						&& lzid == Setting.currentUser.getPk_user()) {
					Toast.makeText(getApplicationContext(), "问题已被采纳，不可编辑", 0)
							.show();
				} else {
					Intent intent = new Intent(QadetilActivity.this,
							RepayQueActivity.class);
					intent.putExtra("id", getIntent().getStringExtra("id"));
					startActivity(intent);
				}

			}
		});
	}

}
