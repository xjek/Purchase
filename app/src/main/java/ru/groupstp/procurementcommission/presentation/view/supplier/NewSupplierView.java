package ru.groupstp.procurementcommission.presentation.view.supplier;

import java.util.List;

import ru.groupstp.procurementcommission.model.Supplier;
import ru.groupstp.procurementcommission.presentation.view.NewConnectionView;

public interface NewSupplierView extends NewConnectionView {
    void updateUI(List<Supplier> suppliers);
    void returnPosition();
    void showAppBarWithButton(boolean vote);
}
