package com.jack.javabase.thread.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @description
 * @author: zq
 * @create: 2020-10-21 18:19
 **/
public class AtomicDemo {

	//原始账户19
	static int accountMoney = 19;

	static AtomicReference<Integer> money =  new AtomicReference<>(accountMoney);

	static void recharge(){
		for (int i = 0; i < 2; i++) {
			new Thread(() -> {
				for (int j = 0; j < 5; j++) {
					Integer m = money.get();
					if (m == accountMoney){
						if (money.compareAndSet(m,m+20)){
							System.out.println("当前余额：" + m + "，小于20，充值20元成功，余额：" + money.get() + "元");
						}
					}
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}).start();
		}
	}

	/**
	 * 模拟用户消费
	 */
	static void consume() throws InterruptedException {
		for (int i = 0; i < 5; i++) {
			Integer m = money.get();
			if (m > 20) {
				if (money.compareAndSet(m, m - 20)) {
					System.out.println("当前余额：" + m + "，大于10，成功消费10元，余额：" + money.get() + "元");
				}
			}
			//休眠50ms
			TimeUnit.MILLISECONDS.sleep(50);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		recharge();
		consume();
	}


}