package com.jack.javabase.thread.cas;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @description 问题，在多线程处理处理的时候，num++并不是原子性，num++实际是分三步完成
 * 				A = num
 * 				B = A +1
 * 				num = B
 * 				可能存在某个过程中被其他线程执行，所有实际的值 不是1000
 * 				处理方式，
 * 				1、在对资源进行加锁，发现效率特别的低
 * @author: zq
 * @create: 2020-10-21 16:34
 **/
public class Demo1 {

	static int num = 0;

	public static synchronized void add() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(5);
		num++;
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
		System.out.println(Thread.currentThread().getName() + "，耗时：" + (endTime - startTime) + ",count值：" + num);

	}

}