package com.acer.cursos.platzigram.post.view;

import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.acer.cursos.platzigram.PlatzigramAplication;
import com.acer.cursos.platzigram.R;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class DetallesImagenActivity extends AppCompatActivity {

    private static final String TAG = "DetallesImagenActivity";
    private ImageView imageView;
    private PlatzigramAplication app;
    private StorageReference storageReference;
    private String phto_name="JPEG_20181203_21-15-25_7039043765451334844.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_imagen);

        Crashlytics.log("Inicializando" + TAG);

        app=(PlatzigramAplication)getApplicationContext();
        storageReference=app.getReferenceStorage();

        imageView=(ImageView)findViewById(R.id.IDimageDetalle);

        showtoolbar("",true);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
          getWindow().setEnterTransition(new Fade());
            getWindow().setStatusBarColor(Color.TRANSPARENT);

            showData();
        }
    }

    private void showData() {

        storageReference.child("PostImages/" + phto_name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Picasso.get().load(uri.toString()).into(imageView);
                Toast.makeText(DetallesImagenActivity.this,"Se trajo la foto",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Crashlytics.logException(e);
                Toast.makeText(DetallesImagenActivity.this,"Error al traer la imagen" + e.toString(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();

            }
        });
    }


    public void showtoolbar(String titulo, boolean upbutton){
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upbutton);
        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.IDcollapsinToolbar);

    }
}
