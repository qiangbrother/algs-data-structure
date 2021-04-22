package com.jack.jvm.selectthree;
/**
* @Description:    引用技术算法GC回收测试
*@Author:         JackQ
* @CreateDate:     2020/6/10 20:29
*/
public class ReferenceCountingGC {

	public Object instance = null;

	private static final  int _1MB = 1024 * 1024;

	private byte[] bigSize = new byte[2 * _1MB];

	public static void testGC(){
		ReferenceCountingGC objA = new ReferenceCountingGC();
		ReferenceCountingGC objB = new ReferenceCountingGC();
		objA.instance = objB;
		objB.instance = objA;

		objA = null;
		objB = null;

		System.gc();
	}

	public static void main(String[] args) {
		testGC();
	}
}