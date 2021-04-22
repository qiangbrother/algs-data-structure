package com.jack.javabase.thread.cas;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @description  解决num++原子性的问题，num++可以分成为三步，如果使用synchorized对三步都进行了
 * 				加锁，那么我们只对第三步进行加锁呢？
 * 				这种方式也没有问题。
 * @author: zq
 * @create: 2020-10-21 16:50
 **/
public class Demo2 {

	static int num = 0;

	public static void add () throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(5);
		int exceptNum;
		do {
			exceptNum = getNum();
		}while (!compareAndSwap(exceptNum,exceptNum+1));
	}

	public static int getNum(){
		return num;
	}

	/**
	 * @param exceptNum 期望值
	 * @param newNum 新值
	 * @return
	 */
	public static synchronized boolean compareAndSwap(int exceptNum, int newNum){
		if (getNum() == exceptNum){
			num = newNum;
			return true;
		}
		return false;
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