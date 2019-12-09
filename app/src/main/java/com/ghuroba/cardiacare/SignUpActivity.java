package com.ghuroba.cardiacare;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static maes.tech.intentanim.CustomIntent.customType;

public class SignUpActivity extends AppCompatActivity {

    EditText editTextName, editTextEmail, editTextPassword;
    Button butonregister;
    TextView gologinx;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    Uri pickedImgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        editTextName = findViewById(R.id.nama);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        butonregister = findViewById(R.id.buttonregister);
        progressBar = findViewById(R.id.progressbar);
        //progressBar.setVisibility(View.GONE);

        findViewById(R.id.buttonregister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.buttonregister:
                        registerUser();
                        break;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            //handle the allready login user
        }
    }


    private void registerUser() {
        final String name = editTextName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if (name.isEmpty()) {
            editTextName.setError(getString(R.string.input_error_name));
            editTextName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.input_error_email));
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError(getString(R.string.input_error_email_invalid));
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError(getString(R.string.input_error_password));
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError(getString(R.string.input_error_password_length));
            editTextPassword.requestFocus();
            return;
        }


        //progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Users user = new Users(
                                    name,
                                    email

                            );

                            FirebaseDatabase.getInstance().getReference("cardiacare-ghuroba")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUpActivity.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                                    } else {
                                        //display a failure message
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }




    //        butonregister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final String namas = namax.getText().toString();
//                String emails = emailx.getText().toString();
//                String passwords = passwordx.getText().toString();
//
//                if (namas.isEmpty()) {
//                    namax.setError("Please Enter Name");
//                    namax.requestFocus();
//                }else if (emails.isEmpty()) {
//                    emailx.setError("Please Enter Email");
//                    emailx.requestFocus();
//                }else if (passwords.isEmpty()) {
//                    passwordx.setError("Please Enter Password");
//                    passwordx.requestFocus();
//                }else if (namas.isEmpty() && emails.isEmpty() && passwords.isEmpty()) {
//                    Toast.makeText(SignUpActivity.this, "Fields Are Empty Area !", Toast.LENGTH_SHORT).show();
//                }else if (!(namas.isEmpty() && emails.isEmpty() && passwords.isEmpty())) {
//                    mFirebaseAuth.createUserWithEmailAndPassword(emails, passwords).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (!task.isSuccessful()) {
//                                Toast.makeText(SignUpActivity.this, "Register Unsuccessfull !, Please Try Again", Toast.LENGTH_SHORT).show();
//                            }else {
//                                Toast.makeText(SignUpActivity.this, "Register Successfull, Welcome", Toast.LENGTH_SHORT).show();
//                                updateUserInfo(namas, pickedImgUri, mFirebaseAuth.getCurrentUser());
//                                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
//
//                            }
//                        }
//                    });
//
//                }else {
//                    Toast.makeText(SignUpActivity.this, "Error Ocurred!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }
//
//    private void updateUserInfo(final String usernames, Uri pickedImgUri, final FirebaseUser curentUser) {
//        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("user_photos");
//        final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
//        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                //upload sukses
//                //dapati img uri
//
//                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
//                                .setDisplayName(usernames)
//                                .setPhotoUri(uri)
//                                .build();
//
//                        curentUser.updateProfile(profileUpdate)
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()) {
//                                            Toast.makeText(SignUpActivity.this, "Register Complete", Toast.LENGTH_SHORT).show();
//                                            updateUI();
//                                        }
//                                    }
//                                });
//                    }
//                });
//            }
//        });
//
//    }
//
//    private void updateUI() {
//        Intent toMainActivity = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(toMainActivity);
//        finish();
//    }
//
//
//
    public void Login(View view) {
        startActivity(new Intent(SignUpActivity.this, SigInActivity.class));
        customType(SignUpActivity.this, "bottom-to-top");

//        *left-to-right
//                *right-to-left
//                *bottom-to-up
//                *up-to-bottom
//                *fadein-to-fadeout
//                *rotateout-to-rotatein
   }
}
