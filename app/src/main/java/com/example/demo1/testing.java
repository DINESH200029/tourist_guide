package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class testing extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner school;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        setTitle("Testing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        school=findViewById(R.id.school);
        ArrayAdapter<CharSequence> sadapter=ArrayAdapter.createFromResource(this,R.array.schools,android.R.layout.simple_spinner_item);
        sadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        school.setAdapter(sadapter);
        school.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int select_item_pos, long l) {

        if(select_item_pos==0){
            //do none
        }
        else
            Toast.makeText(adapterView.getContext(),adapterView.getItemAtPosition(select_item_pos).toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void onClick1(View v){
        Intent i=new Intent(testing.this,smrt_skool.class);
        startActivity(i);
    }
    public void onClick4(View v){
        Intent i=new Intent(testing.this,tSmartCollege.class);
        startActivity(i);
    }
    public void onClick2(View v){
        Intent i=new Intent(testing.this,smrt_parent.class);
        startActivity(i);
    }
    public void onClick5(View v){
        Intent i=new Intent(testing.this,tSmartStudent.class);
        startActivity(i);
    }
    public void onClick3(View v){
        Intent i=new Intent(testing.this,smrt_class.class);
        startActivity(i);
    }
    public void onClick6(View v){
        Intent i=new Intent(testing.this,tSmartBus.class);
        startActivity(i);
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
