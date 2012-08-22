package org.gajaba.rule.core;

/**
 * Created with IntelliJ IDEA.
 * User: kasuncp
 * Date: 8/22/12
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class MockSeparator implements org.gajaba.group.KeySeparator{

    @Override
    public String getComponentName(Object key) {
        return null;
    }

    @Override
    public String getMemberTokenId(Object key) {
        return null;
    }

    @Override
    public Object getKey(Object key) {
        return null;
    }
}
