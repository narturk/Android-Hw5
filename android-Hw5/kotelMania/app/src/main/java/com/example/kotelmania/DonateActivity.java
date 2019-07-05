package com.example.kotelmania;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DonateActivity extends AppCompatActivity {

    public Button btn;
    public TextView textview;
    public EditText editText;
    public FirebaseDatabase database;
    public int sum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        editText = findViewById(R.id.sum);
        textview = findViewById(R.id.ShowSum);
        btn = findViewById(R.id.AddSum);

        database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("donate");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    sum = dataSnapshot.getValue(Integer.class);
                    textview.setText(Integer.toString(sum));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    sum += Integer.parseInt(editText.getText().toString());
                }catch (NumberFormatException e){
                    Toast.makeText(DonateActivity.this, "Enter only numbers!", Toast.LENGTH_SHORT).show();
                    return;
                }

                ref.setValue(sum);
                textview.setText(String.valueOf(sum));
                Toast.makeText(DonateActivity.this, "Bless You", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), listActivity.class);
                startActivity(intent);
                finishActivity(1);
            }
        });
    }
}
