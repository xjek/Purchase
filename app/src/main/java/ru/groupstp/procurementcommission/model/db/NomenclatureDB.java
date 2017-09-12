package ru.groupstp.procurementcommission.model.db;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.realm.Realm;
import ru.groupstp.procurementcommission.model.Commission;
import ru.groupstp.procurementcommission.model.Nomenclature;
import ru.groupstp.procurementcommission.model.Supplier;

/**
 * Работа с таблицой позиций
 */
public class NomenclatureDB {

    public static void saveNomenclature(List<Nomenclature> nomenclatures) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            realm1.delete(Nomenclature.class);
            realm1.delete(Supplier.class);
            realm1.delete(Commission.class);
            realm1.insert(nomenclatures);
        });
        realm.close();
    }

    public static List<String> getOrganizations() {
        Realm realm = Realm.getDefaultInstance();
        Set<String> values = new HashSet<>();
        for (Nomenclature nomenclature : realm.where(Nomenclature.class).findAllSorted("organization")) {
            values.add(nomenclature.getOrganization());
        }
        realm.close();
        return new ArrayList<>(values);
    }

    public static List<String> getUsers() {
        Realm realm = Realm.getDefaultInstance();
        Set<String> values = new HashSet<>();
        for (Nomenclature nomenclature : realm.where(Nomenclature.class).findAllSorted("user")) {
            values.add(nomenclature.getUser());
        }
        realm.close();
        return new ArrayList<>(values);
    }

    public static List<String> getSubdivisions() {
        Realm realm = Realm.getDefaultInstance();
        Set<String> values = new HashSet<>();
        for (Nomenclature nomenclature : realm.where(Nomenclature.class).findAllSorted("sub")) {
            values.add(nomenclature.getSub());
        }
        realm.close();
        return new ArrayList<>(values);
    }

}
