package ru.groupstp.procurementcommission.connection.listener;

import java.util.List;

import ru.groupstp.procurementcommission.model.Nomenclature;

public interface OnNomeclatureListener extends BaseConnectionListener {
    void successConnection(List<Nomenclature> nomenclatures);
    void saveNomenclature(List<Nomenclature> nomenclatures);
}
