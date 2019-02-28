package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.sql.SQLException;

import encode.Encoder;
import infoObjects.EventRequest;
import infoObjects.EventResult;
import infoObjects.EventsRequest;
import infoObjects.EventsResult;
import service.EventService;
import service.EventsService;

/**
 * Created by tyler on 2/13/2017.
 * Handles the cases of requests for one event or multiple events and passes that info to the
 * appropriate service routines which use the DAOs to access the database
 */

public class EventHandler implements HttpHandler {
    /**Creates an object which handles getting one event from a person*/
    public EventHandler() {
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Encoder encode = new Encoder();
        OutputStream respBody = exchange.getResponseBody();
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post") || exchange.getRequestMethod().toLowerCase().equals("get")) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                String authToken = exchange.getRequestHeaders().getFirst("Authorization");
                String[] params  = exchange.getRequestURI().toString().split("/");
                if(params.length == 3) {
                    doEvent(respBody,params[2],authToken);//params[2] is the eventID
                }
                else {
                    doEvents(respBody,authToken);
                }

            }
            else {
                //if we got an Error in the request we will reach here
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                encode.encode(new EventResult("Should be a GET request not POST"), respBody);
                respBody.close();
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            encode.encode(new EventResult(e.getMessage()),respBody);
            respBody.close();
        }
        catch(Exception e){
            encode.encode(new EventResult(e.getMessage()),respBody);
            respBody.close();
        }

    }

    /**
     * Grabs a single event
     * @PARAM OutputStream, holds the response body
     * @PARAM String, the personID
     * @PARAM authToken, the authtoken to validate
     * */
    private void doEvent(OutputStream respBody,String eventID,String authToken) throws IllegalArgumentException, IOException, SQLException {
        EventService service = new EventService();
        EventRequest request = new EventRequest(eventID,authToken);
        if(request.isValidRequest()) {
            EventResult eventResult = service.getEvent(request);
            new Encoder().encode(eventResult,respBody);
        }
        else{
            new Encoder().encode(new EventResult("Invalid Request"),respBody);
        }

        respBody.close();
    }

    /**
     * Grabs a multiple events
     * @PARAM OutputStream, holds the response body
     * @PARAM authToken, the authtoken to validate
     * */
    private void doEvents(OutputStream respBody,String authToken) throws IllegalArgumentException, IOException, SQLException {
        EventsService service = new EventsService();
        EventsRequest request = new EventsRequest(authToken);
        if(request.isValidRequest()) {
            EventsResult eventsResult = service.getEvents(request);
            new Encoder().encode(eventsResult,respBody);
        }
        else{
            new Encoder().encode(new EventResult("Invalid Request"),respBody);
        }
        respBody.close();
    }
}
