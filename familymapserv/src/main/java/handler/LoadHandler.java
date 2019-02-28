package handler;

import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.sql.SQLException;

import encode.Encoder;
import infoObjects.LoadRequest;
import infoObjects.LoadResult;
import service.LoadService;

/**
 * Created by tyler on 2/14/2017.
 * This class handles calls on the server to load preset information into the database
 */

public class LoadHandler implements HttpHandler{

    public LoadHandler() {
    }

    @Override
    /**This method handles the load request from the server*/
    public void handle(com.sun.net.httpserver.HttpExchange exchange) throws IOException {
        OutputStream respBody = exchange.getResponseBody();
        Encoder encode = new Encoder();
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                LoadService service = new LoadService();
                LoadRequest request = encode.decodeLoadRequest(exchange.getRequestBody());//new JsonData().getLoadData();

                LoadResult result = service.load(request);
                encode.encode(result, respBody);
                respBody.close();
            }
            else{
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                encode.encode("Should be a post request, not get",respBody);
                respBody.close();
            }
        } catch(SQLException e){
            encode.encode(e.getMessage(),respBody);
            respBody.close();
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            encode.encode(e.getMessage(),respBody);
            respBody.close();
        }
        catch(Exception e){
            encode.encode(e.getMessage(),respBody);
            respBody.close();
        }
    }
}
