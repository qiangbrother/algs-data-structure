package com.jack.javabase.thread.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: ReentrantLock简单使用,ReentrantLock也是可重入锁
 * @Author: ZhangQiang
 * @CreateDate: 2020/10/20 9:25
 */
public class ReentrantLockUseDemo {

	private static int number = 0;
	/**
	 * 当构造函数中传入true的时候，那么lock为公平锁
	 */
	private static ReentrantLock lock = new ReentrantLock(true);

	/**
	 * lock中的锁必须成对出现，且unlock必须在finally方法中
	 */
	public static void add() {
		//lock.lock();
		lock.lock();
		try {
			number++;
		} finally {
			//lock.unlock();
			lock.unlock();
		}
	}

	public static class T extends Thread{
		@Override
		public void run() {
			for (int i=0 ; i<10000 ; i++){
				ReentrantLockUseDemo.add();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		T t = new T();
		T t1 = new T();
		T t2 = new T();
		t.start();
		t1.start();
		t2.start();

		t.join();
		t1.join();
		t2.join();

		System.out.println(ReentrantLockUseDemo.number);
	}
}