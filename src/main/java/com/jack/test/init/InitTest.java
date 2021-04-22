package com.jack.test.init;

/**
* @Description:
* @Author:         ZhangQiang
* @CreateDate:     2020/8/15 14:52
*/
public class InitTest {

	//静态属性
	public static  int value = getFiled();

	//普通属性
	public String str = getOrdinaryFiled();

	//静态代码块
	static {
		System.out.println("static code");
	}

	//普通代码块
	{
		System.out.println("ordinary code");
	}

	public InitTest() {
		System.out.println("constructor init");
	}

	public static int getFiled(){
		System.out.println("static filed");
		return 123;
	}

	public String getOrdinaryFiled(){
		System.out.println("ordinary filed");
		return "string";
	}

	public static void main(String[] args) {
		new InitTest();
	}
}