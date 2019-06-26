package com.example.kotelmania;

public class Note {

    public int ID;
    public String title;
    public String content;
    public String status;
    public String date;

    public Note(int ID, String title, String content, String status, String date) {
        this.ID = ID;
        this.title = title;
        this.content = content;
        this.status = status;
        this.date = date;
    }


    public static final String TABLE_NAME = "Notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_DATE = "date";

    public int getID(){
        return ID;
    }
    public String getTitle(){
        return title;
    }
    public String getContent(){
        return content;
    }
    public String getStatus(){
        return status;
    }
    public String getDate(){
        return date;
    }
}
