package com.project.healthapp;

public class User {
    private String full_name, username, email, phone_number, birth_date, gender;

    public User(){}

    public User(String full_name, String username, String email, String phone_number, String birth_date, String gender){
        this.full_name = full_name;
        this.username = username;
        this.email = email;
        this.phone_number = phone_number;
        this.birth_date = birth_date;
        this.gender = gender;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
