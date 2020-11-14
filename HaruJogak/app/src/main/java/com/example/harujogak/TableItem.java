package com.example.mobileapp;

public class TableItem {
    String date;
    int resId;

    // Generate > Constructor
    public TableItem(String date, int resId) {
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

    // Generate > toString() : 아이템을 문자열로 출력

}
