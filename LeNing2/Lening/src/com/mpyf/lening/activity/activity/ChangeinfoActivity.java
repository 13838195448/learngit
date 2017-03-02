package com.mpyf.lening.activity.activity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.interfaces.bean.Parame.FileUpload;
import com.mpyf.lening.interfaces.bean.Parame.User;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class ChangeinfoActivity extends Activity {

	private LinearLayout ll_setting_back, ll_changeinfo_uploadphoto;
	private EditText tv_changeinfo_nickname, tv_changeinfo_email,
			tv_changeinfo_phone, tv_changeinfo_zhuanye, tv_changeinfo_softtime,
			et_jianjie;
	private TextView tv_changeinfo_name, tv_changeinfo_sex, tv_changeinfo_id,
			tv_setting_save;
	private Spinner sp_changeinfo_xueli;
	private List<String> list;
	private int xueli = 0;
	private int sex = 0;
	private File tempFile;
	private Uri uri;
	private String email;
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1,
			PHOTO_REQUEST_GALLERY = 2, PHOTO_REQUEST_CUT = 3;// 拍照// 从相册中选择// 结果
	public static final String fileName = "Mi_"
			+ DateFormat.format("yyyyMMddHHmmss", new Date()).toString()
			+ ".jpg", filePath = Environment.getExternalStorageDirectory()
			+ "/DCIM/";
	public static final Uri fileUri = Uri
			.fromFile(new File(filePath + fileName));
	private ImageView iv_photo;
	private Bitmap bitmap;
	private final int charMaxNum = 20;
	private String introduce;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// new Xiaoxibeijing().changetop(this, R.color.main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_changeinfo);
		init();
		showinfo();
		addlistener();
	}

	private void init() {
		ll_setting_back = (LinearLayout) findViewById(R.id.ll_setting_back);
		ll_changeinfo_uploadphoto = (LinearLayout) findViewById(R.id.ll_changeinfo_uploadphoto);
		tv_changeinfo_name = (TextView) findViewById(R.id.tv_changeinfo_name);
		tv_changeinfo_nickname = (EditText) findViewById(R.id.tv_changeinfo_nickname);
		tv_changeinfo_sex = (TextView) findViewById(R.id.tv_changeinfo_sex);
		tv_changeinfo_email = (EditText) findViewById(R.id.tv_changeinfo_email);

		tv_changeinfo_phone = (EditText) findViewById(R.id.tv_changeinfo_phone);
		et_jianjie = (EditText) findViewById(R.id.et_jianjie);

		tv_changeinfo_id = (TextView) findViewById(R.id.tv_changeinfo_id);
		sp_changeinfo_xueli = (Spinner) findViewById(R.id.sp_changeinfo_xueli);
		tv_changeinfo_zhuanye = (EditText) findViewById(R.id.tv_changeinfo_zhuanye);
		tv_setting_save = (TextView) findViewById(R.id.tv_setting_save);
		tv_changeinfo_softtime = (EditText) findViewById(R.id.tv_changeinfo_softtime);
		iv_photo = (ImageView) findViewById(R.id.iv_photo);

	}

	private void showinfo() {
		tv_changeinfo_name.setText(Setting.currentUser.getUsername());
		tv_changeinfo_nickname.setText(Setting.currentUser.getNickname());
		tv_changeinfo_email.setText(Setting.currentUser.getEmail());
		tv_changeinfo_phone.setText(Setting.currentUser.getMphone());
		tv_changeinfo_id.setText(Setting.currentUser.getUsername());
		tv_changeinfo_zhuanye.setText(Setting.currentUser.getProfessional());
		tv_changeinfo_softtime.setText(Setting.currentUser.getSoftTime());
		/**
		 * 简介框添加监听
		 */

		et_jianjie.addTextChangedListener(new TextWatcher() {

			private boolean isRun = false;

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) { // TODO Auto-generated method stub
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) { // TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (isRun) {
					isRun = false;
					return;
				}
				isRun = true;
				if (s.toString().length() > charMaxNum) {
					Toast.makeText(getApplicationContext(), "你输入的字数已经超过了限制！",
							Toast.LENGTH_LONG).show();
					et_jianjie.setText(s.subSequence(0, charMaxNum));
					et_jianjie.setSelection(s.subSequence(0, charMaxNum)
							.length());
					return;
				}
				et_jianjie.setText(s);
				et_jianjie.setSelection(s.length());
			}
		});

		// String string = tv_changeinfo_email.getText().toString();
		// if(!(string.contains("@"))){
		// Toast.makeText(getApplicationContext(), "jdsfdk", 0).show();;
		// }

		if (Setting.currentUser.getSex() == 1) {
			tv_changeinfo_sex.setText("女");
		} else {
			tv_changeinfo_sex.setText("男");
		}

		introduce = Setting.currentUser.getIntroduce();
		if (introduce != null) {
			et_jianjie.setText(introduce);
		}

		list = new ArrayList<String>();
		list.add("请选择学历");
		list.add("大专以下");
		list.add("大专");
		list.add("本科");
		list.add("硕士研究生");
		list.add("博士研究生");

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_changeinfo_xueli.setAdapter(adapter);
		sp_changeinfo_xueli.setSelection(Setting.currentUser.getEducation());
	}

	private void addlistener() {

		ll_setting_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				issave();
			}
		});

		tv_changeinfo_sex.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final Dialog dialog = MyDialog.MyDialogShow(
						ChangeinfoActivity.this, R.layout.popwindow_xuanze,
						1.0f);
				Button bt_xuanze_man = (Button) dialog
						.findViewById(R.id.bt_xuanze_man);
				Button bt_xuanze_woman = (Button) dialog
						.findViewById(R.id.bt_xuanze_woman);
				Button bt_xuanze_quit = (Button) dialog
						.findViewById(R.id.bt_xuanze_quit);

				bt_xuanze_quit.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						dialog.dismiss();
					}
				});

				bt_xuanze_man.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						sex = 1;
						tv_changeinfo_sex.setText("女");
						dialog.dismiss();
					}
				});

				bt_xuanze_woman.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						sex = 2;
						tv_changeinfo_sex.setText("男");
						dialog.dismiss();
					}
				});

				dialog.show();

			}
		});

		tv_changeinfo_email
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					@Override
					public void onFocusChange(View arg0, boolean arg1) {
						if (!arg1) {
							String string = tv_changeinfo_email.getText()
									.toString();
							if (!(string.contains("@"))) {
								Toast.makeText(getApplicationContext(),
										"请输入正确的邮箱地址", 0).show();
							}
						}
					}
				});
		tv_changeinfo_nickname
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					@Override
					public void onFocusChange(View arg0, boolean arg1) {
						if (!arg1) {
							if (tv_changeinfo_nickname.getText().toString()
									.trim()
									.equals(Setting.currentUser.getNickname())) {
								return;
							} else {
								checknickname();
							}
						}
					}
				});

		tv_setting_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				String string = tv_changeinfo_email.getText().toString();
				if (!(string.contains("@"))) {
					Toast.makeText(getApplicationContext(), "请输入正确的邮箱地址", 0)
							.show();

				} else {

					issave();
				}

			}
		});

		sp_changeinfo_xueli
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

		tv_changeinfo_zhuanye
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					@Override
					public void onFocusChange(View arg0, boolean arg1) {
						if (arg1) {
							tv_changeinfo_zhuanye.setText("");
						} else {
							if (tv_changeinfo_zhuanye.getText().toString()
									.trim().equals("")) {
								tv_changeinfo_zhuanye
										.setText(Setting.currentUser
												.getProfessional());
							} else {
								return;
							}
						}
					}
				});

		ll_changeinfo_uploadphoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				final Dialog dialog = MyDialog.MyDialogShow(
						ChangeinfoActivity.this, R.layout.popwindow_photo, 1f);

				Button bt_photo_paizhao = (Button) dialog
						.findViewById(R.id.bt_photo_paizhao);
				Button bt_photo_xiangce = (Button) dialog
						.findViewById(R.id.bt_photo_xiangce);
				Button bt_photo_quxiao = (Button) dialog
						.findViewById(R.id.bt_photo_quxiao);

				bt_photo_paizhao.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// 将File对象转换为Uri并启动照相程序
						Intent intent = new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE); // 照相
						intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // 指定图片输出地址

						startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO); // 启动照相
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
		});

	}

	@SuppressLint("NewApi")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}

		switch (requestCode) {
		case PHOTO_REQUEST_TAKEPHOTO:// 拍照
			// 广播刷新相册
			// Intent intentBc = new
			// Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			// intentBc.setData(fileUri);
			// this.sendBroadcast(intentBc);
			// if (data != null) {
			uri = fileUri;
			// Bitmap bitmap = data.getParcelableExtra("data");
			try {
				bitmap = BitmapFactory.decodeStream(getContentResolver()
						.openInputStream(uri));
				bitmap = comp(bitmap);
				byte[] photoData = Bitmap2Bytes(bitmap);
				tempFile = new File(uri.getPath());
				String type = ".jpg";
				upphoto(tempFile.getName(), type, photoData);

			} catch (Exception e) {
				Diaoxian.showerror(ChangeinfoActivity.this, e.getMessage());
			}

			// }
			break;
		case PHOTO_REQUEST_GALLERY:// 从相册中选择
			if (data != null) {
				uri = data.getData();
				try {
					bitmap = BitmapFactory.decodeStream(getContentResolver()
							.openInputStream(uri));
					bitmap = comp(bitmap);
					byte[] photoData = Bitmap2Bytes(bitmap);
					tempFile = new File(uri.getPath());
					String type = ".jpg";
					upphoto(tempFile.getName(), type, photoData);
				} catch (Exception e) {
					Diaoxian.showerror(ChangeinfoActivity.this, e.getMessage());
				}

			}
			break;
		default:
			break;
		}
	}

	private void checknickname() {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					Diaoxian.showerror(ChangeinfoActivity.this, "昵称已被占用");
				} else {
					Diaoxian.showerror(ChangeinfoActivity.this, "昵称可用");
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("nickname", tv_changeinfo_nickname.getText().toString());

				Message msg = new Message();

				String result = HttpUse.messageget("PersonalCenter",
						"verifyNickname", map);

				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
					}

				} catch (JSONException e) {
					msg.obj = e.getMessage();
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	private void issave() {
		final Dialog dialog = MyDialog.MyDialogShow(this, R.layout.popup_isok,
				1f);
		Button bt_isok_quie = (Button) dialog.findViewById(R.id.bt_isok_quie);
		Button bt_isok_ok = (Button) dialog.findViewById(R.id.bt_isok_ok);

		ListenerServer.setfinish(ChangeinfoActivity.this, bt_isok_quie);

		bt_isok_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				saveinfo();
			}
		});
		dialog.show();

	}

	private void saveinfo() {

		final Dialog dialog = MyDialog.MyDialogloading(ChangeinfoActivity.this);
		dialog.show();
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				dialog.dismiss();
				if (msg.what == 1) {

					Diaoxian.showerror(ChangeinfoActivity.this, "保存成功");
					Setting.currentUser.setSex(sex);
					Setting.currentUser.setEmail(tv_changeinfo_email.getText()
							.toString());
					// 电话
					Setting.currentUser.setMphone(tv_changeinfo_phone.getText()
							.toString());
					// 简介
					Setting.currentUser.setIntroduce(et_jianjie.getText()
							.toString());

					Setting.currentUser.setNickname(tv_changeinfo_nickname
							.getText().toString());
					Setting.currentUser.setProfessional(tv_changeinfo_zhuanye
							.getText().toString());
					Setting.currentUser.setEducation(xueli);
					Setting.currentUser.setSoftTime(tv_changeinfo_softtime
							.getText().toString());
					finish();
				} else {
					Diaoxian.showerror(ChangeinfoActivity.this,
							msg.obj.toString());
				}

			}
		};

		// 请求服务器保存
		new Thread() {
			@Override
			public void run() {
				Message msg = new Message();
				User user = new User();
				user.setDeptname(Setting.currentUser.getDeptname());
				user.setEmail(tv_changeinfo_email.getText().toString());
				user.setField(Setting.currentUser.getField());
				user.setKey(Setting.currentUser.getKey());

				user.setMphone(tv_changeinfo_phone.getText().toString());
				user.setIntroduce(et_jianjie.getText().toString());

				user.setNickname(tv_changeinfo_nickname.getText().toString());
				user.setProfessional(tv_changeinfo_zhuanye.getText().toString());
				user.setPk_user(Setting.currentUser.getPk_user());
				user.setSex(sex);
				user.setTruename(Setting.currentUser.getTruename());
				user.setUsername(Setting.currentUser.getUsername());
				user.setEducation(xueli);
				user.setSoftTime(tv_changeinfo_softtime.getText().toString());

				String result = HttpUse.messagepost("PersonalCenter",
						"editMyDetail", user);
				JSONObject jo;
				try {
					jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
					}
					msg.obj = jo.getString("message");

				} catch (JSONException e) {

					msg.obj = e.getMessage();
				}
				handler.sendMessage(msg);

			};
		}.start();

	}

	private void crop(Uri uri) {
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("outputFormat", "JPEG");// 图片格式
		intent.putExtra("noFaceDetection", true);// 取消人脸识别
		intent.putExtra("return-data", true);
		// 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
		startActivityForResult(intent, 3);
	}

	private void upphoto(final String name, final String type,
			final byte[] photodata) {

		final Dialog dialog = MyDialog.MyDialogloading(ChangeinfoActivity.this);
		dialog.show();

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				dialog.dismiss();

				if (msg.what == 1) {
					Diaoxian.showerror(ChangeinfoActivity.this, "上传成功");
					iv_photo.setImageBitmap(bitmap);
				} else {
					Diaoxian.showerror(ChangeinfoActivity.this, "网络状态不佳");
				}

			}
		};

		new Thread() {
			@Override
			public void run() {
				Message msg = new Message();
				try {

					FileUpload fileUpload = new FileUpload();
					fileUpload.setFileName(name);
					fileUpload.setContent(photodata);
					fileUpload.setFileType(type);
					String result = HttpUse.messagepost("PersonalCenter",
							"uploadEdu", fileUpload);
					try {
						JSONObject jo = new JSONObject(result);
						if (jo.getBoolean("result")) {
							msg.what = 1;
						}
						msg.obj = jo.getString("message");

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

	private Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 30) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	private Bitmap comp(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	// bitmap转换为byte[]
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
}
