package com.example.kotelmania;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper instance = null;
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_NOTES_TABLE_QUERY =
            new StringBuilder().append("CREATE TABLE ").append(Note.TABLE_NAME).append(" (").append(Note.COLUMN_ID).append(" INTEGER,").append(Note.COLUMN_TITLE).append(" TEXT,").append(Note.COLUMN_CONTENT).append(" TEXT,").append(Note.COLUMN_STATUS).append(" TEXT,").append(Note.COLUMN_DATE).append(" TEXT);").toString();

    public static synchronized DBHelper getHelper(Context context)
    {
        if (instance == null)
            instance = new DBHelper(context);

        return instance;
    }

    private DBHelper(Context context) {
        super(context, "db", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOTES_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(int ID, String title, String content, String status, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Note.COLUMN_ID, ID);
        contentValues.put(Note.COLUMN_TITLE, title);
        contentValues.put(Note.COLUMN_CONTENT, content);
        contentValues.put(Note.COLUMN_STATUS, status);
        contentValues.put(Note.COLUMN_DATE, date);
        long result = db.insert(Note.TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+Note.TABLE_NAME,null);
        return res;
    }

    public Cursor getIndexRow(int index){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+Note.TABLE_NAME+" where "+Note.COLUMN_ID+"='"+index+"'",null);
        return res;
    }

    public boolean updateData(int ID, String title, String content, String status, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Note.COLUMN_ID, ID);
        contentValues.put(Note.COLUMN_TITLE, title);
        contentValues.put(Note.COLUMN_CONTENT, content);
        contentValues.put(Note.COLUMN_STATUS, status);
        contentValues.put(Note.COLUMN_DATE, date);
        db.update(Note.TABLE_NAME, contentValues, "ID = ?",new String[] {String.valueOf(ID)});
        return true;
    }

    public Integer deleteData (int ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Note.TABLE_NAME, "ID = ?",new String[] {String.valueOf(ID)});
    }
}
