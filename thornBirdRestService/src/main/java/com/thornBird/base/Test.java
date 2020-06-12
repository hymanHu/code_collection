package com.thornBird.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

import javax.imageio.stream.FileImageInputStream;

import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.internal.connection.SocketChannelStream;

import io.netty.util.internal.ThreadExecutorMap;

public class Test {
	
	@Override
	public int hashCode() {
		Random random = new Random();
		return random.nextInt();
	}

	public static void sayHello() {
		System.out.println("Hello");
	}
	
	public void test() {
		System.out.println("test1");
	}
	
	public void test(String input) {
		System.out.println("test2: " + input);
	}
	
	@Override
	public String toString() {
		return "test";
	}
	
	public static String aaa (String input) {
		StringBuffer sb = new StringBuffer();
		for (int i = input.length() - 1; i >= 0; i --) {
			sb.append(input.charAt(i));
		}
		return sb.toString();
	}
	
	public static void bbb (String a, String b) {
		for (int i = 0; i < b.length(); i ++) {
			String temp = b.charAt(i) + "";
			int count = a.length() - a.replaceAll(temp, "").length();
			System.out.println(temp + ":" + count);
		}
	}
	
	static int a;
	int b;
	static int c;
	int d;
	public static int aMethod() {
		a++;
		return a;
	}
	public int bMethod() {
		d = b++;
		return b;
	}
	public static int cMethod() {
		c++;
		return c;
	}
	
	public static void fdsafas(int x) {
		System.out.println("input:" + x);
		
		int currentColumn = (int)(Math.log(new Double(x % 3 > 0 ? x/3 + 1 : x /3))/Math.log(2.0)  + 1);
		System.out.println("当前列数：" + currentColumn);
		
		int headLeft = 1;
		for (int i = 0; i < currentColumn - 1; i ++ ) {
//			System.out.println(i + 1 + "列有多少组3：" + Math.pow(2, (i)));
			headLeft += (int) (3 * Math.pow(2, i));
		}
		System.out.println("当前列左端数值：" + headLeft);
		
		int headRight = (int)(3 * Math.pow(2, (currentColumn - 1)));
		System.out.println("当前列右端数值：" + headRight);
		
		int currentNumber = headRight - (x - headLeft);
		System.out.println("当前数值：" + currentNumber);
		
	}
	
	public static String fdafa(char[] input) {
		List<String> list = new ArrayList<>();
		for (char temp : input) {
			list.add(temp + "");
		}
		Collections.reverse(list);
		
		return String.join(" ", list);
	}
	
	public static void main(String[] args) {
		System.out.println(fdafa(new char[] {'a', 'b', 'c', 'd'}));
		fdsafas(24);
		String aaa = "worldfdfasfdasworldvfsvsvdsworld";
		String b = "world";
		int length = aaa.length() - aaa.toLowerCase().replaceAll(b, "").length();
		System.out.println(length/b.length());
		
		try {
			Integer[] fdasa = new Integer[] {0,1,2,3,4};
			for (int i = 0; i < fdasa.length; i ++) {
//				Thread.sleep(300);
//				Thread.yield();
				System.out.println(i);
			}
		} catch (Exception e) {
		}
		
//		Scanner scanner = new Scanner(System.in);
//		System.out.println(scanner.next());
		
//		Test test = (Test)null;
//		Test test = new Test();
//		test.sayHello();
//		sayHello();
//		
//		System.out.println("-----" + test.hashCode());
//		
//		test.aMethod();
//		System.out.println(test.aMethod());
//		test.bMethod();
//		System.out.println(test.bMethod());
//		test.cMethod();
//		System.out.println(test.cMethod());
//		
//		int[] intArray = new int[] {1,2,3};
//		Integer[] integerArray = new Integer[] {1,2,3};
//		List<int[]> intList = Arrays.asList(intArray);
//		List<Integer> integerList = Arrays.asList(integerArray);
//		
//		bbb("123456abc12a", "3ad");
//		System.out.println(aaa("well come hyman"));
//		String a = "aa";
//		String b = "aa";
//		String c = new String("aa");
//		String d = new String("aa");
//		System.out.println(a == b);
//		System.out.println(a.equals(b));
//		System.out.println(a == c);
//		System.out.println(a.equals(c));
//		System.out.println(c == d);
//		System.out.println(c.equals(d));
//		System.out.println(5%3 + "**" + -5%3 + "**" + 5%-3 + "**" + -5%-3);
//		
//		char x = 'b';
//		int i = 0;
//		System.out.println(true ? x : 0);
//		System.out.println(true ? x : 65536);
//		System.out.println(true ? x : 111111111);
//		System.out.println(false ? i: x);
//		
//		HashMap<String, String> hashMap = new HashMap<>();
//		hashMap.put("a", "asdffas");
//		hashMap.put("c", "dasfda");
//		hashMap.put("b", "dfasfa");
//		hashMap.put(null, null);
////		hashMap.put("a", "rewreqwre");
//		
//		TreeMap<String, String> treeMap = new TreeMap<>();
//		treeMap.put("b", "dfasfa");
//		treeMap.put("c", null);
//		treeMap.put("a", "asdffas");
//		treeMap.put("a", "ewqeqw");
//		
//		System.out.println("-----------");
//		
//		Map<String, String> mp = new HashMap<String, String>();
//		for (int i1=0; i1<10; i1++) {
//			mp.put("key" + i1, "value" + i1);
//		}
//		
//		for (Map.Entry<String, String> entry : mp.entrySet()) {
//			System.out.println(entry.getKey() + "-" + entry.getValue());
//		}
	}
}
