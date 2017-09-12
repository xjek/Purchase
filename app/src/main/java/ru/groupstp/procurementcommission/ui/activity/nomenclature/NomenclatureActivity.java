package ru.groupstp.procurementcommission.ui.activity.nomenclature;

import android.support.v4.app.Fragment;

import ru.groupstp.procurementcommission.ui.activity.BaseFragmentActivity;
import ru.groupstp.procurementcommission.ui.fragment.nomenclature.NomenclatureFragment;

public class NomenclatureActivity extends BaseFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new NomenclatureFragment();
    }

    public void changeCountConflictItem(int count) {
        ((NomenclatureFragment) getFragment()).changeCountConflictCount(count);
    }
}
