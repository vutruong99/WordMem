package com.example.databasepractice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DictAdapter extends ArrayAdapter<Dictionary> {
    private static final String TAG = "DictAdapter";

    private Context mContext;
    int mResource;
    public DictAdapter(Context context, int resource, ArrayList<Dictionary> objects) {
        super(context, resource, objects);
        mContext= context;
        mResource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       String word = getItem(position).getWord();
       String def = getItem(position).getDef();
       int id = getItem(position).getId();
       Dictionary dictionary = new Dictionary(word, def,id);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tv1 = (TextView)convertView.findViewById(R.id.textView1);
        TextView tv2 = (TextView)convertView.findViewById(R.id.textView2);

        tv1.setText(word);
        tv2.setText(def);

        return convertView;
    }


}
