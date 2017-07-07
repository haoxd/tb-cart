package com.tb.cart.controller.ws;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tb.cart.pojo.Cart;
import com.tb.cart.service.CartService;

@Controller
@RequestMapping("/ws/cart")
public class WsCartController {

	@Resource(name = "cartService")
	private CartService cartService;

	/**
	 * 对外提供接口服务，查询购物车
	 * @param userId
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value="/{userId}",method=RequestMethod.GET)
	public ResponseEntity<List<Cart>> queryCartList(@PathVariable("userId") Long userId,
			@RequestParam("itemId") String itemId) {
		try {
			
			List<Cart> cartList = this.cartService.queryMyCatList(userId, StringUtils.split(itemId, ","));
			if (null == cartList || cartList.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(cartList);
		} catch (Exception e) {			
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
