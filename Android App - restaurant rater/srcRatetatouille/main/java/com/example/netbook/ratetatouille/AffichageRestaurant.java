package com.example.netbook.ratetatouille;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class AffichageRestaurant extends AppCompatActivity {
    private TextView nom;
    private TextView adresse;
    private TextView ville;
    private TextView pays;
    private TextView telephone;
    private TextView site_internet;
    private TextView note_appreciation;
    private TextView cout_moyen_repas;
    private TextView latitude;
    private TextView longitude;
    private TextView origine;
    private TextView description;
    private TextView contenu;
    private TextView lundi;
    private TextView mardi;
    private TextView mercredi;
    private TextView jeudi;
    private TextView vendredi;
    private TextView samedi;
    private TextView dimanche;
    private int id_resto;
    private Cursor cursor;
    private String PATH = ""; //path de l'image du restaurant
    private DatePicker datePicker; //choisir la date de l'evenement du calendrier a enregistrer
    private TimePicker timePicker; //choisir l'heure de l'evenement du calendrier a enregistrer
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minutes; //contiendra les informations de date et heure
    private EditText numero_destinataire;
    private EditText message_destinataire;
    private String numero_dest;
    private String message_dest;
    //public static final int CODE_EDIT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_restaurant);
        ImageView imageResto = (ImageView) findViewById(R.id.imageResto);

        nom = (TextView) findViewById(R.id.nom);
        adresse = (TextView) findViewById(R.id.adresse);
        ville = (TextView) findViewById(R.id.ville);
        pays = (TextView) findViewById(R.id.pays);
        telephone = (TextView) findViewById(R.id.telephone);
        site_internet = (TextView) findViewById(R.id.site_internet);
        note_appreciation = (TextView) findViewById(R.id.note_appreciation);
        cout_moyen_repas = (TextView) findViewById(R.id.cout_moyen_repas);
        latitude = (TextView) findViewById(R.id.latitude);
        longitude = (TextView) findViewById(R.id.longitude);
        origine = (TextView) findViewById(R.id.origine);
        description = (TextView) findViewById(R.id.description);
        contenu = (TextView) findViewById(R.id.contenu);
        lundi = (TextView) findViewById(R.id.lundi);
        mardi = (TextView) findViewById(R.id.mardi);
        mercredi = (TextView) findViewById(R.id.mercredi);
        jeudi = (TextView) findViewById(R.id.jeudi);
        vendredi = (TextView) findViewById(R.id.vendredi);
        samedi = (TextView) findViewById(R.id.samedi);
        dimanche = (TextView) findViewById(R.id.dimanche);
        site_internet.setPaintFlags(site_internet.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); //sous-ligne le site internet

        Intent i = getIntent(); //on recupère l'Intent
        String nom_intent = i.getStringExtra("nom"); //on recupère le nom et l'adresse du resto à affiché
        String adresse_intent = i.getStringExtra("adresse");
        ContentResolver cr = getContentResolver();
        cursor = cr.query(Uri.parse("content://com.example.netbook.ratetatouillebasecontentprovider/details/" + nom_intent + "/" + adresse_intent), null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) { //si la requete renvoi un resultat on la parcour et on affiche les données
            id_resto = cursor.getInt(0);
            nom.setText(cursor.getString(1));
            adresse.setText(cursor.getString(2));
            ville.setText(cursor.getString(3));
            pays.setText(cursor.getString(4));
            telephone.setText(cursor.getString(5));
            site_internet.setText(cursor.getString(6));
            note_appreciation.setText(cursor.getString(7));
            cout_moyen_repas.setText(cursor.getString(8));
            latitude.setText(cursor.getString(9));
            longitude.setText(cursor.getString(10));
            origine.setText(cursor.getString(11));
            description.setText(cursor.getString(12));
            contenu.setText(cursor.getString(13));
            lundi.setText(cursor.getString(14));
            mardi.setText(cursor.getString(15));
            mercredi.setText(cursor.getString(16));
            jeudi.setText(cursor.getString(17));
            vendredi.setText(cursor.getString(18));
            samedi.setText(cursor.getString(19));
            dimanche.setText(cursor.getString(20));
            //Toast.makeText(AffichageRestaurant.this, latitude.getText().toString()+" "+longitude.getText().toString(), Toast.LENGTH_LONG).show();

            //visiter le site du restaurant
            site_internet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);

                    i.setData(Uri.parse("http://" + site_internet.getText().toString()));
                    startActivity(i);
                }
            });
            //Afficher la photo du restaurant
            File file = new File(Environment.getExternalStorageDirectory(), "/DCIM/Camera/" + nom.getText().toString() + "_" + adresse.getText().toString() + ".jpg");

            if (file.exists()) {
                PATH = "/DCIM/Camera/" + nom.getText().toString() + "_" + adresse.getText().toString() + ".jpg";
                Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
                imageResto.setImageBitmap(bm);
            } else {
                Log.d("photo", "aucune photo trouvée");
            }
        } else Toast.makeText(AffichageRestaurant.this, "Erreur...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(cursor != null)
            cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_affichage_restaurant, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.edit_resto) { //modifier les informations du restaurant
            Intent i = new Intent(this, EditRestaurant.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            String[] s = adresse.getText().toString().split(" ");
            String numRue = s[0]; // on suppose que le nom du restaurant est en un seul mot sinon cela pose problème
            String adresse = "";
            for (int cpt = 1; cpt < s.length; cpt++) //on recupere l'adresse
                adresse += s[cpt] + " ";
            i.putExtra("id_resto", id_resto); //on passe toute les informations dans l'intent pour les afficher et modifier uniquement ce qu'on veut
            i.putExtra("nom", nom.getText().toString());
            i.putExtra("numRue", numRue);
            i.putExtra("adresse", adresse);
            i.putExtra("ville", ville.getText().toString());
            i.putExtra("pays", pays.getText().toString());
            i.putExtra("telephone", telephone.getText().toString());
            i.putExtra("site_internet", site_internet.getText().toString());
            i.putExtra("note_appreciation", note_appreciation.getText().toString());
            i.putExtra("cout_moyen_repas", cout_moyen_repas.getText().toString());
            i.putExtra("latitude", latitude.getText().toString());
            i.putExtra("longitude", longitude.getText().toString());
            i.putExtra("origine", origine.getText().toString());
            i.putExtra("description", description.getText().toString());
            i.putExtra("contenu", contenu.getText().toString());
            i.putExtra("lundi", lundi.getText().toString());
            i.putExtra("mardi", mardi.getText().toString());
            i.putExtra("mercredi", mercredi.getText().toString());
            i.putExtra("jeudi", jeudi.getText().toString());
            i.putExtra("vendredi", vendredi.getText().toString());
            i.putExtra("samedi", samedi.getText().toString());
            i.putExtra("dimanche", dimanche.getText().toString());
            startActivity(i); //on lance l'activité
            return true;

        }
        else if (id == R.id.localiser_resto) { // localiser le restaurant sur Google Maps
            Uri uri = Uri.parse("geo:" + latitude.getText().toString().trim() + "," + longitude.getText().toString().trim() + "?q=" + Uri.encode(nom.getText().toString().trim() + " " + adresse.getText().toString().trim()));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
            mapIntent.setPackage("com.google.android.apps.maps");
            mapIntent.setData(uri);
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else
                Toast.makeText(AffichageRestaurant.this, "Erreur lors de la localisation sur Google Maps", Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.streetview_resto) {
            String lat =latitude.getText().toString().trim();
            String longi =longitude.getText().toString().trim();

            if (!lat.equals("") && !longi.equals("")) {
                Uri uri = Uri.parse("google.streetview:cbll=" + lat + "," + longi);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                mapIntent.setPackage("com.google.android.apps.maps");
                mapIntent.setData(uri);
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else
                    Toast.makeText(AffichageRestaurant.this, "Erreur lors de la localisation sur StreetView", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(AffichageRestaurant.this, "Aucune coordonnée GPS indiquée", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.phonecall_resto) { //appeler le restaurant
            if (!telephone.getText().toString().equals("")) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + telephone.getText().toString()));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return false;
                }
                startActivity(callIntent);
            } else
                Toast.makeText(AffichageRestaurant.this, "Aucun numéro de téléphone enregistré", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.add_calendar_resto) {

            datePicker = new DatePicker(this); //datePicker permet de choisir une date
            datePicker.setCalendarViewShown(false); //affichage en format spinner et non en calendrier

            timePicker = new TimePicker(this);
            timePicker.setIs24HourView(true);

            final AlertDialog.Builder builderHour = new AlertDialog.Builder(this);
            builderHour.setMessage("Choisissez la date et l'heure")
                    .setView(timePicker) // on place le timePicker dans la boite de dialog
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (Build.VERSION.SDK_INT < 23) {//si la version d'android est inferieure à 23
                                hour = timePicker.getCurrentHour();
                                minutes = timePicker.getCurrentMinute();
                            } else if (Build.VERSION.SDK_INT >= 23) { //si la version d'android est superieur à 23
                                hour = timePicker.getHour();
                                minutes = timePicker.getMinute();
                            }
                            int calId = 3; //dans la documentation l'id est a 3 mais on peut mettre autre chose
                            TimeZone timeZone = TimeZone.getDefault(); //pour definir dans quelle zone on est (GM+1,GMT+2,etc...)
                            Calendar beginTime = Calendar.getInstance(); //sert a stocker la date
                            beginTime.set(year, month, day, hour, minutes);
                            long startTime = beginTime.getTimeInMillis(); //date en milliseconde
                            ContentResolver cr = getContentResolver();//pour inserer l'evenement dans le calendrier
                            ContentValues values = new ContentValues(); //encapsuler les informations de l'evenement
                            values.put(CalendarContract.Events.DTSTART, startTime); //date de debut
                            values.put(CalendarContract.Events.DTEND, startTime + 1000 * 60 * 60); // pendant une heure,(arbitraire)
                            values.put(CalendarContract.Events.TITLE, "RDV au " + nom.getText().toString() + " " + adresse.getText().toString()); //titre
                            values.put(CalendarContract.Events.DESCRIPTION, ""); //description
                            values.put(CalendarContract.Events.CALENDAR_ID, calId);
                            values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
                            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            cr.insert(CalendarContract.Events.CONTENT_URI, values);
                            Toast.makeText(AffichageRestaurant.this, "Enregistré", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            builderHour.create();

            AlertDialog.Builder builderDate = new AlertDialog.Builder(this);
            builderDate.setMessage("Choisissez la date")
                    .setView(datePicker) //on place le datePicker dans la boite de dialog
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            year = datePicker.getYear(); //on recupere les donnees
                            month = datePicker.getMonth();
                            day = datePicker.getDayOfMonth();
                            builderHour.show(); // puis on affiche la boite de dialog pour recuperer l'heure
                        }
                    })
                    .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            builderDate.create();
            builderDate.show(); //on affiche la boite de dialog pour la date
        }
        else if(id == R.id.add_contact_resto){
            ArrayList < ContentProviderOperation > ops = new ArrayList<>();
            int rawContactInsertIndex = ops.size();
            ops.add(ContentProviderOperation.newInsert(
                    ContactsContract.RawContacts.CONTENT_URI)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());
            ops.add(ContentProviderOperation.newInsert(
                    ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, nom.getText().toString()+"-"+adresse.getText().toString())
                    .build());
            ops.add(ContentProviderOperation.newInsert(
                            ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, telephone.getText().toString())
                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                            .build()
            );
            try {
                getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(AffichageRestaurant.this, "Erreur lors de l'ajout au contact", Toast.LENGTH_LONG).show();
            }
            Toast.makeText(AffichageRestaurant.this, "Contact enregistré", Toast.LENGTH_LONG).show();
        }
        else if(id == R.id.send_sms_resto){ //envoyer un message à a un ami
            numero_destinataire = new EditText(this); //edittext qui contiendra le numero du destinataire
            numero_destinataire.setInputType(InputType.TYPE_CLASS_PHONE); //inputType = phone car on veut rentrer un numero et non pas des lettres

            message_destinataire = new EditText(this); //edittext qui contiendra le message du destinataire
            message_destinataire.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);//inputType = texte sur plusieurs lignes

            final AlertDialog.Builder builderMessage = new AlertDialog.Builder(this); //boite de dialog pour entrer le message
            builderMessage.setMessage("Entrez le message")
                    .setView(message_destinataire) // on met l'editText dans la boite de dialog
                    .setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!message_destinataire.getText().toString().trim().equals("")) {
                                message_dest = message_destinataire.getText().toString().trim(); //on récupère le message
                                SmsManager smsManager = SmsManager.getDefault(); //on recupère le SMSManager qui servira à envoyer le message
                                smsManager.sendTextMessage(numero_dest, null, message_dest, null, null); // on envoi le message avec les donnee entrées
                            } else
                                Toast.makeText(AffichageRestaurant.this, "Veuillez entrer un message svp", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            builderMessage.create(); //on creer la boite de dialog mais on ne l'appel pas maintenant

            AlertDialog.Builder builderNumero = new AlertDialog.Builder(this); //boite de dialog pour entrer le numéro du destinataire
            builderNumero.setMessage("Entrez le numéro du destinataire")
                    .setView(numero_destinataire) // on met l'editText dans la boite de dialog
                    .setPositiveButton("Suivant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!numero_destinataire.getText().toString().trim().equals("")) {
                                numero_dest = numero_destinataire.getText().toString().trim();//on recupere le numéro du destinataire
                                builderMessage.show(); //on affiche la boite de dialog qui recupere le message
                            } else
                                Toast.makeText(AffichageRestaurant.this, "Veuillez entrer un numéro de téléphone svp", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            builderNumero.create();//on creer la boite de dialog
            builderNumero.show(); //on l'affiche
        }
        else if(id == R.id.record_voice_resto){
            Intent recordIntent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
            String path = "/Record/sound_"+nom.getText().toString().trim()+"_"+adresse.getText().toString().trim()+String.valueOf(System.currentTimeMillis())+".amr";
            Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),path));
            recordIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
            startActivityForResult(recordIntent,1111);


            /*String path = "/Record/sound_"+nom.getText().toString().trim()+"_"+adresse.getText().toString().trim()+String.valueOf(System.currentTimeMillis())+".amr";

            MediaRecorder mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(path);
            try {
                mediaRecorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaRecorder.start();*/
        }
        return super.onOptionsItemSelected(item);
    }

    public void afficherPhoto(View view) {
        if(!PATH.equals("")) { //si le path n'est pas vide
            Intent i = new Intent(this,PhotoDisplay.class);
            i.putExtra("path", PATH);
            startActivity(i); //on affiche la photo
        }
    }
}

/*
A faire
    faire le onProximityAlert avec Notification qui precise quon est proche d'un restaurant et afficher ce restaurant au retour a lapp
    rechercher un restaurant a moin d'un kilometre dici par exemple, ou ouvert maintenant
        recuperer le jour actuel et l heure actuelle, elle doit etre superieur a lheure d'ouverture du restaurant de ce meme jour et inferieur a lheure de fermeture
    gerer les bundle instance state : pas besoin ?
*/
