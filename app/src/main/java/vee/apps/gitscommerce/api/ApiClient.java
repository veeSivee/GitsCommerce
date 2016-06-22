package vee.apps.gitscommerce.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vee.apps.gitscommerce.api.response.Detail;
import vee.apps.gitscommerce.api.response.Gits;

/**
 * Created by binaryvi on 12/05/2016.
 */
public interface ApiClient {

    @GET("/apiecommerce/public/index.php/list")
    Call<Gits> getGits();

    @GET("/apiecommerce/public/index.php/list/{id}")
    Call<Detail> getDetail(@Path("id") String ID);

}
