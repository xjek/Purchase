package ru.groupstp.procurementcommission.connection.listener;

public interface LoginConnectionListener extends BaseConnectionListener {
    void successConnection(String[] data, String ... input);
}
