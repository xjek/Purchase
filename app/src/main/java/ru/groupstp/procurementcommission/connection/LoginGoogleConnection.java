package ru.groupstp.procurementcommission.connection;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.groupstp.procurementcommission.connection.listener.LoginConnectionListener;

/**
 * Вход в приложение через гугл авторизацию
 */
public class LoginGoogleConnection extends LoginConnection {

    public LoginGoogleConnection(LoginConnectionListener listener) {
        super(listener);
    }

    @Override
    public void connection(Object... params) {
        String code = (String) params[1];
        Rx2AndroidNetworking.post(BASE_URL + "login")
                .setOkHttpClient(sOkHttpClient)
                .addBodyParameter("type", "google")
                .addBodyParameter("method", "gtokens")
                .addBodyParameter("code", code)
                .build()
                .getJSONObjectObservable()
                .flatMap(this::getObsDecode)
                .flatMap(this::getParseObservable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(getListener()::startConnection)
                .doAfterTerminate(getListener()::endConnection)
                .subscribe(data -> getListener().successConnection(data), getListener()::resultError);

    }
}
