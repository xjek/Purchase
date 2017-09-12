package ru.groupstp.procurementcommission.presentation.presenter.login;

import android.content.Context;
import android.widget.EditText;

import com.arellomobile.mvp.InjectViewState;

import ru.groupstp.procurementcommission.R;
import ru.groupstp.procurementcommission.connection.LoginConnection;
import ru.groupstp.procurementcommission.model.db.UserDB;
import ru.groupstp.procurementcommission.model.User;

@InjectViewState
public class LoginDefaultPresenter extends LoginPresenter {

    public User getAuth() {
        return UserDB.getAuth();
    }

    public void enter(Context context, EditText login, EditText password) {
        String loginString = login.getText().toString();
        String passString = password.getText().toString();

        if (loginString.isEmpty() || passString.isEmpty()) {
            getViewState().showError(R.string.errorEmptyLoginPass);
            return;
        }
        connection(context, new LoginConnection(this), false, loginString, passString);
    }
}
