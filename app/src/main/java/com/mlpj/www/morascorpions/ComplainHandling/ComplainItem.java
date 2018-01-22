package com.mlpj.www.morascorpions.ComplainHandling;

/**
 * Created by DELL on 1/22/2018.
 * All Rights Reserved by MLPJ©
 */

public class ComplainItem {

    private int actionId;
    private String action;
    private boolean actionTaken;
    private String actionDate;
    private int complainId;
    private String topic;
    private String content;
    private String complainDate;
    private String complaineeId;
    private String complainerId;
    private boolean anonymous;
    private boolean status;
    private String complaineeName;
    private String complainerName;
    private String satisfaction;

    public ComplainItem(int actionId, String action, boolean actionTaken, String actionDate, int complainId, String topic, String content, String complainDate, String complaineeId, String complainerId, boolean anonymous, boolean status, String complaineeName) {
        this.actionId = actionId;
        this.action = action;
        this.actionTaken = actionTaken;
        this.actionDate = actionDate;
        this.complainId = complainId;
        this.topic = topic;
        this.content = content;
        this.complainDate = complainDate;
        this.complaineeId = complaineeId;
        this.complainerId = complainerId;
        this.anonymous = anonymous;
        this.status = status;
        this.complaineeName = complaineeName;

    }

    public ComplainItem(String topic, String content, String complaineeId, String complainerId, boolean anonymous, String complaineeName) {
        this.topic = topic;
        this.content = content;
        this.complaineeId = complaineeId;
        this.complainerId = complainerId;
        this.anonymous = anonymous;
        this.complaineeName = complaineeName;
    }

    public ComplainItem(int actionId, String action, boolean actionTaken, String actionDate, int complainId, String topic, String content, String complainDate, String complaineeId, String complainerId, boolean anonymous, boolean status, String complainerName, String satisfaction) {
        this.actionId = actionId;
        this.action = action;
        this.actionTaken = actionTaken;
        this.actionDate = actionDate;
        this.complainId = complainId;
        this.topic = topic;
        this.content = content;
        this.complainDate = complainDate;
        this.complaineeId = complaineeId;
        this.complainerId = complainerId;
        this.anonymous = anonymous;
        this.status = status;
        this.complainerName = complainerName;
        this.satisfaction = satisfaction;
    }

    public ComplainItem(int actionId, String action) {
        this.actionId = actionId;
        this.action = action;
    }

    public int getActionId() {
        return actionId;
    }

    public String getAction() {
        return action;
    }

    public boolean isActionTaken() {
        return actionTaken;
    }

    public String getActionDate() {
        String[] parts = actionDate.split("T");
        return parts[0];
    }

    public int getComplainId() {
        return complainId;
    }

    public String getTopic() {
        return topic;
    }

    public String getContent() {
        return content;
    }

    public String getComplainDate() {
        String[] parts = complainDate.split("T");
        return parts[0];
    }

    public String getComplaineeId() {
        return complaineeId;
    }

    public String getComplainerId() {
        return complainerId;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public boolean isStatus() {
        return status;
    }

    public String getComplaineeName() {
        return complaineeName;
    }

    public String getComplainerName() {
        return complainerName;
    }

    public String getSatisfaction() {
        return satisfaction;
    }
}
