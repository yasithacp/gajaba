import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class HelloWorld extends AbstractHandler {
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        response.getWriter().println(this.getHtmlString());
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        server.setHandler(new HelloWorld());

        server.start();
        server.join();
    }

    public String getHtmlString() throws IOException {
        String filePath = new String("index.html");
        String htmlString = this.readFileAsString(filePath);
        htmlString = htmlString.replace("#table", this.getHtmlForTable());
        return htmlString;

    }

    private String getHtmlForTable() {
        TableGenerator tg = new TableGenerator();
        return tg.getHtml();
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
