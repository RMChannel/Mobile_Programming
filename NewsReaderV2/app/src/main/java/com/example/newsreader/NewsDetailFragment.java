package com.example.newsreader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewsDetailFragment extends Fragment {
    private static final String ARG_NEWS = "arg_news";

    public static NewsDetailFragment newInstance(News news) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NEWS, news);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(android.R.layout.simple_list_item_2, container, false);
        TextView title = view.findViewById(android.R.id.text1);
        TextView body = view.findViewById(android.R.id.text2);

        if (getArguments() != null) {
            News news = getArguments().getParcelable(ARG_NEWS);
            if (news != null) {
                title.setText(news.getTitolo());
                body.setText(news.getTesto());
            }
        }
        return view;
    }
}

