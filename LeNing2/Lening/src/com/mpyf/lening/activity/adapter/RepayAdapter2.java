package com.mpyf.lening.activity.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.AsyncBitmapLoader;
import com.mpyf.lening.Jutil.ImageOptions;
import com.mpyf.lening.Jutil.MyGridview;
import com.mpyf.lening.activity.activity.QadetilActivity;
import com.mpyf.lening.activity.activity.RepayloucengActivity;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RepayAdapter2 extends BaseAdapter {

	private Context context;
	private List<Map<String, Object>> list;
	private int state;
	private String qtruename;
	private DisplayImageOptions options;

	private TextView tv_qadetil_context;
	private String content;
	private boolean isclicked;

	public RepayAdapter2(Context context, List<Map<String, Object>> list,
			int state, String qtruename, TextView tv_qadetil_context,
			String content) {
		this.context = context;
		this.list = list;
		this.state = state;
		this.qtruename = qtruename;
		this.tv_qadetil_context = tv_qadetil_context;
		this.content = content;
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.icon_defualt)
				.showImageOnFail(R.drawable.icon_defualt)
				.showImageForEmptyUri(R.drawable.icon_defualt)
				.cacheInMemory(true).cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.ALPHA_8).build();
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
	public View getView(int arg0, View convertView, ViewGroup arg2) {

		final ViewHolder holder;
		// if(convertView == null){
		holder = new ViewHolder();
		convertView = View.inflate(context, R.layout.item_repay, null);
		holder.iv_qarepay_touxiang = (ImageView) convertView
				.findViewById(R.id.iv_qarepay_touxiang);
		holder.tv_qarepay_name = (TextView) convertView
				.findViewById(R.id.tv_qarepay_name);
		// 称号
		holder.tv_qarepay_chenghao = (TextView) convertView
				.findViewById(R.id.tv_qarepay_chenghao);
		holder.tv_qarepay_time = (TextView) convertView
				.findViewById(R.id.tv_qarepay_time);
		holder.tv_qarepay_caina = (TextView) convertView
				.findViewById(R.id.tv_qarepay_caina);
		holder.tv_qarepay_context = (TextView) convertView
				.findViewById(R.id.tv_qarepay_context);
		holder.iv_itemrepay_good = (ImageView) convertView
				.findViewById(R.id.iv_itemrepay_good);
		holder.tv_itemrepay_good = (TextView) convertView
				.findViewById(R.id.tv_itemrepay_good);
		holder.iv_itemrepay_bad = (ImageView) convertView
				.findViewById(R.id.iv_itemrepay_bad);
		holder.tv_itemrepay_bad = (TextView) convertView
				.findViewById(R.id.tv_itemrepay_bad);
		holder.ll_itemrepay_repay = (LinearLayout) convertView
				.findViewById(R.id.ll_itemrepay_repay);
		holder.ll_itemrepay_good = (LinearLayout) convertView
				.findViewById(R.id.ll_itemrepay_good);
		holder.ll_itemrepay_bad = (LinearLayout) convertView
				.findViewById(R.id.ll_itemrepay_bad);
		holder.ll_itemrepay_answers = (LinearLayout) convertView
				.findViewById(R.id.ll_itemrepay_answers);
		holder.tv_itemrepay_answers1 = (TextView) convertView
				.findViewById(R.id.tv_itemrepay_answers1);
		holder.tv_itemrepay_answers2 = (TextView) convertView
				.findViewById(R.id.tv_itemrepay_answers2);
		holder.tv_itemrepay_moreanswers = (TextView) convertView
				.findViewById(R.id.tv_itemrepay_moreanswers);
		holder.gv_pic = (MyGridview) convertView.findViewById(R.id.gv_pic);

		// convertView.setTag(holder);
		// }else{
		// holder = (ViewHolder) convertView.getTag();
		// }

		final String queid = list.get(arg0).get("qid").toString();
		final String aid = list.get(arg0).get("id").toString();
		final String uid = list.get(arg0).get("Pk_user").toString();
		final String trueName = list.get(arg0).get("trueName").toString();
		final String honor_name = list.get(arg0).get("honor_name").toString();
		final String anscontent = list.get(arg0).get("ANS_CONTENT").toString();
		final int goodnum = (Integer) list.get(arg0).get("GOOD_NUM");
		final int badnum = (Integer) list.get(arg0).get("BAD_NUM");
		final String time = list.get(arg0).get("AnsTime").toString();
		final String havechild = list.get(arg0).get("is_havechild").toString();
		final int ANS_STATE = (Integer) list.get(arg0).get("ANS_STATE");
		final String p_truename = list.get(arg0).get("p_TrueName").toString();
		// 判断最佳答案

		if (state == 0) {

			holder.tv_qarepay_caina.setVisibility(View.VISIBLE);
		} else if (state == 1 && ANS_STATE == 1) {
			holder.tv_qarepay_caina.setVisibility(View.VISIBLE);
			holder.tv_qarepay_caina
					.setBackgroundResource(R.drawable.know_icon_good);
		}
		if ((!Setting.currentUser.getTruename().equals(qtruename))
				&& ANS_STATE == 0) {
			holder.tv_qarepay_caina.setVisibility(View.GONE);
		}

		switch (ANS_STATE) {
		case 0:

			if (Setting.currentUser.getTruename().equals(qtruename)) {

				holder.tv_qarepay_caina
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {

								if (!isclicked) {

									final Handler handler = new Handler() {
										@Override
										public void handleMessage(Message msg) {
											if (msg.what == 1) {
												holder.tv_qarepay_caina
														.setText("");
												holder.tv_qarepay_caina
														.setBackgroundResource(R.drawable.know_icon_good);

												tv_qadetil_context.setText(Html
														.fromHtml("<font color='#a2d46f'>[已解决]</font> "
																+ content));
												// holder.tv_qarepay_caina.setClickable(false);
												isclicked = true;
											}
										}
									};
									new Thread() {
										@Override
										public void run() {
											// TODO Auto-generated method stub
											Map<String, Object> map = new HashMap<String, Object>();
											map.put("QueID", queid);
											map.put("AnsID", aid);

											Message msg = new Message();
											String result = HttpUse.messageget(
													"QueAndAns", "adoptAns",
													map);
											JSONObject jo;
											try {
												jo = new JSONObject(result);
												if (jo.getBoolean("result")) {
													msg.what = 1;
													msg.obj = jo
															.getString("data");
												} else {
													msg.obj = jo
															.getString("message");
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
			break;
		case 1:
			holder.tv_qarepay_caina.setText("");
			holder.tv_qarepay_caina
					.setBackgroundResource(R.drawable.know_icon_good);
			break;
		default:
			break;
		}

		int num = (Integer) list.get(arg0).get("pic_num");
		int[] arr = new int[num];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = i + 1;
		}
		String a = list.get(arg0).get("id").toString();
		AgridviewAdapter newGrideView = new AgridviewAdapter(context, arr, a);
		holder.gv_pic.setAdapter(newGrideView);

		// AsyncBitmapLoader.setRoundImage(holder.iv_qarepay_touxiang,
		// Setting.apiUrl
		// + "new-pages/PersonalPhoto/" + uid + ".jpg");

		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(Setting.apiUrl + "new-pages/PersonalPhoto/"
				+ uid + ".jpg", holder.iv_qarepay_touxiang, options);
		holder.tv_qarepay_name.setText(trueName);
		// 称号
		holder.tv_qarepay_chenghao.setText("(" + honor_name + ")");
		holder.tv_qarepay_context.setText(anscontent);
		holder.tv_itemrepay_good.setText(goodnum + "");
		holder.tv_itemrepay_bad.setText(badnum + "");
		holder.tv_qarepay_time.setText(time);

		holder.ll_itemrepay_good.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				final Handler handler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						if (msg.what == 1) {
							holder.tv_itemrepay_good
									.setText((goodnum + 1) + "");
						}
					}
				};
				new Thread() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Map<String, Object> map = new HashMap<String, Object>();

						map.put("AnsID", aid);
						map.put("praiseType", 1);

						Message msg = new Message();
						String result = HttpUse.messageget("QueAndAns",
								"savePraise", map);
						JSONObject jo;
						try {
							jo = new JSONObject(result);
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
		});

		holder.ll_itemrepay_bad.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				final Handler handler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						if (msg.what == 1) {
							holder.tv_itemrepay_bad.setText((badnum + 1) + "");
						}
					}
				};
				new Thread() {
					@Override
					public void run() {
						Map<String, Object> map = new HashMap<String, Object>();

						map.put("AnsID", aid);
						map.put("praiseType", 2);

						Message msg = new Message();
						String result = HttpUse.messageget("QueAndAns",
								"savePraise", map);
						JSONObject jo;
						try {
							jo = new JSONObject(result);
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
		});

		if (havechild.equals("0")) {
			holder.ll_itemrepay_answers.setVisibility(View.GONE);
		}
		if (havechild.equals("1")) {

			final ArrayList<Map<String, Object>> listdata = new ArrayList<Map<String, Object>>();
			final Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {

					JSONArray ja;
					try {
						ja = new JSONArray(msg.obj.toString());
						for (int i = 0; i < ja.length(); i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							JSONObject jo = ja.getJSONObject(i);

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
							map.put("pic_num", jo.getString("pic_num"));

							listdata.add(map);
						}
						if (listdata.size() == 1) {
							holder.tv_itemrepay_answers2
									.setVisibility(View.GONE);
							holder.tv_itemrepay_moreanswers
									.setVisibility(View.GONE);

							holder.tv_itemrepay_answers1.setText(Html
									.fromHtml("<font color='#a2d46f'>"
											+ listdata.get(0).get("trueName")
											+ "</font>"
											+ " 回复  "
											+ listdata.get(0).get("p_TrueName")
											+ ":"
											+ listdata.get(0)
													.get("ANS_CONTENT")
											+ "<font color='#979797'>" + " "
											+ listdata.get(0).get("AnsTime")
											+ "</font>"));

						} else if (listdata.size() == 2) {
							holder.tv_itemrepay_moreanswers
									.setVisibility(View.GONE);
							holder.tv_itemrepay_answers1.setText(Html
									.fromHtml("<font color='#a2d46f'>"
											+ listdata.get(0).get("trueName")
											+ "</font>"
											+ " 回复  "
											+ listdata.get(0).get("p_TrueName")
											+ ":"
											+ listdata.get(0)
													.get("ANS_CONTENT")
											+ "<font color='#979797'>" + " "
											+ listdata.get(0).get("AnsTime")
											+ "</font>"));
							holder.tv_itemrepay_answers2.setText(Html
									.fromHtml("<font color='#a2d46f'>"
											+ listdata.get(1).get("trueName")
											+ "</font>"
											+ " 回复  "
											+ listdata.get(1).get("p_TrueName")
											+ ":"
											+ listdata.get(1)
													.get("ANS_CONTENT")
											+ "<font color='#979797'>" + " "
											+ listdata.get(1).get("AnsTime")
											+ "</font>"));
						} else if (listdata.size() > 2) {
							holder.tv_itemrepay_answers1.setText(Html
									.fromHtml("<font color='#a2d46f'>"
											+ listdata.get(0).get("trueName")
											+ "</font>"
											+ " 回复  "
											+ listdata.get(0).get("p_TrueName")
											+ ":"
											+ listdata.get(0)
													.get("ANS_CONTENT")
											+ "<font color='#979797'>" + " "
											+ listdata.get(0).get("AnsTime")
											+ "</font>"));
							holder.tv_itemrepay_answers2.setText(Html
									.fromHtml("<font color='#a2d46f'>"
											+ listdata.get(1).get("trueName")
											+ "</font>"
											+ " 回复  "
											+ listdata.get(1).get("p_TrueName")
											+ ":"
											+ listdata.get(1)
													.get("ANS_CONTENT")
											+ "<font color='#979797'>" + " "
											+ listdata.get(1).get("AnsTime")
											+ "</font>"));
							holder.tv_itemrepay_moreanswers.setText("查看更多"
									+ (listdata.size() - 2) + "条回复");
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			};

			new Thread() {
				@Override
				public void run() {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("ansID", aid);
					map.put("page", 1);
					map.put("pageSize", 99);

					Message msg = new Message();
					String result = HttpUse.messageget("QueAndAns",
							"getAnswerChild", map);
					Log.i("result", result);
					JSONObject jo;
					try {
						jo = new JSONObject(result);
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

		holder.ll_itemrepay_repay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO 回复，你在这进行了判断，如果不是自己才跳转，所以自己不能回复自己
//				if (!trueName.equals(Setting.currentUser.getTruename())) {

					Intent intent = new Intent(context,
							RepayloucengActivity.class);
					intent.putExtra("ishavechild", havechild);
					intent.putExtra("queid", queid);
					intent.putExtra("ansid", aid);
					intent.putExtra("uid", uid);
					intent.putExtra("name", trueName);
					intent.putExtra("anscontent", anscontent);
					intent.putExtra("time", time);
					intent.putExtra("goodnum", goodnum);
					intent.putExtra("badnum", badnum);
					intent.putExtra("ANS_STATE", ANS_STATE);
					intent.putExtra("qtruename", qtruename);
					intent.putExtra("qstate", state);
					context.startActivity(intent);
//				}
			}
		});

		holder.tv_itemrepay_moreanswers
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View view) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(context,
								RepayloucengActivity.class);
						intent.putExtra("ishavechild", havechild);
						intent.putExtra("queid", queid);
						intent.putExtra("ansid", aid);
						intent.putExtra("uid", uid);
						intent.putExtra("name", trueName);
						intent.putExtra("anscontent", anscontent);
						intent.putExtra("time", time);
						intent.putExtra("goodnum", goodnum);
						intent.putExtra("badnum", badnum);
						intent.putExtra("ANS_STATE", ANS_STATE);
						intent.putExtra("qtruename", qtruename);
						intent.putExtra("qstate", state);

						context.startActivity(intent);
					}
				});

		return convertView;
	}

	class ViewHolder {
		private ImageView iv_qarepay_touxiang, iv_itemrepay_good,
				iv_itemrepay_bad;
		private TextView tv_qarepay_name, tv_qarepay_caina, tv_qarepay_time,
				tv_qarepay_context, tv_itemrepay_good, tv_itemrepay_bad,
				tv_itemrepay_answers1, tv_itemrepay_answers2,
				tv_itemrepay_moreanswers, tv_qarepay_chenghao;
		private LinearLayout ll_itemrepay_repay, ll_itemrepay_answers,
				ll_itemrepay_bad, ll_itemrepay_good;
		private MyGridview gv_pic;
	}
}
