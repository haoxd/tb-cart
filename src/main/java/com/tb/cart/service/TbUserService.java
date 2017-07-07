package com.tb.cart.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.tb.cart.system.constant.HttpConstants.HttpStatusCode;
import com.tb.cart.system.http.ws.intf.HttpClientApiServerTools;
import com.tb.common.bean.api.RespInfo;
import com.tb.sso.query.api.bean.TbUser;
import com.tb.sso.query.api.server.QueryUserService;




@Service("tbUserService")
public class TbUserService {

	/**
	 * dubbo用户查询服务
	 */
	@Autowired
	private QueryUserService queryUserService;


	public TbUser queryUserByToken(String token) {
		return this.queryUserService.queryUserByToken(token);
	}

}
