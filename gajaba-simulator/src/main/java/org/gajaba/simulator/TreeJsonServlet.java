package org.gajaba.simulator;


import org.gajaba.group.GMSSeparator;
import org.gajaba.server.Server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TreeJsonServlet extends HttpServlet {

    private Server server;
    private GMSSeparator separator;

    /**
     * Constructor
     * @param gajabaServer Server
     * @param separator GMSSeparator
     */
    public TreeJsonServlet(Server gajabaServer, GMSSeparator separator) {
        this.server = gajabaServer;
        this.separator = separator;
    }

    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        PrintWriter out = resp.getWriter();
        Map map = this.server.getDistributedCache();
        String json = this.writeDataFromCache(map);
        out.print(json);
    }

    /**
     * Create the json string
     * @param map HashMap
     * @return
     */
    private String writeDataFromCache(Map map){

        HashMap<String, ArrayList<String>> stringMap = getMapAsAnArray(map);
        String html = "{\n" +
                " \"name\": \"Load Balancer\",\n" +
                " \"children\": [\n";

        Iterator it = stringMap.entrySet().iterator();
        int var = 1;
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            ArrayList<String> values = (ArrayList<String>) pairs.getValue();
            html += "  { \"name\": \""+ pairs.getKey()+"\",\n" +
                    "   \"children\": [\n";

            for(int i=0; i< values.size(); i++){
                html += "    {\n" +
                        "     \"name\": \"" + values.get(i) + "\"\n" +
                        "    }";
                if( i != values.size()-1){
                    html += ",\n";
                } else {
                    html += "\n";
                }
            }
            html += "   ]\n";
            if(var != stringMap.size()){
                html += "  },\n";
            } else {
                html += "  }\n";
            }
            var++;
            //it.remove();
        }
        html += " ]\n" +
                "}";
        return html;
    }

    /**
     * Create a hash map for the iteration
     * @param map HashMap
     * @return
     */
    public HashMap<String, ArrayList<String>> getMapAsAnArray(Map map){

        HashMap<String, ArrayList<String>> stringMap = new HashMap<String, ArrayList<String>>();

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            Object key =  pairs.getKey();
            if(stringMap.get(separator.getMemberTokenId(key)) == null){
                ArrayList<String> arraylist = new ArrayList();
                arraylist.add(separator.getKey(key).toString() + " = " + pairs.getValue().toString());
                stringMap.put(separator.getMemberTokenId(key).toString(), arraylist);
            } else {
                ArrayList<String> existArrayList = stringMap.get(separator.getMemberTokenId(key));
                existArrayList.add(separator.getKey(key).toString() + " = " + pairs.getValue().toString());
                stringMap.remove(separator.getMemberTokenId(key).toString());
                stringMap.put(separator.getMemberTokenId(key).toString(), existArrayList);
            }
            it.remove(); // avoids a ConcurrentModificationException
        }

        return stringMap;
    }

}
