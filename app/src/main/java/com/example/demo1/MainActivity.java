package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    boolean logged;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                i=new Intent(MainActivity.this,seknd_scrn.class);
                startActivity(i);


                SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                boolean isFirstRun = wmbPreference.getBoolean("FIRST_RUN", true);
                if (isFirstRun)
                {
                    logged=false;
                    SharedPreferences.Editor editor = wmbPreference.edit();
                    editor.putBoolean("FIRST_RUN", false);
                    editor.commit();

                    if(!logged){
                        Intent i=new Intent(MainActivity.this,login_page.class);
                        i.putExtra("logged",logged);
                        startActivity(i);
                    }
                }
            }
        },2000);

    }

}
