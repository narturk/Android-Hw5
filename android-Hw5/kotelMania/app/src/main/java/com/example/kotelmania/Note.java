package com.example.kotelmania;

public class Note {

    public String id, title, content, status, date;

    public Note(){}

    public Note(String id, String title, String content, String status, String date){
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.date = date;
    }

    @Override
    public String toString(){
        return "id='"+id+'\''+
                ", title='"+title+'\''+
                ", content='"+content+'\''+
                ", status='"+status+'\''+
                ", date='"+date+'\'';
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
