package com.cpkf.basis.innerClass;

/**  
 * Filename:    StaticInnerClass.java  
 * Description: 静态内部类
 * Copyright:   Copyright (c)2010  
 * Company:      
 * @author:     hj  
 * @version:    1.0  
 * Create at:   Mar 1, 2011 4:50:59 PM 
 */
public class StaticInnerClass {
	private String name = "hj";
	private static String gender = "male";
	public static void display(){
		System.out.println("hah");
	}
	public static class InnerClass {
		public String name = "jh";
		public static int i = 10;
		public void dis(){
			//静态内部类只能访问外部静态成员和方法
			System.out.println(name);
			System.out.println(gender);
			display();
		}
	}
	public void dis() {
		//外部类可直接访问静态内部类静态成员和方法，非静态的需要产生对象
		System.out.println(InnerClass.i);
		InnerClass ic = new InnerClass();
		System.out.println(ic.name);
		System.out.println("========");
		ic.dis();
	}
	public static void main(String[] args) {
		StaticInnerClass sic = new StaticInnerClass();
		sic.dis();
	}
}
