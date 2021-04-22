package com.jack.javabase.thread;
/**
* @Description:    wait方法和sleep方法
* @Author:         ZhangQiang
* @CreateDate:     2020/10/19 14:18
*/
public class WaitAndSleepThread {

	static Object object =  new Object();

	public static class T1 implements Runnable{

		@Override
		public void run() {
			synchronized (object){
				System.out.println(System.currentTimeMillis()+"：T1 start...");
				try {
					System.out.println(System.currentTimeMillis()+": T1 wait for object....");
					object.wait();
					System.out.println(System.currentTimeMillis()+": T1 wait for object  end....");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(System.currentTimeMillis()+": T1 end.....");
			}
		}
	}


	public static class T2 implements Runnable{

		@Override
		public void run() {
			synchronized (object){
				System.out.println(System.currentTimeMillis()+": T2 notify for object");
				object.notify();
				System.out.println(System.currentTimeMillis()+": T2 end.....");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		new Thread(new T1()).start();
		//new Thread(new T2()).start();

	}

}