package com.ghuroba.cardiacare;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class CreateDiagnosaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_diagnosa);

    }

    public void onRadioButtonClicked(View view) {
        boolean checked =((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.diabetesYes:
                break;
            case R.id.diabetesNo:
                break;
            default:
                break;
        }

        switch (view.getId()){
            case R.id.rokokYes:
                break;
            case R.id.rokokNo:
                break;
             default:
                 break;
        }
    }

    public void showAlert(View view) {
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
    }
}
