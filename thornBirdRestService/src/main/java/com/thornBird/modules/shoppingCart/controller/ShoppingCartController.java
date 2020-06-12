package com.thornBird.modules.shoppingCart.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thornBird.common.CookieUtils;
import com.thornBird.modules.shoppingCart.entity.Cart;
import com.thornBird.modules.shoppingCart.entity.CartItem;

@Controller
public class ShoppingCartController {

	public static final String CART_ID = "cookie.cart.id";  
	
	/**
	 * 查看购物车
	 */
	@RequestMapping(value = "/shoppingcart", method = RequestMethod.GET)  
    public String shoppingcart(HttpServletRequest request,ModelMap model){ 
		
		Cart cart = this.getCart(request);  
		model.addAttribute("cart", cart);
        return "****";  
    }
	
	/** 
     * 加入购物车 
     */  
    @RequestMapping(value = "/addItem")  
    public void addItem(HttpServletRequest request,  
            HttpServletResponse response, CartItem item, Integer amount) throws Exception {  
        Cart cart = this.getCart(request);
        if (cart == null) {
        	cart = new Cart();
        }
        
        cart.addItem(item);
          
        //放入购物车  
        saveToCookie(response, cart);  
    }
    
    /** 
     * 删除某一项 
     */  
    @RequestMapping(value = "/delItem")  
    public void Item(HttpServletRequest request,  
            HttpServletResponse response,CartItem item,Integer amount) throws Exception {  
    	Cart cart = this.getCart(request);
        if (cart == null) {
        	return;
        }
          
        cart.deleteItem(item);  
          
        //放入购物车  
        saveToCookie(response, cart);  
    }
    
    /** 
     * 保存cookie 
     */  
    private void saveToCookie(HttpServletResponse response, Cart cart)  
            throws Exception {  
        CookieUtils.addCookie(response, CART_ID + "01", cart.serialize());  
    }
	
	/**
	 * 购物车的初始化
	 */
	private Cart getCart(HttpServletRequest request) {
		Cart cart = null;
		try {
			String cartStr = "";
			cartStr = CookieUtils.findCookie(request, CART_ID + "01");
			cart = new Cart();
			if (cartStr != null && !"".equals(cartStr)) {
				cart.load(cartStr);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return cart;
	}
}
