package com.mlpj.www.morascorpions;

/**
 * Created by DELL on 12/1/2017.
 * All Rights Reserved by MLPJ©
 */

public class ChatItem {
    private String name;
    private String description;
    //add the user id
    private String userId;


    public ChatItem(String name, String description, String userId) {
        this.name = name;
        this.description = description;
        this.userId = userId;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {

        return description;
    }

    public String getUserId() {
        return userId;
    }
}
