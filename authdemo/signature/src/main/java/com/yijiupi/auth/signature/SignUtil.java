package com.yijiupi.auth.signature;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yijiupi.auth.signature.util.DigitalSignTool;

public class SignUtil {
	protected Log logger = LogFactory.getLog(getClass());
	private DigitalSignTool signerTool = null;
	private DigitalSignTool checkSignerTool = null;

	public SignUtil() {
		try {
			logger.info(SignUtil.class.getName()+":"+SignUtil.class.getResource("/"));
			logger.info(SignUtil.class.getName()+":"+SignUtil.class.getResource("/").getPath());
			String path = java.net.URLDecoder.decode(SignUtil.class.getResource("/").getPath(), "utf-8");// 证书路径
			String pfx_file = path + "chongzhika.pfx"; // 商户证书路径
			String rootCertificatePath = path + "cacert.pem"; // 根证书路径
			String keyStorePassword = "000000";// 商户证书库密码
			String keyPassword = "000000";// 商户证书库私钥保护密码
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
		SignUtil signToolManage = new SignUtil();// 创建一个实例
		/**
		*组织报文，报文格式根据文档规定组织
		*/
		String admin = "100001";
		Long id = 1L;
//		String productNo = "0000001";
//		String extendParam = "";// 数据不能为null  处理一下null的问题
		StringBuffer buffer = new StringBuffer();
		buffer.append(admin);
		buffer.append("|");
		buffer.append(id);
		buffer.append("|");
//		buffer.append(productNo);
//		buffer.append("|");
//		buffer.append(extendParam);
//		buffer.append("|");
		String toPayData = buffer.toString();
	
		
		//签名
		String signData = signToolManage.sign(toPayData);
		System.out.println(signData);
		
		if (null == signData) {
			throw new RuntimeException("签名失败");
		}
		
		//验签
		try {
			new SignUtil().verifySign(signData, toPayData, null);
			System.out.println("验签成功！");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("验签失败！");
		}

//		/**
//		*如果是通过HttpClient工具发送HTTP请求
//		*/
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
