package com.example.databasepractice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHandler myDb;
    EditText word;
    EditText def;
    Button add,view;
    SharedPreferences mSharedPrefs;
    SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myDb = new DatabaseHandler(this);
        word = (EditText) findViewById(R.id.word);
        def = (EditText) findViewById(R.id.def);
        add = (Button) findViewById(R.id.add);
        view = (Button) findViewById(R.id.viewall);


        addWord();
        //viewAll();


        //View list of words
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewListContents.class);
                startActivity(intent);
            }
        });

    }

    public String getshit() {
        myDb= new DatabaseHandler(this);
        Cursor res = myDb.getWord(1);
        return res.getString(0);
    }

    //Add new word to list
    public void addWord() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = myDb.insertData(word.getText().toString(),def.getText().toString());
                if (check==true) Toast.makeText(MainActivity.this, "Added",Toast.LENGTH_LONG).show();
                else Toast.makeText(MainActivity.this, "Failed to add word",Toast.LENGTH_LONG).show();
            }
        });
    }

    public int length() {
        return myDb.length();
    }
    /*public void viewAll() {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getData(1);
                if (res.getCount()==0) {
                    showMessage("Error","No data found");
                    return;
                } else {
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()) {
                        buffer.append("Id: " + res.getString(0) + "\n");
                        buffer.append("Word: " + res.getString(1) + "\n");
                        buffer.append("Definition: " + res.getString(2) + "\n\n");
                    }

                    showMessage("Query success",buffer.toString());
                }
            }
        });
    }*/

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.show();
    }
}
