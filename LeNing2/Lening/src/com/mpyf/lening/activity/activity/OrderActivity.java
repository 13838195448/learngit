package com.mpyf.lening.activity.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class OrderActivity extends Activity {

	private LinearLayout ll_order_back;
	private RelativeLayout rl_address;
	private TextView tv_shouhuo, tv_phone, tv_address, tv_order_title,
			tv_order_time, tv_order_amount, tv_order_num, tv_jin_le,
			tv_buytype;
	private ImageView iv_order_type, iv_order_goods, iv_order_tijiao;

	private DisplayImageOptions options;

	private String pk_adr;
	private String pk_goods;
	public String imgurl = "";
	public static int buyWay;
	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_order);

		init();
		showinfo();
		addlistener();
	}

	private void init() {
		// ���ؼ�
		ll_order_back = (LinearLayout) findViewById(R.id.ll_order_back);
		// �����ջ���ַ
		rl_address = (RelativeLayout) findViewById(R.id.rl_address);
		// �ջ���
		tv_shouhuo = (TextView) findViewById(R.id.tv_shouhuo);
		// �绰
		tv_phone = (TextView) findViewById(R.id.tv_phone);
		// �ջ���ַ
		tv_address = (TextView) findViewById(R.id.tv_address);
		// ��Ʒ������
		tv_order_title = (TextView) findViewById(R.id.tv_order_title);
		// ��Ʒ�۸�
		tv_order_amount = (TextView) findViewById(R.id.tv_order_amount);
		tv_order_num = (TextView) findViewById(R.id.tv_order_num);
		tv_jin_le = (TextView) findViewById(R.id.tv_jin_le);
		tv_buytype = (TextView) findViewById(R.id.tv_buytype);
		// ����ʱ��
		tv_order_time = (TextView) findViewById(R.id.tv_order_time);
		// ͼ��
		iv_order_type = (ImageView) findViewById(R.id.iv_order_type);
		// ��Ʒͼ��
		iv_order_goods = (ImageView) findViewById(R.id.iv_order_goods);
		// �ύ��ť
		iv_order_tijiao = (ImageView) findViewById(R.id.iv_order_tijiao);

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.defaultimage)
				.showImageOnFail(R.drawable.defaultimage)
				.showImageForEmptyUri(R.drawable.defaultimage)
				.cacheInMemory(true).cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.ALPHA_8).build();

		// TODO ��������
		tv_order_title.setText(getIntent().getStringExtra("name"));

		tv_order_amount.setText(getIntent().getIntExtra("amount", 0) + "");
		tv_order_time.setText("����ʱ��:" + getIntent().getStringExtra("time"));
		pk_goods = getIntent().getStringExtra("pk_goods");
		imgurl = getIntent().getStringExtra("imgurl");
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(imgurl, iv_order_goods, options);

		buyWay = getIntent().getIntExtra("buyWay", 0);

		if (buyWay == 1) {
			iv_order_type.setImageResource(R.drawable.me_icon_le);
			tv_jin_le.setText("�ֱ�");
			tv_buytype.setText("�ֱ�");
		} else if (buyWay == 2) {
			iv_order_type.setImageResource(R.drawable.me_icon_jin);
			tv_jin_le.setText("���");
			tv_buytype.setText("���");
		}

		tv_order_num.setText(getIntent().getIntExtra("amount", 0) + "");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		showinfo();
	}

	private void showinfo() {

		// ���������ȡĬ���ջ���ַ
		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					try {
						JSONObject js = (JSONObject) msg.obj;
						pk_adr = js.getString("pk_adr");
						tv_shouhuo.setText("�ջ���:" + js.getString("consignee"));
						tv_phone.setText(js.getString("mphone"));
						tv_address.setText("�ջ���ַ:" + js.getString("address"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					tv_shouhuo.setText("�ջ���:");
					tv_phone.setText("");
					tv_address.setText("�ջ���ַ:");
				}
			}
		};

		new Thread() {
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();

				Message msg = new Message();
				String result = HttpUse.messageget("QueAndAns",
						"defaultShippingAddress", map);
				// TODO ��ӡ���󵽵����� û�ж�data
				System.out.println("=====Ĭ���ջ���ַ====" + result);

				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = jo.getJSONObject("data");
					} else {
						msg.obj = jo.getString("message");
					}

				} catch (JSONException e) {
					msg.obj = e.getMessage();
				}

				handler.sendMessage(msg);
			};
		}.start();

	}

	private void addlistener() {
		// ���ؼ�
		ListenerServer.setfinish(OrderActivity.this, ll_order_back);
		// �ύ��ť�ĵ���¼�
		iv_order_tijiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String shouHuoRen = tv_shouhuo.getText().toString().trim();
				String phone = tv_phone.getText().toString().trim();
				String address = tv_address.getText().toString().trim();
				if (!TextUtils.isEmpty(shouHuoRen.replace("�ջ���:","").trim()) && !TextUtils.isEmpty(phone)
						&& !TextUtils.isEmpty(address.replace("�ջ���ַ:","").trim())) {

					showbuypopu();
				} else {
					Toast.makeText(OrderActivity.this, "�����Ƶ�ַ��Ϣ",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		rl_address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(OrderActivity.this,
						YF_AddressActivity.class);

				startActivity(intent);
			}
		});
	}

	private void showbuypopu() {
		dialog = new Dialog(this);

		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.popupwindow_buy);
		dialog.setCanceledOnTouchOutside(true);
		Window window = dialog.getWindow();
		window.setGravity(Gravity.CENTER);
		WindowManager.LayoutParams lp = window.getAttributes();
		// lp.alpha = 0.8f;
		// lp.width=;
		window.setAttributes(lp);
		window.setBackgroundDrawableResource(R.drawable.yuanjiao);

		ImageView iv_buy_course = (ImageView) dialog
				.findViewById(R.id.iv_buy_course);
		TextView tv_buy_title = (TextView) dialog
				.findViewById(R.id.tv_buy_title);
		TextView tv_buy_cost = (TextView) dialog.findViewById(R.id.tv_buy_cost);
		Button bt_buy_ok = (Button) dialog.findViewById(R.id.bt_buy_ok);
		Button bt_buy_quit = (Button) dialog.findViewById(R.id.bt_buy_quit);

		// AsyncBitmapLoader.sethoneImage(iv_buy_course, imgurl);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(imgurl, iv_buy_course, options);

		tv_buy_title.setText(getIntent().getStringExtra("name"));

		if (buyWay == 1) {
			tv_buy_cost.setText("��֧����" + getIntent().getIntExtra("amount", 0)
					+ "�ֱ�");
			iv_order_type.setImageResource(R.drawable.me_icon_le);
		} else if (buyWay == 2) {
			tv_buy_cost.setText("��֧����" + getIntent().getIntExtra("amount", 0)
					+ "���");
			iv_order_type.setImageResource(R.drawable.me_icon_jin);
		}

		bt_buy_quit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});

		bt_buy_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// �����жϣ����ݽ�һ����ֱң����Լ�ӵ�еĽ����жϣ�>=������ʾ��else buy()
				buy();
			}
		});

		dialog.show();
	}

	protected void buy() {
		// TODO �ύ�����Ĳ���
		dialog.dismiss();
		finish();
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					Toast.makeText(OrderActivity.this, "����ɹ�",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(OrderActivity.this, msg.obj.toString(),
							Toast.LENGTH_SHORT).show();
				}
			}
		};

		new Thread() {
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				Message msg = new Message();
				map.put("pk_goods", pk_goods);
				map.put("pk_adr", pk_adr);

				String result = HttpUse.messageget("QueAndAns", "userBuyGood",
						map);
				try {
					JSONObject jo = new JSONObject(result);
					if (jo.getBoolean("result")) {
						msg.what = 1;
						msg.obj = jo.getJSONObject("data");
					} else {
						msg.obj = jo.getString("message");
					}

				} catch (JSONException e) {
					msg.obj = e.getMessage();
				}

				handler.sendMessage(msg);

			};
		}.start();
	}

}
