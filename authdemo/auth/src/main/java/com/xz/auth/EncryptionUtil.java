package com.xz.auth;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;
import com.htjc.cht.api.util.DigitalSignTool;

/**
 * 签名加密工具类
 * @author admin
 *
 */
public class EncryptionUtil {

	private EncryptionUtil() {
		try {
			load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static{
		single = new EncryptionUtil();
    }
 
    public static EncryptionUtil single;

    public static EncryptionUtil create() {
        return single;
    }

	private DigitalSignTool signerTool = null;
	
	public void load() throws Exception {
		String path = URLDecoder.decode(EncryptionUtil.class.getResource("/").getPath(), "utf-8");
		String pfx_file = path + "cert1/xiaozhi.pfx"; // 商户证书路径,更换你根路径下的证书
		signerTool = DigitalSignTool.getSigner(pfx_file, "123456", "123456"); // 签名工具实例
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
	*@param orginalData待签名数据
	*@return 成功返回：签名数据；失败返回：null
	*/
	public String signJSON(JSON orginalData) {
		return this.signTreeMap(transBean2TreeMap(orginalData));
	}

	/**
	*数据签名
	*@param orginalData待签名数据
	*@return 成功返回：签名数据；失败返回：null
	*/
	public String signJSON(String orginalData) {
		return this.signTreeMap(transBean2TreeMap(JSON.parseObject(orginalData, TreeMap.class)));
	}

	@SuppressWarnings("unchecked")
	public static TreeMap<String, Object> transBean2TreeMap(Object obj) {
		if (obj == null) {
			return null;
		}
		if (obj instanceof TreeMap) {
			return (TreeMap)obj;
		}
		TreeMap<String, Object> map = new TreeMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					if(null !=value && !"".equals(value))
						map.put(key, value);
				}

			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}
		return map;
	}
	
	/**
	*数据签名
	*@param orginalData待签名数据
	*@return 成功返回：签名数据；失败返回：null
	*/
	public  String signTreeMap(TreeMap<String, Object> orginalData) {
		String summary = generateSummary(new ArrayList<>(orginalData.values()));
		String signData = null;
		try {
			signData = signerTool.sign(summary.getBytes());
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
	 * 生成报文
	 * @param args
	 * @return
	 */
	private String generateSummary(List<Object> args) {
		StringBuffer buffer = new StringBuffer();
		for (Object arg: args) {
			if (null != arg) {
				buffer.append(arg);
				buffer.append("|");
			}
		}
		return buffer.toString();
	}

	/**
	 * 简单的demo
	 * @param args
	 */
	public static void main(String[] args) {
		String code = "admin";
		Long id = 1L;
		/**
		 *  数据不能为null，如果要传递该字段请处理一下，传递空字符串，或者不传递
		 *  系统验签时会自动过滤为空的字符串
		 *  String extendParam = "";
		 */

		/**
		 * 报文组织形式
		 */
		
		/**
		 * 对于GET形式
		 */
		StringBuffer buffer = new StringBuffer();
		buffer.append(code);
		buffer.append("|");
		buffer.append(id);
		buffer.append("|");

		single.sign(buffer.toString());
		
		
		/**
		 * 对于POST形式
		 * 如果你的参数是一个实体对象，请转成JSON字符串传递
		 * 记住一定要用TreeMap
		 */
		Map<String, Object> param = new TreeMap<>();
		param.put("code", "admin");
		param.put("id", 1L);
		single.sign(JSON.toJSONString(param));
		
		/**
		 * 对于POST形式
		 * 如果你的参数由一个实体对象和query参数共同确定
		 * 请先加上query参数，在加上实体对象参数
		 */
		Map<String, Object> param2 = new HashMap<>();
		param.put("code", "admin");
		param.put("id", 1L);
		single.sign(JSON.toJSONString(param));
	}
}
