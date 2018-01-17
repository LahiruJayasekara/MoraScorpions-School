package com.mlpj.www.morascorpions;

/**
 * Created by DELL on 12/2/2017.
 * All Rights Reserved by MLPJ©
 */

public class MessageItem {
    private String senderName;
    private String message;
    private boolean seen;
    private long time;
    private String from;

    public MessageItem(){}

    public MessageItem(String message, boolean seen, long time, String from) {
        this.message = message;
        this.seen = seen;
        this.time = time;
        this.from = from;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSeen() {
        return seen;
    }

    public long getTime() {
        return time;
    }

    public String getFrom() {
        return from;
    }
}
