package com.tb.cart.system.constant;

public class ServiceCode {

	public interface token {

		String LOGIN_COOKIE_TOKEN_NAME = "SSO_TOKEN";

		String LOGIN_PAGE = "http://sso.tb.com/user/login.html";
	}

	public interface cartCookie {
		// cookie 名称
		String CART_COOKIE_NAME = "TB_CART";
		// cookie 有效时间
		Integer CART_COOKIE_TIME = 60 * 24 * 30 * 12;
	}

	public interface cart {
		String ORDER_STATUS = "status";
		String ORDER_SUBMIT_STATUS_SUCC = "200";
		String ORDER_SUBMIT_STATUS_FAIL = "500";
		String ORDER_DATA = "data";
		// 业务状态
		Integer ORDER_SERVICE_CODE = 200;
	}
}
