package ru.groupstp.procurementcommission.connection;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.RealmList;
import ru.groupstp.procurementcommission.connection.listener.OnNomeclatureListener;
import ru.groupstp.procurementcommission.model.Commission;
import ru.groupstp.procurementcommission.model.Nomenclature;
import ru.groupstp.procurementcommission.model.Supplier;

/**
 * Получение списка позиций
 */
public class NomenclatureConnection extends BaseConnection<OnNomeclatureListener> {

    private static String[][] ARRAY_COMMISSIONS = {{Commission.FIRST_VOTE, "fID"}, {Commission.SECOND_VOTE, "sID"}, {Commission.CHM_VOTE, "chmID"}};

    public NomenclatureConnection(OnNomeclatureListener listener) {
        super(listener);
    }

    @Override
    public void connection(Object... params) {
        String token = (String) params[0];
        Rx2AndroidNetworking.post(BASE_URL + "nom")
                .setOkHttpClient(sOkHttpClient)
                .addBodyParameter("obj", "st.com")
                .addBodyParameter("workOut", "false")
                .addHeaders("Accept", token)
                .build()
                .getJSONObjectObservable()
                .flatMap(this::getObsDecode)
                .flatMap(this::getObsNomenclatures)
                .flatMap(this::getObsSaveNomenclatures)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(getListener()::startConnection)
                .doAfterTerminate(getListener()::endConnection)
                .subscribe(getListener()::successConnection, getListener()::resultError);
    }

    private Observable<List<Nomenclature>> getObsSaveNomenclatures(List<Nomenclature> nomenclatures) {
        return Observable.fromCallable(() -> {
            getListener().saveNomenclature(nomenclatures);
            return nomenclatures;
        });
    }

    private Observable<List<Nomenclature>> getObsNomenclatures(JSONObject object) {
        return Observable.fromCallable(() -> {
            JSONObject records = object.getJSONObject("records");
            Map<String, Nomenclature> nomenclatures = new HashMap<>();
            Iterator<String> iterator = records.keys();
            while (iterator.hasNext()) {
                String idPositin = iterator.next();
                JSONObject record = records.getJSONObject(idPositin);
                String qid = record.getString("qid");
                Nomenclature nomenclature = nomenclatures.get(qid);
                if (nomenclature == null) {
                    nomenclature = getNomeclature(qid, record);
                    nomenclatures.put(qid, nomenclature);
                }
                nomenclature.addSupplierr(getSupplier(idPositin, record));
            }

            return new ArrayList<>(nomenclatures.values());
        });
    }

    private Nomenclature getNomeclature(String id, JSONObject record) throws JSONException{
        Nomenclature nomenclature = new Nomenclature();
        nomenclature.setId(id);
        nomenclature.setName(record.getJSONObject("nom").getString("display"));
        nomenclature.setUser(record.getJSONObject("userID").getString("display"));
        nomenclature.setSub(record.getJSONObject("subID").getString("display"));
        nomenclature.setOrganization(record.getJSONObject("organizationID").getString("display"));
        nomenclature.setCount(record.getInt("have"));
        nomenclature.setTimestamp(record.getString("timestamp"));
        if (!record.isNull("comment"))
            nomenclature.setComment(record.getString("comment"));
        return nomenclature;
    }

    private Supplier getSupplier(String idPosition, JSONObject record) throws JSONException {
        Supplier supplier = new Supplier();
        supplier.setId(idPosition);
        supplier.setName(record.getJSONObject("sup").getString("display"));
        if (!record.isNull("trustSup"))
            supplier.setTrust(record.getBoolean("trustSup"));
        if (!record.isNull("price"))
            supplier.setPrice(record.getInt("price"));
        if (!record.isNull("analog"))
            supplier.setAnalog(record.getString("analog"));
        if (!record.isNull("guard"))
            supplier.setGuar(record.getInt("guard"));
        if (!record.isNull("have"))
            supplier.setHave(record.getInt("have"));
        if (!record.isNull("prebuy"))
            supplier.setPrebuy(record.getInt("prebuy"));
        if (!record.isNull("maker"))
            supplier.setMaker(record.getString("maker"));
        if (!record.isNull("deliveryTime"))
            supplier.setDeliveryTime(record.getInt("deliveryTime"));
        supplier.setCommissions(getCommession(record));
        return supplier;
    }

    private RealmList<Commission> getCommession(JSONObject record) throws JSONException {
        RealmList<Commission> commissions = new RealmList<>();
        for (String[] com : ARRAY_COMMISSIONS) {
            Commission commission = getVote(com[0], com[1], record);
            if (commission != null)
                commissions.add(commission);
        }
        return commissions;
    }

    private Commission getVote(String isVoteKey, String userKey, JSONObject record) throws JSONException {
        if (!record.isNull(isVoteKey)) {
            if (record.getBoolean(isVoteKey)) {
                Commission commission = new Commission();
                commission.setVote(isVoteKey);
                if (!record.isNull(userKey)) {
                    try {
                        commission.setId(record.getJSONObject(userKey).getString("value"));
                        commission.setName(record.getJSONObject(userKey).getString("display"));
                    } catch (JSONException e) {
                        commission.setId(record.getString(userKey));
                    }
                    return commission;
                }
            }
        }
        return null;
    }
}
