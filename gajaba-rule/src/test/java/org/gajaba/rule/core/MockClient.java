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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MockClient client = (MockClient) o;

        if (componentName != null ? !componentName.equals(client.componentName) : client.componentName != null)
            return false;
        if (key != null ? !key.equals(client.key) : client.key != null) return false;
        if (tokenId != null ? !tokenId.equals(client.tokenId) : client.tokenId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tokenId != null ? tokenId.hashCode() : 0;
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (componentName != null ? componentName.hashCode() : 0);
        return result;
    }
}
