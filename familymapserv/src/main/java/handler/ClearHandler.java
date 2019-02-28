package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import encode.Encoder;
import infoObjects.ClearResult;
import service.ClearService;

/**
 * Created by tyler on 2/13/2017.
 * Handles clear commands that come in by calling/Creating a ClearService obj which will use the DAOs to access the database
 */

public class ClearHandler implements HttpHandler {

    public ClearHandler(){}
    @Override
    /**This method handles the clear request from the server*/
    public void handle(HttpExchange exchange) throws IOException {
        OutputStream respBody = exchange.getResponseBody();
        ClearService service = new ClearService();
        Encoder encoder = new Encoder();
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);//otherwise send Forbidden/BadRequest/etc as needed

                ClearResult result = service.clear();
                encoder.encode(result,respBody);

                respBody.close();
            }
            else{
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                encoder.encode(new ClearResult("Should be a POST not GET"),respBody);
                respBody.close();
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            encoder.encode(new ClearResult("Server Error"),respBody);
            respBody.close();
            //e.printStackTrace();
        }
    }
}
