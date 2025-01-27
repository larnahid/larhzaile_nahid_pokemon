package com.example.pokemonapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore.setLoggingEnabled(true);

        // Obtiene el FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Encuentra la barra de navegación inferior
        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);

        // Encuentra el NavHostFragment desde el FragmentContainerView
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.nav_host_fragment);

        // Verifica que navHostFragment no sea nulo
        if (navHostFragment != null) {
            // Vincula el NavController con la barra de navegación inferior
            NavigationUI.setupWithNavController(navView, navHostFragment.getNavController());
        } else {
            throw new IllegalStateException("NavHostFragment no encontrado. Asegúrate de que el ID nav_host_fragment existe en activity_main.xml");
        }
    }
}
