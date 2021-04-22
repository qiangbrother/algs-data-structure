package com.jack.test.init;
/**
* @Description:
 * 	 * 1 继承ClassLoader抽象类
 * 	 * 2 重写findClass()
 * 	 * 3 重写的findClass()中调用defineClass()
* @Author:         ZhangQiang
* @CreateDate:     2020/8/24 9:26
*/
public class MyClassLoader extends ClassLoader{

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {

		return super.findClass(name);
	}
}