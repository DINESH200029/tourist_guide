package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class UserPofile extends AppCompatActivity {

    TextView l_id,fname,lname,eid,phone,address;
    String login_id;
    ImageView pic;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("User Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        progress.show();

        login_id=getIntent().getStringExtra("login_id");
        String logged=getIntent().getStringExtra("logged");

        if(login_id!=null) {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("id",login_id);
            editor.commit();
        }
        else {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
            login_id= settings.getString("id", "0");
        }
        if(login_id.equals("0") || logged.equals("0")){
            Intent i= new Intent(UserPofile.this,login_page.class);
            startActivity(i);
        }

        displayProfile();
    }


    FirebaseFirestore db=FirebaseFirestore.getInstance();
    public void displayProfile(){

        DocumentReference users = db.collection("users").document(login_id);
        users.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task< DocumentSnapshot > task) {
                if (task.isSuccessful()) {
                    setContentView(R.layout.activity_user_pofile);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    setTitle("User Profile");
                    l_id=findViewById(R.id.id);
                    pic=findViewById(R.id.pic);
                    fname=findViewById(R.id.fname);
                    lname=findViewById(R.id.lname);
                    eid=findViewById(R.id.eid);
                    phone=findViewById(R.id.phone);
                    address=findViewById(R.id.address);
                    pic.setImageResource(R.drawable.user_icon);
                    progress.dismiss();

                    DocumentSnapshot doc = task.getResult();
                    address.setText(doc.get("address").toString());
                    l_id.setText(login_id);
                    fname.setText(doc.get("fname").toString());
                    lname.setText(doc.get("lname").toString());
                    eid.setText(doc.get("email_id").toString());
                    phone.setText(doc.get("phone").toString());
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progress.dismiss();
                        Toast.makeText(UserPofile.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
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
