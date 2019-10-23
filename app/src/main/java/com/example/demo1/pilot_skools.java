package com.example.demo1;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class pilot_skools extends AppCompatActivity {

    Calendar cal;
    int month, day, year;
    EditText date, sname, eid, phone, lattitude, longitude, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilot_skools);
        setTitle("Add School");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sname = findViewById(R.id.sname);
        eid = findViewById(R.id.eid);
        phone = findViewById(R.id.phone);
        lattitude = findViewById(R.id.lat);
        longitude = findViewById(R.id.longitude);
        address = findViewById(R.id.address);
        date = findViewById(R.id.date);

        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                date.setText(day + "/" + (month + 1) + "/" + year);
            }
        });
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void schoolsFirebase() {

        final String s_name =sname.getText().toString();
        final String s_date = date.getText().toString();
        final String s_phone = phone.getText().toString();
        final String s_email_id = eid.getText().toString();
        final String s_lattitude = lattitude.getText().toString();
        final String s_longitude = longitude.getText().toString();
        final String s_address = address.getText().toString();

        Map<String, Object> newContact = new HashMap<>();
        newContact.put("school name", s_name);
        newContact.put("date", s_date);
        newContact.put("lattitude",s_lattitude);
        newContact.put("longitude",s_longitude);
        newContact.put("email_id", s_email_id);
        newContact.put("phone", s_phone);
        newContact.put("address", s_address);
        db.collection("school").document(s_name).set(newContact)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(pilot_skools.this, "School Registered", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(pilot_skools.this, "ERROR" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void submit(View v){

        if(sname.getText().toString()!=null) {
            schoolsFirebase();
            Toast.makeText(this, "School Added to Database", Toast.LENGTH_LONG).show();
            reset(v);
        }
        else
            Toast.makeText(this, "Please fill all the details", Toast.LENGTH_LONG).show();

    }

    public void reset(View v){

        date.setText("");
        sname.setText("");
        eid.setText("");
        phone.setText("");
        lattitude.setText("");
        longitude.setText("");
        address.setText("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i=new Intent(this,seknd_scrn.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }
}
