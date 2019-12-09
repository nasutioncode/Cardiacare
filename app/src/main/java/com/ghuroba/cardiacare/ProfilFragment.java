package com.ghuroba.cardiacare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ProfilFragment extends Fragment {

    Button bt_logout;
    Activity context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        context = getActivity();

        return inflater.inflate(R.layout.fragment_profil,container,false);



    }

    public void onStart() {
        super.onStart();

        bt_logout = (Button)context.findViewById(R.id.logout);

        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Intent inToLoginPage = new Intent(context, SigInActivity.class);
                startActivity(inToLoginPage);
            }
        });
    }
}
