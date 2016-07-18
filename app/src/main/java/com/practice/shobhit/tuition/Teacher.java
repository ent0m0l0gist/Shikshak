package com.practice.shobhit.tuition;

public class Teacher{
    public String name;
    public String email;
    public String password;
    public String address;
    public String contact;

    public Teacher(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = "";
        this.contact = "";
    }

    public Teacher(String name, String email){
        this.name = name;
        this.email = email;
        this.password = "";
        this.address = "";
        this.contact = "";
    }

    public Teacher(String name, String email, String password, String address, String contact){
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.contact = contact;

    }
}