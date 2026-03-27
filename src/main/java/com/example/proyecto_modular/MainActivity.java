package com.example.proyecto_modular;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




        Button btn = findViewById(R.id.button);
        EditText nome = findViewById(R.id.editTextText2);
        EditText contrasinal = findViewById(R.id.editTextTextPassword);


        Button bt;
        EditText etNome;
        EditText etCorreo;

        btn.setOnClickListener(v -> {
            String username = nome.getText().toString().trim();
            String password = contrasinal.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Apires.login(username, password, this, new Apires.LoginCallback() {
                @Override
                public void onLoginResult(Perfil usuario) {
                    getSharedPreferences("session", MODE_PRIVATE)
                            .edit()
                            .putLong("id", usuario.getId())
                            .putString("username", usuario.getUsername())
                            .putString("email", usuario.getEmail())
                            .putString("display_name", usuario.getDisplayName())
                            .apply();

                    Toast.makeText(MainActivity.this, "Login correcto", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(MainActivity.this, Principal.class);
                    startActivity(i);
                    finish();
                }

                @Override
                public void onError() {
                    Toast.makeText(MainActivity.this, "Usuario ou contrasinal incorrectos", Toast.LENGTH_SHORT).show();
                }
            });
        });

        TextView txtSubir = findViewById(R.id.textView2);
        txtSubir.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, IniciarSesion.class);
            startActivity(intent);
            finish();
        });






    }
}