package com.sameetasadullah.i180479_180531;

public class contact {
    private String name, number, dp;

    public contact(String name, String number, String dp) {
        this.name = name;
        this.number = number;
        this.dp = dp;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
