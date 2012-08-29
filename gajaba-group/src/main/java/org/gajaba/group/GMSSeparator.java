package org.gajaba.group;

import com.sun.enterprise.ee.cms.core.GMSCacheable;

public class GMSSeparator implements KeySeparator {

    @Override
    public String getComponentName(Object key) {
        GMSCacheable cacheable = (GMSCacheable) key;
        return cacheable.getComponentName();
    }

    @Override
    public String getMemberTokenId(Object key) {
        GMSCacheable cacheable = (GMSCacheable) key;
        return cacheable.getMemberTokenId();
    }

    @Override
    public Object getKey(Object key) {
        GMSCacheable cacheable = (GMSCacheable) key;
        return cacheable.getKey();
    }

    @Override
    public Object construct(String compName, String tokenId, String key) {
        return new GMSCacheable(compName, tokenId, key);
    }

}
