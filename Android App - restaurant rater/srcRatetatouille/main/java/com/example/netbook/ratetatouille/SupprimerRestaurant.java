package com.example.netbook.ratetatouille;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SupprimerRestaurant extends AppCompatActivity {
    private EditText nom;
    private EditText adresse;
    private ContentResolver cr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supprimer_restaurant);
        nom = (EditText) findViewById(R.id.nom);
        adresse = (EditText) findViewById(R.id.adresse);
        cr = getContentResolver();
    }

    public void supprimerResto(View view) {
        // supprimer ce restaurant de la base de donnee
        String n = nom.getText().toString().trim();
        String a = adresse.getText().toString().trim();
        if(!n.equals("") && !a.equals("")){
            String selection = "nom LIKE \""+n+"\" AND adresse LIKE \""+a+"\"";
            int nb = cr.delete(Uri.parse("content://com.example.netbook.ratetatouillebasecontentprovider"), selection, null);
            if(nb > 0)
                Snackbar.make(view, "Restaurant supprimé", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            else
                Snackbar.make(view, "Impossible de supprimer ce restaurant : inexistant", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
        }
        else Snackbar.make(view, "Veuillez entrer le nom et l'adresse du restaurant à supprimé", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void supprimerTousLesResto(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Etes-vous sur de vouloir supprimer tous les restaurants ?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int deleted = cr.delete(Uri.parse("content://com.example.netbook.ratetatouillebasecontentprovider"), "nom LIKE '%'", null);
                        if(deleted > 0) {
                            Toast.makeText(SupprimerRestaurant.this, "Tous les restaurants ont été supprimés", Toast.LENGTH_SHORT).show();
                            SharedPreferences pref = getSharedPreferences("CLE RESTAURANT", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putInt("COMPTEUR RESTAURANTS",1);
                            editor.apply();
                        }
                        else
                            Toast.makeText(SupprimerRestaurant.this, "Aucun restaurant à suppimer", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.create();
        builder.show();
    }
}
