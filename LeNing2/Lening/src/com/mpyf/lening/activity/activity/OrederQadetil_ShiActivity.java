package com.mpyf.lening.activity.activity;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.ListenerServer;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrederQadetil_ShiActivity extends Activity {

	private LinearLayout ll_ordershi_back;
	private TextView tv_goodsName, tv_buyWay, tv_amount, tv_buyDate,
			tv_order_type, tv_name, tv_phone, tv_address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_order_shi);
		init();
		getdate();
		addlistenr();

	}

	private void init() {
		ll_ordershi_back = (LinearLayout) findViewById(R.id.ll_ordershi_back);
		tv_goodsName = (TextView) findViewById(R.id.tv_goodsName);
		tv_buyWay = (TextView) findViewById(R.id.tv_buyWay);
		tv_amount = (TextView) findViewById(R.id.tv_amount);
		tv_buyDate = (TextView) findViewById(R.id.tv_buyDate);
		tv_order_type = (TextView) findViewById(R.id.tv_order_type);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_phone = (TextView) findViewById(R.id.tv_phone);
		tv_address = (TextView) findViewById(R.id.tv_address);

	}

	private void getdate() {
		String goodsName = getIntent().getStringExtra("goodsName");
		String buyWay = getIntent().getStringExtra("buyWay");
		String amount = getIntent().getStringExtra("amount");
		String buyDate = getIntent().getStringExtra("buyDate");
		String order_type = getIntent().getStringExtra("order_type");
		String name = getIntent().getStringExtra("name");
		String phone = getIntent().getStringExtra("phone");
		String address = getIntent().getStringExtra("address");
		if (!TextUtils.isEmpty(goodsName)) {
			tv_goodsName.setText(goodsName);
		} else {
			tv_goodsName.setText("");
		}
		if (!TextUtils.isEmpty(buyWay)) {
			tv_buyWay.setText(buyWay);
		} else {
			tv_buyWay.setText("");
		}

		if (!TextUtils.isEmpty(amount)) {
			tv_amount.setText(amount);
		} else {
			tv_amount.setText("");
		}
		if (!TextUtils.isEmpty(buyDate)) {
			tv_buyDate.setText(buyDate);
		} else {
			tv_buyDate.setText("");
		}
		if (!TextUtils.isEmpty(order_type)) {
			tv_order_type.setText(order_type);
		} else {
			tv_order_type.setText("");
		}
		if (!TextUtils.isEmpty(name)) {
			tv_name.setText(name);
		} else {
			tv_name.setText("");
		}
		if (!TextUtils.isEmpty(phone)) {
			tv_phone.setText(phone);
		} else {
			tv_phone.setText("");
		}
		if (!TextUtils.isEmpty(address)) {
			tv_address.setText(address);
		} else {
			tv_address.setText("");
		}

	}

	private void addlistenr() {
		// ·µ»Ø¼ü
		ListenerServer.setfinish(this, ll_ordershi_back);
	}
}
