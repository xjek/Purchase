package ru.groupstp.procurementcommission.model.db;

import java.util.List;

import io.realm.Realm;
import ru.groupstp.procurementcommission.model.Commission;
import ru.groupstp.procurementcommission.model.Nomenclature;
import ru.groupstp.procurementcommission.model.Supplier;
import ru.groupstp.procurementcommission.model.User;

/**
 * Работа с таблицой поставщиками
 */
public class SupplierDB {

    public static List<Supplier> getSuppliers(String qid) {
        Realm realm = Realm.getDefaultInstance();
        Nomenclature nomenclature = realm.copyFromRealm(realm.where(Nomenclature.class).equalTo("id", qid).findFirst());
        realm.close();
        return nomenclature.getSuppliers();
    }

    public static boolean userIsVoted(String nomenclatureId, String userId) {
        Realm realm = Realm.getDefaultInstance();
        long count = realm.where(Nomenclature.class).equalTo("id", nomenclatureId).equalTo("suppliers.commissions.id", userId).count();
        realm.close();
        return count > 0;
    }

    public static void setVote(String nomenclatureId, String supplierId, User auth) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            Nomenclature nomenclature = realm1.where(Nomenclature.class).equalTo("id", nomenclatureId).findFirst();
            Commission commissionIsUser = null;
            for (Supplier supplier : nomenclature.getSuppliers()) {
                for (Commission commission : supplier.getCommissions())
                    if (commission.getId().equals(auth.getId()))
                        commissionIsUser = commission;
                if (supplier.getId().equals(supplierId)) {
                    Commission commission = new Commission();
                    commission.setId(auth.getId());
                    commission.setName(auth.getUsername());
                    commission.setVote(auth.getVote());
                    supplier.addCommission(commission);
                }
            }
            if (commissionIsUser != null) {
                commissionIsUser.deleteFromRealm();
            }
        });
        realm.close();
    }
}
