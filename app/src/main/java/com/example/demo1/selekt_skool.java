package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Query;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class selekt_skool extends AppCompatActivity  {

    Spinner school;
    TextView date,sname,eid,phone,lattitude,longitude,address;

    ProgressDialog progress;
    List<CharSequence> skool=new ArrayList<>();
    FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(false).build();

    FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selekt_skool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Select School");

        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        progress.show();

        sname = findViewById(R.id.sname);
        eid = findViewById(R.id.eid);
        phone = findViewById(R.id.phone);
        lattitude = findViewById(R.id.lat);
        longitude = findViewById(R.id.longitude);
        address = findViewById(R.id.address);
        date = findViewById(R.id.date);

        db.collection("school").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                skool.add("Select School Name");
                for (QueryDocumentSnapshot document : documentSnapshots)
                    if (document.exists()) {
                        skool.add(document.getId().trim());
                    }
                    else
                        Toast.makeText(selekt_skool.this, "No Such School Exists", Toast.LENGTH_SHORT).show();
                    progress.dismiss();

                school = findViewById(R.id.school);
                ArrayAdapter<CharSequence> sadapter = new ArrayAdapter<CharSequence>(selekt_skool.this, android.R.layout.simple_spinner_item, skool);
                sadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                school.setAdapter(sadapter);
                school.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int select_item_pos, long l) {

                        progress = new ProgressDialog(selekt_skool.this);
                        progress.setTitle("Loading");
                        progress.setMessage("Wait while loading...");
                        progress.setCancelable(false);
                        progress.show();
                        if(select_item_pos==0) {

                            date.setText(" ");
                            sname.setText(" ");
                            eid.setText(" ");
                            phone.setText(" ");
                            lattitude.setText(" ");
                            longitude.setText(" ");
                            address.setText(" ");
                            progress.dismiss();
                        }
                        else
                        {

                            String name = (String) adapterView.getItemAtPosition(select_item_pos);
                            showSchool(name);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }
        });
    }

        public void showSchool(final String school){

            DocumentReference users = db.collection("school").document(school);
            users.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {

                        progress.dismiss();
                        DocumentSnapshot doc = task.getResult();
                        if (doc.exists()) {
                            longitude.setText(doc.get("longitude").toString());
                            lattitude.setText(doc.get("lattitude").toString());
                            date.setText(doc.get("date").toString());
                            eid.setText(doc.get("email_id").toString());
                            phone.setText(doc.get("phone").toString());
                            address.setText(doc.get("address").toString());
                            sname.setText(school);
                        } else
                            Toast.makeText(selekt_skool.this, "No Such School Exists", Toast.LENGTH_SHORT).show();
                    }
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progress.dismiss();
                            Toast.makeText(selekt_skool.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    });
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