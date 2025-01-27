package com.example.pokemonapp;

import java.util.List;

public class PokemonResponse {
    private List<PokemonResult> results;

    public List<PokemonResult> getResults() {
        return results;
    }

    public void setResults(List<PokemonResult> results) {
        this.results = results;
    }
}

class PokemonResult {
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
