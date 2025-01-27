package com.example.pokemonapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CapturedPokemonFragment extends Fragment {

    private RecyclerView recyclerView;
    private CapturedPokemonAdapter adapter;
    private List<Pokemon> pokemonList;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Cambiar a fragment_captured_pokemon
        View rootView = inflater.inflate(R.layout.fragment_captured_pokemon, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_view_captured_pokemon);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        pokemonList = new ArrayList<>();
        adapter = new CapturedPokemonAdapter(pokemonList, this::showPokemonDetails);
        recyclerView.setAdapter(adapter);

        // Cargar los datos desde Firebase
        //loadCapturedPokemons();

        setupItemTouchHelper();

        return rootView;
    }

    private void setupItemTouchHelper() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Pokemon pokemon = pokemonList.get(position);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("captured_pokemons").document(pokemon.getName())
                        .delete()
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(getContext(), pokemon.getName() + " deleted!", Toast.LENGTH_SHORT).show();
                            pokemonList.remove(position);
                            adapter.notifyItemRemoved(position);
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getContext(), "Failed to delete " + pokemon.getName(), Toast.LENGTH_SHORT).show();
                            adapter.notifyItemChanged(position);
                        });
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void showPokemonDetails(Pokemon pokemon) {
        // Navegar al fragmento de detalles
        PokemonDetailFragment fragment = PokemonDetailFragment.newInstance(pokemon);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.recycler_view_captured_pokemon, fragment) // Cambiar al ID correcto
                .addToBackStack(null)
                .commit();
    }


}
