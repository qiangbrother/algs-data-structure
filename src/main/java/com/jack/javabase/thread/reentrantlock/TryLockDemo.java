package com.jack.javabase.thread.reentrantlock;

import javax.xml.transform.Source;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
* @Description:    trylock方法使用
* @Author:         ZhangQiang
* @CreateDate:     2020/10/20 10:04
*/
public class TryLockDemo {
	private static ReentrantLock lock = new ReentrantLock();


	public static class T extends Thread {
		public T(String name) {
			super(name);
		}

		@Override
		public void run() {
			try {
				System.out.println(System.currentTimeMillis()+":"+this.getName()+":开始获取锁");
				if (lock.tryLock(3,TimeUnit.SECONDS)){
					System.out.println(System.currentTimeMillis()+":"+this.getName()+":获取到锁");
					TimeUnit.SECONDS.sleep(5);
				}else {
					System.out.println(System.currentTimeMillis()+":"+this.getName()+":未获取锁");
				}
			}catch (InterruptedException e){
				e.printStackTrace();
			}finally {
				if (lock.isHeldByCurrentThread()){
					lock.unlock();
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		T t = new T("t1");
		T t1 = new T("t2");
		t.start();
		t1.start();
	}
}