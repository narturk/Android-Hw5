package com.example.kotelmania;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DonateActivity extends AppCompatActivity {
    int sum = 0, sum_to_add;
    Button btn;
    TextView textview;
    EditText editText;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            sum_to_add = sp.getInt("SUM", 0);
            sum += sum_to_add;
        }catch (ClassCastException e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

        editText = findViewById(R.id.sum);
        textview = findViewById(R.id.ShowSum);
        textview.setText(String.valueOf(sum));
        btn = findViewById(R.id.AddSum);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    sum_to_add = Integer.parseInt(editText.getText().toString());
                }catch (NumberFormatException e){
                    Toast.makeText(DonateActivity.this, "Enter only numbers!", Toast.LENGTH_SHORT).show();
                    return;
                }
                sum += sum_to_add;
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("SUM", sum);
                editor.commit();
                textview.setText(String.valueOf(sum));
                Toast.makeText(DonateActivity.this, "Bless You", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), listActivity.class);
                startActivity(intent);
                finishActivity(1);
            }
        });
    }
}
