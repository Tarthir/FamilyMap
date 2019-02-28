package com.tylerbrady34gmail.familyclient.Models;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import encode.Encoder;
import infoObjects.ClearResult;
import infoObjects.EventRequest;
import infoObjects.EventResult;
import infoObjects.EventsRequest;
import infoObjects.EventsResult;
import infoObjects.FillRequest;
import infoObjects.FillResult;
import infoObjects.LoadRequest;
import infoObjects.LoadResult;
import infoObjects.LoginRequest;
import infoObjects.LoginResult;
import infoObjects.PeopleRequest;
import infoObjects.PeopleResult;
import infoObjects.PersonRequest;
import infoObjects.PersonResult;
import infoObjects.RegisterRequest;
import infoObjects.RegisterResult;

/**
 * Created by tyler on 2/10/2017.
 * This class interacts with the client and the web API
 * Sends JSON to the web(Main server)
 */

public class FamilyMapServerProxy {
    //public static final FamilyMapServerProxy SINGLETON = new FamilyMapServerProxy();

    private String serverHost; //= FamilyMapServerProxy.SINGLETON.getServerHost();//"128.187.83.252";//need to connect in a different way
    private String serverPort; //= FamilyMapServerProxy.SINGLETON.getServerPort();//"8080";
    private static String authToken;

    public FamilyMapServerProxy() {
        //FamilyMapServerProxy.SINGLETON.setServerHost("localhost");
        //FamilyMapServerProxy.SINGLETON.setServerPort("8080");
        this.serverHost = "localhost";//FamilyMapServerProxy.SINGLETON.getServerHost();
        this.serverPort = "8080";//FamilyMapServerProxy.SINGLETON.getServerPort();
    }

    public FamilyMapServerProxy(String serverHost, String serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    /***
     * A method to register a new user
     *
     * @PARAM request, the request to register a new user
     * @Return the result, successful or not of the register attempt
     */
    public RegisterResult register(URL url, RegisterRequest request) {

        try {
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true);    // There is a request body, dofor all except clear

            http.addRequestProperty("Accept", "application/json");
            Encoder encoder = new Encoder();
            encoder.encode(request, http.getOutputStream());
            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream respBody = http.getInputStream();

                String respData = readString(respBody);

                RegisterResult result = encoder.decodeRegResult(respData);
                authToken = result.getAuthToken();
                return result;
            } else {

                InputStream resBody = http.getErrorStream();

                String respData = readString(resBody);

                System.out.println(respData);

                System.out.println("ERROR: " + http.getResponseMessage());

                return encoder.decodeRegResult(respData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * A method to login a user
     *
     * @Param request, this object holds the info needed to successfully login
     * @Return the result, successful or not of the login attempt
     */
    public LoginResult login(URL url, LoginRequest request) {

        try {
            //IP address and port
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true);    // There is a request body, dofor all except clear

            // http.addRequestProperty("Authorization", );
            //http.addRequestProperty("Accept", "application/json");
            Encoder encode = new Encoder();
            encode.encode(request, http.getOutputStream());
            http.connect();

            InputStream resBody = null;
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                resBody = http.getInputStream();

            } else {

                resBody = http.getErrorStream();

            }
            LoginResult result = encode.decodeLoginResult(resBody);
            authToken = result.getAuthToken();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * A method to get a user's ancestor's info
     *
     * @PARAM request, the info needed to make a request on the database for a specific ancestor
     * @Return the result, a person object; successful or not of the getPerson attempt
     */
    public PersonResult getPerson(PersonRequest request) {

        try {
            //IP address and port
            URL url = new URL("http://" + serverHost + ":"
                    + serverPort + "/person/" + request.getPersonID());

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setDoOutput(true);    // There is a request body, do for all except clear
            http.setRequestMethod("GET");


            http.addRequestProperty("Authorization", authToken);
            http.addRequestProperty("Accept", "application/json");
            Encoder encode = new Encoder();
            encode.encode(request, http.getOutputStream());
            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return encode.decodePersonResult(http.getInputStream());
            } else {
                System.out.println("ERROR: " + http.getResponseMessage());
                return encode.decodePersonResult(http.getErrorStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * A method to get all of a users ancestor's
     *
     * @PARAM request, the info needed to make a request on the database for all ancestors
     * @Return the array of people related to the User
     */
    public PeopleResult getPeople(URL url, PeopleRequest request) {

        try {
            //IP address and port
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setDoOutput(true);    // There is a request body, do for all except clear
            http.setRequestMethod("GET");
            if (!request.getAuthToken().equals("")) {//if we have an authtoken
                http.addRequestProperty("Authorization", request.getAuthToken()); //NEED TO SEND authorization
            }
            else{
                http.addRequestProperty("Authorization", authToken); //NEED TO SEND authorization
            }
            http.addRequestProperty("Accept", "application/json");
            Encoder encode = new Encoder();
            encode.encode(request, http.getOutputStream());
            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                return encode.decodePeopleResult(http.getInputStream());
            } else {
                System.out.println("ERROR: " + http.getResponseMessage());
                return encode.decodePeopleResult(http.getErrorStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * A method to get a specific event
     *
     * @PARAM request, the info needed to make a request on a particular event of a particular person of a user
     * @Return the result, an event object; successful or not of the getEvent Attempt attempt
     */
    public EventResult getEvent(EventRequest request) {

        try {
            //IP address and port
            URL url = new URL("http://" + serverHost
                    + ":" + serverPort + "/event/" + request.getEventID());

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setDoOutput(true);    // There is a request body, dofor all except clear
            http.setRequestMethod("GET");


            http.addRequestProperty("Authorization", authToken);
            http.addRequestProperty("Accept", "application/json");
            Encoder encode = new Encoder();
            encode.encode(request, http.getOutputStream());

            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                return encode.decodeEventResult(http.getInputStream());
            } else {
                System.out.println("ERROR: " + http.getResponseMessage());
                return encode.decodeEventResult(http.getErrorStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * A method to get all of a user's ancestor's events
     *
     * @PARAM request, the info needed to make a request on the database for all events of a user's ancestor
     * @Return the result, an event object array; successful or not of the getEvents Attempt attempt
     */
    public EventsResult getEvents(URL url, EventsRequest request) {

        try {
            //IP address and port

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setDoOutput(true);    // There is a request body, dofor all except clear
            http.setRequestMethod("GET");

            if (!request.getAuthToken().equals("")) {
                http.addRequestProperty("Authorization", request.getAuthToken()); //NEED TO SEND authorization
            }
            else{
                http.addRequestProperty("Authorization", authToken); //NEED TO SEND authorization
            }
            http.addRequestProperty("Accept", "application/json");
            Encoder encode = new Encoder();
            encode.encode(request, http.getOutputStream());

            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                EventsResult result = encode.decodeEventsResult(http.getInputStream());
                return result;
            } else {
                System.out.println("ERROR: " + http.getResponseMessage());
                return encode.decodeEventsResult(http.getErrorStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * Calls a method to clear the database
     *
     * @Return the result, successful or not of the clear attempt
     */
    public ClearResult clear() {

        try {
            //IP address and port
            URL url = new URL("http://" + serverHost
                    + ":" + serverPort + "/clear");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(false);    // There is a request body, dofor all except clear
            http.addRequestProperty("Accept", "application/json");

            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                return new Encoder().decodeClearResult(http.getInputStream());
            } else {
                System.out.println("ERROR: " + http.getResponseMessage());
                return new Encoder().decodeClearResult(http.getErrorStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * Calls a method to fill the database with new data
     *
     * @Return the result, a FillResult object; successful or not of the fill attempt
     */
    public FillResult fill(FillRequest request) {

        try {
            //IP address and port
            URL url = null;
            if (request.getNumOfGenerations() == 0) {
                url = new URL("http://" + serverHost
                        + ":" + serverPort + "/fill/" + request.getUserName());
            } else {
                url = new URL("http://" + serverHost
                        + ":" + serverPort + "/fill/" + request.getUserName() + "/" + request.getNumOfGenerations());
            }

            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true);    // There is a request body, dofor all except clear

            http.addRequestProperty("Accept", "application/json");
            Encoder encode = new Encoder();
            encode.encode(request, http.getOutputStream());
            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                return new Encoder().decodeFillResult(http.getInputStream());
            } else {
                System.out.println("ERROR: " + http.getResponseMessage());
                return new Encoder().decodeFillResult(http.getErrorStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * Calls a method to fill the database with new Data
     *
     * @Param request, A object which contains the info needs to load up a database
     * @Return the result, an loadResult object; successful or not of the load attempt
     */
    public LoadResult load(LoadRequest request) {

        try {
            //IP address and port
            URL url = new URL("http://" + serverHost
                    + ":" + serverPort + "/load");

            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true);    // There is a request body, dofor all except clear
            http.addRequestProperty("Accept", "application/json");

            Encoder encode = new Encoder();
            encode.encode(request, http.getOutputStream());
            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                return encode.decodeLoadResult(http.getInputStream());
            } else {
                System.out.println("ERROR: " + http.getResponseMessage());
                return encode.decodeLoadResult(http.getErrorStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }
}
