package com.ghuroba.cardiacare;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CreateDiagnosaActivity extends AppCompatActivity {


    private static final String TAG = "CreateDianosaActivity";


    String indexSatu, indexDua, indexTiga, indexEmpat, indexLima;


    int indexSpiner;
    String ApaDiabetes = "";
    String ApaKelamin = "";
    String ApaRokok = "";
    String simpanDate;
    int usiaFix;
    int tensiFix;


    TextView tvDate;
    RadioGroup rgDiabetes, rgKelamin, rgRokok;
    RadioButton rbDiabetes, rbKelamin, rbRokok;
    EditText etUsia, etTensi;
    Spinner spinKolesterol;
    Button btHasil;
    String hasil , usia, tensi, kolesterol, tingkat , saranDiabetes = "", saranRokok = "", saranTensi = "", saranKolesterol ="", saranFaktor = "", saran1 = "";
    Calendar tanggal = Calendar.getInstance();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore FSdatabase = FirebaseFirestore.getInstance();
    private CollectionReference userIdRef = FSdatabase.collection("UserInfo");
    private String userID;
    TextView BacaData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_diagnosa);

        tvDate = (TextView) findViewById(R.id.date_formJantung);
        rgDiabetes = findViewById(R.id.radio_diabetes);
        rgKelamin = findViewById(R.id.radio_kelamin);
        rgRokok = findViewById(R.id.radio_rokok);
        etUsia = (EditText) findViewById(R.id.text_usia);
        etTensi = (EditText) findViewById(R.id.text_tensi);
        spinKolesterol = (Spinner) findViewById(R.id.spinner_kolesterol);
        btHasil = (Button) findViewById(R.id.button_hasil);

        BacaData = (TextView) findViewById(R.id.texthasil);


        String CurrentDate = DateFormat.getDateInstance(DateFormat.FULL).format(tanggal.getTime());
        tvDate.setText(CurrentDate);

        final Spinner kolesterols = spinKolesterol;

        List<String> categories = new ArrayList<>();
        categories.add(0, "4 = 15444 mg/dL");
        categories.add(1, "5 = 19305 mg/dL");
        categories.add(2, "6 = 23166 mg/dL");
        categories.add(3, "7 = 27027 mg/dL");
        categories.add(4, "8 = 30888 mg/dL");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateDiagnosaActivity.this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kolesterols.setAdapter(adapter);

        spinKolesterol.setAdapter(adapter);

        spinKolesterol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                indexSpiner = spinKolesterol.getSelectedItemPosition() + 1;

                Toast.makeText(CreateDiagnosaActivity.this,  "Item Position is = " + indexSpiner + " ", Toast.LENGTH_SHORT ).show();

                if (indexSpiner == 1) {
                    indexSatu = "4 = 15444 mg/dL";
                }else if (indexSpiner == 2) {
                    indexDua = "5 = 19305 mg/dL";
                }else if (indexSpiner == 3) {
                    indexTiga = "6 = 23166 mg/dL";
                }else if (indexSpiner == 4) {
                    indexEmpat = "7 = 27027 mg/dL";
                }else if (indexSpiner == 5) {
                    indexLima = "8 = 30888 mg/dL";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        kolesterol.setAdapter(adapter);


        btHasil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar tanggal = Calendar.getInstance();

                userID = mAuth.getCurrentUser().getUid();

                int rgD = rgDiabetes.getCheckedRadioButtonId();
                rbDiabetes = findViewById(rgD);
                int rbD = Integer.parseInt(rbDiabetes.getText().toString());

                if (rbD == 1) {

                    ApaDiabetes = "Iya";
                }

                if (rbD == 2) {
                    ApaDiabetes = "Tidak";
                }

                int rgK = rgKelamin.getCheckedRadioButtonId();
                rbKelamin = findViewById(rgK);
                int rbK = Integer.parseInt(rbKelamin.getText().toString());

                if (rbK == 1) {
                    ApaKelamin = "Pria";
                }

                if (rbK == 2) {
                    ApaKelamin = "Wanita";
                }

                int rgR = rgRokok.getCheckedRadioButtonId();
                rbRokok = findViewById(rgR);
                int rbR = Integer.parseInt(rbRokok.getText().toString());

                if (rbR == 1) {

                    ApaRokok = "Iya";
                }

                if (rbR == 2) {
                    ApaRokok = "Tidak";
                }

                usia = etUsia.getText().toString();
                usiaFix = Integer.parseInt(usia);
                tensi = etTensi.getText().toString();
                tensiFix = Integer.parseInt(tensi);

                if (usia.isEmpty()) {
                    etUsia.setError("Tolong masukkan usia anda");
                    etUsia.requestFocus();
                }
                if (tensi.isEmpty()) {
                    etTensi.setError("Tolong masukkan tekanan darah anda");
                    etTensi.requestFocus();
                }

                Calendar klasifikasiBulan = Calendar.getInstance();
                String CurrentDate = DateFormat.getDateInstance(DateFormat.FULL).format(klasifikasiBulan.getTime());
                simpanDate = CurrentDate;


                kolesterol = spinKolesterol.getSelectedItem().toString();
//                int kolestrolFix = Integer.parseInt(kolesterol);


                //:::::::::::::::::::::::::::::::::::::::; NON - DIABETES && NON - PEROKOK && PRIA  ;:::::::::::::::::::::::::::::::::::::::::::::::
                //PERUBAHAN PADA KOLESTEROL

                //============== 1 - 5
                if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 0 && tensiFix <=139 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Lanjutkan pola hidup sehatmu";
                    saranTensi = "Lanjutkan pola hidup sehatmu";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 0 && tensiFix <=139 && kolesterol == indexDua){
                    hasil = "< 10 %";
                    tingkat = " Aman";
                    saran1 = "Lanjutkan pola hidup sehatmu";
                    saranTensi = "Lanjutkan pola hidup sehatmu";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 0 && tensiFix <=139 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Kolesterol). Untuk hidup yang lebih sehat";
                    saranTensi = "Lanjutkan pola hidup sehatmu";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 0 && tensiFix <=139 && kolesterol == indexEmpat){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Kolesterol). Untuk hidup yang lebih sehat";
                    saranTensi = "Lanjutkan pola hidup sehatmu";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 0 && tensiFix <=139 && kolesterol == indexLima){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Kolesterol). Untuk hidup yang lebih sehat";
                    saranTensi = "Lanjutkan pola hidup sehatmu";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu";
                }

                // PERUBAHAN PADA TEKANAN DARAH && KOLESTEROL
                //============== 6 - 10
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Lanjutkan pola hidup sehatmu";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Ringan";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexDua){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Lanjutkan pola hidup sehatmu";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Ringan";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Kolesterol). Untuk hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Ringan";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexEmpat){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Kolesterol). Untuk hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Ringan";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexLima){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                    saran1 = "Coba turunkan faktor pencetusmu (Kolesterol). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Ringan";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu";
                }

                //PERUBAHAN PADA TEKANAN DARAH && KOLESTEROL 2
                //=================== 11 - 15
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Tekanan darah). Untuk hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Sedang";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexDua){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Tekanan darah). Untuk hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Sedang";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Sedang";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexEmpat){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Sedang";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexLima){
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                    saran1 = "Coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Sedang";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu";
                }

                //PERUBAHAN PADA TEKANAN DARAH
                //================= 16 - 20
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49 && tensiFix > 180 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Tekanan darah). Untuk hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Berat";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49 && tensiFix > 180  && kolesterol == indexDua){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                    saran1 = "Coba turunkan faktor pencetusmu (Tekanan darah). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Berat";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49 && tensiFix > 180 && kolesterol == indexTiga){
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                    saran1 = "Coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Berat";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49 && tensiFix > 180 && kolesterol == indexEmpat){
                    hasil = "30 % to < 40 %";
                    tingkat = "Lumayan Bahaya";
                    saran1 = "Lakukan konsultasi ke dokter & Turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Berat";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49 && tensiFix > 180 && kolesterol == indexLima){
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                    saran1 = "Lakukan konsultasi ke dokter & Turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Berat";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu";
                }

                //PERUBAHAN PADA USIA POLA YANG SAMA 10:10===================================================================================
                //================= 21 - 25
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Lanjutkan pola hidup sehatmu";
                    saranTensi = "Lanjutkan pola hidup sehatmu";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexDua){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Lanjutkan pola hidup sehatmu";
                    saranTensi = "Lanjutkan pola hidup sehatmu";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Kolesterol). Untuk hidup yang lebih sehat";
                    saranTensi = "Lanjutkan pola hidup sehatmu";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexEmpat){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Kolesterol). Untuk hidup yang lebih sehat";
                    saranTensi = "Lanjutkan pola hidup sehatmu";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexLima){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Kolesterol). Untuk hidup yang lebih sehat";
                    saranTensi = "Lanjutkan pola hidup sehatmu";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu";
                }

                //============== 26 - 30
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Lanjutkan pola hidup sehatmu";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Ringan";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexDua){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Lanjutkan pola hidup sehatmu";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Ringan";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Kolesterol). Untuk hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Ringan";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexEmpat){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Kolesterol). Untuk hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Ringan";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexLima){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                    saran1 = "Coba turunkan faktor pencetusmu (Kolesterol). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Ringan";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu";
                }

                //================ 31 - 35
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59   && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Tekanan darah). Untuk hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Sedang";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59   && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexDua){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Tekanan darah). Untuk hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Sedang";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Tekanan darah). Untuk hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Sedang";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexEmpat){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                    saran1 = "Coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Sedang";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";

                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59   && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexLima){
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                    saran1 = "Coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Sedang";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }

                //=============== 36 - 40
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 &&  usiaFix <=59  && tensiFix > 180 && kolesterol == indexSatu){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                    saran1 = "Coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Berat";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 &&  usiaFix <=59  && tensiFix > 180 && kolesterol == indexDua){
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                    saran1 = "Coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Berat";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 &&  usiaFix <=59  && tensiFix > 180 && kolesterol == indexTiga){
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                    saran1 = "Coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Berat";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 &&  usiaFix <=59  && tensiFix > 180 && kolesterol == indexEmpat){
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                    saran1 = "Lakukan konsultasi ke Dokter & coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Berat";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 &&  usiaFix <=59  && tensiFix > 180 && kolesterol == indexLima){
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                    saran1 = "Lakukan konsultasi ke Dokter & Coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Berat";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }

                //PERUBAHAN PADA USIA ** LAGI ** DENGAN POLA YANG SAMA 10:10=========== K E D U A=============================================================================
                //========== 41 - 45
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Lanjutkan pola hidup sehatmu";
                    saranTensi = "Lanjutkan pola hidup sehatmu";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexDua){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Lanjutkan pola hidup sehatmu";
                    saranTensi = "Lanjutkan pola hidup sehatmu";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Kolesterol). Untuk hidup yang lebih sehat";
                    saranTensi = "Lanjutkan pola hidup sehatmu";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexEmpat){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Kolesterol). Untuk hidup yang lebih sehat";
                    saranTensi = "Lanjutkan pola hidup sehatmu";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexLima){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Kolesterol). Untuk hidup yang lebih sehat";
                    saranTensi = "Lanjutkan pola hidup sehatmu";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu";
                }

                //========== 46 - 50
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Lanjutkan pola hidup sehatmu";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Ringan";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexDua){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Lanjutkan pola hidup sehatmu";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Ringan";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Kolesterol). Untuk hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Ringan";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexEmpat){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                    saran1 = "Coba turunkan faktor pencetusmu (Kolesterol). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Ringan";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexLima){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Kolesterol). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Ringan";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }

                //============== 51 - 55
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69   && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Tekanan darah). Untuk hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Sedang";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69   && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexDua){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                    saran1 = "Coba turunkan faktor pencetusmu (Tekanan darah). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Sedang";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexTiga){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                    saran1 = "Coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Sedang";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexEmpat){
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                    saran1 = "Coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Sedang";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69   && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexLima){
                    hasil = "30 % to < 40 %";
                    tingkat = "Lumayan Bahaya";
                    saran1 = "Lakukan konsultasi ke Dokter & coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Sedang";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }

                //============== 56 - 60
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 &&  usiaFix <=69  && tensiFix > 180 && kolesterol == indexSatu){
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                    saran1 = "Coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Berat";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 &&  usiaFix <=69  && tensiFix > 180 && kolesterol == indexDua){
                    hasil = "30 % to < 40 %";
                    tingkat = "Lumayan Bahaya";
                    saran1 = "Lakukan konsultasi ke Dokter & coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Berat";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 &&  usiaFix <=69  && tensiFix > 180 && kolesterol == indexTiga){
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                    saran1 = "Lakukan konsultasi ke Dokter & coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Berat";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 &&  usiaFix <=69  && tensiFix > 180 && kolesterol == indexEmpat){
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                    saran1 = "Lakukan konsultasi ke Dokter & coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Berat";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 &&  usiaFix <=69  && tensiFix > 180 && kolesterol == indexLima){
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                    saran1 = "Lakukan konsultasi ke Dokter & coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Berat";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }

                //PERUBAHAN PADA USIA ** LAGI ** DENGAN POLA YANG SAMA 10:10=========== K E T I G A =============================================================================
                //=========== 61 - 65
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Lanjutkan pola hidup sehatmu";
                    saranTensi = "Lanjutkan pola hidup sehatmu";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexDua){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Lanjutkan pola hidup sehatmu";
                    saranTensi = "Lanjutkan pola hidup sehatmu";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Kolesterol). Untuk hidup yang lebih sehat";
                    saranTensi = "Lanjutkan pola hidup sehatmu";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexEmpat){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                    saran1 = "Coba turunkan faktor pencetusmu (Kolesterol). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Lanjutkan pola hidup sehatmu";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexLima){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                    saran1 = "Coba turunkan faktor pencetusmu (Kolesterol). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Lanjutkan pola hidup sehatmu";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu";
                }

                //========= 66 - 70
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Aman, tetapi coba turunkan faktor pencetusmu (Tekanan darah). Untuk hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Ringan";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu ";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexDua){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                    saran1 = "Coba turunkan faktor pencetusmu (Tekanan darah).Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Ringan";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu ";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexTiga){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                    saran1 = "Coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Ringan";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexEmpat){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                    saran1 = "Coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Ringan";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexLima){
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                    saran1 = "Coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Ringan";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }

                //================ 71 - 75
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexSatu){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                    saran1 = "Coba turunkan faktor pencetusmu (Tekanan darah). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Sedang";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu ";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexDua){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                    saran1 = "Coba turunkan faktor pencetusmu (Tekanan darah). Untuk menurunkan tingkat risiko dan hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Ringan";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu ";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexTiga){
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                    saran1 = "Coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk menurunkan tingkat risiko hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Sedang";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexEmpat){
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                    saran1 = "Coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk menurunkan tingkat risiko hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Sedang";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexLima){
                    hasil = "30 % to < 40 %";
                    tingkat = "Lumayan Bahaya";
                    saran1 = "Lakukan konsultasi ke Dokter & Coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk menurunkan tingkat risiko hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Sedang";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }

                //============= 76 - 80
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexSatu){
                    hasil = "30 % to < 40 %";
                    tingkat = "Lumayan Bahaya";
                    saran1 = "Lakukan konsultasi ke Dokter & Coba turunkan faktor pencetusmu (Tekanan darah). Untuk menurunkan tingkat risiko hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Sedang";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 180 && kolesterol == indexDua){
                    hasil = "30 % to < 40 %";
                    tingkat = "Lumayan Bahaya";
                    saran1 = "Lakukan konsultasi ke Dokter & Coba turunkan faktor pencetusmu (Tekanan darah). Untuk menurunkan tingkat risiko hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Sedang";
                    saranKolesterol = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexTiga){
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                    saran1 = "Lakukan konsultasi ke Dokter & Coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk menurunkan tingkat risiko hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Sedang";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexEmpat){
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                    saran1 = "Lakukan konsultasi ke Dokter & Coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk menurunkan tingkat risiko hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Sedang";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexLima){
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                    saran1 = "Lakukan konsultasi ke Dokter & Coba turunkan faktor pencetusmu (Tekanan darah dan Kolesterol). Untuk menurunkan tingkat risiko hidup yang lebih sehat";
                    saranTensi = "Tekanan darahmu masuk ke kategori Hipertensi Sedang";
                    saranKolesterol = "cobalah turunkan tingkat kolesterolmu ";
                }

                //####################### T A B L E  !NON - D I A B E T E S  && !NON - P E R O K O K && PRIA ||| DONE #######################################################33
                //####################### T A B L E  !NON - D I A B E T E S  && !NON - P E R O K O K && PRIA ||| DONE #######################################################33

                //:::::::::::::::::::::::::::::::::::::::; NON-DIABETES && PEROKOK && PRIA  ;::::::::::::::::::::::::::::::::::::::::::::::::://

                //==========================> 1 - 5
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexDua){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexEmpat){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexLima){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }

                //==========================> 6 - 10
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexDua) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexTiga) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexEmpat) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexLima) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }

                //=========================> 11 - 15
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexDua) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexTiga) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexEmpat) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexLima) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }

                //========================== 16 - 20
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 180  && kolesterol == indexSatu) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 180  && kolesterol == indexDua) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 180  && kolesterol == indexTiga) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 180  && kolesterol == indexEmpat) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 180  && kolesterol == indexLima) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }

                //========================= 21 - 25
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 0 && tensiFix <= 139  && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexDua) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexTiga) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexEmpat) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexLima) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }

                //====================== 26 - 30
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexDua) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexTiga) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexEmpat) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexLima) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }

                //====================== 31 - 35
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexDua) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexTiga) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexEmpat) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexLima) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }

                //======================= 36 - 40
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 180 && kolesterol == indexSatu) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 180 && kolesterol == indexDua) {
                    hasil = "30 % to < 40 %";
                    tingkat = "Lumayan Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 180 && kolesterol == indexTiga) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 180 && kolesterol == indexEmpat) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 180 && kolesterol == indexLima) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }

                //======================= 41 - 45
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexDua) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexTiga) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexEmpat) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexLima) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }

                //====================== 46 - 50
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexDua) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexTiga) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexEmpat) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexLima) {
                    hasil = "30 % to < 40 %";
                    tingkat = "Lumayan Bahaya";
                }

                //====================== 51 - 55
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexSatu) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexDua) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexTiga) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexEmpat) {
                    hasil = "30 % to < 40 %";
                    tingkat = "Lumayan Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexLima) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }

                //===================== 56 - 60
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 180 && kolesterol == indexSatu) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 180 && kolesterol == indexDua) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 180 && kolesterol == indexTiga) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 180 && kolesterol == indexEmpat) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 180 && kolesterol == indexLima) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }

                //=================== 61 - 65
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexDua) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexTiga) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexEmpat) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexLima) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }

                //==================== 66 - 70
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexSatu) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexDua) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexTiga) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexEmpat) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexLima) {
                    hasil = "30 % to < 40 %";
                    tingkat = "Lumayan Bahaya";
                }

                //===================== 71 - 75
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexSatu) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexDua) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexTiga) {
                    hasil = "30 % to < 40 %";
                    tingkat = "Lumayan Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexEmpat) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexLima) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }

                //================= 76 - 80
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexSatu) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexDua) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexTiga) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexEmpat) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexLima) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }

                //####################### T A B L E  !NON - D I A B E T E S  && P E R O K O K && PRIA ||| DONE #######################################################33
                //####################### T A B L E  !NON - D I A B E T E S  && P E R O K O K && PRIA ||| DONE #######################################################33

                //:::::::::::::::::::::::::::::::::::::::; NON-DIABETES && NON - PEROKOK && WANITA  ;::::::::::::::::::::::::::::::::::::::::::::::::://

                //============ 1 - 5
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 0 && tensiFix <=139 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 0 && tensiFix <=139 && kolesterol == indexDua){
                    hasil = "< 10 %";
                    tingkat = " Aman";
                    saran1 = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 0 && tensiFix <=139 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 0 && tensiFix <=139 && kolesterol == indexEmpat){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 0 && tensiFix <=139 && kolesterol == indexLima){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }

                //================= 6 - 10
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexDua){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexEmpat){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexLima){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }

                //==================== 11 - 15
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexDua){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexEmpat){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexLima){
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }

                //====================== 16 - 20
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49 && tensiFix > 180 && kolesterol == indexSatu){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49 && tensiFix > 180  && kolesterol == indexDua){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49 && tensiFix > 180 && kolesterol == indexTiga){
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49 && tensiFix > 180 && kolesterol == indexEmpat){
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49 && tensiFix > 180 && kolesterol == indexLima){
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }

                //======================= 21 - 25
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexDua){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                    saran1 = "Lanjutkan pola hidup sehatmu";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexEmpat){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexLima){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }

                //====================== 26 - 30
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexDua){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexEmpat){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexLima){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }

                //======================= 31 - 35
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59   && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59   && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexDua){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexEmpat){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59   && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexLima){
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }

                //======================= 36 - 40
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 &&  usiaFix <=59  && tensiFix > 180 && kolesterol == indexSatu){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 &&  usiaFix <=59  && tensiFix > 180 && kolesterol == indexDua){
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 &&  usiaFix <=59  && tensiFix > 180 && kolesterol == indexTiga){
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 &&  usiaFix <=59  && tensiFix > 180 && kolesterol == indexEmpat){
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 &&  usiaFix <=59  && tensiFix > 180 && kolesterol == indexLima){
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }


                // ================= 41 - 45
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexDua){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexEmpat){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexLima){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }

                //=================== 46 - 50
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexDua){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexEmpat){
                    hasil = "10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexLima){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }

                //================== 51 - 55
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69   && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69   && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexDua){
                    hasil = "10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexTiga){
                    hasil = "10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexEmpat){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69   && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexLima){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }

                //================== 56 - 60
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 &&  usiaFix <=69  && tensiFix > 180 && kolesterol == indexSatu){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 &&  usiaFix <=69  && tensiFix > 180 && kolesterol == indexDua){
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 &&  usiaFix <=69  && tensiFix > 180 && kolesterol == indexTiga){
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 &&  usiaFix <=69  && tensiFix > 180 && kolesterol == indexEmpat){
                    hasil = "30 % to < 40 %";
                    tingkat = "Lumayan Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 &&  usiaFix <=69  && tensiFix > 180 && kolesterol == indexLima){
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }

                //==================== 61 - 65
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexDua){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexEmpat){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexLima){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }

                //======================= 66 - 70
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexDua){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexEmpat){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexLima){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }

                //====================== 71 - 75
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexDua){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexTiga){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexEmpat){
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexLima){
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }

                //================ 76 - 80
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexSatu){
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 180 && kolesterol == indexDua){
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexTiga){
                    hasil = "30 % to < 40 %";
                    tingkat = "Lumayan Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexEmpat){
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexLima){
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }

                //####################### T A B L E  !NON - D I A B E T E S  && !NON - P E R O K O K && WANITA ||| DONE #######################################################33
                //####################### T A B L E  !NON - D I A B E T E S  && !NON - P E R O K O K && WANITA ||| DONE #######################################################33

                //:::::::::::::::::::::::::::::::::::::::; NON-DIABETES && PEROKOK && WANITA  ;::::::::::::::::::::::::::::::::::::::::::::::::://

                //==========================> 1 - 5
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexDua){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexEmpat){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexLima){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }

                //==========================> 6 - 10
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexDua) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexTiga) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexEmpat) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexLima) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }

                //=========================> 11 - 15
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexDua) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexTiga) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexEmpat) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexLima) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }

                //========================== 16 - 20
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 180  && kolesterol == indexSatu) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 180  && kolesterol == indexDua) {
                    hasil = "30 % to < 40 %";
                    tingkat = "Lumayan Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 180  && kolesterol == indexTiga) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 180  && kolesterol == indexEmpat) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 180  && kolesterol == indexLima) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }

                //========================= 21 - 25
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 0 && tensiFix <= 139  && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexDua) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexTiga) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexEmpat) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexLima) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }

                //====================== 26 - 30
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexDua) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexTiga) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexEmpat) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexLima) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }

                //====================== 31 - 35
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexDua) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexTiga) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexEmpat) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexLima) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }

                //======================= 36 - 40
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 180 && kolesterol == indexSatu) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 180 && kolesterol == indexDua) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 180 && kolesterol == indexTiga) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 180 && kolesterol == indexEmpat) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 180 && kolesterol == indexLima) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }

                //======================= 41 - 45
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexDua) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexTiga) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexEmpat) {
                    hasil = "<10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexLima) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }

                //====================== 46 - 50
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexDua) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexTiga) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexEmpat) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexLima) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }

                //====================== 51 - 55
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexDua) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexTiga) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexEmpat) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexLima) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }

                //===================== 56 - 60
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 180 && kolesterol == indexSatu) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 180 && kolesterol == indexDua) {
                    hasil = "30 % to < 40 %";
                    tingkat = "Lumayan Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 180 && kolesterol == indexTiga) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 180 && kolesterol == indexEmpat) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 180 && kolesterol == indexLima) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }

                //=================== 61 - 65
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexDua) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexTiga) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexEmpat) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexLima) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }

                //==================== 66 - 70
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexSatu) {
                    hasil = " < 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexDua) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexTiga) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexEmpat) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexLima) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }

                //===================== 71 - 75
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexSatu) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexDua) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexTiga) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexEmpat) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexLima) {
                    hasil = "30 % to < 40 %";
                    tingkat = "Lumayan Bahaya";
                }

                //================= 76 - 80
                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexSatu) {
                    hasil = "30 % to < 40 %";
                    tingkat = "Lumayan Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexDua) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexTiga) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexEmpat) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexLima) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }

                //####################### T A B L E  !NON - D I A B E T E S  && P E R O K O K && WANITA ||| DONE #######################################################33
                //####################### T A B L E  !NON - D I A B E T E S  && P E R O K O K && WANITA ||| DONE #######################################################33

                //-------------------------------------------------------------------------------------------------------------------------------
                //-------------------------------------------------------------------------------------------------------------------------------
                //--------------------------------------------------------PEMBATAS---------------------------------------------------------------

                //:::::::::::::::::::::::::::::::::::::::; DIABETES && NON-PEROKOK && PRIA  ;::::::::::::::::::::::::::::::::::::::::::::::::://

                //==========================> 1 - 5
                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexDua){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexEmpat){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexLima){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }
//
//                //==========================> 6 - 10
                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexDua) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexTiga) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexEmpat) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexLima) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }
//
//                //=========================> 11 - 15
                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexDua) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexTiga) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexEmpat) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexLima) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }
//
//                //========================== 16 - 20
                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 180  && kolesterol == indexSatu) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 180  && kolesterol == indexDua) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 180  && kolesterol == indexTiga) {
                    hasil = "30 % to < 40 %";
                    tingkat = "Lumayan Bahaya";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 180  && kolesterol == indexEmpat) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 180  && kolesterol == indexLima) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }
//
//                //========================= 21 - 25
                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 0 && tensiFix <= 139  && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexDua) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexTiga) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexEmpat) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexLima) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }
//
//                //====================== 26 - 30
                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexDua) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexTiga) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexEmpat) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexLima) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }
//
//                //====================== 31 - 35
                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexDua) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexTiga) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexEmpat) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexLima) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }
//
//                //======================= 36 - 40
                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 180 && kolesterol == indexSatu) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 180 && kolesterol == indexDua) {
                    hasil = "30 % to < 40 %";
                    tingkat = "Lumayan Bahaya";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 180 && kolesterol == indexTiga) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 180 && kolesterol == indexEmpat) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 180 && kolesterol == indexLima) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }
//
//                //======================= 41 - 45
                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexDua) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexTiga) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexEmpat) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexLima) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }
//
//                //====================== 46 - 50
                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexDua) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexTiga) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexEmpat) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexLima) {
                    hasil = "30 % to < 40 %";
                    tingkat = "Lumayan Bahaya";
                }
//
//                //====================== 51 - 55
                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexSatu) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexDua) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexTiga) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexEmpat) {
                    hasil = "30 % to < 40 %";
                    tingkat = "Lumayan Bahaya";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexLima) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }
//
//                //===================== 56 - 60
                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 180 && kolesterol == indexSatu) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 180 && kolesterol == indexDua) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 180 && kolesterol == indexTiga) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 180 && kolesterol == indexEmpat) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 180 && kolesterol == indexLima) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }
//
//                //=================== 61 - 65
                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                    tingkat = "Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexDua) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexTiga) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexEmpat) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexLima) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }
//
//                //==================== 66 - 70
                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexSatu) {
                    hasil = "10 % to < 20 %";
                    tingkat = "Lumayan Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexDua) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexTiga) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexEmpat) {
                    hasil = "30 % to < 40 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexLima) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }
//
//                //===================== 71 - 75
                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexSatu) {
                    hasil = "20 % to < 30 %";
                    tingkat = "Kurang Aman";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexDua) {
                    hasil = "30 % to < 40 %";
                    tingkat = "Lumayan Bahaya";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexTiga) {
                    hasil = "30 % to < 40 %";
                    tingkat = "Lumayan Bahaya";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexEmpat) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexLima) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }

                //================= 76 - 80
                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexSatu) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexDua) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexTiga) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexEmpat) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexLima) {
                    hasil = "> 40 %";
                    tingkat = "Bahaya";
                }


//

//                                            ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ B A T A S   C O M P I L E ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

//                //####################### T A B L E  !D I A B E T E S  && P E R O K O K && PRIA ||| DONE #######################################################33
//                //####################### T A B L E  !D I A B E T E S  && P E R O K O K && PRIA ||| DONE #######################################################33
//
//
//                //:::::::::::::::::::::::::::::::::::::::; DIABETES && PEROKOK && PRIA  ;::::::::::::::::::::::::::::::::::::::::::::::::://
//
//                //==========================> 1 - 5
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexSatu){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexDua){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexTiga){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexEmpat){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexLima){
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }
//
//                //==========================> 6 - 10
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexSatu){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexDua) {
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexTiga) {
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexEmpat) {
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexLima) {
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Lumayan Aman";
//                }
//
//                //=========================> 11 - 15
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexSatu) {
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexDua) {
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexTiga) {
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexEmpat) {
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Lumayan Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexLima) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //========================== 16 - 20
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 180  && kolesterol == indexSatu) {
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Lumayan Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 180  && kolesterol == indexDua) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 180  && kolesterol == indexTiga) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 180  && kolesterol == indexEmpat) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 180  && kolesterol == indexLima) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //========================= 21 - 25
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 0 && tensiFix <= 139  && kolesterol == indexSatu) {
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexDua) {
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexTiga) {
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexEmpat) {
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexLima) {
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //====================== 26 - 30
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexSatu) {
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexDua) {
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexTiga) {
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexEmpat) {
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexLima) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //====================== 31 - 35
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexSatu) {
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexDua) {
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexTiga) {
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Lumayan Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexEmpat) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexLima) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //======================= 36 - 40
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 180 && kolesterol == indexSatu) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 180 && kolesterol == indexDua) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 180 && kolesterol == indexTiga) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 180 && kolesterol == indexEmpat) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 180 && kolesterol == indexLima) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //======================= 41 - 45
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexSatu) {
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexDua) {
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexTiga) {
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexEmpat) {
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexLima) {
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Lumayan Bahaya";
//                }
//
//                //====================== 46 - 50
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexSatu) {
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexDua) {
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexTiga) {
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexEmpat) {
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Lumayan Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexLima) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //====================== 51 - 55
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexSatu) {
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexDua) {
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Lumayan Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexTiga) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexEmpat) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexLima) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //===================== 56 - 60
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 180 && kolesterol == indexSatu) {
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Lumayan Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 180 && kolesterol == indexDua) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 180 && kolesterol == indexTiga) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 180 && kolesterol == indexEmpat) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 180 && kolesterol == indexLima) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //=================== 61 - 65
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexSatu) {
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexDua) {
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexTiga) {
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexEmpat) {
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Lumayan Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexLima) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //==================== 66 - 70
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexSatu) {
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexDua) {
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Lumayan Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexTiga) {
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Lumayan Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexEmpat) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexLima) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //===================== 71 - 75
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexSatu) {
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Lumayan Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexDua) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexTiga) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexEmpat) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexLima) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //================= 76 - 80
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexSatu) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexDua) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexTiga) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexEmpat) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Pria" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexLima) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//                //####################### T A B L E  !D I A B E T E S  && P E R O K O K && PRIA ||| DONE #######################################################33
//                //####################### T A B L E  !D I A B E T E S  && P E R O K O K && PRIA ||| DONE #######################################################33
//
//                //:::::::::::::::::::::::::::::::::::::::; !DIABETES && NON - PEROKOK && WANITA  ;::::::::::::::::::::::::::::::::::::::::::::::::://
//
//                //============ 1 - 5
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 0 && tensiFix <=139 && kolesterol == indexSatu) {
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                    saran1 = "Lanjutkan pola hidup sehatmu";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 0 && tensiFix <=139 && kolesterol == indexDua){
//                    hasil = "< 10 %";
//                    tingkat = " Aman";
//                    saran1 = "Lanjutkan pola hidup sehatmu";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 0 && tensiFix <=139 && kolesterol == indexTiga){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 0 && tensiFix <=139 && kolesterol == indexEmpat){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 0 && tensiFix <=139 && kolesterol == indexLima){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }
//
//                //================= 6 - 10
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexSatu){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                    saran1 = "Lanjutkan pola hidup sehatmu";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexDua){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                    saran1 = "Lanjutkan pola hidup sehatmu";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexTiga){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexEmpat){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexLima){
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }
//
//                //==================== 11 - 15
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexSatu){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                    saran1 = "Lanjutkan pola hidup sehatmu";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexDua){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                    saran1 = "Lanjutkan pola hidup sehatmu";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexTiga){
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexEmpat){
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexLima){
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //====================== 16 - 20
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49 && tensiFix > 180 && kolesterol == indexSatu){
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49 && tensiFix > 180  && kolesterol == indexDua){
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Lumayan Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49 && tensiFix > 180 && kolesterol == indexTiga){
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49 && tensiFix > 180 && kolesterol == indexEmpat){
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 0 && usiaFix <=49 && tensiFix > 180 && kolesterol == indexLima){
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //======================= 21 - 25
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexSatu) {
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                    saran1 = "Lanjutkan pola hidup sehatmu";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexDua){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                    saran1 = "Lanjutkan pola hidup sehatmu";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexTiga){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexEmpat){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexLima){
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }
//
//                //====================== 26 - 30
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexSatu){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexDua){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexTiga){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexEmpat){
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexLima){
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }
//
//                //======================= 31 - 35
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59   && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexSatu){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59   && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexDua){
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexTiga){
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexEmpat){
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59   && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexLima){
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //======================= 36 - 40
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 &&  usiaFix <=59  && tensiFix > 180 && kolesterol == indexSatu){
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 &&  usiaFix <=59  && tensiFix > 180 && kolesterol == indexDua){
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Lumayan Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 &&  usiaFix <=59  && tensiFix > 180 && kolesterol == indexTiga){
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 &&  usiaFix <=59  && tensiFix > 180 && kolesterol == indexEmpat){
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 &&  usiaFix <=59  && tensiFix > 180 && kolesterol == indexLima){
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                // ================= 41 - 45
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexSatu) {
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexDua){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexTiga){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexEmpat){
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexLima){
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }
//
//                //=================== 46 - 50
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexSatu){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexDua){
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexTiga){
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexEmpat){
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexLima){
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Lumayan Bahaya";
//                }
//
//                //================== 51 - 55
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69   && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexSatu){
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69   && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexDua){
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexTiga){
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexEmpat){
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Lumayan Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69   && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexLima){
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //================== 56 - 60
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 &&  usiaFix <=69  && tensiFix > 180 && kolesterol == indexSatu){
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 &&  usiaFix <=69  && tensiFix > 180 && kolesterol == indexDua){
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 &&  usiaFix <=69  && tensiFix > 180 && kolesterol == indexTiga){
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 &&  usiaFix <=69  && tensiFix > 180 && kolesterol == indexEmpat){
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 &&  usiaFix <=69  && tensiFix > 180 && kolesterol == indexLima){
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //==================== 61 - 65
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexSatu) {
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexDua){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexTiga){
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexEmpat){
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexLima){
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }
//
//                //======================= 66 - 70
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexSatu){
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexDua){
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexTiga){
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexEmpat){
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexLima){
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Lumayan Bahaya";
//                }
//
//                //====================== 71 - 75
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexSatu){
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexDua){
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexTiga){
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexEmpat){
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Lumayan Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexLima){
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //================ 76 - 80
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexSatu){
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 180 && kolesterol == indexDua){
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexTiga){
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexEmpat){
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexLima){
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //####################### T A B L E  !D I A B E T E S  && !NON - P E R O K O K && WANITA ||| DONE #######################################################33
//                //####################### T A B L E  !D I A B E T E S  && !NON - P E R O K O K && WANITA ||| DONE #######################################################33
//
//                //:::::::::::::::::::::::::::::::::::::::; !DIABETES && PEROKOK && WANITA  ;::::::::::::::::::::::::::::::::::::::::::::::::://
//
//                //==========================> 1 - 5
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexSatu){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexDua){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexTiga){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexEmpat){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexLima){
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }
//
//                //==========================> 6 - 10
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexSatu){
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexDua) {
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexTiga) {
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexEmpat) {
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexLima) {
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Kurang Aman";
//                }
//
//                //=========================> 11 - 15
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexSatu) {
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexDua) {
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexTiga) {
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexEmpat) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexLima) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //========================== 16 - 20
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 180  && kolesterol == indexSatu) {
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Lumayan Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 180  && kolesterol == indexDua) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 180  && kolesterol == indexTiga) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 180  && kolesterol == indexEmpat) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 0 && usiaFix <= 49 && tensiFix >= 180  && kolesterol == indexLima) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //========================= 21 - 25
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 0 && tensiFix <= 139  && kolesterol == indexSatu) {
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexDua) {
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexTiga) {
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexEmpat) {
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexLima) {
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }
//
//                //====================== 26 - 30
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexSatu) {
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexDua) {
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexTiga) {
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexEmpat) {
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexLima) {
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Lumayan Bahaya";
//                }
//
//                //====================== 31 - 35
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexSatu) {
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexDua) {
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexTiga) {
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexEmpat) {
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexLima) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //======================= 36 - 40
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 180 && kolesterol == indexSatu) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 180 && kolesterol == indexDua) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 180 && kolesterol == indexTiga) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 180 && kolesterol == indexEmpat) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 50 && usiaFix <= 59 && tensiFix >= 180 && kolesterol == indexLima) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //======================= 41 - 45
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexSatu) {
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexDua) {
//                    hasil = "< 10 %";
//                    tingkat = "Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexTiga) {
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexEmpat) {
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexLima) {
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }
//
//                //====================== 46 - 50
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexSatu) {
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexDua) {
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexTiga) {
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexEmpat) {
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexLima) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //====================== 51 - 55
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexSatu) {
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexDua) {
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexTiga) {
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Lumayan Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexEmpat) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexLima) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //===================== 56 - 60
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 180 && kolesterol == indexSatu) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 180 && kolesterol == indexDua) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 180 && kolesterol == indexTiga) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 180 && kolesterol == indexEmpat) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 60 && usiaFix <= 69 && tensiFix >= 180 && kolesterol == indexLima) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //=================== 61 - 65
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexSatu) {
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexDua) {
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexTiga) {
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexEmpat) {
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 0 && tensiFix <= 139 && kolesterol == indexLima) {
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Lumayan Bahaya";
//                }
//
//                //==================== 66 - 70
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexSatu) {
//                    hasil = "10 % to < 20 %";
//                    tingkat = "Lumayan Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexDua) {
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexTiga) {
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Lumayan Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexEmpat) {
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Lumayan Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <= 159 && kolesterol == indexLima) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //===================== 71 - 75
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexSatu) {
//                    hasil = "20 % to < 30 %";
//                    tingkat = "Kurang Aman";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexDua) {
//                    hasil = "30 % to < 40 %";
//                    tingkat = "Lumayan Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexTiga) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexEmpat) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <= 179 && kolesterol == indexLima) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }
//
//                //================= 76 - 80
//                else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexSatu) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexDua) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexTiga) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexEmpat) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//                }else if (ApaDiabetes == "Iya" && ApaKelamin == "Wanita" && ApaRokok == "Iya" && usiaFix >= 70 && tensiFix >= 180 && kolesterol == indexLima) {
//                    hasil = "> 40 %";
//                    tingkat = "Bahaya";
//
//                }
                //####################### T A B L E  !D I A B E T E S  && P E R O K O K && WANITA ||| DONE #######################################################33
                //####################### T A B L E  !D I A B E T E S  && P E R O K O K && WANITA ||| DONE #######################################################33




                Diagnosa diagnosa = new Diagnosa(
                        tanggal, ApaDiabetes, ApaKelamin, ApaRokok, usia, tensi, kolesterol, hasil, tingkat, saran1, saranDiabetes, saranFaktor, saranKolesterol, saranTensi, saranRokok);

                userIdRef.document(userID).collection("Diagnosa").add(diagnosa);


//                Bundle bundle = new Bundle();
//                bundle.putString("hasils", hasil);
               // Intent intent = new Intent(CreateDiagnosaActivity.this, HasilDiagnosaJantungActivity.class);
               // intent.putExtras(bundle);


                Intent intent = new Intent(CreateDiagnosaActivity.this, HasilDiagnosaJantungActivity.class);
                intent.putExtra("hasils", hasil);
                intent.putExtra("tingkats", tingkat);
                intent.putExtra("saranssatu", saran1);
                intent.putExtra("sarandiabetess", saranDiabetes);
                intent.putExtra("saranFaktors", saranFaktor);
                intent.putExtra("saranKolesterols", saranKolesterol);
                intent.putExtra("saranTensis", saranTensi);
                intent.putExtra("saranRokoks",saranRokok);
                startActivity(intent);


            }
        });
    }

}
