package com.tb.cart.controller.cart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.tb.cart.controller.base.BaseController;
import com.tb.cart.pojo.Cart;
import com.tb.cart.service.CartService;
import com.tb.cart.service.NoLoginCartService;
import com.tb.cart.system.constant.ServiceCode.cart;
import com.tb.cart.system.constant.ServiceCode.cartCookie;
import com.tb.cart.thread.UserThreadLocal;
import com.tb.common.sys.util.CookieUtils;
import com.tb.sso.query.api.bean.TbUser;

/**
 * @author acer11 作者：haoxd 创建时间：2017年6月23日 下午2:03:43 项目名称：tb-cart
 *         文件名称：CartController.java 类说明：购物车
 */
@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {

	/**
	 * 登录状态下购物车服务
	 */
	@Resource(name = "cartService")
	private CartService cartService;

	/**
	 * 未登录状态下购物车服务
	 */
	@Resource(name = "noLoginCartService")
	private NoLoginCartService noLoginCartService;

	/**
	 * 加入商品到购物车
	 * 
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "/addMyCart/{itemId}", method = RequestMethod.GET)
	public String addMyCart(@PathVariable("itemId") Long itemId, @RequestParam("buyNum") Integer buyNum,
			HttpServletRequest request, HttpServletResponse response) {
		TbUser user = UserThreadLocal.get();
		if (null == user) {
			// 未登录
			String jsonData = CookieUtils.getCookieValue(request, cartCookie.CART_COOKIE_NAME, true);
			try {
				String myCartData = this.noLoginCartService.addMyCart(itemId, buyNum, jsonData);
				// 写cookie购物车数据
				CookieUtils.setCookie(request, response, cartCookie.CART_COOKIE_NAME, myCartData,
						cartCookie.CART_COOKIE_TIME, true);
			} catch (JsonParseException e) {

				e.printStackTrace();
			} catch (JsonMappingException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		} else {
			// 登录
			this.cartService.addMyCart(itemId, buyNum);
		}
		// 添加成功，重定向到购物车页面
		return "redirect:/cart/list.html";
	}

	/**
	 * 购物车列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView cartList(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("cart");
		TbUser user = UserThreadLocal.get();
		List<Cart> cartList = null;
		if (null == user) {
			// 未登录
			String jsonData = CookieUtils.getCookieValue(request, cartCookie.CART_COOKIE_NAME, true);

			try {
				cartList = this.noLoginCartService.queryMyCatList(jsonData);
			} catch (JsonParseException e) {
				cartList = new ArrayList<Cart>();
			} catch (JsonMappingException e) {
				cartList = new ArrayList<Cart>();
			} catch (IOException e) {
				cartList = new ArrayList<Cart>();
			}
		} else {
			// 登录
			cartList = this.cartService.queryMyCatList();

		}
		modelAndView.addObject("cartList", cartList);
		return modelAndView;

	}

	/**
	 * 修改购物车商品数量
	 */
	@RequestMapping(value = "/update/num/{itemId}/{num}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCartNum(@PathVariable("itemId") Long itemId, @PathVariable("num") Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resp = new HashMap<String, Object>();
		TbUser user = UserThreadLocal.get();
		String jsonData = CookieUtils.getCookieValue(request, cartCookie.CART_COOKIE_NAME, true);

		boolean result = false;
		if (null == user) {
			// 未登录
			try {
				String myCartData = this.noLoginCartService.updateCartNum(itemId, num, jsonData);
				if (StringUtils.isNotEmpty(myCartData)) {
					// 写cookie购物车数据
					result = true;
					CookieUtils.setCookie(request, response, cartCookie.CART_COOKIE_NAME, myCartData,
							cartCookie.CART_COOKIE_TIME, true);
				} else {
					result = false;
				}
			} catch (JsonParseException e) {
				result = false;
			} catch (JsonMappingException e) {
				result = false;
			} catch (IOException e) {
				result = false;
			}
		} else {
			result = this.cartService.updateCartNum(itemId, num);
		}
		resp.put(cart.ORDER_STATUS, result == true ? cart.ORDER_SUBMIT_STATUS_SUCC : cart.ORDER_SUBMIT_STATUS_FAIL);
		return resp;
	}

	/**
	 * 删除购物车数据
	 * 
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "/delete/{itemId}", method = RequestMethod.GET)
	public String deleteMycart(@PathVariable("itemId") Long itemId,
			HttpServletRequest request, HttpServletResponse response) {
		TbUser user = UserThreadLocal.get();
		if (null == user) {
			String jsonData = CookieUtils.getCookieValue(request, cartCookie.CART_COOKIE_NAME, true);
			// 未登录
			try {
				String myCartData =	this.noLoginCartService.delMyCart(itemId,jsonData);
				if (StringUtils.isNotEmpty(myCartData)) {
					
					CookieUtils.setCookie(request, response, cartCookie.CART_COOKIE_NAME, myCartData,
							cartCookie.CART_COOKIE_TIME, true);
				} 
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			this.cartService.delMyCart(itemId);

		}
		return "redirect:/cart/list.html";
	}
}
