package com.mpyf.lening.activity.activity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.CommonUtils;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.MyDialog;
import com.mpyf.lening.Jutil.Xiaoxibeijing;
import com.mpyf.lening.activity.activity.FatieActivity.GridAdapter.ViewHolder;
import com.mpyf.lening.activity.activity.RepayQueActivity.GridAdapter;
import com.mpyf.lening.interfaces.bean.Parame.Answer;
import com.mpyf.lening.interfaces.bean.Result.Bimp;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;

public class RepayQueActivity extends Activity {

	private LinearLayout ll_addnote_back;
	private TextView tv_addnote_title,
	tv_addnote_ok;
	private LinearLayout ll_addnote_top;
	private EditText et_addnote_context;
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1,
			PHOTO_REQUEST_GALLERY = 2, PHOTO_REQUEST_CUT = 3;// ����// �������ѡ��// ���
	public static final String fileName = "Mi_"
			+ DateFormat.format("yyyyMMddHHmmss", new Date()).toString()
			+ ".jpg", filePath = Environment.getExternalStorageDirectory()
			+ "/DCIM/";
	public static final Uri fileUri = Uri
			.fromFile(new File(filePath + fileName));
	private File tempFile;
	private Uri uri;
	Bitmap bitmap;
	private GridView gridView1;
	private GridAdapter gridAdapter;
	private Handler hand;
	private Dialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//new Xiaoxibeijing().changetop(this, R.color.main);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_addnote);
		Bimp.tempSelectBitmap.clear();
		init();
		showinfo();
		addlistener();
	}
	private void init(){
		ll_addnote_back=(LinearLayout) findViewById(R.id.ll_addnote_back);
		tv_addnote_title=(TextView) findViewById(R.id.tv_addnote_title);
		tv_addnote_ok=(TextView) findViewById(R.id.tv_addnote_ok);
		ll_addnote_top=(LinearLayout) findViewById(R.id.ll_addnote_top);
		et_addnote_context=(EditText) findViewById(R.id.et_addnote_context);
		gridView1 = (GridView) findViewById(R.id.gridView1);
		
	
		gridAdapter = new GridAdapter(RepayQueActivity.this);
		gridView1.setAdapter(gridAdapter);

		hand = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					gridAdapter.notifyDataSetChanged();
				}
			};
		};
	}
	
	private void showinfo(){
		tv_addnote_ok.setText("����");
		tv_addnote_title.setText("׫д�ش�");
		ll_addnote_top.setVisibility(View.GONE);
	}
	
	private void addlistener(){
		//ListenerServer.setfinish(this, ll_addnote_back);
		ll_addnote_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
				Bimp.tempSelectBitmap.clear();
			}
		});
		gridView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				if (arg2 == Bimp.tempSelectBitmap.size()) {

					sendImage();
				}
			}
		});
		
		tv_addnote_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(!CommonUtils .isFastDoubleClick()){
					
					if(TextUtils.isEmpty(et_addnote_context.getText())&&Bimp.tempSelectBitmap.size()==0){
						Toast.makeText(getApplicationContext(), "���ݲ���Ϊ��", 0).show();
					}else{
						faSong();
					}
				}else{
					Diaoxian.showerror(RepayQueActivity.this, "�벻Ҫ�ظ��ύ");
				}
			}
		});
	}
	private void faSong(){
		//dialog = MyDialog.MyDialogloading(RepayQueActivity.this);
		//dialog.show();
		final Handler handler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what==1) {
				//	dialog.dismiss();
					Diaoxian.showerror(RepayQueActivity.this, "�����ɹ�");
					finish();
				}else{
					Diaoxian.showerror(RepayQueActivity.this, msg.obj.toString());
				}
			}
		};
		
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Answer answer=new Answer();
				answer.setANS_CONTENT(et_addnote_context.getText().toString());
				answer.setANS_STATE(0);
				answer.setAnsTime("");
				answer.setBAD_NUM(0);
				answer.setGOOD_NUM(0);
				answer.setIs_child(0);
				answer.setIs_havechild(0);
				answer.setNickname(Setting.currentUser.getNickname());
				answer.setP_Nickname("");
				answer.setP_pk_Ans("");
				answer.setP_Pk_user(0);
				answer.setP_TrueName("");
				answer.setP_UserName("");
				answer.setPK_Ans("");
				answer.setPK_Que(getIntent().getStringExtra("id"));
				answer.setPk_user(Setting.currentUser.getPk_user());
				answer.setTrueName(Setting.currentUser.getTruename());
				answer.setUserName(Setting.currentUser.getUsername());
				
				int a = Bimp.tempSelectBitmap.size();
				answer.setPic_num(a);
				switch (a) {
				case 9:
					answer.setPic9(Bimp.tempSelectBitmap.get(8).photoData);
					
				case 8:
					answer.setPic8(Bimp.tempSelectBitmap.get(7).photoData);
					
				case 7:
					answer.setPic7(Bimp.tempSelectBitmap.get(6).photoData);
					
				case 6:
					answer.setPic6(Bimp.tempSelectBitmap.get(5).photoData);
					
				case 5:
					answer.setPic5(Bimp.tempSelectBitmap.get(4).photoData);
					
				case 4:
					answer.setPic4(Bimp.tempSelectBitmap.get(3).photoData);
					
				case 3:
					answer.setPic3(Bimp.tempSelectBitmap.get(2).photoData);
					
				case 2:
					answer.setPic2(Bimp.tempSelectBitmap.get(1).photoData);
					
				case 1:
					answer.setPic1(Bimp.tempSelectBitmap.get(0).photoData);
					break;
				
				}
				
				
				
				
				Message msg=new Message();
				
				String result=HttpUse.messagepost("QueAndAns", "saveAnswer", answer);
				
				JSONObject jo;
				try {
					jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what=1;
						msg.obj=jo.getString("data");
					}else{
						msg.obj=jo.getString("message");
					}
				} catch (JSONException e) {
					msg.obj=e.getMessage();
				}
				handler.sendMessage(msg);
			}
		}.start();
	}
	
	protected void sendImage() {

		final Dialog dialog = MyDialog.MyDialogShow(RepayQueActivity.this,
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

		if (resultCode != RepayQueActivity.this.RESULT_OK) {
			// finish();
			return;
		}

		switch (requestCode) {
		case PHOTO_REQUEST_TAKEPHOTO:// ����

			uri = fileUri;
			try {
				bitmap = BitmapFactory.decodeStream(RepayQueActivity.this
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
				Diaoxian.showerror(RepayQueActivity.this, e.getMessage());
			}
			// }
			break;
		case PHOTO_REQUEST_GALLERY:// �������ѡ��
			if (data != null) {
				uri = data.getData();
				// Bitmap bitmap = data.getParcelableExtra("data");
				try {
					bitmap = BitmapFactory.decodeStream(RepayQueActivity.this
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
					Diaoxian.showerror(RepayQueActivity.this, e.getMessage());
				}

			}
			break;
		default:
			break;
		}

	}
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
	
	//ѹ��ͼƬ
	 private Bitmap compressImage(Bitmap image) {  
	        
         ByteArrayOutputStream baos = new ByteArrayOutputStream();  
         image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��  
         int options = 100;  
         while ( baos.toByteArray().length / 1024>100) {  //ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��         
             baos.reset();//����baos�����baos  
             image.compress(Bitmap.CompressFormat.JPEG, options, baos);//����ѹ��options%����ѹ��������ݴ�ŵ�baos��  
             options -= 10;//ÿ�ζ�����10  
         }  
         ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//��ѹ���������baos��ŵ�ByteArrayInputStream��  
         Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//��ByteArrayInputStream��������ͼƬ  
         return bitmap;  
     }  
	    
	    private Bitmap comp(Bitmap image) {  
	          
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();         
	        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
	        if( baos.toByteArray().length / 1024>1024) {//�ж����ͼƬ����1M,����ѹ������������ͼƬ��BitmapFactory.decodeStream��ʱ���    
	            baos.reset();//����baos�����baos  
	            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//����ѹ��50%����ѹ��������ݴ�ŵ�baos��  
	        }  
	        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());  
	        BitmapFactory.Options newOpts = new BitmapFactory.Options();  
	        //��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��  
	        newOpts.inJustDecodeBounds = true;  
	        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
	        newOpts.inJustDecodeBounds = false;  
	        int w = newOpts.outWidth;  
	        int h = newOpts.outHeight;  
	        //���������ֻ��Ƚ϶���800*480�ֱ��ʣ����ԸߺͿ���������Ϊ  
	        float hh = 800f;//�������ø߶�Ϊ800f  
	        float ww = 480f;//�������ÿ��Ϊ480f  
	        //���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��  
	        int be = 1;//be=1��ʾ������  
	        if (w > h && w > ww) {//�����ȴ�Ļ����ݿ�ȹ̶���С����  
	            be = (int) (newOpts.outWidth / ww);  
	        } else if (w < h && h > hh) {//����߶ȸߵĻ����ݿ�ȹ̶���С����  
	            be = (int) (newOpts.outHeight / hh);  
	        }  
	        if (be <= 0)  
	            be = 1;  
	        newOpts.inSampleSize = be;//�������ű���  
	        //���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��  
	        isBm = new ByteArrayInputStream(baos.toByteArray());  
	        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
	        return compressImage(bitmap);//ѹ���ñ�����С���ٽ�������ѹ��  
	    }  
}
