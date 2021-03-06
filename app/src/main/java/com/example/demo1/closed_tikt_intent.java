package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class closed_tikt_intent extends AppCompatActivity {

    String tick;
    String status_prev;
    ProgressDialog progress;
    TextView t_no,date,Cstatus,name,email,phone,subject,desc,attachment;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closed_tikt_intent);
        setTitle("Ticket Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tick = String.valueOf(getIntent().getStringExtra("t_no"));
        status_prev = getIntent().getStringExtra("status");

        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        progress.show();

        showTikts(tick);

        attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(closed_tikt_intent.this,pdf_veiwer.class);
                i.putExtra("document",attachment.getText());
                startActivity(i);
            }
        });
    }

    FirebaseFirestore db=FirebaseFirestore.getInstance();
    public void showTikts(final String tikt_id){

        setContentView(R.layout.activity_closed_tikt_intent);
        setTitle("Ticket Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        t_no = findViewById(R.id.t_no);
        Cstatus=findViewById(R.id.Cstatus);
        date = findViewById(R.id.date);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        subject = findViewById(R.id.subject);
        desc = findViewById(R.id.desc);
        attachment = findViewById(R.id.attachment);


        DocumentReference users = db.collection("tickets").document(tikt_id);
        users.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        t_no.setText(tikt_id);
                        Cstatus.setText(doc.get("status").toString());
                        date.setText(doc.get("date").toString());
                        name.setText(doc.get("name").toString());
                        email.setText(doc.get("email_id").toString());
                        phone.setText(doc.get("phone").toString());
                        subject.setText(doc.get("subject").toString());
                        desc.setText(doc.get("description").toString());
                        attachment.setText("doc1");
                    }
                    else {
                        progress.dismiss();
                        Toast.makeText(closed_tikt_intent.this, "No Document", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progress.dismiss();
                        Toast.makeText(closed_tikt_intent.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                });
        progress.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            onBackPressed();
        else {
            Intent i = new Intent(this, seknd_scrn.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}

