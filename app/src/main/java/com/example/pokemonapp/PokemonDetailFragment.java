package com.example.pokemonapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class PokemonDetailFragment extends Fragment {

    private static final String ARG_POKEMON = "pokemon";
    private Pokemon pokemon;

    public static PokemonDetailFragment newInstance(Pokemon pokemon) {
        PokemonDetailFragment fragment = new PokemonDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_POKEMON, pokemon);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pokemon = (Pokemon) getArguments().getSerializable(ARG_POKEMON);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pokemon_detail, container, false);

        // Vincular los elementos de la interfaz
        ImageView pokemonImage = rootView.findViewById(R.id.pokemon_image);
        TextView pokemonName = rootView.findViewById(R.id.pokemon_name);
        TextView pokemonDetails = rootView.findViewById(R.id.pokemon_details);

        // Configurar los datos del Pokémon
        if (pokemon != null) {
            pokemonName.setText(pokemon.getName());
            pokemonDetails.setText(pokemon.getDetails());

            Glide.with(this)
                    .load(pokemon.getImage())
                    .into(pokemonImage);
        }

        return rootView;
    }
}
