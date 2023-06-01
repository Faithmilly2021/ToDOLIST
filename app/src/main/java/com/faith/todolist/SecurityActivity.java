package com.faith.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SecurityActivity extends AppCompatActivity {
    Button securityBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        securityBtn = findViewById(R.id.Security_btn);

        securityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to AddAccountActivity
                Intent intent = new Intent(SecurityActivity.this, AddAccountActivity.class);
                startActivity(intent);
            }
        });
    }

}
