package com.thornBird.base.algorithms;

import java.util.Arrays;
import java.util.List;

public class Trees {
	
	public static String aa (Integer[] input) {
		List<Integer> list = Arrays.asList(input);
		
		int index = 0;
		int length = 0;
		int count = 0;
		for (int i = 1; i <= 100; i ++) {
			if (i % 2 == 0) {
				if (list.contains(i) || i == 100) {
					System.out.print("坑,");
					if (count > length) {
						length  = count - 1;
						index = i - 2;
					}
					count = 0;
				} else {
					System.out.print(i + ",");
				}
				count += 1;
			}
		}
		
		System.out.println("------------");
		count = 0;
		for (int i = 1; i <= 100; i ++) {
			if (i % 2 != 0) {
				if (list.contains(i) || i == 99) {
					System.out.print("坑,");
					if (count > length) {
						length  = count - 1;
						index = i - 2;
					}
					count = 0;
				} else {
					System.out.print(i + ",");
				}
				count += 1;
			}
		}
		
		System.out.println("------------");
		return index + "----" + length;
	}
	
	public static void main(String[] args) {
		System.out.println(Trees.aa(new Integer[] {1,10,15,60,70,100}));
	}

}
