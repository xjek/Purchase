package ru.groupstp.procurementcommission.ui.fragment.nomenclature;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import ru.groupstp.procurementcommission.R;
import ru.groupstp.procurementcommission.model.Nomenclature;
import ru.groupstp.procurementcommission.presentation.presenter.nomenclature.NomenclatureTabPresenter;
import ru.groupstp.procurementcommission.presentation.view.nomenclature.NomecnlatureTabView;
import ru.groupstp.procurementcommission.ui.activity.nomenclature.NomenclatureActivity;
import ru.groupstp.procurementcommission.ui.activity.supplier.SupplierActivity;
import ru.groupstp.procurementcommission.ui.adapter.NomenclatureAdapter;

import ru.groupstp.procurementcommission.ui.fragment.ConnectionBaseFragment;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import static ru.groupstp.procurementcommission.model.Nomenclature.ORG;
import static ru.groupstp.procurementcommission.model.Nomenclature.SUB;
import static ru.groupstp.procurementcommission.model.Nomenclature.USER;
import static ru.groupstp.procurementcommission.ui.activity.supplier.SupplierActivity.REQUEST_SUPPLIER;

/**
 * Фрагмент - содержимое таба
 */
public class NomenclatureTabFragment extends ConnectionBaseFragment implements NomecnlatureTabView, NomenclatureAdapter.OnNomenclatureAdapterListener {

    public static final int CURRENT_ITEMS = 0;
    public static final int CONFLICT_ITEMS = 1;
    public static final int READY_ITEMS = 2;

    @InjectPresenter
    NomenclatureTabPresenter nomenclatureTabPresenter;

    private int mCurrentMode;

    private RecyclerView mRecyclerView;

    private NomenclatureAdapter mAdapter;

    private boolean mIsNeededUpdate = false;

    @Override
    public int layout() {
        return R.layout.fragment_tab_nomenclature;
    }

    /**
     * Текущие позиции
     * @return
     */
    public static NomenclatureTabFragment newCurrentItemsFragment() {
        NomenclatureTabFragment fragment = new NomenclatureTabFragment();
        return fragment.setCurrentMode(CURRENT_ITEMS);
    }

    /**
     * Спорные позиции
     * @return
     */
    public static NomenclatureTabFragment newConflictItemsFragment() {
        NomenclatureTabFragment fragment = new NomenclatureTabFragment();
        return fragment.setCurrentMode(CONFLICT_ITEMS);
    }

    /**
     * Проголосованные позиции
     * @return
     */
    public static NomenclatureTabFragment newReadyItemsFragment() {
        NomenclatureTabFragment fragment = new NomenclatureTabFragment();
        return fragment.setCurrentMode(READY_ITEMS);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        nomenclatureTabPresenter.init(getContext(), mCurrentMode);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), layoutManager.getOrientation());
        decoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.item_decorate));
        mRecyclerView.addItemDecoration(decoration);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.table_menu, menu);
        menu.setGroupVisible(R.id.menu_group_nomenclature, true);
        MenuItem item = menu.findItem(R.id.menu_search);
        nomenclatureTabPresenter.initSearchView(getContext(), item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!super.onOptionsItemSelected(item)) {
            switch (item.getItemId()) {
                case R.id.filter_by_organization:
                    nomenclatureTabPresenter.showDialog(ORG);
                    break;
                case R.id.filter_by_subdivision:
                    nomenclatureTabPresenter.showDialog(SUB);
                    break;
                case R.id.filter_by_initiator:
                    nomenclatureTabPresenter.showDialog(USER);
                    break;
                case R.id.filter_reset:
                    nomenclatureTabPresenter.resetFilter();
                    break;
            }
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Обновление списка с проголосованными заявками
        if (mCurrentMode == READY_ITEMS && mIsNeededUpdate) {
            mIsNeededUpdate = false;
            nomenclatureTabPresenter.load();
        }
    }

    @Override
    public void updateUI(List<Nomenclature> nomenclatures) {
        if (mCurrentMode == CONFLICT_ITEMS)
            ((NomenclatureActivity) getActivity()).changeCountConflictItem(nomenclatures.size());
        mAdapter = new NomenclatureAdapter(nomenclatures, getContext(), this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showSuppliers(String id, int position, String name) {
        Intent intent = new Intent(getContext(), SupplierActivity.class);
        intent.putExtra("qid", id);
        intent.putExtra("pos", position);
        intent.putExtra("name", name);
        getActivity().startActivityForResult(intent, REQUEST_SUPPLIER);
    }

    @Override
    public void showComments(String[] comments) {
        nomenclatureTabPresenter.showComments(getContext(), comments);
    }

    public void setIsNeededUpdate() {
        mIsNeededUpdate = true;
    }

    public NomenclatureTabFragment setCurrentMode(int currentMode) {
        mCurrentMode = currentMode;
        return this;
    }

    public void removeItem(int position) {
        mAdapter.removeItem(position);
    }

    public boolean isReadyListFragment() {
        return mCurrentMode == READY_ITEMS;
    }
}
