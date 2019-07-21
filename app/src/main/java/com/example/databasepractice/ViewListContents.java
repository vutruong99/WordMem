package com.example.databasepractice;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewListContents extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);

        ListView listView = (ListView) findViewById(R.id.listview);
        final DatabaseHandler myDb = new DatabaseHandler(this);

        //ArrayList<String> list = new ArrayList<>();
          ArrayList<Dictionary> list = new ArrayList<>();
          list = myDb.getAllData();

          final DictAdapter adapter = new DictAdapter(this, R.layout.adapterview_layout, list);
          listView.setAdapter(adapter);

          listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> DictAdapter, View view, int position, long id) {
                  Dictionary key = (Dictionary) DictAdapter.getItemAtPosition(position);

                  Cursor data = myDb.getItemId(Long.valueOf(key.getId()));
                  int itemID = -1;
                  String word= " ";
                  String def= " ";
                  while (data.moveToNext()) {
                      itemID= data.getInt(0);
                      word=data.getString(1);
                      def=data.getString(2);
                  }

                  if (itemID > -1) {
                      Intent editIntent = new Intent(ViewListContents.this,EditDataContent.class);
                      editIntent.putExtra("id",itemID);
                      editIntent.putExtra("word",word);
                      editIntent.putExtra("definition",def);
                      startActivity(editIntent);
                  } else {
                      Log.d("VIEWLISTCONTENT","FAIL TO FIND WORD");
                  }
              }

          });




          //Cursor res = myDb.getData();

        /*if (res.getCount()==0) {
            Toast.makeText(ViewListContents.this, "Data base empty",Toast.LENGTH_LONG).show();
        } else {
            while (res.moveToNext()) {
                list.add(res.getString(1));
                list.add(res.getString(2));
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,list);
                listView.setAdapter(listAdapter);
            }
        }*/
    }
}
