package org.gajaba.simulator;

import org.gajaba.group.GMSSeparator;
import org.gajaba.server.Server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: yasitha
 * Date: 8/29/12
 * Time: 9:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleDataServlet extends HttpServlet {

    private Server server;
    private GMSSeparator separator;

    /**
     * Constructor
     * @param gajabaServer Server
     * @param separator GMSSeparator
     */
    public RuleDataServlet(Server gajabaServer, GMSSeparator separator) {
        this.server = gajabaServer;
        this.separator = separator;
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        PrintWriter out = resp.getWriter();
        out.println(server.getRuleDefinition().getCode());
    }
}
