package com.jack.javabase.thread.test;

/**
 * @description
 * @author: zq
 * @create: 2020-10-23 15:52
 **/
public class Test {
	static int i = 0;
	static Object object = new Object();
	public static void get() throws InterruptedException {
		while (true){
			System.out.println(System.currentTimeMillis()+",第"+ (i++)+" 次开始");
			//MySleep.simpleWait(1000);
			//Thread.sleep(1000);
			synchronized (object){
				object.wait(1000);
			}
			System.out.println(System.currentTimeMillis()+",第"+ (i)+" 次结束");

		}
	}

	public static void main(String[] args) throws InterruptedException {
		Test.get();
	}
}