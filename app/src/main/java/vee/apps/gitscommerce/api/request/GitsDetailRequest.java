package vee.apps.gitscommerce.api.request;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vee.apps.gitscommerce.api.ApiClient;
import vee.apps.gitscommerce.api.ApiService;
import vee.apps.gitscommerce.api.response.Detail;

/**
 * Created by binaryvi on 13/05/2016.
 */
public class GitsDetailRequest {

    private ApiClient apiClient;
    private Call<Detail> request;
    private OnGitsDetailRequestListener listener;

    public GitsDetailRequest(OnGitsDetailRequestListener listener){
        apiClient = ApiService.createService(ApiClient.class);
        this.listener = listener;
    }

    public void callApi(String id){
        request = apiClient.getDetail(id);


        request.enqueue(new Callback<Detail>() {
            @Override
            public void onResponse(Call<Detail> call, Response<Detail> response) {
                if(response!=null && response.isSuccessful()){

                    Detail mDetail = response.body();
                    listener.onRequestGitsDetailSuccess(mDetail);
                }else{
                    listener.onRequestGitsDetailFailure("Response Invalid");
                }
            }

            @Override
            public void onFailure(Call<Detail> call, Throwable t) {
                String errorMessage = t.getMessage() !=null?
                        t.getMessage() : "Unable to connect to server";
                listener.onRequestGitsDetailFailure(errorMessage);
            }
        });
    }


    public void cancelApi(){
        if(request!=null){
            request.cancel();
        }
    }

    public interface OnGitsDetailRequestListener{
        void onRequestGitsDetailSuccess(Detail gitsResponse);
        void onRequestGitsDetailFailure(String message);
    }
}
