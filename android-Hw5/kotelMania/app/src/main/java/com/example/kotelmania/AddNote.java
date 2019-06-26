package com.example.kotelmania;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddNote extends AppCompatActivity{
    public Button btn;
    public EditText title;
    public EditText note;
    public static int id = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        Log.d(null, "onCreate: Add note");
        title = (EditText) findViewById(R.id.NoteTitle);
        note = (EditText) findViewById(R.id.NoteText);
        btn = (Button) findViewById(R.id.AddNote);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                addNote(title.getText().toString(), note.getText().toString());
            }
        });
        Log.d(null, "onCreate: Add note2");
    }

    public void addNote(String title, String note){
        int noteId = id;
        id++;
        String stat = "Send";
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String dateToSend = dateFormat.format(date);

        DBHelper dbHelper = DBHelper.getHelper(this);
        dbHelper.insertData(noteId, title, note, stat, dateToSend);

        Intent intent = new Intent(this, listActivity.class);
        startActivity(intent);
    }
}
