package com.example.kotelmania;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class listActivity extends AppCompatActivity {

    public ListView lst;
    public DBHelper dbHelper;
    private ArrayList<Note> noteList;
    Button btn, btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        noteList = new ArrayList<Note>();
        dbHelper = DBHelper.getHelper(this);
        Cursor ret = dbHelper.getAllData();

        checkDate();
        while(ret.moveToNext()){
            String[] columns = ret.getColumnNames();

            int id=0;
            String title="";
            String content="";
            String status="";
            String date="";

            for(int i=0; i< columns.length; i++){
                switch(i){
                    case 0: id = Integer.parseInt(ret.getString(i));
                    case 1: title = ret.getString(i);
                    case 2: content = ret.getString(i);
                    case 3: status = ret.getString(i);
                    case 4: date = ret.getString(i);
                }
            }
            Note insertNote = new Note(id,title, content, status, date);
            noteList.add(insertNote);
        }

        lst = (ListView) findViewById(R.id.NoteList);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(),noteList);
        lst.setAdapter(customAdapter);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(view.getContext(), EditNote.class);
                intent1.putExtra("index", position);
                startActivityForResult(intent1, position);
                finishActivity(1);
            }
        });
        btn = findViewById(R.id.AddNote);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(v.getContext(), AddNote.class);
                startActivity(intent2);
                finishActivity(1);
            }
        });
        btn2 = findViewById(R.id.Donate);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(v.getContext(), DonateActivity.class);
                startActivity(intent3);
                finishActivity(1);
            }
        });
    }

    public void checkDate(){
        Cursor cur = dbHelper.getAllData();

        DateFormat dateFormat = new SimpleDateFormat("dd");
        Date day = new Date();
        String DateToCheck = dateFormat.format(day);

        while(cur.moveToNext()){
            String str  = cur.getString(4);
            String[] array = str.split("/");
            if(Integer.parseInt(array[0])+2 <= Integer.parseInt(DateToCheck)){
                dbHelper.updateData(cur.getInt(0), cur.getString(1), cur.getString(2), "received",cur.getString(4));
            }
        }


    }
}
