package com.mpyf.lening.activity.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mpyf.lening.R;
import com.mpyf.lening.Jutil.ListenerServer;
import com.mpyf.lening.Jutil.MyListView;
import com.mpyf.lening.activity.adapter.ExamsAdapter;
import com.mpyf.lening.activity.adapter.ExamselfAdapter;
import com.mpyf.lening.activity.adapter.ZhuantiDtatilAdapter;
import com.mpyf.lening.interfaces.bean.Result.Course;
import com.mpyf.lening.interfaces.bean.Result.ExamSelf;
import com.mpyf.lening.interfaces.bean.Result.ProColumn;
import com.mpyf.lening.interfaces.bean.Result.Project;
import com.mpyf.lening.interfaces.http.HttpUse;
import com.mpyf.lening.interfaces.http.Setting;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Administrator
 *◊®Ã‚œÍ«È“≥
 */
public class ZhuantiDetailActivity extends Activity {

	private ImageView iv_zhuanti;
	private TextView tv_project_title;
	private TextView tv_project_remark;
	private DisplayImageOptions options;
	private MyListView lv_exam;
	private MyListView lv_detail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_zhuntidetail);
		
		initUI();
		initData();
	}

	private void initUI() {
		
		iv_zhuanti = (ImageView) findViewById(R.id.iv_zhuanti);
		tv_project_title = (TextView) findViewById(R.id.tv_project_title);
		tv_project_remark = (TextView) findViewById(R.id.tv_project_remark);
		

		lv_exam = (MyListView) findViewById(R.id.lv_exam);
		lv_detail = (MyListView) findViewById(R.id.lv_detail);
		
		LinearLayout ll_list_back = (LinearLayout) findViewById(R.id.ll_list_back);
		
		ListenerServer.setfinish(ZhuantiDetailActivity.this, ll_list_back);
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.defaultimage) 
		.showImageOnFail(R.drawable.defaultimage)
		.showImageForEmptyUri(R.drawable.defaultimage)
		.cacheInMemory(true) 
		.cacheOnDisk(true) 
		 .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
        .bitmapConfig(Bitmap.Config.ALPHA_8)
		.build(); 
	}

	private void initData() {

		final Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==1){
					try {
						JSONObject jo = new JSONObject(msg.obj.toString());
						Project project = new Project();
						project.pro_Name = jo.getString("pro_Name");
						project.remark = jo.getString("remark");
						project.proInfo_pic_url = jo.getString("proInfo_pic_url");
						project.isExam = jo.getBoolean("isExam"); 
						
						JSONArray ja = jo.getJSONArray("pro_Column");
						ArrayList<ProColumn> list = new ArrayList<ProColumn>();
						for (int i = 0; i < ja.length(); i++) {
							ProColumn proColum = new ProColumn();
							JSONObject jo1 = ja.getJSONObject(i);
							proColum.PK_Col = jo1.getString("PK_Col");
							proColum.col_Name = jo1.getString("col_Name");
							proColum.remark = jo1.getString("remark");
							
							
							JSONArray ja2 = jo1.getJSONArray("col_course");
							List<Course> list2 = new ArrayList<Course>();
							for (int j = 0; j < ja2.length(); j++) {
								
								JSONObject jo2 = ja2.getJSONObject(j);
								Course course = new Course();
								course.PK_Course = jo2.getString("PK_Course");
								course.courseName = jo2.getString("courseName");
								course.remark = jo2.getString("remark");
								list2.add(course);
							}
							proColum.col_course = list2;
							
							list.add(proColum);
						}
						
						project.pro_Column = list;
						
						JSONArray ja3 = jo.getJSONArray("pro_Exam");
						List<ExamSelf> list3 = new ArrayList<ExamSelf>();
						for (int i = 0; i < ja3.length(); i++) {
							ExamSelf examSelf = new ExamSelf();
							JSONObject jo3 = ja3.getJSONObject(i);
							examSelf.PK_ExamSelf = jo3.getString("PK_ExamSelf");
							examSelf.exam_Name = jo3.getString("exam_Name");
							examSelf.sta_Time = jo3.getString("sta_Time");
							examSelf.end_Time = jo3.getString("end_Time");
							examSelf.exam_Long = jo3.getInt("exam_Long");
							examSelf.exam_State = jo3.getInt("exam_State");
							
							examSelf.score = jo3.getInt("score");
							examSelf.buyWay = jo3.getInt("buyWay");
							examSelf.amount = jo3.getInt("amount");
							
							list3.add(examSelf);
						}
						project.pro_Exam = list3;
						
					
						showData(project);
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		new Thread(){
			
			public void run() {
				
				HashMap<String,Object> map = new HashMap<String, Object>();
				map.put("PK_Pro", getIntent().getStringExtra("PK_Pro"));
				
				String result = HttpUse.messageget("CourseStudy", "getProject", map);
				Message msg = Message.obtain();
				try {
					JSONObject jo = new JSONObject(result);
					
					if(jo.getBoolean("result")){
						msg.what=1;
						msg.obj = jo.getString("data");
					}else{
						msg.obj = jo.getString("message");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				handler.sendMessage(msg);
			};
			
		}.start();
		
	}

	protected void showData(Project project) {

		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage( Setting.apiUrl+project.proInfo_pic_url, iv_zhuanti, options);
		tv_project_title.setText(project.pro_Name);
		if(TextUtils.isEmpty(project.remark)){
			tv_project_remark.setVisibility(View.GONE);
		}else{
			tv_project_remark.setText(project.remark);
		}
		if(project.isExam){
			
			List<ExamSelf> examlist = project.pro_Exam;
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < examlist.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				ExamSelf exam = examlist.get(i);
				map.put("PK_ExamSelf",exam.PK_ExamSelf);
				map.put("Exam_Name", exam.exam_Name);
				map.put("Sta_Time", exam.sta_Time);
				map.put("End_Time", exam.end_Time);
				map.put("Exam_Long", exam.exam_Long);
				map.put("Exam_State", exam.exam_State);
				map.put("Score", exam.score);
					map.put("BuyWay", exam.buyWay);
				map.put("Amount", exam.amount);
				
				list.add(map);
			}
			
			ExamselfAdapter examsAdapter = new ExamselfAdapter(ZhuantiDetailActivity.this, list);
			lv_exam.setAdapter(examsAdapter);
		}else{
			lv_exam.setVisibility(View.GONE);
		}
		
		List<ProColumn> columnList = project.pro_Column;
		
		ZhuantiDtatilAdapter adapter = new ZhuantiDtatilAdapter(ZhuantiDetailActivity.this,columnList);
		 
		lv_detail.setAdapter(adapter);
	}

	
}
