package com.cpkf.basis.innerClass;

/**  
 * Filename:    LocalInnerClass.java  
 * Description: 局部内部类
 * Copyright:   Copyright (c)2010  
 * Company:      
 * @author:     hj  
 * @version:    1.0  
 * Create at:   Mar 1, 2011 4:37:42 PM 
 */
public class LocalInnerClass {
	private String name = "hj";
	private String gender = "male";
	public void dis(){
		final int i = 99;
		//局部内部类不能加访问修饰符，也不能为静态的
		class InnerClass{
			private String name = "jh";
			InnerClass(){}
			public void dis(){
				System.out.println(name);
				System.out.println(LocalInnerClass.this.name);
				//局部内部类访问外部类的方法和属性，必须是常量
				System.out.println(i);
				System.out.println(gender);
			}
		}
		//在方法里面产生内部类的对象，外部类才能调用
		(new InnerClass()).dis();
	}
	public static void main(String[] args) {
		LocalInnerClass lic = new LocalInnerClass();
		lic.dis();
	}
}
