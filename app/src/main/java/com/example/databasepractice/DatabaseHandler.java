package com.example.databasepractice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class DatabaseHandler extends SQLiteOpenHelper  {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "fre_dict.db";
    public static final String TABLE_NAME = "french_dictionary";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_WORD= "WORD";
    public static final String COLUMN_DEF= "DEF";

    SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = " CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_WORD + " TEXT NOT NULL, " +
                COLUMN_DEF + " TEXT NOT NULL)";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String word, String def) {
        db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_WORD,word);
        contentValues.put(COLUMN_DEF,def);
        long result = db.insert(TABLE_NAME,null,contentValues);

        if (result==-1) return false;
        else return true;
    }

    public String getData(int i) {
        StringBuffer buffer = new StringBuffer();
        db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = "+ i, null);
        while (res.moveToNext()) {
            String word = res.getString(1);
            String definition = res.getString(2);
            buffer.append(word);
            buffer.append(" ");
            buffer.append(definition);
        }

        if (buffer.toString().length()==0) {
            return "null";
        }
        return buffer.toString();
    }

    public Cursor getWord(int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = "+ i, null);
        return cursor;
    }

    public ArrayList<Dictionary> getAllData() {
        ArrayList<Dictionary> arrayList = new ArrayList<>();
        db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        while (res.moveToNext()) {
            int id = res.getInt(0);
            String word = res.getString(1);
            String def = res.getString(2);
            Dictionary dictionary = new Dictionary(word, def,id);

            arrayList.add(dictionary);

        }
        return arrayList;
    }

    public Cursor getItemId(Long COLID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME +" WHERE " + COLUMN_ID+ " = " + COLID;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public void update(String newWord,  int id, String oldWord, String newDef, String oldDef) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryWord = "UPDATE " + TABLE_NAME + " SET " + COLUMN_WORD +
                " = '" + newWord + "' WHERE " + COLUMN_ID + " = '" + id + "'" +
                " AND " + COLUMN_WORD + " = '" + oldWord + "'";

        String queryDef = "UPDATE " + TABLE_NAME + " SET " + COLUMN_DEF +
                " = '" + newDef+ "' WHERE " + COLUMN_ID + " = '" + id + "'" +
                " AND " + COLUMN_DEF + " = '" + oldDef + "'";

        Log.d("DatabaseHandler", "updateName: query: " + queryWord + queryDef);
        Log.d("DatabaseHandler", "updateName: Setting name to " + newWord );
        db.execSQL(queryWord);
        db.execSQL(queryDef);

    }

    public void delete(int id, String word, String def){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COLUMN_ID + " = '" + id + "'" +
                " AND " + COLUMN_WORD + " = '" + word + "'" + " AND " + COLUMN_DEF + " = '" + def +"'";
        Log.d("DatabaseHandler", "deleteName: query: " + query);
        Log.d("DatabaseHandler", "deleteName: Deleting " + word + " from database.");
        db.execSQL(query);
    }

    public boolean doesTableExist(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

}
