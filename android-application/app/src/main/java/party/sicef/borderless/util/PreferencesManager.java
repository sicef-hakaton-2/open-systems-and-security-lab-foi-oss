package party.sicef.borderless.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import party.sicef.borderless.ui.fragment.SettingsFragment;

/**
 * Created by Andro on 11/15/2015.
 */
public class PreferencesManager {

    public static final String SENT_TOKEN_TO_SERVER = "gcm_sent_token";
    public static final String GCM_TOKEN = "gcm_token";
    public static final String GCM_URL = "gcm_url";
    public static final String ROLE = "role";
    public static final String TOKEN = "token";
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";

    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void gcmTokenSent(Context context, boolean sent) {
        getSharedPreferences(context).edit().putBoolean(SENT_TOKEN_TO_SERVER, sent).apply();
    }

    public static boolean isGcmTokenSent(Context context) {
        return getSharedPreferences(context).getBoolean(SENT_TOKEN_TO_SERVER, false);
    }

    public static void saveGcmToken(Context context, String token) {
        getSharedPreferences(context).edit().putString(GCM_TOKEN, token).apply();
    }

    public static String getGcmToken(Context context) {
        return getSharedPreferences(context).getString(GCM_TOKEN, null);
    }

    public static int getMaxDistance(Context context) {
        String string = getSharedPreferences(context).getString(SettingsFragment.KEY_DISTANCE, "5");
        return Integer.valueOf(string);
    }

    public static void saveGcmUrl(Context context, String url) {
        getSharedPreferences(context).edit().putString(GCM_URL, url).apply();
    }

    public static String getGcmUrl(Context context) {
        return getSharedPreferences(context).getString(GCM_URL, null);
    }

    public static void saveRole(Context context, String role) {
        getSharedPreferences(context).edit().putString(ROLE, role).apply();
    }

    public static String getRole(Context context) {
        return getSharedPreferences(context).getString(ROLE, null);
    }


    public static void saveToken(Context context, String role) {
        getSharedPreferences(context).edit().putString(TOKEN, role).apply();
    }

    public static String getToken(Context context) {
        return getSharedPreferences(context).getString(TOKEN, null);
    }

    public static boolean isCategorySet(Context context, String key) {
        return getSharedPreferences(context).getBoolean(key, false);
    }


    public static void saveEmail(Context context, String role) {
        getSharedPreferences(context).edit().putString(EMAIL, role).apply();
    }

    public static String getEmail(Context context) {
        return getSharedPreferences(context).getString(EMAIL, null);
    }


    public static void saveUsername(Context context, String role) {
        getSharedPreferences(context).edit().putString(USERNAME, role).apply();
    }

    public static String getUsername(Context context) {
        return getSharedPreferences(context).getString(USERNAME, null);
    }

}