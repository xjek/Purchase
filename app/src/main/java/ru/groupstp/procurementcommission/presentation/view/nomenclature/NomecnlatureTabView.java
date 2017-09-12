package ru.groupstp.procurementcommission.presentation.view.nomenclature;

import java.util.List;

import ru.groupstp.procurementcommission.model.Nomenclature;
import ru.groupstp.procurementcommission.presentation.view.NewConnectionView;

public interface NomecnlatureTabView extends NewConnectionView {
    void updateUI(List<Nomenclature> nomenclatures);
}
