package com.example.kotelmania;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class EditNote extends AppCompatActivity {

    public TextView title_tv;
    public TextView content_tv;
    public Button btn1;
    public Button btn2;
    private int id=0;
    private String title="";
    private String content="";
    private String status="";
    private String date="";
    public ArrayList<Note> peteks = new ArrayList<>();
    public FirebaseDatabase database;
    public int index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        Intent intent = getIntent();
        index = intent.getIntExtra("index", 0);

        database = FirebaseDatabase.getInstance();
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = database.getReference(currentUser).child("notes");

        title_tv = (TextView) findViewById(R.id.edit_NoteTitle);
        content_tv = (TextView) findViewById(R.id.edit_NoteText);
        btn1 = (Button) findViewById(R.id.edit_AddNote);
        btn2 = (Button) findViewById(R.id.edit_return);

        ref.keepSynced(true);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    GenericTypeIndicator<Map<String, Note>> g =
                            new GenericTypeIndicator<Map<String, Note>>(){};
                    HashMap<String, Note> dataMap = (HashMap<String, Note>) dataSnapshot.getValue(g);
                    peteks.clear();
                    int size = dataMap.keySet().size();
                    for (int i=1; i<= size; i++) {
                        peteks.add(dataMap.get("note-"+i));
                    }

                    title_tv.setText(peteks.get(index).title);
                    content_tv.setText(peteks.get(index).content);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = title_tv.getText().toString();
                content = content_tv.getText().toString();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String dateToSend = dateFormat.format(date);
                String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                database.getReference(currentUser).child("notes")
                        .child("note-"+(index+1)).setValue(new Note(Integer.toString((index+1)), title, content, "send", dateToSend));
                Intent intent1 = new Intent(v.getContext(), listActivity.class);
                startActivity(intent1);
                finishActivity(1);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(v.getContext(), listActivity.class);
                startActivity(intent2);
                finishActivity(1);
            }
        });
    }
}
