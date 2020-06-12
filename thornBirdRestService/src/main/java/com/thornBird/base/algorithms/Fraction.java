package com.thornBird.base.algorithms;

/**
 * @Description: Give a fraction, reduce it to lowest items, for example 12/28 ----> 3/7. Java实现分数约分
 * @author: HymanHu
 * @date: 2019-08-22 22:08:07
 */
public class Fraction {
	// 分子
	private int numerator;
	// 分母
	private int denominator;
	// 符号
	private String sign;

	public Fraction(int numerator, int denominator) {
		if (denominator == 0) {
			throw new IllegalArgumentException("The denominator can not be zero.");
		}
		
		this.sign = numerator * denominator < 0 ? "-" : "";
		this.numerator = numerator < 0 ? -numerator : numerator;
		this.denominator = denominator < 0 ? -denominator : denominator;
	}

	@Override
	public String toString() {
		if (this.numerator == 0) {
			return "0";
		} else if (this.numerator % this.denominator == 0) {
			return String.format("%s%d", this.sign, this.numerator / this.denominator);
		} else {
			return String.format("%s%d/%d", this.sign, this.numerator, this.denominator);
		}
	}
	
	// 最大公约数
	public static int getGCD(int a, int b) {
		int max = a > b ? a : b;
		int min = a < b ? a : b;
		
		if (max % min != 0) {
			return getGCD(min, max % min);
		} else {
			return min;
		}
	}
	
	// 最小公倍数
	public static int getLCM(int a, int b) {
		return a * b / getGCD(a, b);
	}
	
	// 循环除以i，得出最小约分
	public void reduceToLowest1() {
		int i = this.numerator > this.denominator ? this.denominator : this.numerator;
		if (i <= 1) {
			return;
		}
		for (; i >= 1; --i) {
			if (this.numerator % i == 0 && this.denominator % i == 0) {
				this.numerator /= i;
				this.denominator /= i;
			}
		}
	}
	
	// 获得最大公约数
	public void reduceToLowest2() {
		int gcd = getGCD(this.numerator, this.denominator);
		this.numerator /= gcd;
		this.denominator /= gcd;
	}
	
	public static void main(String[] args) {
		System.out.println(getGCD(6, 32));
		System.out.println(getLCM(6, 4));
		
		Fraction fraction = new Fraction(-36, 128);
		fraction.reduceToLowest1();
		System.out.println(fraction.toString());
		fraction = new Fraction(-38, 128);
		fraction.reduceToLowest2();
		System.out.println(fraction.toString());
		
	}
}
