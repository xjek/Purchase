package ru.groupstp.procurementcommission.presentation.presenter.nomenclature;

import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;

import com.arellomobile.mvp.InjectViewState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.groupstp.procurementcommission.R;
import ru.groupstp.procurementcommission.model.Nomenclature;
import ru.groupstp.procurementcommission.model.db.Filter;
import ru.groupstp.procurementcommission.model.db.NomenclatureDB;
import ru.groupstp.procurementcommission.presentation.presenter.ConnectionPresenter;
import ru.groupstp.procurementcommission.presentation.view.nomenclature.NomecnlatureTabView;
import ru.groupstp.procurementcommission.ui.dialog.CommentDialog;
import ru.groupstp.procurementcommission.ui.dialog.FilterDialog;

import static ru.groupstp.procurementcommission.model.Nomenclature.ORG;
import static ru.groupstp.procurementcommission.model.Nomenclature.SUB;
import static ru.groupstp.procurementcommission.model.Nomenclature.USER;

@InjectViewState
public class NomenclatureTabPresenter extends ConnectionPresenter<NomecnlatureTabView> implements FilterDialog.OnDialogFilterListener, SearchView.OnQueryTextListener {

    private Filter mFilter;
    private FilterDialog mFilterDialog;
    private Map<String, List<String>> mDataForFilter = new HashMap<String, List<String>>(){{
        put(ORG, NomenclatureDB.getOrganizations());
        put(SUB, NomenclatureDB.getSubdivisions());
        put(USER, NomenclatureDB.getUsers());
    }};

    public void init(Context context, int mode) {
        if (mFilter == null) {
            mFilterDialog = new FilterDialog(context, this);
            mFilter = new Filter(mode);
            load();
        }
    }

    public void load() {
        getViewState().updateUI(mFilter.findAll());
    }

    public void initSearchView(Context context, MenuItem item) {
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(context.getString(R.string.holder_search));

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                clearSearch();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                clearSearch();
                return true;
            }
        });
    }

    public void showComments(Context context,String[] comments) {
        new CommentDialog(context, comments).show();
    }

    public void showDialog(String key) {
        mFilterDialog.initDialog(mDataForFilter, key);
        mFilterDialog.show();
    }

    public void resetFilter() {
        List<Nomenclature> nomenclatures = mFilter.reset()
                .findAll();
        getViewState().updateUI(nomenclatures);
    }

    @Override
    public void onApply(List<String> listItems, String key) {
        List<Nomenclature> nomenclatures = mFilter.filter(key, listItems)
                .findAll();
        getViewState().updateUI(nomenclatures);
    }

    @Override
    public void onReset(String key) {
        List<Nomenclature> nomenclatures = mFilter.reset(key)
                .findAll();
        getViewState().updateUI(nomenclatures);
    }

    private void clearSearch() {
        List<Nomenclature> nomenclatures = mFilter.resetSearch()
                .findAll();
        getViewState().updateUI(nomenclatures);
    }

    private void setSearch(String text) {
        List<Nomenclature> nomenclatures = mFilter.search(text)
                .findAll();
        getViewState().updateUI(nomenclatures);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        setSearch(s);
        return true;
    }
}
