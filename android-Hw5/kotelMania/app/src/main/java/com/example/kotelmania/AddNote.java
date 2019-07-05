package com.example.kotelmania;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNote extends AppCompatActivity{

    public Button send;
    public EditText title;
    public EditText content;
    public int id = 0;
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        id = getIntent().getIntExtra("lastId", id+1);

        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = database.getReference(currentUser).child("size");

        title = (EditText) findViewById(R.id.NoteTitle);
        content = (EditText) findViewById(R.id.NoteText);
        send = (Button) findViewById(R.id.sendNote);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _title = title.getText().toString();
                if(_title .isEmpty()){
                    title.setError("Title can't be empty");
                    return;
                }

                String _content = content.getText().toString();
                if(_content.isEmpty()){
                    content.setError("Content can't be empty");
                    return;
                }

                String noteId = String.valueOf(id);
                String stat = "Send";
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String dateToSend = dateFormat.format(date);

                String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                database.getReference(currentUser).child("notes")
                        .child("note-"+noteId).setValue(new Note(noteId, _title, _content, stat, dateToSend));

                database.getReference(currentUser).child("size").setValue(id);

                Intent intent = new Intent(getApplicationContext(), listActivity.class);
                startActivity(intent);
                finishActivity(1);
            }
        });
    }
}