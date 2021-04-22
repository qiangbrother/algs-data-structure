package com.jack.javabase.thread.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @description
 * @author: zq
 * @create: 2020-10-21 14:37
 **/
public class FutureTaskDemo {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		FutureTask<Integer> futureTask = new FutureTask<>(() -> {
			System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",start!");
			TimeUnit.SECONDS.sleep(5);
			System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",end!");
			return 10;
		});
		System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName());
		new Thread(futureTask).start();
		System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName()+",结果："+futureTask.get());

	}
}