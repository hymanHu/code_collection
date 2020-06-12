package com.thornBird.base.algorithms;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Description: Print out the binary equivalent of a number, you input is an integer variable. ---- 输入十进制，输出二进制
 * @author: HymanHu
 * @date: 2019-09-06 19:25:15
 */
public class DecimalToBinary {
	
	public static void main(String[] args) {
//		Scanner scanner = new Scanner(System.in);
//		System.out.println("Please input: ");
//		Integer input = scanner.nextInt();
//		System.out.println(Integer.toBinaryString(input));
//		System.out.println(Integer.parseInt("1001"));
		
		int input  = 23;
		System.out.println(Integer.toBinaryString(input));
		
		for(int i = 31; i >= 0; i--) {
			System.out.print(input >>> i & 1);
		}
		System.out.println();
		
		String result = "";
		while (input != 0) {
			result = input % 2 + result;
			input /= 2;
		}
		System.out.println(result);
		
		input = 23;
		int result2 = 0;
		int index = 0;
		while (input != 0) {
			result2 += (int) ((input % 2) * Math.pow(10, index));
			input /= 2;
			index ++;
		}
		System.out.println(result2);
	}
}
