package com.example.lenovo.test;

public class Items {
    private String category;
    private String class_room_no;
    private String company;
    private String date;
    private String department;
    private String description;
    private String imageurl;
    private String lab_no;
    private String lost_thing;
    private String model;
    private String status;
    private String time;

    public Items()
    {}

    public Items(String category, String class_room_no, String company, String date, String department, String description, String imageurl, String lab_no, String lost_thing, String model, String status, String time) {
        this.category = category;
        this.class_room_no = class_room_no;
        this.company = company;
        this.date = date;
        this.department = department;
        this.description = description;
        this.imageurl = imageurl;
        this.lab_no = lab_no;
        this.lost_thing = lost_thing;
        this.model = model;
        this.status = status;
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getClass_room_no() {
        return class_room_no;
    }

    public void setClass_room_no(String class_room_no) {
        this.class_room_no = class_room_no;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getLab_no() {
        return lab_no;
    }

    public void setLab_no(String lab_no) {
        this.lab_no = lab_no;
    }

    public String getLost_thing() {
        return lost_thing;
    }

    public void setLost_thing(String lost_thing) {
        this.lost_thing = lost_thing;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
