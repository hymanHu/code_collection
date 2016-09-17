package com.cpkf.basis.reflex;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflexTest {
	public static void main(String[] args) {
		try {
			// 得到类模板
			Class pt0 = People.class;
			Class pt1 = Class.forName("com.cpkf.basis.reflex.People");

			Constructor constructor = pt0.getDeclaredConstructor(null);
			People people = (People) constructor.newInstance(null);

			// 遍历多有公共属性
			Field[] fields = pt0.getFields();
			for (Field field : fields) {
				System.out.println(field.getName());
			}
			// 操作似有属性
			Field field = pt0.getDeclaredField("name");
			field.setAccessible(true);
			field.set(people, "dh");
			System.out.println(people.getName());

			// 操作似有方法
			Method method = pt0.getDeclaredMethod("test", String.class);
			method.setAccessible(true);
			method.invoke(people, "hah");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
}
