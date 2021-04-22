package com.jack.javabase.thread.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
/**
* @Description:
* @Author:         ZhangQiang
* @CreateDate:     2020/10/20 14:45
*/
public class SemaphoreReleaseDemo {
	static Semaphore semaphore = new Semaphore(1);

	public static class T1 extends Thread{

		public T1 (String name){
			super(name);
		}

		@Override
		public void run() {
			//解决锁在finally中导致许可证不一致的问题
			boolean success = false;
			try {
				semaphore.acquire();
				success = true;
				System.out.println(System.currentTimeMillis()+","+this.getName()+",获取许可,当前许可证数量："+semaphore.availablePermits());
				TimeUnit.SECONDS.sleep(5);
				System.out.println(System.currentTimeMillis()+","+this.getName()+",运行结束！！");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally {
				//这种方式释放锁 会存在问题，如果出现中断，许可证会一直增加
				//semaphore.release();
				if (success){
					semaphore.release();
				}
			}
			System.out.println(System.currentTimeMillis()+","+this.getName()+",当前许可证数量："+semaphore.availablePermits());
		}
	}

	public static void main(String[] args) throws InterruptedException {
		new T1("t1").start();
		TimeUnit.SECONDS.sleep(1);
		T1 t2 = new T1("t2");
		t2.start();
		TimeUnit.SECONDS.sleep(1);
		T1 t3 = new T1("t3");
		t3.start();

		t2.interrupt();
		t3.interrupt();
	}
}