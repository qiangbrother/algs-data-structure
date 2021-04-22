package com.jack.javabase.thread.cyclibarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @description
 * @author: zq
 * @create: 2020-10-20 19:04
 **/
public class CyclibarrierDemo {

	public static CyclicBarrier cyclicBarrier = new CyclicBarrier(10,
			//在所有的等待线程之前调用
			() ->{
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+"说，不好意思，让大家就等了！");
	});

	public static class T extends Thread{
		int sleep;

		public T (String name,int sleep){
			super(name);
			this.sleep = sleep;
		}

		@Override
		public void run() {

			try {
				TimeUnit.SECONDS.sleep(sleep);
				long startTime = System.currentTimeMillis();
				//调用await()
				cyclicBarrier.await();
				long endTime = System.currentTimeMillis();
				System.out.println(this.getName()+",sleep:"+this.sleep+" 等待了"+(endTime-startTime));
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10 ; i++) {
			new T("员工"+i,i).start();
		}
	}
}