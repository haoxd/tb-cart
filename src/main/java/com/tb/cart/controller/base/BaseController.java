package com.tb.cart.controller.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author acer11 作者：haoxd 创建时间：2017年2月24日 下午2:36:38 项目名称：tb-cart
 * @version 1.0
 * @since JDK 1.6.0_21 文件名称：BaseController.java 类说明：controller 基类
 */
public abstract class BaseController {

	/**
	 * 类成员方法只对继承类公开，那么限制为 protected 
	 * */
	protected Logger log;

	public BaseController() {
		log = LoggerFactory.getLogger(getClass());

	}

	public void init(HttpServletRequest request, HttpServletResponse response) {
	}

}
