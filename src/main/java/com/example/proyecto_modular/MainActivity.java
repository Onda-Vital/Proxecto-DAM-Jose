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

        Apires api = new Apires();

        btn.setOnClickListener(v -> {
            boolean flag = true;

            if (nome.getText().toString().trim().isEmpty()){
                nome.setError("Obrigatorio");
                flag = false;
            }
            if (contrasinal.getText().toString().trim().isEmpty()){
                contrasinal.setError("Obrigatorio");
                flag = false;
            }

            if (flag){
                String nombre = nome.getText().toString();
                String correo = contrasinal.getText().toString();

                Intent intent = new Intent(MainActivity.this, Principal.class);
                startActivity(intent);
                finish();
            }else{
                return;
            }
        });
        TextView txtSubir = findViewById(R.id.textView2);
        txtSubir.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, IniciarSesion.class);
            startActivity(intent);
            finish();
        });






    }
}