package com.mlpj.www.morascorpions.Comment;

/**
 * Created by DELL on 12/20/2017.
 * All Rights Reserved by MLPJ©
 */

public class CommentItem {

    private int noteId;
    private int commentId;
    private String commentedTime;
    private String content;
    private String userId;
    private String name;
    private String image;

    public CommentItem(int commentId, String commentedTime, String content, String userId, String name, String image) {
        this.commentId = commentId;
        this.commentedTime = commentedTime;
        this.content = content;
        this.userId = userId;
        this.name = name;
        this.image = image;
    }

    public CommentItem(int noteId, String content, String userId) {
        this.noteId = noteId;
        this.content = content;
        this.userId = userId;
    }

    public int getCommentId() {
        return commentId;
    }

    public String getCommentedTime() {
        String[] parts = commentedTime.split("T");
        return parts[0];
    }

    public String getContent() {
        return content;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getNoteId() {
        return noteId;
    }
}
