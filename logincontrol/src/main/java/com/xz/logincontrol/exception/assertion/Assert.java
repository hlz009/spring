package com.xz.logincontrol.exception.assertion;

import java.util.Collection;
import java.util.Map;

import com.xz.logincontrol.exception.BaseException;

/**
 * 枚举类异常断言，提供简便的方式判断条件，并在条件满足时抛出异常
 * 错误码和错误信息定义在枚举类中，在本断言方法中，传递错误信息需要的参数
 * @author xiaozhi009
 * @time 2019年7月14日上午10:32:07
 */
public interface Assert {
	/**
	 * 创建异常
	 * @param args
	 * @return
	 */
	BaseException newException(Object... args);

	/**
	 * 创建异常
	 * @param t
	 * @param args
	 * @return
	 */
	BaseException newException(Throwable t, Object... args);

	/**
	 * 断言对象obj非空。如果obj为空，则抛出异常
	 * @param obj
	 */
	default void assertNotNull(Object obj) {
		if (obj == null) {
			throw newException();
		}
	}

	/**
	 * 断言对象obj非空。如果obj为空，则抛出异常
	 * @param obj 断言对象
	 * @param args 异常消息
	 */
	default void assertNotNull(Object obj, Object... args) {
		if (obj == null) {
			throw newException(args);
		}
	}

	/**
	 * 如果字符串str为空串，则抛出异常
	 * @param str 待判断字符串
	 */
	default void assertNotEmpty(String str) {
		if (null == str || "".equals(str.trim())) {
			throw newException();
		}
	}

	/**
	 * 如果字符串str为空串，则抛出异常
	 * @param str 待判断字符串
	 * @param args 异常消息
	 */
	default void assertNotEmpty(String str, Object... args) {
		if (null == str || "".equals(str.trim())) {
			throw newException(args);
		}
	}

	/**
	 * 断言数组大小不为0。如果数组arrays大小不为0，则抛出异常
	 * @param arrays 待判断数组
	 */
	default void assertNotEmpty(Object[] arrays) {
		if (arrays == null || arrays.length == 0) {
			throw newException();
		}
	}

	/**
	 * 断言数组大小不为0。如果数组大小不为0，则抛出异常
	 * @param arrays 待判断数组
	 * @param args message占位符对应的参数列表
	 */
	default void assertNotEmpty(Object[] arrays, Object... args) {
        if (arrays == null || arrays.length == 0) {
            throw newException(args);
        }
    }

	/**
	 * 断言集合大小不为0。如果集合大小不为0，则抛出异常
	 * @param c 待判断数组
	 */
	default void assertNotEmpty(Collection<?> c) {
		if (c == null || c.isEmpty()) {
			throw newException();
		}
	}

	/**
	 * 断言集合大小不为0。如果集合大小不为0，则抛出异常
	 * @param c 待判断集合
	 * @param args
	 */
	default void assertNotEmpty(Collection<?> c, Object... args) {
		if (c ==  null || c.isEmpty()) {
			throw newException(args);
	    }		
	}

	/**
	 * 断言map大小不为0，如果mao大小不为0，则抛出异常
	 * @param map 待判断Map
	 */
	default void assertNotEmpty(Map<?, ?> map) {
	    if (map ==  null || map.isEmpty()) {
	        throw newException();
	    }
	}

	/**
	 * @param map 待判断Map
	 * @param args message占位符对应的参数列表
	 */
	default void assertNotEmpty(Map<?, ?> map, Object... args) {
		if (map == null || map.isEmpty()) {
			throw newException(args);
		}
	}

	/**
	 * 如果布尔值expression为true，则抛出异常
	 * @param expression 待判断布尔变量
	 */
	default void assertIsFalse(boolean expression) {
	    if (expression) {
	         throw newException();
	    }
	}

    /**
     * 断言布尔值为false。如果布尔值expression为true，则抛出异常
     * @param expression 待判断布尔变量
     * @param args message占位符对应的参数列表
     */
    default void assertIsFalse(boolean expression, Object... args) {
        if (expression) {
            throw newException(args);
        }
    }

    /**
     * 断言布尔值expression为true。如果布尔值expression为false，则抛出异常
     * @param expression 待判断布尔变量
     */
    default void assertIsTrue(boolean expression) {
        if (!expression) {
            throw newException();
        }
    }

    /**
     * 断言布尔值expression为true。如果布尔值expression为false，则抛出异常
     * @param expression 待判断布尔变量
     * @param args message占位符对应的参数列表
     */
    default void assertIsTrue(boolean expression, Object... args) {
        if (!expression) {
            throw newException(args);
        }
    }

    /**
     * 断言对象obj为null。如果对象obj不为null，则抛出异常
     * @param obj 待判断对象
     */
    default void assertIsNull(Object obj) {
        if (obj != null) {
            throw newException();
        }
    }

    /**
     * 断言对象obj为null。如果对象obj不为null，则抛出异常
     *
     * @param obj 待判断布尔变量
     * @param args message占位符对应的参数列表
     */
    default void assertIsNull(Object obj, Object... args) {
        if (obj != null) {
            throw newException(args);
        }
    }

    /**
     * 直接抛出异常
     *
     */
    default void assertFail() {
        throw newException();
    }

    /**
     * 直接抛出异常
     *
     * @param args message占位符对应的参数列表
     */
    default void assertFail(Object... args) {
        throw newException(args);
    }

    /**
     * 直接抛出异常，并包含原异常信息
     * 当捕获非运行时异常（非继承{@link RuntimeException}）时，并该异常进行业务描述时，
     * 必须传递原始异常，作为新异常的cause
     *
     * @param t 原始异常
     */
    default void assertFail(Throwable t) {
        throw newException(t);
    }

    /**
     * 直接抛出异常，并包含原异常信息
     * 当捕获非运行时异常（非继承{@link RuntimeException}）时，并该异常进行业务描述时，
     * 必须传递原始异常，作为新异常的cause
     *
     * @param t 原始异常
     * @param args message占位符对应的参数列表
     */
    default void assertFail(Throwable t, Object... args) {
        throw newException(t, args);
    }

    /**
     * 断言对象o1与对象o2相等，此处的相等指（o1.equals(o2)为true）。
     * 如果两对象不相等，则抛出异常
     *
     * @param o1 待判断对象，若o1为null，也当作不相等处理
     * @param o2  待判断对象
     */
    default void assertEquals(Object o1, Object o2) {
        if (o1 == o2) {
            return;
        }
        if (o1 == null) {
            throw newException();
        }
        if (!o1.equals(o2)) {
            throw newException();
        }
    }

    /**
     * 断言对象o1与对象o2相等，此处的相等指（o1.equals(o2)为true）。
     * 如果两对象不相等，则抛出异常
     *
     * @param o1 待判断对象，若o1为null，也当作不相等处理
     * @param o2  待判断对象
     * @param args message占位符对应的参数列表
     */
    default void assertEquals(Object o1, Object o2, Object... args) {
        if (o1 == o2) {
            return;
        }
        if (o1 == null) {
            throw newException(args);
        }
        if (!o1.equals(o2)) {
            throw newException(args);
        }
    }
}
