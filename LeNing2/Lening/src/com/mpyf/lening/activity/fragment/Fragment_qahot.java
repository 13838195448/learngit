package com.mpyf.lening.activity.fragment;
/**
 * 知道 -----问答------推荐
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.activity.activity.QadetilActivity;
import com.mpyf.lening.activity.adapter.Wenda1;
import com.mpyf.lening.interfaces.http.HttpUse;

public class Fragment_qahot extends Fragment {

	private ListView lv_list;
	private List<Map<String, Object>> list;
	private ImageView iv_qa_seek;
	private EditText et_qa_search;
	private RelativeLayout rl_qa_search;
	private TextView tv_qa_title;

	private Wenda1 adapter;
	private static int page = 1;
	private boolean is_divpage;
	private ArrayList<Map<String, Object>> data;
	private Dialog dialog;
	protected boolean isVisible;
	private View view;

	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_list, null);
		page = 1;
		init(view);

		search();
		showinfo();
		return view;
	};

	public void onPause() {
		super.onPause();
	}

	// @Override
	// public void onResume() {
	// super.onResume();
	// page=1;
	// showinfo();
	// }

	private void init(View view) {
		iv_qa_seek = (ImageView) getActivity().findViewById(R.id.iv_qa_seek);
		rl_qa_search = (RelativeLayout) getActivity().findViewById(
				R.id.rl_qa_search);
		tv_qa_title = (TextView) getActivity().findViewById(R.id.tv_qa_title);
		et_qa_search = (EditText) getActivity().findViewById(R.id.et_qa_search);
		lv_list = (ListView) view.findViewById(R.id.lv_list);
	}

	// 这个是搜索的

	private void search() {
		iv_qa_seek.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (rl_qa_search.getVisibility() == View.INVISIBLE) {
					rl_qa_search.setVisibility(View.VISIBLE);
					tv_qa_title.setVisibility(View.GONE);
				} else if (rl_qa_search.getVisibility() == View.VISIBLE) {
					final Handler handler = new Handler() {
						@Override
						public void handleMessage(Message msg) {
							if (msg.what == 1) {
								try {
									list = new ArrayList<Map<String, Object>>();
									JSONArray ja = new JSONArray(msg.obj
											.toString());
									for (int i = 0; i < ja.length(); i++) {
										Map<String, Object> map = new HashMap<String, Object>();
										JSONObject jo = ja.getJSONObject(i);
										map.put("id", jo.getString("PK_Que"));
										map.put("userid", jo.getInt("pk_user"));
										map.put("UserName",
												jo.getString("userName"));
										map.put("Nickname",
												jo.getString("nickname"));
										map.put("QUE_CONTENT",
												jo.getString("QUE_CONTENT"));
										map.put("REWARD_WAY",
												jo.getInt("REWARD_WAY"));
										map.put("REWARD_Num",
												jo.getString("REWARD_Num"));
										map.put("Ans_Num",
												jo.getString("ans_Num"));
										map.put("QUE_STATE",
												jo.getInt("QUE_STATE"));
										map.put("PIC_NUM", jo.getInt("pic_num"));
										map.put("honor_name",
												jo.getString("honor_name"));
										map.put("honor_pic",
												jo.getString("honor_pic"));
										map.put("isCollection",
												jo.getInt("isCollection"));
										list.add(map);
									}
									Wenda1 adapter = new Wenda1(getActivity(),
											list);
									lv_list.setAdapter(adapter);
									lv_list.setOnItemClickListener(new OnItemClickListener() {

										@Override
										public void onItemClick(
												AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
											Intent intent = new Intent(
													getActivity(),
													QadetilActivity.class);
											intent.putExtra("id", list
													.get(arg2).get("id")
													.toString());
											startActivity(intent);
										}
									});
								} catch (JSONException e) {
									Diaoxian.showerror(getActivity(),
											e.getMessage());
								}
							} else {
								Diaoxian.showerror(getActivity(),
										msg.obj.toString());
							}
						}
					};

					new Thread() {
						@Override
						public void run() {
							Message msg = new Message();
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("search", et_qa_search.getText().toString());
							map.put("page", 1);
							map.put("pageSize", 99);

							String result = HttpUse.messageget("QueAndAns",
									"searchQue", map);
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

	// 这才是给listview请求数据展示的
	private void showinfo() {

		data = new ArrayList<Map<String, Object>>();
		adapter = new Wenda1(getActivity(), data);

		getData();

		lv_list.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

				if (is_divpage && scrollState == 0) {
					is_divpage = true;

					getData();
				}
			}

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				if ((arg1 + arg2) == arg3) {
					is_divpage = true;
				} else {
					is_divpage = false;
				}
			}
		});

	}

	// 这个才是请求数据填充
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
							map.put("id", jo.getString("PK_Que"));
							map.put("userid", jo.getInt("pk_user"));
							map.put("UserName", jo.getString("userName"));
							map.put("Nickname", jo.getString("nickname"));
							map.put("QUE_CONTENT", jo.getString("QUE_CONTENT"));
							map.put("REWARD_WAY", jo.getInt("REWARD_WAY"));
							map.put("REWARD_Num", jo.getString("REWARD_Num"));
							map.put("Ans_Num", jo.getString("ans_Num"));
							map.put("QUE_STATE", jo.getInt("QUE_STATE"));
							map.put("PIC_NUM", jo.getInt("pic_num"));
							map.put("trueName", jo.getString("trueName"));
							map.put("honor_name", jo.getString("honor_name"));
							map.put("honor_pic", jo.getString("honor_pic"));
							map.put("isCollection", jo.getInt("isCollection"));
							list.add(map);
						}
						data.addAll(list);
						if (page == 1) {

							lv_list.setAdapter(adapter);
						}
						adapter.notifyDataSetChanged();
						page++;

						lv_list.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								Intent intent = new Intent(getActivity(),
										QadetilActivity.class);
								intent.putExtra("id", data.get(arg2).get("id")
										.toString());
								startActivity(intent);
							}
						});
					} catch (JSONException e) {
						Diaoxian.showerror(getActivity(), e.getMessage());
					}
				} else {
					Diaoxian.showerror(getActivity(), msg.obj.toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("page", page);
				map.put("pageSize", 5);

				Message msg = new Message();

				String result = HttpUse.messageget("QueAndAns", "hotQue", map);
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
	}
}
