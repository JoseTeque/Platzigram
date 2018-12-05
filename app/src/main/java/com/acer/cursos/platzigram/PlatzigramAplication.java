package com.acer.cursos.platzigram;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PlatzigramAplication extends Application {


    private static final String TAG = "LoginActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseStorage firebaseStorage;


    @Override
    public void onCreate() {
        super.onCreate();

        Crashlytics.log("Estamos en PlatzigramApplication inicializando variables");

        firebaseAuth= FirebaseAuth.getInstance();
        authStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();

                if(firebaseUser!=null){
                    Log.w(TAG,"El usuario fue logueado"+ firebaseUser.getEmail());
                }else{
                    Log.w(TAG,"El usuario no fue logueado");

                }
            }
        };

        firebaseStorage= FirebaseStorage.getInstance();
    }


    public StorageReference getReferenceStorage(){

        return firebaseStorage.getReference();
    }
}
