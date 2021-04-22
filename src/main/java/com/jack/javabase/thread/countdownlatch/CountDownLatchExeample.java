package com.jack.javabase.thread.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
/**
* @Description:    有3个人参见跑步比赛，需要先等指令员发指令枪后才能开跑，所有人都跑完之后，指令员喊一声，大家跑完了。
* @Author:         ZhangQiang
* @CreateDate:     2020/10/20 15:41
*/
public class CountDownLatchExeample {

	public static class T extends Thread{
		int sleepSecond;
		CountDownLatch countDownLatch;
		CountDownLatch cmdCountDownLatch;

		public T(String name, int sleepSecond, CountDownLatch countDownLatch,CountDownLatch cmdCountDownLatch){
			super(name);
			this.sleepSecond = sleepSecond;
			this.countDownLatch = countDownLatch;
			this.cmdCountDownLatch = cmdCountDownLatch;
		}

		@Override
		public void run() {
			try {
				//所有线程进入等待，一起执行
				cmdCountDownLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			long startTime = System.currentTimeMillis();
			System.out.println(startTime+":"+this.getName()+"开始跑了！");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally {
				countDownLatch.countDown();
			}
			long endTime = System.currentTimeMillis();
			System.out.println(this.getName()+",跑步结束了！总耗时："+(endTime-startTime));
		}
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println(System.currentTimeMillis()+":"+Thread.currentThread().getName()+"开始执行");
		CountDownLatch cmdCountDownLatch = new CountDownLatch(1);
		CountDownLatch countDownLatch = new CountDownLatch(3);
		long startTime = System.currentTimeMillis();
		T t1 = new T("小张", 2, countDownLatch, cmdCountDownLatch);
		t1.start();

		T t2 = new T("小李", 5, countDownLatch, cmdCountDownLatch);
		t2.start();

		T t3 = new T("小王", 10, countDownLatch, cmdCountDownLatch);
		t3.start();
		TimeUnit.SECONDS.sleep(5);
		System.out.println(System.currentTimeMillis()+":响枪了，比赛开始！！");
		cmdCountDownLatch.countDown();
		countDownLatch.await();
		long endTime = System.currentTimeMillis();
		System.out.println(System.currentTimeMillis()+":"+Thread.currentThread().getName()+":比赛结束，总耗时："+(endTime-startTime));
	}
}