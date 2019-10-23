package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

public class seknd_scrn extends AppCompatActivity {

    String lid;
    CardView c1,c2,c3,c4,c5,c6,c7,c8;
    String logged="0";
    SharedPreferences settings;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seknd_scrn);
        setTitle("HOME");

        c1=findViewById(R.id.c1);
        c2=findViewById(R.id.c2);
        c3=findViewById(R.id.c3);
        c4=findViewById(R.id.c4);
        c5=findViewById(R.id.c5);
        c6=findViewById(R.id.c6);
        c7=findViewById(R.id.c7);
        c8=findViewById(R.id.c8);

        logged=getIntent().getStringExtra("logged");
        lid=getIntent().getStringExtra("login_id");

        if(logged!=null) {
            settings= PreferenceManager.getDefaultSharedPreferences(seknd_scrn.this);
            editor= settings.edit();
            editor.putString("logged",logged);
            editor.commit();
        }
        else {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
            logged= settings.getString("logged", "0");
        }
        if(logged.equals("0"))
            startActivity(new Intent(seknd_scrn.this,login_page.class));

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(seknd_scrn.this,UserPofile.class);
                //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("login_id",lid);
                i.putExtra("logged",logged);
                startActivity(i);
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(seknd_scrn.this,selekt_skool.class));
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(seknd_scrn.this,asngd_tikts.class);
                startActivity(i);
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(seknd_scrn.this,inprgrs_tikts.class);
                startActivity(i);
            }
        });
        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(seknd_scrn.this,closed_tikts.class);
                startActivity(i);
            }
        });
        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(seknd_scrn.this,pilot_skools.class);
                startActivity(i);
            }
        });
        c7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(seknd_scrn.this,testing.class);
                startActivity(i);
            }
        });

    }
    public void onClick(View view) {
        Intent i=new Intent(seknd_scrn.this,login_page.class);
        logged="0";
        settings= PreferenceManager.getDefaultSharedPreferences(seknd_scrn.this);
        editor= settings.edit();
        editor.putString("logged","0");
        editor.commit();
        startActivity(i);
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
