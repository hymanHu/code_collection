package com.cpkf.other;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculateAbout {
	public static void main(String[] args) {
		BigDecimal a = new BigDecimal(5);
		BigDecimal b = new BigDecimal(3);
		// 保留四位小数，第五位省略
		System.out.println(a.divide(b, 4, RoundingMode.FLOOR));
	}
}
