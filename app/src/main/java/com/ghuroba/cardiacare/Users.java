package com.ghuroba.cardiacare;

public class Users {
    public String name, Email, key;

    public Users(){

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Users(String name, String email) {
        this.name = name;
        Email = email;
        //this.phone = phone;
    }

    public String getNama() {
        return key;
    }

    public void setNama(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return " "+name+"\n" +
                " "+Email;
    }

}