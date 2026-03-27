package com.example.proyecto_modular;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Principal extends AppCompatActivity {

    private RecyclerView recycler;
    private FloatingActionButton fab;

    private ArrayList<Tweet> tweets;
    private TweetAdapter adaptado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal2);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recycler = findViewById(R.id.deslizar);
        fab = findViewById(R.id.floatingActionButton2);

        // LayoutManager só unha vez
        LinearLayoutManager lm = new LinearLayoutManager(this);
        recycler.setLayoutManager(lm);

        // Datos + adapter real
        tweets = new ArrayList<>();
        tweets.add(new Tweet("1", "Meu", "@meu", "Non quero pilotar",
                R.drawable.shinji, null, 0, 0));
        tweets.add(new Tweet("2", "Gendo Ikari", "@Comandante", "Shinji subete ao Eva.",
                R.drawable.ic_launcher_background, R.drawable.gendoikair, 12, 3));


        cargarTimeline();
        adaptado = new TweetAdapter(tweets);
        recycler.setAdapter(adaptado);

        // FAB: ir arriba
        fab.setOnClickListener(v -> recycler.smoothScrollToPosition(0));
        fab.hide();

        // En vez de "dy", móstrase cando non estás arriba de todo
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                int firstVisible = lm.findFirstVisibleItemPosition();
                if (firstVisible > 2) {
                    fab.show();
                } else {
                    fab.hide();
                }
            }
        });

        // Ir a perfil
        ImageView perfil = findViewById(R.id.perfil);
        perfil.setOnClickListener(v -> startActivity(new Intent(Principal.this, PerfilActivity.class)));

        // Botón de novo tweet (subir)
        ImageView subir = findViewById(R.id.subir);
        subir.setOnClickListener(v -> mostrarPopupTweet());

        ImageView lupa = findViewById(R.id.lupa);
        lupa.setOnClickListener(v -> mostrarlupa());

        ImageView lootac = findViewById(R.id.lootboxac);

        lootac.setOnClickListener(v -> showLootboxPopup());
    }


    private void cargarTimeline() {
        Apires.getTimeline(new Apires.TweetsCallback() {
            @Override
            public void onOk(ArrayList<Tweet> descargados) {
                runOnUiThread(() -> {
                    tweets.clear();
                    tweets.addAll(descargados);
                    adaptado.notifyDataSetChanged();
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(() ->
                        Toast.makeText(Principal.this, "Erro cargando tweets", Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

    private void mostrarlupa(){
        View view = getLayoutInflater().inflate(R.layout.lupa, null);
        EditText input = view.findViewById(R.id.editTextText6);     Button btnPublicar = view.findViewById(R.id.publicar);
        Button btnCancelar = view.findViewById(R.id.cancelar);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .create();

        dialog.setCanceledOnTouchOutside(false);
        btnCancelar.setOnClickListener(v -> dialog.dismiss());

        btnPublicar.setOnClickListener(v ->{
            String texto = input.getText().toString().trim();

            if (texto.isEmpty()) {
                input.setError("Tes que pór o que queres procurar");
                return;
            }
            if (texto.length() > 280) {
                input.setError("Máximo 280 caracteres");
                return;
            }
            dialog.dismiss();
        });
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    private void mostrarPopupTweet() {
        View view = getLayoutInflater().inflate(R.layout.novotweet, null);

        EditText input = view.findViewById(R.id.editTextText6);
        Button btnPublicar = view.findViewById(R.id.publicar);
        Button btnCancelar = view.findViewById(R.id.cancelar);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .create();

        dialog.setCanceledOnTouchOutside(false);

        btnCancelar.setOnClickListener(v -> dialog.dismiss());

        btnPublicar.setOnClickListener(v -> {
            String texto = input.getText().toString().trim();

            if (texto.isEmpty()) {
                input.setError("Escribe algo antes de publicar");
                return;
            }
            if (texto.length() > 280) {
                input.setError("Máximo 280 caracteres");
                return;
            }

            String usernameSesion = getSharedPreferences("session", MODE_PRIVATE)
                    .getString("username", "");

            if (usernameSesion.isEmpty()) {
                Toast.makeText(this, "Non hai sesión iniciada", Toast.LENGTH_SHORT).show();
                return;
            }

            Apires.subirTweet(usernameSesion, "@" + usernameSesion, texto);

            dialog.dismiss();

            recycler.postDelayed(this::cargarTimeline, 500);
        });

        dialog.show();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }



    private void showLootboxPopup() {
        View v = getLayoutInflater().inflate(R.layout.lootboxes, null);

        ImageView ivBox = v.findViewById(R.id.ivBox);
        Button btnOpen = v.findViewById(R.id.btnOpen);
        TextView tvReward = v.findViewById(R.id.tvReward);
        ProgressBar pbLoading = v.findViewById(R.id.pbLoading);

        tvReward.setVisibility(View.GONE);
        pbLoading.setVisibility(View.GONE);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(v)
                .setCancelable(true)
                .create();

        btnOpen.setOnClickListener(view -> {
            btnOpen.setEnabled(false);
            pbLoading.setVisibility(View.VISIBLE);

            // Aquí chamas á API real
            long userId = 1; // substitúe polo teu userId gardado do login

            Apires.openLootbox(userId, this, new Apires.LootboxCallback() {
                @Override
                public void onOk(int opensLeft, int rewardCoins, int newBalance) {
                    pbLoading.setVisibility(View.GONE);

                    // caixa "aberta"
                    ivBox.setImageResource(R.drawable.ootbox_open);

                    tvReward.setVisibility(View.VISIBLE);
                    tvReward.setText("Gañaches " + rewardCoins + " moedas!\nSaldo: " + newBalance + "\nQuédanche: " + opensLeft + " hoxe.");

                }

                @Override
                public void onError(String msg) {
                    pbLoading.setVisibility(View.GONE);
                    btnOpen.setEnabled(true);
                    Toast.makeText(Principal.this, msg, Toast.LENGTH_SHORT).show();
                }
            });
        });

        dialog.show();
    }


}
