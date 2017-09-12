package ru.groupstp.procurementcommission.ui;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import ru.groupstp.procurementcommission.BuildConfig;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if ( !BuildConfig.DEBUG )
            Fabric.with(this, new Crashlytics());
        Realm.init(this);
    }
}
