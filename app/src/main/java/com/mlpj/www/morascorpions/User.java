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

    public String name;
    public String userType;
    public String className;
    public String admitionDate;
}
