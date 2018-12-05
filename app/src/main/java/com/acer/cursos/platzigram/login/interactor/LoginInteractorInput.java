package com.acer.cursos.platzigram.login.interactor;

import android.app.Activity;

import com.acer.cursos.platzigram.login.presenter.LoginPresenter;
import com.acer.cursos.platzigram.login.repository.LoginRepository;
import com.acer.cursos.platzigram.login.repository.LoginRepositoryInput;
import com.google.firebase.auth.FirebaseAuth;

public class LoginInteractorInput implements LoginInterator {


    private LoginPresenter   loginPresenter;
    private LoginRepository  loginRepository;

    public LoginInteractorInput(LoginPresenter loginPresenter) {
        this.loginPresenter =  loginPresenter;
        loginRepository     =  new LoginRepositoryInput(loginPresenter);
    }

    @Override
    public void signIn(String username, String password, Activity activity,FirebaseAuth firebaseAuth) {
        loginRepository.signIn(username,password,activity,firebaseAuth);
    }
}
