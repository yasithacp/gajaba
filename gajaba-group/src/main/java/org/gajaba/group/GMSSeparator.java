package org.gajaba.group;

import com.sun.enterprise.ee.cms.core.GMSCacheable;

public class GMSSeparator implements KeySeparator {

    @Override
    public String getComponentName(Object key) {
        GMSCacheable cacheble = (GMSCacheable) key;
        return cacheble.getComponentName();
    }

    @Override
    public String getMemberTokenId(Object key) {
        GMSCacheable cacheble = (GMSCacheable) key;
        return cacheble.getMemberTokenId();
    }

    @Override
    public Object getKey(Object key) {
        GMSCacheable cacheble = (GMSCacheable) key;
        return cacheble.getKey();
    }
}