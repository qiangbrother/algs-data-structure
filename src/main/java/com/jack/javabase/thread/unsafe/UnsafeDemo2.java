package com.jack.javabase.thread.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

/**
 * @description
 * @author: zq
 * @create: 2020-10-21 17:54
 **/
public class UnsafeDemo2 {
	static Unsafe unsafe;

	static {
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			unsafe = (Unsafe) field.get(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 调用park和unpark，模拟线程的挂起和唤醒
	 *
	 * @throws InterruptedException
	 */
	public static void m1() throws InterruptedException {
		Thread thread = new Thread(() -> {
			System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",start");
			unsafe.park(false, 0);
			System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",end");
		});
		thread.setName("thread1");
		thread.start();

		TimeUnit.SECONDS.sleep(5);
		unsafe.unpark(thread);
	}

	/**
	 * 阻塞指定的时间
	 */
	public static void m2() {
		Thread thread = new Thread(() -> {
			System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",start");
			//线程挂起3秒
			unsafe.park(false, TimeUnit.SECONDS.toNanos(3));
			System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",end");
		});
		thread.setName("thread2");
		thread.start();
	}

	public static void main(String[] args) throws InterruptedException {
		m1();
		m2();
	}
}