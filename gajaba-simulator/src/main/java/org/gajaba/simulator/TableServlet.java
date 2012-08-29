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

    /**
     * Constructor
     * @param gajabaServer Server
     * @param separator GMSSeparator
     */
    public TableServlet(Server gajabaServer, GMSSeparator separator) {
        this.server = gajabaServer;
        this.separator = separator;
    }

    /**
     * Create the table according to the distributed cache
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {

        String table;
        PrintWriter out = resp.getWriter();
        Map<String, String> map = this.server.getDistributedCache();

        table = "<table class=\"altrowstable\" id=\"alternatecolor\">\n" +
                "<tr>\n" +
                "<th>GMSMember</th>\n" +
                "<th>Key</th>\n" +
                "<th>Value</th>\n" +
                "</tr>\n";

        Iterator it = map.entrySet().iterator();
        int row = 0;
        while (it.hasNext()) {
            String cssClass = (row%2 == 0) ? "evenrowcolor" : "oddrowcolor";
            Map.Entry pairs = (Map.Entry)it.next();
            Object key =  pairs.getKey();
            table += "<tr class=\"trHover " + cssClass + "\"><td>"+separator.getMemberTokenId(key)+"</td><td>"+separator.getKey(key)+"</td><td>"+pairs.getValue()+"</td></tr>";
            row++;
            it.remove(); // avoids a ConcurrentModificationException
        }
        table += "</table>";
        out.println(table);
    }

}
