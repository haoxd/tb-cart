package com.tb.cart.pojo.sort;

import java.util.Comparator;

import com.tb.cart.pojo.Cart;

public class CartSort implements Comparator {

	@Override
	public int compare(Object obj1, Object obj2) {
		Cart cart1 = (Cart) obj1;
		Cart cart2 = (Cart) obj2;
		int flag = cart1.getCreateTime().after(cart2.getCreateTime())==true?1:0;  
		return flag;
	}

}
