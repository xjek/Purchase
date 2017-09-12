package ru.groupstp.procurementcommission.connection.listener;

public interface OnSupplierConnectionListener extends BaseConnectionListener {
    void successVote(String nomenclatureId, String supplierId);
}
