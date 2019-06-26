package com.example.kotelmania;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EditNote extends AppCompatActivity {

    public DBHelper dbHelper;
    public TextView title_tv;
    public TextView content_tv;
    public Button btn1;
    public Button btn2;
    private int id=0;
    private String title="";
    private String content="";
    private String status="";
    private String date="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        Intent intent = getIntent();
        int index = intent.getIntExtra("index", 0);

        dbHelper = DBHelper.getHelper(this);
        Cursor ret = dbHelper.getAllData();


        String[] columns = ret.getColumnNames();
        while (ret.moveToNext()){
            Log.d(null, "onCreate: db index="+ret.getString(0));
            if(ret.getInt(0) == index+1 ) {
                for (int i = 0; i < columns.length; i++) {
                    switch (i) {
                        case 0:
                            id =ret.getInt(i);
                        case 1:
                            title = ret.getString(i);
                        case 2:
                            content = ret.getString(i);
                        case 3:
                            status = ret.getString(i);
                        case 4:
                            date = ret.getString(i);
                    }
                }
            }
        }

        Log.d(null, "onCreate: index="+index+" dbIndex="+id);
        Log.d(null, "onCreate: title="+title);
        Log.d(null, "onCreate: note="+content);

        title_tv = (TextView) findViewById(R.id.edit_NoteTitle);
        content_tv = (TextView) findViewById(R.id.edit_NoteText);
        btn1 = (Button) findViewById(R.id.edit_AddNote);
        btn2 = (Button) findViewById(R.id.edit_return);

        title_tv.setText(title);
        content_tv.setText(content);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = title_tv.getText().toString();
                content = content_tv.getText().toString();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String dateToSend = dateFormat.format(date);
                dbHelper.updateData(id, title, content, status, dateToSend);
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
