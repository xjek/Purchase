package ru.groupstp.procurementcommission.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ru.groupstp.procurementcommission.R;
import ru.groupstp.procurementcommission.model.Commission;
import ru.groupstp.procurementcommission.model.Supplier;
import ru.groupstp.procurementcommission.model.User;
import ru.groupstp.procurementcommission.ui.holder.SupplierHolder;


public class SupplierAdapter extends RecyclerView.Adapter<SupplierHolder> {

    private Context mContext;
    private List<Supplier> mSuppliers;
    private List<List<String>> mListWithVotes = new LinkedList<>(); // Список членов комиссии для каждого поставщика
    private User mAuth;

    private int mPositionVoted = -1; // Позиция поставщика за которого проголосовал пользователь
    private boolean mModeVotedAgain;
    private OnSupplierAdapterListener mListener;

    public SupplierAdapter(Context context, List<Supplier> suppliers, User auth, OnSupplierAdapterListener listener) {
        mContext = context;
        mSuppliers = suppliers;
        mAuth = auth;
        mListener = listener;

        for (int i = 0; i < mSuppliers.size(); i++) {
            List<String> supplierVotes = new ArrayList<>();
            for (Commission commission : mSuppliers.get(i).getCommissions()) {
                if (commission.getId().equals(auth.getId()))
                    mPositionVoted = i;
                supplierVotes.add(commission.getVote());
            }
            mListWithVotes.add(supplierVotes);
        }
    }

    @Override
    public SupplierHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SupplierHolder(LayoutInflater.from(mContext).inflate(R.layout.item_new_supplier, parent, false), mContext);
    }

    @Override
    public void onBindViewHolder(SupplierHolder holder, int position) {
        onBind(holder, position);
    }

    private void onBind(SupplierHolder holder, int position) {
        Supplier supplier = mSuppliers.get(position);
        holder.name.setText(supplier.getName());
        holder.price.setText(supplier.getPrice());
        holder.showTrustSupplier(supplier.isTrust());
        holder.createTable(supplier.getData());
        holder.showUsersVote(mListWithVotes.get(position));

        if (position == 0)
            holder.hideCycleTop();
        else
            holder.showCycleTop();

        if (!mModeVotedAgain)
            holder.highlightVotedItem(mPositionVoted, position);
        else
            holder.resetVoted();

        holder.vote.setOnClickListener(v -> mListener.vote(supplier.getId(), mAuth.getVote()));
    }

    @Override
    public int getItemCount() {
        return mSuppliers.size();
    }

    /**
     * Установить режим повторного голосования
     */
    public void setModeVotedAgain() {
        mModeVotedAgain = true;
        notifyDataSetChanged();
    }

    public interface OnSupplierAdapterListener {
        void vote(String supplierId, String vote);
    }
}