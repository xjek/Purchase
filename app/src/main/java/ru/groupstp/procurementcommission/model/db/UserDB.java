package ru.groupstp.procurementcommission.model.db;

import io.realm.Realm;
import ru.groupstp.procurementcommission.model.User;

/**
 * Работа с таблицой пользователь
 */
public class UserDB {

    public static void saveAuth(User auth) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            realm.delete(User.class);
            realm.insert(auth);
        });
        realm.close();
    }

    public static User getAuth() {
        Realm realm = Realm.getDefaultInstance();
        User auth = realm.where(User.class).findFirst();
        if (auth == null)
            return null;
        else
            auth = realm.copyFromRealm(auth);
        realm.close();
        return auth;
    }

}
