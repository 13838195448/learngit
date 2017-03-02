package com.mpyf.lening.activity.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.activity.adapter.CommentAdapter;
import com.mpyf.lening.interfaces.bean.Parame.Comments;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class Fragment_docomment extends Fragment implements OnClickListener {

	private RelativeLayout rl_course_rb, rl_course_comment;
	private ListView lv_note;
	private List<Map<String, Object>> list;
	private String courseid, resid;
	private ImageView iv_course_start1, iv_course_start2, iv_course_start3,
			iv_course_start4, iv_course_start5;

	private Button bt_course_ok;
	private EditText et_course_title, et_course_comment;

	private ScrollView sv_note;
	private String id="";
	
	private int score = 0;

	public Fragment_docomment(String courseid, String resid) {
		// TODO Auto-generated constructor stub
		this.courseid = courseid;
		this.resid = resid;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_note, null);
		init(view);

		addlistener();
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		showinfo();
	}

	private void init(View view) {
		rl_course_rb = (RelativeLayout) view.findViewById(R.id.rl_course_rb);
		rl_course_comment = (RelativeLayout) view
				.findViewById(R.id.rl_course_comment);
		lv_note = (ListView) view.findViewById(R.id.lv_note);
		iv_course_start1 = (ImageView) view.findViewById(R.id.iv_course_start1);
		iv_course_start2 = (ImageView) view.findViewById(R.id.iv_course_start2);
		iv_course_start3 = (ImageView) view.findViewById(R.id.iv_course_start3);
		iv_course_start4 = (ImageView) view.findViewById(R.id.iv_course_start4);
		iv_course_start5 = (ImageView) view.findViewById(R.id.iv_course_start5);

		et_course_title = (EditText) view.findViewById(R.id.et_course_title);
		et_course_comment = (EditText) view
				.findViewById(R.id.et_course_comment);
		
		sv_note=(ScrollView) view.findViewById(R.id.sv_note);
		
		et_course_comment.setVisibility(View.GONE);
		rl_course_comment.setVisibility(View.GONE);
		bt_course_ok = (Button) view.findViewById(R.id.bt_course_ok);
	}

	private void showinfo() {

		list = new ArrayList<Map<String, Object>>();

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						for (int i = 0; i < ja.length(); i++) {
							JSONObject jo = ja.getJSONObject(i);
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("PK_Com", jo.getString("PK_Com"));
							map.put("ImagePath", jo.getString("pk_user"));
							map.put("name", jo.getString("nickname"));
							map.put("time", jo.getString("commTime"));
							map.put("context", jo.getString("COMMENT_CONTENT"));
							map.put("srcoe", jo.getInt("COMMENT_LEVEL"));
							list.add(0,map);
						}

						CommentAdapter adapter = new CommentAdapter(
								getActivity(), list);
						lv_note.setAdapter(adapter);
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
				map.put("courseId", courseid);
				map.put("resId", resid);
				map.put("page", 1);
				map.put("pageSize", 9999);

				Message msg = new Message();

				String result = HttpUse.messageget("CourseStudy",
						"getCommentsByCourseIdAndresId", map);

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

	private void addlistener() {

		et_course_title.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				if (et_course_title.getText().toString().length() > 0) {
					bt_course_ok
							.setBackgroundResource(R.drawable.course_btn_send);
					bt_course_ok.setClickable(true);
				} else {
					bt_course_ok
							.setBackgroundResource(R.drawable.course_btn_send_d);
					bt_course_ok.setClickable(false);
				}
			}
		});
		
		lv_note.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(list.get(arg2).get("ImagePath").toString().equals(Setting.currentUser.getPk_user()+"")){
					et_course_title.setText(list.get(arg2).get("context").toString());
					changestar((Integer)list.get(arg2).get("srcoe"));
					id=list.get(arg2).get("PK_Com").toString();
					sv_note.smoothScrollTo(0, 0);
				}else{
					return;
				}
				
			}
		});

		iv_course_start1.setOnClickListener(this);
		iv_course_start2.setOnClickListener(this);
		iv_course_start3.setOnClickListener(this);
		iv_course_start4.setOnClickListener(this);
		iv_course_start5.setOnClickListener(this);
		bt_course_ok.setOnClickListener(this);

	}

	private void changestar(int index) {
		final ImageView[] stars = { iv_course_start1, iv_course_start2,
				iv_course_start3, iv_course_start4, iv_course_start5 };
		for (int i = 0; i < stars.length; i++) {
			if (i < index) {
				stars[i].setImageResource(R.drawable.course_icon_star02);
			} else {
				stars[i].setImageResource(R.drawable.course_icon_star01);
			}
		}
		score = index;
	}

	private void postcomment() {

		final Dialog dialog = MyDialog.MyDialogloading(getActivity());
		dialog.show();

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				dialog.dismiss();

				if (msg.what == 1) {
					Diaoxian.showerror(getActivity(), "ÆÀÂÛ³É¹¦");
					et_course_title.setText("");
					final ImageView[] stars = { iv_course_start1,
							iv_course_start2, iv_course_start3,
							iv_course_start4, iv_course_start5 };
					for (int i = 0; i < stars.length; i++) {
						stars[i].setImageResource(R.drawable.course_icon_star01);
					}
					score = 0;
					showinfo();
				} else {
					Diaoxian.showerror(getActivity(), msg.obj.toString());
				}

			}
		};

		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = new Message();

				Date date = new Date();
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");
				String datestr = format.format(date);
				Comments comments = new Comments();
				comments.setCOMMENT_CONTENT(et_course_title.getText()
						.toString());
				comments.setCOMMENT_LEVEL(score);
				comments.setCommTime(datestr);
				comments.setNickname(Setting.currentUser.getNickname());
				comments.setPK_Com(id);
				comments.setPK_Course(courseid);
				comments.setPK_Res(resid);
				comments.setPk_user(Setting.currentUser.getPk_user());
				comments.setUserName(Setting.currentUser.getUsername());

				String result = HttpUse.messagepost("CourseStudy",
						"saveResComments", comments);

				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
					}
					msg.obj = jo.getString("message");

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					msg.obj = e.getMessage();
				}
				handler.sendMessage(msg);
			}
		}.start();

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.iv_course_start1:
			changestar(1);
			break;
		case R.id.iv_course_start2:
			changestar(2);
			break;
		case R.id.iv_course_start3:
			changestar(3);
			break;
		case R.id.iv_course_start4:
			changestar(4);
			break;
		case R.id.iv_course_start5:
			changestar(5);
			break;
		case R.id.bt_course_ok:
			postcomment();
			break;
		default:
			break;
		}
	}

}
