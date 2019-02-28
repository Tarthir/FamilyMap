package encode;



import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;

import infoObjects.ClearResult;
import infoObjects.EventResult;
import infoObjects.EventsResult;
import infoObjects.FillResult;
import infoObjects.LoadRequest;
import infoObjects.LoadResult;
import infoObjects.LoginRequest;
import infoObjects.LoginResult;
import infoObjects.PeopleResult;
import infoObjects.PersonResult;
import infoObjects.RegisterRequest;
import infoObjects.RegisterResult;


/**
 * Created by tyler on 2/10/2017.
 * Decodes JSON to java objects and encodes java objects into JSON
 */

public class Encoder {
    private Gson gson = new Gson();
    public Encoder() {
    }
    /**
     * This encodes java objects into JSON
     * @RETURN JSon String
     * */
    public void encode(Object obj,OutputStream respBody) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(respBody);
        writer.write(gson.toJson(obj));
        writer.flush();
    }


    /**
     * This decodes java objects from JSON
     * @PARAM String, input
     * @RETURN A result object
     * @EXCEPTION IOException
     * */
    public RegisterResult decodeRegResult(String input)throws IOException{
        return gson.fromJson(input, RegisterResult.class);
    }

    /**
     * This decodes java objects from JSON
     * @PARAM String, input
     * @RETURN A result object
     * @EXCEPTION IOException
     * */
    public RegisterRequest decodeRegRequest(InputStream input)throws IOException{
        Reader reader = new InputStreamReader(input);
        return gson.fromJson(reader, RegisterRequest.class);
    }
    /**
     * This decodes java objects from JSON
     * @PARAM String, input
     * @RETURN A PersonResult object
     * @EXCEPTION IOException
     * */
    public PersonResult decodePersonResult(InputStream input)throws IOException{
        Reader reader = new InputStreamReader(input);
        return gson.fromJson(reader, PersonResult.class);
    }

    /**
     * This decodes java objects from JSON
     * @PARAM String, input
     * @RETURN A PersonResult object
     * @EXCEPTION IOException
     * */
    public EventResult decodeEventResult(InputStream input)throws IOException{
        Reader reader = new InputStreamReader(input);
        return gson.fromJson(reader, EventResult.class);
    }
    /**
     * This decodes java objects from JSON
     * @PARAM String, input
     * @RETURN A PersonResult object
     * @EXCEPTION IOException
     * */
    public EventsResult decodeEventsResult(InputStream input)throws IOException{
        Reader reader = new InputStreamReader(input);
        return gson.fromJson(reader, EventsResult.class);
    }

    /**
     * This decodes java objects from JSON
     * @PARAM String, input
     * @RETURN A PersonResult object
     * @EXCEPTION IOException
     * */
    public ClearResult decodeClearResult(InputStream input)throws IOException{
        Reader reader = new InputStreamReader(input);
        return gson.fromJson(reader, ClearResult.class);
    }

    /**
     * This decodes java objects from JSON
     * @PARAM String, input
     * @RETURN A PersonResult object
     * @EXCEPTION IOException
     * */
    public FillResult decodeFillResult(InputStream input)throws IOException{
        Reader reader = new InputStreamReader(input);
        return gson.fromJson(reader, FillResult.class);
    }

    /**
     * This decodes java objects from JSON
     * @PARAM String, input
     * @RETURN A PeopleResult object
     * @EXCEPTION IOException
     * */
    public PeopleResult decodePeopleResult(InputStream input)throws IOException{
        Reader reader = new InputStreamReader(input);
        return gson.fromJson(reader, PeopleResult.class);
    }

    /**
     * This decodes java objects from JSON
     * @PARAM String Json object
     * @RETURN LoginResult object
     * @EXCEPTION IOException
     * */
    public LoginResult decodeLoginResult(InputStream input)throws IOException{
        Reader reader = new InputStreamReader(input);
        return gson.fromJson(reader, LoginResult.class);
    }

    /**
     * This decodes java objects from JSON
     * @PARAM String Json object
     * @RETURN LoginRequest object
     * @EXCEPTION IOException
     * */
    public LoginRequest decodeLoginRequest(InputStream input)throws IOException{
        Reader reader = new InputStreamReader(input);
        return gson.fromJson(reader, LoginRequest.class);
    }

    /**
     * This decodes java objects from JSON
     * @PARAM HttpExchange object
     * @RETURN LoadRequest object
     * @EXCEPTION IOException
     * */
    public LoadRequest decodeLoadRequest(InputStream reqBody)throws IOException,IllegalArgumentException{
        Reader reader = new InputStreamReader(reqBody);
        LoadDataHolder holder = gson.fromJson(reader, LoadDataHolder.class);//put into holder class
        //System.out.println(holder.getUsers()[0].getfName());
        if(holder.getEvents().length > 0 && holder.getPersons().length > 0 && holder.getUsers().length > 0) {
            //System.out.println(holder.getUsers()[0].getfName());
            return new LoadRequest(holder.getUsers(), holder.getPersons(), holder.getEvents());
        }
        else{
            throw new IllegalArgumentException("LoadData incomplete error");
        }
    }

    /**
     * This decodes java objects from JSON
     * @PARAM InputStream object
     * @RETURN LoadRequest object
     * @EXCEPTION IOException
     * */
    public LoadResult decodeLoadResult(InputStream input)throws IOException,IllegalArgumentException{
        Reader reader = new InputStreamReader(input);
        return gson.fromJson(reader, LoadResult.class);
    }
}
