package ru.groupstp.procurementcommission.presentation.presenter.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.arellomobile.mvp.InjectViewState;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import ru.groupstp.procurementcommission.R;
import ru.groupstp.procurementcommission.connection.LoginGoogleConnection;

@InjectViewState
public class LoginGooglePresenter extends LoginPresenter implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInAccount mGoogleSignInAccount;

    public void init(FragmentActivity activity) {
        String serverClientId = activity.getString(R.string.server_client_id);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode(serverClientId)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .enableAutoManage(activity, this)
                .addApi(com.google.android.gms.auth.api.Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public void signIn() {
        getViewState().startGoogleAuthActivity(com.google.android.gms.auth.api.Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient));
    }

    public void signResult(Context context, Intent data) {
        GoogleSignInResult result = com.google.android.gms.auth.api.Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if (result.isSuccess()) {
            mGoogleSignInAccount = result.getSignInAccount();
            connection(context, new LoginGoogleConnection(this), false, mGoogleSignInAccount.getServerAuthCode());
        } else {
            getViewState().showError("Вход через google не прошел успешно");
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
