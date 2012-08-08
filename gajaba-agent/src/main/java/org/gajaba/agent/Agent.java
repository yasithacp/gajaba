package org.gajaba.agent;

import org.gajaba.group.GroupManager;

public class Agent {
    public void start(String serverName) {
        GroupManager manager = new GroupManager();
        manager.start(serverName);
    }
}