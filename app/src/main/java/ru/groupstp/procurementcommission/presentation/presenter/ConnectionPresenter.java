package ru.groupstp.procurementcommission.presentation.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.androidnetworking.error.ANError;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.disposables.Disposable;
import ru.groupstp.procurementcommission.connection.BaseConnection;
import ru.groupstp.procurementcommission.connection.listener.BaseConnectionListener;
import ru.groupstp.procurementcommission.presentation.view.NewConnectionView;

public class ConnectionPresenter<T extends NewConnectionView> extends MvpPresenter<T> implements BaseConnectionListener {

    private boolean mConnection = false;

    protected void connection(Context context, BaseConnection connection, boolean reconnect, Object ... params) {
        if (reconnect || !mConnection) {
            mConnection = true;
            if (isNetworkAvailableAndConnected(context))
                connection.connection(params);
            else
                getViewState().noNetwork();
        }

    }

    protected boolean isNetworkAvailableAndConnected(Context context) {
        int[] networkTypes = {ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI};
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            for (int networkType : networkTypes) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.getType() == networkType)
                    return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public void resultError(Throwable e) {
        if (e instanceof ANError) {
            ANError error = (ANError) e;
            if (error.getErrorCode() == 401)
                getViewState().showError("Ошибка авторизации");
        }
        e.printStackTrace();
        getViewState().hideLoading();
        getViewState().showError("Произошла ошибка при подключении");
    }

    @Override
    public void startConnection(Disposable disposable) {
        getViewState().showLoading();
    }

    @Override
    public void endConnection() {
        getViewState().hideLoading();
    }
}
