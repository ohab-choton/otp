package com.example.oppt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;

public class Update extends AppCompatActivity {

    EditText name, fname, mname, nid, add;
    Button addfire;
    TextView displayShow;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference request = db.getReference("User");
    private ValueEventListener eventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        name = findViewById(R.id.name_ID);
        fname = findViewById(R.id.Fateher_Id);
        mname = findViewById(R.id.ma_Id);
        nid = findViewById(R.id.Nid_id);
        add = findViewById(R.id.add_id);
        addfire = findViewById(R.id.submit_button_Id);
        displayShow = findViewById(R.id.display_id);


        addfire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e1 = name.getText().toString();
                String e2 = fname.getText().toString();
                String e3 = mname.getText().toString();
                String e4 = nid.getText().toString().trim();
                String e5 = add.getText().toString();
                if (!TextUtils.isEmpty(e1) && !TextUtils.isEmpty(e2) && !TextUtils.isEmpty(e3) && !TextUtils.isEmpty(e4) && !TextUtils.isEmpty(e5) && (e4.length() == 10 || e4.length() == 17)) {
                    //    AddData sendData = new AddData(e1, e2, e3, e4, e5);
                     //     request.setValue(sendData);  w d push

                    String datetime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                    AddData setData = new AddData(e1, e2, e3, e4, e5);
                    request.child(datetime).setValue(setData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(Update.this, "add successful", Toast.LENGTH_SHORT).show();
                                name.setText("");
                                fname.setText("");
                                mname.setText("");
                                nid.setText("");
                                add.setText("");
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Update.this, "Failed to Add data", Toast.LENGTH_SHORT).show();
                        }
                    });


                    //                   Toast.makeText(Update.this, "add successfull", Toast.LENGTH_SHORT).show();
//                    name.setText("");
//                    fname.setText("");
//                    mname.setText("");
//                    nid.setText("");
//                    add.setText("");
                } else {
                    Toast.makeText(Update.this, "error", Toast.LENGTH_SHORT).show();



        /*            Toast.makeText(Update.this, "error", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    fname.setText("");
                    mname.setText("");
                    nid.setText("");
                    add.setText(""); */


                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        eventListener = request.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data ="";
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()){

                    AddData addData= itemSnapshot.getValue(AddData.class);
                    String oName = addData.getName();
                    String fName= addData.getFname();
                    String mName=addData.getMname();
                    String nId=addData.getNid();
                    String add=addData.getAdd();


                    data= data+"name :"+oName+"\nfather :"+fName+"\nmother :"+mName+"\nnid :"+nId+"\n\n";
                }
                displayShow.setText(data);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}

