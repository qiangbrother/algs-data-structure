package com.jack.javabase.thread.test;

/**
 * @description
 * @author: zq
 * @create: 2020-10-23 15:50
 **/
public class MySleep {

	public static void simpleWait(long time){
		Object o = new Object();
		synchronized (o){
			try {
				o.wait(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}