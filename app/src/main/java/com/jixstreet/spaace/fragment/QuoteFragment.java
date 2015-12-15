package com.jixstreet.spaace.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jixstreet.spaace.R;


public class QuoteFragment extends Fragment {

    private String LOG_TAG = QuoteFragment.class.getName();

    public static QuoteFragment newInstance(Context context) {
        return new QuoteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quote, container, false);

        return view;
    }
}