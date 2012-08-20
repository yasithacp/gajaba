package org.gajaba.rule.core;

import javax.script.Bindings;
import javax.script.CompiledScript;
import javax.script.SimpleBindings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: kasuncp
 * Date: 8/20/12
 * Time: 12:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class DSLEngineTest {

    @org.junit.Test
    public void testEval() throws Exception {
        System.out.println("Testing DSLEngine:eval()");

        String src = "@ip=serverIp;";

        try {
            DSLEngine engine = new DSLEngine();
            CompiledScript compiledScript = engine.compile(src);
            Bindings bindings = new SimpleBindings();

            String a = "server A";
            String b = "server B";
            String c = "server C";
            bindings.put("clients", Arrays.asList(a, b));

            Map<String,String> map = new HashMap<String, String>();
            map.put(a,"100.10.29.12");
            map.put(b,"100.10.29.13");
            map.put(c,"100.10.29.12");
            bindings.put("serverIp", map);

            bindings.put("ip", "100.10.29.13");

            Object answer = compiledScript.eval(bindings);

            assertTrue(answer.toString().equals("[server B]"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
