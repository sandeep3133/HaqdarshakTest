package com.mudhales.haqdarshak.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {
    private String id;
    private String name;
    private String mobile;
    private String gender;
    private String age;
    private String email;
    private String password;
    private String image;


    public UserData() {
    }

    public UserData(String id, String name, String mobile, String gender, String age, String email, String password, String image) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.password = password;
        this.image = image;
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
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
