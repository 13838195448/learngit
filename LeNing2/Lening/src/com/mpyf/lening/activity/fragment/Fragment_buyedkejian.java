package com.mpyf.lening.activity.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.Diaoxian;
import com.mpyf.lening.Jutil.Quanjubianliang;
import com.mpyf.lening.activity.activity.StudyActivity;
import com.mpyf.lening.activity.adapter.KejianAdapter;
import com.mpyf.lening.interfaces.http.Setting;

public class Fragment_buyedkejian extends Fragment {

	private ListView lv_list;
	private List<Map<String, Object>> list;
	private String studyres = "", id = "";
	private String resid = "";
	private View space_list;

	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list, null);
		init(view);
		addlistener();
		return view;
	};

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		showinfo();

	}

	public Fragment_buyedkejian() {

	}

	public Fragment_buyedkejian(String id, String studyres) {
		this.studyres = studyres;
		this.id = id;
	}

	private void init(View view) {
		lv_list = (ListView) view.findViewById(R.id.lv_list);
		space_list = view.findViewById(R.id.space_list);
		Quanjubianliang.kejiancontrl = false;
	}

	private void showinfo() {
		list = new ArrayList<Map<String, Object>>();

		JSONArray ja;
		try {
			ja = new JSONArray(studyres);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", jo.getString("PK_Res"));
				map.put("title", jo.getString("resName"));
				map.put("time", "ÒÑÑ§Ï°   " + jo.getString("speed") + "%");
				map.put("ResUrl", Setting.apiUrl + jo.getString("resUrl"));
				list.add(map);
			}

			KejianAdapter adapter = new KejianAdapter(getActivity(), lv_list,
					list);
			lv_list.setAdapter(adapter);

			lv_list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					if (Quanjubianliang.kejiancontrl) {
						// int fc=lv_list.getFirstVisiblePosition();
						// showinfo();
						// Quanjubianliang.kejiancontrl=false;
						// lv_list.setSelection(fc);
						clearall();

					} else {

						Intent intent = new Intent(getActivity(),
								StudyActivity.class);
						intent.putExtra("resId", list.get(arg2).get("id")
								.toString());
						startActivity(intent);
					}
					// View view=lv_list.getChildAt(arg2);
					// LinearLayout rl_buyedkejian_info=(LinearLayout)
					// view.findViewById(R.id.rl_buyedkejian_info);
					// LinearLayout ll_buyedkejian_main=(LinearLayout)
					// view.findViewById(R.id.ll_buyedkejian_main);
					// LinearLayout ll_buyedkejian_dialog=(LinearLayout)
					// view.findViewById(R.id.ll_buyedkejian_dialog);
					// LinearLayout ll_buyedkejian_ctrl=(LinearLayout)
					// view.findViewById(R.id.ll_buyedkejian_ctrl);
					//
					// if(arg1==ll_buyedkejian_main){
					// if (Quanjubianliang.kejiancontrl) {
					// int fc=lv_list.getFirstVisiblePosition();
					// KejianAdapter adapter = new
					// KejianAdapter(getActivity(),lv_list,list);
					// lv_list.setAdapter(adapter);
					// lv_list.setSelection(fc);
					// Quanjubianliang.kejiancontrl=false;
					// }else{
					// Intent intent=new Intent(getActivity(),
					// StudyActivity.class);
					// intent.putExtra("resId",
					// list.get(arg2).get("id").toString());
					// startActivity(intent);
					// }
					// }else if (arg1==ll_buyedkejian_dialog) {
					// if (Quanjubianliang.kejiancontrl) {
					// int fc=lv_list.getFirstVisiblePosition();
					// KejianAdapter adapter = new
					// KejianAdapter(getActivity(),lv_list,list);
					// lv_list.setAdapter(adapter);
					// lv_list.setSelection(fc);
					// Quanjubianliang.kejiancontrl=false;
					// }else{
					// rl_buyedkejian_info.setVisibility(View.GONE);
					// ll_buyedkejian_ctrl.setVisibility(View.VISIBLE);
					// Quanjubianliang.kejiancontrl=true;
					// }
					// }

				}
			});
			// showdialog();
		} catch (JSONException e) {
			Diaoxian.showerror(getActivity(), e.getMessage());
		}

	}

	private void addlistener() {
		space_list.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				KejianAdapter adapter = new KejianAdapter(getActivity(),
						lv_list, list);
				lv_list.setAdapter(adapter);
			}
		});
	}

	private void clearall() {
		if (lv_list.getCount() > 0) {
			for (int i = 0; i < lv_list.getCount(); i++) {
				View view = lv_list.getChildAt(i);
				LinearLayout rl_buyedkejian_info = (LinearLayout) view
						.findViewById(R.id.rl_buyedkejian_info);
				LinearLayout ll_buyedkejian_ctrl = (LinearLayout) view
						.findViewById(R.id.ll_buyedkejian_ctrl);
				ll_buyedkejian_ctrl.setVisibility(View.GONE);
				rl_buyedkejian_info.setVisibility(View.VISIBLE);
			}
		}
		Quanjubianliang.kejiancontrl = false;
	}

}
