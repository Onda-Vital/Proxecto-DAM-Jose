package com.example.proyecto_modular;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.TweetVH> {

    private final ArrayList<Tweet> lista;

    public TweetAdapter(ArrayList<Tweet> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public TweetVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tweet, parent, false);
        return new TweetVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TweetVH holder, int position) {
        Tweet t = lista.get(position);

        holder.txtUser.setText(t.getUserName());
        holder.txtHandle.setText(t.getHandel());
        holder.txtContent.setText(t.getText());

        holder.imgAvatar.setImageResource(t.getAvatarResId());

        // Imaxe do tweet (opcional)
        if (t.getImageResId() != null) {
            holder.imgTweet.setVisibility(View.VISIBLE);
            holder.divider2.setVisibility(View.VISIBLE);
            holder.divider.setVisibility(View.GONE);
            holder.imgTweet.setImageResource(t.getImageResId());
        } else {
            holder.imgTweet.setVisibility(View.GONE);
            holder.divider2.setVisibility(View.GONE);
            holder.divider.setVisibility(View.VISIBLE);
        }

        Button btnDelete = holder.itemView.findViewById(R.id.btnDelete);

        String usernameSesion = holder.itemView.getContext()
                .getSharedPreferences("session", Context.MODE_PRIVATE)
                .getString("username", "");

// Só amosar se é teu
        if (t.getUserName().equals(usernameSesion)) {
            btnDelete.setVisibility(View.VISIBLE);
        } else {
            btnDelete.setVisibility(View.GONE);
        }

// Acción borrar
        btnDelete.setOnClickListener(v -> {

            Apires.borrarTweet(t.getId(), usernameSesion, (Activity) holder.itemView.getContext(),
                    new Apires.DeleteTweetCallback() {
                        @Override
                        public void onOk() {
                            lista.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(holder.itemView.getContext(), "Borrado", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String msg) {
                            Toast.makeText(holder.itemView.getContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class TweetVH extends RecyclerView.ViewHolder {

        ImageView imgAvatar, imgTweet;
        TextView txtUser, txtHandle, txtContent;

        View divider, divider2;

        public TweetVH(@NonNull View itemView) {
            super(itemView);
            imgAvatar  = itemView.findViewById(R.id.imageView2);
            imgTweet   = itemView.findViewById(R.id.imageView3);
            txtUser    = itemView.findViewById(R.id.nomeusuario);
            txtHandle  = itemView.findViewById(R.id.textView6);
            txtContent = itemView.findViewById(R.id.contidodotweet);
            divider = itemView.findViewById(R.id.divider);
            divider2 = itemView.findViewById(R.id.divider2);

        }
    }
}
