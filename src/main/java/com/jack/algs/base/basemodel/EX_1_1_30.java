package com.jack.algs.base.basemodel;


/**
* @Description:   一个N*N的二维数组，当i和j互质的时候，a[i][j]为ture
* @Author:         JackQ
* @CreateDate:     2020/6/5 21:03
*/
public class EX_1_1_30 {

	public static void main(String[] args) {
		String[][] strings = co_prime(new String[5][5]);
		for (String[] a : strings){
			for (String b : a){
				System.out.print(b +"  ");
			}
			System.out.println();
		}
	}

	public static String[][] co_prime(String[][] a){
		for (int i=1;i<=a.length;i++){
			for (int j=1;j<=a[i-1].length;j++){
				//取两个数的最大公约数
				int euclid = EX_1_1_24.euclid(i, j);
				if (euclid==1){
					a[i-1][j-1] = "true";
				}else{
					a[i-1][j-1] = "false";
				}
			}
		}
		return a;

	}
}