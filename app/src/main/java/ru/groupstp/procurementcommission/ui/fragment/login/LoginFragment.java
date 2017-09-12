package ru.groupstp.procurementcommission.ui.fragment.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import ru.groupstp.procurementcommission.R;
import ru.groupstp.procurementcommission.app.QueryPreferences;

import ru.groupstp.procurementcommission.model.User;
import ru.groupstp.procurementcommission.presentation.presenter.login.LoginDefaultPresenter;

import ru.groupstp.procurementcommission.presentation.presenter.login.LoginGooglePresenter;
import ru.groupstp.procurementcommission.presentation.view.login.LoginView;
import ru.groupstp.procurementcommission.ui.activity.nomenclature.NomenclatureActivity;
import ru.groupstp.procurementcommission.ui.fragment.ConnectionBaseFragment;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.Calendar;

public class LoginFragment extends ConnectionBaseFragment implements LoginView {

    @InjectPresenter
    LoginDefaultPresenter mDefaultLoginPresenter;

    @InjectPresenter
    LoginGooglePresenter mGoogleLoginPresenter;

    private static final int RC_SIGN_IN = 707;

    @Override
    public int layout() {
        return R.layout.fragment_login;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleLoginPresenter.init(getActivity());
        test();
    }

    public void test() {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime());
        calendar.add(Calendar.DATE, -12);
        System.out.println(calendar.getTime());
    }


    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        EditText login = (EditText) view.findViewById(R.id.login);
        EditText password = (EditText) view.findViewById(R.id.password);
        Button enter = (Button) view.findViewById(R.id.enter);

        User auth = mDefaultLoginPresenter.getAuth();

        if (auth != null) {
            login.setText(auth.getLogin());
            password.setText(auth.getPassword());
        }

        enter.setOnClickListener(v -> {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(enter.getWindowToken(), 0);
            mDefaultLoginPresenter.enter(getContext(), login, password);

        });
        view.findViewById(R.id.signGoogle).setOnClickListener(v -> mGoogleLoginPresenter.signIn());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            mGoogleLoginPresenter.signResult(getContext(), data);
        }
    }

    @Override
    public void setToken(String token) {
        QueryPreferences.setToken(getContext(), token);
        startNewActivity();
    }

    @Override
    public void startNewActivity() {
        getActivity().startActivity(new Intent(getContext(), NomenclatureActivity.class));
        getActivity().finish();
    }

    @Override
    public void startGoogleAuthActivity(Intent intent) {
        getActivity().startActivityForResult(intent, RC_SIGN_IN);
    }
}
