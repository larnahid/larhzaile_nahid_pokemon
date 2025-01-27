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

public class CapturedPokemonAdapter extends RecyclerView.Adapter<CapturedPokemonAdapter.PokemonViewHolder> {

    private final List<Pokemon> pokemonList;
    private final OnPokemonClickListener listener;

    public CapturedPokemonAdapter(List<Pokemon> pokemonList, OnPokemonClickListener listener) {
        this.pokemonList = pokemonList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon_card, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);
        holder.name.setText(pokemon.getName());
        holder.details.setText(pokemon.getDetails());
        Glide.with(holder.itemView.getContext()).load(pokemon.getImage()).into(holder.image);

        holder.itemView.setOnClickListener(v -> listener.onPokemonClick(pokemon));
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public interface OnPokemonClickListener {
        void onPokemonClick(Pokemon pokemon);
    }

    public static class PokemonViewHolder extends RecyclerView.ViewHolder {
        TextView name, details;
        ImageView image;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.pokemon_name);
            details = itemView.findViewById(R.id.pokemon_details);
            image = itemView.findViewById(R.id.pokemon_image);
        }
    }
}
