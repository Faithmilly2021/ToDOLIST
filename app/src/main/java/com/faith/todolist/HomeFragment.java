package com.faith.todolist;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class HomeFragment extends Fragment {

    FloatingActionButton floatingActionButton;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setVisibility(View.GONE);

        return view;
    }
}


        //return inflater.inflate(R.layout.fragment_home, container, false);


