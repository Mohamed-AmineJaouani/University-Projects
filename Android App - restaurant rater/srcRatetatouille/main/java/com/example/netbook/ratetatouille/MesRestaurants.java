package com.example.netbook.ratetatouille;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MesRestaurants extends ListActivity {
    private ImageButton im;
    private TextView nom;
    private TextView adresse;
    private Cursor cursor;
    private SharedPreferences pref;
    private SharedPreferences.Editor prefEdit;
    private int cpt_favoris;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = getSharedPreferences("RESTAURANT FAVORIS", Context.MODE_PRIVATE);
        prefEdit = pref.edit();


        ContentResolver cr = getContentResolver();
        cursor = cr.query(Uri.parse("content://com.example.netbook.ratetatouillebasecontentprovider/tout"), null, null, null, null);
        if (cursor == null || !cursor.moveToFirst()) // si il n'y a aucun restaurant enregistré
            Toast.makeText(MesRestaurants.this, "Aucun restaurant enregistré", Toast.LENGTH_LONG).show();
        else {

            String[] from = new String[]{"nom", "adresse", "ville"};
            int[] to = new int[]{R.id.nomResto, R.id.adresseResto, R.id.villeResto};
            SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.mes_resto_item, cursor, from, to, 0);
            this.setListAdapter(sca);

            for (int i = 0; i < this.getListAdapter().getCount(); i++) {
                Cursor c = (Cursor) this.getListAdapter().getItem(i);

                String n = c.getString(1);
                String a = c.getString(2);
                ListView l = this.getListView();
                TextView ville = (TextView)l.findViewById(R.id.nomResto);

                ImageButton img = (ImageButton) l.findViewById(R.id.button_add_favoris);
                if(img == null)
                    Toast.makeText(MesRestaurants.this, "echec", Toast.LENGTH_SHORT).show();
                else {
                    int cpt_favoris = pref.getInt("cpt_favoris", 0);
                    Log.d("cpt_favoris", cpt_favoris + "");
                    for (int j = 1; j <= cpt_favoris; j++)
                        if ((pref.getString("nom" + j, "empty")).equals(n) && (pref.getString("adresse" + j, "empty")).equals(a)) {
                            img.setImageResource(android.R.drawable.btn_star_big_on);
                            img.setTag("android.R.drawable.btn_star_big_on");
                        }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(cursor != null)
            cursor.close();
    }

    public void favoris(View view) {
        LinearLayout l = (LinearLayout) view.getParent();
        nom = (TextView)l.findViewById(R.id.nomResto);
        adresse = (TextView) l.findViewById(R.id.adresseResto);
        im = (ImageButton) view.findViewById(R.id.button_add_favoris);

        if (im.getTag().equals("android.R.drawable.btn_star_big_off")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Rajouter aux favoris ?")
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //rajouter ce restaurant aux favoris
                            String fav_nom = nom.getText().toString();
                            String fav_adresse = adresse.getText().toString();
                            Toast.makeText(MesRestaurants.this, fav_nom + " " + fav_adresse, Toast.LENGTH_LONG).show();

                            im.setImageResource(android.R.drawable.btn_star_big_on);
                            im.setTag("android.R.drawable.btn_star_big_on");

                            cpt_favoris = pref.getInt("cpt_favoris", 0);
                            cpt_favoris++;
                            prefEdit.putString("nom"+cpt_favoris,fav_nom);
                            prefEdit.putString("adresse" + cpt_favoris, fav_adresse);

                            prefEdit.putInt("cpt_favoris",cpt_favoris);
                            prefEdit.apply();
                            Toast.makeText(MesRestaurants.this, "succès", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            builder.create();
            builder.show();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Enlever des favoris ?")
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //enlever ce restaurant aux favoris
                            im.setImageResource(android.R.drawable.btn_star_big_off);
                            im.setTag("android.R.drawable.btn_star_big_off");

                            Intent resultIntent = new Intent();

                            String fav_nom = nom.getText().toString();
                            String fav_adresse = adresse.getText().toString();
                            resultIntent.putExtra("nom",fav_nom);
                            resultIntent.putExtra("adresse", fav_adresse);
                            setResult(RESULT_FIRST_USER, resultIntent);
                           // finish();
                        }
                    })
                    .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            builder.create();
            builder.show();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this,AffichageRestaurant.class);
        Cursor c = (Cursor) getListAdapter().getItem(position);
        String nom = c.getString(1); //nom du resto
        String adresse = c.getString(2); //addresse du resto
        i.putExtra("nom",nom);
        i.putExtra("adresse",adresse);
        startActivity(i);
    }
}