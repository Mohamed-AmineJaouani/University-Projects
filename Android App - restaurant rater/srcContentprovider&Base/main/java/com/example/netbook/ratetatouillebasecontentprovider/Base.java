package com.example.netbook.ratetatouillebasecontentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Base extends SQLiteOpenHelper {
    //version de la base
    private static final int VERSION = 6;
    //nom de la base
    private static final String DB_NAME = "restauration.db";
    //nom des tables
    public static final String TABLE_RESTAURANT = "restaurant";
    public static final String TABLE_CUISINE ="cuisine";
    public static final String TABLE_DONNEE ="donnee";
    public static final String TABLE_HORAIRE ="horaire";
    public static final String TABLE_RELATION_RESTAURANT_CUISINE ="relation_restaurant_cuisine";
    public static final String TABLE_RELATION_RESTAURANT_HORAIRE ="relation_restaurant_horaire";
    //drop tables
    private static final String DROP_TABLE_RESTAURANT = "DROP TABLE IF EXISTS " + TABLE_RELATION_RESTAURANT_CUISINE + ";";
    private static final String DROP_TABLE_CUISINE = "DROP TABLE IF EXISTS " + TABLE_RELATION_RESTAURANT_HORAIRE + ";";
    private static final String DROP_TABLE_DONNEE = "DROP TABLE IF EXISTS " + TABLE_HORAIRE + ";";
    private static final String DROP_TABLE_HORAIRE = "DROP TABLE IF EXISTS " + TABLE_CUISINE + ";";
    private static final String DROP_TABLE_RELATION_RESTAURANT_CUISINE = "DROP TABLE IF EXISTS " + TABLE_DONNEE + ";";
    private static final String DROP_TABLE_RELATION_RESTAURANT_HORAIRE = "DROP TABLE IF EXISTS " + TABLE_RESTAURANT + ";";
    //attributs
    //attributs de la table RESTAURANT
    public static final String RESTAURANT_ID = "id_restaurant";
    public static final String RESTAURANT_NOM = "nom";
    public static final String RESTAURANT_ADRESSE = "adresse";
    public static final String RESTAURANT_VILLE = "ville";
    public static final String RESTAURANT_PAYS = "pays";
    public static final String RESTAURANT_TELEPHONE = "telephone";
    public static final String RESTAURANT_SITE_INTERNET = "site_internet";
    public static final String RESTAURANT_NOTE_APPRECIATION = "note_appreciation";
    public static final String RESTAURANT_LONGITUDE = "longitude";
    public static final String RESTAURANT_LATITUDE = "latitude";
    public static final String RESTAURANT_COUT_MOYEN_REPAS = "cout_moyen_repas";
    //attributs de la table CUISINE
    public static final String CUISINE_ID = "id_cuisine";
    public static final String CUISINE_ORIGINE = "origine";
    public static final String CUISINE_DESCRIPTION = "description";
    //attributs de la table DONNEE
    public static final String DONNEE_ID = "id_donnee";
    public static final String DONNEE_ID_RESTAURANT = "id_restaurant";
    private static final String DONNEE_TYPE = "type";
    public static final String DONNEE_CONTENU = "contenu";
    //attributs de la table HORAIRE
    public static final String HORAIRE_ID = "id_horaire";
    public static final String HORAIRE_LUNDI = "lundi";
    public static final String HORAIRE_MARDI = "mardi";
    public static final String HORAIRE_MERCREDI = "mercredi";
    public static final String HORAIRE_JEUDI = "jeudi";
    public static final String HORAIRE_VENDREDI = "vendredi";
    public static final String HORAIRE_SAMEDI = "samedi";
    public static final String HORAIRE_DIMANCHE = "dimanche";
    //attributs de la table RELATION_RESTAURANT_CUISINE
    public static final String RELATION_RESTAURANT_CUISINE_ID_RESTAURANT = "id_restaurant";
    private static final String RELATION_RESTAURANT_CUISINE_ID_CUISINE = "id_cuisine";
    //attributs de la table RELATION_RESTAURANT_HORAIRE
    public static final String RELATION_RESTAURANT_HORAIRE_ID_RESTAURANT = "id_restaurant";
    private static final String RELATION_RESTAURANT_HORAIRE_ID_HORAIRE = "id_horaire";

    private static final String CREATE_TABLE_RESTAURANT =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_RESTAURANT + "(" +
                    RESTAURANT_ID + " INTEGER," +
                    RESTAURANT_NOM + " TEXT NOT NULL," +
                    RESTAURANT_ADRESSE + " TEXT NOT NULL," +
                    RESTAURANT_VILLE + " TEXT NOT NULL," +
                    RESTAURANT_PAYS + " TEXT NOT NULL," +
                    RESTAURANT_TELEPHONE + " TEXT," +
                    RESTAURANT_SITE_INTERNET + " TEXT," +
                    RESTAURANT_NOTE_APPRECIATION + " INTEGER," +
                    RESTAURANT_COUT_MOYEN_REPAS + " REAL," +
                    RESTAURANT_LATITUDE + " BLOB," + // peut etre remplacer par blob pour plus de precision car real est sur 8 bits
                    RESTAURANT_LONGITUDE + " BLOB," +
                    "PRIMARY KEY ("+RESTAURANT_NOM+", "+RESTAURANT_ADRESSE+")" +
                    ")";

    private static final String CREATE_TABLE_CUISINE =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_CUISINE + "(" +
                    CUISINE_ID + " INTEGER PRIMARY KEY," +
                    CUISINE_ORIGINE + " TEXT NOT NULL," +
                    CUISINE_DESCRIPTION + " TEXT"+")";

    private static final String CREATE_TABLE_DONNEE =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_DONNEE + "(" +
                    DONNEE_ID + " INTEGER PRIMARY KEY," +
                    DONNEE_ID_RESTAURANT + " INTEGER NOT NULL," +
                    DONNEE_TYPE + " TEXT," +
                    DONNEE_CONTENU + " TEXT,"+
                    "FOREIGN KEY ("+
                    DONNEE_ID_RESTAURANT+") REFERENCES "+TABLE_RESTAURANT+"("+RESTAURANT_ID+")"+
                    ")";

    private static final String CREATE_TABLE_HORAIRE =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_HORAIRE + "(" +
                    HORAIRE_ID + " INTEGER PRIMARY KEY,"+
                    HORAIRE_LUNDI + " TEXT NOT NULL," +
                    HORAIRE_MARDI + " TEXT NOT NULL," +
                    HORAIRE_MERCREDI + " TEXT NOT NULL," +
                    HORAIRE_JEUDI + " TEXT NOT NULL," +
                    HORAIRE_VENDREDI + " TEXT NOT NULL," +
                    HORAIRE_SAMEDI + " TEXT NOT NULL," +
                    HORAIRE_DIMANCHE + " TEXT NOT NULL"+")";

    private static final String CREATE_TABLE_RELATION_RESTAURANT_CUISINE =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_RELATION_RESTAURANT_CUISINE + "(" +
                    RELATION_RESTAURANT_CUISINE_ID_RESTAURANT + " INTEGER NOT NULL," +
                    RELATION_RESTAURANT_CUISINE_ID_CUISINE + " INTEGER NOT NULL,"+
                    "PRIMARY KEY ("
                    +RELATION_RESTAURANT_CUISINE_ID_RESTAURANT+", "
                    +RELATION_RESTAURANT_CUISINE_ID_CUISINE+")," +
                    "FOREIGN KEY ("+
                    RELATION_RESTAURANT_CUISINE_ID_RESTAURANT+") REFERENCES "+TABLE_RESTAURANT+"("+RESTAURANT_ID+"),"
                    +"FOREIGN KEY ("+
                    RELATION_RESTAURANT_CUISINE_ID_CUISINE+") REFERENCES "+TABLE_CUISINE+"("+CUISINE_ID+")"+
                    ")";

    private static final String CREATE_TABLE_RELATION_RESTAURANT_HORAIRE =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_RELATION_RESTAURANT_HORAIRE + "(" +
                    RELATION_RESTAURANT_HORAIRE_ID_RESTAURANT + " INTEGER NOT NULL," +
                    RELATION_RESTAURANT_HORAIRE_ID_HORAIRE + " INTEGER NOT NULL,"+
                    "PRIMARY KEY ("
                    +RELATION_RESTAURANT_HORAIRE_ID_RESTAURANT+", "
                    +RELATION_RESTAURANT_HORAIRE_ID_HORAIRE+"),"+
                    "FOREIGN KEY ("+
                    RELATION_RESTAURANT_HORAIRE_ID_RESTAURANT+") REFERENCES "+TABLE_RESTAURANT+"("+RESTAURANT_ID+"),"
                    +"FOREIGN KEY ("+
                    RELATION_RESTAURANT_HORAIRE_ID_HORAIRE+") REFERENCES "+TABLE_HORAIRE+"("+HORAIRE_ID+")"
                    +")";


    public Base(Context context) {
        super(context, Base.DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RESTAURANT);
        db.execSQL(CREATE_TABLE_CUISINE);
        db.execSQL(CREATE_TABLE_DONNEE);
        db.execSQL(CREATE_TABLE_HORAIRE);
        db.execSQL(CREATE_TABLE_RELATION_RESTAURANT_CUISINE);
        db.execSQL(CREATE_TABLE_RELATION_RESTAURANT_HORAIRE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if( newVersion != oldVersion) {
            db.execSQL(DROP_TABLE_RELATION_RESTAURANT_CUISINE);
            db.execSQL(DROP_TABLE_RELATION_RESTAURANT_HORAIRE);
            db.execSQL(DROP_TABLE_CUISINE);
            db.execSQL(DROP_TABLE_HORAIRE);
            db.execSQL(DROP_TABLE_DONNEE);
            db.execSQL(DROP_TABLE_RESTAURANT);
            onCreate(db);
        }
    }

/********************* INSERER TABLE **********************************/
    public boolean ajouterLigne(ContentValues v){
        return ajouterLigne((int)v.get("id_restaurant"),(String)v.get("nom"),(String)v.get("adresse"),(String)v.get("ville"),(String)v.get("pays"),(String)v.get("telephone"),(String)v.get("site_internet"),(String)v.get("note_appreciation"),(String)v.get("cout_moyen_repas"),(String)v.get("latitude"),(String)v.get("longitude"), (int)v.get("id_cuisine"),(String)v.get("origine"),(String)v.get("description"),(int)v.get("id_donnee"), (int)v.get("id_restaurant"),(String)v.get("type"),(String)v.get("contenu"),(int)v.get("id_horaire"), (String)v.get("lundi"),(String)v.get("mardi"),(String)v.get("mercredi"),(String)v.get("jeudi"),(String)v.get("vendredi"),(String)v.get("samedi"),(String)v.get("dimanche"),(int)v.get("id_restaurant"),(int)v.get("id_cuisine"),(int)v.get("id_restaurant"),(int)v.get("id_horaire"));
    }

    private boolean ajouterLigne(int id, String nom, String adresse, String ville, String pays, String telephone, String siteInternet, String noteAppreciation, String coutMoyenRepas, String latitude, String longitude, int id_cuisine, String origine, String description, int id_donnee, int idRestaurant, String type, String contenu, int id_horaire, String lundi, String mardi, String mercredi, String jeudi, String vendredi, String samedi, String dimanche, int rrcir, int rrcic, int rrhir, int rrhih){
        boolean ok;
        ok = ajouterLigneRestaurant(id,nom,adresse,ville,pays,telephone,siteInternet,noteAppreciation,coutMoyenRepas,latitude,longitude);
        ok = ok && ajouterLigneCuisine(id_cuisine,origine,description);
        ok = ok && ajouterLigneDonnee(id_donnee,idRestaurant,type,contenu);
        ok = ok && ajouterLigneHoraire(id_horaire,lundi,mardi,mercredi,jeudi,vendredi,samedi,dimanche);
        ok = ok && ajouterLigneRelationRestaurantCuisine(rrcir, rrcic);
        ok = ok && ajouterLigneRelationRestaurantHoraire(rrhir, rrhih);
        return ok;
    }

    private boolean ajouterLigneRestaurant(int id, String nom, String adresse, String ville, String pays, String telephone, String siteInternet, String noteAppreciation, String coutMoyenRepas, String latitude, String longitude){
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues row = new ContentValues();

        row.put(Base.RESTAURANT_ID,id);
        row.put(Base.RESTAURANT_NOM, nom);
        row.put(Base.RESTAURANT_ADRESSE, adresse);
        row.put(Base.RESTAURANT_VILLE, ville);
        row.put(Base.RESTAURANT_PAYS, pays);
        row.put(Base.RESTAURANT_TELEPHONE, telephone.equals("null") ? null : telephone);
        row.put(Base.RESTAURANT_SITE_INTERNET, siteInternet.equals("null")? null : siteInternet);
        row.put(Base.RESTAURANT_NOTE_APPRECIATION, noteAppreciation.equals("null")? null : Integer.parseInt(noteAppreciation));
        row.put(Base.RESTAURANT_COUT_MOYEN_REPAS, coutMoyenRepas.equals("null")? null : Double.parseDouble(coutMoyenRepas));
        row.put(Base.RESTAURANT_LATITUDE, latitude.equals("null")? null : latitude);
        row.put(Base.RESTAURANT_LONGITUDE, longitude.equals("null") ? null : longitude);
        return (bd.insert(Base.TABLE_RESTAURANT, null, row) > 0);
    }

    private boolean ajouterLigneCuisine(int id_cuisine, String origine, String description){
        SQLiteDatabase bd = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(Base.CUISINE_ID,id_cuisine);
        row.put(Base.CUISINE_ORIGINE, origine);
        row.put(Base.CUISINE_DESCRIPTION, description.equals("null")? null : description);
        return (bd.insert(Base.TABLE_CUISINE, null, row) > 0);
    }

    private boolean ajouterLigneDonnee(int id_donnee, int idRestaurant, String type, String contenu){
        SQLiteDatabase bd = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(Base.DONNEE_ID, id_donnee);
        row.put(Base.DONNEE_ID_RESTAURANT, idRestaurant);
        row.put(Base.DONNEE_TYPE, type);
        row.put(Base.DONNEE_CONTENU, contenu.equals("null")? null : contenu);
        return (bd.insert(Base.TABLE_DONNEE, null, row) > 0);
    }

    private boolean ajouterLigneHoraire(int id_horaire, String lundi, String mardi, String mercredi, String jeudi, String vendredi, String samedi, String dimanche){
        SQLiteDatabase bd = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(Base.HORAIRE_ID, id_horaire);
        row.put(Base.HORAIRE_LUNDI, lundi.equals("null")? null : lundi);
        row.put(Base.HORAIRE_MARDI, mardi.equals("null")? null : mardi);
        row.put(Base.HORAIRE_MERCREDI, mercredi.equals("null")? null : mercredi);
        row.put(Base.HORAIRE_JEUDI, jeudi.equals("null")? null : jeudi);
        row.put(Base.HORAIRE_VENDREDI, vendredi.equals("null")? null : vendredi);
        row.put(Base.HORAIRE_SAMEDI, samedi.equals("null")? null : samedi);
        row.put(Base.HORAIRE_DIMANCHE, dimanche.equals("null")? null : dimanche);
        return (bd.insert(Base.TABLE_HORAIRE, null, row) > 0);
    }

    private boolean ajouterLigneRelationRestaurantCuisine(int rrcir, int rrcic){
        SQLiteDatabase bd = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(Base.RELATION_RESTAURANT_CUISINE_ID_RESTAURANT, rrcir);
        row.put(Base.RELATION_RESTAURANT_CUISINE_ID_CUISINE, rrcic);
        return (bd.insert(Base.TABLE_RELATION_RESTAURANT_CUISINE, null, row) > 0);
    }

    private boolean ajouterLigneRelationRestaurantHoraire(int rrhir, int rrhih){
        SQLiteDatabase bd = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(Base.RELATION_RESTAURANT_HORAIRE_ID_RESTAURANT, rrhir);
        row.put(Base.RELATION_RESTAURANT_HORAIRE_ID_HORAIRE, rrhih);
        return (bd.insert(Base.TABLE_RELATION_RESTAURANT_HORAIRE, null, row) > 0);
    }
    /*********************************************************/




    /******************* MODIFIER TABLE ***********************/
    public boolean modifierLigne(ContentValues v){
        return modifierLigne((int)v.get("id_restaurant"),(String)v.get("nom"),(String)v.get("adresse"),(String)v.get("ville"),(String)v.get("pays"),(String)v.get("telephone"),(String)v.get("site_internet"),(String)v.get("note_appreciation"),(String)v.get("cout_moyen_repas"),(String)v.get("latitude"),(String)v.get("longitude"), (int)v.get("id_cuisine"),(String)v.get("origine"),(String)v.get("description"),(int)v.get("id_donnee"), (int)v.get("id_restaurant"),(String)v.get("type"),(String)v.get("contenu"),(int)v.get("id_horaire"), (String)v.get("lundi"),(String)v.get("mardi"),(String)v.get("mercredi"),(String)v.get("jeudi"),(String)v.get("vendredi"),(String)v.get("samedi"),(String)v.get("dimanche"));
    }

    private boolean modifierLigne(int id, String nom, String adresse, String ville, String pays, String telephone, String siteInternet, String noteAppreciation, String coutMoyenRepas, String latitude, String longitude, int id_cuisine, String origine, String description, int id_donnee, int idRestaurant, String type, String contenu, int id_horaire, String lundi, String mardi, String mercredi, String jeudi, String vendredi, String samedi, String dimanche){
        boolean ok;
        ok = modifierLigneRestaurant(id,nom,adresse,ville,pays,telephone,siteInternet,noteAppreciation,coutMoyenRepas,latitude,longitude);
        ok = ok && modifierLigneCuisine(id_cuisine, origine, description);
        ok = ok && modifierLigneDonnee(id_donnee, idRestaurant, type, contenu);
        ok = ok && modifierLigneHoraire(id_horaire,lundi,mardi,mercredi,jeudi,vendredi,samedi,dimanche);
        return ok;
    }

    private boolean modifierLigneRestaurant(int id, String nom, String adresse, String ville, String pays, String telephone, String siteInternet, String noteAppreciation, String coutMoyenRepas, String latitude, String longitude){
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(Base.RESTAURANT_NOM, nom);
        row.put(Base.RESTAURANT_ADRESSE, adresse);
        row.put(Base.RESTAURANT_VILLE, ville);
        row.put(Base.RESTAURANT_PAYS, pays);
        row.put(Base.RESTAURANT_TELEPHONE,telephone.equals("null") ? null : telephone);
        row.put(Base.RESTAURANT_SITE_INTERNET,siteInternet.equals("null")? null : siteInternet);
        row.put(Base.RESTAURANT_NOTE_APPRECIATION, noteAppreciation.equals("null")? null : Integer.parseInt(noteAppreciation));
        row.put(Base.RESTAURANT_COUT_MOYEN_REPAS, coutMoyenRepas.equals("null")? null : Double.parseDouble(coutMoyenRepas));
        row.put(Base.RESTAURANT_LATITUDE, latitude.equals("null")? null : latitude);
        row.put(Base.RESTAURANT_LONGITUDE, longitude.equals("null")? null : longitude);

        int nb = bd.update(Base.TABLE_RESTAURANT,row, Base.RESTAURANT_ID +" = ?" , new String[]{id+""});
        return  nb > 0;
    }

    private boolean modifierLigneCuisine(int id_cuisine, String origine, String description){
        SQLiteDatabase bd = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(Base.CUISINE_ORIGINE, origine);
        row.put(Base.CUISINE_DESCRIPTION, description.equals("null")?null : description);
        return (bd.update(Base.TABLE_CUISINE, row, Base.CUISINE_ID + " = ?", new String[]{id_cuisine+""}) > 0);
    }

    private boolean modifierLigneDonnee(int id_donnee, int idRestaurant, String type, String contenu){
        SQLiteDatabase bd = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(Base.DONNEE_ID_RESTAURANT, idRestaurant);
        row.put(Base.DONNEE_TYPE, type);
        row.put(Base.DONNEE_CONTENU, contenu.equals("null")? null : contenu);
        return (bd.update(Base.TABLE_DONNEE, row, Base.DONNEE_ID + " = ?" , new String[]{id_donnee+""}) > 0);
    }

    private boolean modifierLigneHoraire(int id_horaire, String lundi, String mardi, String mercredi, String jeudi, String vendredi, String samedi, String dimanche){
        SQLiteDatabase bd = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(Base.HORAIRE_LUNDI, lundi.equals("null")? null : lundi);
        row.put(Base.HORAIRE_MARDI, mardi.equals("null")? null : mardi);
        row.put(Base.HORAIRE_MERCREDI, mercredi.equals("null")? null : mercredi);
        row.put(Base.HORAIRE_JEUDI, jeudi.equals("null")? null : jeudi);
        row.put(Base.HORAIRE_VENDREDI, vendredi.equals("null")? null : vendredi);
        row.put(Base.HORAIRE_SAMEDI, samedi.equals("null")? null : samedi);
        row.put(Base.HORAIRE_DIMANCHE, dimanche.equals("null")? null : dimanche);
        return (bd.update(Base.TABLE_HORAIRE, row, Base.HORAIRE_ID + " = ?" , new  String[]{id_horaire+""}) > 0);
    }
/*************************************************************************/
}