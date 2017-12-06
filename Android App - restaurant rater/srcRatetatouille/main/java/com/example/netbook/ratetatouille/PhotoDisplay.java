package com.example.netbook.ratetatouille;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;

public class PhotoDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_display);
        ImageView imageResto = (ImageView) findViewById(R.id.photoGrande);
        Intent i = getIntent();
        String path = i.getStringExtra("path");
        File file = new File(Environment.getExternalStorageDirectory(),path);//on recupere le fichier contenant l'image

        if(file.exists()) { //si le fichier existe
            Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
            imageResto.setImageBitmap(bm); //on met le bitmap dans l'ImageView
            TouchImageView img = new TouchImageView(this); //on creer le TouchImageViewqui permet de zoomer la photo avec les doigts
            img.setImageBitmap(bm); //on met le bitmap dans le TouchImageView
            img.setMaxZoom(4f); //on defini le zoom maximal
            setContentView(img);
        }
        else {
            Log.d("photo", "aucune photo trouvée");
        }
    }
}