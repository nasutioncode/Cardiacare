package com.ghuroba.cardiacare;

public class HistorysList {
    private String Diabetes;
    private String Perokok;
    private String Gender;
    private String tDarah;

    public String getDiabetes() {
        return Diabetes;
    }

    public void setDiabetes(String diabetes) {
        Diabetes = diabetes;
    }

    public String getPerokok() {
        return Perokok;
    }

    public void setPerokok(String perokok) {
        Perokok = perokok;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String gettDarah() {
        return tDarah;
    }

    public void settDarah(String tDarah) {
        this.tDarah = tDarah;
    }

    public String getJumlah_kolesterol() {
        return jumlah_kolesterol;
    }

    public void setJumlah_kolesterol(String jumlah_kolesterol) {
        this.jumlah_kolesterol = jumlah_kolesterol;
    }

    private String jumlah_kolesterol;

    public HistorysList() {

    }

    public HistorysList(String diabetes, String perokok, String gender, String tDarah, String jumlah_kolesterol) {
        Diabetes = diabetes;
        Perokok = perokok;
        Gender = gender;
        this.tDarah = tDarah;
        this.jumlah_kolesterol = jumlah_kolesterol;
    }


}
