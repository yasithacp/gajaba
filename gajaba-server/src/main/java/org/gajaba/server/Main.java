package org.gajaba.server;


import org.gajaba.group.GroupManager;

public class Main {

    public static final String SERVER_NAME = "GAJABA_SERVER";

    public static void main(String[] args) {
        GroupManager manager = new GroupManager();
        manager.start(SERVER_NAME);

    }
}
