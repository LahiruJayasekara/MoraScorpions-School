package com.mlpj.www.morascorpions;

import java.io.Serializable;

/**
 * Created by DELL on 9/4/2017.
 * All Rights Reserved by MLPJ©
 */

public class User implements Serializable{
    public User(String name, String userType, String className, String admitionDate){
        this.name = name;
        this.userType = userType;
        this.className = className;
        this.admitionDate = admitionDate;
    }


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String email;
    public String password;
    public String name;
    public String userType;
    public String className;
    public String admitionDate;
    public boolean success;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public String getClassName() {
        return className;
    }

    public String getAdmitionDate() {
        return admitionDate;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getName() {
        return name;
    }
}
