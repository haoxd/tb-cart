package com.tb.cart.system.http.ws.intf;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.tb.common.bean.api.RespInfo;



/**
 * @author acer11 作者：haoxd 创建时间：2017年3月26日 下午7:08:38 项目名称：tb-web
 * @author daniel
 * @version 1.0
 * @since JDK 1.6.0_21 文件名称：HttpClientApiServerTools.java 类说明：访问外部接口服务
 * 在单例对象中如何使用多例对象：不可以注入，使用bean工程获取该对象
 * 实现方法：实现BeanFactoryAware接口
 */
@Service("http")
public class HttpClientApiServerTools  implements BeanFactoryAware{

	
	

	@Autowired
	private RequestConfig requestConfig;
	
	private BeanFactory beanFactory;

	
	/**
	 * 发送get请求
	 * 
	 * @param url：请求路径
	 * @return
	 * @throws DocumentException 
	 */
	@SuppressWarnings("unchecked")
	public RespInfo sendGetNoReadXML(String url) throws HttpClientErrorException, IOException{
		RespInfo respBean = new RespInfo();
		
		// 创建http GET请求
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requestConfig);
		httpGet.setHeader("HaiTao", "1");
		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = getHttpClient().execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				respBean.setRespCode("200");
				respBean.setData(EntityUtils.toString(response.getEntity(), "UTF-8"));
				respBean.setRespDesc("发送GET请求成功返回");
			} else if (response.getStatusLine().getStatusCode() == 404) {
				respBean.setRespCode("404");
				respBean.setData(null);
				respBean.setRespDesc("发送GET请求404错误");
			} else {
				respBean.setRespCode("500");
				respBean.setData(null);
				respBean.setRespDesc("发送GET请求500错误");
			}

		} finally {
			if (response != null) {
				response.close();
			}
		}
		return respBean;
	}

	/**
	 * 带有参数的get请求
	 * 
	 * @param url:请求路径
	 * @param inParam：参数
	 * @return
	 * @throws HttpClientErrorException
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws DocumentException 
	 */
	@SuppressWarnings("unchecked")
	public RespInfo sendGetByParam(String url, Map<String, Object> inParam)
			throws HttpClientErrorException, IOException, URISyntaxException{
		RespInfo resp = new RespInfo();

		URIBuilder uriBuilder = new URIBuilder(url);
		for (Map.Entry<String, Object> params : inParam.entrySet()) {
			uriBuilder.setParameter(params.getKey(), params.getValue().toString());
		}
		return this.sendGetNoReadXML(uriBuilder.build().toString());
	}

	/**
	 * 发送post请求 带有参数
	 * @param url
	 * @param inParam
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 * @throws DocumentException 
	 */
	public RespInfo sendPostByParam(String url, Map<String, String> inParam) throws ParseException, IOException{
		RespInfo resp = new RespInfo();

		// 创建http POST请求
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		if (null != inParam) {
			List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
			for (Map.Entry<String, String> params : inParam.entrySet()) {
				parameters.add(new BasicNameValuePair(params.getKey(), params.getValue()));

			}
			// 构造一个form表单式的实体
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
			// 将请求实体设置到httpPost对象中
			httpPost.setEntity(formEntity);
		}
		httpPost.setHeader("HaiTao", "1");
		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = getHttpClient().execute(httpPost);
			resp.setData(EntityUtils.toString(response.getEntity(), "UTF-8"));
			resp.setRespCode(String.valueOf(response.getStatusLine().getStatusCode()));
		} finally {
			if (response != null) {
				response.close();
			}
		}

		return resp;
	}
	
	/**
	 * 发送post请求 带有参数
	 * @param url
	 * @param inParam
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 * @throws DocumentException 
	 */
	public RespInfo sendPostByParamNoReadAppXml(String url, Map<String, String> inParam) throws ParseException, IOException{
		RespInfo resp = new RespInfo();

		// 创建http POST请求
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		if (null != inParam) {
			List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
			for (Map.Entry<String, String> params : inParam.entrySet()) {
				parameters.add(new BasicNameValuePair(params.getKey(), params.getValue()));

			}
			// 构造一个form表单式的实体
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
			// 将请求实体设置到httpPost对象中
			httpPost.setEntity(formEntity);
		}
		httpPost.setHeader("HaiTao", "1");
		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = getHttpClient().execute(httpPost);
			resp.setData(EntityUtils.toString(response.getEntity(), "UTF-8"));
			resp.setRespCode(String.valueOf(response.getStatusLine().getStatusCode()));
		} finally {
			if (response != null) {
				response.close();
			}
		}

		return resp;
	}
	
	/**
	 * 发送post请求 带有参数
	 * @param url
	 * @param inParam
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 * @throws DocumentException 
	 */
	public RespInfo sendPostJSON(String url, String Json) throws ParseException, IOException {
		RespInfo resp = new RespInfo();

		// 创建http POST请求
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		if (null != Json) {
			// 构造一个form表单式的实体
			StringEntity JSONstr = new StringEntity(Json, ContentType.APPLICATION_JSON);
			// 将请求实体设置到httpPost对象中
			httpPost.setEntity(JSONstr);
		}
		httpPost.setHeader("HaiTao", "1");
		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = getHttpClient().execute(httpPost);
			resp.setData(EntityUtils.toString(response.getEntity(), "UTF-8"));
			resp.setRespCode(String.valueOf(response.getStatusLine().getStatusCode()));
		} finally {
			if (response != null) {
				response.close();
			}
		}

		return resp;
	}
	
	
	/**
	 * 发送post请求
	 * @param url
	 * @param inParam
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 * @throws DocumentException 
	 */
	public RespInfo sendPost(String url) throws ParseException, IOException {
		
		return this.sendPostByParam(url, null);
	}
	
	/**
	 * 从httpclient池当中获取httpcliect对象
	 * @return
	 */
	private CloseableHttpClient getHttpClient(){
		return this.beanFactory.getBean(CloseableHttpClient.class);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		// 该方法在spring容器初始化时 会调用该方法，传入beanfactory
		this.beanFactory = beanFactory;
	}
}
