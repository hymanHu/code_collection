package com.cpkf.basis.sort;

public class QuickSort {

	public void quickSort(String[] data, int start, int end) {
		if (start < end) {
			int middle = getMiddle(data, start, end);
			quickSort(data, start, middle - 1);
			quickSort(data, middle + 1, end);
		}
	}
	
	public int getMiddle(String[] data, int start, int end) {
		int i  = start;
		int j = end;
		String temp = data[start];
		while (i < j) {
			while (Integer.parseInt(temp) < Integer.parseInt(data[j]) && i < j) {
				j --;
			}
			if (i < j) {
				data[i] = data[j];
				i ++;
			}
			while (Integer.parseInt(temp) > Integer.parseInt(data[i]) && i < j) {
				i ++;
			}
			if (i < j) {
				data[j] = data[i];
				j --;
			}
		}
		data[i] = temp;
		return i;
	}
	
	public void printResult(String[] data){
		for (String member : data) {
			System.out.print(member + ",");
		}
	}
	
	public static void main(String[] args) {
		String[] data = new String[]{"11","66","22","0","55","23","4","32"};
		QuickSort quickSort = new QuickSort();
		quickSort.quickSort(data, 0, data.length - 1);
		quickSort.printResult(data);
	}
}
