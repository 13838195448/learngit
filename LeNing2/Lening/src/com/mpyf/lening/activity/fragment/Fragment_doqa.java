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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.Quanjubianliang;
import com.mpyf.lening.activity.adapter.DayiAdapter;
import com.mpyf.lening.interfaces.bean.Parame.ResFaq;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class Fragment_doqa extends Fragment {

	private RelativeLayout rl_course_rb, rl_course_comment;
	private List<Map<String, Object>> list;
	private EditText et_course_title;
	private Button bt_course_ok;
	private ListView lv_note;
	private String courseid="",resid="",fqid="";

	private ScrollView sv_note;
	
	public Fragment_doqa(String courseid, String resid) {
		// TODO Auto-generated constructor stub
		this.courseid=courseid;
		this.resid=resid;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_note, null);
		init(view);
		
		addlistener();
		return view;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		showinfo();
	}
	
	private void init(View view) {
		rl_course_rb = (RelativeLayout) view.findViewById(R.id.rl_course_rb);
		rl_course_comment = (RelativeLayout) view
				.findViewById(R.id.rl_course_comment);
		et_course_title = (EditText) view.findViewById(R.id.et_course_title);
		bt_course_ok = (Button) view.findViewById(R.id.bt_course_ok);
		
		lv_note=(ListView) view.findViewById(R.id.lv_note);
		sv_note=(ScrollView) view.findViewById(R.id.sv_note);
		rl_course_rb.setVisibility(View.GONE);
		rl_course_comment.setVisibility(View.GONE);
	}

	private void showinfo() {
		list = new ArrayList<Map<String, Object>>();
		
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				JSONArray ja;
				try {
					ja = new JSONArray(msg.obj.toString());
					for(int i=0;i<ja.length();i++){
						JSONObject jo=ja.getJSONObject(i);
						
							Map<String,Object> map=new HashMap<String, Object>();
							
							map.put("beAnswered", jo.getBoolean("beAnswered"));
							
							map.put("id", jo.getString("PK_Faq"));
							map.put("context", jo.getString("FAQ_CONTENT"));
							map.put("from", "");
							map.put("answer", "答案： "+jo.getString("ans_CONTENT"));
							list.add(map);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Diaoxian.showerror(getActivity(), e.getMessage());;
				}
				DayiAdapter adapter=new DayiAdapter(getActivity(), list);
				lv_note.setAdapter(adapter);
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("courseId", Quanjubianliang.courseid);
				map.put("resId", resid);
				map.put("page", 1);
				map.put("pageSize", 9999);
				Message msg=new Message();
				String result=HttpUse.messageget("CourseStudy", "myCourseResResFaq", map);
				try {
					JSONObject jo=new JSONObject(result);
					if(jo.getBoolean("result")){
						msg.what=1;
						msg.obj=jo.getString("data");
					}else{
						msg.obj=jo.getString("message");
					}
				} catch (JSONException e) {
					msg.obj=e.getMessage();
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
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
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

		bt_course_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				postqus();
			}
		});
		
		lv_note.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				et_course_title.setText(list.get(arg2).get("context").toString());
				fqid=list.get(arg2).get("id").toString();
				sv_note.smoothScrollTo(0, 0);
			}
		});
	}

	
	private void postqus() {
		final Dialog dialog = MyDialog.MyDialogloading(getActivity());
		dialog.show();

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				
				if (msg.what == 1) {
					Diaoxian.showerror(getActivity(),fqid.equals("")?"已提问":"已修改");
					et_course_title.setText("");
					showinfo();
				}else{
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
						"yyyy-MM-dd");
				String datestr = format.format(date);

				ResFaq resfaq=new ResFaq();
				
				resfaq.setAns_CONTENT("");
				resfaq.setAns_nickname("");
				resfaq.setAns_Time("");
				resfaq.setAns_username("");
				resfaq.setBeAnswered(false);
				resfaq.setFAQ_CONTENT(et_course_title.getText().toString());
				resfaq.setNickname(Setting.currentUser.getNickname());
				resfaq.setFAQ_Time(datestr);
				resfaq.setPK_Course(courseid);
				resfaq.setPK_Faq(fqid);
				resfaq.setPK_Res(resid);
				resfaq.setPk_user(Setting.currentUser.getPk_user());
				resfaq.setUserName(Setting.currentUser.getUsername());
				
				String result = HttpUse.messagepost("CourseStudy","saveResFaq", resfaq);

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
}
