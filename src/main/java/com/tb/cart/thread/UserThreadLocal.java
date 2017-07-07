package com.tb.cart.thread;

import com.tb.sso.query.api.bean.TbUser;

/**
 * @author acer11
 *  作者：郝旭东
* 创建时间：2017年6月19日 下午2:33:59  
* 项目名称：tb-cart  
* 文件名称：UserThreadLocal.java  
* 类说明：线程局部变量(ThreadLocal)其实的功用非常简单，
* 就是为每一个使用该变量的线程都提供一个变量值的副本，
* 是Java中一种较为特殊的线程绑定机制，是每一个线程都可以独立地改变自己的副本，而不会和其它线程的副本冲突。
* ,构造方法私有化
 */
public class UserThreadLocal {
	
	private static final ThreadLocal<TbUser> local = new ThreadLocal<TbUser>();
	
	private UserThreadLocal(){};
	
	public static void set(TbUser user){
		local.set(user);
	}
	
	public static TbUser get(){
		return local.get();
	}

}
