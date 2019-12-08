package com.xz.auth;

import java.io.IOException;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.xz.auth.model.vo.RoleVO;

public class TestHttpUtil {

	public static void main(String[] args) {
		doGetTestWayOne();
//		doPostTestTwo();
	}
    /**
	 * GET---有参测试 (方式一:手动在url后面加上参数)
	 *
	 * @date 2018年7月13日 下午4:19:23
	 */
	public static void doGetTestWayOne() {
		// 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
 
		// 参数
		StringBuffer params = new StringBuffer();
		try {
			// 字符数据最好encoding以下;这样一来，某些特殊字符才能传过去(如:某人的名字就是“&”,不encoding的话,传不过去)
//			params.append("name=" + URLEncoder.encode("&", "utf-8"));
			params.append("id=1");
			params.append("&");
			params.append("code=admin");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// 加密
		StringBuffer buffer = new StringBuffer();
		buffer.append(1L);
		buffer.append("|");
		buffer.append("admin");
		buffer.append("|");

//		String signature = "MIIEfgYJKoZIhvcNAQcCoIIEbzCCBGsCAQExCzAJBgUrDgMCGgUAMAsGCSqGSIb3DQEHAaCCA0YwggNCMIICKqADAgECAgQQAAACMA0GCSqGSIb3DQEBCwUAMFMxCzAJBgNVBAYTAkNOMQ4wDAYDVQQIDAVIdWJlaTEQMA4GA1UECgwHeWlqaXVwaTEQMA4GA1UECwwHSHVhc2hhbjEQMA4GA1UEAwwHeGlhb3poaTAeFw0xOTEyMDUwNzQ1MzRaFw0yMDEyMDQwNzQ1MzRaMHoxCzAJBgNVBAYTAkNOMQ4wDAYDVQQIDAVIdWJlaTEQMA4GA1UECgwHeWlqaXVwaTEQMA4GA1UECwwHSHVhc2hhbjEQMA4GA1UEAwwHeGlhb3poaTElMCMGCSqGSIb3DQEJARYWaHVsaWFuZ3poaUB5aWppdXBpLmNvbTCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEA6PV4mtmd8nbkTVDrhtX3wTa0zwHvj+sJhyM0PFXT0uHOZiOIXTLO8YfJ+jFupMmYfzHy5ZD2pOF1FwrZaPCL8cX7WQ0Xbbfn386PWoiOjYYLbsap+1DFOkIw72AhNUUphG6J5ummAn2Xjakn8Lt/ZAl2gHqdEuafAEiaAeuyUjMCAwEAAaN7MHkwCQYDVR0TBAIwADAsBglghkgBhvhCAQ0EHxYdT3BlblNTTCBHZW5lcmF0ZWQgQ2VydGlmaWNhdGUwHQYDVR0OBBYEFAqGbl3OghFLNmFvMAo/OytSUr4eMB8GA1UdIwQYMBaAFIqjaPq3fwzlqpLklX5HOec6cBKrMA0GCSqGSIb3DQEBCwUAA4IBAQCLOl5bGxv1N/ER0Q0Irf8rXO5mTy3SKjQdnsx5phNefU8SSunjsXKYN4YreFseTtdqxW7yG4wla/pRD4m1OXiEZCPTsx+z52WNHOZ7SxgVlY4V+nSpm8bgFtMqbOoXEAOND9UIb1mGZOwNUAjS4RRA4Q3dUCZo63/c0SKO451GVSIaI/6KClCK6Wf60uKf+wqyQ4+Nnt73+9yMesDoarXc4FSMfLeOF9wENNn9fYWYoiMm5MQR/XFh8vrKaRRqfvH5rcHMWB2kPsI+S7+553lFrPKE7bfZY/nvgdl6iHnhY8AX9SNHiRG6Il28KhgCiK36VPAyW43VPfeF0JoCXMVuMYIBADCB/QIBATBbMFMxCzAJBgNVBAYTAkNOMQ4wDAYDVQQIEwVIdWJlaTEQMA4GA1UEChMHeWlqaXVwaTEQMA4GA1UECxMHSHVhc2hhbjEQMA4GA1UEAxMHeGlhb3poaQIEEAAAAjAJBgUrDgMCGgUAMA0GCSqGSIb3DQEBAQUABIGAgfot8ca1GRIdkytVpTFUwJxL1Tcf7/4G1ry2hC2X2WyZnw4IPhKOVaTv1CGUz2qWhj0s7LlzTZrbbGgerpHeNnxkPevjNeKzWG3C1TH+dJzCgT/yeMn4ufujLYcaxaCCKibRCiU0J0spmeZ+3N+1+iC7VHlw7fHEkxNqWeZGhMk=";
		// 创建Get请求
		HttpGet httpGet = new HttpGet("http://localhost:8080/roles/checkRoleCode" + "?" + params);
//		System.out.println(JSON.toJSONString(param));
		httpGet.setHeader("signature", EncryptionUtil.create().sign(buffer.toString()));
		// 响应模型
		CloseableHttpResponse response = null;
		try {
			// 配置信息
			RequestConfig requestConfig = RequestConfig.custom()
					// 设置连接超时时间(单位毫秒)
					.setConnectTimeout(5000)
					// 设置请求超时时间(单位毫秒)
					.setConnectionRequestTimeout(5000)
					// socket读写超时时间(单位毫秒)
					.setSocketTimeout(5000)
					// 设置是否允许重定向(默认为true)
					.setRedirectsEnabled(true).build();
 
			// 将上面的配置信息 运用到这个Get请求里
			httpGet.setConfig(requestConfig);
 
			// 由客户端执行(发送)Get请求
			response = httpClient.execute(httpGet);
 
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			System.out.println("响应状态为:" + response.getStatusLine());
			if (responseEntity != null) {
				System.out.println("响应内容长度为:" + responseEntity.getContentLength());
				System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * POST---有参测试(对象参数)
	 *
	 * @date 2018年7月13日 下午4:18:50
	 */
	public static void doPostTestTwo() {
 
		// 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
 
		// 创建Post请求
		HttpPost httpPost = new HttpPost("http://localhost:8080/roles/addRole");

		RoleVO roleVO = new RoleVO();
		roleVO.setName("超级管理员");
		roleVO.setId(1L);
		roleVO.setCode("超级管理员");
		
		TreeMap<String, Object> param = new TreeMap<>();
		param.put("id", 1L);
		param.put("name", "超级管理员");
		param.put("code","超级管理员");
		// 我这里利用阿里的fastjson，将Object转换为json字符串;
		// (需要导入com.alibaba.fastjson.JSON包)
//		String jsonString = JSON.toJSONString(param);
		
		String jsonString = JSON.toJSONString(roleVO);
		
		StringEntity entity = new StringEntity(jsonString, "UTF-8");
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(entity);

		// post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
		httpPost.setEntity(entity);
		httpPost.setHeader("signature", EncryptionUtil.create().signTreeMap(param));
//		httpPost.setHeader("signature", EncryptionUtil.create().signJSON(jsonString));
		httpPost.setHeader("Content-Type", "application/json;charset=utf8");
 
		// 响应模型
		CloseableHttpResponse response = null;
		try {
			// 由客户端执行(发送)Post请求
			response = httpClient.execute(httpPost);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
 
			System.out.println("响应状态为:" + response.getStatusLine());
			if (responseEntity != null) {
				System.out.println("响应内容长度为:" + responseEntity.getContentLength());
				System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
