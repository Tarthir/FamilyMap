package encode;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import infoObjects.FillRequest;
import models.Location;


/**
 * Created by tyler on 3/1/2017.
 * Grabs our Json data that we need for Fill requests
 */

public class JsonData {

    public JsonData() {
    }

    /**
     *Takes our Json arrays we have and then converts them to java arrays
     * @PARAM Object, a type of request
     * @RETURN The request Object with updated fields containg the arrays we setup
     * @EXCEPTION IllegalArgumentException
     * */
    public FillRequest setupJSONArrays(FillRequest request) throws IllegalArgumentException,IOException {
        String filePathStr = "C:\\Users\\tyler\\AndroidStudioProjects\\FamilyMap\\DefaultFiles\\locations.json";
        Gson gson = new Gson();
        try {
            FileReader reader = new FileReader(filePathStr);

            request.setfNames(new FemaleNamesHolder().getFnames());
            request.setlNames(new SurNamesHolder().getSnames());
            LocationDataHolder holder = gson.fromJson(reader,LocationDataHolder.class);
            request.setLocations(holder.getLocArray());
            request.setmNames(new MaleNamesHolder().getMnames());

            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return request;
    }
}
