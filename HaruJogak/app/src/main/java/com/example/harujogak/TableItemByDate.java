package com.example.harujogak;

public class TableItemByDate {
    String date;
    int resId;

    // Generate > Constructor
    public TableItemByDate(String date, int resId) {
        this.date = date;
        this.resId = resId;
    }

    // Generate > Getter and Setter
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

}

class TableItemByDay {
    String day;
    int resId;

    // Generate > Constructor
    public TableItemByDay(String day, int resId) {
        this.day = day;
        this.resId = resId;
    }

    // Generate > Getter and Setter
    public String getDate() {
        return day;
    }

    public void setDate(String day) {
        this.day = day;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

}