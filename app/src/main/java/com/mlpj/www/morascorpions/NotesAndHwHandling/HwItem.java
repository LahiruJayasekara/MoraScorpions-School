package com.mlpj.www.morascorpions.NotesAndHwHandling;

import java.io.Serializable;

/**
 * Created by DELL on 1/17/2018.
 * All Rights Reserved by MLPJ©
 */

public class HwItem implements Serializable{
    private int ternaryId;
    private String uploadedTime;
    private String deadline;
    private String topic;
    private String content;
    private String pdf;
    private String visibilityStartDate;
    private String visibilityEndDate;
    private boolean visibility;

    public HwItem(int ternaryId, String uploadedTime, String deadline, String topic, String content, String pdf, String visibilityStartDate, String visibilityEndDate, boolean visibility) {
        this.ternaryId = ternaryId;
        this.uploadedTime = uploadedTime;
        this.deadline = deadline;
        this.topic = topic;
        this.content = content;
        this.pdf = pdf;
        this.visibilityStartDate = visibilityStartDate;
        this.visibilityEndDate = visibilityEndDate;
        this.visibility = visibility;
    }

    public int getTernaryId() {
        return ternaryId;
    }

    public String getUploadedTime() {
        String[] parts = uploadedTime.split("T");
        return parts[0];
    }

    public String getDeadline() {
        String[] parts = deadline.split("T");
        return parts[0];
    }

    public String getTopic() {
        return topic;
    }

    public String getContent() {
        return content;
    }

    public String getPdf() {
        return pdf;
    }

    public String getVisibilityStartDate() {
        String[] parts = visibilityStartDate.split("T");
        return parts[0];
    }

    public String getVisibilityEndDate() {
        String[] parts = visibilityEndDate.split("T");
        return parts[0];
    }

    public boolean isVisibility() {
        return visibility;
    }
}
