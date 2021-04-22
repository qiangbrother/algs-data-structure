package com.jack.test.set;

import java.util.*;

/**
* @Description:
* @Author:         ZhangQiang
* @CreateDate:     2020/8/17 15:43
*/
public class SetTest {

	public static void main(String[] args) {

		HashSet<String> hashSet = new HashSet<>();
		LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
		TreeSet<String> treeSet = new TreeSet<>();
		List<String> list = Arrays.asList("A", "C", "B", "E", "D");
		list.stream().forEach(str ->{
			hashSet.add(str);
			linkedHashSet.add(str);
			treeSet.add(str);
		});
		System.out.println("hashSet : "+hashSet);
		System.out.println("linkedHashSet : "+linkedHashSet);
		System.out.println("treeSet : "+treeSet);
		LinkedList<String> strings = new LinkedList<>();
	}
}