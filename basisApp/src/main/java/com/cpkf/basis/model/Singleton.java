package com.cpkf.basis.model;

/**  
 * Filename:    Singleton.java  
 * Description: 双锁机制单例模式
 * Copyright:   Copyright (c)2010  
 * Company:      
 * @author:     hj  
 * @version:    1.0  
 * Create at:   Mar 1, 2011 4:17:03 PM 
 */
public class Singleton {
	private static Singleton si = null;
	private Singleton(){
	}
	public static Singleton getsi(){
		if(si == null){
			synchronized (Singleton.class) {
				if(si == null){
					si = new Singleton();
				}
			}
		}
		return si;
	}
	public static void main(String[] args) {
		Singleton si0 = Singleton.getsi();
		Singleton si1 = Singleton.getsi();
		System.out.println(si0 == si1);
	}
}
