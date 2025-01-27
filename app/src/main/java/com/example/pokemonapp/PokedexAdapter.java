package com.example.pokemonapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PokedexAdapter extends RecyclerView.Adapter<PokedexAdapter.ViewHolder> {

    private final List<Pokemon> pokemonList;
    private final OnPokemonClickListener listener;

    public interface OnPokemonClickListener {
        void onPokemonClick(Pokemon pokemon);
    }

    public PokedexAdapter(List<Pokemon> pokemonList, OnPokemonClickListener listener) {
        this.pokemonList = pokemonList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pokemon_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);

        holder.nameTextView.setText(pokemon.getName());
        holder.detailsTextView.setText(pokemon.getDetails());

        Glide.with(holder.imageView.getContext())
                .load(pokemon.getImage())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> listener.onPokemonClick(pokemon));
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, detailsTextView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.pokemon_name); // Actualizado al ID del XML
            detailsTextView = itemView.findViewById(R.id.pokemon_details); // Actualizado al ID del XML
            imageView = itemView.findViewById(R.id.pokemon_image); // Actualizado al ID del XML
        }
    }
}
