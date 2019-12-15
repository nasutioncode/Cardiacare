package com.ghuroba.cardiacare;

import java.util.Calendar;

public class ProfileModel {
    private String Nama;
    private String Email;

    public String getNama() {
        return Nama;
    }

    public String getEmail() {
        return Email;
    }

    public ProfileModel(String nama, String email) {
        Nama = nama;
        Email = email;
    }
}
