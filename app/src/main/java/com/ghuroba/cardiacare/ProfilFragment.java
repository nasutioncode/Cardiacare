package com.ghuroba.cardiacare;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static androidx.constraintlayout.widget.Constraints.TAG;

//import static androidx.constraintlayout.Constraints.TAG;

public class ProfilFragment extends Fragment {

    private static final int CHOOSE_IMAGE = 101;
    Button bt_logout, bt_edit, bt_delete, bt_baca;
    Activity context;
    ImageView imageView;
    ProgressBar progressBar;
    Uri uriProfilImage;
    Dialog myDialog;
    String profilImageUri;
    Button txtclose;
    Dialog fbDialogue;
    EditText nama, email;

    FirebaseAuth mAuth;

    EditText txt_nama, txt_email;


    private FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        context = getActivity();


        return inflater.inflate(R.layout.fragment_profil,container,false);
    }



    public void onStart() {
        super.onStart();



        nama = (EditText) context.findViewById(R.id.baca_nama);
        email = (EditText) context.findViewById(R.id.baca_email);
        //foto = (ImageView)context.findViewById(R.id.profilPhoto);
        imageView = (ImageView)context.findViewById(R.id.profilPhoto);
        progressBar = (ProgressBar)context.findViewById(R.id.progresBar);




        fbDialogue = new Dialog(context, android.R.style.Theme_Black_NoTitleBar);

        bt_logout = (Button)context.findViewById(R.id.logout);
        bt_edit = (Button)context.findViewById(R.id.Edit);
        bt_delete = (Button)context.findViewById(R.id.Delete);
        //txtclose =(Button) context.findViewById(R.id.txtclose);

        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent inToLoginPage = new Intent(context, SigInActivity.class);
                startActivity(inToLoginPage);
                getActivity().finish();
                //ProfilFragment.this.finalize();
            }
        });

        bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
//                fbDialogue.setContentView(R.layout.activity_edit_profile);
//                fbDialogue.setCancelable(true);
//                fbDialogue.show();

                Intent i = new Intent(context, EditProfile.class);
                startActivity(i);

            }
        });

        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser();
                Intent intent = new Intent(context, SigInActivity.class);
                startActivity(intent);
            }
        });

//        bt_baca.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loadUserInformation();
//            }
//        });

        loadUserInformation();
    }


    private void loadUserInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            if (user.getPhotoUrl() != null) {
                Glide.with(ProfilFragment.this)
                        .load(user.getPhotoUrl().toString())
                        .into(imageView);
            }

            if (user.getDisplayName() != null) {
                nama.setText(user.getDisplayName());
            }

            if (user.getEmail() != null) {
                email.setText(user.getEmail());
            }
        }
    }


    public void deleteUser() {
        // [START delete_user]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String info = user.getUid();


        FirebaseFirestore FSdatabase = FirebaseFirestore.getInstance();
        CollectionReference userIdRef = FSdatabase.collection("UserInfo");

        userIdRef.document(info).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted.");

                        }
                    }
                });

        user.delete();


        // [END delete_user]
    }


    public void getUserProfile() {
        // [START get_user_profile]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String info = user.getUid();

        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri foto = user.getPhotoUrl();

            // Check if user's email is verified
            String uid = user.getUid();
        }
        // [END get_user_profile]
    }

}
