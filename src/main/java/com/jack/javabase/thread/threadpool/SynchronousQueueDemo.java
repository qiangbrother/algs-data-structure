package com.jack.javabase.thread.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @description SynchronousQueue：相对比较特殊，任务需要其他的线程来处理，如果其他线程没有空进行处理，那么将会创建新的
 * 线程来进行处理，如果任务很多，可以存在创建很多线程，将资源耗尽，触发OOM
 * @author: zq
 * @create: 2020-10-21 10:16
 **/
public class SynchronousQueueDemo {

	public static void main(String[] args) {

		ExecutorService executorService = Executors.newCachedThreadPool();
		for (int i = 0; i < 50; i++) {
			int j =i;
			String taskName = "任务"+j;
			executorService.execute(() ->{
				System.out.println(Thread.currentThread().getName()+taskName+"处理完成！");
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}

		executorService.shutdown();
	}
}