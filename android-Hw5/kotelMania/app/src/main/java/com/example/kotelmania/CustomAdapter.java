package com.example.kotelmania;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Note> noteList;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, ArrayList<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.row_listview, null);
        TextView title = (TextView) view.findViewById(R.id.textview_title);
        TextView content = (TextView) view.findViewById(R.id.textview_note);
        TextView date = (TextView) view.findViewById(R.id.textview_date);
        TextView status = (TextView) view.findViewById(R.id.textview_status);

        title.setText(noteList.get(i).getTitle());
        content.setText(noteList.get(i).getContent());
        date.setText(noteList.get(i).getDate());
        status.setText(noteList.get(i).getStatus());

        return view;
    }
}
