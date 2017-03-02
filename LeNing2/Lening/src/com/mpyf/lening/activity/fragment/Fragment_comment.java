package com.mpyf.lening.activity.fragment;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.activity.activity.AddcommentActivity;
import com.mpyf.lening.activity.adapter.MyCommentAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class Fragment_comment extends Fragment {

	private ListView lv_list;
	private String id;
	private TextView tv_wmcourse_comment;
	List<Map<String, Object>> list;

	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list, null);
		init(view);

		return view;
	};

	@Override
	public void onStart() {

		super.onStart();
		showinfo();
	}

	public Fragment_comment(String id) {
		this.id = id;
	}
	public Fragment_comment() {
	}

	private void init(View view) {
		lv_list = (ListView) view.findViewById(R.id.lv_list);
		tv_wmcourse_comment = (TextView) getActivity().findViewById(
				R.id.tv_wmcourse_comment);
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
							map.put("id", jo.getString("PK_Com"));
							map.put("name", jo.getString("nickname"));
							map.put("time", jo.getString("commTime"));
							map.put("context", jo.getString("COMMENT_CONTENT"));
							map.put("from", "来自课件： " + jo.getString("resName"));
							map.put("srcoe", jo.getInt("COMMENT_LEVEL"));
							map.put("userid", jo.getString("pk_user"));
							map.put("PK_Res", jo.getString("PK_Res"));
							map.put("ImagePath",
									Setting.apiUrl + "new-pages/PersonalPhoto/"
											+ jo.getString("pk_user") + ".jpg");
							list.add(map);
						}

						MyCommentAdapter adapter = new MyCommentAdapter(
								getActivity(), list);
						lv_list.setAdapter(adapter);
						if (tv_wmcourse_comment != null) {
							tv_wmcourse_comment.setText("评论（" + list.size()
									+ "）");
						}
//						else {
//							lv_list.setOnItemClickListener(new OnItemClickListener() {
//
//								@Override
//								public void onItemClick(AdapterView<?> arg0,
//										View arg1, int arg2, long arg3) {
//									if (list.get(arg2)
//											.get("userid")
//											.toString()
//											.equals(Setting.currentUser
//													.getPk_user() + "")) {
//
//										Intent intent = new Intent(
//												getActivity(),
//												AddcommentActivity.class);
//										intent.putExtra("id", list.get(arg2)
//												.get("id").toString());
//										intent.putExtra("srcoe", (Integer) list
//												.get(arg2).get("srcoe"));
//										intent.putExtra("resId", list.get(arg2)
//												.get("PK_Res").toString());
//										intent.putExtra("context",
//												list.get(arg2).get("context")
//														.toString());
//										startActivity(intent);
//									} else {
//										return;
//									}
//								}
//
//							});
//						}

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
				map.put("courseId", id);
				map.put("page", 1);
				map.put("pageSize", 9999);

				Message msg = new Message();

				String result = HttpUse.messageget("CourseStudy",
						"getCourseCommentsByCourseId", map);
//				System.out.println("课程评价=="+result);

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
