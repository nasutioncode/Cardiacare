package com.ghuroba.cardiacare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class DetailsAdapter extends FirestoreRecyclerAdapter<Details, DetailsAdapter.DetailHolder> {

    public DetailsAdapter(@NonNull FirestoreRecyclerOptions<Details> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull DetailHolder holder, int position, @NonNull Details model) {
        holder.textDiabetes.setText(model.getDiabetes());
        holder.textKelamin.setText(model.getKelamin());
        holder.textPerokok.setText(model.getRokok());
        holder.textUsia.setText(model.getUsia());
        holder.textTensi.setText(model.getTensi());
        holder.textKolestrol.setText(model.getKolesterol());
        holder.textHasil.setText(model.getHasil());
        holder.tingkat.setText(model.getTingkat());
        holder.saranDiabetes.setText(model.getSaranDiabetes());
        holder.saranFaktor.setText(model.getSaranFaktor());
        holder.saranKolestrol.setText(model.getSaranKolesterol());
        holder.saranTensi.setText(model.getSaranTensi());
        holder.saranRokok.setText(model.getSaranRokok());
        holder.saranSatu.setText(model.getSaran1());
    }

    @NonNull
    @Override
    public DetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_item, parent, false);
        return new DetailHolder(v);
    }


    //Hapus Data
    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class DetailHolder extends RecyclerView.ViewHolder {
        TextView textDiabetes;
        TextView textKelamin;
        TextView textPerokok;
        TextView textUsia;
        TextView textTensi;
        TextView textKolestrol;
        TextView textHasil;
        TextView tingkat;
        TextView saranDiabetes;
        TextView saranFaktor;
        TextView saranKolestrol;
        TextView saranTensi;
        TextView saranRokok;
        TextView saranSatu;

        public DetailHolder(@NonNull View itemView) {
            super(itemView);
            textDiabetes = itemView.findViewById(R.id.textViewDiabetes);
            textKelamin = itemView.findViewById(R.id.textViewKelamin);
            textPerokok = itemView.findViewById(R.id.textViewPerokok);
            textUsia = itemView.findViewById(R.id.textViewUmur);
            textTensi = itemView.findViewById(R.id.textViewTensi);
            textKolestrol = itemView.findViewById(R.id.textViewKolestrol);
            textHasil = itemView.findViewById(R.id.textViewHasil);
            tingkat = itemView.findViewById(R.id.textViewTingkat);
            saranDiabetes = itemView.findViewById(R.id.textViewSaranDiabetes);
            saranFaktor = itemView.findViewById(R.id.textViewSaranFaktor);
            saranKolestrol = itemView.findViewById(R.id.textViewSaranKolesterol);
            saranTensi = itemView.findViewById(R.id.textViewSaranTensi);
            saranRokok = itemView.findViewById(R.id.textViewSaranRokok);
            saranSatu = itemView.findViewById(R.id.textViewSaranSatu);
        }
    }
}
