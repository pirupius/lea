package com.agritech.lea.models;

public class NewsItem {

    private String id, cover, title, brief, date;

    public NewsItem() {
    }

    public NewsItem(String id, String cover, String title, String brief, String date) {
        super();
        this.id = id;
        this.cover = cover;
        this.title = title;
        this.brief = brief;
        this.date = date;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }
    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() { return brief; }
    public void setBrief(String brief) { this.brief = brief; }
}
