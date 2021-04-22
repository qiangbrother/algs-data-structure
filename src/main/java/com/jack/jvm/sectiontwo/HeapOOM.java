package com.jack.jvm.sectiontwo;

import java.util.ArrayList;
import java.util.List;

/**
* @Description:    Java堆内存溢出测试
* @Author:         JackQ
* @CreateDate:     2020/6/8 21:02
*/
public class HeapOOM {

	public static void main(String[] args) {
		test();
	}

	/**
	 * VM Args:-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
	 * */
	public static void test(){
		List<OOMObject> list = new ArrayList<>();
		while (true){
			list.add(new OOMObject());
		}
	}

	static class  OOMObject{

	}
}