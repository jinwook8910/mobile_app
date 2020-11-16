package com.example.harujogak;

import java.sql.Time;
import java.util.ArrayList;

public class User {
    String id, passWord, eMail;
    ArrayList<TableItemByDate> schedule = new ArrayList<>();
    ArrayList<TableItemByDay> week = new ArrayList<>(7);
    //ArrayList<Goal> goal = new ArrayList<>();
    //ArrayList<Obstruction> obstruction;
}

class Task{

}