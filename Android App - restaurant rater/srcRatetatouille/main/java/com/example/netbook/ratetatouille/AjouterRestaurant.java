package com.example.netbook.ratetatouille;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class AjouterRestaurant extends AppCompatActivity implements LocationListener {
    private EditText nom;
    private EditText numRue;
    private EditText adresse;
    private EditText ville;
    private EditText pays;
    private EditText telephone;
    private EditText site_internet;
    private String note_appre;
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
    private SharedPreferences.Editor editor;
    private ContentResolver cr;
    private Uri outputFileUri;
    private static int COMPTEUR_RESTAURANT = 1;
    private static final int TAKE_PICTURE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_restaurant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cr = getContentResolver();

        nom = (EditText) findViewById(R.id.nom);
        numRue = (EditText) findViewById(R.id.numero_rue);
        adresse = (EditText) findViewById(R.id.adresse);
        ville = (EditText) findViewById(R.id.ville);
        pays = (EditText) findViewById(R.id.pays);
        telephone = (EditText) findViewById(R.id.telephone);
        site_internet = (EditText) findViewById(R.id.site_internet);
        Spinner note_appreciation = (Spinner) findViewById(R.id.note_appreciation_spinner);
        cout_moyen_repas = (EditText) findViewById(R.id.cout_moyen_repas);
        latitude = (EditText) findViewById(R.id.latitude);
        longitude = (EditText) findViewById(R.id.longitude);
        specialitees = (EditText) findViewById(R.id.spécialité);
        description = (EditText) findViewById(R.id.description);
        commentaire = (EditText) findViewById(R.id.commentaire);
        lundi = (EditText) findViewById(R.id.lundi);
        mardi = (EditText) findViewById(R.id.mardi);
        mercredi = (EditText) findViewById(R.id.mercredi);
        jeudi = (EditText) findViewById(R.id.jeudi);
        vendredi = (EditText) findViewById(R.id.vendredi);
        samedi = (EditText) findViewById(R.id.samedi);
        dimanche = (EditText) findViewById(R.id.dimanche);


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

        SharedPreferences pref = getSharedPreferences("CLE RESTAURANT", Context.MODE_PRIVATE);
        editor = pref.edit();
        COMPTEUR_RESTAURANT = pref.getInt("COMPTEUR RESTAURANTS", 1); //on recupere le nombre de restaurant enregistrés, si il n'y en a pas il vaut 1

        FloatingActionButton ajouter = (FloatingActionButton) findViewById(R.id.add_restaurant);
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
                                    values.put("id_restaurant", COMPTEUR_RESTAURANT);
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
                                    values.put("id_cuisine", COMPTEUR_RESTAURANT);
                                    values.put("origine", specialitees.getText().toString().trim());
                                    values.put("description", (s_description.equals("") ? "null" : s_description));
                                    values.put("id_donnee", COMPTEUR_RESTAURANT);
                                    values.put("id_restaurant", COMPTEUR_RESTAURANT);
                                    values.put("type", "texte");
                                    values.put("contenu", (s_commentaire.equals("") ? "null" : s_commentaire));
                                    values.put("id_horaire", COMPTEUR_RESTAURANT);
                                    values.put("lundi", (s_lundi.equals("") ? "null" : s_lundi));
                                    values.put("mardi", (s_mardi.equals("") ? "null" : s_mardi));
                                    values.put("mercredi", (s_mercredi.equals("") ? "null" : s_mercredi));
                                    values.put("jeudi", (s_jeudi.equals("") ? "null" : s_jeudi));
                                    values.put("vendredi", (s_vendredi.equals("") ? "null" : s_vendredi));
                                    values.put("samedi", (s_samedi.equals("") ? "null" : s_samedi));
                                    values.put("dimanche", (s_dimanche.equals("") ? "null" : s_dimanche));
                                    values.put("id_restaurant", COMPTEUR_RESTAURANT);
                                    values.put("id_cuisine", COMPTEUR_RESTAURANT);
                                    values.put("id_restaurant", COMPTEUR_RESTAURANT);
                                    values.put("id_horaire", COMPTEUR_RESTAURANT);

                                    Uri uri = cr.insert(Uri.parse("content://com.example.netbook.ratetatouillebasecontentprovider"), values); //on envoi une requete d'insertion au content provider
                                    if (uri != null && uri.getLastPathSegment().equals("true")) {
                                        Toast.makeText(AjouterRestaurant.this, "Le restaurant " + nom.getText().toString().trim() + " a bien été ajouté", Toast.LENGTH_LONG).show();
                                        COMPTEUR_RESTAURANT++; //on incremente le nombre de restaurant
                                        editor.putInt("COMPTEUR RESTAURANTS", COMPTEUR_RESTAURANT); //on le sauvegarde dans les preferences
                                        editor.apply();
                                        finish(); //on ferme l'activité
                                    } else
                                        Snackbar.make(view, "Vous avez déjà ajouté ce restaurant", Snackbar.LENGTH_LONG)
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

        FloatingActionButton fab_take_picture = (FloatingActionButton) findViewById(R.id.take_picture);
        fab_take_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s_nom = nom.getText().toString().trim();
                String s_numRue = numRue.getText().toString().trim();
                String s_adresse = adresse.getText().toString().trim();

                if (!s_nom.equals("") && !s_adresse.equals("") && !s_numRue.equals("")) {
                    Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //on creer l'intent qui servira à l'ouverture de l'appareil photo
                    File file = new File(Environment.getExternalStorageDirectory(),"/DCIM/Camera/"+s_nom+"_"+s_numRue+" "+s_adresse+".jpg");//on creer le fichier nom_adresse.jpg
                    outputFileUri = Uri.fromFile(file);
                    photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    if (photoIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(photoIntent, TAKE_PICTURE);
                    } else
                        Log.d("take picture","resolve null");
                }else
                    Toast.makeText(AjouterRestaurant.this, "Veuillez entrer le nom et l'adresse avant de prendre la photo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void localiser(View view) {//localiser la position courante
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);//on teste si le gps est activé
        boolean isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);//on teste si internet est activé
        if (!isGPSEnabled && !isNetworkEnabled)
            Toast.makeText(AjouterRestaurant.this, "Veuillez activer internet et le GPS", Toast.LENGTH_SHORT).show();
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
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, this); // on lance la localisation

                Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);//on recupere la valeur

                if (location != null) {
                    latitude.setText(String.format("%s", location.getLatitude())); //on met les valeurs dans les EditText
                    longitude.setText(String.format("%s", location.getLongitude()));
                } else
                    Toast.makeText(AjouterRestaurant.this, "Erreur de localisation, Veuillez activer internet", Toast.LENGTH_SHORT).show();
            }
            if(isGPSEnabled){
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,10,this);

                Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                if (location != null) {
                    latitude.setText(String.format("%s", location.getLatitude()));
                    longitude.setText(String.format("%s", location.getLongitude()));
                } else
                    Toast.makeText(AjouterRestaurant.this, "Erreur de localisation, Veuillez activer le gps", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TAKE_PICTURE)
            if(resultCode == RESULT_OK) {
                Toast.makeText(AjouterRestaurant.this, "Photo enregstrée dans la galerie", Toast.LENGTH_SHORT).show();
            }
            else if(resultCode == RESULT_CANCELED)
                Toast.makeText(AjouterRestaurant.this, "Annulé", Toast.LENGTH_LONG).show();
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
