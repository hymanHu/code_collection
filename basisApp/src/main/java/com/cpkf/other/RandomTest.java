package com.cpkf.other;

public class RandomTest {
	// 返回指定范围的随机数(m-n之间)
	public static void mToNRandom(int m, int n) {
		System.out.println((int)(m + Math.random() * (n - m)));
	}
	public static void main(String[] args) {
		mToNRandom(20, 999);
	}
}
