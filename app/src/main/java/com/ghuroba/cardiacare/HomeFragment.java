package com.ghuroba.cardiacare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class HomeFragment extends Fragment {

    CardView diagnosa_card;
    CardView history_card;
    Activity context;
    Button butt;
    ViewFlipper viewFlipper;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int image[]  = {R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};

        viewFlipper = (ViewFlipper) context.findViewById(R.id.Vflipper);

//        for (int i=0; i<image.length; i++) {
//            flipperImage(image[i]);
//        }

        for (int images : image) {
            flipperImage(images);
        }

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
                Intent inToHistory = new Intent(context, HistorysActivity.class);
                startActivity(inToHistory);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();

        return inflater.inflate(R.layout.fragment_home,container,false);
    }


    public void onStart() {
        super.onStart();

        int image[]  = {R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};

        viewFlipper = (ViewFlipper) context.findViewById(R.id.Vflipper);

//        for (int i=0; i<image.length; i++) {
//            flipperImage(image[i]);
//        }

        for (int images : image) {
            flipperImage(images);
        }

//        diagnosa_card = (CardView) context.findViewById(R.id.diag_card);
//        history_card = (CardView) context.findViewById(R.id.his_card);
//
//        diagnosa_card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent inToLoginPage = new Intent(context, CreateDiagnosaActivity.class);
//                startActivity(inToLoginPage);
//            }
//        });
//
//        history_card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent inToHistory = new Intent(context, HistorysActivity.class);
//                startActivity(inToHistory);
//            }
//        });



    }

    public void flipperImage(int image) {
        ImageView imageView = new ImageView(context);
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(context, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(context, android.R.anim.slide_out_right);
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
