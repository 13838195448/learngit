package com.mpyf.lening.activity.activity;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.ListenerServer;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrederQadetil_XuActivity extends Activity {
	private LinearLayout ll_orderxu_back;
	private TextView tv_goodsName, tv_buyWay, tv_amount, tv_buyDate,
			tv_order_type, tv_num, tv_pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_order_xu);
		init();
		getdate();
		addlistenr();

	}

	private void init() {
		ll_orderxu_back = (LinearLayout) findViewById(R.id.ll_orderxu_back);
		tv_goodsName = (TextView) findViewById(R.id.tv_goodsName);
		tv_buyWay = (TextView) findViewById(R.id.tv_buyWay);
		tv_amount = (TextView) findViewById(R.id.tv_amount);
		tv_buyDate = (TextView) findViewById(R.id.tv_buyDate);
		tv_order_type = (TextView) findViewById(R.id.tv_order_type);
		tv_num = (TextView) findViewById(R.id.tv_num);
		tv_pwd = (TextView) findViewById(R.id.tv_pwd);
	}

	private void getdate() {
		String goodsName = getIntent().getStringExtra("goodsName");
		String buyWay = getIntent().getStringExtra("buyWay");
		String amount = getIntent().getStringExtra("amount");
		String buyDate = getIntent().getStringExtra("buyDate");
		String order_type = getIntent().getStringExtra("order_type");
		String num = getIntent().getStringExtra("courierName");
		String pwd = getIntent().getStringExtra("courierNo");

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
		if (!TextUtils.isEmpty(num)) {
			tv_num.setText(num);
		} else {
			tv_num.setText("");
		}
		if (!TextUtils.isEmpty(pwd)) {
			tv_pwd.setText(pwd);
		} else {
			tv_pwd.setText("");
		}

	}

	private void addlistenr() {
		// ·µ»Ø¼ü
		ListenerServer.setfinish(this, ll_orderxu_back);
	}
}
