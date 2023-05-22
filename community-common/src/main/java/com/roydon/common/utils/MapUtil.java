package com.roydon.common.utils;

import java.util.HashMap;
import java.util.Map;


public class MapUtil {


	/**
	 * args的长度必须是偶数，args奇数位的类型必须是String类型
	 *
	 * @param args
	 *            Object数组
	 * @return
	 */
	public static Map<String, String> createStringMap(String... args) {
		if (args.length % 2 != 0) {
			throw new IllegalArgumentException("传入的参数必须是偶数！");
		}
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < args.length; i += 2) {
			if (!(args[i] instanceof String)) {
				throw new IllegalArgumentException("参数的奇数对象类型必须是String！");
			}

			if (args[i + 1] instanceof String && StringUtil.isNullEmpty((String)args[i + 1])) {
			    continue;
			}
			map.put((String) args[i], args[i + 1]);
		}
		return map;
	}

	/**
	 * args的长度必须是偶数，args奇数位的类型必须是String类型
	 *
	 * @param args
	 *            Object数组
	 * @return
	 */
	public static Map<String, Object> createMap(Object... args) {
				if (args.length % 2 != 0) {
					throw new IllegalArgumentException("传入的参数必须是偶数！");
				}
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < args.length; i += 2) {
					if (!(args[i] instanceof String)) {
				throw new IllegalArgumentException("参数的奇数对象类型必须是String！");
			}

			if (args[i + 1] instanceof String && ((String)args[i + 1]) == null) {
			    continue;
			}
			map.put((String) args[i], args[i + 1]);
		}
		return map;
	}

	/**
	 * args的长度必须是偶数，args奇数位的类型必须是String类型
	 *
	 * @param args
	 *            Object数组
	 * @return
	 */
	public static <T> Map<String, T> createMapByGenericity(Object... args) {
		if (args.length % 2 != 0) {
			throw new IllegalArgumentException("传入的参数必须是偶数！");
		}
		Map<String, T> map = new HashMap<>();
		for (int i = 0; i < args.length; i += 2) {
			if (!(args[i] instanceof String)) {
				throw new IllegalArgumentException("参数的奇数对象类型必须是String！");
			}

			if (args[i + 1] instanceof String && ((String)args[i + 1]) == null) {
				continue;
			}
			map.put((String) args[i], (T)args[i + 1]);
		}
		return map;
	}

    public static Map<String, Object> createMapAllowEmptyString(Object... args) {
        if (args.length % 2 != 0) {
            throw new IllegalArgumentException("传入的参数必须是偶数！");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < args.length; i += 2) {
            if (!(args[i] instanceof String)) {
                throw new IllegalArgumentException("参数的奇数对象类型必须是String！");
            }

            map.put((String) args[i], args[i + 1]);
        }
        return map;
    }

	/**
	 * args的长度必须是偶数
	 *
	 * @param args
	 *            Object数组
	 * @return
	 */
	public static Map<Object, Object> createMap1(Object... args) {
		if (args.length % 2 != 0) {
			throw new IllegalArgumentException("传入的参数必须是偶数！");
		}
		Map<Object, Object> map = new HashMap<Object, Object>();
		for (int i = 0; i < args.length; i += 2) {
			map.put((String) args[i], args[i + 1]);
		}
		return map;
	}
}
