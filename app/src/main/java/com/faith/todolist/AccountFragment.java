package com.faith.todolist;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AccountFragment extends Fragment {
    FloatingActionButton floatingActionButton;
    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_account, container, false);
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        TextView addAccountTextView = view.findViewById(R.id.add_account_textview);
        TextView manageAccountTextView = view.findViewById(R.id.manage_account_textview);


        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setVisibility(View.GONE);


        addAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddAccountScreen();

            }
        });

        manageAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openManageAccountScreen();
            }
        });

        return view;
    }


    private void openAddAccountScreen() {
        // Implement the logic to open the Add Account screen
        // This could be done by starting a new activity or navigating to another fragment
        // Example:
         Intent intent = new Intent(getActivity(), AddAccountActivity.class);
         startActivity(intent);
    }

    private void openManageAccountScreen() {
        // Implement the logic to open the Manage Account screen
        // This could be done by starting a new activity or navigating to another fragment
        // Example:
        // FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        // FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // fragmentTransaction.replace(R.id.fragment_container, new ManageAccountFragment());
        // fragmentTransaction.addToBackStack(null);
        // fragmentTransaction.commit();
    }
}