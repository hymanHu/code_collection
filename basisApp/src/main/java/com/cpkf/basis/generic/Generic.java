package com.cpkf.basis.generic;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义泛型类
 * @author Hyman
 */
public class Generic<T> {

	// 泛型成员变量
	private T obj;

	private Generic(T obj) {
		this.obj = obj;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	public void showType() {
		System.out.println(String.format("%s%s", "T的类型:", obj.getClass().getName()));
	}
	
	// 泛型方法
	@SuppressWarnings("hiding")
	public <T> void showType(T obj) {
		System.out.println(String.format("%s%s", "T的类型:", obj.getClass().getName()));
	}
	
	@SuppressWarnings("hiding")
	public <T> T ifThenElse(boolean a, T b, T c) {
		return a ? b : c;
	}
	
	public static void main(String[] args) {
		Generic<Integer> generic = new Generic<Integer>(88);
		generic.showType();
		generic.showType(new String("fff"));
		System.out.println(generic.ifThenElse(false, new String("sss"), new Integer(33)));
		
		// 泛型最多的例子来源于集合框架
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("a", new Integer(22));
		Integer a = map.get("a");
		System.out.println(a);
	}
}
