package ru.groupstp.procurementcommission.presentation.view;

import com.arellomobile.mvp.MvpView;

public interface NewConnectionView extends MvpView {

    void showLoading();

    void hideLoading();

    void showError(String text);

    void showError(int resString);

    void noNetwork();
}
