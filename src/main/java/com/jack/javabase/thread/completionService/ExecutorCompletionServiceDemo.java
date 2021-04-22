package com.jack.javabase.thread.completionService;

import java.util.concurrent.*;

/**
 * @description
 * @author: zq
 * @create: 2020-10-21 15:19
 **/
public class ExecutorCompletionServiceDemo {

	static class GoodsModel{
		//商品名称
		String name;
		//开始购物时间
		Long starTime;
		//送到时间
		Long endTime;

		public GoodsModel(String name, Long starTime, Long endTime) {
			this.name = name;
			this.starTime = starTime;
			this.endTime = endTime;
		}

		@Override
		public String toString() {
			return name + ",下单时间[" + this.starTime+" , "+this.endTime+" ],耗时："+ (endTime-starTime);
		}
	}

	static void moveUp(GoodsModel goodsModel) throws InterruptedException {
		TimeUnit.SECONDS.sleep(5);
		System.out.println("将商品搬上楼，商品信息:" + goodsModel);
	}

	/**
	 * 下单
	 * @param name 商品名称
	 * @param time 购物花费时间
	 * @return
	 */
	static Callable<GoodsModel> buyGoods(String name, Long time){
		return () ->{
			long startTime = System.currentTimeMillis();
			System.out.println(startTime+ "购买" + name +"下单了！");
			TimeUnit.SECONDS.sleep(time);
			long endTime = System.currentTimeMillis();
			System.out.println(endTime+ name + "送到了！");
			return new GoodsModel(name,startTime,endTime);
		};
	}
	//-------------------------------------------------------------------------------------------
	//存在的问题，在将洗衣机和冰箱送上楼的时候，如果先将冰箱送上楼，那么中间有3秒是空闲等待
	//这个时候我们不知道到底是谁先到，那么问题来了 该怎么处理呢？这个时候使用CompletionService
	//改造：使用ExecutorCompletionService
	//-------------------------------------------------------------------------------------------
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		long st = System.currentTimeMillis();
		System.out.println(st+":开始购物了！");
		//创建一个线程池，异步下单
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		ExecutorCompletionService<GoodsModel> completionService =
				new ExecutorCompletionService<GoodsModel>(executorService);
		//异步下单洗衣机
		//Future<CompletionServiceDemo.GoodsModel> xyjFuture = executorService.submit(buyGoods("洗衣机", 2L));
		completionService.submit(buyGoods("洗衣机", 2L));
		//异步下单冰箱
		//Future<CompletionServiceDemo.GoodsModel> bxFuture = executorService.submit(buyGoods("冰箱", 5L));
		completionService.submit(buyGoods("冰箱", 5L));
		executorService.shutdown();

		int goodsCount =2;

		for (int i = 0; i < goodsCount ; i++) {
			GoodsModel goodsModel = completionService.take().get();
			moveUp(goodsModel);
		}

		long et = System.currentTimeMillis();
		System.out.println(et + "货物已送到家里咯，哈哈哈！");
		System.out.println("总耗时:" + (et - st));
	}
}