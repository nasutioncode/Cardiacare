package com.ghuroba.cardiacare;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Calendar;

@IgnoreExtraProperties
public class Diagnosa {
    private Calendar tanggal;
    private String diabetes;
    private String kelamin;
    private String rokok;
    private String usia;
    private String tensi;
    private String kolesterol;
    private String hasil;

    public Diagnosa(){

    }

    public Diagnosa(Calendar tanggal, String rbD, String rbK, String rbR, String usia,
                    String tensi, String kolesterol) {
        this.tanggal = tanggal;
        this.diabetes = rbD;
        this.kelamin = rbK;
        this.rokok = rbR;
        this.usia = usia;
        this.tensi = tensi;
        this.kolesterol = kolesterol;
    }

    public Calendar getTanggal() {
        return tanggal;
    }

    public void setTanggal(Calendar tanggal) {

        this.tanggal = tanggal;
    }

    public String getDiabetes() {

        return diabetes;
    }

    public void setDiabetes(String diabetes) {

        this.diabetes = diabetes;
    }

    public String getKelamin() {

        return kelamin;
    }

    public void setKelamin(String kelamin) {

        this.kelamin = kelamin;
    }

    public String getRokok() {

        return rokok;
    }

    public void setRokok(String rokok)
    {
        this.rokok = rokok;
    }

    public String getUsia() {

        return usia;
    }

    public void setUsia(String usia) {

        this.usia = usia;
    }

    public String getTensi() {

        return tensi;
    }

    public void setTensi(String tensi) {
        this.tensi = tensi;
    }

    public String getKolesterol() {

        return kolesterol;
    }

    public void setKolesterol(String kolesterol)
    {
        this.kolesterol = kolesterol;
    }

    public String getHasil() {

        return hasil;
    }

    public void setHasil(String hasil) {
        this.hasil = hasil;
    }
}
