package com.tb.cart.mysql.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.tb.cart.mysql.datasource.holder.DynamicDataSourceHolder;



/**
 * @author acer11
 *  作者：
* 创建时间：2017年6月27日 下午3:43:04  
* 项目名称：tb.cart 
* 文件名称：DynamicDataSource.java  
* 类说明：由于要实现mysql主从负责，定义动态数据源，实现通过集成Spring提供的AbstractRoutingDataSource，
* 	只需要实现determineCurrentLookupKey方法即可
* 	由于DynamicDataSource是单例的，线程不安全的，所以采用ThreadLocal保证线程安全，
*   由DynamicDataSourceHolder完成。
*   
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

		@Override
	    protected Object determineCurrentLookupKey() {
	        // 使用DynamicDataSourceHolder保证线程安全，并且得到当前线程中的数据源key
	        return DynamicDataSourceHolder.getDataSourceKey();
	    }
}
