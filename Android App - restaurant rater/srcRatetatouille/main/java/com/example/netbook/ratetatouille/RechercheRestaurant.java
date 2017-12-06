package com.example.netbook.ratetatouille;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class RechercheRestaurant extends AppCompatActivity {
    private ContentResolver cr;
    private Cursor cursor;
    private EditText nom;
    private EditText adresse;
    private EditText ville;
    private EditText pays;
    private EditText spec;
    private EditText note_search;
    private ListView listView;
    private SimpleCursorAdapter sca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_restaurant);

        nom = (EditText) findViewById(R.id.nom_search);
        adresse = (EditText) findViewById(R.id.adresse_search);
        ville = (EditText) findViewById(R.id.ville_search);
        pays = (EditText) findViewById(R.id.pays_search);
        spec = (EditText) findViewById(R.id.spec_search);
        note_search = (EditText) findViewById(R.id.note_search);
        listView = (ListView) findViewById(R.id.listViewSearch);
        cr = getContentResolver();
    }

    public void search(View view) {
        String s_nom = nom.getText().toString().trim();
        String s_adresse = adresse.getText().toString().trim();
        String s_ville = ville.getText().toString().trim();
        String s_pays = pays.getText().toString().trim();
        String s_spec = spec.getText().toString().trim();
        String s_note_search = note_search.getText().toString().trim();

        if(s_nom.equals("") && s_adresse.equals("") && s_ville.equals("") && s_pays.equals("") && s_spec.equals("") && s_note_search.equals(""))
            Toast.makeText(RechercheRestaurant.this, "Veuillez entrer des critères de recherche", Toast.LENGTH_SHORT).show();

        else if(!s_nom.equals("")){ //si on entre le nom
            if(!s_adresse.equals("")) //si on entre le nom et l'adresse
                cursor = cr.query(Uri.parse("content://com.example.netbook.ratetatouillebasecontentprovider/nom_adresse/"+s_nom+"/"+s_adresse),null,null,null,null);
            else if(!s_ville.equals("")) // si on entre le nom et la ville
                cursor = cr.query(Uri.parse("content://com.example.netbook.ratetatouillebasecontentprovider/nom_ville/"+s_nom+"/"+s_ville),null,null,null,null);
            else if(!s_pays.equals("")) // si on entre le nom et le pays
                cursor = cr.query(Uri.parse("content://com.example.netbook.ratetatouillebasecontentprovider/nom_pays/"+s_nom+"/"+s_pays),null,null,null,null);
            else // si on entre que le nom
                cursor = cr.query(Uri.parse("content://com.example.netbook.ratetatouillebasecontentprovider/nom/"+s_nom),null,null,null,null);
        }
        else if(s_nom.equals("")){ // si on a pas entrer le nom
            if(!s_adresse.equals(""))
                cursor = cr.query(Uri.parse("content://com.example.netbook.ratetatouillebasecontentprovider/adresse/"+s_adresse),null,null,null,null);
            else if(!s_ville.equals(""))
                cursor = cr.query(Uri.parse("content://com.example.netbook.ratetatouillebasecontentprovider/ville/"+s_ville),null,null,null,null);
            else if(!s_pays.equals(""))
                cursor = cr.query(Uri.parse("content://com.example.netbook.ratetatouillebasecontentprovider/pays/"+s_pays),null,null,null,null);
            else if(!s_spec.equals(""))
                cursor = cr.query(Uri.parse("content://com.example.netbook.ratetatouillebasecontentprovider/specialite/"+s_spec),null,null,null,null);
            else if(!s_note_search.equals(""))
                cursor = cr.query(Uri.parse("content://com.example.netbook.ratetatouillebasecontentprovider/note/"+Integer.parseInt(s_note_search)),null,null,null,null);
        }

        if(cursor != null && cursor.moveToFirst()){
            String[] from = {"nom","adresse","ville","pays"};
            int[] to = {R.id.nomRestoSearch,R.id.adresseRestoSearch,R.id.villeRestoSearch,R.id.paysRestoSearch};
            sca = new SimpleCursorAdapter(this,R.layout.recherche_resto_item,cursor,from,to,0);
            listView.setAdapter(sca);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(getApplicationContext(),AffichageRestaurant.class);
                    TextView nom = (TextView) view.findViewById(R.id.nomRestoSearch);
                    TextView adresse = (TextView) view.findViewById(R.id.adresseRestoSearch);
                    i.putExtra("nom",nom.getText().toString());
                    i.putExtra("adresse",adresse.getText().toString());
                    startActivity(i);
                }
            });
        }
        else {
            Toast.makeText(RechercheRestaurant.this, "Aucun restaurant trouvé", Toast.LENGTH_SHORT).show();
            listView.setAdapter(null); // on vide la liste si on e trouve rien pour faire plus clair
//          sca.notifyDataSetChanged();
        }
        //on vide le contenu des editText pour continuer à chercher d'autre restaurants

        nom.setText("");
        adresse.setText("");
        ville.setText("");
        pays.setText("");
        spec.setText("");
        note_search.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(cursor != null)
            cursor.close();
    }
}
