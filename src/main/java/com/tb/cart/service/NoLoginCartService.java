package com.tb.cart.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tb.cart.pojo.Cart;
import com.tb.cart.pojo.Item;
import com.tb.cart.pojo.sort.CartSort;
import com.tb.common.sys.util.CookieUtils;

/**
 * @author acer11 作者： 创建时间：2017年6月24日 下午3:35:25 项目名称：tb-cart
 *         文件名称：NoLoginCartService.java 类说明：未登录状态下的购物车逻辑
 */
@Service("noLoginCartService")
public class NoLoginCartService {

	private static final ObjectMapper oMapper = new ObjectMapper();
	
	@Resource(name = "itemService")
	private ItemService itemService;

	/**
	 * @param itemId:商品id
	 * @param buyNum：商品数量
	 * @param jsonData：购物车商品数据
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public String addMyCart(Long itemId, Integer buyNum, String jsonData)
			throws JsonParseException, JsonMappingException, IOException {

		List<Cart> cartList = this.createList(jsonData);
		Cart cart = null;
		// 循环判断是否该商品已经存在购物车
		for (Cart myCart : cartList) {
			if (myCart.getItemId().longValue() == itemId.longValue()) {
				cart = myCart;
				break;
			}
		}

		if (cart == null) {
			// 不存在
			cart = new Cart();
			cart.setCreateTime(new Date());
			cart.setUpdateTime(cart.getCreateTime());

			Item item = this.itemService.queryItemById(itemId);
			cart.setItemId(itemId);
			cart.setItemPrice(item.getPrice());
			cart.setItemTitle(item.getTitle());
			if (StringUtils.isNotEmpty(item.getImage())) {
				cart.setItemImage(StringUtils.split(item.getImage(), ",")[0]);
			} else {
				cart.setItemImage("");
			}
			cart.setNum(buyNum);
			//加入购物车列表
			cartList.add(cart);
		} else {
			// 存在
			cart.setNum(cart.getNum()+buyNum);// 修改数量
			cart.setUpdateTime(new Date());
		}
		return oMapper.writeValueAsString(cartList);
		//购物车列表数据写入cookie
		
	}

	/**
	 * 查询购物车列表
	 * @param jsonData
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public List<Cart> queryMyCatList(String jsonData) throws JsonParseException, JsonMappingException, IOException {
		return this.createList(jsonData);
	}

	/**
	 * 更新购物车数量
	 * @param itemId
	 * @param num
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public String updateCartNum(Long itemId, Integer num,String jsonData) throws JsonParseException, JsonMappingException, IOException {
		List<Cart> cartList = this.createList(jsonData);
		Cart cart = null;
		// 循环判断是否该商品已经存在购物车
		for (Cart myCart : cartList) {
			if (myCart.getItemId().longValue() == itemId.longValue()) {
				cart = myCart;
				break;
			}
		}
		if(cart!=null){
			// 存在
			cart.setNum(num);// 修改数量
			cart.setUpdateTime(new Date());
		}else{
			return "";
		}
		return oMapper.writeValueAsString(cartList);
	}
	
	/**
	 * 删除购物车
	 * @param itemId
	 * @param jsonData
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public String delMyCart(Long itemId, String jsonData) throws JsonParseException, JsonMappingException, IOException {
		List<Cart> cartList = this.createList(jsonData);
		// 循环判断是否该商品已经存在购物车
		Cart cart = null;
		for (Cart myCart : cartList) {
			if (myCart.getItemId().longValue() == itemId.longValue()) {
				cart = myCart;
				cartList.remove(myCart);
				break;
			}
		}
		if(cart == null){
			return "";	
		}
		return oMapper.writeValueAsString(cartList);
	}
	
	@SuppressWarnings("unchecked")
	private List<Cart> createList(String jsonData) throws JsonParseException, JsonMappingException, IOException{
		List<Cart> cartList = null;
		if (StringUtils.isEmpty(jsonData)) {
			cartList = new ArrayList<Cart>();
		} else {
			// 将json数据转为list
			cartList = oMapper.readValue(jsonData,
					oMapper.getTypeFactory().constructCollectionType(List.class, Cart.class));
	
		}

		CartSort sort = new CartSort();
		Collections.sort(cartList,sort);  
		return cartList;
	}

	

}
