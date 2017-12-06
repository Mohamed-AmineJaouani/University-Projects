package com.example.netbook.ratetatouillebasecontentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.List;

public class RatetatouilleContentProvider extends ContentProvider {
    private static final String AUTHORITY = "com.example.netbook.ratetatouillebasecontentprovider";
    private Base base = new Base(this.getContext());
    private SQLiteDatabase db;
    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    final String CONTENT_TYPE = "vnd.android.cursor.dir/restaurants";
    final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/restaurant";

    private final static int ALL = 0;
    private final static int NOM_ADRESSE = 1;
    private final static int NOM_VILLE = 2;
    private final static int NOM_PAYS = 3;
    private final static int NOM = 4;
    private final static int ADRESSE = 5;
    private final static int VILLE = 6;
    private final static int PAYS = 7;
    private final static int SPECIALITE = 8;
    private final static int NOTE = 9;
    private final static int DETAILS = 10;
    private final static int TEST = 11;
    //final static int ZONE = 9;

    static {
        matcher.addURI(AUTHORITY, "tout", ALL);
        matcher.addURI(AUTHORITY, "nom_adresse/*/*", NOM_ADRESSE);
        matcher.addURI(AUTHORITY, "nom_ville/*/*", NOM_VILLE);
        matcher.addURI(AUTHORITY, "nom_pays/*/*", NOM_PAYS);
        matcher.addURI(AUTHORITY, "nom/*", NOM);
        matcher.addURI(AUTHORITY, "adresse/*", ADRESSE);
        matcher.addURI(AUTHORITY, "ville/*", VILLE);
        matcher.addURI(AUTHORITY, "pays/*", PAYS);
        matcher.addURI(AUTHORITY, "specialite/*", SPECIALITE);
        matcher.addURI(AUTHORITY, "note/#", NOTE);
        matcher.addURI(AUTHORITY, "details/*/*", DETAILS);
        matcher.addURI(AUTHORITY, "test/*/*", TEST);
        //matcher.addURI(AUTHORITY, "zone/*", ZONE); //Tour eiffel, metro 14, ...
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        db = base.getWritableDatabase();
        db.delete(Base.TABLE_RELATION_RESTAURANT_HORAIRE,Base.RELATION_RESTAURANT_HORAIRE_ID_RESTAURANT+" IN ( SELECT "+Base.RESTAURANT_ID+" FROM "+Base.TABLE_RESTAURANT+" WHERE "+selection+")",selectionArgs);
        db.delete(Base.TABLE_RELATION_RESTAURANT_CUISINE,Base.RELATION_RESTAURANT_CUISINE_ID_RESTAURANT+" IN ( SELECT "+Base.RESTAURANT_ID+" FROM "+Base.TABLE_RESTAURANT+" WHERE "+selection+")",selectionArgs);
        db.delete(Base.TABLE_DONNEE,Base.DONNEE_ID_RESTAURANT+" IN ( SELECT "+Base.RESTAURANT_ID+" FROM "+Base.TABLE_RESTAURANT+" WHERE "+selection+")",selectionArgs);
        return db.delete(Base.TABLE_RESTAURANT,selection,selectionArgs);
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (matcher.match(uri)) {
            case ALL:
                return CONTENT_TYPE;
            case NOM_ADRESSE:
                return CONTENT_ITEM_TYPE;
            case NOM_VILLE:
                return CONTENT_TYPE;
            case NOM_PAYS:
                return CONTENT_TYPE;
            case ADRESSE:
                return CONTENT_TYPE;
            case NOM:
                return CONTENT_TYPE;
            case VILLE:
                return CONTENT_TYPE;
            case PAYS:
                return CONTENT_TYPE;
            case SPECIALITE:
                return CONTENT_TYPE;
            case NOTE:
                return CONTENT_TYPE;
            case DETAILS:
                return CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        boolean ok = base.ajouterLigne(values);
        if(!ok)
            return null;
        else
            return Uri.withAppendedPath(uri,"true");
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        if(base.modifierLigne(values))
            return 1;
        else
            return 0;
    }

    @Override
    public boolean onCreate() {
        try {
            base = new Base(getContext());
        }catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        db = base.getReadableDatabase();
        int code = matcher.match(uri);
        List<String> liste;
        liste = uri.getPathSegments();
        switch (code) {
            case ALL:
                return db.query(Base.TABLE_RESTAURANT,
                        new String[]{Base.RESTAURANT_ID+" as _id", Base.RESTAURANT_NOM, Base.RESTAURANT_ADRESSE, Base.RESTAURANT_VILLE, Base.RESTAURANT_PAYS},
                        null,null,null,null,null,null);

            case NOM_ADRESSE:
                return db.query(true, Base.TABLE_RESTAURANT,
                        new String[]{Base.RESTAURANT_ID+" as _id",Base.RESTAURANT_NOM, Base.RESTAURANT_ADRESSE, Base.RESTAURANT_VILLE, Base.RESTAURANT_PAYS},
                        Base.RESTAURANT_NOM +" LIKE '%"+liste.get(1)+"%' AND "+Base.RESTAURANT_ADRESSE+" LIKE '%"+liste.get(2)+"%'",
                        null,null,null,null,null);
            case NOM_VILLE:
                return db.query(true, Base.TABLE_RESTAURANT,
                        new String[]{Base.RESTAURANT_ID+" as _id",Base.RESTAURANT_NOM, Base.RESTAURANT_ADRESSE, Base.RESTAURANT_VILLE, Base.RESTAURANT_PAYS},
                        Base.RESTAURANT_NOM +" LIKE '%"+liste.get(1)+"%' AND "+Base.RESTAURANT_VILLE+" LIKE '%"+liste.get(2)+"%'",
                        null,null,null,null,null);
            case NOM_PAYS:
                return db.query(true, Base.TABLE_RESTAURANT,
                        new String[]{Base.RESTAURANT_ID+" as _id",Base.RESTAURANT_NOM, Base.RESTAURANT_ADRESSE, Base.RESTAURANT_VILLE, Base.RESTAURANT_PAYS},
                        Base.RESTAURANT_NOM +" LIKE '%"+liste.get(1)+"%' AND "+Base.RESTAURANT_PAYS+" LIKE '%"+liste.get(2)+"%'",
                        null,null,null,null,null);
            case NOM:
                return db.query(true, Base.TABLE_RESTAURANT,
                        new String[]{Base.RESTAURANT_ID+" as _id",Base.RESTAURANT_NOM, Base.RESTAURANT_ADRESSE, Base.RESTAURANT_VILLE, Base.RESTAURANT_PAYS},
                        Base.RESTAURANT_NOM +" LIKE '%"+liste.get(1)+"%'",
                        null,null,null,null,null);
            case ADRESSE:
                return db.query(true, Base.TABLE_RESTAURANT,
                        new String[]{Base.RESTAURANT_ID+" as _id",Base.RESTAURANT_NOM, Base.RESTAURANT_ADRESSE, Base.RESTAURANT_VILLE, Base.RESTAURANT_PAYS},
                        Base.RESTAURANT_ADRESSE +" LIKE '%"+liste.get(1)+"%'",
                        null,null,null,null,null);
            case VILLE:
                return db.query(true, Base.TABLE_RESTAURANT,
                        new String[]{Base.RESTAURANT_ID+" as _id",Base.RESTAURANT_NOM, Base.RESTAURANT_ADRESSE, Base.RESTAURANT_VILLE, Base.RESTAURANT_PAYS},
                        Base.RESTAURANT_VILLE +" LIKE '%"+liste.get(1)+"%'",
                        null,null,null,null,null);
            case PAYS:
                return db.query(true, Base.TABLE_RESTAURANT,
                        new String[]{Base.RESTAURANT_ID+" as _id",Base.RESTAURANT_NOM, Base.RESTAURANT_ADRESSE, Base.RESTAURANT_VILLE, Base.RESTAURANT_PAYS},
                        Base.RESTAURANT_PAYS +" LIKE '%"+liste.get(1)+"%'",
                        null,null,null,null,null);
            case SPECIALITE:
                return db.query(true, Base.TABLE_RESTAURANT+", "+Base.TABLE_CUISINE,
                        new String[]{Base.RESTAURANT_ID+" as _id",Base.RESTAURANT_NOM, Base.RESTAURANT_ADRESSE, Base.RESTAURANT_VILLE, Base.RESTAURANT_PAYS},
                        Base.CUISINE_ORIGINE+" LIKE '%"+liste.get(1)+"%'",
                        null,null,null,null,null);
            case NOTE:
                return db.query(true, Base.TABLE_RESTAURANT,
                        new String[]{Base.RESTAURANT_ID+" as _id",Base.RESTAURANT_NOM, Base.RESTAURANT_ADRESSE, Base.RESTAURANT_VILLE, Base.RESTAURANT_PAYS},
                        Base.RESTAURANT_NOTE_APPRECIATION+" = '"+liste.get(1)+"'",
                        null,null,null,null,null);
            case DETAILS:
                String[] col = new String[]{
                        Base.TABLE_RESTAURANT+"."+Base.RESTAURANT_ID+" as _id",
                        Base.TABLE_RESTAURANT+"."+Base.RESTAURANT_NOM,
                        Base.TABLE_RESTAURANT+"."+Base.RESTAURANT_ADRESSE,
                        Base.TABLE_RESTAURANT+"."+Base.RESTAURANT_VILLE,
                        Base.TABLE_RESTAURANT+"."+Base.RESTAURANT_PAYS,
                        Base.TABLE_RESTAURANT+"."+Base.RESTAURANT_TELEPHONE,
                        Base.TABLE_RESTAURANT+"."+Base.RESTAURANT_SITE_INTERNET,
                        Base.TABLE_RESTAURANT+"."+Base.RESTAURANT_NOTE_APPRECIATION,
                        Base.TABLE_RESTAURANT+"."+Base.RESTAURANT_COUT_MOYEN_REPAS,
                        Base.TABLE_RESTAURANT+"."+Base.RESTAURANT_LATITUDE,
                        Base.TABLE_RESTAURANT+"."+Base.RESTAURANT_LONGITUDE,
                        Base.TABLE_CUISINE+"."+Base.CUISINE_ORIGINE,
                        Base.TABLE_CUISINE+"."+Base.CUISINE_DESCRIPTION,
                        Base.TABLE_DONNEE+"."+Base.DONNEE_CONTENU ,
                        Base.TABLE_HORAIRE+"."+Base.HORAIRE_LUNDI,
                        Base.TABLE_HORAIRE+"."+Base.HORAIRE_MARDI,
                        Base.TABLE_HORAIRE+"."+Base.HORAIRE_MERCREDI,
                        Base.TABLE_HORAIRE+"."+Base.HORAIRE_JEUDI,
                        Base.TABLE_HORAIRE+"."+Base.HORAIRE_VENDREDI,
                        Base.TABLE_HORAIRE+"."+Base.HORAIRE_SAMEDI,
                        Base.TABLE_HORAIRE+"."+Base.HORAIRE_DIMANCHE
                };

                return db.query(true, Base.TABLE_RESTAURANT+" INNER JOIN "+Base.TABLE_CUISINE
                                +" ON "+Base.TABLE_RESTAURANT+"."+Base.RESTAURANT_ID+"="+Base.TABLE_CUISINE+"."+Base.CUISINE_ID
                                +" INNER JOIN "+Base.TABLE_DONNEE
                                +" ON "+Base.DONNEE_ID+"="+Base.CUISINE_ID
                                +" INNER JOIN "+Base.TABLE_HORAIRE
                                +" ON "+Base.HORAIRE_ID+"="+Base.CUISINE_ID,
                        col,
                        Base.TABLE_RESTAURANT+"."+Base.RESTAURANT_NOM +" = \"" +liste.get(1)
                                +"\" AND "
                                +Base.TABLE_RESTAURANT+"."+Base.RESTAURANT_ADRESSE+" = '"+liste.get(2)+"'",
                        null,null,null,null,null);
            case TEST:
                return db.query(true, Base.TABLE_CUISINE+", "+Base.TABLE_DONNEE,
                        new String[]{Base.CUISINE_ORIGINE, Base.CUISINE_DESCRIPTION, Base.DONNEE_CONTENU},
                        Base.CUISINE_ID
                                + " IN ( SELECT "+Base.RESTAURANT_ID
                                +" FROM "+Base.TABLE_RESTAURANT
                                +" WHERE "+Base.RESTAURANT_NOM +" = '"+liste.get(1)
                                +"' AND "+Base.RESTAURANT_ADRESSE+" = '"+liste.get(2)
                                +"')",
                        null,null,null,null,null);
            default:
                return null;
        }
    }
}