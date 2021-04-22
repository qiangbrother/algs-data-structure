package com.jack.javabase.thread.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description  线程池的基本使用方法
 * @author: zq
 * @create: 2020-10-21 09:50
 **/
public class ThreadPoolBaseDemo {

	static ThreadPoolExecutor executor =  new ThreadPoolExecutor(3,5,
			10, TimeUnit.SECONDS,
			new ArrayBlockingQueue<>(10),
			Executors.defaultThreadFactory(),
			new ThreadPoolExecutor.AbortPolicy());

	public static void main(String[] args) {
		for (int i = 0; i < 11; i++) {
			int j =i;
			String taskName = "任务"+j;
			executor.execute(() -> {
				try {
					TimeUnit.SECONDS.sleep(j);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()+taskName+"处理完成！");
			});
		}
	}
}