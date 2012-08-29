package org.gajaba.simulator;

import org.gajaba.group.GMSSeparator;
import org.gajaba.rule.core.RuleDefinition;
import org.gajaba.server.Server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: yasitha
 * Date: 8/29/12
 * Time: 9:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleServlet extends HttpServlet {

    private Server server;
    private GMSSeparator separator;

    /**
     * Constructor
     * @param gajabaServer Server
     * @param separator GMSSeparator
     */
    public RuleServlet(Server gajabaServer, GMSSeparator separator) {
        this.server = gajabaServer;
        this.separator = separator;
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        String field = req.getParameter("textarea");
        System.out.println(field);
        RuleDefinition def = RuleDefinition.createRuleDefinition(field, separator);
        server.setRuleDefinition(def);
        resp.sendRedirect("/ruleEditor.html");
    }
}
