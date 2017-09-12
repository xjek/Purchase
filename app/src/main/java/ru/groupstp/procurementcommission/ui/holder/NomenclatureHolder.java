package ru.groupstp.procurementcommission.ui.holder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.groupstp.procurementcommission.R;

public class NomenclatureHolder extends RecyclerView.ViewHolder {

    public View mainView;
    public View timeWillOver;
    public TextView bookmark;
    public TextView data;
    public TextView time;
    public TextView sub;
    public TextView organization;
    public TextView name;
    public TextView count;
    public TextView user;
    public TextView comments;
    public View showComments;

    public NomenclatureHolder(View itemView) {
        super(itemView);
        itemView.setClickable(true);
        mainView = itemView;
        timeWillOver = itemView.findViewById(R.id.time_over);
        bookmark = (TextView) itemView.findViewById(R.id.bookmark);
        data = (TextView) itemView.findViewById(R.id.data);
        time = (TextView) itemView.findViewById(R.id.time);
        sub = (TextView) itemView.findViewById(R.id.sub);
        organization = (TextView) itemView.findViewById(R.id.organization);
        count = (TextView) itemView.findViewById(R.id.count);
        user = (TextView) itemView.findViewById(R.id.user);
        comments = (TextView) itemView.findViewById(R.id.comments);
        name = (TextView) itemView.findViewById(R.id.name);
        showComments = itemView.findViewById(R.id.show_comments);
    }

    public void highlightBlockWithDate(Context context, boolean highlight) {
        if (highlight) {
            timeWillOver.setVisibility(View.VISIBLE);
            time.setTextColor(ContextCompat.getColor(context, R.color.white));
            data.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {
            timeWillOver.setVisibility(View.GONE);
            time.setTextColor(ContextCompat.getColor(context, R.color.black));
            data.setTextColor(ContextCompat.getColor(context, R.color.black));
        }
    }

    public void highlightBlockBrowsed(Context context, String qid) {
       /* if (!NomenclatureTabPresenter.browsedRecordsContains(qid)) {
            mainView.setBackgroundColor(ContextCompat.getColor(context, R.color.light_gray2));
        } else {
            mainView.setBackground(ContextCompat.getDrawable(context, R.drawable.selector_item_list));
        }*/
    }

    public void clearHighlightBlockBrowsed(Context context) {
        mainView.setBackground(ContextCompat.getDrawable(context, R.drawable.selector_item_list));
    }
}
