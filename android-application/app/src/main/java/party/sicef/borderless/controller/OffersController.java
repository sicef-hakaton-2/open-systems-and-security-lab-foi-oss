package party.sicef.borderless.controller;

import android.app.Activity;
import android.os.AsyncTask;

import party.sicef.borderless.api.RestAPI;
import party.sicef.borderless.api.UtilsAPI;
import party.sicef.borderless.api.data.OfferData;
import party.sicef.borderless.api.request.LogInRequest;
import party.sicef.borderless.api.response.BaseResponse;
import party.sicef.borderless.api.response.LogInResponse;
import party.sicef.borderless.api.response.OffersResponse;
import party.sicef.borderless.util.DBHelper;
import party.sicef.borderless.util.PreferencesManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ahuskano on 14.11.2015..
 */
public class OffersController extends DialogController {

    public OffersController(Activity activity) {
        super(activity);
    }

    protected Callback<OffersResponse> responseCallback = new Callback<OffersResponse>() {

        @Override
        public void success(OffersResponse response, Response response2) {
            if (onDataReadListener != null)
                onDataReadListener.onDataReceive(response);

         //   new SaveInDB().execute(response);
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


    public void getOffers(){
        showDialog();
         UtilsAPI.getRestAdapter(RestAPI.API_LOCATION).create(RestAPI.class).getOffers(RestAPI.HEADER, responseCallback);
    }


    public void getMyOffers(){
        showDialog();
        UtilsAPI.getRestAdapter(RestAPI.API_LOCATION).create(RestAPI.class).getMyOffers(RestAPI.HEADER, PreferencesManager.getToken(getActivity()), responseCallback);
    }

    public void sendOffer(int id){
        showDialog();
        UtilsAPI.getRestAdapter(RestAPI.API_LOCATION).create(RestAPI.class).sendOffer(RestAPI.HEADER,PreferencesManager.getToken(getActivity()),id,callback);
    }


    class SaveInDB extends AsyncTask<OffersResponse, OffersResponse, OffersResponse>{

        @Override
        protected void onPostExecute(OffersResponse aVoid) {
            super.onPostExecute(aVoid);
            if (onDataReadListener != null)
                onDataReadListener.onDataReceive(aVoid);

        }

        @Override
        protected OffersResponse doInBackground(OffersResponse... voids) {
            for(OfferData offer:voids[0].getData()){
                DBHelper.saveOffer(offer);
            }
            return voids[0];
        }
    }


}
