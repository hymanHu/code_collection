package com.thornBird.base.bean;

public class Car implements Comparable<Car> {
	public String name;
	public int count;
	public double cost;
	public boolean madeInChina;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public boolean isMadeInChina() {
		return madeInChina;
	}

	public void setMadeInChina(boolean madeInChina) {
		this.madeInChina = madeInChina;
	}

	@Override
	public String toString() {
		return this.getName() + "----" + this.getCount();
	}

	@Override
	public int compareTo(Car o) {
		return this.getName().compareTo(o.getName());
	}

}
