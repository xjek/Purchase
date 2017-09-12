package ru.groupstp.procurementcommission.connection;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.groupstp.procurementcommission.connection.listener.OnSupplierConnectionListener;

/**
 * Отправка голоса
 */
public class SupplierConnection extends BaseConnection<OnSupplierConnectionListener> {

    public SupplierConnection(OnSupplierConnectionListener listener) {
        super(listener);
    }

    @Override
    public void connection(Object... params) {
        String nomenclatureId = (String) params[0];
        String supplierId = (String) params[1];
        String vote = (String) params[2];
        String token = (String) params[3];

        Rx2AndroidNetworking.post(BASE_URL + "vote")
                .setOkHttpClient(sOkHttpClient)
                .addBodyParameter("command", vote)
                .addBodyParameter("ID", supplierId)
                .addBodyParameter("zipFlag", "0")
                .addHeaders("Accept", token)
                .build()
                .getJSONObjectObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(getListener()::startConnection)
                .doAfterTerminate(getListener()::endConnection)
                .subscribe(r -> getListener().successVote(nomenclatureId, supplierId), getListener()::resultError);
    }
}
