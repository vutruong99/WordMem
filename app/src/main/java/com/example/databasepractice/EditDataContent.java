package com.example.databasepractice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditDataContent extends AppCompatActivity {
    EditText editWord, editDef;
    Button btnDelete;
    Button btnSave;
    DatabaseHandler myDb;

    int id;
    String word, def;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data_layout);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        editDef = (EditText) findViewById(R.id.editable_definition);
        editWord = (EditText) findViewById(R.id.editable_word);
        myDb = new DatabaseHandler(this);

        Intent receivedIntent = getIntent();

        id = receivedIntent.getIntExtra("id", -1);
        word = receivedIntent.getStringExtra("word");
        def = receivedIntent.getStringExtra("definition");

        editWord.setText(word);
        editDef.setText(def);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newWord = editWord.getText().toString();
                String newDef = editDef.getText().toString();
                if (!newWord.equals("") && !newDef.equals("")) {
                    myDb.update(newWord,id,word,newDef,def);
                } else {
                    Toast.makeText(EditDataContent.this, "Null value",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.delete(id, word, def);
                editDef.setText("");
                editWord.setText("");
            }
        });


    }


}
