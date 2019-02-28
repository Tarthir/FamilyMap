package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import encode.Encoder;
import infoObjects.RegisterRequest;
import infoObjects.RegisterResult;
import service.RegisterService;

/**
 * Created by tyler on 2/13/2017.
 * Handles requests to register users
 */

public class RegisterHandler implements HttpHandler {

    public RegisterHandler() {

    }

    @Override
    /**This method handles the register request from the server*
     * @PARAM HttpExchange variable holding required info
     * @RETURN void
     */
    public void handle(HttpExchange exchange) throws IOException {
        Encoder encode = new Encoder();
        OutputStream respBody = exchange.getResponseBody();
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                RegisterService service = new RegisterService();
                RegisterRequest request = encode.decodeRegRequest(exchange.getRequestBody());

                RegisterResult result = service.register(request);
                encode.encode(result, respBody);
                respBody.close();
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                encode.encode(new RegisterResult("Should be a POST, not GET"), respBody);
                respBody.close();
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            encode.encode(new RegisterResult(e.getMessage()), respBody);
            respBody.close();
            // e.printStackTrace();
        }
        //System.out.println("errorout");
    }


}
