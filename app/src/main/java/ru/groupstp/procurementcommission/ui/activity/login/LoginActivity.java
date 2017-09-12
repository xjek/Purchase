package ru.groupstp.procurementcommission.ui.activity.login;

import android.support.v4.app.Fragment;
import ru.groupstp.procurementcommission.ui.activity.BaseFragmentActivity;
import ru.groupstp.procurementcommission.ui.fragment.login.LoginFragment;

public class LoginActivity extends BaseFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new LoginFragment();
    }
}
