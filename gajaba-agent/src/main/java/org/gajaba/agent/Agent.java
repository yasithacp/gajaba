package org.gajaba.agent;

import org.gajaba.group.GroupManager;

public class Agent {

    private GroupManager manager;

    public void start(String serverName) {
        manager = new GroupManager();
        manager.start(serverName);
    }

    public void publish(String key,String value) {
        manager.publish(key,value);

    }


    public void start() {
        start("server" + System.currentTimeMillis());
    }
}