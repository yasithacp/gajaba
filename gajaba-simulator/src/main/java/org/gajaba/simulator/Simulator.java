package org.gajaba.simulator;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.gajaba.rule.core.RuleDefinition;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class Simulator extends AbstractHandler {

    org.gajaba.server.Server gajabaServer;

    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
        //response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        InputStream input = this.getInputPutStream(target);

        if (input == null) {
            response.sendError(404, "not found in jar");
        } else {
            OutputStream output = response.getOutputStream();
            copyStream(input, output);
            input.close();
            output.close();
        }
    }

    public static void main(String[] args) throws Exception {

        RuleDefinition def = RuleDefinition.createRuleDefinition("@ip=serverIp;");

        org.gajaba.server.Server gajabaServer = new org.gajaba.server.Server(def);
        gajabaServer.start();

        Simulator simulator = new Simulator();
        simulator.startServer(gajabaServer);
    }

    public void startServer(org.gajaba.server.Server gajabaServer) throws Exception {

        Server server = new Server(8080);
        server.setHandler(this);
        this.gajabaServer = gajabaServer;
        server.start();
        server.join();

    }

    public InputStream getInputPutStream(String target) throws IOException {
        String filePath;
        switch (target) {
            case "/":
                filePath = new String("webapp/index.html");
                break;
            default:
                filePath = new String("webapp" + target);
        }

        //String htmlString = this.readFileAsString(filePath);
        //htmlString = htmlString.replace("#table", this.getHtmlForTable());
        return this.getClass().getResourceAsStream(filePath);

    }

    private String getHtmlForTable() {
        TableGenerator tg = new TableGenerator();
        return tg.getHtml(this.gajabaServer);
    }

    public static void copyStream(InputStream input, OutputStream output)
            throws IOException {
        byte[] buffer = new byte[1024]; // Adjust if you want
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    private static String readFileAsString(String filePath) throws IOException {

        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[1024];

        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {

            String readData = String.valueOf(buf, 0, numRead);

            fileData.append(readData);

            buf = new char[1024];

        }
        reader.close();
        return fileData.toString();

    }

}
