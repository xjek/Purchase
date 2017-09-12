package ru.groupstp.procurementcommission.model.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmQuery;
import ru.groupstp.procurementcommission.model.Commission;
import ru.groupstp.procurementcommission.model.Nomenclature;
import ru.groupstp.procurementcommission.model.Supplier;

import static ru.groupstp.procurementcommission.model.Nomenclature.ORG;
import static ru.groupstp.procurementcommission.model.Nomenclature.SUB;
import static ru.groupstp.procurementcommission.model.Nomenclature.USER;

/**
 * Работа со списком позиций с применением фильтров и поиска
 */
public class Filter {

    private String mUserId = UserDB.getAuth().getId();
    private String mSearch = "";
    private Map<String, String[]> mKeys = getEmptyKeys();
    private int mMode;

    private Map<String, String[]> getEmptyKeys() {
        return  new HashMap<String, String[]>(){{
            put(ORG, new String[]{});
            put(SUB, new String[]{});
            put(USER, new String[]{});
        }};
    }

    public Filter(int mode) {
        mMode = mode;
    }

    public Filter search(String search) {
        this.mSearch = search;
        return this;
    }

    public Filter filter(String key, List<String> values) {
        String[] arrayValues = new String[values.size()];
        for (int i = 0; i < values.size(); i++)
            arrayValues[i] = values.get(i);
        mKeys.put(key, arrayValues);
        return this;
    }

    public Filter reset(String ... key) {
        if (key.length > 0)
            mKeys.put(key[0], new String[]{});
        else
            mKeys = getEmptyKeys();
        return this;
    }

    public Filter resetSearch() {
        mSearch = "";
        return this;
    }

    public List<Nomenclature> findAll() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Nomenclature> query = realm.where(Nomenclature.class);
        if (!mSearch.isEmpty())
            query.like("name", "*" + mSearch + "*");

        if (mKeys.get(ORG).length > 0)
            query.in("organization", mKeys.get(ORG));
        if (mKeys.get(SUB).length > 0)
            query.in("sub", mKeys.get(SUB));
        if (mKeys.get(USER).length > 0)
            query.in("user", mKeys.get(USER));
        List<Nomenclature> nomenclatures = null;
        switch (mMode) {
            case 0:
                nomenclatures = getDefault(query);
                break;
            case 1:
                nomenclatures = getConflict(query);
                break;
            case 2:
                nomenclatures = getCompleted(query);
                break;
        }
        nomenclatures = realm.copyFromRealm(nomenclatures);
        realm.close();
        return nomenclatures;
    }

    /**
     * Получить текущии позиции
     * @param query
     * @return
     */
    private List<Nomenclature> getDefault(RealmQuery<Nomenclature> query) {
        List<Nomenclature> nomenclatures = new ArrayList<>();
        for (Nomenclature nomenclature : query.findAll())
            if (!isVoted(nomenclature))
                nomenclatures.add(nomenclature);
        return nomenclatures;
    }

    private boolean isVoted(Nomenclature nomenclature) {
        for (Supplier supplier : nomenclature.getSuppliers())
            for (Commission commission : supplier.getCommissions())
                if (commission.getId().equals(mUserId))
                    return true;
        return false;
    }

    /**
     * Получить спорные позиции
     * @param query
     * @return
     */
    private List<Nomenclature> getConflict(RealmQuery<Nomenclature> query) {
        List<Nomenclature> nomenclatures = new ArrayList<>();
        for (Nomenclature nomenclature : query.findAll()) {
            int countVote = 0;
            for (Supplier supplier : nomenclature.getSuppliers()) {
                if (supplier.getCommissions().size() == 1)
                    countVote++;
            }
            if (countVote == 3)
                nomenclatures.add(nomenclature);
        }
        return nomenclatures;
    }

    /**
     * Получить проголосованные позиции
     * @param query
     * @return
     */
    private List<Nomenclature> getCompleted(RealmQuery<Nomenclature> query) {
        return query.equalTo("suppliers.commissions.id", mUserId).findAll();
    }

}
