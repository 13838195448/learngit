package com.mpyf.lening.Jutil;

import com.mpyf.lening.R;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.LinearLayout;

public class CheckableLinearLayout extends LinearLayout implements Checkable {
	private boolean mChecked;

	public CheckableLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setChecked(boolean checked) {
		mChecked = checked;
	//	setBackgroundDrawable(checked ? new ColorDrawable(0xff0000a0) : null);//当�?�中时呈现蓝�?
		
		setBackgroundResource(checked ? R.drawable.yuanjiao_lv :R.drawable.yunajiao_bai);
	}

	@Override
	public boolean isChecked() {
		return mChecked;
	}

	@Override
	public void toggle() {
		setChecked(!mChecked);
	}

}
