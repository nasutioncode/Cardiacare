package com.ghuroba.cardiacare;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class EditProfile extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 101;
    Button bt_logout, bt_edit;
    Activity context;
    ImageView imageView;
    ProgressBar progressBar;
    Uri uriProfilImage;
    Dialog myDialog;
    String profilImageUri;
    Button txtclose;
    Button save;

    EditText txt_nama, txt_email;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth = FirebaseAuth.getInstance();

        txt_nama = (EditText) findViewById(R.id.nama);
        txt_email = (EditText) findViewById(R.id.email);

        imageView = (ImageView) findViewById(R.id.profilPhoto);
        progressBar = (ProgressBar) findViewById(R.id.progresBar);
        save = (Button) findViewById(R.id.btnSaveProfile);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoiceImage();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserProfile();
            }
        });
    }

    private void saveUserProfile() {
        String displayNames = txt_nama.getText().toString();
        String displayEmails = txt_email.getText().toString();

        if (displayNames.isEmpty()) {
            txt_nama.setError("Nama Required");
            txt_nama.requestFocus();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null && profilImageUri != null) {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayNames)
                   // .setDisplayEmail(displayEmails)
                    .setPhotoUri(Uri.parse(profilImageUri))
                    .build();

            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(EditProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                            Intent toProfilFragment = new Intent(context, ProfilFragment.class);
                            startActivity(toProfilFragment);
                        }
                    });
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriProfilImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfilImage);
                imageView.setImageBitmap(bitmap);

                uploadImageToFirebaseStorage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebaseStorage() {


        StorageReference storageReference =
                FirebaseStorage.getInstance().getReference("profilepics/" + System.currentTimeMillis() + ".jpg");

        if (uriProfilImage != null) {

           // progressBar.setVisibility(View.VISIBLE);

            storageReference.putFile(uriProfilImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           // progressBar.setVisibility(View.GONE);
                            profilImageUri = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);

                            Snackbar snackBar = Snackbar.make(context.findViewById(android.R.id.content),
                                    "Error", Snackbar.LENGTH_SHORT);
                            snackBar.show();

                            //Snackbar snackbar = Snackbar.make(context, "www.journaldev.com", Snackbar.LENGTH_LONG).show();
                            //Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();

                        }
                    });

        }
    }

    private void showChoiceImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profil Image"),  CHOOSE_IMAGE);
    }

}
