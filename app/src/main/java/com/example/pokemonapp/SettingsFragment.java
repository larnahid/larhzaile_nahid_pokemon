package com.example.pokemonapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.content.res.Configuration;

import android.app.AlertDialog;

import java.util.Locale;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private boolean isLanguageChanging = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button aboutButton = view.findViewById(R.id.btn_about);

        // Configurar el botón para mostrar el diálogo
        aboutButton.setOnClickListener(v -> showAboutDialog());

        TextView tvLogout = view.findViewById(R.id.tv_logout);
        tvLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(requireContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show();

            // Redirige al usuario a la pantalla de login
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            requireActivity().finish();
        });

        // Inicializar SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("AppSettings", MODE_PRIVATE);
        Switch switchLanguage = view.findViewById(R.id.switch_language);

        // Obtener idioma actual
        String currentLanguage = sharedPreferences.getString("Language", "es");
        boolean isEnglish = currentLanguage.equals("en");

        // Configurar el estado inicial del Switch
        switchLanguage.setChecked(isEnglish);

        // Listener para cambiar idioma
        switchLanguage.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String newLanguage = isChecked ? "en" : "es";
            if (!currentLanguage.equals(newLanguage)) {
                setLocale(newLanguage);
            }
        });
    }

    private void setLocale(String languageCode) {
        // Actualizar configuración de idioma
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.setLocale(locale);

        requireActivity().getResources().updateConfiguration(
                config,
                requireActivity().getResources().getDisplayMetrics()
        );

        // Guardar idioma en preferencias
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Language", languageCode);
        editor.apply();

        // Recargar la actividad para aplicar el cambio
        requireActivity().recreate();
    }

    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_about, null);

        builder.setView(dialogView)
                .setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }
}
