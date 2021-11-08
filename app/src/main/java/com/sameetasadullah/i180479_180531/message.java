package com.sameetasadullah.i180479_180531;

import android.graphics.Bitmap;

import java.util.List;

public class message {
    private String message, time, location, ID, receiverName;
    private Boolean isSent;
    private Bitmap image;

    public message(String message, String time, String location, String ID, Boolean isSent, Bitmap image) {
        this.message = message;
        this.time = time;
        this.location = location;
        this.ID = ID;
        this.isSent = isSent;
        this.image = image;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public Boolean getSent() { return isSent; }

    public void setSent(Boolean sent) {
        isSent = sent;
    }

    public Bitmap getImage() { return image; }

    public void setImage(Bitmap image) { this.image = image; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }
}
