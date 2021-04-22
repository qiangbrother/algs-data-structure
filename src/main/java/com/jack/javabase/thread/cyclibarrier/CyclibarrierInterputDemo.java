package com.jack.javabase.thread.cyclibarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @description
 * @author: zq
 * @create: 2020-10-20 19:27
 **/
public class CyclibarrierInterputDemo {

	public static CyclicBarrier cyclicBarrier = new CyclicBarrier(10);

	public static class T extends Thread {
		int sleep;

		public T(String name, int sleep) {
			super(name);
			this.sleep = sleep;
		}

		@Override
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(sleep);
				long startTime = System.currentTimeMillis();
				System.out.println(this.getName() + "到了！");
				//调用await()
				cyclicBarrier.await();
				/*
				* 在try-catch代码块中的 await()后面的代码不在执行
				* */
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
			System.out.println(this.getName() + ",sleep:" + this.sleep);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			int sleep = 0;
			if (i==9){
				sleep=9;
			}
			T t = new T("员工" + i, sleep);
			t.start();
			if (5==i){
				TimeUnit.SECONDS.sleep(1);
				System.out.println(t.getName()+",有点急事，先走了！");
				t.interrupt();
				TimeUnit.SECONDS.sleep(2);

			}
		}
	}

}