package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import encode.Encoder;
import infoObjects.PeopleRequest;
import infoObjects.PeopleResult;
import infoObjects.PersonRequest;
import infoObjects.PersonResult;
import service.PeopleService;
import service.PersonService;

/**
 * Created by tyler on 2/13/2017.
 * Handles multiple people or one person requests and sends appropriate info to the correct service routines
 */

public class PersonHandler implements HttpHandler {

    public PersonHandler() {
    }

    @Override
    /**This method handles the getPerson request from the server*/
    public void handle(HttpExchange exchange) throws IOException {
        Encoder encode = new Encoder();
        OutputStream respBody = exchange.getResponseBody();
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post") || exchange.getRequestMethod().toLowerCase().equals("get")) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                String authToken = exchange.getRequestHeaders().getFirst("Authorization");
                String[] params  = exchange.getRequestURI().toString().split("/");
                if(params.length == 3) {
                    doPerson(respBody,params[2],authToken);
                }
                else {
                    doPeople(respBody,authToken);
                }

            }
            else {
                //if we got an Error in the request we will reach here
                //System.out.println("errorPerson");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                encode.encode(new Exception("Should be a GET request not POST"), respBody);
                respBody.close();
            }
        }
        catch(IllegalArgumentException e){
            // System.out.println("errorPersonIllegal");
            encode.encode(e,respBody);
            respBody.close();
        }
        catch (IOException e) {
            //System.out.println("errorPersonIO");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            encode.encode(e,respBody);
            respBody.close();
            //e.printStackTrace();
        }

    }
    /**
     * Grabs a single person
     * @PARAM OutputStream, holds the response body
     * @PARAM String, the personID
     * @PARAM authToken, the authtoken to validate
     * */
    private void doPerson(OutputStream respBody,String PersonID,String authToken) throws IOException {
        PersonService service = new PersonService();
        PersonRequest request = new PersonRequest(PersonID,authToken);
        Encoder encode = new Encoder();
        PersonResult result = service.getPerson(request);
        encode.encode(result, respBody);
        respBody.close();
    }

    /**
     * Grabs a single person
     * @PARAM OutputStream, holds the response body
     * @PARAM authToken, the authtoken to validate
     * */
    private void doPeople(OutputStream respBody,String authToken) throws IOException {
        PeopleService service = new PeopleService();
        PeopleRequest request = new PeopleRequest(authToken);
        PeopleResult result = service.getPeople(request);
        new Encoder().encode(result, respBody);
        respBody.close();
    }
}
