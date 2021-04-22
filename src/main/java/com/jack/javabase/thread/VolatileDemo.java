package com.jack.javabase.thread;

/**
 * @Description: 测试可见性
 * @Author: ZhangQiang
 * @CreateDate: 2020/10/19 15:39
 */
public class VolatileDemo {

	public static volatile boolean flag = true;

	public static class T1 extends Thread {

		public T1(String name) {
			super(name);
		}

		@Override
		public void run() {
			System.out.println(System.currentTimeMillis() + ":T1 线程开始执行。。。。");
			while (flag) {

			}
			System.out.println(System.currentTimeMillis() + ":T1 线程结束了。。。。");
		}
	}

	public static void main(String[] args) throws InterruptedException {
		new T1("t1").start();
		Thread.sleep(200);
		flag = false;
	}
}