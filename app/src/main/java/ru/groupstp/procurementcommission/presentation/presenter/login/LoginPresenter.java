package ru.groupstp.procurementcommission.presentation.presenter.login;

import ru.groupstp.procurementcommission.connection.listener.LoginConnectionListener;

import ru.groupstp.procurementcommission.model.User;
import ru.groupstp.procurementcommission.model.db.UserDB;
import ru.groupstp.procurementcommission.presentation.presenter.ConnectionPresenter;
import ru.groupstp.procurementcommission.presentation.view.login.LoginView;

abstract class LoginPresenter extends ConnectionPresenter<LoginView> implements LoginConnectionListener {

    @Override
    public void successConnection(String[] data, String... input) {
        String token = data[0];
        String id = data[1];
        String userName = data[2];
        String userEmail = data[3];
        String vote = data[4];
        getViewState().setToken(token);

        User auth = new User();
        auth.setId(id);
        auth.setUsername(userName);
        auth.setUserEmail(userEmail);
        auth.setVote(vote);
        if (input.length > 0) {
            auth.setLogin(input[0]);
            auth.setPassword(input[1]);
        }
        UserDB.saveAuth(auth);
    }
}
