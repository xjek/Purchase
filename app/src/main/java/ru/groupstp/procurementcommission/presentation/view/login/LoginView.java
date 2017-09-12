package ru.groupstp.procurementcommission.presentation.view.login;

import android.content.Intent;

import ru.groupstp.procurementcommission.presentation.view.NewConnectionView;

public interface LoginView extends NewConnectionView {
    void setToken(String token);
    void startNewActivity();
    void startGoogleAuthActivity(Intent intent);
}
