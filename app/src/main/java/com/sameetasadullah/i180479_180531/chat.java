package com.sameetasadullah.i180479_180531;

public class chat {
    private String id, name, message, time, dp;
    private Boolean isOnline, isRead;

    public chat(String id, String name, String message, String time, Boolean isOnline, Boolean isRead, String dp) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.time = time;
        this.isOnline = isOnline;
        this.isRead = isRead;
        this.dp = dp;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }
}
