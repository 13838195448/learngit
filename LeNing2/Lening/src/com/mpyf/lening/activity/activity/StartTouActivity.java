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

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.CommonUtils;
import com.mpyf.lening.Jutil.CustomDialog;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.interfaces.bean.Parame.Vote;
import com.mpyf.lening.interfaces.bean.Parame.Vote1;
import com.mpyf.lening.interfaces.bean.Result.Bimp;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class StartTouActivity extends Activity {
	private List<String> list;
	private List<Map<String, String>> list1;
	private TextView tv_tou_send, tv_tou_choosed, tv_tou_spinner, tv_addchoose;
	private LinearLayout tv_tou_cancle;
	private EditText et_tou_context, ed_tou_num, ed_every_num;
	private int payway = 1;
	private ImageView iv_add;
	private Dialog dialog;
	private File tempFile;
	private Uri uri;
	private Map<String, Object> map;
	// private Vote vote = new Vote();
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1,
			PHOTO_REQUEST_GALLERY = 2, PHOTO_REQUEST_CUT = 3;// ����// �������ѡ��// ���
	public static final String fileName = "Mi_"
			+ DateFormat.format("yyyyMMddHHmmss", new Date()).toString()
			+ ".jpg", filePath = Environment.getExternalStorageDirectory()
			+ "/DCIM/";
	public static final Uri fileUri = Uri
			.fromFile(new File(filePath + fileName));

	Bitmap bitmap;
	private GridView gv_tou;
	private List<Bitmap> list2 = new ArrayList<Bitmap>();
	private Bitmap bitMap;
	private GridAdapter gridAdapter;
	private Handler hand;
	private LinearLayout ll_tou_choose;
	private HashSet<Integer> hs = new HashSet<Integer>();
	private ListView lv_xuanxiang;
	private XuanXiangAdapter adaptered;
	public ArrayList<String> arr = new ArrayList<String>();
	private int optionType;
	private int rewardType = 1;

	// TODO �����json
	// option_a=aaa&option_b=sss&option_num=2&option_type=0&pic=%5BB%404a9a3a70&reward_every=33&reward_num=555&reward_type=1&sourcefrom=3&time=2017-01-09&vote_title=www&token=56E05A83C4CFA1ED8B39594442D86201
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// new Xiaoxibeijing().changetop(this, R.color.main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_starttou);
		Bimp.tempSelectBitmap.clear();
		// �����Ƿ��ǵ�ѡ�ĶԻ���
		new AlertDialog.Builder(StartTouActivity.this).setMessage("����ͶƱ�Ƿ�Ϊ��ѡ?")
		// ������ʾ������
				.setPositiveButton("��", new DialogInterface.OnClickListener() {//
							// ����ȷ����ť
							@Override
							public void onClick(DialogInterface dialog,
									int which) {// ȷ����ť����Ӧ�¼�
								// vote.setOption_type(1);
								optionType = 1;
							}
						}).setNegativeButton("��", null).show();// �ڰ�����Ӧ�¼�����ʾ�˶Ի���

		init();
		addlistener();
	}

	private void init() {

		tv_tou_cancle = (LinearLayout) findViewById(R.id.tv_tou_cancle);
		tv_tou_send = (TextView) findViewById(R.id.tv_tou_send);
		et_tou_context = (EditText) findViewById(R.id.et_tou_context);

		gv_tou = (GridView) findViewById(R.id.gv_tou);
		// ѡ���һ����ֱ�
		ll_tou_choose = (LinearLayout) findViewById(R.id.ll_tou_choose);
		tv_tou_choosed = (TextView) findViewById(R.id.tv_tou_choosed);
		ed_tou_num = (EditText) findViewById(R.id.ed_tou_num);
		ed_every_num = (EditText) findViewById(R.id.ed_every_num);
		tv_tou_spinner = (TextView) findViewById(R.id.tv_tou_spinner);

		list2 = new ArrayList<Bitmap>();
		bitMap = BitmapFactory.decodeResource(getResources(),
				R.drawable.know_img_add);

		list2.add(bitMap);

		gridAdapter = new GridAdapter(StartTouActivity.this);

		gv_tou.setAdapter(gridAdapter);

		hand = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					gridAdapter.notifyDataSetChanged();
				}
			};
		};

		// ����һ��ѡ��
		tv_addchoose = (TextView) findViewById(R.id.tv_addchoose);
		lv_xuanxiang = (ListView) findViewById(R.id.lv_xuanxiang);

		arr.add(0, "");
		adaptered = new XuanXiangAdapter(this, arr);
		lv_xuanxiang.setAdapter(adaptered);

		// �������һ��ѡ��
		tv_addchoose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (arr.size() >= 10) {
					Toast.makeText(StartTouActivity.this, "���ֻ������ʮ��ѡ��",
							Toast.LENGTH_SHORT).show();
				} else {

					arr.add(arr.size(), "");
					adaptered.notifyDataSetChanged();
					lv_xuanxiang.setSelection(arr.size() - 1);
				}
			}
		});

	}

	// ѡ���adapter
	public class XuanXiangAdapter extends BaseAdapter {
		private Context context;
		private LayoutInflater inflater;
		public ArrayList<String> arr;

		public XuanXiangAdapter(Context context, ArrayList<String> arr) {
			super();
			this.context = context;
			inflater = LayoutInflater.from(context);
			this.arr = arr;
		}

		@Override
		public int getCount() {
			return arr.size();
		}

		@Override
		public Object getItem(int arg0) {
			return arr.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(final int arg0, View arg1, ViewGroup arg2) {
			View view = View.inflate(StartTouActivity.this,
					R.layout.item_xuanxiang, null);
			final EditText tv_xuan = (EditText) view.findViewById(R.id.tv_xuan);
			String text = arr.get(arg0);
			if (TextUtils.isEmpty(text)) {
				tv_xuan.setHint("ѡ��" + (arg0 + 1)); // ���ع�adapter��ʱ���������ݴ���
			} else {
				tv_xuan.setText(text);
			}

			tv_xuan.addTextChangedListener(new TextWatcher() {

				@Override
				public void afterTextChanged(Editable s) {
					arr.remove(arg0);
					arr.add(arg0, tv_xuan.getText().toString());
					// adaptered.notifyDataSetChanged();

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {

				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {

				}

			});
			return view;
		}

	}

	private void addlistener() {

		ll_tou_choose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (tv_tou_spinner.getVisibility() == View.GONE) {
					tv_tou_spinner.setVisibility(View.VISIBLE);

				} else {
					tv_tou_spinner.setVisibility(View.GONE);
				}

				if (tv_tou_choosed.getText().toString().equals("���")) {
					tv_tou_spinner.setText("�ֱ�");
					rewardType = 1;
				} else if (tv_tou_choosed.getText().toString().equals("�ֱ�")) {
					tv_tou_spinner.setText("���");
					rewardType = 2;
				}
			}
		});

		tv_tou_spinner.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				tv_tou_spinner.setVisibility(View.GONE);
				if (tv_tou_choosed.getText().toString().equals("���")) {
					tv_tou_choosed.setText("�ֱ�");
					rewardType = 1;
				} else if (tv_tou_choosed.getText().toString().equals("�ֱ�")) {
					tv_tou_choosed.setText("���");
					rewardType = 2;
				}
			}
		});

		gv_tou.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				if (arg2 == Bimp.tempSelectBitmap.size()) {

					sendImage();
				}
			}
		});

		tv_tou_cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
				Bimp.tempSelectBitmap.clear();
			}
		});

		tv_tou_send.setOnClickListener(new OnClickListener() {

			@SuppressLint("ShowToast")
			@Override
			public void onClick(View arg0) {
				if (!CommonUtils.isFastDoubleClick()) {
					if (optionType == 1) {

						if (TextUtils.isEmpty(et_tou_context.getText()
								.toString())) {
							Toast.makeText(getApplicationContext(), "���ⲻ��Ϊ��", 0)
									.show();
						} else if (!TextUtils.isEmpty(ed_tou_num.getText()
								.toString())
								&& Integer.valueOf(ed_tou_num.getText()
										.toString()) > 0
								&& (TextUtils.isEmpty(ed_every_num.getText()
										.toString())
										|| Integer.valueOf(ed_every_num
												.getText().toString()) <= 0 || (Integer
										.valueOf(ed_every_num.getText()
												.toString()) > Integer
										.valueOf(ed_tou_num.getText()
												.toString())))) {
							Toast.makeText(getApplicationContext(),
									"ÿ�����Ͳ���Ϊ���Ҳ��ܴ���������", 0).show();

						} else if (arr.size() < 3) {
							Toast.makeText(getApplicationContext(),
									"����Ҫ����3��ѡ��", 0).show();
						} else {

							// ����ͶƱ�����ӿ�
							boolean flag = true;
							for (int i = 0; i < arr.size(); i++) {
								String context = arr.get(i);
								if (TextUtils.isEmpty(context)) {
									flag = false;
									break;
								}
							}
							if (flag) {
								faSong();
							} else {
								Toast.makeText(getApplicationContext(),
										"ѡ�����ݲ���Ϊ��", 0).show();
							}

						}
					} else {

						if (TextUtils.isEmpty(et_tou_context.getText()
								.toString())) {
							Toast.makeText(getApplicationContext(), "���ⲻ��Ϊ��", 0)
									.show();
						} else if (!TextUtils.isEmpty(ed_tou_num.getText()
								.toString())
								&& Integer.valueOf(ed_tou_num.getText()
										.toString()) > 0
								&& (TextUtils.isEmpty(ed_every_num.getText()
										.toString())
										|| Integer.valueOf(ed_every_num
												.getText().toString()) <= 0 || (Integer
										.valueOf(ed_every_num.getText()
												.toString()) > Integer
										.valueOf(ed_tou_num.getText()
												.toString())))) {
							Toast.makeText(getApplicationContext(),
									"ÿ�����Ͳ���Ϊ���Ҳ��ܴ���������", 0).show();

						} else if (arr.size() < 2) {
							Toast.makeText(getApplicationContext(),
									"����Ҫ����2��ѡ��", 0).show();
						} else {

							// ����ͶƱ�����ӿ�
							boolean flag = true;
							for (int i = 0; i < arr.size(); i++) {
								String context = arr.get(i);
								if (TextUtils.isEmpty(context)) {
									flag = false;
									break;
								}
							}
							if (flag) {
								faSong();
							} else {
								Toast.makeText(getApplicationContext(),
										"ѡ�����ݲ���Ϊ��", 0).show();
							}

						}
					}
				} else {
					Diaoxian.showerror(StartTouActivity.this, "�벻Ҫ�ظ��ύ");
				}
			}

		});

	}

	protected void sendImage() {

		final Dialog dialog = MyDialog.MyDialogShow(StartTouActivity.this,
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode != StartTouActivity.this.RESULT_OK) {
			// finish();
			return;
		}

		switch (requestCode) {
		case PHOTO_REQUEST_TAKEPHOTO:// ����

			uri = fileUri;
			try {
				bitmap = BitmapFactory.decodeStream(StartTouActivity.this
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
				Diaoxian.showerror(StartTouActivity.this, e.getMessage());
			}
			// }
			break;
		case PHOTO_REQUEST_GALLERY:// �������ѡ��
			if (data != null) {
				uri = data.getData();
				// Bitmap bitmap = data.getParcelableExtra("data");
				try {
					bitmap = BitmapFactory.decodeStream(StartTouActivity.this
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
					Diaoxian.showerror(StartTouActivity.this, e.getMessage());
				}

			}
			break;
		default:
			break;
		}

	}

	// bitmapת��Ϊbyte[]
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
					.size() == 1) {
				return 2;
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
				if (position == 1) {
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
		float ww = 480f;// �������ÿ���Ϊ480f
		// ���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��
		int be = 1;// be=1��ʾ������
		if (w > h && w > ww) {// ������ȴ�Ļ����ݿ��ȹ̶���С����
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// ����߶ȸߵĻ����ݿ��ȹ̶���С����
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

	// ����ͶƱ
	private void faSong() {
		// dialog = MyDialog.MyDialogloading(FatieActivity.this);
		// dialog.show();
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {

					Toast.makeText(StartTouActivity.this, "�����ɹ�",
							Toast.LENGTH_SHORT).show();

					finish();
				} else {
					Diaoxian.showerror(StartTouActivity.this,
							msg.obj.toString());
				}
			}
		};

		new Thread() {

			@SuppressLint("SimpleDateFormat")
			@Override
			public void run() {
				// TODO
				// ����ͶƱ��Ҫ���������vote_title,vote_pic,pic,vote_time,reward_type,reward_num,reward_every,
				// option_type,option_num
				// HashMap<String, Object> vote = new HashMap<String, Object>();
				// ͶƱ�ı���
				Vote1 vote = new Vote1();
				// vote.put("vote_title", et_tou_context.getText().toString());
				vote.setVote_title(et_tou_context.getText().toString());
				// TODO �����ͼƬ��ô�ϴ��������������ͼƬ�Ǳ����ֶ���
				if (Bimp.tempSelectBitmap.size() != 0) {
					// vote.put("pic", Bitmap2Bytes(bitmap));
					vote.setPic(Bimp.tempSelectBitmap.get(0).photoData);
					// question.setPic9(Bimp.tempSelectBitmap.get(8).photoData);
				}
				// ����ͶƱʱ�� 2016-11-17 13:44:13
				// vote.setVote_time("2017-01-09 15:43:13");
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				vote.setVote_time(format.format(new Date()));

				// ͶƱ���� reward_type���ͷ�ʽ0������1�ֱ�2���
				// vote.put("option_type", optionType);
				vote.setOption_type(optionType);
				// ѡ�����
				// vote.put("option_num", arr.size());
				vote.setOption_num(arr.size());
				// ������
				// if (!TextUtils.isEmpty(ed_tou_num.getText().toString())) {
				//
				// // vote.put("reward_num", ed_tou_num.getText().toString());
				//
				// }
				vote.setReward_num(TextUtils.isEmpty(ed_tou_num.getText()
						.toString()) ? 0 : Integer.valueOf(ed_tou_num.getText()
						.toString()));
				// ���ͷ�ʽ
				// vote.put("reward_type", rewardType);
				vote.setReward_type(rewardType);
				// ÿ������
				// if (!TextUtils.isEmpty(ed_every_num.getText().toString())) {

				// vote.put("reward_every",
				// ed_every_num.getText().toString());
				vote.setReward_every(TextUtils.isEmpty(ed_every_num.getText()
						.toString()) ? 0 : Integer.valueOf(ed_every_num
						.getText().toString()));
				// }

				// ѡ��a������
				// if (arr.size() > 0 &&
				// !TextUtils.isEmpty(arr.get(0).toString())) {
				// vote.put("option_a", arr.get(0).toString());
				vote.setOption_a((arr.size() > 0 && !TextUtils.isEmpty(arr.get(
						0).toString())) ? arr.get(0).toString() : "");
				// }
				// ѡ��b������
				// if (arr.size() > 1 &&
				// !TextUtils.isEmpty(arr.get(1).toString())) {
				// vote.put("option_b", arr.get(1).toString());
				vote.setOption_b((arr.size() > 1 && !TextUtils.isEmpty(arr.get(
						1).toString())) ? arr.get(1).toString() : "");
				// }
				// ѡ��c������
				// if (arr.size() > 2 &&
				// !TextUtils.isEmpty(arr.get(2).toString())) {
				// vote.put("option_c", arr.get(2).toString());
				vote.setOption_c((arr.size() > 2 && !TextUtils.isEmpty(arr.get(
						2).toString())) ? arr.get(2).toString() : "");
				// }
				// ѡ��d������
				// if (arr.size() > 3 &&
				// !TextUtils.isEmpty(arr.get(3).toString())) {
				// vote.put("option_d", arr.get(3).toString());
				vote.setOption_d((arr.size() > 3 && !TextUtils.isEmpty(arr.get(
						3).toString())) ? arr.get(3).toString() : "");
				// }
				// ѡ��e������
				// if (arr.size() > 4 &&
				// !TextUtils.isEmpty(arr.get(4).toString())) {
				// vote.put("option_e", arr.get(4).toString());
				vote.setOption_e((arr.size() > 4 && !TextUtils.isEmpty(arr.get(
						4).toString())) ? arr.get(4).toString() : "");
				// }
				// ѡ��f������
				// if (arr.size() > 5 &&
				// !TextUtils.isEmpty(arr.get(5).toString())) {
				// vote.put("option_f", arr.get(5).toString());
				vote.setOption_f(arr.size() > 5
						&& !TextUtils.isEmpty(arr.get(5).toString()) ? arr.get(
						5).toString() : "");
				// }
				// ѡ��g������
				// if (arr.size() > 6 &&
				// !TextUtils.isEmpty(arr.get(6).toString())) {
				// vote.put("option_g", arr.get(6).toString());
				vote.setOption_g((arr.size() > 6 && !TextUtils.isEmpty(arr.get(
						6).toString())) ? arr.get(6).toString() : "");
				// }
				// ѡ��h������
				// if (arr.size() > 7 &&
				// !TextUtils.isEmpty(arr.get(7).toString())) {
				// vote.put("option_h", arr.get(7).toString());
				vote.setOption_h((arr.size() > 7 && !TextUtils.isEmpty(arr.get(
						7).toString())) ? arr.get(7).toString() : "");
				// }
				// ѡ��i������
				// if (arr.size() > 8 &&
				// !TextUtils.isEmpty(arr.get(8).toString())) {
				// vote.put("option_i", arr.get(8).toString());
				vote.setOption_i((arr.size() > 8 && !TextUtils.isEmpty(arr.get(
						8).toString())) ? arr.get(8).toString() : "");
				// }
				// ѡ��j������
				// if (arr.size() > 9 &&
				// !TextUtils.isEmpty(arr.get(9).toString())) {
				// vote.put("option_j", arr.get(9).toString());
				vote.setOption_j((arr.size() > 9 && !TextUtils.isEmpty(arr.get(
						9).toString())) ? arr.get(9).toString() : "");
				// }
				String result = HttpUse.messagepost("QueAndAns", "saveVote",
						vote);
				// TODO
//				System.out.println("++++�鿴���������++" + result);
				Message msg = new Message();

				try {
					JSONObject jo = new JSONObject(result);
					boolean data = jo.getBoolean("result");
					if (data) {
						msg.what = 1;
					} else {
						msg.obj = jo.getString("message");
					}
				} catch (JSONException e) {
					// e.printStackTrace();
					msg.obj = e.getMessage();
				}

				handler.sendMessage(msg);

			}
		}.start();
	}
}