package com.ghuroba.cardiacare;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HasilDiagnosaJantungActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_diagnosa_jantung);

        TextView hasilIntent = (TextView) findViewById(R.id.text_hasilRisiko);
        TextView tingkatIntent = (TextView) findViewById(R.id.tingkat_resiko);
        TextView saranDiabetes = (TextView) findViewById(R.id.sarandiabtes);
        TextView saranFaktor = (TextView) findViewById(R.id.saranfaktor);
        TextView saranKolestrol = (TextView) findViewById(R.id.sarankolestrol);
        TextView saranTensi = (TextView) findViewById(R.id.sarantensi);
        TextView saranRokok = (TextView) findViewById(R.id.saranrokok);
        TextView saranSatu = (TextView) findViewById(R.id.saransatu);


        String hasil = hasilIntent.getText().toString();


        if (hasil.equals("< 10 %")) {
            hasilIntent.setTextColor(Color.RED);
        }





        Button btn_detail = (Button) findViewById(R.id.button_hasilDetail);


        if(getIntent().getExtras()!=null){
            /**
             * Jika Bundle ada, ambil data dari Bundle
             */
            Bundle bundle = getIntent().getExtras();
            hasilIntent.setText(bundle.getString("hasils"));
            tingkatIntent.setText(bundle.getString("tingkats"));
            saranDiabetes.setText(bundle.getString("sarandiabetess"));
            saranFaktor.setText(bundle.getString("saranFaktors"));
            saranKolestrol.setText(bundle.getString("saranKolesterols"));
            saranTensi.setText(bundle.getString("saranTensis"));
            saranRokok.setText(bundle.getString("saranRokoks"));
            saranSatu.setText(bundle.getString("saranssatu"));

        }else{
            /**
             * Apabila Bundle tidak ada, ambil dari Intent
             */
            hasilIntent.setText(getIntent().getStringExtra("hasils"));
            tingkatIntent.setText(getIntent().getStringExtra("tingkats"));
            saranDiabetes.setText(getIntent().getStringExtra("sarandiabetess"));
            saranFaktor.setText(getIntent().getStringExtra("saranFaktors"));
            saranKolestrol.setText(getIntent().getStringExtra("saranKolesterols"));
            saranTensi.setText(getIntent().getStringExtra("saranTensis"));
            saranRokok.setText(getIntent().getStringExtra("saranRokoks"));
            saranSatu.setText(getIntent().getStringExtra("saranssatu"));

        }

        btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toDetails = new Intent(HasilDiagnosaJantungActivity.this, DetailsActivity.class);
                startActivity(toDetails);
            }
        });
    }


}
