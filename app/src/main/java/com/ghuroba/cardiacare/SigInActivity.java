package com.ghuroba.cardiacare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import static maes.tech.intentanim.CustomIntent.customType;

public class SigInActivity extends AppCompatActivity {

    EditText usernamex, emailx, passwordx;
    Button butonloginx;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //setTheme(R.style.AppThemeLauncher);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sig_in);


        mFirebaseAuth = FirebaseAuth.getInstance();
        usernamex = findViewById(R.id.nama);
        emailx = findViewById(R.id.emaillogin);
        passwordx = findViewById(R.id.passwordlogin);
        butonloginx = findViewById(R.id.buttonlogin);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null ) {
                    Toast.makeText(SigInActivity.this, "You Are Loged In", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SigInActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(SigInActivity.this, "Welcome, Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };

        butonloginx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emails = emailx.getText().toString();
                String passwords = passwordx.getText().toString();

                if (emails.isEmpty()) {
                    emailx.setError("Please Enter Email");
                    emailx.requestFocus();
                }else if (passwords.isEmpty()) {
                    passwordx.setError("Please Enter Password");
                    passwordx.requestFocus();
                }else if (emails.isEmpty() && passwords.isEmpty()) {
                    Toast.makeText(SigInActivity.this, "Fields Are Empty Area !", Toast.LENGTH_SHORT).show();
                }else if (!(emails.isEmpty() && passwords.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(emails,passwords).addOnCompleteListener(SigInActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(SigInActivity.this, "Email and Password not Registered", Toast.LENGTH_SHORT).show();
                            }else {
                                Intent inTohome = new Intent(SigInActivity.this, MainActivity.class);
                                startActivity(inTohome);
                            }
                        }

                    });
                }else {
                    Toast.makeText(SigInActivity.this, "Error Ocurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    public void Register(View view) {
        startActivity(new Intent(SigInActivity.this, SignUpActivity.class));
        customType(SigInActivity.this, "bottom-to-up");
    }
}

//        1. Siapakah client --> Dokter,
//
//        2. Siapakah pengguna -->
//        Pengguna dapat digunakan untuk segala umur tetapi lebih ditekankan untuk Orangtua di atas umur 40 tahun yang belum tahu mengenai kondisi jantungnya. dan belum tahu cara untuk mengatasi agar terhindar dari penyakit jantung.
//
//        3. Mengapa perlu dibuat dalam aplikasi bergerak -->
//        Aplikasi ini dibuat bertujuan untuk mengecek seberapa tinggi potensi seseorang terkena penyakit jantung untuk 10 tahun mendatang dengan kondisi yang sekarang. Dengan dia mengetahui kondisinya yang sekarang dia bisa tahu apakah dia sekarang sudah aman dari bahaya penyakit jantung atau belum. Jika belum, di aplikasi ini menyediakan saran-saran yang harus dilakukan untuk meminimalisir potensi risiko terkena penyakit jantung. Aplikasi ini juga menyediakan history yang berfungsi untuk memantau perkembangan seseorang selama 1 bulan. Apakah ada perubahan dengan hasil tingkat risikonya atau tidak.
//
//        4. CRUDnya dimana? .......BISA DI AKUN, UBAH PASWORD, DELETE AKUN, READ HISTORYS
//
//        5. Proses yang dapat dilakukan -->
//        • Mengecek tingkat/level potensi risiko terkena penyakit jantung.
//        • melihat history data tingkat/levelnya.
//        • melihat saran yang harus dilakukan sesuai tingkat risikonya.
//        • (Mungkin bisa jadi tambahan waktu nanti ditanya bedanya bikin web sama   aplikasi apa) Mendapatkan Notifikasi untuk mengingatkan agar menjalani   saran-saran yang sudah diberikan
//
//        6. Masukan dari aplikasi ini adalah dengan cara pengguna menginput data:
//        • Apakah terkena diabetes atau tidak.
//        • Jenis kelamin yang dia miliki (saat registrasi)
//        • Apakah dia perokok
//        • Usianya (saat registrasi)
//        • Tekanan darah/tensi
//        • Kadar kolesterolnya
//
//        7.Masukan yang didapatkan: Pengguna jadi tahu tingkat potensi risiko terkena penyakit jantungnya dengan kondisi kesehatannya yang sekarang. dan apa yang harus di ubah darinya agar dia dapat terhindar dari penyakit jantung tersebut.
