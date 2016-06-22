package vee.apps.gitscommerce.api.request;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vee.apps.gitscommerce.api.ApiClient;
import vee.apps.gitscommerce.api.ApiService;
import vee.apps.gitscommerce.api.response.Gits;

/**
 * Created by binaryvi on 12/05/2016.
 */
public class GitsRequest {

    private ApiClient apiClient;
    private Call<Gits> request;
    private OnGitsRequestListener listener;

    public GitsRequest(OnGitsRequestListener listener){
        apiClient = ApiService.createService(ApiClient.class);
        this.listener = listener;
    }

    public void callApi(String id){
        request = apiClient.getGits();

        /*String isi = request.request().toString();
        Log.e("INI REQUEST", "******************* sukses " + isi);*/

        request.enqueue(new Callback<Gits>() {
            @Override
            public void onResponse(Call<Gits> call, Response<Gits> response) {
                if(response!=null && response.isSuccessful()){
                    Gits mGits = response.body();
                    listener.onRequestGitsSuccess(mGits);
                    cancelApi();
                }else{
                    listener.onRequestGitsFailure("Response Invalid");
                    cancelApi();
                }
            }

            @Override
            public void onFailure(Call<Gits> call, Throwable t) {
                String errorMessage = t.getMessage() !=null?
                        t.getMessage() : "Unable to connect to server";
                listener.onRequestGitsFailure(errorMessage);
                cancelApi();
            }
        });
    }

    public void cancelApi(){
        if(request!=null){
            request.cancel();
        }
    }

    public interface OnGitsRequestListener{
        void onRequestGitsSuccess(Gits gitsResponse);
        void onRequestGitsFailure(String message);
    }
}
