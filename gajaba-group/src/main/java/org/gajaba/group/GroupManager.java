package org.gajaba.group;

import com.sun.enterprise.ee.cms.core.*;
import com.sun.enterprise.ee.cms.impl.client.JoinNotificationActionFactoryImpl;
import com.sun.enterprise.ee.cms.impl.client.MessageActionFactoryImpl;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GroupManager extends Observable implements CallBack {
    public static final String GROUP_NAME = "DEFAULT_GAJABA_GROUP";
    public static final String CACHE_CHANGE = "CACHE_CHANGE";
    private GroupManagementService gms;
    final static Logger logger = Logger.getLogger("gajaba.group.GroupManager");
    private DistributedStateCache cache;

    public void start(String serverName) {
        logger.log(Level.INFO, "Initializing Shoal for with server name: " + serverName);
        gms = (GroupManagementService) GMSFactory.startGMSModule(serverName,
                GROUP_NAME, GroupManagementService.MemberType.CORE, null);
        try {
            gms.join();
        } catch (GMSException e) {
            logger.log(Level.SEVERE, "Error joining group" + e);
        }
    }

    public void processNotification(Signal notification) {
        setChanged();
        notifyObservers(notification);
    }

    public void addObserver(Observer o) {
        logger.log(Level.INFO, "Registering for group notifications");
        gms.addActionFactory(new JoinNotificationActionFactoryImpl(this));
        gms.addActionFactory(new MessageActionFactoryImpl(this), CACHE_CHANGE);
        super.addObserver(o);
    }

    public void publish(String key, String value) {
        logger.log(Level.INFO, "adding cache entry: " + key + " = " + value);
        try {
            if (cache == null) {
                cache = gms.getGroupHandle().getDistributedStateCache();
            }
            cache.addToCache(GROUP_NAME, gms.getInstanceName(), key, value);
            GroupHandle gh = gms.getGroupHandle();
            gh.sendMessage(CACHE_CHANGE, CACHE_CHANGE.getBytes());
        } catch (GMSException e) {
            logger.log(Level.SEVERE, "Error updating cache" + e);
        }

    }

    public Map getCache() {
        if (cache == null) {
            cache = gms.getGroupHandle().getDistributedStateCache();
        }
        return cache.getAllCache();
    }

    public List<String> getAgent() {
        return gms.getGroupHandle().getAllCurrentMembers();
    }
}
