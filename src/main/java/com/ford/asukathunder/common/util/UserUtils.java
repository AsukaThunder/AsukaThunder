package com.ford.asukathunder.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户工具类
 * @ClassName: UserUtils
 * @author: Ford.Zhang
 * @version: 1.0
 * 2019/12/26 下午 6:45
 **/
public class UserUtils {
    private static final String USER_KEY = "USER";

    private static ThreadLocal<Map<String, Object>> cache = new ThreadLocal<>();

    public static String getUserId() {
        Object userId = getCache(USER_KEY);
        return userId == null ? null : userId.toString();
    }


    public static void setUserId(String userId) {
        set(USER_KEY, userId);
    }


    /**
     * 从ThreadLocal里获取缓存的值
     *
     * @param key 要获取的数据的KEY
     * @return 要获取的值
     */
    private static Object getCache(String key) {
        Map<String, Object> map = cache.get();
        if (isCacheIsNull()) {
            return null;
        }
        return map.getOrDefault(key, null);
    }

    /**
     * 向ThreadLocal缓存值
     *
     * @param key   要缓存的KEY
     * @param value 要缓存的VALUE
     */
    private static void set(String key, Object value) {
        if (!isCacheIsNull()) {
            cache.get().put(key, value);
        } else {
            Map<String, Object> vmap = new HashMap<>();
            vmap.put(key, value);
            cache.set(vmap);
        }
    }

    /**
     * 根据KEY移除缓存里的数据
     *
     * @param key
     */
    private static void removeByKey(String key) {
        if (!isCacheIsNull()) {
            cache.get().remove(key);
        }
    }

    /**
     * 移除当前线程缓存
     * 用于释放当前线程threadlocal资源
     */
    private static void remove() {
        cache.remove();
    }

    private static boolean isCacheIsNull() {
        return cache.get() == null;
    }
}
