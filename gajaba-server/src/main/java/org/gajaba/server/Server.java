package org.gajaba.server;

import com.sun.enterprise.ee.cms.core.JoinNotificationSignal;
import com.sun.enterprise.ee.cms.core.MessageSignal;
import org.gajaba.group.GroupManager;

import java.util.Arrays;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Observer {

    public static final String SERVER_NAME = "GAJABA_SERVER";
    final static Logger logger = Logger.getLogger("gajaba.server.Server");
    private GroupManager manager;
    private Map cache;


    public void start() {
        manager = new GroupManager();
        manager.start(SERVER_NAME);
        manager.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println(arg);
        if (arg instanceof JoinNotificationSignal) {
            JoinNotificationSignal signal = (JoinNotificationSignal) arg;
            logger.log(Level.INFO, "member joined : " + signal.getMemberToken());
        } else if (arg instanceof MessageSignal) {
            MessageSignal signal = (MessageSignal) arg;
            if (Arrays.equals(signal.getMessage(), GroupManager.CACHE_CHANGE.getBytes())) {
                cache = manager.getCache();
                System.out.println(cache);
            }
        }
    }
}
