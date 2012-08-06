package org.gajaba.rule.core;

import com.sun.enterprise.ee.cms.core.GMSException;
import org.gajaba.rule.shoalDSC.SimpleShoalGMSSample;

//import javax.script.*;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.logging.Level;


public class Main {

    public static void main(String[] args) throws Exception {
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

        SimpleShoalGMSSample sgs = new SimpleShoalGMSSample();
        try {
            sgs.runSimpleSample();
        } catch (GMSException e) {
            //logger.log(Level.SEVERE, "Exception occured while joining group:"+e);
        }

    }

}
