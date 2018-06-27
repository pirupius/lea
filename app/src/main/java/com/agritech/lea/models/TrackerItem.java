package com.agritech.lea.models;

public class TrackerItem {
    private String stage, days, date, description;

    public TrackerItem() {
    }

    public TrackerItem(String stage, String days, String date, String description) {
        super();
        this.stage = stage;
        this.days = days;
        this.date = date;
        this.description = description;
    }

    public String getStage() {
        return stage;
    }
    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getDays() {
        return days;
    }
    public void setDays(String days) {
        this.days = days;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
