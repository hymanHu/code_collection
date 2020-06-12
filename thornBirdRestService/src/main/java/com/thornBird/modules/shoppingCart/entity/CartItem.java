package com.thornBird.modules.shoppingCart.entity;

public class CartItem {
	private int amount = 1;
	private Object goods;
	private double totalamount;

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Object getGoods() {
		return goods;
	}

	public void setGoods(Object goods) {
		this.goods = goods;
	}

	public double getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(double totalamount) {
		this.totalamount = totalamount;
	}
}
