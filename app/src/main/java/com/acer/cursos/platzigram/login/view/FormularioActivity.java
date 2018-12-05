package com.acer.cursos.platzigram.login.view;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.acer.cursos.platzigram.R;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FormularioActivity extends AppCompatActivity {

    private static final String TAG = "FormularioActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private TextInputEditText Email,Password;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        showtoolbar(getResources().getString(R.string.toolbar_titulo), true);

        button=(Button)findViewById(R.id.IDunete);
        Email=(TextInputEditText)findViewById(R.id.IDcorreo);
        Password=(TextInputEditText)findViewById(R.id.IDcontraseña);

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

       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Crearcuenta();

           }
       });

    }


    public void showtoolbar(String titulo, boolean upbutton){
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upbutton);

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    public void Crearcuenta(){

        String mail= Email.getText().toString();
        String correo= Password.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(mail,correo).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(FormularioActivity.this,"cuenta creada exitosamente",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(FormularioActivity.this,"Ocurrio un error al crear la cuenta",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
