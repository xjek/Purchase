package ru.groupstp.procurementcommission.ui.activity.supplier;

import android.content.Intent;
import android.support.v4.app.Fragment;

import ru.groupstp.procurementcommission.ui.activity.BaseFragmentActivity;
import ru.groupstp.procurementcommission.ui.fragment.supplier.SupplierFragment;

public class SupplierActivity extends BaseFragmentActivity {

    public static final int REQUEST_SUPPLIER = 100;

    private int mPosition = -1;

    @Override
    protected Fragment createFragment() {
        return new SupplierFragment();
    }

    @Override
    protected void returnResult() {
        Intent intent = new Intent();
        intent.putExtra("pos", mPosition);
        setResult(RESULT_OK, intent);
    }

    public void setReturnPosition(int position) {
        mPosition = position;
    }
}
