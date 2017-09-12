package ru.groupstp.procurementcommission.presentation.presenter.nomenclature;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import ru.groupstp.procurementcommission.R;
import ru.groupstp.procurementcommission.app.QueryPreferences;
import ru.groupstp.procurementcommission.connection.NomenclatureConnection;
import ru.groupstp.procurementcommission.connection.listener.OnNomeclatureListener;
import ru.groupstp.procurementcommission.model.Nomenclature;
import ru.groupstp.procurementcommission.model.db.NomenclatureDB;
import ru.groupstp.procurementcommission.presentation.presenter.ConnectionPresenter;
import ru.groupstp.procurementcommission.presentation.view.nomenclature.NomenclatureView;

@InjectViewState
public class NomenclaturePresenter extends ConnectionPresenter<NomenclatureView> implements OnNomeclatureListener{

    private TextView mCountInTab;

    public void load(Context context) {
        connection(context, new NomenclatureConnection(this), false, QueryPreferences.getToken(context));
    }

    @Override
    public void saveNomenclature(List<Nomenclature> nomenclatures) {
        NomenclatureDB.saveNomenclature(nomenclatures);
    }

    @Override
    public void successConnection(List<Nomenclature> nomenclatures) {
        getViewState().successLoading();
    }

    public void initTab(ViewPager viewPager, TabLayout tabLayout) {
        tabLayout.setupWithViewPager(viewPager);
        setMarginBetweenTabs(tabLayout);
    }

    /**
     * Проставление отступов между табами
     * @param tabs
     */
    private void setMarginBetweenTabs(TabLayout tabs) {

        for(int i=0; i < tabs.getTabCount(); i++) {
            View tab = ((ViewGroup) tabs.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(25, 5, 25, 10);
            tab.requestLayout();
        }
        TabLayout.Tab tabWithCount = tabs.getTabAt(1);
        tabWithCount.setCustomView(R.layout.tab_with_count);
        View view = tabWithCount.getCustomView();
        mCountInTab = (TextView)  view.findViewById(R.id.count);
        final TextView tabTitle = (TextView) view.findViewById(R.id.tabTitle);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1)
                    tabTitle.setTextColor(ContextCompat.getColor(tabs.getContext(), R.color.colorPrimary));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1)
                    tabTitle.setTextColor(ContextCompat.getColor(tabs.getContext(), R.color.white));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void setCountInTab(int count) {
        if (mCountInTab != null)
            mCountInTab.setText(String.valueOf(count));
    }
}
