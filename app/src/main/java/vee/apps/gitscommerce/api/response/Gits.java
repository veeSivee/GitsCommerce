package vee.apps.gitscommerce.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by binaryvi on 12/05/2016.
 */
public class Gits {
    @SerializedName("DATA")
    private GitsData gitsdata;

    @SerializedName("STATUS")
    private boolean status;

    @SerializedName("STATUS_CODE")
    private int statusCode;

    @SerializedName("MESSAGE")
    private GitsMessage gitMessage;

    public GitsMessage getGitMessage() {
        return gitMessage;
    }

    public void setGitMessage(GitsMessage gitMessage) {
        this.gitMessage = gitMessage;
    }

    public GitsData getGitsdata() {
        return gitsdata;
    }

    public void setGitsdata(GitsData gitsdata) {
        this.gitsdata = gitsdata;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
