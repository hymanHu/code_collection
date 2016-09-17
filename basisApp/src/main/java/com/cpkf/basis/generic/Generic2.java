package com.cpkf.basis.generic;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 定义限制泛型<T extends Collection>、<? super Double>等
 * 
 * @author Hyman
 */
public class Generic2<T extends Collection<?>> {

	private T obj;

	private Generic2(T obj) {
		this.obj = obj;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	public void displayType() {
		System.out.println(obj.getClass().getName());
	}

	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		// 使用通配符泛型<? extends Collection>
		@SuppressWarnings("rawtypes")
		Generic2<? extends Collection> generic2 = new Generic2<ArrayList>(list);
		generic2.displayType();

	}
}
