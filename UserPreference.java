package com.example.rijunath.friendforever;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import org.apache.commons.lang3.builder.DiffResult;

public class UserPreference {
    public static final String KEY_USER_ALREADY_LOGIN = "user_log_in";
    public static final String KEY_USER_EMAIL = "user_email";

    public static SharedPreferences getPreferanse(Context context) {
        return context.getSharedPreferences("USERPREFERENCE", 0);
    }

    public static void setUserLogin(Context context, boolean isAlreadyLogin) {
        Editor editor = getPreferanse(context).edit();
        editor.putBoolean(KEY_USER_ALREADY_LOGIN, isAlreadyLogin);
        editor.commit();
    }

    public static boolean isUserAlreadyLogin(Context context) {
        return getPreferanse(context).getBoolean(KEY_USER_ALREADY_LOGIN, false);
    }

    public static void setUserEmail(Context context, String userEmail) {
        Editor editor = getPreferanse(context).edit();
        editor.putString(KEY_USER_EMAIL, userEmail);
        editor.commit();
    }

    public static String getUserEmail(Context context) {
        return getPreferanse(context).getString(KEY_USER_EMAIL, DiffResult.OBJECTS_SAME_STRING);
    }
}
