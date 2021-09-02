package com.jack.algs.base.basemodel;

/**
* @Description:    基础模型练习 1.1.13
* @Author:         JackQ
* @CreateDate:     2020/6/4 20:43
*/
public class EX_1_1_13 {

	public static void main(String[] args) {
		int[][] a = {{1,2,3,4},{4,5,6,7},{7,8,9,4}};
		switchTwoArray(a);
	}
	/**
	 * 1.1.13
	 *打印一个M行N列的二维数组转置（交换行和列）
	 */
	public static void switchTwoArray(int[][] twoArray) {
		System.out.println(twoArray[0].length);
		//获取行数
		int row = twoArray.length;
		//获取列数
		int col = twoArray[0].length;
		int[][] newTwoArray = new int[col][row];
		System.out.println("==============转换前的数组===============");
		for (int i=0;i<twoArray.length;i++){
			for (int j=0;j<twoArray[i].length;j++){
				System.out.print(twoArray[i][j]+"  ");
			}
			System.out.println();
		}
		//转换数组
		for (int i=0;i<twoArray.length;i++){
			for (int j=0;j<twoArray[i].length;j++){
				newTwoArray[j][i] = twoArray[i][j];
			}
		}

		System.out.println("**************转换后的数组********************");
		for (int i=0;i<newTwoArray.length;i++){
			for (int j=0;j<newTwoArray[i].length;j++){
				System.out.print(newTwoArray[i][j]+"  ");
			}
			System.out.println();
		}

	}
}