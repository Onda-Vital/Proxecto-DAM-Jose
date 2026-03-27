package com.example.proyecto_modular;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class IniciarSesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_iniciar_sesion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btn1 = findViewById(R.id.btnNovaConta);
        EditText  nomereal = findViewById(R.id.etNome);
        EditText nomeusuario = findViewById(R.id.etUsuario);
        EditText correo = findViewById(R.id.etCorreo);
        EditText contrasinal = findViewById(R.id.etContrasinal);



        btn1.setOnClickListener(v -> {
            boolean flag = true;
            if (nomereal.getText().toString().trim().isEmpty()){
                flag = false;
                nomereal.setError("Obrigatorio");
            }
            if (nomeusuario.getText().toString().trim().isEmpty()){
                flag = false;
                nomeusuario.setError("Obrigatorio");
            }
            if (correo.getText().toString().trim().isEmpty()){
                flag = false;
                correo.setError("Obrigatorio");
            }
            if (contrasinal.getText().toString().trim().isEmpty()){
                flag = false;
                contrasinal.setError("Obrigatorio");
            }

            if (flag){
                String username = nomeusuario.getText().toString().trim();
                String email = correo.getText().toString().trim();
                String password = contrasinal.getText().toString(); // trim opcional en password
                String displayName = nomereal.getText().toString().trim();


                Apires.subirCorreo(email, username, password, displayName);
                getSharedPreferences("session", MODE_PRIVATE)
                        .edit()
                        .putString("username", username)
                        .apply();

                try {

                    Intent intent = new Intent(IniciarSesion.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    e.getLocalizedMessage();
                }

            }else {
                return;
            }

        });

        TextView txtSubir = findViewById(R.id.tvXaTeñoConta);
        txtSubir.setOnClickListener(v -> {
            Intent intent = new Intent(IniciarSesion.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}