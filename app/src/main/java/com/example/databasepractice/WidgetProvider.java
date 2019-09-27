package com.example.databasepractice;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WidgetProvider extends AppWidgetProvider {
    int k = 0;
    private SharedPreferences mSharedPrefs;
    private SharedPreferences.Editor mEditor;
    DatabaseHandler myDb;


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            myDb = new DatabaseHandler(context);
            int dL = myDb.length(); //database's length (number of rows)
            int counterForStop = 0; //if this counter exceeds the dL, that means theres no data left

            mSharedPrefs = context.getSharedPreferences("sharedPrefs",0); //Initialize the SharedPreference variable
            mEditor = mSharedPrefs.edit();

            k = mSharedPrefs.getInt("counter",1); // k=1 the first time, next time k will be increased
            Log.d("TEST k after first time", "" + k);
            Toast.makeText(context, "Widget has been updated! ", Toast.LENGTH_SHORT).show();
            DatabaseHandler myDb = new DatabaseHandler(context);
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setOnClickPendingIntent(R.id.button, pendingIntent);


            //mSharedPrefs.getInt("counter,0") should return 1
            while (myDb.getData(mSharedPrefs.getInt("counter",0)) == "null") {
                counterForStop++;
                Log.d("CounterForStop", "" + counterForStop);
                if (counterForStop>=dL) { //If no more data then reset the list to word(1)
                    mEditor.putInt("counter",1);
                    mEditor.apply();
                    k=mSharedPrefs.getInt("counter",0);
                    Log.d("Counter after if loop", "" + k);
                    break;
                }
                //if the there is no data at column, increase counter
                Log.d("TEST if k is increased", "" + mSharedPrefs.getInt("counter",0));
                mEditor.putInt("counter",k+1);
                mEditor.apply();
                k++;
            }

            views.setCharSequence(R.id.button, "setText", myDb.getData(k)); //k=1
            mEditor.putInt("counter",k+1); //increase counter for next update
            mEditor.apply();
            Log.d("TEST TEST", "" + mSharedPrefs.getInt("counter",0));
            appWidgetManager.updateAppWidget(appWidgetId, views);

        }
    }

}


    /* @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        DatabaseHandler myDb = new DatabaseHandler(context);

        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setOnClickPendingIntent(R.id.button, pendingIntent);
            views.setCharSequence(R.id.button,"setText",myDb.getData(1));
            appWidgetManager.updateAppWidget(appWidgetId,views);
        }
    }*/

    /*  @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            ++i;
            k=i;
            Toast.makeText(context, "Widget has been updated! ", Toast.LENGTH_SHORT).show();
            DatabaseHandler myDb = new DatabaseHandler(context);
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setOnClickPendingIntent(R.id.button, pendingIntent);

            while (myDb.getData(i) == "null") {
                i++;
                k++;
            }

            views.setCharSequence(R.id.button,"setText",myDb.getData(k));
            appWidgetManager.updateAppWidget(appWidgetId,views);

        }
    }*/


    /*
}*/