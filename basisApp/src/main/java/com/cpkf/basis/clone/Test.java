package com.cpkf.basis.clone;

import java.io.IOException;

public class Test {
	public static void main(String[] args) {
		Good good = new Good();
		good.setName("apple");
		good.setCount(11);
		People dh = new People();
		dh.setName("dh");
		dh.setAge(22);
		dh.setGood(good);
		try {
			People dh1 = (People) dh.clone();
			People dh2 = dh.deepClone();
			dh.getGood().setCount(22);
			System.out.println(dh1.getGood().getCount());
			System.out.println(dh2.getGood().getCount());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
