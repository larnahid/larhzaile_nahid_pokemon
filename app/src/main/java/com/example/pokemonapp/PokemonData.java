package com.example.pokemonapp;

import java.util.ArrayList;
import java.util.List;

public class PokemonData {
    public static List<Pokemon> getSamplePokemonList() {
        List<Pokemon> pokemonList = new ArrayList<>();
        pokemonList.add(new Pokemon("Pikachu", "Tipo electrico", "https://example.com/pikachu.png"));
        pokemonList.add(new Pokemon("Charmander", "Tipo fuego", "https://example.com/charmander.png"));

        return pokemonList;
    }
}
