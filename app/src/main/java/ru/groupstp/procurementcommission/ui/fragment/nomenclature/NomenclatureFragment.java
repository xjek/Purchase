package ru.groupstp.procurementcommission.ui.fragment.nomenclature;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.groupstp.procurementcommission.R;
import ru.groupstp.procurementcommission.presentation.presenter.nomenclature.NomenclaturePresenter;
import ru.groupstp.procurementcommission.presentation.view.nomenclature.NomenclatureView;
import ru.groupstp.procurementcommission.ui.fragment.ConnectionBaseFragment;
import ru.groupstp.procurementcommission.ui.view_pager.ViewPagerAdapter;

import static android.app.Activity.RESULT_OK;
import static ru.groupstp.procurementcommission.ui.activity.supplier.SupplierActivity.REQUEST_SUPPLIER;

/**
 * Начальные фрагмент, который содержит табы
 */
public class NomenclatureFragment extends ConnectionBaseFragment implements NomenclatureView {

    @InjectPresenter
    NomenclaturePresenter nomenclaturePresenter;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    public int layout() {
        return R.layout.fragment_nomenclature;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        nomenclaturePresenter.load(getContext());

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabs);
    }

    private void setupViewPager(ViewPager viewPager) {
        mViewPagerAdapter = new ViewPagerAdapter((getActivity()).getSupportFragmentManager());
        mViewPagerAdapter.addFragment(NomenclatureTabFragment.newCurrentItemsFragment(), "Текущие заявки");
        mViewPagerAdapter.addFragment(NomenclatureTabFragment.newConflictItemsFragment(), "Спорные заявки");
        mViewPagerAdapter.addFragment(NomenclatureTabFragment.newReadyItemsFragment(), "Проголосованные заявки");
        viewPager.setAdapter(mViewPagerAdapter);
    }

    /**
     * Вызывает возврат из списка поставщиков
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_SUPPLIER) {
            if (data != null) {
                int position = data.getIntExtra("pos", -1);
                NomenclatureTabFragment currentFragment = (NomenclatureTabFragment) mViewPagerAdapter.getItem(mViewPager.getCurrentItem());
                // Если позиция больше -1 и текущий таб не проголосованные позиции, значит необходимо удалить позицию и обновить список с проголосованными позициями
                if (position > -1 && !currentFragment.isReadyListFragment()) {
                    currentFragment.removeItem(position);
                    updateReadyList();
                }
            }
        }
    }

    private void updateReadyList() {
        NomenclatureTabFragment readyFragment = (NomenclatureTabFragment) mViewPagerAdapter.getItem(NomenclatureTabFragment.READY_ITEMS);
        readyFragment.setIsNeededUpdate();
    }

    @Override
    public void errorAuth() {
        super.errorAuth();
        startLoginActivity();
    }

    @Override
    public void successLoading() {
        setupViewPager(mViewPager);
        nomenclaturePresenter.initTab(mViewPager, mTabLayout);
        hideLoading();
    }

    public void changeCountConflictCount(int count) {
        nomenclaturePresenter.setCountInTab(count);
    }
}
