package com.jack.javabase.thread.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: 公平锁
 * @Author: ZhangQiang
 * @CreateDate: 2020/10/20 9:40
 */
public class FairLockDemo {
	/**
	 * 当构造函数中传入true的时候，那么lock为公平锁
	 */
	private static ReentrantLock lock = new ReentrantLock(true);


	public static class T extends Thread {

		public T(String name) {
			super(name);
		}

		@Override
		public void run() {
			for (int i = 0; i < 5; i++) {
				lock.lock();
				try {

					System.out.println("当前线程是：" + this.getName());

				} finally {
					lock.unlock();
				}
			}

		}
	}

	public static void main(String[] args) throws InterruptedException {
		T t = new T("t1");
		T t1 = new T("t2");
		T t2 = new T("t3");
		t.start();
		t1.start();
		t2.start();

		t.join();
		t1.join();
		t2.join();

	}
}