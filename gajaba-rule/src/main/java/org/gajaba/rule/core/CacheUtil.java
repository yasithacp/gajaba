package org.gajaba.rule.core;

import org.gajaba.group.KeySeparator;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CacheUtil {
    public static Map<Object, String> getCacheSubMapForKey(String key, Map<Object, String> cache, KeySeparator separator) {
        ConcurrentHashMap<Object, String> map = new ConcurrentHashMap<>();
        for (Map.Entry<Object, String> cacheEntry : cache.entrySet()) {
            Object cacheEntryKey = separator.getKey(cacheEntry.getKey());
            if (cacheEntryKey.equals(key)) {
                map.put(cacheEntry.getKey(), cacheEntry.getValue());
            }
        }
        return map;
    }

    public static String getCacheValue(String tokenId, String key, Map<Object, String> cache, KeySeparator separator) {
        Object keyEntryWithValue = separator.construct(null, tokenId, key);
        return cache.get(keyEntryWithValue);
    }

    public static Map<Object, String> getCacheSubMapForKey(Map<Object, String> keys, Map<Object, String> cache, KeySeparator separator) {
        ConcurrentHashMap<Object, String> map = new ConcurrentHashMap<>();
        for (Map.Entry<Object, String> keyEntry : keys.entrySet()) {
            Object keyEntryWithValue = separator.construct(
                    separator.getComponentName(keyEntry.getKey()),
                    separator.getMemberTokenId(keyEntry.getKey())
                    , keyEntry.getValue());
            String value = cache.get(keyEntryWithValue);
            if (value != null)
                map.put(keyEntry.getKey(), value);
        }
        return map;
    }

    public static void getTrimAccepted(List<String> accepted, Map<Object, String> subCache, KeySeparator separator) {
        Iterator<String> iterator = accepted.iterator();
        while (iterator.hasNext()) {
            String agent = iterator.next();
            boolean keep = false;
            for (Map.Entry<Object, String> cacheEntry : subCache.entrySet()) {
                Object cacheEntryTokenId = separator.getMemberTokenId(cacheEntry.getKey());
                if (cacheEntryTokenId.equals(agent)) {
                    keep = true;
                    break;
                }
            }
            if (!keep) {
                iterator.remove();
            }
        }
    }

    public static Map<Object, String> getCacheSubMapForValue
            (String value, Map<Object, String> cache, KeySeparator separator) {
        ConcurrentHashMap<Object, String> map = new ConcurrentHashMap<>();
        for (Map.Entry<Object, String> cacheEntry : cache.entrySet()) {
            if (cacheEntry.getValue().equals(value)) {
                map.put(cacheEntry.getKey(), cacheEntry.getValue());
            }
        }
        return map;
    }

    public static String getRegex(String input, String regex, int group) {
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(input);
        if (m.matches()) {
            return m.group(group);
        }
        return null;
    }
}
