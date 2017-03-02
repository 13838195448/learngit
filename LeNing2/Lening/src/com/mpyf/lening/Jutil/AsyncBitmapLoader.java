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
 * 异步图片加载类
 */
public class AsyncBitmapLoader {
	/**
	 * 内存图片软应用缓存
	 */
	private static HashMap<String, SoftReference<Bitmap>> imageCache = null;
	/**
	 * 本地图片缓存路径
	 */
	private static final String cacheLocation = "/mnt/sdcard/com.mpyf.weikecheng/";

	/**
	 * 构造函数
	 */
	public AsyncBitmapLoader() {
		imageCache = new HashMap<String, SoftReference<Bitmap>>();
	}

	/**
	 * 异步加载照片方法 用下面的代码调用 可以在自定义的baseAdapter中直接调用
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
		 * 如果在内存缓存中，则返回Bitmap对象
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

		/** 如果不在本地内存缓存中，也不在本地（被JVM回收掉），则开启线程下载图片 */
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
						// 捕获OutOfMemoryError，避免直接崩溃
					}
					if (bitmap == null) {
						// 如果实例化失败 返回默认的Bitmap对象
						return;
					}
					matrix.postScale(max, max); // 长和宽放大缩小的比例
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
