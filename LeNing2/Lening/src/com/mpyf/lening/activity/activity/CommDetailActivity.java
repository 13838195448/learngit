package com.mpyf.lening.activity.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import u.aly.co;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.activity.adapter.CommentAdapter;
import com.mpyf.lening.interfaces.bean.Parame.ShareComment;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class CommDetailActivity extends Activity implements OnClickListener{

	private LinearLayout ll_back;
	private TextView tv_resource_name;
	private TextView tv_num;
	private TextView tv_time;
	private TextView tv_size;
	private TextView tv_cost;
	private TextView tv_name;
	private TextView tv_remark;
	private ImageView iv_course_start1, iv_course_start2, iv_course_start3,
			iv_course_start4, iv_course_start5;
	private Button bt_course_ok;
	private ListView lv_note;
	private EditText et_course_title;
	private ArrayList<Map<String, Object>> list;
	private int score = 0;
	private String id="";
	private ScrollView sv_note;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_comm_detail);
		init();
		initData();
		addlistener();
	}

	private void init() {

		ll_back = (LinearLayout) findViewById(R.id.ll_back);
		sv_note = (ScrollView)findViewById(R.id.sv_note);

		
		tv_resource_name = (TextView) findViewById(R.id.tv_resource_name);
		tv_num = (TextView) findViewById(R.id.tv_num);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_size = (TextView) findViewById(R.id.tv_size);
		tv_cost = (TextView) findViewById(R.id.tv_cost);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_remark = (TextView) findViewById(R.id.tv_remark);

		lv_note = (ListView) findViewById(R.id.lv_note);
		iv_course_start1 = (ImageView) findViewById(R.id.iv_course_start1);
		iv_course_start2 = (ImageView) findViewById(R.id.iv_course_start2);
		iv_course_start3 = (ImageView) findViewById(R.id.iv_course_start3);
		iv_course_start4 = (ImageView) findViewById(R.id.iv_course_start4);
		iv_course_start5 = (ImageView) findViewById(R.id.iv_course_start5);
		bt_course_ok = (Button) findViewById(R.id.bt_course_ok);
		et_course_title = (EditText)findViewById(R.id.et_course_title);
		
		
	}

	private void initData() {
		

		tv_resource_name.setText(getIntent().getStringExtra("shareName"));
		tv_num.setText(getIntent().getIntExtra("buyNum", 0)+"次");
		tv_time.setText(getIntent().getStringExtra("onlineTime"));
		tv_size.setText(getIntent().getStringExtra("shareSize"));
		// String state = getIntent().getStringExtra("buyWay");
		int state = getIntent().getIntExtra("buyWay", 1);
		if (state == 1) {
			tv_cost.setText(getIntent().getIntExtra("amount", 0) + "乐币");
		} else if (state == 2) {
			tv_cost.setText(getIntent().getIntExtra("amount", 0) + "金币");
		}

		tv_name.setText(getIntent().getStringExtra("trueName"));
		tv_remark.setText(getIntent().getStringExtra("remark"));


		showInfo();
		
	}

	private void showInfo() {
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
							map.put("ImagePath", jo.getString("pk_users"));
							map.put("name", jo.getString("userName"));
							map.put("time", jo.getString("com_Date"));
							map.put("context", jo.getString("com_Con"));
							map.put("srcoe", jo.getInt("com_Level"));
							list.add(map);
						}

						CommentAdapter adapter = new CommentAdapter(
								CommDetailActivity.this, list);
						lv_note.setAdapter(adapter);
					} catch (JSONException e) {
						Diaoxian.showerror(CommDetailActivity.this, e.getMessage());
					}
				} else {
					Diaoxian.showerror(CommDetailActivity.this, msg.obj.toString());
				}
			}
		};

		new Thread(){
			
			public void run() {
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("PK_Share",getIntent().getStringExtra("PK_Share"));
				map.put("page", 1);
				map.put("pageSize", 999);
				
				String result = HttpUse.messageget("PersonalCenter", "getShareResComments", map);
				Message msg = Message.obtain();
				try {
					JSONObject jo = new JSONObject(result);
					if(jo.getBoolean("result")){
						msg.what = 1;
						msg.obj = jo.getString("data");
					}else{
						msg.obj = jo.getString("message");
					}
					
				} catch (JSONException e) {
					msg.obj = e.getMessage();
				}
				
				handler.sendMessage(msg);
			};
		}.start();
	}
	
	private void addlistener(){
		
		ListenerServer.setfinish(CommDetailActivity.this, ll_back);
		
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
					id = list.get(arg2).get("PK_Com").toString();
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

	protected void changestar(Integer index) {


		final ImageView[] stars = { iv_course_start1, iv_course_start2,
				iv_course_start3, iv_course_start4, iv_course_start5 };
		for (int i = 0; i < stars.length; i++) {
			if (i < index) {
				stars[i].setImageResource(R.drawable.course_icon_star02);
			} else {
				stars[i].setImageResource(R.drawable.course_btn_star_d);
			}
		}
		score = index;
	
	}
	
	
	/**
	 * 发送评论
	 */
	private void postComment(){
		
		final Dialog dialog = MyDialog.MyDialogloading(CommDetailActivity.this);
		dialog.show();

		final Handler handle = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				dialog.dismiss();

				if (msg.what == 1) {
					Diaoxian.showerror(CommDetailActivity.this, "评论成功");
					et_course_title.setText("");
					final ImageView[] stars = { iv_course_start1,
							iv_course_start2, iv_course_start3,
							iv_course_start4, iv_course_start5 }; 
					for (int i = 0; i < stars.length; i++) {
						stars[i].setImageResource(R.drawable.course_btn_star_d);
					}
					score = 0;
					showInfo();
				} else {
					Diaoxian.showerror(CommDetailActivity.this, msg.obj.toString());
				}

			}
		};
		
		new Thread(){
			
			public void run() {
				Message msg = Message.obtain();
				Date date = new Date();
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");
				String datestr = format.format(date);
				ShareComment comment = new ShareComment();
				comment.setCom_Date(datestr); 
				comment.setPK_Com(id); 
				comment.setPK_Share( getIntent().getStringExtra("PK_Share"));
				comment.setCom_Con(et_course_title.getText().toString());
				comment.setCom_Level(score); 
				comment.setPk_users(Setting.currentUser.getPk_user()); 
				comment.setTrueName(Setting.currentUser.getTruename()); 
				comment.setUserName(Setting.currentUser.getUsername()); 
				
				String result = HttpUse.messagepost("PersonalCenter", "saveShareResComments", comment);
				
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
					}
					msg.obj = jo.getString("message");
				} catch (JSONException e) {
					msg.obj = e.getMessage();
				}
				
				handle.sendMessage(msg);
			};
			
		}.start();
	}

	@Override
	public void onClick(View arg0) {

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
			if(score!=0 && !TextUtils.isEmpty(et_course_title.getText())){
				postComment();
			}else if(score==0 && TextUtils.isEmpty(et_course_title.getText())){
				Diaoxian.showerror(CommDetailActivity.this, "评价和星级不能为空");
			}else if(score==0&&!TextUtils.isEmpty(et_course_title.getText())){
				Diaoxian.showerror(CommDetailActivity.this, "星级不能为空");
			}else if(score!=0 && TextUtils.isEmpty(et_course_title.getText())){
				Diaoxian.showerror(CommDetailActivity.this, "评价不能为空");
			}
		break;
		default:
			break;
		}
		
	}
}
