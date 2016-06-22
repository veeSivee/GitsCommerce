package vee.apps.gitscommerce.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by binaryvi on 12/05/2016.
 */
public class GitsData {
    @SerializedName("filter")
    ArrayList<GitsFilter> gitsFilters = new ArrayList<>();

    @SerializedName("products")
    ArrayList<GitsProducts> gitsProductses = new ArrayList<>();

    public ArrayList<GitsFilter> getGitsFilters() {
        return gitsFilters;
    }

    public void setGitsFilters(ArrayList<GitsFilter> gitsFilters) {
        this.gitsFilters = gitsFilters;
    }

    public ArrayList<GitsProducts> getGitsProductses() {
        return gitsProductses;
    }

    public void setGitsProductses(ArrayList<GitsProducts> gitsProductses) {
        this.gitsProductses = gitsProductses;
    }
}
