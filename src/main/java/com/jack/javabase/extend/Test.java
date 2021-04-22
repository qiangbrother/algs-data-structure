package com.jack.javabase.extend;

import java.time.LocalDateTime;

public class Test {
	public static void main(String[] args) {
		/*Manager[] managers = new Manager[10];
		Employee employee = new Employee();
		Employee[] staff = managers;
		staff[0] = employee;*/
		LocalDateTime now = LocalDateTime.now();
		String s = String.valueOf(now.getHour()) +  + now.getMinute() + now.getSecond();
		int documentModel = Integer.parseInt(s);
		System.out.println(documentModel);
	}
}