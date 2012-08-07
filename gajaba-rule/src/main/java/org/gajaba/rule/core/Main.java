package org.gajaba.rule.core;

import com.sun.enterprise.ee.cms.core.DistributedStateCache;
import com.sun.enterprise.ee.cms.core.GMSException;
import com.sun.enterprise.ee.cms.core.GMSFactory;
import com.sun.enterprise.ee.cms.core.GroupManagementService;

import java.io.Serializable;
import java.util.Map;

//import javax.script.*;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.logging.Level;


public class Main {

    public static void main(String[] args) throws Exception {

        // ===========Rule Engine Main====================
//        String src = "@ip=serverIp;";
//        ScriptEngine engine = new DSLEngine();
//
//        try {
//            Compilable compiler = (Compilable) engine;
//            CompiledScript compiledScript = compiler.compile(src);
//            Bindings bindings = new SimpleBindings();
//
//            Client a = new Client("server A");
//            Client b = new Client("server B");
//            Client c = new Client("server C");
//            bindings.put("clients", Arrays.asList(a, b));
//
//            Map<Client,String> map = new HashMap<Client, String>();
//            map.put(a,"100.10.29.12");
//            map.put(b,"100.10.29.13");
//            map.put(c,"100.10.29.12");
//            bindings.put("serverIp", map);
//
//            bindings.put("ip", "100.10.29.12");
//
//            Object answer = compiledScript.eval(bindings);
//            System.out.println("acceptable servers : " + answer);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        // ==================================================


        // ==============Shoal Sample Main======================
//        SimpleShoalGMSSample sgs = new SimpleShoalGMSSample();
//        try {
//            sgs.runSimpleSample();
//        } catch (GMSException e) {
//            //logger.log(Level.SEVERE, "Exception occured while joining group:"+e);
//        }

        // ==================================================

        //=================DistributedStateCache Test Main====================

        final String serverName = "server1";
        final String groupName = "Group1";

        GroupManagementService gms = (GroupManagementService)
                GMSFactory.startGMSModule(serverName, groupName, GroupManagementService.MemberType.CORE, null);
        try {
            gms.join();
            //gms.getGroupHandle().sendMessage(null, "message".getBytes());
            DistributedStateCache dsc = gms.getGroupHandle().getDistributedStateCache();

            dsc.addToCache("name", "id", (Serializable) "Key", (Serializable) "State");

            Object o = dsc.getFromCache("name", "id", (Serializable) "Key");
            System.out.println(o);
            Map map = dsc.getAllCache();
            System.out.println(map.toString());

        } catch (GMSException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        // ==================================================

    }

}
