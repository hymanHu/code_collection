package com.cpkf.basis.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * list排序
 */
public class ListSort {
	
	public List<Integer> intList = new ArrayList<Integer>();
	public List<Man> manList = new ArrayList<Man>();
	public Set<Integer> hashSet = new HashSet<Integer>();
	public Set<Integer> treeSet = new TreeSet<Integer>();

	public ListSort() {
		intList.add(1);
		intList.add(4);
		intList.add(3);
		intList.add(8);
		manList.add(new Man("ahj", 30));
		manList.add(new Man("dhyman", 33));
		manList.add(new Man("bhawkist", 21));
		manList.add(new Man("chunter", 26));
		hashSet.add(1);
		hashSet.add(11);
		hashSet.add(14);
		hashSet.add(21);
		treeSet.add(3);
		treeSet.add(43);
		treeSet.add(23);
		treeSet.add(13);
	}

	public void simpleListSort () {
		System.out.println(intList);
		
		//反转原顺序
		Collections.reverse(intList);
		System.out.println(intList);
		
		//按照自然顺序排序
		Collections.sort(intList);
		System.out.println(intList);
		
		//强行反转自然顺序
		Collections.sort(intList, Collections.reverseOrder());
		System.out.println(intList);
		
		//乱序
		Collections.shuffle(intList);
		System.out.println(intList);
	}
	
	public void comparatorSort() {
		System.out.println(manList.toString());
		
		//按照年龄排序
		Collections.sort(manList, new Comparator<Man>() {
			public int compare(Man o1, Man o2) {
				return o1.getAge() - o2.getAge();
			}
		});
		System.out.println(manList.toString());
		
		//按照姓名排序
		Collections.sort(manList, new Comparator<Man>() {
			public int compare(Man o1, Man o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		System.out.println(manList);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setSort() {
		//hash原始是乱序的，tree原始是自然排列的
		System.out.println(hashSet);
		System.out.println(treeSet);
		
		List<Integer> temp = new ArrayList(Arrays.asList(hashSet.toArray()));
		Collections.sort(temp);
		System.out.println(temp);
	}
	
	public static void main(String[] args) {
		ListSort sort = new ListSort();
//		sort.simpleListSort();
//		sort.comparatorSort();
		sort.setSort();
	}
}
