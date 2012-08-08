package org.gajaba.server;

import org.gajaba.group.GroupManager;

public class Server {

    public static final String SERVER_NAME = "GAJABA_SERVER";

    public void start() {
        GroupManager manager = new GroupManager();
        manager.start(SERVER_NAME);

    }
}
