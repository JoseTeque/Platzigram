package com.acer.cursos.platzigram.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.acer.cursos.platzigram.R;
import com.acer.cursos.platzigram.fragments.BurcarFragment;
import com.acer.cursos.platzigram.login.view.FormularioActivity;
import com.acer.cursos.platzigram.login.view.LoginActivity;
import com.acer.cursos.platzigram.post.view.HomeFragment;
import com.acer.cursos.platzigram.fragments.PerfilFragment;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ContenedorActivity extends AppCompatActivity{

    private static final String TAG = "ContenedorActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor);

        firebaseInicializar();
        BottomNavigationView bottomNavigationView=findViewById(R.id.IDbottonBar);
        bottomNavigationView.setSelectedItemId(R.id.IDinicio);
        getSupportFragmentManager().beginTransaction().replace(R.id.IDcontainer,new HomeFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

    }

    private void firebaseInicializar(){
        firebaseAuth= FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
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

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment selecFragment= null;

        switch (menuItem.getItemId()){

            case R.id.IDinicio:
                selecFragment= new HomeFragment();
                break;

            case R.id.IDBuscar:
                selecFragment= new BurcarFragment();
                break;

            case R.id.IDPerfil:
                selecFragment= new PerfilFragment();
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.IDcontainer,selecFragment).commit();
        return true;

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_opciones,menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.mSignOut:

                firebaseAuth.signOut();

                if(LoginManager.getInstance()!= null){
                    LoginManager.getInstance().logOut();
                }
                Toast.makeText(this,"Cerro la sesion",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(ContenedorActivity.this,LoginActivity.class);
                startActivity(intent);

                break;

            case R.id.mAbout:

                Toast.makeText(this,"Platzigram by platzi",Toast.LENGTH_SHORT).show();
                break;

        }


        return true;
    }
}