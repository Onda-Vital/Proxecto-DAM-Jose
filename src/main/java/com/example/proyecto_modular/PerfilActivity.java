package com.example.proyecto_modular;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PerfilActivity extends AppCompatActivity {

    private TextView tvUsername, tvEmail, tvDisplayName;
    private Button btnPecharSesion, btnBorrarConta;

    private long userId;
    private String username, email, displayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        tvDisplayName = findViewById(R.id.tvDisplayName);
        btnPecharSesion = findViewById(R.id.btnPecharSesion);
        btnBorrarConta = findViewById(R.id.btnBorrarConta);

        SharedPreferences prefs = getSharedPreferences("session", MODE_PRIVATE);
        userId = prefs.getLong("id", -1);
        username = prefs.getString("username", "");
        email = prefs.getString("email", "");
        displayName = prefs.getString("display_name", "");

        tvUsername.setText("Usuario: " + username);
        tvEmail.setText("Correo: " + email);
        tvDisplayName.setText("Nome: " + displayName);

        btnPecharSesion.setOnClickListener(v -> {
            prefs.edit().clear().apply();
            Intent i = new Intent(PerfilActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        });

        btnBorrarConta.setOnClickListener(v -> mostrarConfirmacion());
    }

    private void mostrarConfirmacion() {
        new AlertDialog.Builder(this)
                .setTitle("Borrar conta")
                .setMessage("Estás seguro? Esta acción non se pode desfacer.")
                .setPositiveButton("Si", (dialog, which) -> borrarConta())
                .setNegativeButton("Non", null)
                .show();
    }

    private void borrarConta() {
        if (userId == -1) {
            Toast.makeText(this, "Non hai sesión iniciada", Toast.LENGTH_SHORT).show();
            return;
        }

        Apires.borrarUsuario(userId, this, new Apires.DeleteUserCallback() {
            @Override
            public void onOk() {
                getSharedPreferences("session", MODE_PRIVATE).edit().clear().apply();

                Toast.makeText(PerfilActivity.this, "Conta borrada correctamente", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(PerfilActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(PerfilActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}