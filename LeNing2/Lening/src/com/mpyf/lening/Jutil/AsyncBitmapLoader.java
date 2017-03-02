package com.mpyf.lening.Jutil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
/**
 * �첽ͼƬ������
 */
public class AsyncBitmapLoader {
	/**
	 * �ڴ�ͼƬ��Ӧ�û���
	 */
	private static HashMap<String, SoftReference<Bitmap>> imageCache = null;
	/**
	 * ����ͼƬ����·��
	 */
	private static final String cacheLocation = "/mnt/sdcard/com.mpyf.weikecheng/";

	/**
	 * ���캯��
	 */
	public AsyncBitmapLoader() {
		imageCache = new HashMap<String, SoftReference<Bitmap>>();
	}

	/**
	 * �첽������Ƭ���� ������Ĵ������ �������Զ����baseAdapter��ֱ�ӵ���
	 * com.zdsoft.blp.JUnit.AsyncBitmapLoader.setImage(imageView,imageurl);
	 * 
	 * @param iv
	 * @param url
	 */
	public static void setImage(ImageView imageView, String ImageUrl) {
		AsyncBitmapLoader ab = new AsyncBitmapLoader();

		ab.loadBitmap(0.4f, imageView, ImageUrl, new ImageCallBack() {
			@Override
			public void imageLoad(ImageView imageView, Bitmap bitmap) {
				imageView.setImageBitmap(bitmap);
			}
		});
	}

	public static void sethoneImage(ImageView imageView, String ImageUrl) {
		AsyncBitmapLoader ab = new AsyncBitmapLoader();

		ab.loadBitmap(1f, imageView, ImageUrl, new ImageCallBack() {
			@Override
			public void imageLoad(ImageView imageView, Bitmap bitmap) {
				imageView.setImageBitmap(bitmap);
			}
		});
	}

	public static void setRoundImage(ImageView imageView, String ImageUrl) {
		AsyncBitmapLoader ab = new AsyncBitmapLoader();

		ab.loadBitmap(1f, imageView, ImageUrl, new ImageCallBack() {
			@Override
			public void imageLoad(ImageView imageView, Bitmap bitmap) {
				imageView.setImageBitmap(bitmap);
				imageView.setImageBitmap(Roundbitmap.toRoundBitmap(imageView));
			}
		});
	}
	
	public static void setmohuImage(ImageView imageView, String ImageUrl) {
		AsyncBitmapLoader ab = new AsyncBitmapLoader();

		ab.loadBitmap(0.3f, imageView, ImageUrl, new ImageCallBack() {
			@Override
			public void imageLoad(ImageView imageView, Bitmap bitmap) {
				imageView.setImageDrawable(Mohuphoto.BoxBlurFilter(bitmap));
			}
		});
		
	}

	public Bitmap loadBitmap(final float max, final ImageView imageView,
			final String imageURL, final ImageCallBack imageCallBack) {
		/**
		 * ������ڴ滺���У��򷵻�Bitmap����
		 */
		if (imageCache.containsKey(imageURL)) {
			SoftReference<Bitmap> reference = imageCache.get(imageURL);
			Bitmap bitmap = reference.get();
			if (bitmap != null) {
				return bitmap;
			}
		}
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0) {
					imageCallBack.imageLoad(imageView, (Bitmap) msg.obj);
				}
			}
		};

		/** ������ڱ����ڴ滺���У�Ҳ���ڱ��أ���JVM���յ����������߳�����ͼƬ */
		new Thread() {
			@Override
			public void run() {
				try {
					Log.i("Thread", "----------------------------------------");

					BitmapFactory.Options opt = new BitmapFactory.Options();
					opt.inPreferredConfig = Bitmap.Config.ALPHA_8;
					opt.inPurgeable = true;
					opt.inInputShareable = true;
					InputStream bitmapIs = HttpUtils.getStreamFromURL(imageURL);

					Matrix matrix = new Matrix();
					Bitmap bitmap = null;
					try {
						bitmap = BitmapFactory
								.decodeStream(bitmapIs, null, opt);
					} catch (OutOfMemoryError e) {
						// ����OutOfMemoryError������ֱ�ӱ���
					}
					if (bitmap == null) {
						// ���ʵ����ʧ�� ����Ĭ�ϵ�Bitmap����
						return;
					}
					matrix.postScale(max, max); // ���Ϳ�Ŵ���С�ı���
					Bitmap resizeBmp = Bitmap
							.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
									bitmap.getHeight(), matrix, true);
					imageCache.put(imageURL, new SoftReference<Bitmap>(
							resizeBmp));
					Message msg = handler.obtainMessage(0, resizeBmp);
					handler.sendMessage(msg);
					File dir = new File(cacheLocation);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					File bitmapFile = new File(cacheLocation
							+ imageURL.substring(imageURL.lastIndexOf("/") + 1));
					if (!bitmapFile.exists()) {
						try {
							bitmapFile.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					FileOutputStream fos;

					fos = new FileOutputStream(bitmapFile);
					resizeBmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
		return null;
	}

	public interface ImageCallBack {
		public void imageLoad(ImageView imageView, Bitmap bitmap);
	}
	
}
