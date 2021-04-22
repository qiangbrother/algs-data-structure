package com.jack.javabase.thread.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
* @Description:
* @Author:         ZhangQiang
* @CreateDate:     2020/10/20 15:26
*/
public class CountDownLatchDemo {

	public static class T extends Thread{
		int sleepSecond;
		CountDownLatch countDownLatch;

		public T(String name,int sleepSecond,CountDownLatch countDownLatch){
			super(name);
			this.sleepSecond = sleepSecond;
			this.countDownLatch = countDownLatch;
		}

		@Override
		public void run() {
			long startTime = System.currentTimeMillis();
			System.out.println(startTime+":"+this.getName()+",开始处理！");
			try {
				TimeUnit.SECONDS.sleep(sleepSecond);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally {
				countDownLatch.countDown();
			}
			long endTime = System.currentTimeMillis();
			System.out.println(endTime-startTime+":"+this.getName()+",处理结束！");
		}
	}

	public static void main(String[] args) throws InterruptedException {
		long startTime = System.currentTimeMillis();

		CountDownLatch countDownLatch = new CountDownLatch(2);
		T t1 = new T("t1", 2, countDownLatch);
		t1.start();
		T t2 = new T("t2", 5, countDownLatch);
		t2.start();
		countDownLatch.await();
		long endTime = System.currentTimeMillis();
		System.out.println("总线程耗时："+(endTime-startTime));
	}
}