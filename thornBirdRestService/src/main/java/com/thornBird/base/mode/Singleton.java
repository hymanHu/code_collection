package com.thornBird.base.mode;

/**
 * @Description:Singleton, 双锁机制单例模式 
 * @author: HymanHu
 * @date: 2019-08-03 21:48:28
 */
public class Singleton {
	private static Singleton singleton = null;

	private Singleton() {
	}
	
	public static Singleton getSingleton() {
		if (singleton == null) {
			synchronized (Singleton.class) {
				if (singleton == null) {
					singleton = new Singleton();
				}
			}
		}
		return singleton;
	}
	
	public static void main(String[] args) {
		Singleton si1 = Singleton.getSingleton();
		Singleton si2 = Singleton.getSingleton();
		System.out.println(si1 == si2);
	}
}
