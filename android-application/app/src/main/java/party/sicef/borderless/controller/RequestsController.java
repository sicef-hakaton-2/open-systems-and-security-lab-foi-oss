package party.sicef.borderless.controller;

import android.app.Activity;

import party.sicef.borderless.api.RestAPI;
import party.sicef.borderless.api.UtilsAPI;
import party.sicef.borderless.api.response.BaseResponse;
import party.sicef.borderless.api.response.OffersResponse;
import party.sicef.borderless.util.PreferencesManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ahuskano on 15.11.2015..
 */
public class RequestsController extends DialogController {

    public RequestsController(Activity activity) {
        super(activity);
    }

    protected Callback<OffersResponse> responseCallback = new Callback<OffersResponse>() {

        @Override
        public void success(OffersResponse response, Response response2) {
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


    protected Callback<BaseResponse> callback = new Callback<BaseResponse>() {

        @Override
        public void success(BaseResponse response, Response response2) {
            dismissDialog();
        }

        @Override
        public void failure(RetrofitError error) {
            if (onDataErrorListener != null)
                onDataErrorListener.onDataErrorReceive(error);
            dismissDialog();
        }
    };


    public void getRequests() {
        showDialog();
        UtilsAPI.getRestAdapter(RestAPI.API_LOCATION).create(RestAPI.class).getRequests(RestAPI.HEADER, responseCallback);
    }


    public void getMyRequests() {
        showDialog();
        UtilsAPI.getRestAdapter(RestAPI.API_LOCATION).create(RestAPI.class).getMyRequests(RestAPI.HEADER, PreferencesManager.getToken(getActivity()), responseCallback);
    }

    public void sendRequest(int id) {
        showDialog();
        UtilsAPI.getRestAdapter(RestAPI.API_LOCATION).create(RestAPI.class).sendRequest(RestAPI.HEADER,PreferencesManager.getToken(getActivity()),id,callback);
    }
}
