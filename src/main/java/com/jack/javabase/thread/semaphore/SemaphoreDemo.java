package com.jack.javabase.thread.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
* @Description:
* @Author:         ZhangQiang
* @CreateDate: 2020/10/20 14:34
*/
public class SemaphoreDemo {

	static Semaphore semaphore = new Semaphore(2);

	public static class T1 extends Thread{

		public T1 (String name){
			super(name);
		}

		@Override
		public void run() {
			try {
				semaphore.acquire();
				System.out.println(System.currentTimeMillis()+","+this.getName()+",获取许可");
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally {
				System.out.println(System.currentTimeMillis()+","+this.getName()+",释放许可");
				//这种方式释放锁 会存在问题，如果出现中断，许可证会一直增加
				semaphore.release();
			}
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new T1("t-"+i).start();
		}
	}
}