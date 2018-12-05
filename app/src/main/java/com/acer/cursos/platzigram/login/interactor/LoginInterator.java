package com.acer.cursos.platzigram.login.interactor;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

public interface LoginInterator {

    void signIn(String username, String password, Activity activity,FirebaseAuth firebaseAuth);
}
