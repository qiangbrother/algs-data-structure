package com.jack.javabase.thread.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @description
 * @author: zq
 * @create: 2020-10-21 17:46
 **/
public class UnsafeDemo1 {

	static Unsafe unsafe;
	/**
	 * 用于记录请求次数
	 */
	static int count;
	/**
	 * count在UnsafeDemo1.class对象中的地址偏移量
	 */
	static long countOffset;

	static {
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			unsafe = (Unsafe) field.get(null);

			Field countFiled = UnsafeDemo1.class.getDeclaredField("count");
			//获取count在UnsafeDemo1.class对象中的地址偏移量
			countOffset = unsafe.staticFieldOffset(countFiled);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void add() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(5);
		unsafe.getAndAddInt(UnsafeDemo1.class,countOffset,1);
	}

	public static void main(String[] args) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		int ThreadSize = 100;
		CountDownLatch countDownLatch = new CountDownLatch(ThreadSize);
		for (int i = 0; i < ThreadSize; i++) {
			Thread thread = new Thread(() -> {
				try {
					for (int j = 0; j < 10; j++) {
						add();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					countDownLatch.countDown();
				}
			});
			thread.start();
		}
		countDownLatch.await();
		long endTime = System.currentTimeMillis();
		System.out.println(Thread.currentThread().getName() + "，耗时：" + (endTime - startTime) + ",count值：" + count);

	}

}