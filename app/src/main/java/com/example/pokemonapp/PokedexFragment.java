package com.example.pokemonapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonapp.PokedexAdapter;
import com.example.pokemonapp.ApiClient;
import com.example.pokemonapp.PokemonApiService;
import com.example.pokemonapp.Pokemon;
import com.example.pokemonapp.PokemonResponse;

import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PokedexFragment extends Fragment {

    private RecyclerView recyclerView;
    private PokedexAdapter pokedexAdapter;
    private List<Pokemon> pokedexList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pokedex, container, false);

        // Inicializar RecyclerView y Adaptador
        recyclerView = rootView.findViewById(R.id.recycler_view_pokedex);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pokedexList = new ArrayList<>();
        pokedexAdapter = new PokedexAdapter(pokedexList, pokemon -> {
            // Acción al capturar un Pokémon (por ejemplo, agregarlo a Firebase)
            capturePokemon(pokemon);
        });
        recyclerView.setAdapter(pokedexAdapter);

        // Llamar a la API
        fetchPokemonList();

        return rootView;
    }

    private void fetchPokemonList() {
        Retrofit retrofit = ApiClient.getRetrofitInstance();
        PokemonApiService apiService = retrofit.create(PokemonApiService.class);

        apiService.getPokemonList(0, 150).enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PokemonResult> results = response.body().getResults();

                    for (PokemonResult result : results) {
                        pokedexList.add(new Pokemon(
                                result.getName(),
                                "Details unavailable",
                                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + extractIdFromUrl(result.getUrl()) + ".png"
                        ));
                    }


                    pokedexAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to load Pokémon list", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private int extractIdFromUrl(String url) {
        String[] segments = url.split("/");
        return Integer.parseInt(segments[segments.length - 1]);
    }

    private void capturePokemon(Pokemon pokemon) {
        Log.d("CAPTURE_POKEMON", "Starting capturePokemon method...");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> pokemonData = new HashMap<>();
        pokemonData.put("name", pokemon.getName());
        pokemonData.put("details", pokemon.getDetails());
        pokemonData.put("image", pokemon.getImage());

        Log.d("CAPTURE_POKEMON", "Data prepared: " + pokemonData.toString());

        db.collection("captured_pokemons")
                .document(pokemon.getName())
                .set(pokemonData)
                .addOnSuccessListener(aVoid -> {
                    Log.d("CAPTURE_POKEMON", "Pokemon saved successfully!");
                    Toast.makeText(getContext(), pokemon.getName() + " captured and saved!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.e("CAPTURE_POKEMON", "Error saving Pokémon: ", e);
                    Toast.makeText(getContext(), "Failed to save " + pokemon.getName(), Toast.LENGTH_SHORT).show();
                });

        Log.d("CAPTURE_POKEMON", "Capture method finished.");
    }


}
