package org.gajaba.rule.core;

/**
 * Created with IntelliJ IDEA.
 * User: kasuncp
 * Date: 8/22/12
 * Time: 2:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class MockClient {

    private String tokenId;
    private String key;
    private String componentName;

    public MockClient(String componentName, String tokenId, String key) {

        this.componentName = componentName;
        this.tokenId = tokenId;
        this.key = key;
    }


    public String getTokenId() {
        return tokenId;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return tokenId + ":" + key;
    }

    public String getComponentName() {
        return componentName;
    }
}
