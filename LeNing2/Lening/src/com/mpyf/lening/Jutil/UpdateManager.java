package com.mpyf.lening.Jutil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager.OnActivityDestroyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.mpyf.lening.R;
import com.mpyf.lening.activity.activity.GateAcitvity;
import com.mpyf.lening.activity.activity.LoginActivity;
import com.mpyf.lening.activity.activity.MainActivity;
import com.mpyf.lening.interfaces.bean.Parame.Login;
import com.mpyf.lening.interfaces.bean.Parame.User;
import com.mpyf.lening.interfaces.bean.Result.VersionInfo;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;
public class UpdateManager {

	private Context mContext;
	private  VersionInfo versionInfo;
	
	private Dialog mDownloadDialog;
	private ProgressBar mProgress;
	private String mSavePath;//�����ļ��ı���·��
	
	private boolean cancelUpdate = false;//�Ƿ�ȡ������
	private Dialog dialog;
	/* ������ */
	private static final int DOWNLOAD = 1;
	/* ���ؽ��� */
	private static final int DOWNLOAD_FINISH = 2;
	/* ��¼���������� */
	private int progress;
	private String fileName;//�ļ�����
	
	private Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			// ��������
			case DOWNLOAD:
				// ���ý�����λ��
				mProgress.setProgress(progress);
				break;
			case DOWNLOAD_FINISH:
				// ��װ�ļ�
				installApk();
				break;
			default:
				break;
			}
		};
	};
	
	public UpdateManager(Context context, VersionInfo versionInfo){
		this.mContext = context;
		this.versionInfo = versionInfo;
	}
	
	protected void installApk() {


		File apkfile = new File(mSavePath, fileName);
		if (!apkfile.exists())
		{
			return;
		}
		// ͨ��Intent��װAPK�ļ�
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
		mContext.startActivity(i);
	
	}

	public void checkUpdate(){
		if(isUpdate()){
			showNoticeDialog();
		}else{
			login();
		}
	}
		//��¼
	private void login() {

		if(!Readsaved.read(mContext, "logined").equals("1")){
			mContext.startActivity(new Intent(mContext, GateAcitvity.class));
			((Activity) mContext).finish();
		}else if(Readsaved.read(mContext, "username")==null||Readsaved.read(mContext, "username").equals("")){
			mContext.startActivity(new Intent(mContext, LoginActivity.class));
			((Activity) mContext).finish();
		}else if(Readsaved.read(mContext, "off").equals("off")){
			mContext.startActivity(new Intent(mContext, LoginActivity.class));
			((Activity) mContext).finish();
		}
		else{

			final Handler handler=new Handler(){
				@Override
				public void handleMessage(Message msg) {
					
					if(msg.what==1){
						
						try {
							JSONObject jo=new JSONObject(msg.obj.toString());
							Setting.apiKey=jo.getString("key");
							Setting.currentUser=new User();
							
							Setting.currentUser.setDeptname(jo.getString("deptname"));
							Setting.currentUser.setEmail(jo.getString("email"));
							Setting.currentUser.setField(jo.getString("field"));
							Setting.currentUser.setKey(jo.getString("key"));
							Setting.currentUser.setMphone(jo.getString("mphone"));
							Setting.currentUser.setIntroduce(jo.getString("introduce"));
							Setting.currentUser.setNickname(jo.getString("nickname"));
							Setting.currentUser.setPk_user(jo.getInt("pk_user"));
							Setting.currentUser.setProfessional(jo.getString("professional"));
							Setting.currentUser.setSex(jo.getInt("sex"));
							Setting.currentUser.setTruename(jo.getString("truename"));
							Setting.currentUser.setUsername(jo.getString("username"));
							Setting.currentUser.setEducation(jo.getInt("education"));
							Setting.currentUser.setSoftTime(jo.getString("softTime"));
							
							
						} catch (JSONException e) {
							Diaoxian.showerror(mContext, e.getMessage());
						}
						
						Timer timer=new Timer();
						TimerTask task=new TimerTask() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								Intent intent=new Intent(mContext,MainActivity.class);
								mContext.startActivity(intent);
								Writesaved.write(mContext, "off" ,"on");
								((Activity) mContext).finish();
							}
						};
						timer.schedule(task, 1500);
						
					}else{
						Diaoxian.showerror(mContext, msg.obj.toString());
						Intent intent=new Intent(mContext,LoginActivity.class);
						mContext.startActivity(intent);
						((Activity) mContext).finish();
					}
				}
			};
			
			new Thread(){
				@Override
				public void run() {
					Setting.apiKey="6C9FBC3C-03BF-4329-BBE7-EFAC4C404253";
					Login login=new Login();
					login.setUsername(Readsaved.read(mContext, "username"));
					login.setPassword(Readsaved.read(mContext, "password"));
					
					Message msg=new Message();
					
					try {
						JSONObject jo=new JSONObject(HttpUse.messagepost("Account", "login", login));
						if(jo.getBoolean("result")){
							msg.what=1;
							msg.obj=jo.getString("data");
						}else{
							msg.obj=jo.getString("message");
						}
						
					} catch (JSONException e) {
						msg.obj=e.getMessage();
					}
					
					handler.sendMessage(msg);
					
				};
			}.start();
		
		}
		
	
	}
	
	public void dismissDialog(){
		if(dialog != null && dialog.isShowing()){
			dialog.dismiss();
		}
		if(mDownloadDialog != null && mDownloadDialog.isShowing()){
			mDownloadDialog.dismiss();
		}
	}

	private void showNoticeDialog() {

		dialog=MyDialog.MyDialogShow(mContext, R.layout.pop_version, 0.8f);
		
//		TextView tv_version_main=(TextView) dialog.findViewById(R.id.tv_version_main);
		Button bt_version_quit=(Button) dialog.findViewById(R.id.bt_version_quit);
		Button bt_version_download=(Button) dialog.findViewById(R.id.bt_version_download);
		
		bt_version_quit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				login();
			}
		});
		
		bt_version_download.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 
				dialog.dismiss();
				showDownloadDialog();
			}
		});
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}

	protected void showDownloadDialog() {

		// ����������ضԻ���
				AlertDialog.Builder builder = new Builder(mContext);
				builder.setTitle("���ڸ���");
				// �����ضԻ������ӽ�����
				final LayoutInflater inflater = LayoutInflater.from(mContext);
				View v = inflater.inflate(R.layout.softupdate_progress, null);
				mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
				builder.setView(v);
				
				builder.setNegativeButton("ȡ������", new android.content.DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int arg1) {

						dialog.dismiss();
						cancelUpdate = true;
						login();
					}

				});
					
				 mDownloadDialog = builder.create();
				mDownloadDialog.setCancelable(false);
				mDownloadDialog.show();
					downloadApk();//����apk
	}

	private void downloadApk() {

		new downloadApkThread().start();
	}

	
	private class downloadApkThread extends Thread{
		

		@Override
		public void run() {
			
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				String sdpath = Environment.getExternalStorageDirectory() + "/";
				mSavePath = sdpath + "download";
				try {
					URL url = new URL(versionInfo.downurl);
					
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.connect();
					// ��ȡ�ļ��Ĵ�С
					int length = conn.getContentLength();
					InputStream is = conn.getInputStream();
					File file = new File(mSavePath);
					
					if (!file.exists())
					{
						file.mkdir();
					}
					
					fileName = "����"+versionInfo.versionName;
					File apkFile = new File(mSavePath,fileName);
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
				 	// ����
					byte buf[] = new byte[1024];
					
					do
					{
						int numread = is.read(buf);
						count += numread;
						// ���������λ��
						progress = (int) (((float) count / length) * 100);
						// ���½���
						mHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0)
						{
							// �������
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						// д���ļ�
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// ���ȡ����ֹͣ����.
					fos.close();
					is.close();
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			mDownloadDialog.dismiss();
		}
	}
	/**
	 * @return ����Ƿ��и��°汾
	 */
	public boolean isUpdate() {
		 PackageManager packageManager =mContext.getPackageManager();
		 int newVersionCode = 0 ;
		 int curVersionCode = 1;
		  try {
			PackageInfo packInfo = packageManager.getPackageInfo("com.mpyf.lening",0);
			curVersionCode = packInfo.versionCode;
			if(versionInfo.versioncode!=null){
				
				newVersionCode = Integer.parseInt(versionInfo.versioncode);
			}
			
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			new Diaoxian().showerror(mContext,e.getMessage());
		}
		
		if(newVersionCode>curVersionCode){
			return true;
		}else{
			return false;
		}
		 
	}
	
}
