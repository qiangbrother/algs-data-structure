package com.jack.javabase.thread.countdownlatch;

import cn.hutool.core.collection.CollUtil;
import edu.princeton.cs.algs4.In;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description: CountDownLatch工具类，并行处理任务
 * @Author: ZhangQiang
 * @CreateDate: 2020/10/20 16:09
 */
public class TaskDisposeUtils {

	//并行数量
	public static final int POOL_SIZE;

	static {
		POOL_SIZE = Integer.max(Runtime.getRuntime().availableProcessors(), 5);
	}

	public static <T> void dispose(List<T> taskList, Consumer<T> consumer) throws InterruptedException {
		dispose(true, POOL_SIZE, taskList, consumer);
	}

	public static <T> void dispose(boolean moreThread, int poolSize, List<T> taskList, Consumer<T> consumer) throws InterruptedException {
		if (CollUtil.isEmpty(taskList)) {
			return;
		}
		if (moreThread && poolSize > 1) {
			//取线程池和任务中的最小值作为线程池
			poolSize = Math.min(poolSize, taskList.size());
			ExecutorService executorService = null;
			try {
				executorService = Executors.newFixedThreadPool(poolSize);
				CountDownLatch countDownLatch = new CountDownLatch(taskList.size());
				for (T item : taskList) {
					executorService.execute(() -> {
						try {
							consumer.accept(item);
						} finally {
							countDownLatch.countDown();
						}
					});
				}
				countDownLatch.await();
			} finally {
				if (executorService != null) {
					executorService.shutdown();
				}
			}
		} else {
			for (T item : taskList) {
				consumer.accept(item);
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		List<Integer> list = Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList());

		TaskDisposeUtils.dispose(list, item -> {
			try {
				long startTime = System.currentTimeMillis();
				TimeUnit.SECONDS.sleep(item);
				long endTime = System.currentTimeMillis();
				System.out.println(System.currentTimeMillis() + ",任务" + item + "执行任务耗时：" + (endTime - startTime));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		System.out.println(list + "任务执行完毕");
	}
}