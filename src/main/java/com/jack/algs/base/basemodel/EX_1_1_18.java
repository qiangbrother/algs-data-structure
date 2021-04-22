package com.jack.algs.base.basemodel;
/**
* @Description:
* @Author:         JackQ
* @CreateDate:     2020/6/4 21:57
*/
public class EX_1_1_18 {
	public static void main(String[] args) {
		int mystery = mystery(2, 25);
		System.out.println(mystery);
		int mystery1 = mystery(3, 11);
		System.out.println(mystery1);
	}

	public static int mystery(int a,int b){
		if (b == 0){
			return 0;
		}
		if(b % 2 ==0) {
			mystery(a+a,b/2);
		}
		return mystery(a+a,b/2);
	}
}