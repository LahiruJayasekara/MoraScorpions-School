package com.mlpj.www.morascorpions.Chat;

/**
 * Created by DELL on 12/1/2017.
 * All Rights Reserved by MLPJ©
 */

public class ChatItem {
    private String name;
    //private String description;
    //add the user id
    private String userId;
    private String subjectName;
    private String image;
    private String studentName;
    private String studentId;

    public ChatItem(String name, String p_Id, String subjectName, String picUrl) {
        this.name = name;
        this.userId = p_Id;
        this.subjectName = subjectName;
        this.image = picUrl;
    }

    public ChatItem(String name, String userId, String image, String studentName, String studentId) {
        this.name = name;
        this.userId = userId;
        this.image = image;
        this.studentName = studentName;
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public String getP_Id() {
        return userId;
    }

    public String getSubjectName() {
        return subjectName + " Teacher";
    }

    public String getPicUrl() {
        return image;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentId() {
        return studentId;
    }
}
