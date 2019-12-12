package com.ghuroba.cardiacare;

import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CreateDiagnosaActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;

    TextView tvDate;
    RadioGroup rgDiabetes, rgKelamin, rgPerokok;
    RadioButton rbDiabetesYes, rbDiabetesNo, rbKelaminPria, rbKelaminWanita, rbRokokYes, rbRokokNo;
    EditText etUsia, etTensi;
    Spinner spinKolesterol;
    Button btHasil;

    String diabetes = "";
    String kelamin = "";
    String rokok = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_diagnosa);

        tvDate = (TextView) findViewById(R.id.date_formJantung);
        rgDiabetes = findViewById(R.id.radio_diabetes);
        rgKelamin = findViewById(R.id.radio_kelamin);
        rgPerokok = findViewById(R.id.radio_rokok);
        rbDiabetesYes = (RadioButton) findViewById(R.id.diabetesYes);
        rbDiabetesNo = (RadioButton) findViewById(R.id.diabetesNo);
        rbKelaminPria = (RadioButton) findViewById(R.id.pria);
        rbKelaminWanita = (RadioButton) findViewById(R.id.wanita);
        rbRokokYes = (RadioButton) findViewById(R.id.rokokYes);
        rbRokokNo = (RadioButton) findViewById(R.id.rokokNo);
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

        databaseReference = FirebaseDatabase.getInstance().getReference("dataDiagnosa");

        btHasil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar tanggal = Calendar.getInstance();

                if (rbDiabetesYes.isChecked()){
                    diabetes = "1";
                } else if (rbDiabetesNo.isChecked()){
                    diabetes = "2";
                }

                if (rbKelaminPria.isChecked()){
                    kelamin = "1";
                } else if (rbKelaminWanita.isChecked()){
                    kelamin = "2";
                }

                if (rbRokokYes.isChecked()){
                    rokok = "1";
                } else if (rbRokokNo.isChecked()){
                    rokok = "2";
                }

                final String usia = etUsia.getText().toString();
                final String tensi = etTensi.getText().toString();
                Date tanggal2 = Calendar.getInstance().getTime();


                if (usia.isEmpty()) {
                    etUsia.setError("Tolong masukkan usia anda");
                    etUsia.requestFocus();
                }
                if (tensi.isEmpty()) {
                    etTensi.setError("Tolong masukkan tekanan darah anda");
                    etTensi.requestFocus();
                }

                //
                final String kolesterol = spinKolesterol.getSelectedItem().toString();

                //submitBarang(new Diagnosa(etNama.getText().toString(), etMerk.getText().toString(), etHarga.getText().toString()));

            }
        });

//        private void submitBarang(Diagnosa diagnosa) {
//            /**
//             * Ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime Database
//             * dan juga kita set onSuccessListener yang berisi kode yang akan dijalankan
//             * ketika data berhasil ditambahkan
//             */
//            database.child("barang").push().setValue(barang).addOnSuccessListener(this, new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void aVoid) {
//                    etNama.setText("");
//                    etMerk.setText("");
//                    etHarga.setText("");
//                    Snackbar.make(findViewById(R.id.bt_submit), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
//                }
//            });
//        }


    }

}
