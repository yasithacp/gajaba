package org.gajaba.simulator;


import com.sun.enterprise.ee.cms.core.GMSCacheable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TreeJsonServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        PrintWriter out = resp.getWriter();
        feedDummyData(out);
    }

    private void feedDummyData(PrintWriter out){
        out.print("{\n" +
                " \"name\": \"Load Balancer\",\n" +
                " \"children\": [\n" +
                "  {\n" +
                "   \"name\": \"Server 1\",\n" +
                "   \"children\": [\n" +
                "    {\n" +
                "     \"name\": \"Load = 50\"\n" +
                "    },\n" +
                "    {\n" +
                "     \"name\": \"Services\",\n" +
                "     \"children\": [\n" +
                "      {\"name\": \"Service_1\", \"size\": 3534},\n" +
                "      {\"name\": \"Service_2\", \"size\": 5731},\n" +
                "      {\"name\": \"Service_3\", \"size\": 7840},\n" +
                "      {\"name\": \"Service_4\", \"size\": 5914}\n" +
                "     ]\n" +
                "    },\n" +
                "    {\n" +
                "     \"name\": \"Server Type = type_1\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Server Mode = mode_1\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Server Status = Connected\"\n" +
                "    }\n" +
                "   ]\n" +
                "  },\n" +
                "  {\n" +
                "   \"name\": \"Server 2\",\n" +
                "   \"children\": [\n" +
                "    {\n" +
                "     \"name\": \"Load = 30\"\n" +
                "    },\n" +
                "    {\n" +
                "     \"name\": \"Services\",\n" +
                "     \"children\": [\n" +
                "      {\"name\": \"Service_1\", \"size\": 3534},\n" +
                "      {\"name\": \"Service_2\", \"size\": 5731},\n" +
                "      {\"name\": \"Service_4\", \"size\": 5914}\n" +
                "     ]\n" +
                "    },\n" +
                "    {\n" +
                "     \"name\": \"Server Type = type_1\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Server Mode = mode_2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Server Status = Connected\"\n" +
                "    }\n" +
                "   ]\n" +
                "  },\n" +
                "  {\n" +
                "   \"name\": \"Server 3\",\n" +
                "   \"children\": [\n" +
                "    {\n" +
                "     \"name\": \"Load = 700\"\n" +
                "    },\n" +
                "    {\n" +
                "     \"name\": \"Services\",\n" +
                "     \"children\": [\n" +
                "      {\"name\": \"Service 2\", \"size\": 5731}\n" +
                "     ]\n" +
                "    },\n" +
                "    {\n" +
                "     \"name\": \"Server Type = type_2\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Server Mode = mode_1\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Server Status = Connected\"\n" +
                "    }\n" +
                "   ]\n" +
                "  },\n" +
                "  {\n" +
                "   \"name\": \"Server 4\",\n" +
                "   \"children\": [\n" +
                "    {\n" +
                "     \"name\": \"Load = 10\"\n" +
                "    },\n" +
                "    {\n" +
                "     \"name\": \"Services\",\n" +
                "     \"children\": [\n" +
                "      {\"name\": \"Service_1\", \"size\": 3534},\n" +
                "      {\"name\": \"Service_2\", \"size\": 5731}\n" +
                "     ]\n" +
                "    },\n" +
                "    {\n" +
                "     \"name\": \"Server Type = type_1\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Server Mode = mode_1\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Server Status = Connected\"\n" +
                "    }\n" +
                "   ]\n" +
                "  }\n" +
                " ]\n" +
                "}");
    }

    // method to create a dummy cache
    private HashMap createDummyCache(){
        HashMap<GMSCacheable, String> cache = new HashMap();
        cache.put(new GMSCacheable("ComponentName1", "MemberTokenId1", (Serializable) "key1"), "value1");
        cache.put(new GMSCacheable("ComponentName1", "MemberTokenId1", (Serializable) "key2"), "value2");
        cache.put(new GMSCacheable("ComponentName1", "MemberTokenId1", (Serializable) "key3"), "value3");
        cache.put(new GMSCacheable("ComponentName2", "MemberTokenId1", (Serializable) "key4"), "value4");
        cache.put(new GMSCacheable("ComponentName3", "MemberTokenId1", (Serializable) "key5"), "value5");
        cache.put(new GMSCacheable("ComponentName1", "MemberTokenId1", (Serializable) "key6"), "value6");
        cache.put(new GMSCacheable("ComponentName1", "MemberTokenId1", (Serializable) "key7"), "value7");
        cache.put(new GMSCacheable("ComponentName2", "MemberTokenId1", (Serializable) "key8"), "value8");
        cache.put(new GMSCacheable("ComponentName3", "MemberTokenId1", (Serializable) "key9"), "value9");
        cache.put(new GMSCacheable("ComponentName1", "MemberTokenId1", (Serializable) "key10"), "value10");
        cache.put(new GMSCacheable("ComponentName3", "MemberTokenId1", (Serializable) "key11"), "value11");
        cache.put(new GMSCacheable("ComponentName1", "MemberTokenId1", (Serializable) "key12"), "value12");
        cache.put(new GMSCacheable("ComponentName3", "MemberTokenId1", (Serializable) "key13"), "value13");
        cache.put(new GMSCacheable("ComponentName1", "MemberTokenId1", (Serializable) "key14"), "value14");
        return cache;
    }

    private void writeDataFromCache(HashMap map){
        String text="{\n" +
                " \"name\": \"Load Balancer\" ";
        Iterator it;
        if(!map.isEmpty()){

            it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();
                Object key =  pairs.getKey();
                //todo: make the children tree(making the string like in feedDummyData() method
                text += "adding childern algo goes here";
                it.remove(); // avoids a ConcurrentModificationException
            }
        }
    }

//    private void temp(){
//        HashMap<GMSCacheable, String> cache = createDummyCache();
//        System.out.println("The raw map: "+cache);
//        ValueComparator bvc =  new ValueComparator(map);
//        TreeMap<String,Double> sorted_map = new TreeMap<String,Double>(bvc);
//    }
}
