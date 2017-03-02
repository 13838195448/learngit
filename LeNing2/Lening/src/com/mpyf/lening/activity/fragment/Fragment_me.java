package com.mpyf.lening.activity.fragment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

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
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.AsyncBitmapLoader;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.Quanjubianliang;
import com.mpyf.lening.Jutil.Roundbitmap;
import com.mpyf.lening.activity.activity.MessageActivity;
import com.mpyf.lening.activity.activity.Moneyjilu;
import com.mpyf.lening.activity.activity.MyExamActivity;
import com.mpyf.lening.activity.activity.MyOrderActivity;
import com.mpyf.lening.activity.activity.MyPeixunActivity;
import com.mpyf.lening.activity.activity.MycommentActivity;
import com.mpyf.lening.activity.activity.MycourseActivity;
import com.mpyf.lening.activity.activity.MycoursedayiActivity;
import com.mpyf.lening.activity.activity.MynoteActivity;
import com.mpyf.lening.activity.activity.MyqaActivity;
import com.mpyf.lening.activity.activity.MyrenzhengActivity;
import com.mpyf.lening.activity.activity.SettingActivity;
import com.mpyf.lening.interfaces.bean.Parame.FileUpload;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class Fragment_me extends Fragment implements OnClickListener {

	private RelativeLayout rl_me_bgphoto, rl_me_top;
	private LinearLayout ll_me_meony, ll_me_yiqiandao, ll_me_kcpx,
			ll_me_mycourse, ll_me_myorder, ll_me_qa, ll_me_mydayi,
			ll_me_mynote, ll_me_comment, ll_me_le, ll_me_gold;
	private ImageView iv_me_news, iv_me_setting, iv_me_photo, iv_me_bgphoto;
	private TextView tv_me_name, tv_me_peixun, tv_me_myrenzheng,
			tv_me_yiqiandao, tv_me_le, tv_me_gold, tv_me_myexam;
	private File tempFile;
	private Uri uri;
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1,
			PHOTO_REQUEST_GALLERY = 2, PHOTO_REQUEST_CUT = 3;// 拍照// 从相册中选择// 结果
	public static final String fileName = "Mi_"
			+ DateFormat.format("yyyyMMddHHmmss", new Date()).toString()
			+ ".jpg", filePath = Environment.getExternalStorageDirectory()
			+ "/DCIM/";
	public static final Uri fileUri = Uri
			.fromFile(new File(filePath + fileName));

	Bitmap bitmap;
	private DisplayImageOptions options;

	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_me, null);
		init(view);
		duang();
		addlistener();
		return view;
	};

	@Override
	public void onStart() {
		super.onStart();
		duang();
		ischecked();

	}

	private void init(View view) {
		rl_me_bgphoto = (RelativeLayout) view.findViewById(R.id.rl_me_bgphoto);
		rl_me_top = (RelativeLayout) view.findViewById(R.id.rl_me_top);
		ll_me_meony = (LinearLayout) view.findViewById(R.id.ll_me_meony);
		ll_me_yiqiandao = (LinearLayout) view
				.findViewById(R.id.ll_me_yiqiandao);
		ll_me_kcpx = (LinearLayout) view.findViewById(R.id.ll_me_kcpx);
		ll_me_mynote = (LinearLayout) view.findViewById(R.id.ll_me_mynote);
		ll_me_comment = (LinearLayout) view.findViewById(R.id.ll_me_comment);

		ll_me_le = (LinearLayout) view.findViewById(R.id.ll_me_le);
		ll_me_gold = (LinearLayout) view.findViewById(R.id.ll_me_gold);

		iv_me_news = (ImageView) view.findViewById(R.id.iv_me_news);
		iv_me_setting = (ImageView) view.findViewById(R.id.iv_me_setting);
		iv_me_photo = (ImageView) view.findViewById(R.id.iv_me_photo);
		iv_me_bgphoto = (ImageView) view.findViewById(R.id.iv_me_bgphoto);
		tv_me_name = (TextView) view.findViewById(R.id.tv_me_name);
		tv_me_myexam = (TextView) view.findViewById(R.id.tv_me_myexam);
		tv_me_peixun = (TextView) view.findViewById(R.id.tv_me_peixun);
		tv_me_myrenzheng = (TextView) view.findViewById(R.id.tv_me_myrenzheng);
		tv_me_yiqiandao = (TextView) view.findViewById(R.id.tv_me_yiqiandao);
		tv_me_le = (TextView) view.findViewById(R.id.tv_me_le);
		tv_me_gold = (TextView) view.findViewById(R.id.tv_me_gold);
		ll_me_mycourse = (LinearLayout) view.findViewById(R.id.ll_me_mycourse);
		// 我的订单
		ll_me_myorder = (LinearLayout) view.findViewById(R.id.ll_me_myorder);
		ll_me_mydayi = (LinearLayout) view.findViewById(R.id.ll_me_mydayi);
		ll_me_qa = (LinearLayout) view.findViewById(R.id.ll_me_qa);

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.icon_defualt)
				.showImageOnFail(R.drawable.icon_defualt)
				.showImageForEmptyUri(R.drawable.icon_defualt)
				.cacheInMemory(true).cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.ALPHA_8).build();
	}

	private void duang() {
		ll_me_meony.getBackground().setAlpha(75);
		ll_me_yiqiandao.getBackground().setAlpha(75);
		ll_me_kcpx.getBackground().setAlpha(75);
		rl_me_top.getBackground().setAlpha(75);

		iv_me_photo.setImageResource(R.drawable.icon_defualt);
		iv_me_photo.setImageBitmap(Roundbitmap.toRoundBitmap(iv_me_photo));
		tv_me_name.setText(Setting.currentUser.getNickname());
		tv_me_le.setText(Setting.les + "");

		AsyncBitmapLoader.setRoundImage(iv_me_photo, Setting.apiUrl
				+ "new-pages/PersonalPhoto/" + Setting.currentUser.getPk_user()
				+ ".jpg");
		// ImageLoader imageLoader = ImageLoader.getInstance();
		// imageLoader.displayImage(Setting.apiUrl+"new-pages/PersonalPhoto/"+Setting.currentUser.getPk_user()+".jpg",
		// iv_me_photo,options);

		AsyncBitmapLoader.setmohuImage(iv_me_bgphoto, Setting.apiUrl
				+ "new-pages/PersonalPhoto/" + Setting.currentUser.getPk_user()
				+ ".jpg");

		ischecked();
		getgoldle();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		AsyncBitmapLoader.setmohuImage(iv_me_bgphoto, Setting.apiUrl
				+ "new-pages/PersonalPhoto/" + Setting.currentUser.getPk_user()
				+ ".jpg");
		iv_me_photo.setImageBitmap(bitmap);
		iv_me_photo.setImageBitmap(Roundbitmap.toRoundBitmap(iv_me_photo));
	}

	private void addlistener() {
		iv_me_news.setOnClickListener(this);
		iv_me_photo.setOnClickListener(this);
		ll_me_meony.setOnClickListener(this);
		ll_me_mynote.setOnClickListener(this);
		iv_me_setting.setOnClickListener(this);
		ll_me_mycourse.setOnClickListener(this);
		// 我的订单
		ll_me_myorder.setOnClickListener(this);
		ll_me_mydayi.setOnClickListener(this);
		ll_me_comment.setOnClickListener(this);
		tv_me_peixun.setOnClickListener(this);
		tv_me_myrenzheng.setOnClickListener(this);
		ll_me_le.setOnClickListener(this);
		ll_me_gold.setOnClickListener(this);
		ll_me_qa.setOnClickListener(this);
		tv_me_myexam.setOnClickListener(this);
		ll_me_yiqiandao.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.ll_me_le:
			Intent intentl = new Intent(getActivity(), Moneyjilu.class);
			intentl.putExtra("money", "le");
			startActivity(intentl);
			break;
		case R.id.iv_me_photo:
			changeimag();
			break;
		case R.id.ll_me_gold:
			Intent intentg = new Intent(getActivity(), Moneyjilu.class);
			intentg.putExtra("money", "gold");
			startActivity(intentg);
			break;
		case R.id.ll_me_mynote:
			startActivity(new Intent(getActivity(), MynoteActivity.class));
			break;
		case R.id.iv_me_news:
			startActivity(new Intent(getActivity(), MessageActivity.class));
			break;
		case R.id.iv_me_setting:
			startActivity(new Intent(getActivity(), SettingActivity.class));
			break;
		case R.id.ll_me_mycourse:
			startActivity(new Intent(getActivity(), MycourseActivity.class));
			break;
		// 跳到我得订单页面
		case R.id.ll_me_myorder:
			startActivity(new Intent(getActivity(), MyOrderActivity.class));
			break;
		case R.id.ll_me_qa:
			startActivity(new Intent(getActivity(), MyqaActivity.class));
			break;
		case R.id.ll_me_mydayi:
			startActivity(new Intent(getActivity(), MycoursedayiActivity.class));
			break;
		case R.id.ll_me_comment:
			startActivity(new Intent(getActivity(), MycommentActivity.class));
			break;
		case R.id.tv_me_myexam:
			startActivity(new Intent(getActivity(), MyExamActivity.class));
			break;
		case R.id.tv_me_myrenzheng:
			startActivity(new Intent(getActivity(), MyrenzhengActivity.class));
			break;
		case R.id.tv_me_peixun:
			startActivity(new Intent(getActivity(), MyPeixunActivity.class));
			break;
		case R.id.ll_me_yiqiandao:
			// Toast.makeText(getActivity(), "点击了", Toast.LENGTH_SHORT).show();
			ischecked();
			if (Quanjubianliang.qiandao.equals("未签到")) {
				getCoin();
			}
			break;
		default:
			break;
		}
	}

	private void getCoin() {

		final Handler handle = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						JSONObject jo = new JSONObject(msg.obj.toString());
						getgoldle();
						Diaoxian.showerror(getActivity(), "签到成功! " + "领取乐币×"
								+ jo.getInt("num"));
						Quanjubianliang.qiandao = "已签到";
						tv_me_yiqiandao.setText(Quanjubianliang.qiandao);

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				Message msg = new Message();
				try {

					String result = HttpUse
							.messageget("Account", "signIn", map);

					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = jo.getString("data");
					} else {
						msg.obj = jo.getString("message");
					}
				} catch (Exception e) {
					msg.obj = e.getMessage();
				}
				handle.sendMessage(msg);
			};
		}.start();
	}

	// 判断是否签到
	private void ischecked() {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub

				if (msg.what == 1) {
					Quanjubianliang.qiandao = "未签到";
					// tv_me_yiqiandao.setText("未签到");
				} else {
					Quanjubianliang.qiandao = "已签到";
					// tv_me_yiqiandao.setText("已签到");
				}

				tv_me_yiqiandao.setText(Quanjubianliang.qiandao);

			}
		};

		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				String result = HttpUse.messageget("Account", "isSignIn", map);
				Message msg = new Message();
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
					}
					handler.sendMessage(msg);
				} catch (JSONException e) {
				}
			};
		}.start();
	}

	private void getgoldle() {

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub

				if (msg.what == 1) {
					JSONObject jo;
					try {
						jo = new JSONObject(msg.obj.toString());
						tv_me_le.setText(jo.getString("class_coins"));
						tv_me_gold.setText(jo.getString("gold_coins"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
				String result = HttpUse.messageget("Account", "myGoldClass",
						map);
				Message msg = new Message();
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = jo.getString("data");
					} else {
						msg.obj = jo.getString("message");
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					msg.obj = e.getMessage();
				}
				handler.sendMessage(msg);
			};
		}.start();

	}

	private void changeimag() {

		final Dialog dialog = MyDialog.MyDialogShow(getActivity(),
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

		/*
		 * bt_photo_paizhao.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) {
		 * 
		 * photo(); } });
		 */

		bt_photo_xiangce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent(Intent.ACTION_PICK);
				intent2.setType("image/*");
				startActivityForResult(intent2, 2);
				dialog.dismiss();
			}
		});

		bt_photo_quxiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.show();

	}

	public void photo() {
		// TODO Auto-generated method stub
		// 将File对象转换为Uri并启动照相程序
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // 照相
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // 指定图片输出地址

		startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO); // 启动照相
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != getActivity().RESULT_OK) {
			// finish();
			return;
		}

		switch (requestCode) {
		case PHOTO_REQUEST_TAKEPHOTO:// 拍照

			uri = fileUri;
			try {
				bitmap = BitmapFactory.decodeStream(getActivity()
						.getContentResolver().openInputStream(uri));
				tempFile = new File(uri.getPath());
				String type = ".jpg";

				bitmap = comp(bitmap);
				byte[] photoData = Bitmap2Bytes(bitmap);

				upphoto(tempFile.getName(), type, photoData);

			} catch (Exception e) {
				Diaoxian.showerror(getActivity(), e.getMessage());
			}

			break;
		case PHOTO_REQUEST_GALLERY:// 从相册中选择
			if (data != null) {
				uri = data.getData();
				// Bitmap bitmap = data.getParcelableExtra("data");
				try {
					bitmap = BitmapFactory.decodeStream(getActivity()
							.getContentResolver().openInputStream(uri));
					tempFile = new File(uri.getPath());
					String type = ".jpg";
					bitmap = comp(bitmap);
					byte[] photoDate = Bitmap2Bytes(bitmap);
					upphoto(tempFile.getName(), type, photoDate);

				} catch (Exception e) {
					Diaoxian.showerror(getActivity(), e.getMessage());
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

	private void upphoto(final String name, final String type,
			final byte[] photodata) {

		final Dialog dialog = MyDialog.MyDialogloading(getActivity());
		dialog.show();

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				dialog.dismiss();

				if (msg.what == 1) {
					Diaoxian.showerror(getActivity(), "修改头像成功");

					iv_me_photo.setImageBitmap(bitmap);
					iv_me_photo.setImageBitmap(Roundbitmap
							.toRoundBitmap(iv_me_photo));
					// iv_me_bgphoto.setImageDrawable(Mohuphoto.BoxBlurFilter(bitmap));
					iv_me_bgphoto.setImageBitmap(bitmap);

				} else {
					Diaoxian.showerror(getActivity(), "网络状态不佳");
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
							"uploadPersonalPhoto", fileUpload);
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
}
