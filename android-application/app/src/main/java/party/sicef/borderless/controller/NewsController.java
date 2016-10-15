package party.sicef.borderless.controller;

import android.app.Activity;

import party.sicef.borderless.api.RestAPI;
import party.sicef.borderless.api.UtilsAPI;
import party.sicef.borderless.api.response.BaseResponse;
import party.sicef.borderless.api.response.NewResponse;
import party.sicef.borderless.api.response.NewsReponse;
import party.sicef.borderless.api.response.RefugeResponse;
import party.sicef.borderless.util.PreferencesManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ahuskano on 15.11.2015..
 */
public class NewsController extends DialogController {

    public NewsController(Activity activity) {
        super(activity);
    }

    protected Callback<NewsReponse> responseNews = new Callback<NewsReponse>() {

        @Override
        public void success(NewsReponse response, Response response2) {
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


    protected Callback<NewResponse> responseNew = new Callback<NewResponse>() {

        @Override
        public void success(NewResponse response, Response response2) {
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

    public void getNews(){
        showDialog();
        UtilsAPI.getRestAdapter(RestAPI.API_LOCATION).create(RestAPI.class).getNews(RestAPI.HEADER, PreferencesManager.getToken(getActivity()), responseNews);
    }


    public void getNew(int id){
        showDialog();
        UtilsAPI.getRestAdapter(RestAPI.API_LOCATION).create(RestAPI.class).getNew(RestAPI.HEADER, PreferencesManager.getToken(getActivity()), id, responseNew);
    }


}
