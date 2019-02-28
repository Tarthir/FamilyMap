package infoObjects;

/**
 * Created by tyler on 2/13/2017.
 * Clears all data from the database
 */

public class ClearResult {
    /**The success message*/
    private String message;

    public ClearResult() {
        message = "Clear Succeeded";
    }

    public ClearResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
