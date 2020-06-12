package com.thornBird.base;

public class ForTest {

	public static void testA() {
		long start = System.currentTimeMillis();
		int i;
		int j;
		for (i = 0; i < 100; i ++) {
			for (j = 0; j < 1000; j ++) {
				System.out.print("");
			}
		}
		System.out.println(System.currentTimeMillis() - start);
	}
	public static void testB() {
		long start = System.currentTimeMillis();
		int i;
		int j;
		for (i = 0; i < 1000; i ++) {
			for (j = 0; j < 100; j ++) {
				System.out.print("");
			}
		}
		System.out.println(System.currentTimeMillis() - start);
	}
	
	public static void main(String[] args) {
		ForTest.testA();
		ForTest.testB();
	}
}
