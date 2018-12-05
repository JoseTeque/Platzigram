package com.acer.cursos.platzigram.post.view;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.acer.cursos.platzigram.PlatzigramAplication;
import com.acer.cursos.platzigram.R;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class NewPostActivity extends AppCompatActivity {

    private static final String TAG ="NewPostActivity" ;
    private ImageView imageView;
    private Button buttonPost;
    private String phtoPath;
    private PlatzigramAplication app;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        app=(PlatzigramAplication)getApplicationContext();

       storageReference= app.getReferenceStorage();

        imageView=(ImageView)findViewById(R.id.IDimageNewPost);
        buttonPost=(Button)findViewById(R.id.IDCrearPOST);

        if(getIntent().getExtras()!= null){
            phtoPath= getIntent().getExtras().getString("PHOTO_PATH_TEMP");
            showPhoto();
        }

        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoto();
            }
        });
    }

    private void uploadPhoto() {

        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();

        Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);

        byte[] photobyte = baos.toByteArray();

        String photoName= phtoPath.substring(phtoPath.lastIndexOf("/")+1, phtoPath.length());

        final StorageReference photoReference= storageReference.child("PostImages/" + photoName);

        UploadTask uploadTask= photoReference.putBytes(photobyte);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                 Crashlytics.logException(e);
                Log.w(TAG,"Photo upload error " + e.toString());
                e.printStackTrace();


            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

             photoReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                 @Override
                 public void onSuccess(Uri uri) {
                     String photoUrl= uri.toString();
                     Log.w(TAG,"Photo Url > " + photoUrl);
                     finish();
                 }
             });

            }
        });

    }


    public void showPhoto(){
        Picasso.get().load(phtoPath).into(imageView);
    }
}
