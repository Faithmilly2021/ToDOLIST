package com.faith.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText fgemail;
    Button fgbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        fgemail = findViewById(R.id.forget_email);
        fgbtn = findViewById(R.id.forget_btn);

        fgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = fgemail.getText().toString().trim();

                sendOTPToEmail(email);


                Intent intent = new Intent(ForgetPasswordActivity.this, SecurityActivity.class);
                startActivity(intent);
            }
        });
    }

    private void sendOTPToEmail(String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            // OTP email sent successfully
                            Toast.makeText(ForgetPasswordActivity.this, "Reset link has sent to your email", Toast.LENGTH_SHORT).show();
                        } else {
                            // Failed to send OTP email
                            Toast.makeText(ForgetPasswordActivity.this, "Failed to send Link", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
