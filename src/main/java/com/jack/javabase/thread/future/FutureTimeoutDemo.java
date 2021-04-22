package com.jack.javabase.thread.future;

import java.util.concurrent.*;

/**
 * @description  异步超时调用，如果超时了，那么阻塞的线程继续执行
 * @author: zq
 * @create: 2020-10-21 14:24
 **/
public class FutureTimeoutDemo {
	public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		Future<Integer> result = executorService.submit(() -> {
			try {
				System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",start!");
				TimeUnit.SECONDS.sleep(5);
				System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",end!");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return 10;
		});
		System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName());
		System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",结果："+result.get(3,
				TimeUnit.SECONDS));

	}
}