package com.acer.cursos.platzigram.login.presenter;

import android.app.Activity;

import com.acer.cursos.platzigram.login.interactor.LoginInteractorInput;
import com.acer.cursos.platzigram.login.interactor.LoginInterator;
import com.acer.cursos.platzigram.login.view.LoginView;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenterInput implements LoginPresenter {

    private LoginView loginView;
    private LoginInterator loginInterator;

    public LoginPresenterInput(LoginView loginView) {
        this.loginView = loginView;
        loginInterator= new LoginInteractorInput(this);
    }

    @Override
    public void signIn(String username, String password, Activity activity,FirebaseAuth firebaseAuth) {
        loginView.disableInputs();
        loginView.showProgressBar();
      loginInterator.signIn(username,password,activity,firebaseAuth);
    }

    @Override
    public void LoginSuccess() {
     loginView.onClickLogin();
     loginView.hideProgressBar();
    }

    @Override
    public void LoginError(String error) {
        loginView.hideProgressBar();
        loginView.EnableInputs();
        loginView.showError(error);

    }
}
