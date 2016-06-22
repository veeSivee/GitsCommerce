package vee.apps.gitscommerce.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by binaryvi on 13/05/2016.
 */
public class Detail {
    @SerializedName("STATUS")
    private boolean status;

    @SerializedName("STATUS_CODE")
    private int statusCode;

    @SerializedName("MESSAGE")
    private DetailMessage message;

    @SerializedName("DATA")
    private DetailData detailData;

    public DetailData getDetailData() {
        return detailData;
    }

    public void setDetailData(DetailData detailData) {
        this.detailData = detailData;
    }


    public DetailMessage getMessage() {
        return message;
    }

    public void setMessage(DetailMessage message) {
        this.message = message;
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
