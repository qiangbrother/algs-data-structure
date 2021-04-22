package com.jack.test;

/**
 * @description
 * @author: zq
 * @create: 2020-10-22 09:35
 **/
public class Test1 {

	static ThreadLocal<String> a = new ThreadLocal<>();
	static ThreadLocal<String> b = new ThreadLocal<>();

	public static void main(String[] args) {
		a.set("111111");
		b.set("ni hao");
		System.out.println(a.toString() +":"+ a.get());
		System.out.println(b.toString() +":"+ b.get());

	}

}