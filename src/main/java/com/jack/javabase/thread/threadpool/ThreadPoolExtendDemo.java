package com.jack.javabase.thread.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description
 * @author: zq
 * @create: 2020-10-21 11:01
 **/
public class ThreadPoolExtendDemo {
	static class Task implements Runnable {
		String name;

		public Task(String name) {
			this.name = name;
		}

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + "处理" + this.name);
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		@Override
		public String toString() {
			return "Task{" +
					"name='" + name + '\'' +
					'}';
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(10,10,60,TimeUnit.SECONDS,
				new ArrayBlockingQueue<>(1), Executors.defaultThreadFactory(),(r,executors) ->{
			//自定义饱和策略
			//记录无法处理的任务
			System.out.println("无法处理的任务：" + r.toString());
		}){
			@Override
			protected void beforeExecute(Thread t, Runnable r) {
				System.out.println(System.currentTimeMillis() + "," + t.getName() + ",开始执行任务:" + r.toString());
			}

			@Override
			protected void afterExecute(Runnable r, Throwable t) {
				System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + ",任务:" + r.toString() + "，执行完毕!");
			}

			@Override
			protected void terminated() {
				System.out.println(System.currentTimeMillis() + "," + Thread.currentThread().getName() + "，关闭线程池!");
			}
		};
		for (int i = 0; i < 10; i++) {
			executor.execute(new Task("任务-" + i));
		}
		TimeUnit.SECONDS.sleep(1);
		executor.shutdown();
	}


}
