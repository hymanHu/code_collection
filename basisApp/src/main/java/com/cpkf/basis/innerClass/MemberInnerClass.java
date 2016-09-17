package com.cpkf.basis.innerClass;

/**  
 * Filename:    MemberInnerClass.java  
 * Description: 成员内部类
 * Copyright:   Copyright (c)2010  
 * Company:      
 * @author:     hj  
 * @version:    1.0  
 * Create at:   Mar 1, 2011 4:26:05 PM 
 */
public class MemberInnerClass {
	private String name = "hj";
	private String gender = "male";
	public void dis(){
		System.out.println("11111");
	}
	/* 
	 * method name   : display 
	 * description   : 外部类非静态方法访问内部类
	 * @author       : hj  
	 * @return       : void
	 * @param        : 
	 * modified      : Administrator ,  Mar 1, 2011
	 * @see          : 
	 */      
	public void display(){
		InnerClass in = new InnerClass();
		in.dis();
	}
	public class InnerClass{
		private String name = "jh";
		public void dis(){
			//同名属性优先指向内部类
			System.out.println(name);
			System.out.println(MemberInnerClass.this.name);
			System.out.println(gender);
		}
	}
	public static void main(String[] args) {
		MemberInnerClass mic = new MemberInnerClass();
		mic.display();
		System.out.println("============");
		//外部类对象产生内部类对象
		InnerClass in = mic.new InnerClass();
		in.dis();
	}
}
