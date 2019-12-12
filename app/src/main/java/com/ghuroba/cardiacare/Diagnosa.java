package com.ghuroba.cardiacare;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class Diagnosa {
    private Date tanggal;
//    private String diabetes;
//    private String kelamin;
//    private String rokok;
//    private String usia;
//    private String tensi;
//    private String kolesterol;
//    private String hasil;

    public Diagnosa(Date tanggal) {
        this.tanggal = tanggal;
//        this.diabetes = diabetes;
//        this.kelamin = kelamin;
//        this.rokok = rokok;
//        this.usia = usia;
//        this.tensi = tensi;
//        this.kolesterol = kolesterol;
//        this.hasil = hasil;
    }

    public Diagnosa(){

    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

//    public String getDiabetes() {
//        return diabetes;
//    }
//
//    public void setDiabetes(String diabetes) {
//        this.diabetes = diabetes;
//    }
//
//    public String getKelamin() {
//        return kelamin;
//    }
//
//    public void setKelamin(String kelamin) {
//        this.kelamin = kelamin;
//    }
//
//    public String getRokok() {
//        return rokok;
//    }
//
//    public void setRokok(String rokok) {
//        this.rokok = rokok;
//    }
//
//    public String getUsia() {
//        return usia;
//    }
//
//    public void setUsia(String usia) {
//        this.usia = usia;
//    }
//
//    public String getTensi() {
//        return tensi;
//    }
//
//    public void setTensi(String tensi) {
//        this.tensi = tensi;
//    }
//
//    public String getKolesterol() {
//        return kolesterol;
//    }
//
//    public void setKolesterol(String kolesterol) {
//        this.kolesterol = kolesterol;
//    }
//
//    public String getHasil() {
//        return hasil;
//    }
//
//    public void setHasil(String hasil) {
//        this.hasil = hasil;
//    }
}
