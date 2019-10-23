package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class create_acct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acct);
    }

    private static final String Login_id = "emp001";
    private static final String fname = "sunku";
    private static final String lname = "prashanth";
    private static final String phone = "0800808009";
    private static final String email_id = "prashanth@gmail.com";
    private static final String address = "Saroor Nagar" ;
    private static final String pass="qwerty";
    FirebaseFirestore db=FirebaseFirestore.getInstance();

    public void writeFirebase(){
        Map< String, Object > newContact = new HashMap< >();
        newContact.put("login_id",Login_id);
        newContact.put("fname", fname);
        newContact.put("lname",lname);
        newContact.put("email_id", email_id);
        newContact.put("phone", phone);
        newContact.put("address",address);
        newContact.put("password",pass);
        db.collection("users").document(Login_id).set(newContact)
                .addOnSuccessListener(new OnSuccessListener< Void >() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(create_acct.this, "User Registered",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(create_acct.this, "ERROR" + e.toString(),
                                Toast.LENGTH_SHORT).show();
                        Log.d("TAG", e.toString());
                    }
                });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(create_acct.this,seknd_scrn.class));
    }
}
