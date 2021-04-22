package com.jack.javabase.thread.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: Condition 使用
 * @Author: ZhangQiang
 * @CreateDate: 2020/10/20 10:33
 */
public class ConditionDemo {

	private static ReentrantLock lock = new ReentrantLock();
	private static Condition condition = lock.newCondition();

	public static class T1 extends Thread {
		@Override
		public void run() {
			System.out.println(System.currentTimeMillis() + ":" + this.getName() + " 准备获取锁");
			lock.lock();
			try {
				System.out.println(System.currentTimeMillis() + ":" + this.getName() + " 获取锁了");
				condition.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
			System.out.println(System.currentTimeMillis() + ":" + this.getName() + " 释放锁了");

		}
	}

	public static class T2 extends Thread {
		@Override
		public void run() {
			System.out.println(System.currentTimeMillis() + ":" + this.getName() + " 准备获取锁");
			lock.lock();
			try {
				System.out.println(System.currentTimeMillis() + ":" + this.getName() + " 获取锁了");
				condition.signal();
				System.out.println(System.currentTimeMillis() + ":" + this.getName() + " 发送single");
			} finally {
				lock.unlock();
			}
			System.out.println(System.currentTimeMillis() + ":" + this.getName() + " 释放锁了");

		}
	}

	public static void main(String[] args) throws InterruptedException {
		T1 t1 = new T1();
		t1.setName("t1");
		t1.start();
		TimeUnit.SECONDS.sleep(5);
		T2 t2 = new T2();
		t2.setName("t2");
		t2.start();

	}

}