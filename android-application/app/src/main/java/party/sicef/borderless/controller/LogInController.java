package party.sicef.borderless.controller;

import android.app.Activity;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import party.sicef.borderless.api.RestAPI;
import party.sicef.borderless.api.UtilsAPI;
import party.sicef.borderless.api.data.Category;
import party.sicef.borderless.api.data.ProfileData;
import party.sicef.borderless.api.request.LogInRequest;
import party.sicef.borderless.api.response.LogInResponse;
import party.sicef.borderless.util.AppConstants;
import party.sicef.borderless.util.DBHelper;
import party.sicef.borderless.util.PreferencesManager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ahuskano on 14.11.2015..
 */
public class LogInController extends DialogController {

    public LogInController(Activity activity) {
        super(activity);
    }

    protected Callback<LogInResponse> responseCallback = new Callback<LogInResponse>() {

        @Override
        public void success(LogInResponse response, Response response2) {
            PreferencesManager.saveToken(getActivity(),response.getData().getToken());
            PreferencesManager.saveEmail(getActivity(),response.getData().getEmail());
            PreferencesManager.saveUsername(getActivity(),response.getData().getUsername());

            AppConstants.categories=response.getConfig().getPost_categories();
            if(response.getProfile_refugee()!=null) {
                DBHelper.saveRefuge(response.getProfile_refugee());
                PreferencesManager.saveRole(getActivity(), AppConstants.ROLE_REFUGEE);
            }else if(response.getProfile_citizen()!=null){
                DBHelper.saveCitizen(response.getProfile_citizen());
                PreferencesManager.saveRole(getActivity(), AppConstants.ROLE_CITIZEN);
            }else{
                DBHelper.saveAuthority(response.getProfile_authority());
                PreferencesManager.saveRole(getActivity(), AppConstants.ROLE_AUTHORITY);
            }
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


    public void logIn(String email, String password, String gcm){
        showDialog();
        LogInRequest request=new LogInRequest(email,password, gcm);
        UtilsAPI.getRestAdapter(RestAPI.API_LOCATION).create(RestAPI.class).logIn(request, RestAPI.HEADER, responseCallback);
    }

}
