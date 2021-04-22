package com.jack.javabase.thread;

/**
 * 线程死锁
 * @author 99552
 */
public class DeadLock {

	public static void main(String[] args) {
		//查看死锁的方法
		/*
		 *1、通过jps查看到进程信息
		 *2、通过 jstack -l pid 来查看死锁信息
		 */
		Object1 obj1 = new Object1();
		Object2 obj2 = new Object2();

		Thread thread = new Thread(new SyncAddRunable(obj1, obj2, 1, 2, true));
		thread.setName("thread-1");
		thread.start();
		Thread thread1 = new Thread(new SyncAddRunable(obj1, obj2, 2, 2, false));
		thread1.setName("thread-2");
		thread1.start();

	}


	public static class  SyncAddRunable implements Runnable{

		Object1 obj1;
		Object2 obj2;
		int a,b;
		boolean flag;

		public SyncAddRunable(Object1 obj1, Object2 obj2, int a, int b, boolean flag) {
			this.obj1 = obj1;
			this.obj2 = obj2;
			this.a = a;
			this.b = b;
			this.flag = flag;
		}


		@Override
		public void run() {
			try {
			if (flag){
				synchronized (obj1){
					Thread.sleep(100);
					synchronized (obj2){
						System.out.println(a+b);
					}
				}
			}else{
				synchronized (obj2){
					Thread.sleep(100);
					synchronized (obj1){
						System.out.println(a+b);
					}
				}
			}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}


	public static class Object1{

	}

	public static class Object2{

	}

}