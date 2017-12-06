package com.example.netbook.ratetatouille;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;

public class EditRestaurant extends AppCompatActivity implements LocationListener{
    private EditText nom;
    private EditText numRue;
    private EditText adresse;
    private EditText ville;
    private EditText pays;
    private EditText telephone;
    private EditText site_internet;
    private EditText cout_moyen_repas;
    private EditText latitude;
    private EditText longitude;
    private EditText specialitees;
    private EditText description;
    private EditText commentaire;
    private EditText lundi;
    private EditText mardi;
    private EditText mercredi;
    private EditText jeudi;
    private EditText vendredi;
    private EditText samedi;
    private EditText dimanche;
    private ContentResolver cr;
    private String note_appre;
    private Uri outputFileUri;
    private int id_resto;
    private static final int TAKE_PICTURE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        id_resto = i.getIntExtra("id_resto", 1);
        String i_nom = i.getStringExtra("nom");
        String i_numRue = i.getStringExtra("numRue");
        String i_adresse = i.getStringExtra("adresse");
        String i_ville = i.getStringExtra("ville");
        String i_pays = i.getStringExtra("pays");
        String i_telephone = i.getStringExtra("telephone");
        String i_site_internet = i.getStringExtra("site_internet");
       // String i_note_appreciation = i.getStringExtra("note_appreciation");
        String i_cout_moyen_repas = i.getStringExtra("cout_moyen_repas");
        String i_latitude = i.getStringExtra("latitude");
        String i_longitude = i.getStringExtra("longitude");
        String i_origine = i.getStringExtra("origine");
        String i_description = i.getStringExtra("description");
        String i_contenu = i.getStringExtra("contenu");
        String i_lundi = i.getStringExtra("lundi");
        String i_mardi = i.getStringExtra("mardi");
        String i_mercredi = i.getStringExtra("mercredi");
        String i_jeudi = i.getStringExtra("jeudi");
        String i_vendredi = i.getStringExtra("vendredi");
        String i_samedi = i.getStringExtra("samedi");
        String i_dimanche = i.getStringExtra("dimanche");

        cr = getContentResolver();

        nom = (EditText) findViewById(R.id.nom_edit);
        numRue = (EditText) findViewById(R.id.numero_rue_edit);
        adresse = (EditText) findViewById(R.id.adresse_edit);
        ville = (EditText) findViewById(R.id.ville_edit);
        pays = (EditText) findViewById(R.id.pays_edit);
        telephone = (EditText) findViewById(R.id.telephone_edit);
        site_internet = (EditText) findViewById(R.id.site_internet_edit);
        Spinner note_appreciation = (Spinner) findViewById(R.id.note_appreciation_spinner_edit);
        cout_moyen_repas = (EditText) findViewById(R.id.cout_moyen_repas_edit);
        latitude = (EditText) findViewById(R.id.latitude_edit);
        longitude = (EditText) findViewById(R.id.longitude_edit);
        specialitees = (EditText) findViewById(R.id.spécialité_edit);
        description = (EditText) findViewById(R.id.description_edit);
        commentaire = (EditText) findViewById(R.id.commentaire_edit);
        lundi = (EditText) findViewById(R.id.lundi_edit);
        mardi = (EditText) findViewById(R.id.mardi_edit);
        mercredi = (EditText) findViewById(R.id.mercredi_edit);
        jeudi = (EditText) findViewById(R.id.jeudi_edit);
        vendredi = (EditText) findViewById(R.id.vendredi_edit);
        samedi = (EditText) findViewById(R.id.samedi_edit);
        dimanche = (EditText) findViewById(R.id.dimanche_edit);

        nom.setText(i_nom);
        numRue.setText(i_numRue);
        adresse.setText(i_adresse);
        ville.setText(i_ville);
        pays.setText(i_pays);
        telephone.setText(i_telephone);
        site_internet.setText(i_site_internet);
        //note_appreciation.setText(i_note_appreciation);
        cout_moyen_repas.setText(i_cout_moyen_repas);
        latitude.setText(i_latitude);
        longitude.setText(i_longitude);
        specialitees.setText(i_origine);
        description.setText(i_description);
        commentaire.setText(i_contenu);
        lundi.setText(i_lundi);
        mardi.setText(i_mardi);
        mercredi.setText(i_mercredi);
        jeudi.setText(i_jeudi);
        vendredi.setText(i_vendredi);
        samedi.setText(i_samedi);
        dimanche.setText(i_dimanche);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.valeurs_spinner,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        note_appreciation.setAdapter(adapter);
        if(Build.VERSION.SDK_INT >=16)
            note_appreciation.setDropDownVerticalOffset(10);
        note_appreciation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Resources resources = getResources();
                String[] valeurs = resources.getStringArray(R.array.valeurs_spinner);
                note_appre = valeurs[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FloatingActionButton ajouter = (FloatingActionButton) findViewById(R.id.edit_restaurant);
        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nom.getText().toString().trim().equals("")) { // on recupere les valeurs du formulaire
                    nom.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    if (!numRue.getText().toString().equals("") && !adresse.getText().toString().trim().equals("")) {
                        adresse.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        if (!ville.getText().toString().trim().equals("")) {
                            ville.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                            if (!pays.getText().toString().trim().equals("")) {
                                pays.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                if (!specialitees.getText().toString().trim().equals("")) {
                                    specialitees.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                                    String s_telephone = telephone.getText().toString().trim(); // trim() sert a enlever les espaces inutiles au debut et a la fin
                                    String s_site_internet = site_internet.getText().toString().trim();
                                    String s_cout_moyen_repas = cout_moyen_repas.getText().toString().trim();
                                    String s_description = description.getText().toString().trim();
                                    String s_commentaire = commentaire.getText().toString().trim();
                                    String s_latitude = latitude.getText().toString().trim();
                                    String s_longitude = longitude.getText().toString().trim();
                                    String s_lundi = lundi.getText().toString().trim();
                                    String s_mardi = mardi.getText().toString().trim();
                                    String s_mercredi = mercredi.getText().toString().trim();
                                    String s_jeudi = jeudi.getText().toString().trim();
                                    String s_vendredi = vendredi.getText().toString().trim();
                                    String s_samedi = samedi.getText().toString().trim();
                                    String s_dimanche = dimanche.getText().toString().trim();

                                    // si lutilisateur decide de ne pas rentrer de donnee on envoi "null" au content provider qui mettra NULL comme valeur et non "null"

                                    ContentValues values = new ContentValues(); //on encapsule les valeur à envoyer au content provider
                                    values.put("id_restaurant", id_resto);
                                    values.put("nom", nom.getText().toString().trim());
                                    values.put("adresse", numRue.getText().toString().trim() + " " + adresse.getText().toString().trim());
                                    values.put("ville", ville.getText().toString().trim());
                                    values.put("pays", pays.getText().toString().trim());
                                    values.put("telephone", (s_telephone.equals("") ? "null" : s_telephone));
                                    values.put("site_internet", (s_site_internet.equals("") ? "null" : s_site_internet));
                                    values.put("note_appreciation", note_appre);
                                    values.put("cout_moyen_repas", (s_cout_moyen_repas.equals("") ? "null" : s_cout_moyen_repas));
                                    values.put("latitude", (s_latitude.equals("") ? "null" : s_latitude));
                                    values.put("longitude", (s_longitude.equals("") ? "null" : s_longitude));
                                    values.put("id_cuisine", id_resto);
                                    values.put("origine", specialitees.getText().toString().trim());
                                    values.put("description", (s_description.equals("") ? "null" : s_description));
                                    values.put("id_donnee", id_resto);
                                    values.put("id_restaurant", id_resto);
                                    values.put("type", "texte");
                                    values.put("contenu", (s_commentaire.equals("") ? "null" : s_commentaire));
                                    values.put("id_horaire", id_resto);
                                    values.put("lundi", (s_lundi.equals("") ? "null" : s_lundi));
                                    values.put("mardi", (s_mardi.equals("") ? "null" : s_mardi));
                                    values.put("mercredi", (s_mercredi.equals("") ? "null" : s_mercredi));
                                    values.put("jeudi", (s_jeudi.equals("") ? "null" : s_jeudi));
                                    values.put("vendredi", (s_vendredi.equals("") ? "null" : s_vendredi));
                                    values.put("samedi", (s_samedi.equals("") ? "null" : s_samedi));
                                    values.put("dimanche", (s_dimanche.equals("") ? "null" : s_dimanche));
                                    values.put("id_restaurant", id_resto);
                                    values.put("id_cuisine", id_resto);
                                    values.put("id_restaurant", id_resto);
                                    values.put("id_horaire", id_resto);

                                    int nb = cr.update(Uri.parse("content://com.example.netbook.ratetatouillebasecontentprovider"),values, null, null);
                                    Log.d("nb", nb + "");
                                    Log.d("id_resto", id_resto + "");
                                    if(nb == 1) {
                                        Snackbar.make(view, "Le restaurant a bien été modifié", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                        finish();
                                    }
                                    else
                                        Snackbar.make(view, "Erreur de modification", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                } else {
                                    Snackbar.make(view, "Veuillez saisir une ou des specialitées", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    specialitees.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_delete, 0); //on affiche une petite croix a droite pour indiquer que ce champ est obligatoire
                                }
                            } else

                            {
                                Snackbar.make(view, "Veuillez saisir un pays", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                pays.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_delete, 0);
                            }
                        } else {
                            Snackbar.make(view, "Veuillez saisir une ville", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            ville.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_delete, 0);
                        }
                    } else {
                        Snackbar.make(view, "Veuillez saisir une adresse", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        adresse.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_delete, 0);
                    }
                } else {
                    Snackbar.make(view, "Veuillez saisir un nom", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    nom.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_delete, 0);
                }
            }
        });

        FloatingActionButton fab_photo = (FloatingActionButton) findViewById(R.id.take_edit_picture);
        fab_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s_nom = nom.getText().toString().trim();
                String s_numRue = numRue.getText().toString().trim();
                String s_adresse = adresse.getText().toString().trim();

                if (!s_nom.equals("") && !s_adresse.equals("") && !s_numRue.equals("")) {
                    Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = new File(Environment.getExternalStorageDirectory(),"/DCIM/Camera/"+s_nom+"_"+s_numRue+" "+s_adresse+".jpg");
                    outputFileUri = Uri.fromFile(file);
                    photoIntent.putExtra(MediaStore.EXTRA_OUTPUT,outputFileUri);
                    if (photoIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(photoIntent, TAKE_PICTURE);
                    } else
                        Log.d("take picture", "resolve null");
                }else
                    Toast.makeText(EditRestaurant.this, "Veuillez entrer le nom et l'adresse avant de prendre la photo", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void localiser_edit(View view) {
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGPSEnabled && !isNetworkEnabled)
            Toast.makeText(EditRestaurant.this, "Veuillez activer internet et le GPS", Toast.LENGTH_SHORT).show();
        else {
            if (isNetworkEnabled) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,1000,this);

                Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                if (location != null) {
                    latitude.setText(String.format("%s", location.getLatitude()));
                    longitude.setText(String.format("%s", location.getLongitude()));
                } else
                    Toast.makeText(EditRestaurant.this, "Erreur de localisation, Veuillez activer internet", Toast.LENGTH_SHORT).show();
            }
            if(isGPSEnabled){
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,10,this);

                Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                if (location != null) {
                    latitude.setText(String.format("%s", location.getLatitude()));
                    longitude.setText(String.format("%s", location.getLongitude()));
                } else
                    Toast.makeText(EditRestaurant.this, "Erreur de localisation, Veuillez activer le gps", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}






























       /* FloatingActionButton fab_edit = (FloatingActionButton) findViewById(R.id.edit_restaurant);
        fab_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                String s_nom = nom.getText().toString().trim();
                String s_numRue = numRue.getText().toString().trim();
                String s_adresse = adresse.getText().toString().trim();
                String s_ville = ville.getText().toString().trim();
                String s_pays = pays.getText().toString().trim();
                String s_telephone = telephone.getText().toString().trim();
                String s_site_internet = site_internet.getText().toString().trim();
                String s_note_appreciation = note_appreciation.getText().toString().trim();
                String s_cout_moyen_repas = cout_moyen_repas.getText().toString().trim();
                String s_latitude = latitude.getText().toString().trim();
                String s_longitude = longitude.getText().toString().trim();
                String s_specialitees = specialitees.getText().toString().trim();
                String s_description = description.getText().toString().trim();
                String s_commentaire = commentaire.getText().toString().trim();
                String s_lundi = lundi.getText().toString().trim();
                String s_mardi = mardi.getText().toString().trim();
                String s_mercredi = mercredi.getText().toString().trim();
                String s_jeudi = jeudi.getText().toString().trim();
                String s_vendredi = vendredi.getText().toString().trim();
                String s_samedi = samedi.getText().toString().trim();
                String s_dimanche = dimanche.getText().toString().trim();

                if(!s_nom.isEmpty() && !s_numRue.isEmpty() && !s_adresse.isEmpty() && !s_ville.isEmpty() && ! s_pays.isEmpty()
                        && !s_telephone.isEmpty() && !s_site_internet.isEmpty() && !s_note_appreciation.isEmpty()
                        && !s_cout_moyen_repas.isEmpty() && !s_latitude.isEmpty() && !s_longitude.isEmpty() && !s_specialitees.isEmpty()
                        && !s_description.isEmpty() && !s_commentaire.isEmpty() && !s_lundi.isEmpty() && !s_mardi.isEmpty()
                        && !s_mercredi.isEmpty() && !s_jeudi.isEmpty() && !s_vendredi.isEmpty() && !s_samedi.isEmpty() && !s_dimanche.isEmpty()){

                    ContentValues values = new ContentValues();
                    values.put("id_restaurant", id_resto);
                    values.put("nom", s_nom);
                    values.put("adresse", s_numRue + " " + s_adresse);
                    values.put("ville", s_ville);
                    values.put("pays", s_pays);
                    values.put("telephone", s_telephone);
                    values.put("site_internet", s_site_internet);
                    values.put("note_appreciation", s_note_appreciation);
                    values.put("cout_moyen_repas", s_cout_moyen_repas);
                    values.put("latitude", s_latitude);
                    values.put("longitude", s_longitude);
                    values.put("id_cuisine", id_resto);
                    values.put("origine", s_specialitees);
                    values.put("description", s_description);
                    values.put("id_donnee", id_resto);
                    values.put("id_restaurant", id_resto);
                    values.put("type", "texte");
                    values.put("contenu", s_commentaire);
                    values.put("id_horaire", id_resto);
                    values.put("lundi", s_lundi);
                    values.put("mardi", s_mardi);
                    values.put("mercredi", s_mercredi);
                    values.put("jeudi", s_jeudi);
                    values.put("vendredi", s_vendredi);
                    values.put("samedi", s_samedi);
                    values.put("dimanche", s_dimanche);
                    int nb = cr.update(Uri.parse("content://com.example.netbook.ratetatouillebasecontentprovider"),values, null, null);
                    Log.d("nb", nb + "");
                    Log.d("id_resto", id_resto + "");
                    if(nb == 1) {
                        Snackbar.make(view, "Le restaurant a bien été modifié", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        finish();
                    }
                    else
                        Snackbar.make(view, "Erreur de modification", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                } else
                    Snackbar.make(view, "Veuillez saisir toutes les informations", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }
        });
*/