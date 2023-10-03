package com.example.planer;

public class ObjectParams {
    private String date;
    private String time;
    private String title;
    private String content;
    private String category;
    private String priority;
    private String key;

    public ObjectParams(String date, String time, String title, String content, String category, String priority, String key){
        this.date = date;
        this.time = time;
        this.title = title;
        this.content = content;
        this.category = category;
        this.priority = priority;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getPriority() {
        return priority;
    }

    public String getCategory() {
        return category;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setKey(String key) {
        this.key = key;
    }
}