package com.jack.algs.base.basemodel;


public class EX_1_1_24 {

	public static void main(String[] args) {
		int euclid = euclid(4, 12);
		System.out.println(euclid);
	}

	/**
	 * 欧几里德算法：
	 * gcd(a,b) = gcd(b,a mod b)
	 * 简单来讲，就是通过递归 用除数来对余数进行取余，余数为0的时候，那么当前除数为最大公约数
	 * 例如
	 * 17%5 余数为2
	 * 5%2  余数1
	 * 2%1  余数0
	 * 那么 1 就是17 和 5 的最大公约数
	 * 计算两个数的最大公约数
	 * @param a
	 * @param b
	 * @return
	 */
	public static int euclid(int a,int b){
		System.out.println(a+" "+b);
		//取公约数 应该是要大的对小的进行取余
		if(a<b){
			int temp = a;
			a = b;
			b = temp;
		}
		int i = a % b;
		if(i ==0 ){
			return b;
		}else {
			return euclid(b,i);
		}
	}
}