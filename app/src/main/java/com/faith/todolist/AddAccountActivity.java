package com.faith.todolist;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;



import org.checkerframework.checker.nullness.qual.NonNull;

public class AddAccountActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private TextView loginTextView, loginForgetPassword;
    private Button loginButton;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        emailEditText = findViewById(R.id.Login_email);
        passwordEditText = findViewById(R.id.Login_password);
        loginTextView = findViewById(R.id.create_account);
        loginForgetPassword = findViewById(R.id.login_forget_password);
        loginButton = findViewById(R.id.Login_btn);
        firebaseAuth = FirebaseAuth.getInstance();

     passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Sign in with email and password
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(AddAccountActivity.this, task -> {
                            if (task.isSuccessful()) {
                               FirebaseUser user = firebaseAuth.getCurrentUser();
                               Intent intent = new Intent(AddAccountActivity.this, DashboardActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(AddAccountActivity.this, "Login failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                            }

                        });
            }
        });

        loginForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAccountActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });


        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open RegistrationActivity
                Intent intent = new Intent(AddAccountActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });


    }

}

