package com.example.demo1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class tSmartCollege extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_smart_college);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void popup(View v){

        final EditText ta=new EditText(tSmartCollege.this);
        final EditText t=findViewById(v.getId());
        AlertDialog.Builder ad= new AlertDialog.Builder(tSmartCollege.this);
        ad.setTitle("Enter Comments");
        ad.setView(ta);
        ta.setText(t.getText().toString());
        ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                t.setText(ta.getText().toString());
            }
        });
        ad.show();
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
