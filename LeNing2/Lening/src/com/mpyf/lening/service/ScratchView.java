package com.mpyf.lening.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class ScratchView extends View {

	public ScratchView(Context context) {
		super(context);
		init();
	}

	public ScratchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		// TODO Auto-generated constructor stub
	}

	public ScratchView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		// TODO Auto-generated constructor stub
	}

	private Canvas mCanvas = null;
	private Path mPath = null;
	private Paint mPaint = null;

	// 定义画布的宽和高
	private int screenWidth = 580;
	private int screenHeight = 270;
	private Bitmap bitmap = null;
	private Bitmap bitmap3;

	private void init() {
		// TODO Auto-generated method stub
		mPath = new Path();
		bitmap = Bitmap.createBitmap(screenWidth, screenHeight,
				Config.ARGB_8888);

		// 对mPaint的设置
		mPaint = new Paint();
		mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		mPaint.setAntiAlias(true);
		mCanvas = new Canvas();
		mPaint.setDither(true);
		// 设置画笔为空心
		mPaint.setStyle(Style.STROKE);
		// 设置线宽，即每次擦除的宽度
		mPaint.setStrokeWidth(20);
		mPaint.setStrokeCap(Cap.ROUND);
		mPaint.setStrokeJoin(Join.ROUND);
		// 设置图形重叠时的处理方式，一共有16种方式，有兴趣可自己查阅
		mPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		mPaint.setAlpha(0);

		mCanvas = new Canvas(bitmap);

		mCanvas.drawColor(Color.parseColor("#c0c0c0"));
		setBitmapText();

	}

	private void setBitmapText() {
		Paint paint = new Paint();
		paint.setTextSize(20);
		paint.setColor(Color.parseColor("#9f9fa0"));
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		paint.setAntiAlias(true);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setFakeBoldText(true);

		Canvas canvas = new Canvas(bitmap);
		canvas.drawColor(Color.alpha(0));
		canvas.rotate(-20);
		// 遍历绘制文字
		for (int i = 0; i < screenWidth + 50; i += 100) {
			for (int j = 0; j < screenHeight + 50; j += 30) {
				canvas.drawText("刮奖区", i, j, paint);
			}
		}

		// 设置中的奖项
		setScratchBackground(bitmap);
	}

	// 接收后台传来的文字，即中奖或者未中奖的文字
	public void setScratchBackground(Bitmap bitmap2) {
		// TODO Auto-generated method stub
		Paint paint = new Paint();
		bitmap3 = Bitmap.createBitmap(screenWidth, screenHeight,
				Config.ARGB_8888);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		paint.setAntiAlias(true);

		Canvas canvas = new Canvas(bitmap3);
		canvas.drawBitmap(bitmap2, 0, 0, paint);
		setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap3));

	}

	public void reset() {
		init();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		mCanvas.drawPath(mPath, mPaint);
		canvas.drawBitmap(bitmap, 0, 0, null);
	}

	int x = 0;
	int y = 0;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int action = event.getAction();
		int currX = (int) event.getX();
		int currY = (int) event.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// 开始刮，设置监听
			onStartListener.startListener();
			mPath.reset();
			x = currX;
			y = currY;
			mPath.moveTo(x, y);

			break;
		case MotionEvent.ACTION_MOVE:
			mPath.quadTo(x, y, currX, currY);
			x = currX;
			y = currY;
			postInvalidate();

			break;
		case MotionEvent.ACTION_UP:
			System.out.println("手指抬起----------");
			new Thread(mRunnable).start();

		case MotionEvent.ACTION_CANCEL:
			mPath.reset();

			break;
		}
		return true;
	}

	private Runnable mRunnable = new Runnable() {
		private int[] mPixels;

		@Override
		public void run() {
			float wipeArea = 0;
			float totalArea = screenWidth * screenHeight;
			Bitmap mBitmap = bitmap;
			mPixels = new int[screenWidth * screenHeight];
			/**
			 * 拿到所有的像素信息
			 */
			mBitmap.getPixels(mPixels, 0, screenWidth, 0, 0, screenWidth,
					screenHeight);
			/**
			 * 遍历统计擦除的区域
			 */
			for (int i = 0; i < screenWidth; i++) {
				for (int j = 0; j < screenHeight; j++) {
					int index = i + j * screenWidth;
					if (mPixels[index] == 0) {
						wipeArea++;
					}
				}
			}

			/**
			 * 根据所占百分比，进行一些操作
			 */
			if (wipeArea > 0 && totalArea > 0) {
				int percent = (int) (wipeArea * 100 / totalArea);
				System.out.println("测试一下wipeArea=" + wipeArea + "\ntotalArea="
						+ totalArea + "\npercent=" + percent);
				/**
				 * 设置达到多少百分比的时候，弹窗提醒是否中奖此处设置为50
				 */
				if (percent > 10) {
					System.out.println("测试一下是否监听到了");
					/**
					 * 刮开奖以后的操作，此处在子线程toast，可能会发生线程阻塞，只为测试使用
					 */
					onShowListener.showListener();
					/*
					 * Looper.prepare(); Toast.makeText(getContext(),"已刮开" +
					 * percent + "%", Toast.LENGTH_LONG).show(); Looper.loop();
					 */
				}
			}
		}
	};

	/**
	 * 刮开监听
	 * 
	 */
	public interface OnShowListener {
		void showListener();
	}

	OnShowListener onShowListener = null;

	public void addShowListener(OnShowListener onShowListener) {
		this.onShowListener = onShowListener;
	}

	/**
	 * 开始刮监听
	 * 
	 */
	public interface OnStartListener {
		void startListener();
	}

	OnStartListener onStartListener = null;

	public void addStartListener(OnStartListener onStartListener) {
		this.onStartListener = onStartListener;
	}

}
