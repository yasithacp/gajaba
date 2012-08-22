package org.gajaba.rule.core;

/**
 * Created with IntelliJ IDEA.
 * User: kasuncp
 * Date: 8/22/12
 * Time: 2:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class MockClient {

    private String name;
    private String tokenId;
    private String key;

    public MockClient(String name, String tokenId, String key){

        this.name = name;
        this.tokenId = tokenId;
        this.key = key;
    }

    public String getName(){
        return name;
    }

    public String getTokenId(){
        return tokenId;
    }

    public String getKey(){
        return key;
    }

    @Override
    public String toString() {
        return name;
    }
}
