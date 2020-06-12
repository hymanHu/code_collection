package com.thornBird.base.algorithms;

public class BigOutTest {
	
	public static int count(int inputCount) {
		int count = 0;
		int currentInputCount = 0;
		int memoryCount = 0;
		while(currentInputCount < inputCount) {
			int aCount = 0;
			int vCount = 0;
			int acvCount = 0;
			if (currentInputCount + 3 <= inputCount) {
				acvCount = count * 2;
				aCount = count + 3;
				vCount = count + memoryCount * 2;
			} else {
				aCount = count + 1;
				vCount = count + memoryCount;
			}
			
			if (currentInputCount < 3) {
				currentInputCount += 1;
				count += 1;
				System.out.print("A,");
			} else if (vCount >= acvCount && vCount >= aCount) {
				currentInputCount += 1;
				count = count + memoryCount;
				System.out.print("CtrlV,");
			} else if (acvCount >= vCount && acvCount >= aCount) {
				currentInputCount += 3;
				memoryCount = count;
				count = count * 2;
				System.out.print("CtrlA,CtrlC,CtrlV,");
			} else if (aCount >= vCount && aCount >= acvCount) {
				currentInputCount += 1;
				count = count + 1;
				System.out.print("A,");
			}
		}
		
		System.out.println();
		System.out.println("a总次数：" + count);
		return count;
	}
	
	public static void main(String[] args) {
		BigOutTest.count(3);
		BigOutTest.count(7);
		BigOutTest.count(11);
	}
}
