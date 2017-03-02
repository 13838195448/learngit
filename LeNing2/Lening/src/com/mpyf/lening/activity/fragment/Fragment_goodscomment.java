package com.mpyf.lening.activity.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.activity.adapter.GoodsCommentAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class Fragment_goodscomment extends Fragment {
	private String id;
	private ListView lv_list;
	private TextView tv_sc_gold_comment;
	List<Map<String, Object>> list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list, null);
		init(view);
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		showinfo();
	}

	public Fragment_goodscomment(String id) {
		this.id = id;
	}

	private void init(View view) {
		lv_list = (ListView) view.findViewById(R.id.lv_list);
		tv_sc_gold_comment = (TextView) getActivity().findViewById(
				R.id.tv_sc_gold_comment);
	}

	private void showinfo() {
		final Dialog dialog = MyDialog.MyDialogloading(getActivity());
		dialog.show();
		list = new ArrayList<Map<String, Object>>();
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				dialog.dismiss();
				if (msg.what == 1) {
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						for (int i = 0; i < ja.length(); i++) {
							JSONObject jo = ja.getJSONObject(i);
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("id", jo.getString("pk_Com"));
							map.put("name", jo.getString("trueName"));
							map.put("userid", jo.getString("pk_users"));
							map.put("time", jo.getString("com_Date"));
							map.put("srcoe", jo.getInt("com_Level"));
							map.put("context", jo.getString("com_Con"));
							map.put("PK_Res", jo.getString("pk_goods"));
							map.put("ImagePath",
									Setting.apiUrl + "new-pages/PersonalPhoto/"
											+ jo.getString("pk_users") + ".jpg");
							list.add(map);
						}

						GoodsCommentAdapter adapter = new GoodsCommentAdapter(
								getActivity(), list);
						lv_list.setAdapter(adapter);
						if (tv_sc_gold_comment != null) {
							tv_sc_gold_comment.setText("评论（" + list.size()
									+ "）");
						}
						// else {
						// lv_list.setOnItemClickListener(new
						// OnItemClickListener() {
						//
						// @Override
						// public void onItemClick(AdapterView<?> arg0,
						// View arg1, int arg2, long arg3) {
						// if (list.get(arg2)
						// .get("userid")
						// .toString()
						// .equals(Setting.currentUser
						// .getPk_user() + "")) {
						//
						// Intent intent = new Intent(
						// getActivity(),
						// AddcommentActivity.class);
						// intent.putExtra("id", list.get(arg2)
						// .get("id").toString());
						// intent.putExtra("srcoe", (Integer) list
						// .get(arg2).get("srcoe"));
						// intent.putExtra("resId", list.get(arg2)
						// .get("PK_Res").toString());
						// intent.putExtra("context",
						// list.get(arg2).get("context")
						// .toString());
						// startActivity(intent);
						// } else {
						// return;
						// }
						// }
						//
						// });
						// }

					} catch (JSONException e) {
						Diaoxian.showerror(getActivity(), e.getMessage());
					}
				} else {
					// Diaoxian.showerror(getActivity(), msg.obj.toString());
				}
			}
		};

		new Thread() {

			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("pk_goods", id);
				map.put("page", 1);
				map.put("pageSize", 20);

				Message msg = new Message();

				String result = HttpUse.messageget("QueAndAns", "listGoodsCom",
						map);

//				System.out.println("商品评价" + result);
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = jo.getString("data");
					} else {
						msg.obj = jo.getString("message");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

				handler.sendMessage(msg);

			};

		}.start();
	}

}
