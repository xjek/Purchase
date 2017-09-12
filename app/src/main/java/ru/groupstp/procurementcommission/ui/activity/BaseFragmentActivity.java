package ru.groupstp.procurementcommission.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import ru.groupstp.procurementcommission.R;

public abstract class BaseFragmentActivity extends BaseActivity {

    protected static final int FRAGMENT_ID = R.id.fragmentContainer;

    protected abstract Fragment createFragment();

    protected Fragment getFragment() {
        Fragment fragment = super.getFragment();
        if (fragment == null)
            return getSupportFragmentManager().findFragmentById(FRAGMENT_ID);
        else
            return fragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(FRAGMENT_ID);
        if (fragment == null) {
            fragment = createFragment();
            setFragment(fragment);
            fm.beginTransaction().add(FRAGMENT_ID, fragment).commit();
        }
    }
}
