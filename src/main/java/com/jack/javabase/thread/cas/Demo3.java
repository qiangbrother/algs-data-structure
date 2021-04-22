package com.jack.javabase.thread.cas;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description 使用cas
 * @author: zq
 * @create: 2020-10-21 16:57
 **/
public class Demo3 {
	static AtomicInteger atomicInteger = new AtomicInteger(0);

	public static void add() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(5);
		atomicInteger.getAndIncrement();
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
		System.out.println(Thread.currentThread().getName() + "，耗时：" + (endTime - startTime) + ",count值：" + atomicInteger.get());

	}

}