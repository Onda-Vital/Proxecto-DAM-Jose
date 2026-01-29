package com.example.proyecto_modular;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
