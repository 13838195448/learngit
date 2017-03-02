package com.mpyf.lening.activity.activity;

import io.vov.vitamio.utils.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.AsyncBitmapLoader;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.Mohuphoto;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.interfaces.bean.Parame.AbilityCertification;
import com.mpyf.lening.interfaces.bean.Parame.FileUpload;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class BaomingActivity extends Activity implements OnClickListener {

	private LinearLayout ll_baoming_back;
	private ImageView iv_baoming_zjz, iv_baoming_topbg;
	private TextView tv_baoming_name, tv_baoming_sex, tv_baoming_zjz,
			tv_baoming_kaoqu, tv_baoming_ywfx, tv_baoming_cpjyy,
			tv_baoming_zhuanye, tv_baoming_jibie, tv_baoming_bmf,
			tv_baoming_bmftype, tv_baoming_save, tv_baoming_tijiao,
			et_baoming_phone, et_baoming_email, et_baoming_id, et_baoming_comp;
	private Spinner sp_baoming_xueli;
	private Dialog dialog;
	private List<Map<String, Object>> list;
	private String ywfxid = "";
	private String cpid = "";
	private String fileid = "";
	private String jibieid = "";
	private int xueli = 0;
	private String picName;
	private String photourl = "";
	private File tempFile;
	private Uri uri;
	private AbilityCertification abilityCertification;
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1,
			PHOTO_REQUEST_GALLERY = 2, PHOTO_REQUEST_CUT = 3;// ����// �������ѡ��// ���
	public static final String fileName = "Mi_"
			+ DateFormat.format("yyyyMMddHHmmss", new Date()).toString()
			+ ".jpg", filePath = Environment.getExternalStorageDirectory()
			+ "/DCIM/";
	public static final Uri fileUri = Uri
			.fromFile(new File(filePath + fileName));
	private Bitmap bitmap;
	private DisplayImageOptions options;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// new Xiaoxibeijing().changetop(BaomingActivity.this, R.color.main);
		setContentView(R.layout.activity_baoming);
		init();
		showinfo();
		showMeg();
		showMyData();
		addlistener();
	}

	private void showMeg() {

		
		final Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				
				if(msg.what==1){
					int state = (Integer) msg.obj;
					String showlog = null ;
					switch (state) {
					case 1:
						showlog = "����֤δ����";
						break;
					case 2:
						showlog = "����֤�ѱ���δ�ύ";
						break;
					case 3:
						showlog = "����֤���ύ";
						break;
					case 4:
						showlog = "����֤�����ѹر�";
					default:
						break;
					}
					Diaoxian.showerror(BaomingActivity.this, showlog);
				}
			}
		};
		
		new Thread() {
			
			public void run() {
				
				Message msg = new Message();
				HashMap<String, Object> map = new HashMap<String,Object>();
				map.put("cerID", getIntent().getStringExtra("id"));
				String result = HttpUse.messageget("AbilityCertification", "queryCerSignById", map);
				try {
					JSONObject jo = new JSONObject(result);
					
					if(jo.getBoolean("result")){
						msg.what = 1;
						msg.obj = jo.getInt("data");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				handler.sendMessage(msg);
			};
		}.start();
	}

	private void showMyData() {

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);

				if (msg.what == 1) {
					try {
						JSONObject jo = new JSONObject(msg.obj.toString());
						tv_baoming_kaoqu.setText(jo.getString("bmkc"));
						tv_baoming_ywfx.setText(jo.getString("YWFXn"));
						ywfxid = jo.getString("YWFX");
						tv_baoming_cpjyy.setText(jo.getString("CPYYn"));
						cpid = jo.getString("CPYY");
						tv_baoming_zhuanye.setText(jo.getString("ZYLYn"));
						fileid = jo.getString("ZYLY");
						tv_baoming_jibie.setText(jo.getString("RZJBn"));
						jibieid = jo.getString("RZJB");
						sp_baoming_xueli.setSelection(jo.getInt("education"));
						xueli = jo.getInt("education");
						tv_baoming_bmf.setText(jo.getInt("amount") + "");

						tv_baoming_zjz.setText("");

						ImageLoader imageLoader = ImageLoader.getInstance();
						imageLoader.displayImage(
								Setting.apiUrl + jo.getString("photo"),
								iv_baoming_zjz, options);

						AsyncBitmapLoader.setmohuImage(iv_baoming_topbg,
								Setting.apiUrl + jo.getString("photo"));

					} catch (JSONException e) {

						e.printStackTrace();
					}
				}
			}
		};

		new Thread() {
			public void run() {

				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("cerID", getIntent().getStringExtra("id"));
				String result = HttpUse.messageget("AbilityCertification",
						"myCertification", map);
				Message msg = new Message();

				try {
					JSONObject jo = new JSONObject(result);

					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = jo.get("data");
					} else {
						msg.obj = jo.get("message");
					}

				} catch (JSONException e) {

					e.printStackTrace();
				}
				handler.sendMessage(msg);
			};
		}.start();
	}

	private void init() {
		ll_baoming_back = (LinearLayout) findViewById(R.id.ll_baoming_back);
		iv_baoming_zjz = (ImageView) findViewById(R.id.iv_baoming_zjz);
		iv_baoming_topbg = (ImageView) findViewById(R.id.iv_baoming_topbg);
		tv_baoming_name = (TextView) findViewById(R.id.tv_baoming_name);
		tv_baoming_sex = (TextView) findViewById(R.id.tv_baoming_sex);
		tv_baoming_zjz = (TextView) findViewById(R.id.tv_baoming_zjz);

		et_baoming_phone = (TextView) findViewById(R.id.et_baoming_phone);
		et_baoming_email = (TextView) findViewById(R.id.et_baoming_email);
		et_baoming_id = (TextView) findViewById(R.id.et_baoming_id);
		et_baoming_comp = (TextView) findViewById(R.id.et_baoming_comp);
		tv_baoming_kaoqu = (TextView) findViewById(R.id.tv_baoming_kaoqu);
		tv_baoming_ywfx = (TextView) findViewById(R.id.tv_baoming_ywfx);
		tv_baoming_cpjyy = (TextView) findViewById(R.id.tv_baoming_cpjyy);
		tv_baoming_zhuanye = (TextView) findViewById(R.id.tv_baoming_zhuanye);
		tv_baoming_jibie = (TextView) findViewById(R.id.tv_baoming_jibie);
		sp_baoming_xueli = (Spinner) findViewById(R.id.sp_baoming_xueli);
		tv_baoming_bmf = (TextView) findViewById(R.id.tv_baoming_bmf);
		tv_baoming_bmftype = (TextView) findViewById(R.id.tv_baoming_bmftype);
		tv_baoming_save = (TextView) findViewById(R.id.tv_baoming_save);
		tv_baoming_tijiao = (TextView) findViewById(R.id.tv_baoming_tijiao);
	}

	private void showinfo() {
		tv_baoming_name.setText(Setting.currentUser.getTruename());
		tv_baoming_sex.setText(Setting.currentUser.getSex() == 1 ? "Ů" : "��");
		et_baoming_phone.setText(Setting.currentUser.getMphone());
		et_baoming_email.setText(Setting.currentUser.getEmail());
		et_baoming_id.setText(Setting.currentUser.getUsername());
		et_baoming_comp.setText(Setting.currentUser.getDeptname());

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.icon_defualt)
				.showImageOnFail(R.drawable.icon_defualt)
				.showImageForEmptyUri(R.drawable.icon_defualt)
				.cacheInMemory(true).cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.ALPHA_8).build();
	}

	private void addlistener() {
		ListenerServer.setfinish(this, ll_baoming_back);
		iv_baoming_zjz.setOnClickListener(this);
		tv_baoming_sex.setOnClickListener(this);
		tv_baoming_kaoqu.setOnClickListener(this);
		tv_baoming_ywfx.setOnClickListener(this);
		tv_baoming_cpjyy.setOnClickListener(this);
		tv_baoming_zhuanye.setOnClickListener(this);
		tv_baoming_jibie.setOnClickListener(this);
		getxueli();

		sp_baoming_xueli
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						xueli = arg2;
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});

		tv_baoming_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (tv_baoming_kaoqu.getText().toString().equals("")) {
					Diaoxian.showerror(BaomingActivity.this, "��ѡ����");
				} else if (tv_baoming_ywfx.getText().toString().equals("")) {
					Diaoxian.showerror(BaomingActivity.this, "��ѡ��ҵ����");
				} else if (tv_baoming_cpjyy.getText().toString().equals("")) {
					Diaoxian.showerror(BaomingActivity.this, "��ѡ���Ʒ��Ӧ��");
				} else if (tv_baoming_zhuanye.getText().toString().equals("")) {
					Diaoxian.showerror(BaomingActivity.this, "��ѡ��רҵ");
				} else if (tv_baoming_jibie.getText().toString().equals("")) {
					Diaoxian.showerror(BaomingActivity.this, "��ѡ����");
				} else if (xueli == 0) {
					Diaoxian.showerror(BaomingActivity.this, "��ѡ��ѧ��");
				} else if (tv_baoming_zjz.getText().equals("�ϴ�֤����")) {
					Diaoxian.showerror(BaomingActivity.this, "���ϴ�֤����");
				} else {
					savenlrz();

				}

			}
		});

		tv_baoming_tijiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (tv_baoming_kaoqu.getText().toString().equals("")) {
					Diaoxian.showerror(BaomingActivity.this, "��ѡ����");
				} else if (tv_baoming_ywfx.getText().toString().equals("")) {
					Diaoxian.showerror(BaomingActivity.this, "��ѡ��ҵ����");
				} else if (tv_baoming_cpjyy.getText().toString().equals("")) {
					Diaoxian.showerror(BaomingActivity.this, "��ѡ���Ʒ��Ӧ��");
				} else if (tv_baoming_zhuanye.getText().toString().equals("")) {
					Diaoxian.showerror(BaomingActivity.this, "��ѡ��רҵ����");
				} else if (tv_baoming_jibie.getText().toString().equals("")) {
					Diaoxian.showerror(BaomingActivity.this, "��ѡ����");
				} else if (xueli == 0) {
					Diaoxian.showerror(BaomingActivity.this, "��ѡ��ѧ��");
				} else if (tv_baoming_zjz.getText().equals("�ϴ�֤����")) {
					Diaoxian.showerror(BaomingActivity.this, "���ϴ�֤����");
				} else {
					tijiaonlrz();

				}

			}
		});
	}

	private void getcpjyy() {

		if (ywfxid.equals("")) {
			Diaoxian.showerror(this, "��ѡ��ҵ����");
			return;
		}

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						list = new ArrayList<Map<String, Object>>();
						for (int i = 0; i < ja.length(); i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							JSONObject jo = ja.getJSONObject(i);
							map.put("id", jo.getString("PK_Dir"));
							map.put("title", jo.getString("dirName"));
							list.add(map);
						}
						dialog = MyDialog.MyDialogShow(BaomingActivity.this,
								R.layout.popwindow_spinner, 1f);
						ListView lv_xuanze = (ListView) dialog
								.findViewById(R.id.lv_xuanze);

						SimpleAdapter adapter = new SimpleAdapter(
								BaomingActivity.this, list,
								R.layout.item_xuanze, new String[] { "title" },
								new int[] { R.id.tv_xuanxiang });
						lv_xuanze.setAdapter(adapter);

						lv_xuanze
								.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> arg0, View arg1,
											int arg2, long arg3) {
										tv_baoming_cpjyy.setText(list.get(arg2)
												.get("title").toString());
										cpid = list.get(arg2).get("id")
												.toString();
										dialog.dismiss();
									}
								});
						dialog.show();

					} catch (JSONException e) {
						Diaoxian.showerror(BaomingActivity.this, e.getMessage());
					}
				} else {
					Diaoxian.showerror(BaomingActivity.this, msg.obj.toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("dirId", ywfxid);

				Message msg = new Message();

				String result = HttpUse.messageget("AbilityCertification",
						"getProduct", map);

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

	private void getywfx() {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						list = new ArrayList<Map<String, Object>>();
						for (int i = 0; i < ja.length(); i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							JSONObject jo = ja.getJSONObject(i);
							map.put("id", jo.getString("PK_Dir"));
							map.put("title", jo.getString("dirName"));
							list.add(map);
						}
						dialog = MyDialog.MyDialogShow(BaomingActivity.this,
								R.layout.popwindow_spinner, 1f);
						ListView lv_xuanze = (ListView) dialog
								.findViewById(R.id.lv_xuanze);

						SimpleAdapter adapter = new SimpleAdapter(
								BaomingActivity.this, list,
								R.layout.item_xuanze, new String[] { "title" },
								new int[] { R.id.tv_xuanxiang });
						lv_xuanze.setAdapter(adapter);

						lv_xuanze
								.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> arg0, View arg1,
											int arg2, long arg3) {
										tv_baoming_ywfx.setText(list.get(arg2)
												.get("title").toString());
										ywfxid = list.get(arg2).get("id")
												.toString();
										dialog.dismiss();
									}
								});
						dialog.show();

					} catch (JSONException e) {
						Diaoxian.showerror(BaomingActivity.this, e.getMessage());
					}
				} else {
					Diaoxian.showerror(BaomingActivity.this, msg.obj.toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				Message msg = new Message();

				String result = HttpUse.messageget("AbilityCertification",
						"getDirection", map);

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

	private void getkaodian() {

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					String[] kaodians = msg.obj.toString().replace("[", "")
							.replace("]", "").split(",");
					dialog = MyDialog.MyDialogShow(BaomingActivity.this,
							R.layout.popwindow_spinner, 1f);
					ListView lv_xuanze = (ListView) dialog
							.findViewById(R.id.lv_xuanze);

					list = new ArrayList<Map<String, Object>>();
					for (int i = 0; i < kaodians.length; i++) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("title", kaodians[i].replace("\"", ""));
						list.add(map);
					}

					SimpleAdapter adapter = new SimpleAdapter(
							BaomingActivity.this, list, R.layout.item_xuanze,
							new String[] { "title" },
							new int[] { R.id.tv_xuanxiang });
					lv_xuanze.setAdapter(adapter);

					lv_xuanze.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							tv_baoming_kaoqu.setText(list.get(arg2)
									.get("title").toString());
							dialog.dismiss();
						}
					});
					dialog.show();
				} else {
					Diaoxian.showerror(BaomingActivity.this, msg.obj.toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				Message msg = new Message();

				String result = HttpUse.messageget("AbilityCertification",
						"getExamDir", map);

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

	private void getfile() {
		if (ywfxid.equals("")) {
			Diaoxian.showerror(this, "��ѡ��ҵ����");
			return;
		} else if (cpid.equals("")) {
			Diaoxian.showerror(this, "��ѡ���Ʒ��Ӧ��");
			return;
		}

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						list = new ArrayList<Map<String, Object>>();
						for (int i = 0; i < ja.length(); i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							JSONObject jo = ja.getJSONObject(i);
							map.put("id", jo.getString("PK_Dir"));
							map.put("title", jo.getString("dirName"));
							list.add(map);
						}
						dialog = MyDialog.MyDialogShow(BaomingActivity.this,
								R.layout.popwindow_spinner, 1f);
						ListView lv_xuanze = (ListView) dialog
								.findViewById(R.id.lv_xuanze);

						SimpleAdapter adapter = new SimpleAdapter(
								BaomingActivity.this, list,
								R.layout.item_xuanze, new String[] { "title" },
								new int[] { R.id.tv_xuanxiang });
						lv_xuanze.setAdapter(adapter);

						lv_xuanze
								.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> arg0, View arg1,
											int arg2, long arg3) {
										tv_baoming_zhuanye.setText(list
												.get(arg2).get("title")
												.toString());
										fileid = list.get(arg2).get("id")
												.toString();
										dialog.dismiss();
									}
								});
						dialog.show();

					} catch (JSONException e) {
						Diaoxian.showerror(BaomingActivity.this, e.getMessage());
					}
				} else {
					Diaoxian.showerror(BaomingActivity.this, msg.obj.toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("dirId", ywfxid);
				map.put("proId", cpid);
				Message msg = new Message();

				String result = HttpUse.messageget("AbilityCertification",
						"getField", map);

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

	private void getjibie() {
		if (ywfxid.equals("")) {
			Diaoxian.showerror(this, "��ѡ��ҵ����");
			return;
		} else if (cpid.equals("")) {
			Diaoxian.showerror(this, "��ѡ���Ʒ��Ӧ��");
			return;
		} else if (fileid.equals("")) {
			Diaoxian.showerror(this, "��ѡ��רҵ����");
			return;
		}

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						list = new ArrayList<Map<String, Object>>();
						for (int i = 0; i < ja.length(); i++) {
							Map<String, Object> map = new HashMap<String, Object>();
							JSONObject jo = ja.getJSONObject(i);
							map.put("id", jo.getString("PK_Dir"));
							map.put("title", jo.getString("dirName"));
							list.add(map);
						}
						dialog = MyDialog.MyDialogShow(BaomingActivity.this,
								R.layout.popwindow_spinner, 1f);
						ListView lv_xuanze = (ListView) dialog
								.findViewById(R.id.lv_xuanze);

						SimpleAdapter adapter = new SimpleAdapter(
								BaomingActivity.this, list,
								R.layout.item_xuanze, new String[] { "title" },
								new int[] { R.id.tv_xuanxiang });
						lv_xuanze.setAdapter(adapter);

						lv_xuanze
								.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> arg0, View arg1,
											int arg2, long arg3) {
										tv_baoming_jibie.setText(list.get(arg2)
												.get("title").toString());
										jibieid = list.get(arg2).get("id")
												.toString();
										getcost();
										dialog.dismiss();
									}
								});
						dialog.show();

					} catch (JSONException e) {
						Diaoxian.showerror(BaomingActivity.this, e.getMessage());
					}
				} else {
					Diaoxian.showerror(BaomingActivity.this, msg.obj.toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("dirId", ywfxid);
				map.put("proId", cpid);
				map.put("fieId", fileid);
				Message msg = new Message();

				String result = HttpUse.messageget("AbilityCertification",
						"getLevel", map);

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

	private void getxueli() {
		String[] xuelis = { "ѡ��ѧ��", "��ר����", "��ר", "����", "˶ʿ�о���", "��ʿ�о���" };
		List<Map<String, Object>> xuelilist = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < xuelis.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", xuelis[i]);
			xuelilist.add(map);
		}

		SimpleAdapter adapter = new SimpleAdapter(BaomingActivity.this,
				xuelilist, R.layout.item_xueli, new String[] { "title" },
				new int[] { R.id.tv_xueli_title });
		// adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_baoming_xueli.setAdapter(adapter);
	}

	private void getcost() {

		if (ywfxid.equals("")) {
			Diaoxian.showerror(this, "��ѡ��ҵ����");
			return;
		}

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {

					tv_baoming_bmf.setText(msg.obj.toString());
				} else {

					Diaoxian.showerror(BaomingActivity.this, msg.obj.toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("levId", jibieid);

				Message msg = new Message();

				String result = HttpUse.messageget("AbilityCertification",
						"getCountgold", map);

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

	private void savenlrz() {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Log.i(getLocalClassName(), abilityCertification.toString());
				Diaoxian.showerror(BaomingActivity.this, msg.obj.toString());
				if (msg.what == 1) {

					finish();
				}
			}
		};

		new Thread() {

			@Override
			public void run() {

				Message msg = new Message();
				abilityCertification = new AbilityCertification();
				abilityCertification.setAmount(Integer.valueOf(tv_baoming_bmf
						.getText().toString()));
				abilityCertification.setBmkc(tv_baoming_kaoqu.getText()// ��������
						.toString());
				abilityCertification.setCer_Name(getIntent().getStringExtra(
						"name"));
				abilityCertification.setCPYY(cpid);// ��ƷӦ��
				abilityCertification.setEducation(xueli);// ѧ��
				abilityCertification.setPayInf(false);
				abilityCertification.setPhoto("");
				abilityCertification.setPhotoName(picName);
				abilityCertification
						.setPK_Cer(getIntent().getStringExtra("id"));
				abilityCertification.setPk_user(Setting.currentUser
						.getPk_user());
				abilityCertification.setRZJB(jibieid);// ��֤����
				abilityCertification.setYWFX(ywfxid);//
				abilityCertification.setZYLY(fileid);// רҵ����

				String result = HttpUse.messagepost("AbilityCertification",
						"saveCertification", abilityCertification);

				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
					}
					msg.obj = jo.getString("message");
				} catch (JSONException e) {
					msg.obj = e.getMessage();
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	private void tijiaonlrz() {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Diaoxian.showerror(BaomingActivity.this, msg.obj.toString());
				if (msg.what == 1) {
					finish();
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				Message msg = new Message();
				AbilityCertification abilityCertification = new AbilityCertification();
				abilityCertification.setAmount(Integer.valueOf(tv_baoming_bmf
						.getText().toString()));
				abilityCertification.setBmkc(tv_baoming_kaoqu.getText()
						.toString());
				abilityCertification.setCer_Name(getIntent().getStringExtra(
						"name"));
				abilityCertification.setCPYY(cpid);
				abilityCertification.setEducation(xueli);
				abilityCertification.setPayInf(true);
				abilityCertification.setPhoto("");
				abilityCertification.setPhotoName(picName);
				abilityCertification
						.setPK_Cer(getIntent().getStringExtra("id"));
				abilityCertification.setPk_user(Setting.currentUser
						.getPk_user());
				abilityCertification.setRZJB(jibieid);
				abilityCertification.setYWFX(ywfxid);
				abilityCertification.setZYLY(fileid);

				String result = HttpUse.messagepost("AbilityCertification",
						"subCertification", abilityCertification);

				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
					}
					msg.obj = jo.getString("message");
				} catch (JSONException e) {
					msg.obj = e.getMessage();
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.iv_baoming_zjz:
			uploadzjz();
			break;
		case R.id.tv_baoming_kaoqu:
			getkaodian();
			break;
		case R.id.tv_baoming_ywfx:
			getywfx();
			break;
		case R.id.tv_baoming_cpjyy:
			getcpjyy();
			break;
		case R.id.tv_baoming_zhuanye:
			getfile();
			break;
		case R.id.tv_baoming_jibie:
			getjibie();
			break;
		case R.id.tv_baoming_tijiao:
			savenlrz();
			break;
		default:
			break;
		}

	}

	private void uploadzjz() {

		final Dialog dialog = MyDialog.MyDialogShow(BaomingActivity.this,
				R.layout.popwindow_photo, 1f);

		Button bt_photo_paizhao = (Button) dialog
				.findViewById(R.id.bt_photo_paizhao);
		Button bt_photo_xiangce = (Button) dialog
				.findViewById(R.id.bt_photo_xiangce);
		Button bt_photo_quxiao = (Button) dialog
				.findViewById(R.id.bt_photo_quxiao);

		bt_photo_paizhao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// ��File����ת��ΪUri�������������
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // ����
				intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // ָ��ͼƬ�����ַ

				startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO); // ��������
				dialog.dismiss();
			}
		});

		bt_photo_xiangce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent2 = new Intent(Intent.ACTION_PICK);
				intent2.setType("image/*");
				startActivityForResult(intent2, 2);
				dialog.dismiss();
			}
		});

		bt_photo_quxiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		dialog.show();

	}

	// private void crop(Uri uri) {
	// // �ü�ͼƬ��ͼ
	// Intent intent = new Intent("com.android.camera.action.CROP");
	// intent.setDataAndType(uri, "image/*");
	// intent.putExtra("crop", "true");
	// // �ü���ı�����1��1
	// intent.putExtra("aspectX", 11);
	// intent.putExtra("aspectY", 16);
	// // �ü������ͼƬ�ĳߴ��С
	// intent.putExtra("outputFormat", "JPG");// ͼƬ��ʽ
	// intent.putExtra("noFaceDetection", true);// ȡ������ʶ��
	// intent.putExtra("return-data", true);
	// // ����һ�����з���ֵ��Activity��������ΪPHOTO_REQUEST_CUT
	// startActivityForResult(intent, 3);
	// }

	@SuppressLint("NewApi")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			// finish();
			return;
		}

		switch (requestCode) {
		case PHOTO_REQUEST_TAKEPHOTO:// ����
			// �㲥ˢ�����
			uri = fileUri;
			// Bitmap bitmap = data.getParcelableExtra("data");
			//
			// iv_baoming_zjz.setImageBitmap(bitmap);
			// tv_baoming_zjz.setVisibility(View.GONE);
			// iv_baoming_topbg.setImageDrawable(Mohuphoto.BoxBlurFilter(bitmap));
			try {
				bitmap = BitmapFactory.decodeStream(getContentResolver()
						.openInputStream(uri));
				bitmap = comp(bitmap);
				byte[] photoData = Bitmap2Bytes(bitmap);
				tempFile = new File(uri.getPath());
				String type = ".jpg";
				upphoto(tempFile.getName(), type, photoData);
				tv_baoming_zjz.setText("");
				iv_baoming_zjz.setImageBitmap(bitmap);
				iv_baoming_topbg.setBackground(Mohuphoto.BoxBlurFilter(bitmap));
			} catch (Exception e) {
				Diaoxian.showerror(BaomingActivity.this, e.getMessage());
			}

			// }
			break;
		case PHOTO_REQUEST_GALLERY:// �������ѡ��
			if (data != null) {
				uri = data.getData();
				// Bitmap bitmap = data.getParcelableExtra("data");
				try {
					bitmap = BitmapFactory.decodeStream(getContentResolver()
							.openInputStream(uri));
					bitmap = comp(bitmap);
					byte[] photoData = Bitmap2Bytes(bitmap);
					tempFile = new File(uri.getPath());
					String type = ".jpg";

					iv_baoming_zjz.setImageBitmap(bitmap);
					tv_baoming_zjz.setText("");
					iv_baoming_topbg.setBackground(Mohuphoto
							.BoxBlurFilter(bitmap));
					upphoto(tempFile.getName(), type, photoData);
				} catch (Exception e) {
					Diaoxian.showerror(BaomingActivity.this, e.getMessage());
				}

			}
			break;
		default:
			break;
		}
	}

	private void upphoto(final String name, final String type,
			final byte[] photodata) {

		final Dialog dialog = MyDialog.MyDialogloading(BaomingActivity.this);
		dialog.show();

		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				dialog.dismiss();

				if (msg.what == 1) {
					Diaoxian.showerror(BaomingActivity.this, "�ϴ��ɹ�");
					picName = msg.obj.toString();
				} else {
					Diaoxian.showerror(BaomingActivity.this, "����״̬����");
				}

			}
		};

		new Thread() {
			@Override
			public void run() {
				Message msg = new Message();
				try {

					FileUpload fileUpload = new FileUpload();
					fileUpload.setFileName(getIntent().getStringExtra("id"));
					fileUpload.setContent(photodata);
					fileUpload.setFileType(type);
					String result = HttpUse.messagepost("PersonalCenter",
							"uploadCerPhoto", fileUpload);
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

				} catch (Exception e1) {
					msg.obj = e1.getMessage();
				}
				handler.sendMessage(msg);
			}
		}.start();

	}

	public byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(CompressFormat.JPEG, 100, baos);
		byte[] result = baos.toByteArray();
		try {
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// ����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��
			baos.reset();// ����baos�����baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// ����ѹ��options%����ѹ��������ݴ�ŵ�baos��
			options -= 10;// ÿ�ζ�����10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// ��ѹ���������baos��ŵ�ByteArrayInputStream��
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// ��ByteArrayInputStream��������ͼƬ
		return bitmap;
	}

	private Bitmap comp(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// �ж����ͼƬ����1M,����ѹ������������ͼƬ��BitmapFactory.decodeStream��ʱ���
			baos.reset();// ����baos�����baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// ����ѹ��50%����ѹ��������ݴ�ŵ�baos��
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// ��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// ���������ֻ��Ƚ϶���800*480�ֱ��ʣ����ԸߺͿ���������Ϊ
		float hh = 800f;// �������ø߶�Ϊ800f
		float ww = 480f;// �������ÿ��Ϊ480f
		// ���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��
		int be = 1;// be=1��ʾ������
		if (w > h && w > ww) {// �����ȴ�Ļ����ݿ�ȹ̶���С����
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// ����߶ȸߵĻ����ݿ�ȹ̶���С����
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// �������ű���
		// ���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);// ѹ���ñ�����С���ٽ�������ѹ��
	}
}
