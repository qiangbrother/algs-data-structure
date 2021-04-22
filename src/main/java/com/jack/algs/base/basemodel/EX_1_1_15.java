package com.jack.algs.base.basemodel;
/**
* @Description:    基础模型练习 1.1.15
* @Author:         JackQ
* @CreateDate:     2020/6/4 20:45
*/
public class EX_1_1_15 {

	public static void main(String[] args) {
		int[] a = {1,2,3,4,5,9,6,7,2,4,5,4,9};
		int M = 8;
		int[] histogram = histogram(a, M);
		for (int b : histogram){
			System.out.print(b+"  ");
		}
		System.out.println(1/2);
	}

	/**
	 *接受一个整型数组int[]a 和一个整数参数M 返回一个大小为M的数组，其中 M数组第i个元素的值为  整数i在参数数组中出现的次数。
	 * 如果 a 中的值均在0到M-1之间，返回数组中所有元素之和应该和a.length相等
	 * @param a
	 * @param M
	 * @return
	 */
	public static int[] histogram(int[] a,int M){
		int[] h = new int[M];
		int N = a.length;
		for (int i = 0; i < N; i++){
			if (a[i] < M){
				h[a[i]]++;
			}
		}
		return h;
	}

}
