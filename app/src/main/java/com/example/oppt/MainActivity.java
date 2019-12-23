package com.example.oppt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText mobilenubber_id;
    CheckBox checkboxID;
    Button vefiryButtonId;
    boolean flag = false;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mobilenubber_id = findViewById(R.id.mobilenubber_id);
        checkboxID = findViewById(R.id.checkboxID);
        vefiryButtonId = findViewById(R.id.vefiryButtonId);
        firebaseAuth = FirebaseAuth.getInstance();

        vefiryButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mobile = mobilenubber_id.getText().toString().trim();
                Intent intent = new Intent(MainActivity.this, Verify.class);
                intent.putExtra("Mobile", mobile);
                startActivity(intent);

            }
        });

        checkboxID.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    String mobile = mobilenubber_id.getText().toString().trim();
                int lenth = mobilenubber_id.length();
                if (b) {

                    flag = true;
                    vefiryButtonId.setEnabled(!mobile.isEmpty() && lenth == 11 && flag == true);


                } else {

                    vefiryButtonId.setEnabled(false);
                    flag = false;
                }
                flag = false;


            }
        });
        mobilenubber_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String mobile = mobilenubber_id.getText().toString().trim();
                int lenth=mobilenubber_id.length();
                if (checkboxID.isChecked()){

                    checkboxID.setChecked(false);
                }
                vefiryButtonId.setEnabled(!mobile.isEmpty()&& lenth==11 && flag== true);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser= firebaseAuth.getCurrentUser();
        if (currentUser!=null){

            SendUserTo();



        }



    }

    private void SendUserTo() {

        Intent intent=new Intent(MainActivity.this,To.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
