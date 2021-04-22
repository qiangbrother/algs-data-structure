package com.jack.jvm.sectiontwo;

import java.util.HashSet;
import java.util.Set;

/**
* @Description:    运行时常量导致内存溢出异常
* @Author:         JackQ
* @CreateDate:     2020/6/8 21:25
*/
public class RuntimeConstantPoolOOM {

	public static void main(String[] args) {
		test();
	}

	/**
	 * VM Args: -XX:PermSize=6m -XX:MaxPermSize=6m
	 */
	public static void test(){
		Set<String> set = new HashSet<>();
		short i = 0;
		while (true){
			set.add(String.valueOf(i++).intern());
		}
	}
}