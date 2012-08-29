package org.gajaba.simulator;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.gajaba.group.GMSSeparator;
import org.gajaba.rule.core.RuleDefinition;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;

public class Simulator extends AbstractHandler {


    private HashMap<String, HttpServlet> map;

    /**
     *
     * @param target
     * @param baseRequest
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
        //response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        InputStream input = this.getInputPutStream(target);

        if (target.startsWith("/dynamic/")) {
            String substring = target.substring(9);
            HttpServlet servlet = map.get(substring);;
            servlet.service(request,response);
        } else {
            if (input == null) {
                response.sendError(404, "Not found in jar");
            } else {
                OutputStream output = response.getOutputStream();
                copyStream(input, output);
                input.close();
                output.close();
            }
        }
    }

    public static void main(String[] args) throws Exception {

        RuleDefinition def = RuleDefinition.createRuleDefinition("@ip=serverIp;");

        org.gajaba.server.Server gajabaServer = new org.gajaba.server.Server(def);
        gajabaServer.start();

        Simulator simulator = new Simulator();
        simulator.startServer(gajabaServer, new GMSSeparator());
    }

    /**
     * Start the server
     * @param gajabaServer Server
     * @param separator GMSSeparator
     * @throws Exception
     */
    public void startServer(org.gajaba.server.Server gajabaServer, GMSSeparator separator) throws Exception {
        map = new HashMap<String , HttpServlet>();
        map.put("table", new TableServlet(gajabaServer,separator));
        map.put("tree", new TreeJsonServlet(gajabaServer,separator));
        map.put("rule", new RuleServlet(gajabaServer,separator));
        map.put("ruleData", new RuleDataServlet(gajabaServer,separator));

        Server server = new Server(8080);
        server.setHandler(this);

        server.start();
        server.join();

    }

    /**
     * Get the requested target as an input stream
     * @param target String
     * @return
     * @throws IOException
     */
    public InputStream getInputPutStream(String target) throws IOException {
        String filePath;
        switch (target) {
            case "/":
                filePath = new String("webapp/index.html");
                break;
            default:
                filePath = new String("webapp" + target);
        }

        return this.getClass().getResourceAsStream(filePath);

    }


    /**
     * Copy the given input stream to output stream
     * @param input InputStream
     * @param output OutputStream
     * @throws IOException
     */
    public static void copyStream(InputStream input, OutputStream output)
            throws IOException {
        byte[] buffer = new byte[1024]; // Adjust if you want
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }
}
