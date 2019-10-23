package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class login_page extends AppCompatActivity {

    EditText id, pass;
    String s_id, s_pass;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        setTitle("Customer Support Login");

        id = findViewById(R.id.login_id);
        pass = findViewById(R.id.pass);

    }


    public void log_in(View v) {

        ConnectivityManager manager =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            readFirebase();
        else
            Toast.makeText(login_page.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
    }

    FirebaseFirestore db=FirebaseFirestore.getInstance();
    public void readFirebase(){

        s_id=id.getText().toString().trim();
        s_pass=pass.getText().toString().trim();

        DocumentReference users = db.collection("users").document(s_id);
        users.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task< DocumentSnapshot > task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if(doc.exists()){
                        if(s_pass.equals(doc.get("password"))){
                            i = new Intent(login_page.this, seknd_scrn.class);
                            i.putExtra("login_id",s_id);
                            i.putExtra("logged","1");
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(login_page.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(login_page.this,"Invalid User Id",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    public void add(View v) {

        startActivity(new Intent(login_page.this,create_acct.class));
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}