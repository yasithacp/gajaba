package org.gajaba.agent;

import org.gajaba.group.GroupManager;

public class Agent {

    public static final String IP = "ip";
    private GroupManager manager;

    public void start(String ip, String serverName) {
        manager = new GroupManager();
        manager.start(serverName);
        try {
            Thread.sleep(5000); //this is for the shoal to start.
            //TODO:replace this with a 'wait for join'.
        } catch (InterruptedException e) {
        }
        manager.publish(IP,ip);
    }

    public void publish(String key, String value) {
        manager.publish(key, value);

    }
}