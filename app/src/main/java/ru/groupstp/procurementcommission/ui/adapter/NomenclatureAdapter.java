package ru.groupstp.procurementcommission.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.groupstp.procurementcommission.R;
import ru.groupstp.procurementcommission.model.Nomenclature;
import ru.groupstp.procurementcommission.ui.holder.NomenclatureHolder;

public class NomenclatureAdapter extends RecyclerView.Adapter<NomenclatureHolder> {

    private List<Nomenclature> mNomenclatures = new ArrayList<>();
    private Context mContext;
    private OnNomenclatureAdapterListener mListener;
    //private Drawable mDrawableBookmarker;

    public NomenclatureAdapter(List<Nomenclature> nomenclatures, Context context, OnNomenclatureAdapterListener listener) {
        mNomenclatures = nomenclatures;
        mContext = context;
        mListener = listener;
        //mDrawableBookmarker = VectorDrawableCompat.create(mContext.getResources(), R.drawable.bookmark_date, mContext.getTheme());
    }

    @Override
    public NomenclatureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NomenclatureHolder(LayoutInflater.from(mContext).inflate(R.layout.item_nomenclature, parent, false));
    }

    @Override
    public void onBindViewHolder(NomenclatureHolder holder, int position) {
        Nomenclature nomenclature = mNomenclatures.get(position);
        holder.highlightBlockWithDate(mContext, nomenclature.isSoonTimeWillEnd());
        //holder.bookmark.setBackground(mDrawableBookmarker);
        holder.data.setText(nomenclature.getDateTimestamp());
        holder.time.setText(nomenclature.getTimeTimestamp());
        holder.sub.setText(nomenclature.getSub());
        holder.organization.setText(nomenclature.getOrganization());
        holder.name.setText(nomenclature.getName());
        holder.count.setText(nomenclature.getCount());
        holder.user.setText(nomenclature.getUser());

        holder.comments.setText(nomenclature.getCommentsCount());
        holder.mainView.setOnClickListener(view -> {
            //NomenclatureTabPresenter.addQidToBrowsedRecords(mFragment.getContext(), nomenclature.getId());
            holder.clearHighlightBlockBrowsed(mContext);
            mListener.showSuppliers(nomenclature.getId(), position, nomenclature.getName());
        });
        holder.showComments.setOnClickListener(view -> {
            mListener.showComments(nomenclature.getComments());
        });
        holder.highlightBlockBrowsed(mContext, nomenclature.getId());
    }

    public void removeItem(int position) {
        mNomenclatures.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mNomenclatures.size();
    }

    public interface OnNomenclatureAdapterListener {
        void showSuppliers(String id, int position, String name);
        void showComments(String[] comments);
    }
}
