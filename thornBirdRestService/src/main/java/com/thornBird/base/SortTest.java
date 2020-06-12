package com.thornBird.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.thornBird.base.bean.Car;

public class SortTest {
	
	public static void main(String[] args) {
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		Random random = new Random();
		
		List<String> strings = new ArrayList<>();
		List<Integer> ints = new ArrayList<>();
		List<Car> cars = new ArrayList<>();
		for (int i = 0; i < 10; i ++) {
			Car car = new Car();
			car.setName(String.valueOf(base.charAt(random.nextInt(base.length()))) + i);
			car.setCount(i);
			cars.add(car);
			if (i == 5) {
				Car car1 = new Car();
				car1.setName(car.getName());
				car1.setCount(i + 1);
				cars.add(car1);
			}
			ints.add(i);
			strings.add(String.valueOf(base.charAt(random.nextInt(base.length()))) + i);
		}
		
		Collections.sort(strings);
		Collections.sort(ints);
		System.out.println(strings);
		System.out.println(ints);
		
		Collections.reverse(strings);
		Collections.reverse(ints);
		System.out.println(strings);
		System.out.println(ints);
		
		Collections.sort(strings, Collections.reverseOrder());
		Collections.sort(ints, Collections.reverseOrder());
		System.out.println(strings);
		System.out.println(ints);
		
		Collections.shuffle(strings);
		Collections.shuffle(ints);
		System.out.println(strings);
		System.out.println(ints);
		
		Collections.sort(cars, new Comparator<Car>() {
			@Override
			public int compare(Car o1, Car o2) {
				return o1.getCount() - o2.getCount();
			}
		});
		System.out.println(cars.toString());
		
		// implements Comparable
		Collections.sort(cars);
		System.out.println(cars.toString());
		
		Collections.sort(cars, new Comparator<Car>() {
			@Override
			public int compare(Car o1, Car o2) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
		});
		System.out.println(cars.toString());
		
		cars.sort((a, b) -> a.getCount() - b.getCount());
		System.out.println(cars.toString());
		
		cars.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
		System.out.println(cars.toString());
		
		cars.sort(Comparator.comparing(Car :: getName).reversed());
		System.out.println(cars.toString());
		
		cars.sort(Comparator.comparing(Car :: getName).reversed().thenComparing(Car :: getCount));
		System.out.println(cars.toString());
		
	}
}
