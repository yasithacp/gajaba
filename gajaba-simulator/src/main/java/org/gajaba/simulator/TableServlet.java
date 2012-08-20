package org.gajaba.simulator;

import org.gajaba.group.GMSSeparator;
import org.gajaba.server.Server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

public class TableServlet extends HttpServlet {


    private Server server;
    private GMSSeparator separator;

    public TableServlet(Server gajabaServer, GMSSeparator separator) {
        this.server = gajabaServer;
        this.separator = separator;
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {

        String table;
        PrintWriter out = resp.getWriter();
        Map<String, String> map = this.server.getDistributedcache();

        table = "<table>\n" +
                "<tr>\n" +
                "<th>GMSMember</th>\n" +
                "<th>Key</th>\n" +
                "<th>Value</th>\n" +
                "</tr>\n";

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            Object key =  pairs.getKey();
            table += "<tr><td>"+separator.getMemberTokenId(key)+"</td><td>"+separator.getKey(key)+"</td><td>"+pairs.getValue()+"</td></tr>";
            it.remove(); // avoids a ConcurrentModificationException
        }
        table += "</table>";
        out.println(table);
    }

}
