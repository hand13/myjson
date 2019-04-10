package com.hand13.exception;
/**
 * @version $Revision$ $Date$
 * @author $Author$
*/
public class JSONException extends Exception {
    private String reason;
    public JSONException(String reason) {
        this.reason = reason;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " reason : "+this.reason;
    }
}
