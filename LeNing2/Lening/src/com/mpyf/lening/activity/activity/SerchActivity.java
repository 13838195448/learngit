package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
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
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.Readsaved;
import com.mpyf.lening.Jutil.Writesaved;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.activity.adapter.FenleidetilAdapter;
import com.mpyf.lening.activity.adapter.PeixunAdapter;
import com.mpyf.lening.activity.adapter.SearchAdapter;
import com.mpyf.lening.activity.adapter.SearchedAdapter;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class SerchActivity extends Activity {

	private LinearLayout ll_search_back, ll_search_xuanxiang;
	private RelativeLayout rl_search_xuanxiang;
	private ImageView iv_search_delete;
	private ListView lv_search;
	private EditText et_search_neirong;
	private TextView tv_search_title, tv_search_histroy, tv_search_delhistroy,
			tv_search_kecheng, tv_search_peixun, tv_search_qa;
	private List<Map<String, Object>> list;
	private int type = 1;
	private static Timer timer;
	private String result = "";
	private String search;
	private String[] searchs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new Xiaoxibeijing().changetop(SerchActivity.this, R.color.main);
		setContentView(R.layout.activity_search);
		init();
		addlistener();
	}

	private void init() {
		ll_search_back = (LinearLayout) findViewById(R.id.ll_search_back);
		ll_search_xuanxiang = (LinearLayout) findViewById(R.id.ll_search_xuanxiang);
		rl_search_xuanxiang = (RelativeLayout) findViewById(R.id.rl_search_xuanxiang);
		iv_search_delete = (ImageView) findViewById(R.id.iv_search_delete);
		lv_search = (ListView) findViewById(R.id.lv_search);
		et_search_neirong = (EditText) findViewById(R.id.et_search_neirong);
		tv_search_title = (TextView) findViewById(R.id.tv_search_title);
		tv_search_histroy = (TextView) findViewById(R.id.tv_search_histroy);
		tv_search_delhistroy = (TextView) findViewById(R.id.tv_search_delhistroy);
		tv_search_kecheng = (TextView) findViewById(R.id.tv_search_kecheng);
		tv_search_peixun = (TextView) findViewById(R.id.tv_search_peixun);
		tv_search_qa = (TextView) findViewById(R.id.tv_search_qa);

		rl_search_xuanxiang.setVisibility(View.GONE);

		if (hashistroy()) {
			return;
		} else {
			tv_search_histroy.setVisibility(View.GONE);
			tv_search_delhistroy.setVisibility(View.GONE);
		}

	}

	private boolean hashistroy() {
		search = Readsaved.read(this, "searched");
		if (search.equals("")) {
			return false;
		} else {
			search = search.replace("[", "");
			search = search.replace("]", "");
			search = search.replace("{", "");
			search = search.replace("}", "");

			searchs = search.split(",");

			for (int i = 0; i < searchs.length - 1; i++) {
				if (searchs[i].equals(searchs[i + 1])) {

					search = search.replace("," + searchs[i] + ",", ",");
				}
			}
			searchs = search.split(",");
			final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < searchs.length; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", searchs[i]
						.substring(0, searchs[i].indexOf("=")).trim());
				map.put("context", (searchs[i].substring(searchs[i]
						.indexOf("=") + 1)).trim());
				list.add(map);
			}

			SearchAdapter adapter = new SearchAdapter(this, list);
			lv_search.setAdapter(adapter);

			tv_search_histroy.setVisibility(View.VISIBLE);
			tv_search_delhistroy.setVisibility(View.VISIBLE);

			lv_search.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					et_search_neirong.setText(list.get(arg2).get("context")
							.toString());
					type = Integer.valueOf(list.get(arg2).get("type")
							.toString());

					switch (type) {
					case 1:
						tv_search_title.setText("¿Î³Ì");
						break;
					case 2:
						tv_search_title.setText("ÅàÑµ");
						break;
					case 3:
						tv_search_title.setText("ÎÊ´ð");
						break;
					default:
						break;
					}

					savehistroy();
				}
			});
			return true;
		}
	}

	private void addlistener() {
		ListenerServer.setfinish(SerchActivity.this, ll_search_back);

		ll_search_xuanxiang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				rl_search_xuanxiang.setVisibility(View.VISIBLE);
				rl_search_xuanxiang.getBackground().setAlpha(100);

			}
		});

		iv_search_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				et_search_neirong.setText("");
				hashistroy();
			}
		});

		tv_search_kecheng.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				type = 1;
				rl_search_xuanxiang.setVisibility(View.GONE);
				tv_search_title.setText("¿Î³Ì");
			}
		});

		tv_search_delhistroy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Writesaved.write(SerchActivity.this, "searched", "");
				tv_search_histroy.setVisibility(View.GONE);
				tv_search_delhistroy.setVisibility(View.GONE);
				lv_search.setAdapter(null);
			}
		});

		tv_search_peixun.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				type = 2;
				rl_search_xuanxiang.setVisibility(View.GONE);
				tv_search_title.setText("ÅàÑµ");
			}
		});

		tv_search_qa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				type = 3;
				rl_search_xuanxiang.setVisibility(View.GONE);
				tv_search_title.setText("ÎÊ´ð");
			}
		});

		rl_search_xuanxiang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				rl_search_xuanxiang.setVisibility(View.GONE);
			}
		});

		et_search_neirong.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				// TODO Auto-generated method stub
				if (arg1 == KeyEvent.KEYCODE_ENTER) {
					if (et_search_neirong.getText().toString().trim()
							.equals("")) {
						hashistroy();
					} else {
						dosearch();
						savehistroy();
					}

				}
				return false;
			}
		});

		et_search_neirong.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				dosearch();
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				if (et_search_neirong.getText().toString().trim().equals("")) {
					hashistroy();
				}
			}
		});
	}

	private void savehistroy() {
		String saved = "" + type + "=" + et_search_neirong.getText().toString();

		if (("," + search).indexOf("," + saved) != -1) {
			search = search.replace("," + saved, "");
		}

		search = saved + "," + search;

		Writesaved.write(SerchActivity.this, "searched", search);
	}

	private void savehistroy(int type, String neirong) {
		String saved = "" + type + "=" + neirong;
		if (("," + search).indexOf("," + saved) != -1) {
			search = search.replace("," + saved, "");
		}
		search = saved + "," + search;

		Writesaved.write(SerchActivity.this, "searched", search);
	}

	private void dosearch() {
		// final Dialog dialog=MyDialog.MyDialogloading(this);
		// dialog.show();
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// dialog.dismiss();
				tv_search_histroy.setVisibility(View.GONE);
				tv_search_delhistroy.setVisibility(View.GONE);
				if (msg.what == 1) {
					showresult(msg.obj.toString());

				} else {
					Diaoxian.showerror(SerchActivity.this, msg.obj.toString());
				}

			}
		};
		new Thread() {
			@Override
			public void run() {

				Message msg = new Message();

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("query", et_search_neirong.getText().toString());
				map.put("queryType", type);
				map.put("page", 1);
				map.put("pageSize", 99);

				result = HttpUse.messageget("CourseStudy", "userSearch", map);

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

	private void showresult(String result) {
		list = new ArrayList<Map<String, Object>>();
		tv_search_histroy.setVisibility(View.GONE);
		switch (type) {
		case 1:
			getkecheng(result);
			break;
		case 2:
			getpeixun(result);
			break;
		case 3:

			break;
		default:
			break;
		}
	}

	private void getkecheng(String result) {
		JSONArray ja;
		try {
			ja = new JSONArray(result);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", jo.getString("PK_Course"));
				map.put("title", jo.getString("CourseName"));
				list.add(map);
			}
			SearchedAdapter adapter = new SearchedAdapter(SerchActivity.this,
					et_search_neirong.getText().toString(), list);
			lv_search.setAdapter(adapter);

			lv_search.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					savehistroy(1, list.get(arg2).get("title").toString());
					Intent intent = new Intent(SerchActivity.this,
							NotbuycourseActivity.class);
					intent.putExtra("id", list.get(arg2).get("id").toString());
					startActivity(intent);
				}
			});

			if (et_search_neirong.getText().toString().trim().equals("")) {
				hashistroy();
			}

		} catch (JSONException e) {
			Diaoxian.showerror(SerchActivity.this, e.getMessage());
		}
	}

	private void getpeixun(String result) {
		try {
			JSONArray ja = new JSONArray(result);
			for (int i = 0; i < ja.length(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				JSONObject jo = ja.getJSONObject(i);
				map.put("id", jo.getString("PK_MainAct"));
				map.put("title", jo.getString("act_Name"));
				list.add(map);
			}
			SearchedAdapter adapter = new SearchedAdapter(SerchActivity.this,
					et_search_neirong.getText().toString(), list);
			lv_search.setAdapter(adapter);

			lv_search.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					savehistroy(2, list.get(arg2).get("title").toString());
					Intent intent = new Intent(SerchActivity.this,
							PeixundetilActivity.class);
					intent.putExtra("trainID", list.get(arg2).get("id")
							.toString());
					startActivity(intent);
				}
			});

			if (et_search_neirong.getText().toString().trim().equals("")) {
				hashistroy();
			}
		} catch (JSONException e) {
			Diaoxian.showerror(SerchActivity.this, e.getMessage());
		}
	}

}
