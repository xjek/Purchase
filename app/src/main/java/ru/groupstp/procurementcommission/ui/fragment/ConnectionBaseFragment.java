package ru.groupstp.procurementcommission.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import ru.groupstp.procurementcommission.R;
import ru.groupstp.procurementcommission.app.DialogLoad;
import ru.groupstp.procurementcommission.presentation.view.ConnectionView;
import ru.groupstp.procurementcommission.ui.activity.login.LoginActivity;

/**
 * Базовый класс в котором происходит подключение
 */
public abstract class ConnectionBaseFragment extends BaseFragment implements ConnectionView {

    private DialogLoad mDialogLoad = new DialogLoad();

    private AlertDialog mBuilder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBuilder = dialogFragment();
    }

    public void showError(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading(int text) {
        showLoading(getContext().getString(text));
    }

    @Override
    public void showError(int text) {
        showError(getContext().getString(text));
    }

    public void showLoading() {
        if (!mBuilder.isShowing())
            mBuilder.show();
    }

    public void hideLoading() {
        mBuilder.dismiss();
    }

    public void showLoading(String text) {
        mDialogLoad.setTextLoading(text);
        showLoading();
    }

    @Override
    public void errorAuth() {
        showError(R.string.error_login);
    }

    protected void startLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void noNetwork() {

    }

    private AlertDialog dialogFragment() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(R.layout.dialog_loading)
                .setCancelable(false)
                .create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return alertDialog;
    }
}
