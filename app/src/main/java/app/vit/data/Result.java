package app.vit.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kishorenarendran on 28/04/15.
 */
public class Result {
    @SerializedName("message")
    private String message;
    @SerializedName("code")
    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
