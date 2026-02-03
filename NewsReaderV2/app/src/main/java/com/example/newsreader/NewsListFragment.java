package com.example.newsreader;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class NewsListFragment extends Fragment {
    private static final String ARG_NEWS_LIST = "news_list";

    public interface OnNewsSelectedListener {
        void onNewsSelected(News news);
    }

    private OnNewsSelectedListener listener;

    // METODO CORRETTO PER PASSARE DATI
    public static NewsListFragment newInstance(ArrayList<News> list) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_NEWS_LIST, list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnNewsSelectedListener) {
            listener = (OnNewsSelectedListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ListView listView = new ListView(getContext());

        // Recuperiamo i dati dagli Arguments
        ArrayList<News> newsList = new ArrayList<>();
        if (getArguments() != null) {
            newsList = getArguments().getParcelableArrayList(ARG_NEWS_LIST);
        }

        if (newsList == null) newsList = new ArrayList<>();

        String[] titoli = new String[newsList.size()];
        for(int i=0; i<newsList.size(); i++) titoli[i] = newsList.get(i).getTitolo();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, titoli);
        listView.setAdapter(adapter);

        ArrayList<News> finalNewsList = newsList;
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (listener != null) listener.onNewsSelected(finalNewsList.get(position));
        });

        return listView;
    }
}
