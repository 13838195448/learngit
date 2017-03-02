package com.mpyf.lening.activity.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

public class Vpadapter extends FragmentPagerAdapter {
	private List<Fragment> list;
	private FragmentManager fm;
	public Vpadapter(FragmentManager fragmentManager,List<Fragment> list) {
		super(fragmentManager);
		this.list=list;
		this.fm = fragmentManager;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
	
	public void clearFragment() {

		setFragments(null);
	}

	private void setFragments(List<Fragment> list) {

		if (this.list != null) {
			FragmentTransaction ft = fm.beginTransaction();
			for (Fragment f : this.list) {
				ft.remove(f);
			}
			ft.commit();
			ft = null;
			fm.executePendingTransactions();
		}
		if(list!=null)
			this.list = list;
		notifyDataSetChanged();
	
	}

}
