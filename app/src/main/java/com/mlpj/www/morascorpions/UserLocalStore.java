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
        spEditor.putString("p_Id", user.getP_Id());
        spEditor.putString("roleName", user.getRoleName());
        spEditor.putString("name", user.getName());
        spEditor.putString("picUrl", user.getPicUrl());
        spEditor.putString("tpNumber", user.getTpNumber());
        spEditor.putString("email", user.getEmail());
        spEditor.putString("admissionNumber", user.getAdmissionNumber());
        spEditor.putString("admitionDate", user.getAdmissionDate());
        spEditor.putString("teacherGrade", user.getTeacherGrade());
        spEditor.putString("principalGrade", user.getPrincipalGrade());
        spEditor.putString("studentId", user.getStudentId());
        spEditor.putString("classRoomName", user.getClassRoomName());

        spEditor.commit();
    }

    public User getUserDetails(){
        String p_Id = userLocalDatabase.getString("p_Id","");
        String roleName = userLocalDatabase.getString("roleName","");
        String name = userLocalDatabase.getString("name","");
        String picUrl = userLocalDatabase.getString("picUrl","");
        String tpNumber = userLocalDatabase.getString("tpNumber","");
        String email = userLocalDatabase.getString("email","");
        String admissionNumber = userLocalDatabase.getString("admissionNumber","");
        String admissionDate = userLocalDatabase.getString("admitionDate","");
        String teacherGrade = userLocalDatabase.getString("teacherGrade","");
        String principalGrade = userLocalDatabase.getString("principalGrade","");
        String studentId = userLocalDatabase.getString("studentId","");
        String classRoomName = userLocalDatabase.getString("classRoomName","");

        User user =new User(p_Id, roleName, name, picUrl, tpNumber, email, admissionNumber, admissionDate, teacherGrade, principalGrade, studentId,
                classRoomName);
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
