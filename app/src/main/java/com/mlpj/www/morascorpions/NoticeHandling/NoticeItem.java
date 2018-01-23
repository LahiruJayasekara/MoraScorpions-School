package com.mlpj.www.morascorpions.NoticeHandling;

/**
 * Created by DELL on 1/23/2018.
 * All Rights Reserved by MLPJ©
 */

public class NoticeItem {
    private int noticeId;
    private String topic;
    private  String content;
    private String uploadDate;
    private String lastUpdatedDate;
    private String endDate;
    private boolean studentView;
    private boolean teacherView;
    private boolean parentView;
    private boolean expire;

    public NoticeItem(int noticeId, String topic, String content, String uploadDate, String lastUpdatedDate, String endDate, boolean studentView, boolean teacherView, boolean parentView, boolean expire) {
        this.noticeId = noticeId;
        this.topic = topic;
        this.content = content;
        this.uploadDate = uploadDate;
        this.lastUpdatedDate = lastUpdatedDate;
        this.endDate = endDate;
        this.studentView = studentView;
        this.teacherView = teacherView;
        this.parentView = parentView;
        this.expire = expire;
    }

    public NoticeItem(String topic, String content, String endDate, boolean studentView, boolean teacherView, boolean parentView) {
        this.topic = topic;
        this.content = content;
        this.endDate = endDate;
        this.studentView = studentView;
        this.teacherView = teacherView;
        this.parentView = parentView;
    }

    public int getNoticeId() {
        return noticeId;
    }

    public String getTopic() {
        return topic;
    }

    public String getContent() {
        return content;
    }

    public String getUploadDate() {
        String[] parts = uploadDate.split("T");
        return parts[0];
    }

    public String getLastUpdatedDate() {
        String[] parts = lastUpdatedDate.split("T");
        return parts[0];
    }

    public String getEndDate() {
        String[] parts = endDate.split("T");
        return parts[0];
    }

    public boolean isStudentView() {
        return studentView;
    }

    public boolean isTeacherView() {
        return teacherView;
    }

    public boolean isParentView() {
        return parentView;
    }

    public boolean isExpire() {
        return expire;
    }
}
