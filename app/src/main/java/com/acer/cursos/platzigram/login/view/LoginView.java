package com.acer.cursos.platzigram.login.view;

import android.view.View;

public interface LoginView {

    void EnableInputs();
    void disableInputs();

    void showProgressBar();
    void hideProgressBar();

    void showError(String error);

    void onClickLogin();
    void onClickImage();
    void onClickTexView();
}
