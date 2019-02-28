package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import encode.Encoder;
import encode.JsonData;
import infoObjects.FillRequest;
import infoObjects.FillResult;
import service.FillService;

/**
 * Created by tyler on 2/13/2017.
 * Called by our Main server. We get our requests and pass them off to the FillService class which will use the
 * DAOs to access the database
 */

public class FillHandler implements HttpHandler {
    public FillHandler() {}

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        OutputStream respBody = exchange.getResponseBody();
        FillService service = new FillService();
        Encoder encode = new Encoder();
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                String[] params  = exchange.getRequestURI().toString().split("/");
                FillRequest request = null;

                if(params.length == 4) {//Index three holds the integer and param two holds the userName
                    request = new FillRequest(Integer.valueOf(params[3]), params[2]);
                }
                else{//default to four as num of generations
                    request = new FillRequest(4, params[2]);
                }

                request = new JsonData().setupJSONArrays(request);//grabs the arrays we need
                FillResult result = service.fill(request);
                encode.encode(new FillResult(result.getMessage()), respBody);
                   respBody.close();
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                encode.encode(new FillResult("Should be a POST, not GET"), respBody);
                respBody.close();
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }
    }
}
