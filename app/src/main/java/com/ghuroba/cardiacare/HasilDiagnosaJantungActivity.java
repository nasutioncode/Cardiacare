package com.ghuroba.cardiacare;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HasilDiagnosaJantungActivity extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore FSdatabase = FirebaseFirestore.getInstance();
    private CollectionReference userIdRef = FSdatabase.collection("UserInfo");
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_diagnosa_jantung);

        TextView hasilIntent = (TextView) findViewById(R.id.text_hasilRisiko);
        Button btn_detail = (Button) findViewById(R.id.button_hasilDetail);

        if(getIntent().getExtras()!=null){
            /**
             * Jika Bundle ada, ambil data dari Bundle
             */
            Bundle bundle = getIntent().getExtras();
            hasilIntent.setText(bundle.getString("hasils"));
        }else{
            /**
             * Apabila Bundle tidak ada, ambil dari Intent
             */
            hasilIntent.setText(getIntent().getStringExtra("hasils"));
        }

        btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toDetails = new Intent(HasilDiagnosaJantungActivity.this, DetailsActivity.class);
                startActivity(toDetails);
            }
        });
    }

//    private void ReadSingleContact() {
//
//        userIdRef.document(userID).collection("Diagnosa").add(diagnosa);
//
//        DocumentReference user = db.collection("PhoneBook").document("Contacts");
//
//        user.get().addOnCompleteListener(new OnCompleteListener< DocumentSnapshot >() {
//
//            @Override
//
//            public void onComplete(@NonNull Task< DocumentSnapshot > task) {
//
//                if (task.isSuccessful()) {
//
//                    DocumentSnapshot doc = task.getResult();
//
//                    StringBuilder fields = new StringBuilder("");
//
//                    fields.append("Name: ").append(doc.get("Name"));
//
//                    fields.append("\nEmail: ").append(doc.get("Email"));
//
//                    fields.append("\nPhone: ").append(doc.get("Phone"));
//
//                    textDisplay.setText(fields.toString());
//
//                }
//
//            }
//
//        })
//
//                .addOnFailureListener(new OnFailureListener() {
//
//                    @Override
//
//                    public void onFailure(@NonNull Exception e) {
//
//                    }
//
//                });
//
//    }
}
