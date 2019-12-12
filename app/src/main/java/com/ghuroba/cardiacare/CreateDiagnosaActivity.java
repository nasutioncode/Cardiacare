package com.ghuroba.cardiacare;

import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

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


    String ApaDiabetes = "";
    String ApaKelamin = "";
    String ApaRokok = "";

    TextView tvDate;
    RadioGroup rgDiabetes, rgKelamin, rgRokok;
//    RadioButton rbDiabetesYes, rbDiabetesNo, rbKelaminPria, rbKelaminWanita, rbRokokYes, rbRokokNo;
    RadioButton rbDiabetes, rbKelamin, rbRokok;
    EditText etUsia, etTensi;
    Spinner spinKolesterol;
    Button btHasil;

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
        categories.add(0, "4 = 154,44");
        categories.add(1, "5 = 193,05");
        categories.add(2, "6 = 231,66");
        categories.add(3, "7 = 270,27");
        categories.add(4, "8 = 308,88");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateDiagnosaActivity.this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kolesterol.setAdapter(adapter);

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
                String tensi = etTensi.getText().toString();

                if (usia.isEmpty()) {
                    etUsia.setError("Tolong masukkan usia anda");
                    etUsia.requestFocus();
                }
                if (tensi.isEmpty()) {
                    etTensi.setError("Tolong masukkan tekanan darah anda");
                    etTensi.requestFocus();
                }

//
//                for (int i=0; i < 366; i++) {
//                    hitungHari++;
//                    if (hitungHari <= 30) {
//                        NamaBulan = "Januari";
//                    }else if (hitungHari >= 31 && hitungHari <= 62) {
//                        NamaBulan = "Februari";
//                    }else if (hitungHari >= 63 && hitungHari <= 94) {
//                        NamaBulan = "Maret";
//                    }
//
//
//                }
//
//
//                if (NamaBulan == "Januari") {
//                    BulanApa = "Januai";
//                }
//
//                if (NamaBulan == "Febuari") {
//                    BulanApa = "Februari";
//                }
//
//                if (NamaBulan == "Maret") {
//                    BulanApa = "Maret";
//                }

                String kolesterol = spinKolesterol.getSelectedItem().toString();





                Diagnosa diagnosa = new Diagnosa(tanggal, ApaDiabetes, ApaKelamin, ApaRokok, usia, tensi, kolesterol);

                userIdRef.document(userID).collection("Diagnosa").add(diagnosa);

                Intent intent = new Intent(CreateDiagnosaActivity.this, HasilDiagnosaJantungActivity.class);
                intent.putExtra(DIABETES, rgD);
                intent.putExtra(TENSI, tensi);
                intent.putExtra(KOLESTEROL, kolesterol);
                startActivity(intent);


//                if (rbDiabetesYes.isChecked()){
//                    diabetes = "1";
//                } else if (rbDiabetesNo.isChecked()){
//                    diabetes = "2";
//                }
//
//                if (rbKelaminPria.isChecked()){
//                    kelamin = "1";
//                } else if (rbKelaminWanita.isChecked()){
//                    kelamin = "2";
//                }
//
//                if (rbRokokYes.isChecked()){
//                    rokok = "1";
//                } else if (rbRokokNo.isChecked()){
//                    rokok = "2";
//                }

//                final String usia = etUsia.getText().toString();
//                final String tensi = etTensi.getText().toString();


                //
            }
        });
    }

}
