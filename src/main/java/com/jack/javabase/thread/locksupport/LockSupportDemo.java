package com.jack.javabase.thread.locksupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @Description:
 * @Author: ZhangQiang
 * @CreateDate: 2020/10/20 14:11
 */
public class LockSupportDemo {

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(System.currentTimeMillis() + ":获取线程锁===");
			LockSupport.park();
			System.out.println(System.currentTimeMillis() + ":释放线程锁");
		});
		t1.start();
		TimeUnit.SECONDS.sleep(2);
		LockSupport.unpark(t1);
		System.out.println(System.currentTimeMillis() + ":LockSupport finish");
	}

}