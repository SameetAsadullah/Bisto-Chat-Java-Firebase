package com.sameetasadullah.i180479_180531;

public class chat {
    private String id, name, message, time, dp, state, lastSeenTime, lastSeenDate;
    private Boolean isRead;

    public chat(String id, String name, String message, String time, Boolean isRead, String dp,
                String state, String lastSeenTime, String lastSeenDate) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.time = time;
        this.isRead = isRead;
        this.dp = dp;
        this.state = state;
        this.lastSeenTime = lastSeenTime;
        this.lastSeenDate = lastSeenDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLastSeenTime() {
        return lastSeenTime;
    }

    public void setLastSeenTime(String lastSeenTime) {
        this.lastSeenTime = lastSeenTime;
    }

    public String getLastSeenDate() {
        return lastSeenDate;
    }

    public void setLastSeenDate(String lastSeenDate) {
        this.lastSeenDate = lastSeenDate;
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

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }
}
