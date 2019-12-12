package com.ghuroba.cardiacare;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HistorysActivity extends AppCompatActivity {

    private RecyclerView rHistorys;
    DatabaseReference rDatabase;
    private FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historys);

        rDatabase = FirebaseDatabase.getInstance().getReference().child("Diagnosa");
        rDatabase.keepSynced(true);

        rHistorys = (RecyclerView) findViewById(R.id.r_historys);
        rHistorys.setHasFixedSize(true);
        rHistorys.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<HistorysList, HistorysListViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<HistorysList, HistorysListViewHolder>
                (HistorysList.class, R.layout.historys_view, HistorysListViewHolder.class, rDatabase) {
            @Override
            protected void populateViewHolder(HistorysListViewHolder viewHolder, HistorysList model,int position) {
                viewHolder.setDiabetes(model.getDiabetes());
                viewHolder.setPerokok(model.gettDarah());
                viewHolder.setGender(model.getDiabetes());
                viewHolder.setUsia(model.gettDarah());
                viewHolder.setTensi(model.getDiabetes());
                viewHolder.setJmlkolestrol(model.gettDarah());
            }
        };
        rHistorys.setAdapter(firebaseRecyclerAdapter);
    }

    public static class HistorysListViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public HistorysListViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setDiabetes(String diabetes) {
            TextView post_diabetes = (TextView)mView.findViewById(R.id.baca_diabetes);
            post_diabetes.setText(diabetes);
        }
        public void setPerokok(String perokok) {
            TextView post_perokok = (TextView)mView.findViewById(R.id.baca_perokok);
            post_perokok.setText(perokok);
        }

        public void setGender(String gender) {
            TextView post_gender = (TextView)mView.findViewById(R.id.baca_gender);
            post_gender.setText(gender);
        }

        public void setUsia(String usia) {
            TextView post_usia = (TextView)mView.findViewById(R.id.baca_usia);
            post_usia.setText(usia);
        }

        public void setTensi(String tensi) {
            TextView post_tensi= (TextView)mView.findViewById(R.id.baca_tensi);
            post_tensi.setText(tensi);
        }

        public void setJmlkolestrol(String jmlkolestrol) {
            TextView post_jmlkolestrol = (TextView)mView.findViewById(R.id.baca_jmlkolestrol);
            post_jmlkolestrol.setText(jmlkolestrol);
        }
    }

}
