package org.gajaba.group;

import com.sun.enterprise.ee.cms.core.GMSFactory;
import com.sun.enterprise.ee.cms.core.GroupManagementService;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GroupManager {
    public static final String GROUP_NAME = "DEFAULT_GAJABA_GROUP";
    private GroupManagementService gms;
    final static Logger logger = Logger.getLogger("gajaba.group.GroupManager");

    public void start(String serverName) {
        logger.log(Level.INFO, "Initializing Shoal for with server name: " + serverName );
        gms = (GroupManagementService) GMSFactory.startGMSModule(serverName,
                GROUP_NAME, GroupManagementService.MemberType.CORE, null);
    }

}
