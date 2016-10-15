package party.sicef.borderless.api;

import android.util.Log;

import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by ahuskano on 14.11.2015..
 */
public class UtilsAPI {

    public static final String RETROFIT_DEBUG = "API";

    public static RestAdapter getRestAdapter(String endpoint) {
        return new RestAdapter.Builder().setConverter(new GsonConverter(new GsonBuilder().create())).setEndpoint(endpoint).setLogLevel(RestAdapter.LogLevel.FULL).setLog(new RestAdapter.Log() {
            @Override
            public void log(String message) {
                Log.d(RETROFIT_DEBUG, message);
            }
        }).build();
    }
}
