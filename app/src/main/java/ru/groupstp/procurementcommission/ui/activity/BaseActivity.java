package ru.groupstp.procurementcommission.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.arellomobile.mvp.MvpAppCompatActivity;

public abstract class BaseActivity extends MvpAppCompatActivity {

    private Fragment mFragment;

    protected void setFragment(Fragment fragment) {
        mFragment = fragment;
    }

    protected Fragment getFragment() {
        return mFragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        returnResult();
        super.onBackPressed();
    }

    protected void returnResult() {}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getFragment().onActivityResult(requestCode, resultCode, data);
    }
}
