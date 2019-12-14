package com.ghuroba.cardiacare;

import java.util.Calendar;

public class Details {
    private String diabetes;
    private String kelamin;
    private String rokok;
    private String usia;
    private String tensi;
    private String kolesterol;
    private String hasil;
    private String tingkat;
    private String saran1;
    private String saranDiabetes;
    private String saranFaktor;
    private String saranKolesterol;
    private String saranTensi;

    public String getTingkat() {
        return tingkat;
    }

    public String getSaran1() {
        return saran1;
    }

    public String getSaranDiabetes() {
        return saranDiabetes;
    }

    public String getSaranFaktor() {
        return saranFaktor;
    }

    public String getSaranKolesterol() {
        return saranKolesterol;
    }

    public String getSaranTensi() {
        return saranTensi;
    }

    public String getSaranRokok() {
        return saranRokok;
    }

    private String saranRokok;

    public String getDiabetes() {
        return diabetes;
    }

    public String getKelamin() {
        return kelamin;
    }

    public String getRokok() {
        return rokok;
    }

    public String getUsia() {
        return usia;
    }

    public String getTensi() {
        return tensi;
    }

    public String getKolesterol() {
        return kolesterol;
    }

    public String getHasil() {
        return hasil;
    }

    public Details(String rbD, String rbK, String rbR, String usia,
                    String tensi, String kolesterol, String hasill, String tingkat, String saran1, String saranDiabetes, String saranFaktor, String saranKolesterol, String saranTensi, String saranRokok) {
        this.diabetes = rbD;
        this.kelamin = rbK;
        this.rokok = rbR;
        this.usia = usia;
        this.tensi = tensi;
        this.kolesterol = kolesterol;
        this.hasil = hasill;
        this.tingkat = tingkat;
        this.saran1 = saran1;
        this.saranDiabetes = saranDiabetes;
        this.saranFaktor = saranFaktor;
        this.saranKolesterol = saranKolesterol;
        this.saranTensi = saranTensi;
        this.saranRokok = saranRokok;
    }

    public Details() {

    }
}
