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
    private String tingkat;
    private String saran1;
    private String saranDiabetes;
    private String saranFaktor;
    private String saranKolesterol;
    private String saranTensi;
    private String saranRokok;

    public Diagnosa(){

    }

    public String getSaran1() {
        return saran1;
    }

    public void setSaran1(String saran1) {
        this.saran1 = saran1;
    }

    public String getSaranDiabetes() {
        return saranDiabetes;
    }

    public void setSaranDiabetes(String saranDiabetes) {
        this.saranDiabetes = saranDiabetes;
    }

    public String getSaranFaktor() {
        return saranFaktor;
    }

    public void setSaranFaktor(String saranFaktor) {
        this.saranFaktor = saranFaktor;
    }

    public String getSaranKolesterol() {
        return saranKolesterol;
    }

    public void setSaranKolesterol(String saranKolesterol) {
        this.saranKolesterol = saranKolesterol;
    }

    public String getSaranTensi() {
        return saranTensi;
    }

    public void setSaranTensi(String saranTensi) {
        this.saranTensi = saranTensi;
    }

    public String getSaranRokok() {
        return saranRokok;
    }

    public void setSaranRokok(String saranRokok) {
        this.saranRokok = saranRokok;
    }

    public Diagnosa(Calendar tanggal, String rbD, String rbK, String rbR, String usia,
                    String tensi, String kolesterol, String hasill, String tingkat, String saran1, String saranDiabetes, String saranFaktor, String saranKolesterol, String saranTensi, String saranRokok) {
        this.tanggal = tanggal;
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
        this.tingkat = tingkat;
    }

    public String getTingkat() {

        return tingkat;
    }

    public void setTingkat(String tingkat) {
        this.tingkat = tingkat;
    }
}
