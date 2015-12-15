package com.jixstreet.spaace.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jixstreet.spaace.R;


public class EditProfileFragment extends Fragment {

    private String LOG_TAG = EditProfileFragment.class.getName();
    private View view;

    public static EditProfileFragment newInstance(Context context) {
        return new EditProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_edit_profile, container, false);


        return view;
    }

    private void initUI(){

    }
}