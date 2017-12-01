package com.mlpj.www.morascorpions;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by DELL on 9/4/2017.
 * All Rights Reserved by MLPJ©
 */

public class UserLocalStore {
    public  final static String spName = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){
        userLocalDatabase = context.getSharedPreferences(spName,0);

    }

    public void setUserDetails(User user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putInt("id", user.id);
        spEditor.putString("name", user.name);
        spEditor.putString("userType", user.userType);
        spEditor.putString("className", user.className);
        spEditor.putString("admitionDate", user.admitionDate);
        spEditor.commit();
    }

    public User getUserDetails(){
        int userId = userLocalDatabase.getInt("id",-1);
        String name = userLocalDatabase.getString("name","");
        String userType = userLocalDatabase.getString("userType","");
        String className = userLocalDatabase.getString("className","");
        String admitionDate = userLocalDatabase.getString("admitionDate","");
        User user =new User(userId, name, userType, className, admitionDate);
        return  user;
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn(){
        if(userLocalDatabase.getBoolean("loggedIn", false))
            return true;
        else
            return false;
    }

    public void clearUser(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        
    }
}
