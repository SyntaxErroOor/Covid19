package com.example.covid19tracker.userSession;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.covid19tracker.Authentication.LoginActivity;
import com.example.covid19tracker.Splash.SplashActivity;

import java.util.HashMap;

public class UserDataa {

    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    Context context;
    private static final String FILE_NAME="userData";
    public static final String KEY_EMAIL="user_email";
    public static final String KEY_NAME="user_name";
    public static final String KEY_ID="user_id";
    public static final String KEY_STATUS="user_status";
    public UserDataa(Context context) {
        this.context = context;
        mSharedPreferences = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        mEditor=mSharedPreferences.edit();
    }

    public void saveData(String email,String name,String id, boolean status){
        mEditor.putString(KEY_EMAIL,email);
        mEditor.putString(KEY_NAME,name);
        mEditor.putString(KEY_ID,id);
        mEditor.putBoolean(KEY_STATUS,status);
        mEditor.apply();
    }

    public  HashMap<String, String> getUserData(){
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_EMAIL,mSharedPreferences.getString(KEY_EMAIL,null));
        user.put(KEY_NAME,mSharedPreferences.getString(KEY_NAME,null));
        user.put(KEY_ID,mSharedPreferences.getString(KEY_ID,null));
        return user;
    }

    public String getName(){
        return mSharedPreferences.getString(KEY_NAME,null);
    }

    public void deleteSavedData(){
        mEditor.clear();
        mEditor.commit();
        Intent i=new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
    public boolean isLogin(){
        return mSharedPreferences.getBoolean(KEY_STATUS,false);
    }

}
