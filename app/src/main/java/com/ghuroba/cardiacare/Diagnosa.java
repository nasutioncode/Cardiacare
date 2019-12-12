package com.ghuroba.cardiacare;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class Diagnosa {
    private Date tanggal;
    private String diabetes;
    private String kelamin;
    private String rokok;
    private int usia;
    private int tensi;
    private String kolesterol;
    private String hasil;

    public Diagnosa(){

    }

    public Diagnosa(Date tanggal, String diabetes, String kelamin, String rokok, int usia, int tensi, String kolesterol){
        this.tanggal = tanggal;
        this.diabetes = diabetes;
        this.kelamin = kelamin;
        this.rokok = rokok;
        this.usia = usia;
        this.tensi = tensi;
        this.kolesterol = kolesterol;
    }

    public Date getTanggal(){
        return tanggal;
    }

    @Exclude
    public String getHasil(){
        return hasil;
    }
    public void setHasil(String hasil){
        this.hasil = hasil;
    }

    public String getDiabetes(){
        return diabetes;
    }
    public void setDiabetes(){
        this.diabetes = diabetes;
    }

    public String getKelamin(){
        return kelamin;
    }
    public void setKelamin(){
        this.kelamin = kelamin;
    }

    public String getRokok(){
        return rokok;
    }
    public void setRokok(){
        this.rokok = rokok;
    }

    public int getUsia(){
        return usia;
    }
    public void setUsia(int usia){
        this.usia = usia;
    }

    public int getTensi(){
        return tensi;
    }
    public void setTensi(int tensi){
        this.tensi = tensi;
    }

    public String getKolesterol(){
        return kolesterol;
    }
    public void setKolesterol(){
        this.kolesterol = kolesterol;
    }
}
