package com.example.minimo2_dsa;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minimo2_dsa.Models.Repositorio;
import com.squareup.picasso.Picasso;


import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolderData> {

    List<Repositorio> listaRepos;


    public DataAdapter(List<Repositorio> listaRepos) {

        this.listaRepos = listaRepos;

    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);

        return new ViewHolderData(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderData holder, int position) {

        holder.assignData(listaRepos.get(position));


    }

    @Override
    public int getItemCount() {
        return listaRepos.size();
    }

    public class ViewHolderData extends RecyclerView.ViewHolder {


        TextView repositorio;
        TextView lenguaje;

        public ViewHolderData(@NonNull View itemView) {
            super(itemView);


            repositorio = (TextView) itemView.findViewById(R.id.repoName);
            lenguaje = (TextView) itemView.findViewById(R.id.language);

        }

        public void assignData(Repositorio repositorioN) {


            repositorio.setText(repositorioN.getName());
            lenguaje.setText(repositorioN.getLanguage());

        }
    }
}
