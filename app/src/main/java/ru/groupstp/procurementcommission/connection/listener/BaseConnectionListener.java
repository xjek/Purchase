package ru.groupstp.procurementcommission.connection.listener;

import io.reactivex.disposables.Disposable;

public interface BaseConnectionListener {
    void resultError(Throwable throwable);
    void startConnection(Disposable disposable);
    void endConnection();
}
