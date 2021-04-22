package com.jack.jvm;


import java.util.ArrayList;
import java.util.List;

public class OOMObjectTest {

	public byte[] placeholder = new byte[64 * 1024];

	public static void fillHeap(int num) throws InterruptedException {
		List<OOMObjectTest> list = new ArrayList<>();
		for (int i=0;i<num;i++){
			Thread.sleep(50);
			list.add(new OOMObjectTest());
		}
		System.gc();
	}

	public static void main(String[] args) throws InterruptedException {
		fillHeap(100);
	}
}