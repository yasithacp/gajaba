package org.gajaba.rule.core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheUtil {
    public static Map<Object,String> getCacheSubMap(String key,Map<Object,String> cache){
        ConcurrentHashMap<Object, String> map = new ConcurrentHashMap<>();
        return map;
    }
}
