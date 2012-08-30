package org.gajaba.rule.core;

public class MockSeparator implements org.gajaba.group.KeySeparator {

    @Override
    public String getComponentName(Object key) {
        MockClient client = (MockClient) key;
        return client.getComponentName();
    }

    @Override
    public String getMemberTokenId(Object key) {
        MockClient client = (MockClient) key;
        return client.getTokenId();
    }

    @Override
    public Object getKey(Object key) {
        MockClient client = (MockClient) key;
        return client.getKey();
    }

    @Override
    public Object construct(String compName, String tokenId, String key) {
        return new MockClient(compName, tokenId, key);
    }

}
