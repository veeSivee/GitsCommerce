package vee.apps.gitscommerce.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by binaryvi on 12/05/2016.
 */
public class GitsFilter {
    @SerializedName("jenis")
    private String jenis;

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }
}
