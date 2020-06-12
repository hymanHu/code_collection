package com.thornBird.base.interview;

import java.util.Arrays;

/**
 * @Description: Interview
 * @author: HymanHu
 * @date: 2019-08-07 14:11:31
 */
public class Interview {
	public void change(String a, char[] b) {
		a = "test ok";
		b[0] = 'd';
	}
	
	public static void main(String[] args) {
		Interview interview = new Interview();
		
		// String类型的参数str并没有改变(string参数 是final)，而char[]数组类型的参数则变化了（char[]是可以修改的）
		String a = new String("good");
		char b[] = {'a', 'b', 'c'};
		int c[] = {1,2,4,5};
		interview.change(a, b);
		// char[] 类型可以直接打印，但不能和String拼接在一起，其他类型数组不行，使用Arrays.toString打印
		System.out.println(a + "----" + Arrays.toString(b));
		System.out.println(b);
		
		System.out.println(0 == 0);
		System.out.println(1000 == 1000);
		Integer integerA = new Integer(0);
		Integer integerB = new Integer(0);
		System.out.println(integerA == integerB);
		
		for(int i = 0; i < c.length; i ++) {
		}
		for(int i = 0, len = c.length; i < len; i ++) {
		}
		for(int item : c){
		}
		
	}
}
