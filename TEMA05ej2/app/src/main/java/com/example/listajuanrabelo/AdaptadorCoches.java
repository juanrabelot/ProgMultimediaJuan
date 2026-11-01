package com.example.listajuanrabelo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorCoches extends RecyclerView.Adapter<AdaptadorCoches.ViewHolder> {

    private ArrayList<Coche> listaCoches;

    public AdaptadorCoches(ArrayList<Coche> listaCoches) {
        this.listaCoches = listaCoches;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewCoche;
        TextView textViewModelo;
        TextView textViewMarca;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewCoche = itemView.findViewById(R.id.imageViewCoche);
            textViewModelo = itemView.findViewById(R.id.textViewModelo);
            textViewMarca = itemView.findViewById(R.id.textViewMarca);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fila_coche,
                        parent,
                        false);

        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        Coche coche = listaCoches.get(position);

        holder.textViewModelo.setText(coche.getModelo());
        holder.textViewMarca.setText(coche.getMarca());
        holder.imageViewCoche.setImageResource(coche.getImagen());
    }

    @Override
    public int getItemCount() {
        return listaCoches.size();
    }
}