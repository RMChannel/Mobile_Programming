package com.example.newsreader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<News> notizie;

    public MyAdapter(List<News> notizie) {
        this.notizie=notizie;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        News n=notizie.get(position);
        holder.titoloTextView.setText(n.getTitolo());
        holder.testoTextView.setText(n.getTesto());
    }

    @Override
    public int getItemCount() {
        return notizie.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titoloTextView;
        TextView testoTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            titoloTextView = itemView.findViewById(R.id.textViewTitolo);
            testoTextView = itemView.findViewById(R.id.textViewTesto);
        }
    }
}
