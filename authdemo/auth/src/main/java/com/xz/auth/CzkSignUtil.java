package com.xz.auth;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.htjc.cht.api.util.DigitalSignTool;

public class CzkSignUtil {
	
	private DigitalSignTool signerTool = null;
	private DigitalSignTool checkSignerTool = null;

	public CzkSignUtil() {
		try {
			//System.out.println(CzkSignUtil.class.getResource("/"));
			//System.out.println(CzkSignUtil.class.getResource("/").getPath());
			String path = java.net.URLDecoder.decode(CzkSignUtil.class.getResource("/").getPath(), "utf-8");// 证书路径
			String pfx_file = path + "TJYXCS.pfx"; // 商户证书路径
			String rootCertificatePath = path + "cacert2.pem"; // 根证书路径
			String keyStorePassword = "TJYXCS";// 商户证书库密码
			String keyPassword = "TJYXCS";// 商户证书库私钥保护密码
			signerTool = DigitalSignTool.getSigner(pfx_file, keyStorePassword,keyPassword); // 签名工具实例
			checkSignerTool = DigitalSignTool.getVerifier(rootCertificatePath);// 验签工具实例
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	*数据签名
	*@param orginalData待签名数据
	*@return 成功返回：签名数据；失败返回：null
	*/
	public  String sign(String orginalData) {
		String signData = null;
		try {
			signData = signerTool.sign(orginalData.getBytes());
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return signData;

	}
	
	/**
	*数据签名
	*/
	public  String signBytes(byte[] orginalData) {
		String signData = null;
		try {
			signData = signerTool.sign(orginalData);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return signData;

	}

	/**
	*数据验签
	*@param signData签名数据
	*@param originalData被签名数据
	*@param dn证书DN
	*@throws Exception签名失败抛出异常
	*/
	public void verifySign(String signData, String originalData, String dn) throws Exception {
		checkSignerTool.verify(signData, originalData.getBytes(), dn);
	}
	
	public static void main(String[] args)throws IOException {
		CzkSignUtil signToolManage = new CzkSignUtil();// 创建一个实例
		/**
		*组织报文，报文格式根据文档规定组织
		*/
		String merchantNo = "100001";
		String orderNo = "100000000000001";
		String productNo = "0000001";
		String extendParam = "";// 数据不能为null
		StringBuffer buffer = new StringBuffer();
		buffer.append(merchantNo);
		buffer.append("|");
		buffer.append(orderNo);
		buffer.append("|");
		buffer.append(productNo);
		buffer.append("|");
		buffer.append(extendParam);
		String toPayData = buffer.toString();
		
		//签名
		String signData = signToolManage.sign(toPayData);
		if (null == signData) {
			throw new RuntimeException("签名失败");
		}
		
		//验签
		try {
			new CzkSignUtil().verifySign(signData, toPayData, null);
			System.out.println("验签成功！");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("验签失败！");
		}

		/**
		*如果是通过HttpClient工具发送HTTP请求
		*/
//		HttpClient client = new HttpClient();
//		PostMethod method = new PostMethod("http://localhost:8080/czk_jyhx_outer/czkjy/testSgin");
//		method.addParameter("merchantNo", merchantNo);
//		method.addParameter("orderNo", orderNo);
//		method.addParameter("productNo", productNo);
//		method.addParameter("extendParam", extendParam);
//		method.addParameter("signData", signData);
//		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
//		client.executeMethod(method);
//		String result = method.getResponseBodyAsString();
//		System.out.println(result);

	}
}
