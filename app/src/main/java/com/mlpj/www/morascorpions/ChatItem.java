package com.mlpj.www.morascorpions;

/**
 * Created by DELL on 12/1/2017.
 * All Rights Reserved by MLPJ©
 */

public class ChatItem {
    private String name;
    //private String description;
    //add the user id
    private String p_Id;
    private String subjectName;
    private String picUrl;

    public ChatItem(String name, String p_Id, String subjectName, String picUrl) {
        this.name = name;
        this.p_Id = p_Id;
        this.subjectName = subjectName;
        this.picUrl = picUrl;
    }

    public ChatItem(String name, String p_Id, String picUrl) {
        this.name = name;
        this.p_Id = p_Id;
        this.picUrl = picUrl;
    }

    public String getName() {
        return name;
    }

    public String getP_Id() {
        return p_Id;
    }

    public String getSubjectName() {
        return subjectName + " Teacher";
    }

    public String getPicUrl() {
        return picUrl;
    }
}
