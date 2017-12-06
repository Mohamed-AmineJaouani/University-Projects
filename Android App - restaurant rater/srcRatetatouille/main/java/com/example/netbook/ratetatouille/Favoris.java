package com.example.netbook.ratetatouille;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Favoris extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.favoris_item);
        listView.setAdapter(arrayAdapter);

        SharedPreferences pref = getSharedPreferences("RESTAURANT FAVORIS", Context.MODE_PRIVATE);
        int cpt_Restaurant = pref.getInt("cpt_favoris", 0);

        for(int i = 1 ; i <= cpt_Restaurant; i++){
            arrayAdapter.add(pref.getString("nom" + i, "null:" + i) + " " + pref.getString("adresse" + i, "null:" + i));
        }
    }
    //quand on affiche le simpleCursorAdapter on verifie si le nom et ladresse sont dans lespreferences si oui on change licon
}