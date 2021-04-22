package com.jack.algs.base.basemodel;

public class EX_1_1_14 {

    public static void main(String[] args) {

        lg(32);
    }



    /**
     * 1.1.14
     * 输入一个整数，返回一个不大于Log2  N 的最大整数
     * @param n 整数n
     */
    public static void lg(int n){
        int count = 2;
        int max = 0;
        while(n>=count){
           count = count *2;
           max++;
        }
        System.out.println("以2为底数的对数的最大整数为："+max);
    }




}