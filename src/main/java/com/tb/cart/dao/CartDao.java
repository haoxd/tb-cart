package com.tb.cart.dao;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.tb.cart.pojo.Cart;
import com.tb.common.org.gtmd.frame.tools.paramData;

public interface CartDao extends Mapper<Cart> {
	
	
	public List<Cart> queryMyCartList(Map params) ;

	public List<Cart> queryMyCartListToWeb(Map inParam);

}
