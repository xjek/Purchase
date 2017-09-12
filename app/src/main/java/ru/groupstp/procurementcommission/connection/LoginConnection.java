package ru.groupstp.procurementcommission.connection;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.groupstp.procurementcommission.app.exception.NoRightException;
import ru.groupstp.procurementcommission.connection.listener.LoginConnectionListener;
import ru.groupstp.procurementcommission.model.User;

/**
 * Обычный вход в приложение
 */
public class LoginConnection extends BaseConnection<LoginConnectionListener> {

    public LoginConnection(LoginConnectionListener listener) {
        super(listener);
    }

    @Override
    public void connection(Object... params) {
        String login = (String) params[0];
        String password = (String) params[1];
        Rx2AndroidNetworking.post(BASE_URL + "login")
                .setOkHttpClient(sOkHttpClient)
                .addBodyParameter("user", login)
                .addBodyParameter("pswd", password)
                .build()
                .getJSONObjectObservable()
                .flatMap(this::getObsDecode)
                .flatMap(this::getParseObservable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(getListener()::startConnection)
                .doAfterTerminate(getListener()::endConnection)
                .subscribe(data -> getListener().successConnection(data, login, password), getListener()::resultError);
    }

    Observable<String[]> getParseObservable(JSONObject message) {
        return Observable.fromCallable(() -> {
            JSONObject info = message.getJSONObject("info");
            JSONArray sFunctions = info.getJSONArray("sFunctions");
            String funcVote = getFuncVote(sFunctions);
            if (funcVote.isEmpty())
                throw new NoRightException();
            String token = message.getString("token");
            String id = info.getString("ID");
            String userName = info.getString("description");
            String userEmail = info.getString("email");
            return new String[]{token, id, userName, userEmail, funcVote};
        });
    }

    private String getFuncVote(JSONArray sFunctions) throws JSONException {
        for (int i = 0; i < sFunctions.length(); i++) {
            String func = sFunctions.getString(i);
            switch (func) {
                case User.MEMBER_1:
                case User.MEMBER_2:
                case User.CHAIRMAN: return func;
            }
        }
        return "";
    }
}
