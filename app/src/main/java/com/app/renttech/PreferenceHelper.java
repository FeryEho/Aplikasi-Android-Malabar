package com.app.renttech;

import android.content.Context;
import android.content.SharedPreferences;


public class PreferenceHelper {

    private final String INTRO = "intro";
    private final String Nama = "Nama";
    private final String NoTelepon = "NoTelepon";
    private SharedPreferences app_prefs;
    private Context context;

    public PreferenceHelper(Context context) {
        app_prefs = context.getSharedPreferences("shared",
                Context.MODE_PRIVATE);
        this.context = context;
    }

    public void putIsLogin(boolean loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(INTRO, loginorout);
        edit.commit();
    }
    public boolean getIsLogin() {
        return app_prefs.getBoolean(INTRO, false);
    }

    public void putNama(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(Nama, loginorout);
        edit.commit();
    }
    public String getNama() {
        return app_prefs.getString(Nama, "");
    }

    public void putNoTelepon(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(NoTelepon, loginorout);
        edit.commit();
    }
    public String getNoTelepon() {
        return app_prefs.getString(NoTelepon, "");
    }

}
