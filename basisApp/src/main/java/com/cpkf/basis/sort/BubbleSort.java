package com.cpkf.basis.sort;

public class BubbleSort {
	
	public void bubbleSort (String[] data) {
		for (int i = 0; i < data.length; i ++) {
			for (int j = 0; j < data.length - i - 1; j ++) {
				if (Integer.parseInt(data[j]) > Integer.parseInt(data[j + 1])) {
					String temp = data[j];
					data[j] = data[j + 1];
					data[j + 1] = temp;
				}
			}
		}
	}
	
	public void printResult(String[] data) {
		for (String member : data) {
			System.out.print(member + ",");
		}
	}
	
	public static void main(String[] args) {
		String[] data = new String[]{"11","66","22","0","55","23","24","32"};
		BubbleSort bubbleSort = new BubbleSort();
		bubbleSort.bubbleSort(data);
		bubbleSort.printResult(data);
	}
}
