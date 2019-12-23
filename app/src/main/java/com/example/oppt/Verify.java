package com.example.oppt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Verify extends AppCompatActivity {

    TextView countdownn;
    EditText secVerifyId;
    Button SigninId, recendcodeID;
    ProgressBar progressBar;
    private CountDownTimer countDownTimer;
    private long timeLeftMillis;
    private static final long countdown_in_milis = 10000;
    private static final String Key_milis_left = "keymillisLeft";
    String CodeSend, phoneNumber;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBarId);
        countdownn = findViewById(R.id.textcountId);
        secVerifyId = findViewById(R.id.secVerifyId);
        SigninId = findViewById(R.id.SigninId);
        recendcodeID = findViewById(R.id.recendcodeID);

        final Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("Mobile");
        sendVerifycode();
        timeLeftMillis = countdown_in_milis;
        startCountdown();

        SigninId.setOnClickListener(new View.OnClickListener() {


            public void onClick(View view) {
                String code = secVerifyId.getText().toString().trim();
                if (!code.isEmpty() && code.length() == 6) {

                    verifySinginCode();
                } else {

                    Toast.makeText(Verify.this, "verification code doesn't match", Toast.LENGTH_SHORT).show();
                }
            }
        });
        recendcodeID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerifycode();
                timeLeftMillis = countdown_in_milis;
                startCountdown();
                recendcodeID.setVisibility(View.GONE);
            }
        });


    }


    private void startCountdown() {
        countDownTimer = new CountDownTimer(timeLeftMillis, 1000) {
            @Override
            public void onTick(long lon) {
                timeLeftMillis = lon;
                updateCoundown();

            }

            @Override
            public void onFinish() {
                timeLeftMillis = 0;
                updateCoundown();
                recendcodeID.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void updateCoundown() {
        int min = (int) (timeLeftMillis / 1000) / 60;
        int sec = (int) (timeLeftMillis / 1000) % 60;
        String timeFor = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
        countdownn.setText(timeFor);


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser uder = mAuth.getCurrentUser();
        if (uder != null) {
            sendTo();
            //  finish();
        }


    }

    private void sendTo() {

        Intent intent = new Intent(Verify.this, To.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    // fr
    private void sendVerifycode() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+88" +
                        phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }


    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            CodeSend = s;
        }
    };

    private void verifySinginCode() {
        progressBar.setVisibility(View.VISIBLE);
        String code = secVerifyId.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(CodeSend, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    sendTo();
                    finish();
                } else {

                    progressBar.setVisibility(View.GONE);
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(Verify.this, "Invalid Verification Code", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }


}
