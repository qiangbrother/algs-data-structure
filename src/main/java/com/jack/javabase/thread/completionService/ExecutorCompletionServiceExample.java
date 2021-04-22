package com.jack.javabase.thread.completionService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * @description
 * @author: zq
 * @create: 2020-10-21 15:40
 **/
public class ExecutorCompletionServiceExample {

	/**
	 *
	 * @param e  线程池
	 * @param solvers  处理的任务
	 * @param consumer 消费
	 * @param <T>
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static <T> void solve(Executor e, Collection<Callable<T>> solvers, Consumer<T> consumer) throws InterruptedException, ExecutionException {
		ExecutorCompletionService<T> completionService = new ExecutorCompletionService<>(e);
		for (Callable<T> s : solvers){
			completionService.submit(s);
		}
		for (int i = 0; i < solvers.size(); i++) {
			T t = completionService.take().get();
			if (t != null){
				consumer.accept(t);
			}
		}
	}

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		List<Callable<Integer>> list = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			int j =i * 2;
			list.add(() ->{
				TimeUnit.SECONDS.sleep(j);
				return j;
			});
		}

		solve(executorService,list,(r) -> {
			System.out.println(System.currentTimeMillis()+": "+r);
		});

		executorService.shutdown();
	}
}