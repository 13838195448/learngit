package com.mpyf.lening.service;

import java.util.HashMap;

import com.mpyf.lening.interfaces.http.MessageHandle;
import com.mpyf.lening.interfaces.http.RequestCallBack;


public class ExamGet {

	public static void GetExam(HashMap<String, Object> map,
			RequestCallBack callBack) {
		MessageHandle.getMessage(map, "Exam!getExamSelf.action",callBack);
		//MessageHandle.getMessage(map, "Exam", "getExamSelf", callBack);
	}

}
///AbilityCertification!announcementCertificationList.action