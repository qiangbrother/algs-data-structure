package com.jack.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @description
 * @author: zq
 * @create: 2021-02-04 22:43
 **/
public class TestDate {
	public static void main(String[] args) {
		String strDate = getStrDate("2021-02", 11);
		System.out.println(strDate);
	}

	public static String getStrDate(String strDate,int num ){
		String stringDate="";
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");//格式化为2017-10
			Date month=formatter.parse(strDate);
			Calendar calendar = Calendar.getInstance();//得到Calendar实例
			calendar.setTime(month);
			calendar.add(Calendar.MONTH, -num);//把月份减三个月
			Date starDate = calendar.getTime();//得到时间赋给Data
			stringDate = formatter.format(starDate).replace("-","");//使用格式化Data
			return stringDate;
		}catch (Exception e){
			e.printStackTrace();
			return stringDate;

		}
	}
}