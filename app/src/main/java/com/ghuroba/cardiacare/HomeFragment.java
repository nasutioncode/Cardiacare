package com.ghuroba.cardiacare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {

    CardView diagnosa_card;
    CardView history_card;
    Activity context;
    Button butt;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();

        return inflater.inflate(R.layout.fragment_home,container,false);
    }




    public void onStart() {
        super.onStart();

        diagnosa_card = (CardView) context.findViewById(R.id.diag_card);
        history_card = (CardView) context.findViewById(R.id.his_card);

        diagnosa_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inToLoginPage = new Intent(context, CreateDiagnosaActivity.class);
                startActivity(inToLoginPage);
            }
        });

        history_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inToHistory = new Intent(context, HasilDiagnosaJantungActivity.class);
                startActivity(inToHistory);
            }
        });


    }


//    public void onStart() {
//        diagnosa_card = (CardView) context.findViewById(R.id.diag_card);
//
//        diagnosa_card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent inToDiagnosa = new Intent(context, CreateDiagnosaJantungActivity.class);
//                startActivity(inToDiagnosa);
//            }
//        });
//    }

}
