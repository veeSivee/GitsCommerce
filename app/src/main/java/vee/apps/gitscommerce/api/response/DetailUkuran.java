package vee.apps.gitscommerce.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by binaryvi on 13/05/2016.
 */
public class DetailUkuran {
    @SerializedName("available")
    private String ukuranAvailable;

    public String getUkuranAvailable() {
        return ukuranAvailable;
    }

    public void setUkuranAvailable(String ukuranAvailable) {
        this.ukuranAvailable = ukuranAvailable;
    }
}
