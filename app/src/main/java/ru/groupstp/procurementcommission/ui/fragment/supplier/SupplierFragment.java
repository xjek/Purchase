package ru.groupstp.procurementcommission.ui.fragment.supplier;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ru.groupstp.procurementcommission.R;
import ru.groupstp.procurementcommission.model.Supplier;
import ru.groupstp.procurementcommission.presentation.presenter.supplier.SupplierPresenter;
import ru.groupstp.procurementcommission.presentation.view.supplier.NewSupplierView;
import ru.groupstp.procurementcommission.ui.activity.supplier.SupplierActivity;
import ru.groupstp.procurementcommission.ui.adapter.SupplierAdapter;
import ru.groupstp.procurementcommission.ui.fragment.ConnectionBaseFragment;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

public class SupplierFragment extends ConnectionBaseFragment implements NewSupplierView, SupplierAdapter.OnSupplierAdapterListener {

   /* @InjectPresenter
    SupplierPresenter mSupplierPresenter;*/

    @InjectPresenter
    SupplierPresenter presenter;

    private RecyclerView mRecyclerView;

    private View mAppBarWithButton;
    private SupplierAdapter mAdapter;

    private int mPositionNomenclature;
    private String mQid;

    @Override
    public int layout() {
        return R.layout.fragment_supplier;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAppBarWithButton = view.findViewById(R.id.appbar_for_button);
        view.findViewById(R.id.voted_again).setOnClickListener(v -> {mAdapter.setModeVotedAgain(); showAppBarWithButton(false);});

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), layoutManager.getOrientation());
        decoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.item_decorate));
        mRecyclerView.addItemDecoration(decoration);

        mQid = getActivity().getIntent().getStringExtra("qid");
        mPositionNomenclature = getActivity().getIntent().getIntExtra("pos", -1);
        getActivity().setTitle(getActivity().getIntent().getStringExtra("name"));
        presenter.load(mQid);
    }

    @Override
    public void errorAuth() {
        super.errorAuth();
        startLoginActivity();
    }

    public void showAppBarWithButton(boolean show) {
        if (show)
            mAppBarWithButton.setVisibility(View.VISIBLE);
        else
            mAppBarWithButton.setVisibility(View.GONE);
    }


    @Override
    public void updateUI(List<Supplier> suppliers) {
        mAdapter = new SupplierAdapter(getContext(), suppliers, presenter.getAuth(), this);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Вернуть в предыдущее активити позицию открытой номенклатуры (позиции), чтобы поместить ее в таб с проголосованными позициями
     */
    @Override
    public void returnPosition() {
        ((SupplierActivity) getActivity()).setReturnPosition(mPositionNomenclature);
    }

    /**
     * Голосовать
     * @param supplierId
     * @param vote
     */
    @Override
    public void vote(String supplierId, String vote) {
        presenter.vote(getContext(), mQid, supplierId, vote);
    }
}
