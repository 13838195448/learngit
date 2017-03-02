package com.mpyf.lening.activity.activity;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnCompletionListener;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.MyVideoview;
import com.mpyf.lening.Jutil.Quanjubianliang;
import com.mpyf.lening.activity.adapter.Vpadapter;
import com.mpyf.lening.activity.fragment.Fragment_docomment;
import com.mpyf.lening.activity.fragment.Fragment_donote;
import com.mpyf.lening.activity.fragment.Fragment_doqa;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class StudyActivity extends FragmentActivity implements OnClickListener {

	private RelativeLayout rl_study_back, rl_study_note, rl_study_question,
			rl_study_comment;
	private LinearLayout ll_study_menu;
	private SeekBar sb_study_played;
	private TextView tv_study_title, tv_study_note, tv_study_question,
			tv_study_comment, tv_study_played, tv_study_total;
	private ImageView iv_study_fullscreen, iv_study_play, iv_study_note,
			iv_study_question, iv_study_comment;
	private MyVideoview vv_study;
	private ViewPager vp_study;
	private List<Fragment> list;
//	private MediaController mMediaController;

	private View view_study_vv;

	private String videourl = "";
	private int STUDY_LONG;

	private Timer timer;
	private Timer Mtimer;
	private String fen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
			return;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_study);
		Vitamio.isInitialized(this);
		init();
		setvp();
		showinfo();
		addlistener();

	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	private void init() {
		rl_study_back = (RelativeLayout) findViewById(R.id.rl_study_back);

		rl_study_note = (RelativeLayout) findViewById(R.id.rl_study_note);
		rl_study_question = (RelativeLayout) findViewById(R.id.rl_study_question);
		rl_study_comment = (RelativeLayout) findViewById(R.id.rl_study_comment);
		ll_study_menu = (LinearLayout) findViewById(R.id.ll_study_menu);

		sb_study_played = (SeekBar) findViewById(R.id.sb_study_played);
		tv_study_played = (TextView) findViewById(R.id.tv_study_played);
		tv_study_total = (TextView) findViewById(R.id.tv_study_total);

		tv_study_title = (TextView) findViewById(R.id.tv_study_title);

		tv_study_note = (TextView) findViewById(R.id.tv_study_note);
		tv_study_question = (TextView) findViewById(R.id.tv_study_question);
		tv_study_comment = (TextView) findViewById(R.id.tv_study_comment);

		iv_study_note = (ImageView) findViewById(R.id.iv_study_note);
		iv_study_question = (ImageView) findViewById(R.id.iv_study_question);
		iv_study_comment = (ImageView) findViewById(R.id.iv_study_comment);

		view_study_vv = findViewById(R.id.view_study_vv);

		iv_study_fullscreen = (ImageView) findViewById(R.id.iv_study_fullscreen);
		vv_study = (MyVideoview) findViewById(R.id.vv_study);
		iv_study_play = (ImageView) findViewById(R.id.iv_study_play);
		vp_study = (ViewPager) findViewById(R.id.vp_study);

	}

	private void showinfo() {

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						JSONObject jo = new JSONObject(msg.obj.toString());
						tv_study_title.setText(jo.getString("resName"));
						String url = jo.getString("resUrl").toString();
//						System.out.println("课件地址" + url);
						STUDY_LONG = jo.getInt("STUDY_LONG");
						videourl = Setting.apiUrl + url;
						// videoset();
					} catch (JSONException e) {
						Diaoxian.showerror(StudyActivity.this, e.getMessage());
					}
				} else {
					Diaoxian.showerror(StudyActivity.this, msg.obj.toString());
				}
			}
		};

		new Thread() {
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("courseId", Quanjubianliang.courseid);
				map.put("resId", getIntent().getStringExtra("resId"));
				Message msg = new Message();
				String result = HttpUse.messageget("CourseStudy", "studyRes",
						map);
//				System.out.println("课件学习==" + result);

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

	private void setvp() {
		list = new ArrayList<Fragment>();
		list.add(new Fragment_donote(Quanjubianliang.courseid, getIntent()
				.getStringExtra("resId")));
		list.add(new Fragment_doqa(Quanjubianliang.courseid, getIntent()
				.getStringExtra("resId")));
		list.add(new Fragment_docomment(Quanjubianliang.courseid, getIntent()
				.getStringExtra("resId")));

		Vpadapter adapter = new Vpadapter(getSupportFragmentManager(), list);
		vp_study.setAdapter(adapter);
	}

	private void videoset() {

		try {
			
			vv_study.setVideoPath(videourl);// 设置播放地址
			// vv_study.setMediaController(new MediaController(this));
			vv_study.setVideoQuality(MediaPlayer.VIDEOQUALITY_MEDIUM);
			vv_study.setVideoLayout(5, 0);
			
//			mMediaController = new MediaController(this);// 实例化控制器
//			mMediaController.show(5000);// 控制器显示5s后自动隐藏
//			vv_study.setMediaController(mMediaController);// 绑定控制器
			vv_study.requestFocus();// 取得焦点
			
			vv_study.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					showcontrl();

				}
			});

			vv_study.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

				@Override
				public void onPrepared(MediaPlayer mp) {
					mp.setPlaybackSpeed(1.0f);
					// vv_study.seekTo(STUDY_LONG);

					tv_study_total.setText(vv_study.getDuration() / 1000 / 60
							+ ":" + vv_study.getDuration() / 1000 % 60);

					vv_study.pause();
					// vv_study.suspend();
					startTimer();

				}
			});

			vv_study.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					Toast.makeText(StudyActivity.this, "视频播放结束",
							Toast.LENGTH_SHORT).show();
					rl_study_back.setVisibility(View.VISIBLE);
					tv_study_title.setVisibility(View.VISIBLE);
					iv_study_fullscreen.setVisibility(View.VISIBLE);
					tv_study_played.setVisibility(View.VISIBLE);
					sb_study_played.setVisibility(View.VISIBLE);
					tv_study_total.setVisibility(View.VISIBLE);
					iv_study_play.setVisibility(View.VISIBLE);
					iv_study_play.setImageResource(R.drawable.course_btn_start);

					Mtimer.cancel();
					Mtimer = null;
					// new Thread() {
					// @Override
					// public void run() {
					// Map<String, Object> map = new HashMap<String, Object>();
					// map.put("courseId", Quanjubianliang.courseid);
					// map.put("resId", getIntent()
					// .getStringExtra("resId"));
					// map.put("length", vv_study.getDuration() / 1000);
					//
					// HttpUse.messageget("CourseStudy", "saveStudyRes",
					// map);
					// }
					// }.start();
					iv_study_play.setVisibility(View.VISIBLE);
				}
			});

			

		} catch (Exception e) {
			Diaoxian.showerror(getApplicationContext(), e.getMessage());
		}

	}

	private void addlistener() {
		rl_study_back.setOnClickListener(this);
		rl_study_note.setOnClickListener(this);
		rl_study_question.setOnClickListener(this);
		rl_study_comment.setOnClickListener(this);
		iv_study_play.setOnClickListener(this);
		iv_study_fullscreen.setOnClickListener(this);
		view_study_vv.setOnClickListener(this);
		vp_study.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				if (arg0 == 2) {
					changevp(vp_study.getCurrentItem());
				}
			}
		});

		sb_study_played
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar arg0) {
						// TODO Auto-generated method stub
						vv_study.seekTo((long) (vv_study.getDuration()
								* sb_study_played.getProgress() / 100));
						String fen = vv_study.getCurrentPosition() / 1000 / 60
								+ "";
						String miao = vv_study.getCurrentPosition() / 1000 % 60
								+ "";

						miao = miao.length() == 1 ? "0" + miao : miao;
						tv_study_played.setText(fen + ":" + miao);

						final Handler handler = new Handler() {
							@Override
							public void handleMessage(Message msg) {
								rl_study_back.setVisibility(View.GONE);
								tv_study_title.setVisibility(View.GONE);
								iv_study_fullscreen.setVisibility(View.GONE);
								tv_study_played.setVisibility(View.GONE);
								sb_study_played.setVisibility(View.GONE);
								tv_study_total.setVisibility(View.GONE);
								iv_study_play.setVisibility(View.GONE);
							}
						};

						Timer timer = new Timer();
						TimerTask task = new TimerTask() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								Message msg = new Message();
								handler.sendMessage(msg);

							}
						};
						timer.schedule(task, 2000);

					}

					@Override
					public void onStartTrackingTouch(SeekBar arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProgressChanged(SeekBar arg0, int arg1,
							boolean arg2) {

					}
				});

	}

	private void changevp(int index) {

		TextView[] texts = { tv_study_note, tv_study_question, tv_study_comment };
		ImageView[] images = { iv_study_note, iv_study_question,
				iv_study_comment };

		for (int i = 0; i < texts.length; i++) {
			if (i == index) {
				texts[i].setTextColor(getResources().getColor(R.color.main));
				images[i].setBackgroundColor(getResources().getColor(
						R.color.main));
			} else {
				texts[i].setTextColor(getResources().getColor(R.color.zywz));
				images[i].setBackgroundColor(getResources().getColor(
						R.color.dise));

			}
		}

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.rl_study_back:
			finish();
			break;
		case R.id.rl_study_note:
			changevp(0);
			vp_study.setCurrentItem(0);
			break;
		case R.id.rl_study_question:
			changevp(1);
			vp_study.setCurrentItem(1);
			break;
		case R.id.rl_study_comment:
			changevp(2);
			vp_study.setCurrentItem(2);
			break;
		case R.id.iv_study_play:
			if (vv_study.getDuration() == -1) {
				rl_study_back.setVisibility(View.GONE);
				tv_study_title.setVisibility(View.GONE);
				iv_study_fullscreen.setVisibility(View.GONE);
				tv_study_played.setVisibility(View.GONE);
				sb_study_played.setVisibility(View.GONE);
				tv_study_total.setVisibility(View.GONE);
				iv_study_play.setVisibility(View.GONE);
				showJIndu();
				videoset();
				vv_study.start();

			} else {
				if (vv_study.isPlaying()) {
					Mtimer.cancel();
					vv_study.pause();
					iv_study_play.setImageResource(R.drawable.course_btn_start);
					Mtimer = null;
				} else {
					iv_study_play.setVisibility(View.GONE);
					showJIndu();
					vv_study.start();
					showcontrl();
				}
			}
			break;
		case R.id.view_study_vv:
			showcontrl();
			break;
		case R.id.iv_study_fullscreen:
			if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {// 横屏
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				this.getWindow().setFlags(
						WindowManager.LayoutParams.FLAG_FULLSCREEN,
						WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏

			} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {// 竖屏
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

			}

			break;
		default:
			break;
		}

	}

	/*
	 * 显示视频进度
	 */
	private void startTimer() {
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// 需要做的事:发送消息
				Message message = new Message();
				message.what = 0;
				handler.sendMessage(message);
			}
		}, 1000, 1000);// 延迟1秒执行，1秒执行1次
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			fen = vv_study.getCurrentPosition() / 1000 / 60 + "";
			String miao = vv_study.getCurrentPosition() / 1000 % 60 + "";

			miao = miao.length() == 1 ? "0" + miao : miao;
			tv_study_played.setText(fen + ":" + miao);
			sb_study_played
					.setProgress((int) (vv_study.getCurrentPosition() * 100 / vv_study
							.getDuration()));

		};
	};

	// 屏幕翻转时
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {// 横屏
			this.getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
			ll_study_menu.setVisibility(View.GONE);
			vp_study.setVisibility(View.GONE);
			iv_study_fullscreen.setImageResource(R.drawable.course_btn_reduce);
			vv_study.setVideoLayout(5, 0);
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {// 竖屏
			ll_study_menu.setVisibility(View.VISIBLE);
			vp_study.setVisibility(View.VISIBLE);
			iv_study_fullscreen.setImageResource(R.drawable.course_btn_unfold);
			vv_study.setVideoLayout(5, 0);
		}
	}

	private void showcontrl() {
		rl_study_back.setVisibility(View.VISIBLE);
		tv_study_title.setVisibility(View.VISIBLE);
		iv_study_fullscreen.setVisibility(View.VISIBLE);
		tv_study_played.setVisibility(View.VISIBLE);
		sb_study_played.setVisibility(View.VISIBLE);
		tv_study_total.setVisibility(View.VISIBLE);
		iv_study_play.setVisibility(View.VISIBLE);

		iv_study_play.setImageResource(R.drawable.course_btn_halt);

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (vv_study.isPlaying()) {
					rl_study_back.setVisibility(View.GONE);
					tv_study_title.setVisibility(View.GONE);
					iv_study_fullscreen.setVisibility(View.GONE);
					tv_study_played.setVisibility(View.GONE);
					sb_study_played.setVisibility(View.GONE);
					tv_study_total.setVisibility(View.GONE);
					iv_study_play.setVisibility(View.GONE);
				} else {
					return;
				}

			}
		};

		Timer timer = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = new Message();
				handler.sendMessage(msg);

			}
		};
		timer.schedule(task, 2000);

	}

	private void showJIndu() {
		// final Handler handler = new Handler(){
		// @Override
		// public void handleMessage(Message msg) {
		// Toast.makeText(getApplication(), fen+"", 1).show();
		// Log.i("fen", fen+"");
		// }
		// };
		Mtimer = new Timer();
		Mtimer.schedule(new TimerTask() {
			// vv_study.getCurrentPosition() / 1000 / 60
			@Override
			public void run() {

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("courseId", Quanjubianliang.courseid);
				map.put("resId", getIntent().getStringExtra("resId"));
				map.put("length", 1);

				String res = HttpUse.messageget("CourseStudy", "saveStudyRes",
						map);
				// Log.i("saveStudyRes", res);
				// Message message = new Message();
				// handler.sendEmptyMessage(0);
			}
		}, 60 * 1000, 60 * 1000);
	}

	@Override
	protected void onPause() {
		if (Mtimer != null) {
			Mtimer.cancel();
			Mtimer = null;
		}
		super.onPause();
	}
}
