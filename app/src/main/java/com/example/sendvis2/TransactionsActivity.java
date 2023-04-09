package com.example.sendvis2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

public class TransactionsActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        mDatabase = FirebaseDatabase.getInstance(getResources().getString(R.string.database_url)).getReference();
        DatabaseReference balanceReference = mDatabase.child("groups");

        Button settle = findViewById(R.id.btn_settle_group);
        TextView amount = findViewById(R.id.activity_transactions_amount);

        settle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                balanceReference.child("2").child("owedSum").setValue("0");
                biometricCheck();
                amount.setText("0.00");


            }
        });
    }

    private void biometricCheck() {
        Executor executor;
        BiometricPrompt biometricPrompt;
        BiometricPrompt.PromptInfo promptInfo;
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(TransactionsActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

//                Intent intent = new Intent(TransactionsActivity.this, MainActivity.class);
//                startActivity(intent);
            }
            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

//                Intent intent = new Intent(TransactionsActivity.this, MainActivity.class);
//                startActivity(intent);

            }
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();

//                Intent intent = new Intent(TransactionsActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }
}