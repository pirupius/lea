package com.agritech.lea.models;

public class AgroExpertItem {

    private String id, name, location, chemicals, phone;

    public AgroExpertItem() {
    }

    public AgroExpertItem(String id, String name, String location, String chemicals, String phone) {
        super();
        this.id = id;
        this.name = name;
        this.location = location;
        this.chemicals = chemicals;
        this.phone = phone;
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
    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getChemicals() { return chemicals; }
    public void setChemicals(String chemicals) { this.chemicals = chemicals; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
