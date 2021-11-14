package com.sameetasadullah.i180479_180531;

import android.graphics.Bitmap;

import java.util.List;

public class message {
    private String message, time, location, key, receiverID, senderID, image;

    public message() {}

    public message(String message, String time, String location, String key, String receiverID, String senderID, String image) {
        this.message = message;
        this.time = time;
        this.location = location;
        this.key = key;
        this.receiverID = receiverID;
        this.senderID = senderID;
        this.image = image;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String ID) {
        this.key = ID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }
}