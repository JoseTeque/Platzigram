package com.acer.cursos.platzigram.login.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.acer.cursos.platzigram.R;
import com.acer.cursos.platzigram.login.presenter.LoginPresenter;
import com.acer.cursos.platzigram.login.presenter.LoginPresenterInput;
import com.acer.cursos.platzigram.view.ContenedorActivity;
import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class LoginActivity extends AppCompatActivity implements LoginView{

    private TextInputEditText nombreUsuario, password;
    private Button login;
    private ProgressBar progressBarLogin;
    private LoginPresenter loginPresenter;

    private static final String TAG = "LoginActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private CallbackManager callbackManager;
    private LoginButton loginButtonfacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager= CallbackManager.Factory.create();

        firebaseAuth= FirebaseAuth.getInstance();
        authStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();

                if(firebaseUser!=null){
                    Crashlytics.log(Log.WARN,TAG,"El usuario fue logueado"+ firebaseUser.getEmail());
                    Log.w(TAG,"El usuario fue logueado"+ firebaseUser.getEmail());
                    onClickLogin();
                }else{
                    Crashlytics.log(Log.WARN,TAG,"El usuario no fue logueado");

                    Log.w(TAG,"El usuario no fue logueado");

                }
            }
        };

        nombreUsuario=(TextInputEditText)findViewById(R.id.IDnombre);
        password=(TextInputEditText)findViewById(R.id.IDpassword);
        login=(Button) findViewById(R.id.IDbutton);
        loginButtonfacebook=(LoginButton)findViewById(R.id.login_facebook);
        progressBarLogin=(ProgressBar)findViewById(R.id.IDprogressbarLogin);
        hideProgressBar();

        loginPresenter= new LoginPresenterInput(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nombreUsuario.getText().toString().equals("") || password.getText().toString().equals("")){
                    showError("Ingrese los datos");
                }else{
                    sigIn(nombreUsuario.getText().toString(),password.getText().toString());

                }

            }
        });

        loginButtonfacebook.setReadPermissions(Arrays.asList("email"));
        loginButtonfacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Crashlytics.log(Log.WARN,TAG,"Facebook login succes toke: " + loginResult.getAccessToken().getApplicationId());

                Log.w(TAG,"Facebook login succes toke: " + loginResult.getAccessToken().getApplicationId());
             signInfacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Crashlytics.log(Log.WARN,TAG,"Facebook login succes toke: " );

                Log.w(TAG,"Facebook login cancelado: " );

            }

            @Override
            public void onError(FacebookException error) {
                Crashlytics.logException(error);
                Log.w(TAG,"Facebook login Error: " +error.toString());
                 error.printStackTrace();
            }
        });
    }

    private void signInfacebook(AccessToken accessToken) {

        AuthCredential authCredential= FacebookAuthProvider.getCredential(accessToken.getToken());

        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    FirebaseUser user= task.getResult().getUser();

                    SharedPreferences preferences= getSharedPreferences("USER", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor= preferences.edit();
                    editor.putString("email",user.getEmail());
                    editor.commit();

                  onClickLogin();
                  Crashlytics.log("Login facebook exitoso");
                  Toast.makeText(LoginActivity.this,"Login facebook exitoso",Toast.LENGTH_SHORT).show();

                }else{
                    Crashlytics.log("Login facebook no exitoso");
                    Toast.makeText(LoginActivity.this,"Login no exitoso",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void sigIn(String username, String password) {
        loginPresenter.signIn(username,password,this,firebaseAuth);

    }

    @Override
    public void EnableInputs() {

        nombreUsuario.setEnabled(true);
        password.setEnabled(true);
        login.setEnabled(true);

    }

    @Override
    public void disableInputs() {
        nombreUsuario.setEnabled(false);
        password.setEnabled(false);
        login.setEnabled(false);
    }

    @Override
    public void showProgressBar() {
       progressBarLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBarLogin.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {

      Toast.makeText(this,"Ocurrio este error: "+ error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickLogin() {
        Intent intent = new Intent(LoginActivity.this, ContenedorActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClickImage() {
        Intent intent_dos= new Intent(Intent.ACTION_VIEW);
        intent_dos.setData(Uri.parse("https://platzi.com"));
        startActivity(intent_dos);
    }

    @Override
    public void onClickTexView() {
        Intent intent_uno = new Intent(LoginActivity.this, FormularioActivity.class);
        startActivity(intent_uno);
    }

    public void onClickTexView(View view) {
        onClickTexView();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
