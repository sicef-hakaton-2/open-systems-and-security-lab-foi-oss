package party.sicef.borderless.controller;

import android.app.Activity;

import party.sicef.borderless.api.RestAPI;
import party.sicef.borderless.api.UtilsAPI;
import party.sicef.borderless.api.response.OffersResponse;
import party.sicef.borderless.api.response.RefugeResponse;
import party.sicef.borderless.api.response.RefugeesResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ahuskano on 15.11.2015..
 */
public class RefugeesController extends DialogController {

    public RefugeesController(Activity activity) {
        super(activity);
    }

    protected Callback<RefugeesResponse> responseCallback = new Callback<RefugeesResponse>() {

        @Override
        public void success(RefugeesResponse response, Response response2) {
            if (onDataReadListener != null)
                onDataReadListener.onDataReceive(response);
            dismissDialog();

        }

        @Override
        public void failure(RetrofitError error) {
            if (onDataErrorListener != null)
                onDataErrorListener.onDataErrorReceive(error);
            dismissDialog();
        }
    };


    protected Callback<RefugeResponse> responseRefugee = new Callback<RefugeResponse>() {

        @Override
        public void success(RefugeResponse response, Response response2) {
            if (onDataReadListener != null)
                onDataReadListener.onDataReceive(response);
            dismissDialog();
        }

        @Override
        public void failure(RetrofitError error) {
            if (onDataErrorListener != null)
                onDataErrorListener.onDataErrorReceive(error);
            dismissDialog();
        }
    };

    public void getRefugees(){
        showDialog();
        UtilsAPI.getRestAdapter(RestAPI.API_LOCATION).create(RestAPI.class).getRefugees(RestAPI.HEADER, responseCallback);
    }

    public void getRefugee(String id){
        showDialog();
        UtilsAPI.getRestAdapter(RestAPI.API_LOCATION).create(RestAPI.class).getRefugee(RestAPI.HEADER,id, responseRefugee);
    }



}
