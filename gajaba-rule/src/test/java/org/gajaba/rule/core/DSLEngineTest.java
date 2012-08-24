package org.gajaba.rule.core;

import javax.script.Bindings;
import javax.script.CompiledScript;
import javax.script.SimpleBindings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class DSLEngineTest {

    @org.junit.Test
    public void testEval() throws Exception {
        System.out.println("Testing DSLEngine:eval()");

        String src = "@ip=serverIp;";

        try {
            DSLEngine engine = new DSLEngine();
            CompiledScript compiledScript = engine.compile(src);
            Bindings bindings = new SimpleBindings();

            MockClient a = new MockClient("MOCK_GROUP", "ip", "100.10.29.12");
            MockClient b = new MockClient("MOCK_GROUP", "ip", "100.10.29.13");
            MockClient c = new MockClient("MOCK_GROUP", "ip", "100.10.29.14");

            bindings.put("cache", Arrays.asList(a, b, c));

            Map<MockClient, String> map = new HashMap<MockClient, String>();
            map.put(a, "100.10.29.12");
            map.put(b, "100.10.29.13");
            map.put(c, "100.10.29.12");
            bindings.put("agents", map);

            bindings.put("ip", "100.10.29.13");
            bindings.put("separator", new MockSeparator());

            Object answer = compiledScript.eval(bindings);

            assertTrue(answer.toString().equals("[server B]"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
