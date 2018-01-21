package com.mlpj.www.morascorpions;

/**
 * Created by DELL on 12/2/2017.
 * All Rights Reserved by MLPJ©
 */

public class MessageItem {
    private String body;
    private boolean seen;
    private String senderMsgKey;
    private String sentBy;
    private long timestamp;



    public MessageItem(){}

    public MessageItem(String body, boolean seen, String senderMsgKey, String sentBy, long timestamp) {
        this.body = body;
        this.seen = seen;
        this.senderMsgKey = senderMsgKey;
        this.sentBy = sentBy;
        this.timestamp = timestamp;
    }

    public String getBody() {
        return body;
    }

    public boolean isSeen() {
        return seen;
    }

    public String getSenderMsgKey() {
        return senderMsgKey;
    }

    public String getSentBy() {
        return sentBy;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
