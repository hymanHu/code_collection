package com.thornBird.modules.shoppingCart.entity;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class Cart {

	private List<CartItem> items = new ArrayList<CartItem>();
	private HttpServletRequest request;
	private double sumamount;
	private int sumnumb;

	public void addItem(CartItem item) {
		// todo 购物车添加商品
	}

	public void deleteItem(CartItem item) {
		// todo 购物车删除商品
	}

	public String serialize() {
		// todo 序列化购物车成字符串
		return "aaaaa";
	}
	
	public void load(String cartString) {
		// todo 初始化购物车
	}

	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public double getSumamount() {
		return sumamount;
	}

	public void setSumamount(double sumamount) {
		this.sumamount = sumamount;
	}

	public int getSumnumb() {
		return sumnumb;
	}

	public void setSumnumb(int sumnumb) {
		this.sumnumb = sumnumb;
	}
}
