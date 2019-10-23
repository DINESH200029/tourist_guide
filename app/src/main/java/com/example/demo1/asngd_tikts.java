package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.room.Room;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.concurrent.CountDownLatch;

public class asngd_tikts extends AppCompatActivity {

    LinearLayout[] l = new LinearLayout[10];
    LinearLayout[] cardinl = new LinearLayout[10];
    LinearLayout[] cl = new LinearLayout[10];
    LinearLayout[] rl = new LinearLayout[2];
    CardView[] c = new CardView[10];
    TextView[] t = new TextView[50];
    TextView[] t1 = new TextView[50];
    CardView[] pass = new CardView[10];
    LinearLayout pl;
    String[] s1 = {"date", "ticket_id", "subject", "description", "status"};
    String status = "unassigned";
    String[] s = {"Date", "Ticket No", "Subject", "Description", "Attachment"};
    String tickets[] = new String[25];
    ProgressDialog progress;
    LinearLayout.LayoutParams llp, params_cl_head, params_cl_data;
    CardView.LayoutParams lp;
    FirebaseFirestore db;
    DocumentReference users;

    int n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asngd_tikts);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Unassigned Tickets");
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(false).build();

        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        progress.show();


        llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.MATCH_PARENT);
        params_cl_head = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params_cl_head.weight = 1;
        params_cl_data = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params_cl_data.weight = 1;

        db = FirebaseFirestore.getInstance();
        db.collection("tickets").whereEqualTo("status", "unassigned").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> queryDocumentSnapshots) {
                String[] tiktId = new String[50];
                if (queryDocumentSnapshots.isSuccessful()) {
                    for(DocumentSnapshot doc:queryDocumentSnapshots.getResult().getDocuments()) {
                        tiktId[n] = doc.getId();
                        n++;
                    }
                    setTiktId(tiktId);
                } else
                    Toast.makeText(asngd_tikts.this, "nothing", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setTiktId(String[] doc) {
        pl = findViewById(R.id.pl);

        for (int i = 0; i < n; i++) {
            l[i] = new LinearLayout(this);
            c[i] = new CardView(this);
            cardinl[i] = new LinearLayout(this);
            c[i].setLayoutParams(lp);
            l[i].setLayoutParams(llp);
            l[i].setPadding(10, 10, 10, 10);
            cardinl[i].setOrientation(LinearLayout.VERTICAL);
            rl[0] = new LinearLayout(this);
            rl[1] = new LinearLayout(this);
            rl[0].setOrientation(LinearLayout.VERTICAL);
            rl[1].setOrientation(LinearLayout.VERTICAL);
            cl[i] = new LinearLayout(this);
            c[i].setId(i);
            rl[0].setLayoutParams(params_cl_head);
            rl[1].setLayoutParams(params_cl_data);
            for (int j = 0; j < 5; j++) {
                rl[0].setGravity(Gravity.LEFT);
                rl[1].setGravity(Gravity.LEFT);
                t[j] = new TextView(this);
                t1[i*5+j] = new TextView(this);
                t[j].setText(s[j]);
                t[j].setTypeface(null, Typeface.BOLD);


                users = db.collection("tickets").document(doc[i]);
                final int finalJ = j;
                final int finalI1 = i;
                users.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            if (doc.exists()) {
                                t1[finalI1 *5+finalJ].setText(doc.get(s1[finalJ]).toString());


                            }
                            else {
                                progress.dismiss();
                                Toast.makeText(asngd_tikts.this, "No Document", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progress.dismiss();
                                Toast.makeText(asngd_tikts.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                            }
                        });
                x:while(t1[i *5+j].getText().equals(null))
                    continue x;
                rl[0].addView(t[j]);
                rl[1].addView(t1[i*5+j]);
            }

            cl[i].setPadding(5, 5, 5, 5);
            cl[i].addView(rl[0]);
            cl[i].addView(rl[1]);
            final int finalI = i;
            c[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pass[finalI] = findViewById(view.getId());
                    Intent i = new Intent(asngd_tikts.this, ticket_intent.class);
                    i.putExtra("t_no", ((TextView) (((LinearLayout) (((LinearLayout) pass[finalI].getChildAt(0)).getChildAt(1))).getChildAt(1))).getText().toString());
                    i.putExtra("status", "inprogress");
                    startActivity(i);
                }
            });
            c[i].addView(cl[i]);
            l[i].addView(c[i]);
            pl.addView(l[i]);
        }
        progress.dismiss();

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