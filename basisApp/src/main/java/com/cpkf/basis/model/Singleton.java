package com.cpkf.basis.model;

/**
 * Filename: Singleton.java Description: 双锁机制单例模式 Copyright: Copyright (c)2010
 * Company:
 * 
 * @author: hj
 * @version: 1.0 Create at: Mar 1, 2011 4:17:03 PM
 */
public class Singleton {
	private Singleton() {
	}

	// 懒惰的单态模式
	// 单态模式会产生static属性，这样在程序加载的时候就会占用静态区空间，非常浪费
//	private static Singleton si = new Singleton();
//	public static Singleton getSi() {
//		return si;
//	}

	// 在懒惰单态模式基础上，改进为延迟加载单态模式
	// 延迟加载单态模式线程不安全，当多线程访问时，实际上产生了多个Singleton对象；
//	private static Singleton si = null;
//	public static Singleton getSi() {
//		// 如果对象为空，则创建一个对象，这样避免了在加载时候占用静态区空间
//		if (si == null) {
//			si = new Singleton();
//		}
//		return si;
//	}

	// 在延迟加载单态模式基础上，给该类的getSi方法加上Synchronized修饰符
	// 这样设计，保证了线程安全，但是效率低下，多线程只能依次排队访问该方法；
//	private static Singleton si = null;
//	public synchronized static Singleton getSi() {
//		// 如果对象为空，则创建一个对象，这样避免了在加载时候占用静态区空间
//		if (si == null) {
//			si = new Singleton();
//		}
//		return si;
//	}

	// 为了解决这个问题，我们用同步块来实现
	// 可是，当多个线程进入getSi方法后，同样存在多创建对象的情况
//	private static Singleton si = null;
//	public static Singleton getSi() {
//		// 如果对象为空，则创建一个对象，这样避免了在加载时候占用静态区空间
//		if (si == null) {
//			// 创建对象这条语句产生了线程不安全，在同步块中，传入的对象为synchronized的Class对象代替实际对象
//			synchronized (Singleton.class) {
//				si = new Singleton();
//			}
//		}
//		return si;
//	}

	// 双锁机制
	private static Singleton si = null;
	public static Singleton getSi() {
		if (si == null) {
			synchronized (Singleton.class) {
				if (si == null) {
					si = new Singleton();
				}
			}
		}
		return si;
	}

	public static void main(String[] args) {
		Singleton si0 = Singleton.getSi();
		Singleton si1 = Singleton.getSi();
		System.out.println(si0 == si1);
	}
}
