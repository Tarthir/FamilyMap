package service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by tyler on 3/6/2017.
 * Serves our index handler
 */

public class IndexService {
    /**
     * Sends off the requested webpages
     * @PARAM OutputStream, the Httpxchange response body
     * @PARAM String[], the parameters given to us by the IndexHandler
     * */
    public void sendRequested(OutputStream respBody, String[] params) throws IOException {
        if(params == null){//if we didnt even get parameters because of a bad request given
            sendError(respBody);
        }
        else if(params.length > 1) {
            if(params[2].equals("main.css")){
                sendOff(respBody,params[2]);
            }
            else if(new File(params[1]).exists()){
                sendOff(respBody,params[1]);
            }
            else{
                sendError(respBody);
            }
        }
        else{
            sendOff(respBody,"index.html");
        }
    }
    /**
     * Sends off the 404 ERROR
     * @PARAM OutputSream
     * @RETURN void
     * */
    private void sendError(OutputStream respBody) throws IOException {
        String filePathStr = "C:\\Users\\tyler\\AndroidStudioProjects\\FamilyMap\\DefaultFiles\\404.html";
        Path filePath = FileSystems.getDefault().getPath(filePathStr);
        Files.copy(filePath, respBody);
        respBody.close();
    }

    /**
     * Sends off website page
     * @PARAM OutputSream
     * @RETURN void
     * */
    private void sendOff(OutputStream respBody,String file) throws IOException {
        String filePathStr = "C:\\Users\\tyler\\AndroidStudioProjects\\FamilyMap\\DefaultFiles\\" + file;
        Path filePath = FileSystems.getDefault().getPath(filePathStr);
        Files.copy(filePath, respBody);

        respBody.close();//right?
    }


}
