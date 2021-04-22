package com.jack.javabase.thread;

import java.util.concurrent.*;

/**
 * @Description: 饥饿 死锁
 * @Author: ZhangQiang
 * @CreateDate: 2020/10/19 9:54
 */
public class HungryLock {

	private static ExecutorService single = Executors.newSingleThreadExecutor();

	public static class AnotherCallable implements Callable<String> {
		@Override
		public String call() throws Exception {
			System.out.println("AnotherCallable");
			return "another success";
		}
	}

	public static class MyCallable implements Callable<String> {
		@Override
		public String call() throws Exception {
			System.out.println("My Callable");
			Future<String> submit = single.submit(new AnotherCallable());
			return "another success:" + submit.get();
		}
	}

	/**
	 * 从代码分析，在27行和35行，
	 * 因为在同一线程中AnotherCallable 一直没有得到执行的机会，一直处于等待的状态
	 *
	 * @param args
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		MyCallable myCallable = new MyCallable();
		Future<String> submit = single.submit(myCallable);
		System.out.println(submit.get());
		System.out.println("over");
		single.shutdown();
	}

}