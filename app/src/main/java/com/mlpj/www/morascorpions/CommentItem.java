package com.mlpj.www.morascorpions;

/**
 * Created by DELL on 12/20/2017.
 * All Rights Reserved by MLPJ©
 */

public class CommentItem {

    private int id;
    private String url;
    private String commentedPersonName;
    private String comment;
    private String commentedTime;


    public CommentItem(String url, String commentedPersonName, String comment, String commentedTime) {
        this.url = url;
        this.commentedPersonName = commentedPersonName;
        this.comment = comment;
        this.commentedTime = commentedTime;
    }

    public String getUrl() {
        return url;
    }

    public String getCommentedPersonName() {
        return commentedPersonName;
    }

    public String getComment() {
        return comment;
    }

    public String getCommentedTime() {
        return commentedTime;
    }


}
