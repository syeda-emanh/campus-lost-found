package com.eman.campuslostfound;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSessionManager {

    private static final String PREF_NAME = "CampusLostFoundPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_USER_PASSWORD = "userPassword";

    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;

    public UserSessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();

        // Default test account if non exists
        if (!pref.contains(KEY_USER_EMAIL)) {
            editor.putString(KEY_USER_NAME, "Alex Morgan");
            editor.putString(KEY_USER_EMAIL, "alex.morgan@campus.edu");
            editor.putString(KEY_USER_PASSWORD, "password123");
            editor.apply();
        }
    }

    public void registerUser(String name, String email, String password) {
        editor.putString(KEY_USER_NAME, name);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_PASSWORD, password);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    public boolean validateCredentials(String emailOrUser, String password) {
        String savedEmail = pref.getString(KEY_USER_EMAIL, "");
        String savedPassword = pref.getString(KEY_USER_PASSWORD, "");
        String savedName = pref.getString(KEY_USER_NAME, "");

        boolean matchesEmail = emailOrUser.equalsIgnoreCase(savedEmail) || emailOrUser.equalsIgnoreCase(savedName);
        return matchesEmail && password.equals(savedPassword);
    }

    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getUserName() {
        return pref.getString(KEY_USER_NAME, "Alex Morgan");
    }

    public String getUserEmail() {
        return pref.getString(KEY_USER_EMAIL, "alex.morgan@campus.edu");
    }

    public void updatePassword(String newPassword) {
        editor.putString(KEY_USER_PASSWORD, newPassword);
        editor.apply();
    }

    public void logout() {
        editor.putBoolean(KEY_IS_LOGGED_IN, false);
        editor.apply();
    }
}