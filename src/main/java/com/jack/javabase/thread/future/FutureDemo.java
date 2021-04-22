package com.jack.javabase.thread.future;

import java.util.concurrent.*;

/**
 * @description 异步任务处理
 * @author: zq
 * @create: 2020-10-21 14:18
 **/
public class FutureDemo {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
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
		executorService.shutdown();
		TimeUnit.SECONDS.sleep(1);
		//取消执行任务。如果任务取消了，那么使用get()方法获取不到值；isCancelled()和isDone()来查看线程的状态
		result.cancel(false);
		System.out.println(result.isCancelled());
		System.out.println(result.isDone());
		System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName());
		System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",结果："+result.get());

	}
}