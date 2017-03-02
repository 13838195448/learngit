package com.mpyf.lening.activity.activity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import u.aly.v;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.CommonUtils;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.activity.adapter.BiaoqianAdapter;
import com.mpyf.lening.activity.adapter.RepayloucengAdapter;
import com.mpyf.lening.interfaces.bean.Parame.Question;
import com.mpyf.lening.interfaces.bean.Result.Bimp;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FatieActivity extends Activity {
	private List<String> list;
	private List<Map<String, String>> list1;
	private LinearLayout ll_addqa_back, ll_addqa_choose;
	private TextView tv_addqa_ok, tv_addqa_spinner, tv_addqa_choosed,
			tv_tianjia;
	private EditText et_addqa_context, ed_addqa_num;
	private int payway = 1;
	private ImageView iv_add;
	private Dialog dialog;
	private File tempFile;
	private Uri uri;
	private Map<String, Object> map;
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1,
			PHOTO_REQUEST_GALLERY = 2, PHOTO_REQUEST_CUT = 3;// 拍照// 从相册中选择// 结果
	public static final String fileName = "Mi_"
			+ DateFormat.format("yyyyMMddHHmmss", new Date()).toString()
			+ ".jpg", filePath = Environment.getExternalStorageDirectory()
			+ "/DCIM/";
	public static final Uri fileUri = Uri
			.fromFile(new File(filePath + fileName));

	Bitmap bitmap;
	private GridView gridView1, gv_biaoqian;
	private List<Bitmap> list2;
	private Bitmap bitMap;
	private GridAdapter gridAdapter;
	private Handler hand;
	private LinearLayout ll_reward, linearLayout;
	private HashSet<Integer> hs = new HashSet<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// new Xiaoxibeijing().changetop(this, R.color.main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_addqa);
		Bimp.tempSelectBitmap.clear();
		init();
		getdata();
		addlistener();
	}

	private void getdata() {

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {

					list1 = new ArrayList<Map<String, String>>();
					try {
						JSONArray ja = new JSONArray(msg.obj.toString());
						for (int i = 0; i < ja.length(); i++) {
							JSONObject jo = ja.getJSONObject(i);
							Map<String, String> map = new HashMap<String, String>();
							// 如果确定其他字段没用，可以只传label_name
							map.put("label_code", jo.getString("label_code"));
							map.put("label_id", jo.getString("label_id"));
							map.put("label_name", jo.getString("label_name"));
							list1.add(map);
							// 这个list1设置到adapter里
						}
						final BiaoqianAdapter adapter =new BiaoqianAdapter(list1,FatieActivity.this,hs);
						gv_biaoqian.setAdapter(adapter);
						
						gv_biaoqian.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								if(hs.contains(arg2)){
									hs.remove(arg2);
								}else{
									hs.add(arg2);
								}
								adapter.notifyDataSetChanged();
//								Resources resourceWN = getBaseContext().getResources();
//								Drawable imageDrawableWN = resourceWN.getDrawable(R.drawable.zd_qa_icon_s);
//								arg1.setBackgroundDrawable(imageDrawableWN);
//								arg1.setBackground(R.drawable.zd_qa_icon_s);
							}
							
						});
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					Diaoxian.showerror(FatieActivity.this, msg.obj.toString());
				}
			}
		};

		new Thread() {

			@Override
			public void run() {
				map = new HashMap<String, Object>();

				Message msg = new Message();
				String result = HttpUse.messageget("QueAndAns", "findLabel",
						map);
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

	private void init() {
		linearLayout = (LinearLayout) findViewById(R.id.linearLayout1);
		ll_addqa_back = (LinearLayout) findViewById(R.id.ll_addqa_back);
		tv_addqa_ok = (TextView) findViewById(R.id.tv_addqa_ok);
		tv_addqa_choosed = (TextView) findViewById(R.id.tv_addqa_choosed);
		et_addqa_context = (EditText) findViewById(R.id.et_addqa_context);
		tv_addqa_spinner = (TextView) findViewById(R.id.tv_addqa_spinner);
		ll_addqa_choose = (LinearLayout) findViewById(R.id.ll_addqa_choose);
		tv_tianjia = (TextView) findViewById(R.id.tv_tianjia);
		gv_biaoqian = (GridView) findViewById(R.id.gv_biaoqian);

		ll_reward = (LinearLayout) findViewById(R.id.ll_reward);
		ed_addqa_num = (EditText) findViewById(R.id.ed_addqa_num);
		TextView tv_addqa_title = (TextView) findViewById(R.id.tv_addqa_title);

		if (!TextUtils.isEmpty(getIntent().getStringExtra("pK_id"))) {
			ll_reward.setVisibility(View.GONE);
			et_addqa_context.setText(getIntent().getStringExtra("content"));
		}
		if (getIntent().getStringExtra("titlename") != null) {
			tv_addqa_title.setText(getIntent().getStringExtra("titlename"));
		}
		gridView1 = (GridView) findViewById(R.id.gridView1);
		list2 = new ArrayList<Bitmap>();
		bitMap = BitmapFactory.decodeResource(getResources(),
				R.drawable.know_img_add);

		list2.add(bitMap);

		gridAdapter = new GridAdapter(FatieActivity.this);
		gridView1.setAdapter(gridAdapter);

		hand = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					gridAdapter.notifyDataSetChanged();
				}
			};
		};

		tv_tianjia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
	}

	private void addlistener() {

		gridView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				if (arg2 == Bimp.tempSelectBitmap.size()) {

					sendImage();
				}
			}
		});

		ll_addqa_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
				Bimp.tempSelectBitmap.clear();
			}
		});

		ll_addqa_choose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (tv_addqa_spinner.getVisibility() == View.GONE) {
					tv_addqa_spinner.setVisibility(View.VISIBLE);

				} else {
					tv_addqa_spinner.setVisibility(View.GONE);
				}

				if (tv_addqa_choosed.getText().toString().equals("金币")) {
					tv_addqa_spinner.setText("乐币");
				} else if (tv_addqa_choosed.getText().toString().equals("乐币")) {
					tv_addqa_spinner.setText("金币");
				}
			}
		});

		tv_addqa_spinner.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tv_addqa_spinner.setVisibility(View.GONE);
				if (tv_addqa_choosed.getText().toString().equals("金币")) {
					tv_addqa_choosed.setText("乐币");
				} else if (tv_addqa_choosed.getText().toString().equals("乐币")) {
					tv_addqa_choosed.setText("金币");
				}
			}
		});

		tv_addqa_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!CommonUtils.isFastDoubleClick()) {

					if (TextUtils.isEmpty(et_addqa_context.getText())
							&& Bimp.tempSelectBitmap.size() == 0) {
						Toast.makeText(getApplicationContext(), "内容不能为空", 0)
								.show();
					} else {
						if (TextUtils.isEmpty(getIntent().getStringExtra(
								"pK_id"))) {
							faSong("",
									ed_addqa_num.getText().toString()
											.equals("") ? 0 : Integer
											.valueOf(ed_addqa_num.getText()
													.toString()));
						} else {

							faSong(getIntent().getStringExtra("pK_id"),
									Integer.valueOf(getIntent().getStringExtra(
											"reward")));
						}

					}
				} else {
					Diaoxian.showerror(FatieActivity.this, "请不要重复提交");
				}
			}

		});

	}

	protected void sendImage() {

		final Dialog dialog = MyDialog.MyDialogShow(FatieActivity.this,
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
				// TODO Auto-generated method stub
				// 将File对象转换为Uri并启动照相程序
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // 照相
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode != FatieActivity.this.RESULT_OK) {
			// finish();
			return;
		}

		switch (requestCode) {
		case PHOTO_REQUEST_TAKEPHOTO:// 拍照

			uri = fileUri;
			try {
				bitmap = BitmapFactory.decodeStream(FatieActivity.this
						.getContentResolver().openInputStream(uri));
				bitmap = comp(bitmap);
				byte[] photoData = Bitmap2Bytes(bitmap);
				tempFile = new File(uri.getPath());
				String type = ".jpg";
				// upphoto(tempFile.getName(), type, photoData);
				com.mpyf.lening.interfaces.bean.Result.ImageItem takePhoto = new com.mpyf.lening.interfaces.bean.Result.ImageItem();
				takePhoto.setBitmap(bitmap);
				takePhoto.setPhotoData(photoData);
				com.mpyf.lening.interfaces.bean.Result.Bimp.tempSelectBitmap
						.add(takePhoto);
				new Thread() {
					public void run() {
						hand.sendEmptyMessage(1);
					};
				}.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Diaoxian.showerror(FatieActivity.this, e.getMessage());
			}
			// }
			break;
		case PHOTO_REQUEST_GALLERY:// 从相册中选择
			if (data != null) {
				uri = data.getData();
				// Bitmap bitmap = data.getParcelableExtra("data");
				try {
					bitmap = BitmapFactory.decodeStream(FatieActivity.this
							.getContentResolver().openInputStream(uri));

					bitmap = comp(bitmap);
					byte[] photoData = Bitmap2Bytes(bitmap);

					// Log.i("photoData", (photoData.length /1024)+"");
					Log.i("photoData", (photoData.length / 1024) + "");
					tempFile = new File(uri.getPath());
					String type = ".jpg";
					// upphoto(tempFile.getName(), type, photoData);
					com.mpyf.lening.interfaces.bean.Result.ImageItem takePhoto = new com.mpyf.lening.interfaces.bean.Result.ImageItem();
					takePhoto.setBitmap(bitmap);
					takePhoto.setPhotoData(photoData);
					com.mpyf.lening.interfaces.bean.Result.Bimp.tempSelectBitmap
							.add(takePhoto);

					new Thread() {
						public void run() {
							hand.sendEmptyMessage(1);
						};
					}.start();
				} catch (Exception e) {
					Diaoxian.showerror(FatieActivity.this, e.getMessage());
				}

			}
			break;
		default:
			break;
		}

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

	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			if (com.mpyf.lening.interfaces.bean.Result.Bimp.tempSelectBitmap
					.size() == 9) {
				return 9;
			}
			return (com.mpyf.lening.interfaces.bean.Result.Bimp.tempSelectBitmap
					.size() + 1);
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_gd_pic, parent,
						false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.iv_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == com.mpyf.lening.interfaces.bean.Result.Bimp.tempSelectBitmap
					.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.know_img_add));
				if (position == 9) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image
						.setImageBitmap(com.mpyf.lening.interfaces.bean.Result.Bimp.tempSelectBitmap
								.get(position).getBitmap());
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					gridAdapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (com.mpyf.lening.interfaces.bean.Result.Bimp.max == com.mpyf.lening.interfaces.bean.Result.Bimp.tempSelectBitmap
								.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							com.mpyf.lening.interfaces.bean.Result.Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
				}
			}).start();
		}
	}

	private Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
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

	private void faSong(final String id, final int reward) {
		// dialog = MyDialog.MyDialogloading(FatieActivity.this);
		// dialog.show();
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 1) {
					// dialog.dismiss();
					if (TextUtils.isEmpty(getIntent().getStringExtra("pK_id"))) {
						Diaoxian.showerror(FatieActivity.this, "提问成功");
					} else {
						Diaoxian.showerror(FatieActivity.this, "编辑成功");
					}
					finish();
				} else {
					Diaoxian.showerror(FatieActivity.this, msg.obj.toString());
				}
			}
		};

		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = new Message();
				Date date = new Date();
				SimpleDateFormat fromat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String fdate = fromat.format(date);

				Question question = new Question();
				question.setPK_Que(id);
				question.setPk_user(Setting.currentUser.getPk_user());
				question.setUserName(Setting.currentUser.getUsername());
				question.setNickname(Setting.currentUser.getNickname());
				question.setTrueName(Setting.currentUser.getTruename());
				question.setQUE_CONTENT(et_addqa_context.getText().toString());
				question.setREWARD_WAY(tv_addqa_choosed.getText().toString()
						.equals("金币") ? 2 : 1);
				question.setREWARD_Num(ed_addqa_num.getText().toString()
						.equals("") ? 0 : Integer.valueOf(ed_addqa_num
						.getText().toString()));
				question.setREAD_Num(0);
				question.setAns_Num(0);
				question.setQUE_STATE(0);
				question.setQueTime(fdate);

				int a = Bimp.tempSelectBitmap.size();
				question.setPic_num(a);

				switch (a) {
				case 9:
					question.setPic9(Bimp.tempSelectBitmap.get(8).photoData);

				case 8:
					question.setPic8(Bimp.tempSelectBitmap.get(7).photoData);

				case 7:
					question.setPic7(Bimp.tempSelectBitmap.get(6).photoData);

				case 6:
					question.setPic6(Bimp.tempSelectBitmap.get(5).photoData);

				case 5:
					question.setPic5(Bimp.tempSelectBitmap.get(4).photoData);

				case 4:
					question.setPic4(Bimp.tempSelectBitmap.get(3).photoData);

				case 3:
					question.setPic3(Bimp.tempSelectBitmap.get(2).photoData);

				case 2:
					question.setPic2(Bimp.tempSelectBitmap.get(1).photoData);

				case 1:
					question.setPic1(Bimp.tempSelectBitmap.get(0).photoData);
					break;

				}
				// String data = new
				// String(Bimp.tempSelectBitmap.get(0).photoData);
				//
				// Log.i("data", data);

				try {
					String result = HttpUse.messagepost("QueAndAns",
							"saveQuestion", question);
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
