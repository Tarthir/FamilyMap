package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import service.IndexService;

/**
 * Created by tyler on 2/28/2017.
 * Handles requests for Index.html
 */

public class IndexHandler implements HttpHandler {
    //exchange variable holds the http info

    public void handle(HttpExchange exchange)throws IOException {
        IndexService service = new IndexService();
        OutputStream respBody = exchange.getResponseBody();
        String[] params = null;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);//otherwise send Forbidden/BadRequest/etc as needed
                params  = exchange.getRequestURI().toString().split("/");

                service.sendRequested(respBody,params);

            }
            else{
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                service.sendRequested(respBody,params);
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            respBody.close();
           // e.printStackTrace();
        }
    }

   /* private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }*/
}
