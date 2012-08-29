package org.gajaba.group;

import com.sun.enterprise.ee.cms.core.GMSCacheable;

public interface KeySeparator {
    String getComponentName(Object key);

    String getMemberTokenId(Object key);

    Object getKey(Object key);

    Object copyWithNewKey(Object key, String value);
}
