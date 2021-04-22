package com.jack.javabase.thread.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @description
 * @author: zq
 * @create: 2020-10-21 17:32
 **/
public class UnsafeDemo {
	static Unsafe unsafe;

	static {
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			unsafe = (Unsafe) field.get(null);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println(unsafe);
	}
}