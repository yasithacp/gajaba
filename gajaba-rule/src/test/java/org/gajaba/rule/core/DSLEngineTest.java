package org.gajaba.rule.core;

import org.gajaba.rule.compiler.GajabaDSLCompliedScript;

import javax.script.Bindings;
import javax.script.CompiledScript;
import javax.script.SimpleBindings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DSLEngineTest {

    @org.junit.Test
    public void testEvalWithDoubleState() throws Exception {
        String src = "#\"x\"=#\"a\";";

        DSLEngine engine = new DSLEngine();
        CompiledScript compiledScript = engine.compile(src);

        Bindings bindings = new SimpleBindings();
        MockClient a = new MockClient("MOCK_GROUP", "agent1", "a");
        MockClient a3 = new MockClient("MOCK_GROUP", "agent1", "x");
        MockClient c = new MockClient("MOCK_GROUP", "agent3", "a");
        MockClient c2 = new MockClient("MOCK_GROUP", "agent3", "x");

        bindings.put("agents", Arrays.asList("agent1", "agent2", "agent3"));
        Map<MockClient, String> map = new HashMap<MockClient, String>();
        map.put(a, "1");
        map.put(a3, "1");
        map.put(c, "2.1");
        map.put(c2, "2.2");

        bindings.put("cache", map);
        bindings.put("ip", "100.10.29.13");
        bindings.put("separator", new MockSeparator());

        Object answer = compiledScript.eval(bindings);
        System.out.println(answer);
    }

    @org.junit.Test
    public void testEval() throws Exception {
        System.out.println("Testing DSLEngine:eval()");

        String src = "#\"serverIp\"=@ip;";

        DSLEngine engine = new DSLEngine();
        CompiledScript compiledScript = engine.compile(src);
        GajabaDSLCompliedScript gajabaDSLCompliedScript = (GajabaDSLCompliedScript) compiledScript;

        List<String> inputVariables = gajabaDSLCompliedScript.getInputVariables();
        assertEquals("only one input variable in this script", 1,inputVariables.size());
        assertEquals("'ip' should be the input variable", "ip",inputVariables.get(0));

        Bindings bindings = new SimpleBindings();
        MockClient a = new MockClient("MOCK_GROUP", "agent1", "serverIp");
        MockClient b = new MockClient("MOCK_GROUP", "agent2", "serverIp");
        MockClient c = new MockClient("MOCK_GROUP", "agent3", "serverIp");
        MockClient c2 = new MockClient("MOCK_GROUP", "agent3", "load");
        bindings.put("agents", Arrays.asList("agent1", "agent2", "agent3"));
        Map<MockClient, String> map = new HashMap<MockClient, String>();
        map.put(a, "100.10.29.12");
        map.put(b, "100.10.29.13");
        map.put(c, "100.10.29.12");
        map.put(c2, "23");
        bindings.put("cache", map);
        bindings.put("ip", "100.10.29.13");
        bindings.put("separator", new MockSeparator());

        Object answer = compiledScript.eval(bindings);
        System.out.println(answer);

        assertEquals(Arrays.asList("agent2"), answer);
    }
}
