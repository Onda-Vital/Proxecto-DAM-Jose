package com.example.proyecto_modular;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Principal extends AppCompatActivity {

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

        RecyclerView recycler = findViewById(R.id.deslizar);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.hide();
        fab.setOnClickListener(v -> recycler.smoothScrollToPosition(0));



        recycler.setLayoutManager(new LinearLayoutManager(this));


        RecyclerView.Adapter adapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>() {

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                TextView tv = new TextView(parent.getContext());
                tv.setPadding(32, 32, 32, 32);
                return new RecyclerView.ViewHolder(tv) {};
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ((TextView) holder.itemView).setText("Item " + position);
            }

            @Override
            public int getItemCount() {
                return 50;
            }
        };

        recycler.setAdapter(adapter);



        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {

                if (dy > 0) {
                    // Estás baixando
                    fab.show();
                } else if (dy < 0) {
                    // Estás subindo
                    fab.hide();
                }
            }
        });

        ImageView perfil = findViewById(R.id.perfil);

        perfil.setOnClickListener(v -> {
            Intent intent = new Intent(Principal.this, paginaperfil.class);
            startActivity(intent);

        });

        recycler.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Tweet> tweets = new ArrayList<>();
        tweets.add(new Tweet("1", "Meu", "@meu", "Non quero pilotar",
                R.drawable.shinji, null, 0, 0));
        tweets.add(new Tweet("2", "Gendo Ikari", "@Comandante", "Shinji subete ao Eva.",
                R.drawable.ic_launcher_background, R.drawable.gendoikair, 12, 3));

        TweetAdapter adaptado = new TweetAdapter(tweets);
        recycler.setAdapter(adaptado);




    }
}