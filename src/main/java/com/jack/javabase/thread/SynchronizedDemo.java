package com.jack.javabase.thread;
/**
* @Description:
* @Author:         ZhangQiang
* @CreateDate:     2020/10/19 17:12
*/
public class SynchronizedDemo {

	int number = 0;

	public synchronized void add(){
		number++;
	}

	public static class T1 extends Thread{

		private SynchronizedDemo synchronizedDemo;

		public T1(SynchronizedDemo synchronizedDemo){
			this.synchronizedDemo = synchronizedDemo;
		}

		@Override
		public void run() {
			for (int i=0;i<10000;i++){
				this.synchronizedDemo.add();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		SynchronizedDemo synchronizedDemo = new SynchronizedDemo();
		T1 t1 = new T1(synchronizedDemo);
		T1 t11 = new T1(synchronizedDemo);
		t1.start();
		t11.start();
		t1.join();
		t11.join();
		System.out.println(synchronizedDemo.number);
	}
}