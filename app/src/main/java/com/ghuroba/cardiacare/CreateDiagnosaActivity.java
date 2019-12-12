package com.ghuroba.cardiacare;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CreateDiagnosaActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;

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

        final Spinner kolesterol = spinKolesterol;

        List<String> categories = new ArrayList<>();
        categories.add(0, "4 = 154,44");
        categories.add(1, "5 = 193,05");
        categories.add(2, "6 = 231,66");
        categories.add(3, "7 = 270,27");
        categories.add(4, "8 = 308,88");

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kolesterol.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("dataDiagnosa");

        btHasil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                //AlertPopUp
                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(CreateDiagnosaActivity.this);

                myAlertDialog.setTitle("PERINGATAN");
                myAlertDialog.setMessage("Apakah anda yakin data yang anda isikan sudah benar?");
                myAlertDialog.setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                myAlertDialog.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                myAlertDialog.show();
            }
        });
    }




/*
* if (RadioGroupDiabetes == 1 %% RadioGroupKelamin == 1 %% RadioGroupPerokok == 1 %% tekanan darah >= 40 %%)
* */
//    public void onRadioButtonClicked(View view) {
//        boolean checked =((RadioButton) view).isChecked();
//
//        switch (view.getId()){
//            case R.id.diabetesYes:
//                break;
//            case R.id.diabetesNo:
//                break;
//            default:
//                break;
//        }
//
//        switch (view.getId()){
//            case R.id.rokokYes:
//                break;
//            case R.id.rokokNo:
//                break;
//             default:
//                 break;
//        }
//    }

    public void buttonHasil(View view) {

//        // RGD = RadioGroupDiabetes
//        // RGK = RadioGroupKelamin
//        // RGP = RadioGroupPerokok
//        int RGD = rgDiabetes.getCheckedRadioButtonId();
//        rbDiabetes = findViewById(RGD);
//        int;
    }
}
