package com.jack.algs.base.basemodel;

public class EX_1_1_27 {

	public static void main(String[] args) {
		double binomail = binomail(100, 50, 0.25, 0);
		System.out.println(binomail);
	}

	public static double binomail(int n,int k,double p,int count){
		double[][] doubles = new double[n+1][k+1];
		for (int i=0;i<=n;i++){
			for (int j=0;j<=k;j++){
				doubles[i][j] = -1;
			}
		}
		return binomail(doubles,n,k,p,count);
	}

	public static double binomail(double[][] a,int n,int k,double p,int count){
		if (n == 0 && k == 0) {
			return 1.0;
		}
		if (n < 0 || k < 0) {
			return 0.0;
		}

		if (a[n][k]==-1){
			count++;
			a[n][k] = (1.0-p)*binomail(a,n-1, k, p, count) + p*binomail(a,n-1, k-1, p, count);
		}

		return count;
	}
}