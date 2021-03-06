package com.mpyf.lening.activity.activity;

import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.AsyncBitmapLoader;
import com.mpyf.lening.interfaces.http.Setting;

public class MainActivity extends FragmentActivity {

	private LinearLayout ll_main_zhuye, ll_main_me;
	private ImageView iv_main_zhuye, iv_main_me;
	private TextView tv_main_zhuye, tv_main_me;
	private View fg_main_zhuye, fg_main_me;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// new Xiaoxibeijing().changetop(MainActivity.this, R.color.main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		init();
		addlistener();

	}

	// @Override
	// protected void onResume() {
	// // TODO Auto-generated method stub
	// super.onResume();
	// //这里我简单暴力的直接设置上去 如果 你想更理想就加一个flag值 当更换头像操作的时候在进行设置
	// AsyncBitmapLoader.setRoundImage(iv_me_photo,
	// Setting.currentUser.getPhoto32());
	// }
	private void init() {
		ll_main_zhuye = (LinearLayout) findViewById(R.id.ll_main_zhuye);
		ll_main_me = (LinearLayout) findViewById(R.id.ll_main_me);

		iv_main_zhuye = (ImageView) findViewById(R.id.iv_main_zhuye);
		iv_main_me = (ImageView) findViewById(R.id.iv_main_me);

		tv_main_zhuye = (TextView) findViewById(R.id.tv_main_zhuye);
		tv_main_me = (TextView) findViewById(R.id.tv_main_me);

		fg_main_zhuye = findViewById(R.id.fg_main_zhuye);
		fg_main_me = findViewById(R.id.fg_main_me);
		fg_main_me.setVisibility(View.GONE);

	}

	private void addlistener() {

		ll_main_zhuye.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				iv_main_zhuye.setImageResource(R.drawable.btn_home_s);
				tv_main_zhuye.setTextColor(getResources()
						.getColor(R.color.main));
				iv_main_me.setImageResource(R.drawable.btn_me_s);
				tv_main_me.setTextColor(getResources().getColor(R.color.ciyao));
				fg_main_zhuye.setVisibility(View.VISIBLE);
				fg_main_me.setVisibility(View.GONE);
			}
		});

		ll_main_me.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				iv_main_me.setImageResource(R.drawable.btn_me_n);
				tv_main_me.setTextColor(getResources().getColor(R.color.main));
				iv_main_zhuye.setImageResource(R.drawable.btn_home_n);
				tv_main_zhuye.setTextColor(getResources().getColor(
						R.color.ciyao));
				fg_main_zhuye.setVisibility(View.GONE);
				fg_main_me.setVisibility(View.VISIBLE);

			}
		});
	}

	/**
	 * 菜单、返回键响应
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click(); // 调用双击退出函数
		}
		return false;
	}

	/**
	 * 双击退出函数
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出乐柠", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		} else {
			finish();
			System.exit(0);
		}
	}

}
