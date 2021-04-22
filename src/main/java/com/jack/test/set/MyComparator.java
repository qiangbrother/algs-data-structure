package com.jack.test.set;

import java.util.Comparator;
import java.util.Hashtable;

/**
* @Description:    
* @Author:         ZhangQiang
* @CreateDate:     2020/8/18 10:12
*/
public class MyComparator implements Comparator {
	@Override
	public int compare(Object o1, Object o2) {
		return 0;
	}

	public static void main(String[] args) {
		Hashtable<String,String> hashtable = new Hashtable<>();
		String put = hashtable.put("1", "1");
		System.out.println(put);
		String put1 = hashtable.put("1", "2");
		System.out.println(put1);
	}
}