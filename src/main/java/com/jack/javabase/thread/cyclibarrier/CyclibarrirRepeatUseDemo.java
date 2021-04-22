package com.jack.javabase.thread.cyclibarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @description  CyclicBarrier 可以重复使用
 * @author: zq
 * @create: 2020-10-20 19:15
 **/
public class CyclibarrirRepeatUseDemo {

	public static CyclicBarrier cyclicBarrier = new CyclicBarrier(10);

	public static class T extends Thread{
		int sleep;

		public T (String name,int sleep){
			super(name);
			this.sleep = sleep;
		}

		void eat(){
			try {
				TimeUnit.SECONDS.sleep(sleep);
				long startTime = System.currentTimeMillis();
				//调用await()
				cyclicBarrier.await();
				long endTime = System.currentTimeMillis();
				System.out.println(this.getName()+",sleep:"+this.sleep+" 等待了"+(endTime-startTime)+"(ms),开始吃饭了");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}

		void drive(){
			try {
				TimeUnit.SECONDS.sleep(sleep);
				long startTime = System.currentTimeMillis();
				//调用await()
				cyclicBarrier.await();
				long endTime = System.currentTimeMillis();
				System.out.println(this.getName()+",sleep:"+this.sleep+" 等待了"+(endTime-startTime)+"(ms),去一下景点的路上");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			this.eat();
			this.drive();
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10 ; i++) {
			new T("员工"+i,i).start();
		}
	}
}