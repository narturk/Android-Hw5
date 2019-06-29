package com.example.kotelmania;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class listActivity extends AppCompatActivity {

    public Button add, exit, donate;
    public FirebaseDatabase database;
    public ListView listView;
    public ArrayList<Note> peteks = new ArrayList<>();
    public CustomAdapter adapter;
    public int lastId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        database = FirebaseDatabase.getInstance();
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = database.getReference(currentUser).child("notes");


        ref.keepSynced(true);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lastId = checkDB(dataSnapshot);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView = (ListView) findViewById(R.id.NoteList);
        adapter = new CustomAdapter(this,peteks);
        listView.setAdapter(adapter);

        add = (Button) findViewById(R.id.AddNote);

        Log.d(null, "onCreate: lastId: "+lastId);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(v.getContext(), AddNote.class);
                intent2.putExtra("lastId",lastId);
                startActivity(intent2);
                finishActivity(1);
            }
        });

        exit = (Button) findViewById(R.id.signOut);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                Intent intent3 = new Intent(v.getContext(), myLogin.class);
                startActivity(intent3);
                finishActivity(1);
            }
        });
        donate = (Button) findViewById(R.id.Donate);
    }

    public int checkDB(DataSnapshot dataSnapshot){
        if(dataSnapshot.exists()){
            GenericTypeIndicator<Map<String, Note>> g =
                    new GenericTypeIndicator<Map<String, Note>>(){};
            HashMap<String, Note> dataMap = (HashMap<String, Note>) dataSnapshot.getValue(g);
            peteks.clear();
            for (String key : dataMap.keySet()) {
                Log.d(null, "checkDB: " + key);
                peteks.add(dataMap.get(key));
            }

            return peteks.size()+1;
//            Log.d(null, "checkDB: lastid: "+lastId);
        }

        return 1;
    }
}
