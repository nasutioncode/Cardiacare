package com.ghuroba.cardiacare;

import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CreateDiagnosaActivity extends AppCompatActivity {

    public static final String DIABETES = "rgDiabetes";
    public static final String KELAMIN = "rgKelamin";
    public static final String ROKOK = "rgRokok";
    public static final String USIA = "usia";
    public static final String TENSI = "tensi";
    public static final String KOLESTEROL = "kolesterol";

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
//    RadioButton rbDiabetesYes, rbDiabetesNo, rbKelaminPria, rbKelaminWanita, rbRokokYes, rbRokokNo;
    RadioButton rbDiabetes, rbKelamin, rbRokok;
    EditText etUsia, etTensi;
    Spinner spinKolesterol;
    Button btHasil;
    String hasil;

//    DatabaseReference databaseReference;
//    FirebaseDatabase firebaseDatabase;
//    FirebaseAuth firebaseAuth;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore FSdatabase = FirebaseFirestore.getInstance();
    private CollectionReference userIdRef = FSdatabase.collection("UserInfo");

    private String userID;

    String diabetes = "";
    String kelamin = "";
    String rokok = "";
    String BulanApa = "";
    String NamaBulan;
    int hitungHari;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_diagnosa);

        tvDate = (TextView) findViewById(R.id.date_formJantung);
        rgDiabetes = findViewById(R.id.radio_diabetes);
        rgKelamin = findViewById(R.id.radio_kelamin);
        rgRokok = findViewById(R.id.radio_rokok);
//        rbDiabetesYes = (RadioButton) findViewById(R.id.diabetesYes);
//        rbDiabetesNo = (RadioButton) findViewById(R.id.diabetesNo);
//        rbKelaminPria = (RadioButton) findViewById(R.id.pria);
//        rbKelaminWanita = (RadioButton) findViewById(R.id.wanita);
//        rbRokokYes = (RadioButton) findViewById(R.id.rokokYes);
//        rbRokokNo = (RadioButton) findViewById(R.id.rokokNo);
        etUsia = (EditText) findViewById(R.id.text_usia);
        etTensi = (EditText) findViewById(R.id.text_tensi);
        spinKolesterol = (Spinner) findViewById(R.id.spinner_kolesterol);
        btHasil = (Button) findViewById(R.id.button_hasil);

        Calendar tanggal = Calendar.getInstance();
        String CurrentDate = DateFormat.getDateInstance(DateFormat.FULL).format(tanggal.getTime());
        tvDate.setText(CurrentDate);

        final Spinner kolesterol = spinKolesterol;

        List<String> categories = new ArrayList<>();
        categories.add(0, "4 = 15444 mg/dL");
        categories.add(1, "5 = 19305 mg/dL");
        categories.add(2, "6 = 23166 mg/dL");
        categories.add(3, "7 = 27027 mg/dL");
        categories.add(4, "8 = 30888 mg/dL");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateDiagnosaActivity.this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kolesterol.setAdapter(adapter);

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
                int rbK = Integer.parseInt(rbDiabetes.getText().toString());

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

                String usia = etUsia.getText().toString();
                usiaFix = Integer.parseInt(usia);
                String tensi = etTensi.getText().toString();
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


                String kolesterol = spinKolesterol.getSelectedItem().toString();
//                int kolestrolFix = Integer.parseInt(kolesterol);


                //PERUBAHAN PADA KOLESTEROL

                if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 40 && usiaFix <=49  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 40 && usiaFix <=49  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexDua){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 40 && usiaFix <=49  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 40 && usiaFix <=49  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexEmpat){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 40 && usiaFix <=49  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexLima){
                    hasil = "< 10 %";
                }

                // PERUBAHAN PADA TEKANAN DARAH

                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 40 && usiaFix <=49  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 40 && usiaFix <=49  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexDua){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 40 && usiaFix <=49  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 40 && usiaFix <=49  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexEmpat){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 40 && usiaFix <=49  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexLima){
                    hasil = "< 20 %";
                }

                //PERUBAHAN PADA TEKANAN DARAH

                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 40 && usiaFix <=49  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 40 && usiaFix <=49  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexDua){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 40 && usiaFix <=49  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 40 && usiaFix <=49  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexEmpat){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 40 && usiaFix <=49  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexLima){
                    hasil = "< 30 %";
                }


                //PERUBAHAN PADA TEKANAN DARAH

                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 40 && usiaFix <=49 && tensiFix > 180 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 40 && usiaFix <=49 && tensiFix > 180  && kolesterol == indexDua){
                    hasil = "< 20 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 40 && usiaFix <=49 && tensiFix > 180 && kolesterol == indexTiga){
                    hasil = "< 30 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 40 && usiaFix <=49 && tensiFix > 180 && kolesterol == indexEmpat){
                    hasil = "< 40 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 40 && usiaFix <=49 && tensiFix > 180 && kolesterol == indexLima){
                    hasil = "> 40 %";
                }



                //PERUBAHAN PADA USIA POLA YANG SAMA 10:10===================================================================================


                if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexDua){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexEmpat){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexLima){
                    hasil = "< 10 %";
                }



                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexDua){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexEmpat){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexLima){
                    hasil = "< 20 %";
                }



                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59   && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59   && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexDua){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexEmpat){
                    hasil = "< 20 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 && usiaFix <=59   && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexLima){
                    hasil = "< 30 %";
                }



                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 &&  usiaFix <=59  && tensiFix > 180 && kolesterol == indexSatu){
                    hasil = "< 20 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 &&  usiaFix <=59  && tensiFix > 180 && kolesterol == indexDua){
                    hasil = "< 30 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 &&  usiaFix <=59  && tensiFix > 180 && kolesterol == indexTiga){
                    hasil = "< 30 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 &&  usiaFix <=59  && tensiFix > 180 && kolesterol == indexEmpat){
                    hasil = "> 40 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 50 &&  usiaFix <=59  && tensiFix > 180 && kolesterol == indexLima){
                    hasil = "> 40 %";
                }


                //PERUBAHAN PADA USIA ** LAGI ** DENGAN POLA YANG SAMA 10:10=========== K E D U A=============================================================================


                if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexDua){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexEmpat){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexLima){
                    hasil = "< 10 %";
                }



                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexDua){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexEmpat){
                    hasil = "< 20 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexLima){
                    hasil = "< 20 %";
                }



                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69   && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69   && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexDua){
                    hasil = "< 20 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexTiga){
                    hasil = "< 20 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexEmpat){
                    hasil = "< 30 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 && usiaFix <=69   && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexLima){
                    hasil = "< 40 %";
                }



                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 &&  usiaFix <=69  && tensiFix > 180 && kolesterol == indexSatu){
                    hasil = "< 30 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 &&  usiaFix <=69  && tensiFix > 180 && kolesterol == indexDua){
                    hasil = "< 40 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 &&  usiaFix <=69  && tensiFix > 180 && kolesterol == indexTiga){
                    hasil = "> 40 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 &&  usiaFix <=69  && tensiFix > 180 && kolesterol == indexEmpat){
                    hasil = "> 40 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 60 &&  usiaFix <=69  && tensiFix > 180 && kolesterol == indexLima){
                    hasil = "> 40 %";
                }



                //PERUBAHAN PADA USIA ** LAGI ** DENGAN POLA YANG SAMA 10:10=========== K E T I G A =============================================================================


                if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexSatu) {
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexDua){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexTiga){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexEmpat){
                    hasil = "< 20 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 120 && tensiFix <=139 && kolesterol == indexLima){
                    hasil = "< 20 %";
                }



                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexSatu){
                    hasil = "< 10 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexDua){
                    hasil = "< 20 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexTiga){
                    hasil = "< 20 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexEmpat){
                    hasil = "< 20 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 140 && tensiFix <=159 && kolesterol == indexLima){
                    hasil = "< 30 %";
                }



                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexSatu){
                    hasil = "< 20 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexDua){
                    hasil = "< 20 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexTiga){
                    hasil = "< 30 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexEmpat){
                    hasil = "< 30 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix >= 160 && tensiFix <=179 && kolesterol == indexLima){
                    hasil = "< 40 %";
                }



                else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix > 180 && kolesterol == indexSatu){
                    hasil = "< 40 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70  && tensiFix > 180 && kolesterol == indexDua){
                    hasil = "< 40 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix > 180 && kolesterol == indexTiga){
                    hasil = "> 40 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix > 180 && kolesterol == indexEmpat){
                    hasil = "> 40 %";
                }else if (ApaDiabetes == "Tidak" && ApaKelamin == "Wanita" && ApaRokok == "Tidak" && usiaFix >= 70 && tensiFix > 180 && kolesterol == indexLima){
                    hasil = "> 40 %";
                }


                //####################### T A B L E  !D I A B E T E S  && !P E R O K O K ||| DONE #######################################################33
                //####################### T A B L E  !D I A B E T E S  && !P E R O K O K ||| DONE #######################################################33




                Diagnosa diagnosa = new Diagnosa(tanggal, ApaDiabetes, ApaKelamin, ApaRokok, usia, tensi, kolesterol, hasil);


                userIdRef.document(userID).collection("Diagnosa").add(diagnosa);


//                Bundle bundle = new Bundle();
//                bundle.putString("hasils", hasil);
               // Intent intent = new Intent(CreateDiagnosaActivity.this, HasilDiagnosaJantungActivity.class);
               // intent.putExtras(bundle);


                Intent intent = new Intent(CreateDiagnosaActivity.this, HasilDiagnosaJantungActivity.class);
                intent.putExtra("hasils", hasil);
                startActivity(intent);


            }
        });
    }

}
